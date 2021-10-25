package matsim.basic.networkCalc;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.Point;
import scala.Int;

import java.util.*;

public class NetworkGraphBuilder {

    private LineString[] _lineStrings;
    /// <summary>
    /// Storing all the points from linestrings.
    /// </summary>
    private HashMap<Point, Integer> _pointsToVertices;

    private ArrayList<SingleLink> _linkList;
    private ArrayList<SingleNode> _nodeList;

    public List<HashMap<String,String>> NodeXmlStringDic;
    public List<HashMap<String,String>> LinkXmlStringDic;

    public NetworkGraphBuilder(MultiLineString curves)
    {
        _lineStrings = Convert2LineString(curves);
        Initiate();
    }

    public NetworkGraphBuilder(LineString[] curves)
    {
        _lineStrings = curves;
        _pointsToVertices=new HashMap<>();
        Initiate();
    }


    private void Initiate(){
        _linkList=Build();
        _nodeList=ConvertPt2Node();

        LinkXmlStringDic=ConvertIntoDic_link(_linkList);
        NodeXmlStringDic=ConvertIntoDic_node(_nodeList);
    }

    private LineString[] Convert2LineString(MultiLineString mLine){
        int count=mLine.getNumGeometries();
        LineString[] result=new LineString[count];
        for (int i = 0; i < count; i++) {
            LineString line = (LineString) mLine.getGeometryN(i);
            result[i]=line;
        }
        return result;
    }

    private ArrayList<SingleLink> Build(){
        ArrayList<SingleLink>linkResult=new ArrayList<>();
        LineString[] lsSpan=new LineString[_lineStrings.length];
        for (int i = 0; i < lsSpan.length; i++) {
            Point[] endsPts={_lineStrings[i].getStartPoint(),_lineStrings[i].getEndPoint()};
            for (int j = 0; j < endsPts.length; j++) {
                var tempPt=endsPts[j];
                if (!_pointsToVertices.containsKey(tempPt)){
                    _pointsToVertices.put(tempPt,_pointsToVertices.size());
                }
            }

            //get the node id in NodeDic
            var s=_pointsToVertices.get(endsPts[0]);
            var e=_pointsToVertices.get(endsPts[1]);
            int id=linkResult.size();

            SingleLink singleLink=new SingleLink(id, s,e,ROADLEVEL.SECONDARYROAD, _lineStrings[i]);
            if (singleLink.onewayNum==2){
                linkResult.add(singleLink);
                SingleLink singleLink_reverse=new SingleLink(id+1, e,s, singleLink.length,
                        singleLink.freeSpeed, singleLink.capacity, singleLink.permlanes, 1, singleLink.modes, singleLink.roadlevel);
                linkResult.add(singleLink_reverse);
            }else{
                linkResult.add(singleLink);
            }
        }
        return linkResult;
    }

    private ArrayList<SingleNode> ConvertPt2Node(){
        int count=_pointsToVertices.size();
        ArrayList<SingleNode> result=new ArrayList<>(count);
        for (Point key : _pointsToVertices.keySet()) {
            int tempId=_pointsToVertices.get(key);
            SingleNode singleNode=new SingleNode(tempId, key);
            result.add(singleNode);
        }
        return result;
    }

    private List<HashMap<String,String>> ConvertIntoDic_node(ArrayList<SingleNode> nodeArray){
        List<HashMap<String,String>> NodeXmlStringDicArray=new ArrayList<HashMap<String,String>>(nodeArray.size());
        for (int i = 0; i < nodeArray.size(); i++) {
            var temp_node=nodeArray.get(i);
            NodeXmlStringDicArray.add(temp_node.xmlStringDic);
        }
        return NodeXmlStringDicArray;
    }

    private List<HashMap<String,String>> ConvertIntoDic_link(ArrayList<SingleLink> linkArray){
        List<HashMap<String,String>> NodeXmlStringDicArray=new ArrayList<HashMap<String,String>>(linkArray.size());
        for (int i = 0; i < linkArray.size(); i++) {
            var temp_node=linkArray.get(i);
            var debug=temp_node.xmlStringDic;
            NodeXmlStringDicArray.add(temp_node.xmlStringDic);
        }
        return NodeXmlStringDicArray;
    }

}
