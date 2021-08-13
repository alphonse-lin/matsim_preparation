package matsim.basic.networkCalc;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class NetworkGraphBuilder {
    private LineString[] _lineStrings;

    /// <summary>
    /// Storing all the points from linestrings.
    /// </summary>
    public HashMap<Point, Integer> _pointsToVertices;

    /// <summary>
    /// Constructor for CityGraphBuilder with input <see cref="LineString"/> array.
    /// </summary>
    /// <param name="curves">Input curves must have been cleared and dissolved.</param>
    public NetworkGraphBuilder(MultiLineString curves)
    {
        _lineStrings = Convert2LineString(curves);
        _pointsToVertices = new HashMap<Point, Integer>();
    }

    public NetworkGraphBuilder(LineString[] curves)
    {
        _lineStrings = curves;
        _pointsToVertices = new HashMap<Point, Integer>();
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
    private void Build(){
        LineString[] lsSpan=new LineString[_lineStrings.length];
        for (int i = 0; i < lsSpan.length; i++) {
            Point[] endsPts={lsSpan[i].getStartPoint(),lsSpan[i].getEndPoint()};
            for (int j = 0; j < endsPts.length; j++) {
                var tempPt=endsPts[j];
                if (!_pointsToVertices.containsKey(tempPt)){
                    _pointsToVertices.put(tempPt,_pointsToVertices.size());
                }
            }

            var s=_pointsToVertices.get(endsPts[0]);
            var e=_pointsToVertices.get(endsPts[1]);

            SingleLink singleLink=new SingleLink(i, s,e,ROADLEVEL.SECONDARYROAD, lsSpan[i]);
        }
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

}
