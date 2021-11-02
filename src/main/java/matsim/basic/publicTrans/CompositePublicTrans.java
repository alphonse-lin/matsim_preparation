package matsim.basic.publicTrans;

import matsim.IO.XMLManager;
import org.jdom2.Element;

public class CompositePublicTrans {


    public void CreateXMLFormat(String filePath) throws Exception {
        String pathName="";

        Element network = new Element("network");
        network.setAttribute("name",pathName);

        //nodes
        Element nodes= XMLManager.CreateElement("nodes","node",_nodeXmlStringDic);
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
