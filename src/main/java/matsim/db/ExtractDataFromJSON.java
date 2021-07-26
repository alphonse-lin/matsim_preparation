package matsim.db;

import matsim.IO.dataIO;

import java.io.IOException;

public class ExtractDataFromJSON {
    private String[] attrList=new String[]{"function", "floors", "totalArea"};

    public String[] funcList ;
    public String[] layerList ;
    public String[] areaList ;

    public ExtractDataFromJSON(String jsonFilePath) throws IOException {
        ReadJson(jsonFilePath);
    }
    private void ReadJson(String jsonFilePath) throws IOException {
        var feactureCollection=dataIO.Read(jsonFilePath, attrList);

        var length = feactureCollection.attrDataDic;
        funcList = feactureCollection.attrDataDic.get(attrList[0]);
        layerList= feactureCollection.attrDataDic.get(attrList[1]);
        areaList = feactureCollection.attrDataDic.get(attrList[2]);//总体面积
    }
}
