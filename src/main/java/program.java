import matsim.IO.FileManager;
import matsim.basic.Preparation;
import matsim.basic.plansCalc.CreateDemand;
import org.matsim.run.NetworkCleaner;

import java.io.File;

public class program {
    public static void main(String[] args) throws Exception {
        Long start = System.currentTimeMillis();
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

        //生成population.csv
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
        var result_popStructure=new CompositePopulation(result_pop);

        var exportData=CompositePopulation.ExportAsCSVStringArray(result_popStructure.popResult);

        var exportCSV="src/main/resources/population.csv";
        CSVManager.Write(exportCSV,exportData,
        new String[]{"id", "buildingID","personID",
                "age","education",
                "trans01","trans02","trans03"
        });

         */

        //读取csv
        /*
        var exportCSV="src/main/resources/population.csv";
        var result=CSVManager.Read(exportCSV);
         */

        //读取geojson
        /*
        InputStream input = new FileInputStream("src/main/resources/road.geojson");
        GeoJSONManager geoRead=new GeoJSONManager();
        var data=geoRead.read(input);
         */

        //生成network.xml
        /*
        String geojsonPath="src/main/resources/road.geojson";
        String exportXML="src/main/resources/network.xml";
        CompositeNetwork compositeNetwork=new CompositeNetwork(geojsonPath);
        compositeNetwork.CreateXMLFormat(exportXML);

         */

        //生成facilities.csv
        /*
        String geojsonPath="src/main/resources/building.geojson";
        CompositeFacility compositeFacility=new CompositeFacility(geojsonPath);
        var exportData=CompositeFacility.ExportAsCSVStringArray(compositeFacility.Facilities);

        var exportCSV="src/main/resources/facilities.csv";
        CSVManager.Write(exportCSV,exportData,
                new String[]{"siteType", "buildingID",
                        "coordX","coordY","start","end"
                });
         */

        //生成所有
//        String inputDir="src/main/resources/debug/tq38_london_strategy/001_input"+File.separator;
//        String outputDir="src/main/resources/debug/tq38_london_strategy_urbanxtools"+File.separator;
        String inputDir="D:\\Code\\114_temp\\008_CodeCollection\\005_java\\matsim_preparation\\src\\main\\resources\\debug\\tq38_london_strategy\\001_input"+File.separator;
        String outputDir="D:\\Code\\114_temp\\008_CodeCollection\\005_java\\matsim_preparation\\src\\main\\resources\\debug\\tq38_london_strategy_urbanxtools"+File.separator;

        String roadPath=ChangeRoad(inputDir+"roadnetwork.geojson");
        String buildingPath=ChangeRoad(inputDir+"building.geojson");
        String xmlPath=ChangeRoad("C:\\Users\\AlphonsePC\\AppData\\Roaming\\Grasshopper\\Libraries\\UrbanXTools\\data\\indexCalculation.xml");

        String tempNetworkXML=outputDir+"temp_network.xml";
        String cleanedNetworkXML=outputDir+"network.xml";
        String facilityCSV=outputDir+"facilities.csv";
        String populationCSV=outputDir+"population.csv";

        //第一阶段生成数据：network.xml, facilityCSV, populationCSV
        Preparation preparation=new Preparation(xmlPath, roadPath,buildingPath);
        preparation.Calculate(tempNetworkXML,facilityCSV,populationCSV);

        //clean road
        var cleanNetwork = new NetworkCleaner();
        cleanNetwork.run(tempNetworkXML, cleanedNetworkXML);

        //第二阶段生数据：plans.xml
        String plansXML=outputDir+"plans.xml";
        CreateDemand createDemand=new CreateDemand(populationCSV,facilityCSV,cleanedNetworkXML);
        createDemand.Run(plansXML);

        //delete unnesseary files
        FileManager.DeleteFiles(new String[]{tempNetworkXML, facilityCSV, populationCSV});

        System.out.println("运行时间："+ (System.currentTimeMillis() - start));
    }

    public static String ChangeRoad(String path){
        if(path.contains("\\\\")){
            return path.replaceAll("\\\\", "/");
        }
        else{return path;}
    }
}
