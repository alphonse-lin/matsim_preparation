package matsim.basic.facilityCalc;

import matsim.IO.GeoJSONData;
import matsim.IO.GeoJSONManager;
import matsim.basic.basicCalc.WeightRandom;
import matsim.basic.peopleCalc.AGE;
import matsim.basic.peopleCalc.EDUCATION;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.util.*;

public class CompositeFacility {
    private final String[] _CGroup=new String[]{"C0","C1","C2"};
    private final Double[] _CPercent=new Double[]{0.4,0.4,0.2};

    private final String[] _HGroup=new String[]{"H0","H1","H2"};
    private final Double[] _HPercent=new Double[]{0.4,0.4,0.2};

    private final String[] _OGroup=new String[]{"O0","O1","O2","School0","School1","School2"};
    private final Double[] _OPercent=new Double[]{0.3,0.4,0.2,0.06,0.02,0.02};

    private final String[] _MGroup=new String[]{"M0","M1","M2"};
    private final Double[] _MPercent=new Double[]{0.4,0.4,0.2};

    private final String[] _WGroup=new String[]{"W0","W1","W2"};
    private final Double[] _WPercent=new Double[]{0.4,0.3,0.3};

    private RandomInLandType _randomInLandType;
    private GeoJSONData _readData;
    private Map<String,String[]> _attrDic;
    private Geometry[] _geoDic;

    public SingleFacility[] Facilities;

    public CompositeFacility(String filePath) throws IOException {
        this._readData=ReadGeojson(filePath);
        this._attrDic=_readData.attrDataDic;
        this._geoDic=_readData.geometries;
        this._randomInLandType=InitiateRandom();
        this.Facilities=ConstructFacilities(this._attrDic,this._geoDic);

    }

    /**
     * @description attach value
     */
    private GeoJSONData ReadGeojson(String filePath) throws IOException {
        var data = GeoJSONManager.Read(filePath, new String[]{"brepID", "buildingID", "subiteID", "blockID","function"});
        return data;
    }

    private SingleFacility[] ConstructFacilities(Map<String,String[]> attrDic, Geometry[] geoDic){
        var attrData=ConstructBuildingID(attrDic);
        int count=attrData.length;
        SingleFacility[] result=new SingleFacility[count];

        for (int i = 0; i < count; i++) {
            var tempGeo=geoDic[i];
            result[i]=ConstructSingleFacility(tempGeo,attrData[i][0],attrData[i][1]);
        }
        return result;
    }

    private String[][] ConstructBuildingID(Map<String,String[]> readDic){
        var functionList=readDic.get("function");
//        var brepID=readDic.get("brepID");
//        var buildingID=readDic.get("buildingID");
//        var subiteID=readDic.get("subsiteID");
//        var blockID=readDic.get("blockID");

        int count=functionList.length;
        String[][] result=new String[count][];

        for (int i = 0; i < count; i++) {
            result[i]=new String[2];
            result[i][0]=CreateSubType(functionList[i]);
            result[i][1]=String.valueOf(i);
//            result[i][1]=String.format("%s_%s_%s_%s",blockID[i],subiteID[i],buildingID[i],brepID[i]);
        }
        return result;
    }
    
    /**
     * @description initiate random value
      * @Param: null
     * @return  
    */
    private RandomInLandType InitiateRandom(){
        WeightRandom<String> C_WR=new WeightRandom<String>();
        C_WR.initWeight(_CGroup,_CPercent);

        WeightRandom<String> H_WR=new WeightRandom<String>();
        H_WR.initWeight(_HGroup,_HPercent);

        WeightRandom<String> O_WR=new WeightRandom<String>();
        O_WR.initWeight(_OGroup,_OPercent);

        WeightRandom<String> M_WR=new WeightRandom<String>();
        M_WR.initWeight(_MGroup,_MPercent);

        WeightRandom<String> W_WR=new WeightRandom<String>();
        W_WR.initWeight(_WGroup,_WPercent);

        return new RandomInLandType(C_WR,H_WR, O_WR, M_WR, W_WR);
    }

    /**
     * @description create sub type based on random value
      * @Param: null
     * @return  
    */
    private String CreateSubType(String type){
        Random r = new Random();
        Double rv = r.nextDouble();

        String result="";
        switch (type){
            case "C":
                result=(String) _randomInLandType.C_WR.getElementByRandomValue(rv).getKey();
                break;
            case "R":
                result="R";
                break;
            case "H":
                result=(String) _randomInLandType.H_WR.getElementByRandomValue(rv).getKey();
                break;
            case "O":
                result=(String) _randomInLandType.O_WR.getElementByRandomValue(rv).getKey();
                break;
            case "M":
                result=(String) _randomInLandType.M_WR.getElementByRandomValue(rv).getKey();
                break;
            case "W":
                result=(String) _randomInLandType.W_WR.getElementByRandomValue(rv).getKey();
                break;
        }
        return result;
    }

    private SingleFacility ConstructSingleFacility(Geometry geo, String type, String name ){
        var centroid=geo.getCentroid();
        SingleFacility facility=new SingleFacility(type,name,centroid.getX(), centroid.getY());
        return facility;
    }

    /**
     * @description export as csv
     */
    public static List<String[]> ExportAsCSVStringArray(SingleFacility[] facilities){
        int count=facilities.length;
        List<String[]> result=new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(SingleFamily2String(facilities[i]));
        }
        return result;
    }

    private static String[] SingleFamily2String(SingleFacility facility){
        var result=new String[]{facility.siteType.toString().toLowerCase(),facility.buildingID,
                                String.valueOf(facility.coordX),String.valueOf(facility.coordY),
                                String.valueOf(facility.start),String.valueOf(facility.end)};
        return result;
    }

    //TODO 输出facilities.xml
}
