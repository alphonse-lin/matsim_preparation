package matsim.basic.networkCalc;

import matsim.IO.GeoJSONManager;
import matsim.IO.XMLManager;
import org.geotools.feature.FeatureIterator;
import org.jdom2.Element;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CompositeNetwork {
    private LineString[] _network;
    private Geometry[] _geo;
    private List<HashMap<String,String>> _nodeXmlStringDic;
    private List<HashMap<String,String>> _linkXmlStringDic;

    public CompositeNetwork(String filePath) throws IOException {
        _geo=ReadGeojson(filePath);
        _network=ConvertIntoLineString(_geo);

        NetworkGraphBuilder builder=new NetworkGraphBuilder(_network);
        _nodeXmlStringDic=builder.NodeXmlStringDic;
        _linkXmlStringDic=builder.LinkXmlStringDic;
    }

    private Geometry[] ReadGeojson(String filePath) throws IOException {
        var data=GeoJSONManager.Read(filePath);
        var result=data.geometries;
        return result;
    }

    private LineString[] ConvertIntoLineString(Geometry[] data){
        LineString[] result=new LineString[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i]=(LineString) data[i];
        }
        return result;
    }

    public void CreateXMLFormat(String filePath) throws Exception {
        String pathName="";

        Element network = new Element("network");
        network.setAttribute("name",pathName);

        //nodes
        Element nodes=XMLManager.CreateElement("nodes","node",_nodeXmlStringDic);
        network.addContent(nodes);

        //links
        Element links=XMLManager.CreateElement(
                "links",
                new String[]{"capperiod","effectivecellsize","effectivelanewidth"},
                new String[]{"01:00:00","7.5","3.75"},
                "link",_linkXmlStringDic);
        network.addContent(links);

        XMLManager.ExportXML(filePath,network);
    }
}
