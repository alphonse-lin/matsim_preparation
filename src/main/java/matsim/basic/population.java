package matsim.basic;


import matsim.db.CalculatePopulation;

import java.io.IOException;

public class population {
    public static CalculatePopulation CalcPopulation() throws IOException {
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
        return result;
    }
}
