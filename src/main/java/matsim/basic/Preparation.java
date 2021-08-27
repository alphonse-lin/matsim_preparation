package matsim.basic;


import matsim.IO.CSVManager;
import matsim.basic.facilityCalc.CompositeFacility;
import matsim.basic.networkCalc.CompositeNetwork;
import matsim.basic.peopleCalc.CompositePopulation;
import matsim.db.CalculatePopulation;

import java.io.IOException;

public class Preparation {
    private String _roadFilePath;
    private String _buildingFilePath;

    public Preparation(String RoadFilePath, String BuildingFilePath) throws Exception {
        this._roadFilePath=RoadFilePath;
        this._buildingFilePath=BuildingFilePath;
    }

    public void Calculate(String exportXML_Network, String exportCSV_Facility,String exportCSV_Population) throws Exception {
        GenerateNetwork(this._roadFilePath, exportXML_Network);
        GenerateFacilities(this._buildingFilePath,exportCSV_Facility);
        GeneratePopulation(this._buildingFilePath,exportCSV_Population);
    }

    private void GenerateNetwork(String geojsonPath, String exportPath) throws Exception {
        CompositeNetwork compositeNetwork=new CompositeNetwork(geojsonPath);
        compositeNetwork.CreateXMLFormat(exportPath);
    }

    private void GenerateFacilities(String geojsonPath, String exportCSV) throws IOException {
        CompositeFacility compositeFacility=new CompositeFacility(geojsonPath);
        var exportData=CompositeFacility.ExportAsCSVStringArray(compositeFacility.Facilities);
        CSVManager.Write(exportCSV,exportData,
                new String[]{"siteType", "buildingID",
                        "coordX","coordY","start","end"
                });
    }

    private void GeneratePopulation(String geojsonPath, String exportCSV) throws IOException {
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

        CalculatePopulation result_pop=new CalculatePopulation(url, user, password, sql, geojsonPath);
        var result_popStructure=new CompositePopulation(result_pop);
        var exportData=CompositePopulation.ExportAsCSVStringArray(result_popStructure.popResult);

        CSVManager.Write(exportCSV,exportData,
                new String[]{"id", "buildingID","personID",
                        "age","education",
                        "trans01","trans02","trans03","lifeType"
                });
    }
}
