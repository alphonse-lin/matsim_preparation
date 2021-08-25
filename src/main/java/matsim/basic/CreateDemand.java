package matsim.basic;

import matsim.IO.CSVManager;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.List;
import java.util.Scanner;

public class CreateDemand {
    private String _populationCSVPath;
    private String _facilityCSVPath;
    private String _networkXML;

    private Scenario _scenario;
    private FacilityCollection _fcData;
    private final double SCALEFACTOR=1;
    private final double[] TRANSRATIO=new double[]{0.755,0.12,0.05,0.075};

    public CreateDemand(){
        this._scenario= ScenarioUtils.createScenario(ConfigUtils.createConfig());
        new MatsimNetworkReader(_scenario.getNetwork()).readFile(_networkXML);
    }

    private void Run(){
        this._fcData=ReadFacilities(this._facilityCSVPath);
        var HomePts=this._fcData.HomeType;
        var WorkPts=this._fcData.WorkType;
        var LeisurePts=this._fcData.LeisureType;
        var ShoppingPts=this._fcData.ShoppingType;
        var StudyPts=this._fcData.StudyType;

        double commuters=this._fcData.TotalCount*SCALEFACTOR;
        double workOnly=commuters*TRANSRATIO[0];
        double studyOnly=commuters*TRANSRATIO[1];
        double studyAndWork=commuters*TRANSRATIO[2];
        double noActivity=commuters*TRANSRATIO[3];
    }

    private void Create4WorkOnly(double workOnly){
        for (int i = 0; i < workOnly; i++) {
            Coord homec=new Coord()
        }
    }

    private void ConvertJTSPt2MSPt(){

    }
    private FacilityCollection ReadFacilities(String path){
        FacilityCollection fc=new FacilityCollection(path,true);
        return fc;
    }

    private

}
