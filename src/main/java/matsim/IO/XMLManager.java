package matsim.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLManager {
//    public static void main(String[] args) throws IOException {
//        Long start = System.currentTimeMillis();
//        createXml("src/main/resources/rssNew.xml");
//        System.out.println("运行时间："+ (System.currentTimeMillis() - start));
//    }
    public static void createXml(String pathFile) throws IOException {
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
        ExportXML(pathFile,network);
    }

    public static void EmbedElement(Element parentEle, Element subEle){
        parentEle.addContent(subEle);
    }
    public static void EmbedElement(Element parentEle, Element[] subEle){
        for (int i = 0; i < subEle.length; i++) {
            parentEle.addContent(subEle[i]);
        }
    }

    public static Element CreateElement(String eleName, HashMap<String,String> StringDic){
        Element ele=new Element(eleName);
        for (String key : StringDic.keySet()) {
            var tempName=key;
            var tempValue=StringDic.get(key);
            ele.setAttribute(tempName,tempValue);
        }
        return ele;
    }

    public static Element CreateElement(String pareEleName, String sonEleName, List<HashMap<String,String>> StringDicList){
        Element ele=new Element(pareEleName);
        for (int i = 0; i < StringDicList.size(); i++) {
            var tempHashMap=StringDicList.get(i);
            var tempElement=CreateElement(sonEleName,tempHashMap);
            ele.addContent(tempElement);
        }
        return ele;
    }

    public static Element CreateElement(String pareEleName, String[] nameArray, String[] valueArray, String sonEleName, List<HashMap<String,String>> StringDicList) throws Exception {
        Element ele=new Element(pareEleName);
        SetAttribute(ele,nameArray, valueArray);

        for (int i = 0; i < StringDicList.size(); i++) {
            var tempHashMap=StringDicList.get(i);
            var tempElement=CreateElement(sonEleName,tempHashMap);
            ele.addContent(tempElement);
        }
        return ele;
    }

    public static void SetAttribute(Element element, String[] nameArray, String[] valueArray) throws Exception {
        if (nameArray.length!=valueArray.length){
            throw new Exception("nameArray is not equal to valueArray");
        }else {
            int count=nameArray.length;
            for (int i = 0; i < count; i++) {
                element.setAttribute(nameArray[i],valueArray[i]);
            }
        }
    }

    public static void ExportXML(String pathFile, Element rss) throws IOException {
        Document document = new Document(rss,new DocType("network","http://www.matsim.org/files/dtd/network_v1.dtd"));
        Format format = Format.getCompactFormat();
        // 设置换行Tab或空格
        format.setIndent("	");
        format.setEncoding("UTF-8");
        // 4、创建XMLOutputter的对象
        XMLOutputter outputer = new XMLOutputter(format);
        // 5、利用outputer将document转换成xml文档
        File file = new File(pathFile);
        outputer.output(document, new FileOutputStream(file));
    }
}
