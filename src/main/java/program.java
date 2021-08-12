import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import matsim.IO.CSVManager;
import matsim.IO.GeoJSONManager;
import matsim.basic.basicCalc.WeightRandom;
import matsim.basic.peopleCalc.AGE;
import matsim.basic.peopleCalc.CompositionPopulation;
import matsim.basic.peopleCalc.SinglePeople;
import matsim.basic.population;
import matsim.db.CalculatePopulation;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class program {
    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        //数据库计算
        /*
        var filePath="src/main/resources/building.geojson";

//        var attr=new String[]{"function", "brepID","baseHeight", "totalArea","floors"};
//        var result=dataIO.Read(filePath,attr);

        String url="jdbc:postgresql://39.107.177.223:5432/postgres";
        String user="postgres";
        String password="admin";
        String sql = "select " +
                "bp.bp_id, bp.bp_name, bp.bp_layer_min,bp.bp_layer_max, bp.bp_people, bp.bp_far_min, bp.bp_far_max," +
                "bp.bp_density_max,bp.bp_green_min,bp.bp_height_max,bf.name, bf.relative_name " +
                "from " +
                "building_population bp, building_functions bf " +
                "where" +
                " bp.bp_func_id = bf.id;";

        CalculatePopulation result=new CalculatePopulation(url, user, password, sql, filePath);
*/

        //随机数
        /*
        WeightRandom<AGE> wr=new WeightRandom<AGE>();
        wr.initWeight(new AGE[]{
                        AGE.age0_4,AGE.age5_14,AGE.age15_24,AGE.age25_34,AGE.age35_64,AGE.age65_79,AGE.age80_85},
                new Double[]{0.043,0.085,0.2413,0.2540,0.3437,0.0298,0.3200});

        Random r = new Random();
        for(int i = 0; i < 10; i++){
            Double rv = r.nextDouble();
            System.out.println(wr.getElementByRandomValue(rv).getKey() + " " + rv);
        }

        HashMap<AGE, Integer> keyCount = new HashMap<AGE, Integer>();
        keyCount.put(AGE.age0_4, 0);
        keyCount.put(AGE.age5_14, 0);
        keyCount.put(AGE.age15_24, 0);
        keyCount.put(AGE.age25_34, 0);
        keyCount.put(AGE.age35_64, 0);
        keyCount.put(AGE.age65_79, 0);
        keyCount.put(AGE.age80_85, 0);
        for(int i = 0; i < 10000; i++){
            Double rv = r.nextDouble();
            AGE key = (AGE)wr.getElementByRandomValue(rv).getKey();
            keyCount.put(key, keyCount.get(key).intValue()+1);
        }

        if(!keyCount.isEmpty()) {
            Iterator it = keyCount.entrySet().iterator();

            while(it.hasNext()) {
                Map.Entry obj = (Map.Entry)it.next();
                System.out.println(obj.getKey()+" "+obj.getValue());
            }
        }
        */

        //人口生成测试
        /*
        var filePath="src/main/resources/building.geojson";

        String url="jdbc:postgresql://39.107.177.223:5432/postgres";
        String user="postgres";
        String password="admin";
        String sql = "select " +
                "bp.bp_id, bp.bp_name, bp.bp_layer_min,bp.bp_layer_max, bp.bp_people, bp.bp_far_min, bp.bp_far_max," +
                "bp.bp_density_max,bp.bp_green_min,bp.bp_height_max,bf.name, bf.relative_name " +
                "from " +
                "building_population bp, building_functions bf " +
                "where" +
                " bp.bp_func_id = bf.id;";

        CalculatePopulation result_pop=new CalculatePopulation(url, user, password, sql, filePath);
        var result_popStructure=new CompositionPopulation(result_pop);

        var exportData=CompositionPopulation.ExportAsCSVStringArray(result_popStructure.popResult);

        var exportCSV="src/main/resources/population.csv";
        CSVManager.Write(exportCSV,exportData,
        new String[]{"id", "buildingID","personID",
                "age","education",
                "trans01","trans02","trans03"
        });
         */

        var exportCSV="src/main/resources/population.csv";
        var result=CSVManager.Read(exportCSV);
    }
}
