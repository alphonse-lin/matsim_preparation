package matsim.db;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class CalculatePopulation {

    public Double[] populationCount;

    private Map<String, ArrayList<BasePopulation>> populationValueDic;
    private ArrayList<Double[]> pp_intervalLayer;
    private String _url, _user, _password, _sql;

    public CalculatePopulation(String url, String user, String password, String sql, String jsonPath) throws IOException {
        _url=url;
        _user=user;
        _password=password;
        _sql=sql;
        ExtractDataFromJSON readFromJSON = new ExtractDataFromJSON(jsonPath);

        Start();
        populationCount = CalculateAll_Population(readFromJSON.funcList, readFromJSON.layerList, readFromJSON.areaList);
    }

    private void Start(){
        this.populationValueDic=ExtractData_Population();
        this.pp_intervalLayer = GenerateLayerCollection(populationValueDic, "R");
    }

    /**
     * @description 计算总体人口
      * @Param: null
     * @return 人口数 double[]
    */
    private Double[] CalculateAll_Population(String[] funcList, String[] layerList, String[] areaList)
    {
        var length=funcList.length;
        Double[] PopulationList = new Double[length];
        for (int i = 0; i < length; i++) {
            PopulationList[i]=CalculatePopulation(funcList[i], Double.parseDouble(areaList[i]), Integer.parseInt(layerList[i]));
        }
        return PopulationList;
    }

    /**
     * @description 生成楼层数对应的人口数
      * @Param: null
     * @return  
    */
    private ArrayList<Double[]> GenerateLayerCollection(Map<String, ArrayList<BasePopulation>> pv_Dic, String function)
    {
        var ppLayerList = new ArrayList<Double[]>(pv_Dic.get(function).size());

        for (int i = 0; i < pv_Dic.get(function).size(); i++)
        {
            Double[] tempData = new Double[] { pv_Dic.get(function).get(i).layerMin, pv_Dic.get(function).get(i).layerMax };
            ppLayerList.add(tempData);
        }
        return ppLayerList;
    }

    /**
     * @description 提取数据库人口计算指数
      * @Param: null
     * @return  
    */
    private Map<String, ArrayList<BasePopulation>> ExtractData_Population()
    {
//        String url="jdbc:postgresql://39.107.177.223:5432/postgres";
//        String user="postgres";
//        String password="admin";
//        String sql = "select " +
//                "bp.bp_id, bp.bp_name, bp.bp_layer_min,bp.bp_layer_max, bp.bp_people, bp.bp_far_min, bp.bp_far_max," +
//                "bp.bp_density_max,bp.bp_green_min,bp.bp_height_max,bf.name, bf.relative_name " +
//                "from " +
//                "building_population bp, building_functions bf " +
//                "where" +
//                " bp.bp_func_id = bf.id;";
        return PostgreSQLJDBC.SelectFromDB_Population(_url, _user, _password, _sql);
    }

    /**
     * @description 基于面积+层高，计算单一体块的人口数
      * @Param: null
     * @return  
    */
    private Double CalculatePopulation(String function, Double totalArea, int layer)
    {
        var result = 0d;
        if (function.equals("R"))
        {
            var type = SpecifyPopulationType(layer);
            var peopleUnitValue = populationValueDic.get(function).get(type).people;
            result = (totalArea/layer) / peopleUnitValue;
        }
        return result;
    }

    /**
     * @description 确定属于建筑属于哪一类型
      * @Param: null
     * @return  
    */
    private int SpecifyPopulationType(int layer)
    {
        int type = -1;
        for (int i = 0; i < pp_intervalLayer.size(); i++)
        {
            if (layer >= pp_intervalLayer.get(i)[0] && layer <= pp_intervalLayer.get(i)[1]) { type = i; break; }
            else if (i == 4 && layer > pp_intervalLayer.get(i)[1])
                type = 4;
            else
                continue;
        }
        return type;
    }
}


