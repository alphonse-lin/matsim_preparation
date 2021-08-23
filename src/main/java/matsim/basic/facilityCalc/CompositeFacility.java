package matsim.basic.facilityCalc;

import matsim.IO.GeoJSONData;
import matsim.IO.GeoJSONManager;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositeFacility {
    private GeoJSONData _readData;
    private Map<String,String[]> _attrDic;
    private Geometry[] _geoDic;

    public SingleFacility[] Facilities;

    public CompositeFacility(String filePath) throws IOException {
        this._readData=ReadGeojson(filePath);
        this._attrDic=_readData.attrDataDic;
        this._geoDic=_readData.geometries;
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
        var brepID=readDic.get("brepID");
        var buildingID=readDic.get("buildingID");
        var subiteID=readDic.get("subiteID");
        var blockID=readDic.get("blockID");

        int count=brepID.length;
        String[][] result=new String[count][];

        for (int i = 0; i < count; i++) {
            result[i][0]=functionList[i];
            result[i][1]=String.format("%s_%s_%s_%s",blockID[i],subiteID[i],buildingID[i],brepID[i]);
        }
        return result;
    }

    private SingleFacility ConstructSingleFacility(Geometry geo, String type, String name ){
        var centroid=geo.getCentroid();
        SingleFacility facility=new SingleFacility(type,name,centroid.getX(), centroid.getY());
        return facility;
    }

    /**
     * @description attach value
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
        var result=new String[]{facility.siteType.toString(),facility.buildingID,
                                String.valueOf(facility.coordX),String.valueOf(facility.coordY),
                                String.valueOf(facility.start),String.valueOf(facility.siteType)};
        return result;
    }
}
