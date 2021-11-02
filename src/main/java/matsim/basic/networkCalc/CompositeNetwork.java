package matsim.basic.networkCalc;

import matsim.IO.GeoJSONManager;
import matsim.IO.XMLManager;
import org.apache.commons.lang3.ArrayUtils;
import org.geotools.feature.FeatureIterator;
import org.jdom2.Element;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.precision.GeometryPrecisionReducer;
import org.matsim.run.NetworkCleaner;
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

        PrecisionModel pm= new PrecisionModel(1000000);
        GeometryPrecisionReducer reducer=new GeometryPrecisionReducer(pm);
        _network=ConvertIntoLineString(_geo, reducer);
        NetworkGraphBuilder builder=new NetworkGraphBuilder(_network);
        _nodeXmlStringDic=builder.NodeXmlStringDic;
        _linkXmlStringDic=builder.LinkXmlStringDic;
    }

    public CompositeNetwork(String roadFilePath, String publicTranFilePath) throws IOException {
        var road=ReadGeojson(roadFilePath);
        var publicTrans=ReadGeojson(publicTranFilePath);

        _geo= ArrayUtils.addAll(road, publicTrans);

        PrecisionModel pm= new PrecisionModel(1000000);
        GeometryPrecisionReducer reducer=new GeometryPrecisionReducer(pm);
        _network=ConvertIntoLineString(_geo, reducer);
        NetworkGraphBuilder builder=new NetworkGraphBuilder(_network);
        _nodeXmlStringDic=builder.NodeXmlStringDic;
        _linkXmlStringDic=builder.LinkXmlStringDic;
    }

    private Geometry[] ReadGeojson(String filePath) throws IOException {
        var data=GeoJSONManager.Read(filePath);
        var result=data.geometries;
        return result;
    }

    private LineString[] ConvertIntoLineString(Geometry[] data, GeometryPrecisionReducer reducer){
        LineString[] result=new LineString[data.length];
        for (int i = 0; i < data.length; i++) {
            var l=reducer.reduce(data[i]);
            result[i]=(LineString) l;
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
