package matsim.basic.configCalc;

import matsim.basic.peopleCalc.LIFETYPE;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PopulationWriter;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateDemand {
    private String _populationCSVPath;
    private String _facilityCSVPath;
    private String _networkXML;

    private Scenario _scenario;
    private FacilityCollection _fcData;
    private PopulationCollection _popcData;
    private final double SCALEFACTOR=1;
    private final double[] TRANSRATIO=new double[]{0.48, 0.21, 0.19, 0.07};

    public CreateDemand(){
        this._scenario= ScenarioUtils.createScenario(ConfigUtils.createConfig());
        new MatsimNetworkReader(_scenario.getNetwork()).readFile(_networkXML);
    }

    private void Run(String outputPath){
        this._fcData=ReadFacilities(this._facilityCSVPath);
        var HomePts=this._fcData.HomeType;
        var WorkPts=this._fcData.WorkType;
        var LeisurePts=this._fcData.LeisureType;
        var ShoppingPts=this._fcData.ShoppingType;
        var StudyPts=this._fcData.StudyType;

        this._popcData=ReadPopulation(this._populationCSVPath);
        var WorkOnly=this._popcData.WorkOnlyType;
        var StudyOnly=this._popcData.StudyOnlyType;
        var StudyAndWork=this._popcData.StudyAndWorkType;
        var NoActivity=this._popcData.NoActivityType;

//        double commuters=this._popcData.TotalCount*SCALEFACTOR;
//        double workOnly=commuters*TRANSRATIO[0];
//        double studyOnly=commuters*TRANSRATIO[1];
//        double studyAndWork=commuters*TRANSRATIO[2];
//        double noActivity=commuters*TRANSRATIO[3];

        Create4WorkOnly(WorkOnly, HomePts, WorkPts, LeisurePts, ShoppingPts);
        Create4StudyOnly(StudyOnly,HomePts,StudyPts,LeisurePts,ShoppingPts);
        Create4StudyAndWork();
        Create4NoActivity();

        PopulationWriter pw=new PopulationWriter(_scenario.getPopulation(),_scenario.getNetwork());
        pw.write(outputPath);
    }

    private void Create4WorkOnly(List<String[]> workOnly,
                                 List<String[]> homePts, List<String[]>workPts,
                                 List<String[]> leisurePts, List<String[]> shoppingPts){

        int count=workOnly.size();

        for (int i = 0; i < count; i++) {
            var singleWork=workOnly.get(i);
            var age=singleWork[3];
            var edu=singleWork[4];
            //目前先默认出行模式为CAR
            String mode="car";

            //得到home数据
            int buildingID=Integer.parseInt(singleWork[1]);
            var singleWorkHome=homePts.get(buildingID);
            Coord homeC=new Coord(Double.parseDouble(singleWorkHome[2]),Double.parseDouble(singleWorkHome[3]));

            Random rndLine=new Random();
            //随机创建工作地点
            int workLine=rndLine.nextInt(workPts.size());
            double wopenTime=Double.parseDouble(workPts.get(workLine)[4]);
            double wcloseTime=Double.parseDouble(workPts.get(workLine)[5]);
            Coord workC=ExtractCoord(workLine, workPts);

            //随机创建娱乐地点
            int leisureLine=rndLine.nextInt(leisurePts.size());
            Coord leisureC=ExtractCoord(leisureLine, leisurePts);

            //随机创建购物地点
            int shoppingLine=rndLine.nextInt(shoppingPts.size());
            Coord shoppingC=ExtractCoord(shoppingLine, shoppingPts);

            CreateWorkOnly_AddPlans(i, age, edu, homeC, workC,wopenTime,wcloseTime, leisureC,shoppingC,mode,"work");
        }
    }

    private void Create4StudyOnly(List<String[]> studyOnly,
                                  List<String[]> homePts, List<String[]>studyPts,
                                  List<String[]> leisurePts, List<String[]> shoppingPts){
        int count=studyOnly.size();

        for (int i = 0; i < count; i++) {
            var singleStudent = studyOnly.get(i);
            var age = singleStudent[3];
            var edu = singleStudent[4];
            //目前先默认出行模式为CAR
            String mode = "car";

            //得到home数据
            int buildingID = Integer.parseInt(singleStudent[1]);
            var singleWorkHome = homePts.get(buildingID);
            Coord homeC = new Coord(Double.parseDouble(singleWorkHome[2]), Double.parseDouble(singleWorkHome[3]));

            Random rndLine = new Random();
            //随机创建学习地点
            int studyLine = rndLine.nextInt(studyPts.size());
            double wopenTime = Double.parseDouble(studyPts.get(studyLine)[4]);
            double wcloseTime = Double.parseDouble(studyPts.get(studyLine)[5]);
            Coord schoolC = ExtractCoord(studyLine, studyPts);

            //随机创建娱乐地点
            int leisureLine = rndLine.nextInt(leisurePts.size());
            Coord leisureC = ExtractCoord(leisureLine, leisurePts);

            //随机创建购物地点
            int shoppingLine = rndLine.nextInt(shoppingPts.size());
            Coord shoppingC = ExtractCoord(shoppingLine, shoppingPts);

            CreateStudyOnly_AddPlans(i, age, edu, homeC, schoolC, wopenTime, wcloseTime, leisureC, shoppingC, mode, "study");
        }
    }

    //TODO 完成这部分
    private void Create4StudyAndWork(){

    }
    //TODO 完成这部分
    private void Create4NoActivity(){

    }

    private void CreateWorkOnly_AddPlans(int i, String age, String edu, Coord coordHome, Coord coordWork,Double wOpenTime, Double wCloseTime,
                                         Coord coordLeisure, Coord coordShopping, String mode, String type){

        InitiatePlan initiatePlan=new InitiatePlan(_scenario,i,coordHome,coordWork,wOpenTime,wCloseTime,type,LIFETYPE.WORKONLY);
        var work=initiatePlan.Work;
        var home=initiatePlan.Home;
        var person=initiatePlan.Person;

        List<Plan> plans=new ArrayList<>();
        switch(age){
            case "age15_24":
            case "age25_34":
                switch (edu){
                    case "primarysch":
                    case "jhighsch":
                    case "shighsch":
                    case "university":
                        plans.add(CreatePlans.Create_HWH(_scenario, work, home, mode));
                        plans.add(CreatePlans.Create_HWSH(_scenario,work,home,coordShopping,mode));
                        plans.add(CreatePlans.Create_HWLH(_scenario,work,home,coordLeisure,mode));
                        break;
                }
                break;
            case "age35_64":
                switch (edu){
                    case "primarysch":
                    case "jhighsch":
                    case "shighsch":
                    case "university":
                        plans.add(CreatePlans.Create_HWH(_scenario, work, home, mode));
                        plans.add(CreatePlans.Create_HWSH(_scenario,work,home,coordShopping,mode));
                        break;
                }
                break;
        }

        //整合数据
        for (int j = 0; j < plans.size(); j++) {person.addPlan(plans.get(j));}
        _scenario.getPopulation().addPerson(person);
    }

    private void CreateStudyOnly_AddPlans(int i, String age, String edu, Coord coordHome, Coord coordStudy,Double sOpenTime, Double sCloseTime,
                                         Coord coordLeisure, Coord coordShopping, String mode, String type){

        InitiatePlan initiatePlan=new InitiatePlan(_scenario,i,coordHome,coordStudy,sOpenTime,sCloseTime,type, LIFETYPE.STUDYONLY);
        var study=initiatePlan.Study;
        var home=initiatePlan.Home;
        var person=initiatePlan.Person;

        List<Plan> plans=new ArrayList<>();
        switch(age){
            case "age5_14":
                switch (edu){
                    case "primarysch":
                        plans.add(CreatePlans.Create_HSTDH(_scenario, study, home, mode));
                        plans.add(CreatePlans.Create_HSTDLSTDH(_scenario, study, home, coordLeisure, mode));
                        plans.add(CreatePlans.Create_HSTDSSTDH(_scenario, study, home, coordShopping, mode));
                        plans.add(CreatePlans.Create_HSTDHSTDH(_scenario, study, home, mode));
                        break;
                }
                break;
            case "age15_24":
                switch (edu){
                    case "jhighsch":
                    case "shighsch":
                        plans.add(CreatePlans.Create_HSTDH(_scenario, study, home, mode));
                        plans.add(CreatePlans.Create_HSTDLSTDH(_scenario, study, home, coordLeisure, mode));
                        plans.add(CreatePlans.Create_HSTDLSTDH(_scenario, study, home, coordShopping, mode));
                        break;
                }
                break;
        }

        //整合数据
        for (int j = 0; j < plans.size(); j++) {person.addPlan(plans.get(j));}
        _scenario.getPopulation().addPerson(person);
    }

    //TODO 完成这部分
    private void CreateStudyAndWork_AddPlans(){

    }
    //TODO 完成这部分
    private void CreateNoActivity_AddPlans(){

    }

    private Coord ExtractCoord(int index, List<String[]> selePts){
        double xCoord=Double.parseDouble(selePts.get(index)[2]);
        double yCoord=Double.parseDouble(selePts.get(index)[3]);
        return new Coord(xCoord,yCoord);
    }

    /**
     * @description extract facility info from csv file
      * @Param: null
     * @return  
    */
    private FacilityCollection ReadFacilities(String path){
        FacilityCollection fc=new FacilityCollection(path,true);
        return fc;
    }
    
    /**
     * @description extract pop info from csv file
      * @Param: null
     * @return  
    */
    private PopulationCollection ReadPopulation(String path){
        PopulationCollection popC=new PopulationCollection(path,true);
        return popC;
    }


}
