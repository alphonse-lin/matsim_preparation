package matsim.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;

public class XMLManager {

    public static Map<String, ArrayList<BasePopulation>> parseXML(String xmlFilePath) {
        Map<String, ArrayList<BasePopulation>> resultMap = new HashMap<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(xmlFilePath));
            doc.getDocumentElement().normalize();

            NodeList populationList = doc.getElementsByTagName("Population");

            for (int temp = 0; temp < populationList.getLength(); temp++) {
                Node nNode = populationList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String type = eElement.getAttribute("Type");

                    String[] layers = eElement.getElementsByTagName("Layer").item(0).getTextContent().split(",");
                    Double layerMin = Double.valueOf(layers[0]);
                    Double layerMax = Double.valueOf(layers[1]);

                    Double people = Double.valueOf(eElement.getElementsByTagName("People").item(0).getTextContent());

                    String[] FARs = eElement.getElementsByTagName("FAR").item(0).getTextContent().split(",");
                    Double FARMin = Double.valueOf(FARs[0]);
                    Double FARMax = Double.valueOf(FARs[1]);

                    Double maxDensity = Double.valueOf(eElement.getElementsByTagName("MaxDensity").item(0).getTextContent());
                    Double minGreen = Double.valueOf(eElement.getElementsByTagName("MinGreen").item(0).getTextContent());
                    Double maxHeight = Double.valueOf(eElement.getElementsByTagName("MaxHeight").item(0).getTextContent());

                    BasePopulation bp = new BasePopulation(type, layerMin, layerMax, people, FARMin, FARMax,
                            maxDensity, minGreen, maxHeight, "R", "Residential"); // funcName和relativeName设置为默认值

                    resultMap.computeIfAbsent("R", k -> new ArrayList<>()).add(bp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }


}
