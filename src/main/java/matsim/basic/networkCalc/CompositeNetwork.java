package matsim.basic.networkCalc;

import matsim.IO.GeoJSONManager;
import matsim.IO.XMLManager;
import org.geotools.feature.FeatureIterator;
import org.jdom2.Element;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;

import java.io.IOException;

public class CompositeNetwork {
    public static void main(String[] args) throws IOException {
        String filepath= "src/main/resources/road.geojson";
        CompositeNetwork.ReadGeojson(filepath);
    }
    private static void ReadGeojson(String filePath) throws IOException {
        var data=GeoJSONManager.Read(filePath);

        var result=data.geometries[0].getCoordinates();
    }

    private static void CreateXMLFormat(String filePath) throws IOException {
        String pathName="";

        Element network = new Element("network");
        network.setAttribute("name",pathName);

        //nodes
        Element nodes=new Element("nodes");

        Element node=new Element("node");
        node.setAttribute("id", "18");
        node.setAttribute("x","0");
        node.setAttribute("y","0");
        nodes.addContent(node);

        Element node1=new Element("node");
        node1.setAttribute("id", "18");
        node1.setAttribute("x","0");
        node1.setAttribute("y","0");
        nodes.addContent(node1);

        network.addContent(nodes);

        //links
        Element links=new Element("links");
        links.setAttribute("capperiod","01:00:00");
        links.setAttribute("effectivecellsize","7.5");
        links.setAttribute("effectivelanewidth","3.75");

        Element link=new Element("link");
        link.setAttribute("id", "18");
        link.setAttribute("x","0");
        link.setAttribute("y","0");
        links.addContent(link);

        network.addContent(links);

        XMLManager.ExportXML(filePath,network);
    }
}
