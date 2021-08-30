package matsim.basic.configCalc;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Plan;

public class CreatePlans {
    public static Plan Create_HWH(Scenario _scenario, Activity work, Activity home, String mode){
        //创建 plan1=H-W-H
        Plan plan1=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间：提早40分钟出门
        Activity home1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home", home.getCoord());
        home1.setEndTime(work.getStartTime().seconds()-40*60);

        plan1.addActivity(home1);
        Leg hw=_scenario.getPopulation().getFactory().createLeg(mode);
        plan1.addLeg(hw);
        plan1.addActivity(work);
        Leg wh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan1.addLeg(wh);
        plan1.addActivity(home);

        return plan1;
    }

    public static Plan Create_HWSH(Scenario _scenario, Activity work, Activity home, Coord coordShopping, String mode){
        //创建 plan2=H-W-S-H
        Plan plan2=_scenario.getPopulation().getFactory().createPlan();
        Activity shopping=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("shopping", coordShopping);

        //逛超市40分钟
        shopping.setStartTime(work.getEndTime().seconds()+40*60);
        shopping.setEndTime(shopping.getStartTime().seconds()+40*60);

        //设置出门时间 提早40分钟出门
        Activity home2=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        home2.setEndTime(work.getStartTime().seconds()-40*60);

        plan2.addActivity(home2);
        Leg hw=_scenario.getPopulation().getFactory().createLeg(mode);
        plan2.addLeg(hw);
        plan2.addActivity(work);
        Leg ws=_scenario.getPopulation().getFactory().createLeg(mode);
        plan2.addLeg(ws);
        plan2.addActivity(shopping);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan2.addLeg(sh);
        plan2.addActivity(home);

        return plan2;
    }

    public static Plan Create_HWLH(Scenario _scenario, Activity work, Activity home, Coord coordLeisure, String mode){
        //创建 plan3=H-W-L-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();
        Activity leisure=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("shopping", coordLeisure);

        //逛超市40分钟
        leisure.setStartTime(work.getEndTime().seconds()+40*60);
        leisure.setEndTime(leisure.getStartTime().seconds()+2*60*60);

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setEndTime(work.getStartTime().seconds()-40*60);

        plan.addActivity(homeOrigin);
        Leg hw=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hw);
        plan.addActivity(work);
        Leg ws=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(ws);
        plan.addActivity(leisure);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HSTDH(Scenario _scenario, Activity study, Activity home, String mode){
        //创建 plan4=H-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setEndTime(study.getStartTime().seconds()-40*60);

        plan.addActivity(homeOrigin);
        Leg hs=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hs);
        plan.addActivity(study);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HSTDLSTDH(Scenario _scenario, Activity study, Activity home, Coord coordLeisure, String mode){
        //创建 plan4=H-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setEndTime(study.getStartTime().seconds()-40*60);

        //中午出去外面休息一下，吃个饭
        Activity leisure=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("leisure",coordLeisure);
        leisure.setStartTime(study.getStartTime().seconds()+3.5*60*60);
        leisure.setEndTime(leisure.getStartTime().seconds()+1.5*60*60);

        //两点回学校，开始上课
        Activity study1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study",study.getCoord());
        study1.setStartTime(study1.getStartTime().seconds()+6*60*60);
        study1.setEndTime(study.getEndTime().seconds());

        plan.addActivity(homeOrigin);
        Leg hs=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hs);
        plan.addActivity(study);
        Leg sl=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sl);
        plan.addActivity(leisure);
        Leg ls=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(ls);
        plan.addActivity(study1);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HSTDSSTDH(Scenario _scenario, Activity study, Activity home, Coord coordShopping, String mode){
        //创建 plan4=H-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setEndTime(study.getStartTime().seconds()-40*60);

        //中午出去外面休息一下，吃个饭
        Activity shopping=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("leisure",coordShopping);
        shopping.setStartTime(study.getStartTime().seconds()+3.5*60*60);
        shopping.setEndTime(shopping.getStartTime().seconds()+1*60*60);

        //两点回学校，开始上课
        Activity study1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study",study.getCoord());
        study1.setStartTime(study.getStartTime().seconds()+5*60*60);
        study1.setEndTime(study.getEndTime().seconds());

        plan.addActivity(homeOrigin);
        Leg hs=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hs);
        plan.addActivity(study);
        Leg STDs=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDs);
        plan.addActivity(shopping);
        Leg sSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sSTD);
        plan.addActivity(study1);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HSTDHSTDH(Scenario _scenario, Activity study, Activity home, String mode){
        //创建 plan4=H-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setEndTime(study.getStartTime().seconds()-30*60);

        //中午直接回家，休息到1:30，去学校
        Activity homeNoon=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeNoon.setStartTime(study.getStartTime().seconds()+3.5*60*60);
        homeNoon.setEndTime(study.getStartTime().seconds()+5.5*60*60);

        //两点回学校，开始上课
        Activity study1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study",study.getCoord());
        study1.setStartTime(study.getStartTime().seconds()+6*60*60);
        study1.setEndTime(study.getEndTime().seconds());

        plan.addActivity(homeOrigin);
        Leg hSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hSTD);
        plan.addActivity(study);
        Leg STDh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDh);
        plan.addActivity(homeNoon);
        Leg hNSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hNSTD);
        plan.addActivity(study1);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sh);
        plan.addActivity(home);

        return plan;
    }
}
