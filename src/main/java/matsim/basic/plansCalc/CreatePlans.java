package matsim.basic.plansCalc;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Plan;

import java.util.Random;

public class CreatePlans {
    //region WorkOnly
    public static Plan Create_HWH(Scenario _scenario, Activity work, Activity home, String mode){
        //创建 plan1=H-W-H
        Plan plan1=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间：提早40分钟出门
        Activity home1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home", home.getCoord());
        home1.setStartTime(0);
        home1.setEndTime(work.getStartTime().seconds()-40*60);

        //回家
        home.setStartTime(work.getEndTime().seconds()+40*60);

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

        //设置出门时间 提早40分钟出门
        Activity home2=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        home2.setStartTime(0);
        home2.setEndTime(work.getStartTime().seconds()-40*60);

        //逛超市40分钟
        Activity shopping=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("shopping", coordShopping);
        shopping.setStartTime(work.getEndTime().seconds()+40*60);
        shopping.setEndTime(shopping.getStartTime().seconds()+40*60);

        //回家
        home.setStartTime(shopping.getEndTime().seconds()+40*60);

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

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime(work.getStartTime().seconds()-40*60);

        //逛超市40分钟
        Activity leisure=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("leisure", coordLeisure);
        leisure.setStartTime(work.getEndTime().seconds()+40*60);
        leisure.setEndTime(leisure.getStartTime().seconds()+2*60*60);

        home.setStartTime(leisure.getEndTime().seconds()+40*60);

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
    //endregion

    //region StudyOnly
    public static Plan Create_HSTDH(Scenario _scenario, Activity study, Activity home, String mode){
        //创建 plan4=H-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime(study.getStartTime().seconds()-40*60);

        home.setStartTime(study.getEndTime().seconds()+40*60);

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
        //创建 plan5=H-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime(study.getStartTime().seconds()-40*60);

        //中午出去外面休息一下，吃个饭
        Activity leisure=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("leisure",coordLeisure);
        leisure.setStartTime(study.getStartTime().seconds()+3.5*60*60);
        leisure.setEndTime(leisure.getStartTime().seconds()+1.5*60*60);

        //两点回学校，开始上课
        Activity study1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study",study.getCoord());
        study1.setStartTime(study.getStartTime().seconds()+6*60*60);
        study1.setEndTime(study.getEndTime().seconds());

        //回家
        home.setStartTime(study1.getEndTime().seconds()+40*60);

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
        //创建 plan6=H-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime(study.getStartTime().seconds()-40*60);

        //中午出去外面休息一下，吃个饭
        Activity shopping=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("shopping",coordShopping);
        shopping.setStartTime(study.getStartTime().seconds()+3.5*60*60);
        shopping.setEndTime(shopping.getStartTime().seconds()+1*60*60);

        //两点回学校，开始上课
        Activity study1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study",study.getCoord());
        study1.setStartTime(study.getStartTime().seconds()+5*60*60);
        study1.setEndTime(study.getEndTime().seconds());

        //回家
        home.setStartTime(study1.getEndTime().seconds()+40*60);

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
        //创建 plan7=H-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
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

        //回家
        home.setStartTime(study1.getEndTime().seconds()+40*60);

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
    //endregion

    //region StudyAndWork
    public static Plan Create_HSTDWH(Scenario _scenario, Activity work, Activity study, Activity home, String mode){
        //创建 plan8=H-STD-W-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门，载孩子去学校
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime(study.getStartTime().seconds()-40*60);

        //去好学校后，工作
        Activity work1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("work",work.getCoord());
        work1.setStartTime(study.getStartTime().seconds()-5*60);
        work1.setEndTime(work.getEndTime().seconds());

        //回家
        home.setStartTime(work1.getEndTime().seconds()+40*60);

        plan.addActivity(homeOrigin);
        Leg hSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hSTD);
        plan.addActivity(study);
        Leg STDw=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDw);
        plan.addActivity(work1);
        Leg wh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(wh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HWSTDH(Scenario _scenario, Activity work, Activity study, Activity home, String mode){
        //创建 plan9=H-W-STD-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早40分钟出门，载孩子去学校
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        var debug=work.getStartTime().seconds();
        homeOrigin.setEndTime(work.getStartTime().seconds()-40*60);

        //去好学校后，工作
        Activity work1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("work", work.getCoord());
        work1.setStartTime(work.getStartTime().seconds());
        work1.setEndTime(study.getEndTime().seconds()-40*60);

        //回家
        home.setStartTime(work1.getEndTime().seconds()+40*60);

        plan.addActivity(homeOrigin);
        Leg hw=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hw);
        plan.addActivity(work1);
        Leg wSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(wSTD);
        plan.addActivity(study);
        Leg STDh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HSTDWHSTDWH(Scenario _scenario, Activity work, Activity study, Activity home, String mode){
        //创建 plan10=H-STD-W-H-STD-W-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        //设置出门时间 提早30分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime(study.getStartTime().seconds()-30*60);

        //去好学校后，工作
        Activity work1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("work",work.getCoord());
        work1.setStartTime(study.getStartTime().seconds()-5*60);
        work1.setEndTime(work.getEndTime().seconds());

        //中午直接回家，休息到1:30，去学校
        Activity homeNoon=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeNoon.setStartTime(study.getStartTime().seconds()+3.5*60*60);
        homeNoon.setEndTime(study.getStartTime().seconds()+5.5*60*60);

        //两点回学校，开始上课
        Activity study1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study",study.getCoord());
        study1.setStartTime(study.getStartTime().seconds()+6*60*60);
        study1.setEndTime(study1.getStartTime().seconds()+5*60);

        Activity work2=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("work",work.getCoord());
        work2.setStartTime(study1.getEndTime().seconds()-25*60);
        work2.setEndTime(work.getEndTime().seconds());

        //回家
        home.setStartTime(work2.getEndTime().seconds()+40*60);

        plan.addActivity(homeOrigin);
        Leg hSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hSTD);
        plan.addActivity(study);
        Leg STDw=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDw);
        plan.addActivity(work1);
        Leg whN=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(whN);
        plan.addActivity(homeNoon);
        Leg hNSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hNSTD);
        plan.addActivity(study1);
        Leg STDw2=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDw2);
        plan.addActivity(work2);
        Leg w2h=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(w2h);
        plan.addActivity(home);

        return plan;
    }
    //endregion

    //region NoActivity
    public static Plan Create_H(Scenario _scenario, Activity home){
        //创建 plan11=H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HLH(Scenario _scenario, Activity home, Coord coordLeisure, String mode){
        //创建 plan12=H-L-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        Random rnd=new Random();
        //随机出门时间
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime((rnd.nextInt(10)+8)*60*60);

        //去快乐
        Activity leisure=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("leisure", coordLeisure);
        leisure.setStartTime(10*60*60);
        leisure.setEndTime(homeOrigin.getEndTime().seconds()+rnd.nextInt(4)*60*60);

        //回家
        home.setStartTime(leisure.getEndTime().seconds()+40*60);

        plan.addActivity(homeOrigin);
        Leg hl=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hl);
        plan.addActivity(leisure);
        Leg lh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(lh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HSH(Scenario _scenario, Activity home,Coord coordShopping, String mode){
        //创建 plan13=H-S-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        Random rnd=new Random();
        //随机出门时间
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime((rnd.nextInt(10)+8)*60*60);

        //去超市
        Activity shopping=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("shopping", coordShopping);
        shopping.setStartTime(9*60*60);
        shopping.setEndTime(homeOrigin.getEndTime().seconds()+1*60*60);

        //回家
        home.setStartTime(shopping.getEndTime().seconds()+40*60);

        plan.addActivity(homeOrigin);
        Leg hs=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hs);
        plan.addActivity(shopping);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HLSH(Scenario _scenario, Activity home, Coord coordLeisure,Coord coordShopping, String mode){
        //创建 plan14=H-L-S-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        Random rnd=new Random();
        //随机出门时间
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime((rnd.nextInt(10)+8)*60*60);

        //去快乐
        Activity leisure=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("leisure", coordLeisure);
        leisure.setStartTime(10*60*60);
        leisure.setEndTime(homeOrigin.getEndTime().seconds()+rnd.nextInt(3)*60*60);

        //去超市
        Activity shopping=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("shopping", coordShopping);
        shopping.setStartTime(leisure.getEndTime().seconds()+30*60);
        shopping.setEndTime(shopping.getStartTime().seconds()+1*60*60);

        //回家
        home.setStartTime(shopping.getEndTime().seconds()+40*60);

        plan.addActivity(homeOrigin);
        Leg hl=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hl);
        plan.addActivity(leisure);
        Leg ls=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(ls);
        plan.addActivity(shopping);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sh);
        plan.addActivity(home);

        return plan;
    }

    public static Plan Create_HSTDSHSTDLSTDH(Scenario _scenario, Activity study, Activity home, Coord coordLeisure, Coord coordShopping, String mode){
        //创建 plan15=H-STD-S-H-STD-L-H
        Plan plan=_scenario.getPopulation().getFactory().createPlan();

        Random rnd=new Random();
        //提前40分钟出门
        Activity homeOrigin=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",home.getCoord());
        homeOrigin.setStartTime(0);
        homeOrigin.setEndTime(study.getStartTime().seconds()-40*60);

        //带孙子孙女去学校
        Activity study1=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study", study.getCoord());
        study1.setStartTime(study.getStartTime().seconds());
        study1.setEndTime(study.getStartTime().seconds()+10*60);

        //去超市
        Activity shopping=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("shopping", coordShopping);
        shopping.setStartTime(study1.getEndTime().seconds()+30*60);
        shopping.setEndTime(shopping.getStartTime().seconds()+1*60*60);

        //回家
        Activity homeNoon=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("home", home.getCoord());
        homeNoon.setStartTime(shopping.getEndTime().seconds()+30*60);
        homeNoon.setEndTime(study1.getStartTime().seconds()+5.5*60*60);

        //送孙子孙女去学校
        Activity study2=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study", study.getCoord());
        study2.setStartTime(study1.getStartTime().seconds()+6*60*60);
        study2.setEndTime(study2.getStartTime().seconds()+10*60);

        //去快乐
        Activity leisure=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("leisure", coordLeisure);
        leisure.setStartTime(10*60*60);
        leisure.setEndTime(homeOrigin.getEndTime().seconds()+rnd.nextInt(3)*60*60);

        //去接孙子孙女
        Activity study3=_scenario.getPopulation().getFactory()
                .createActivityFromCoord("study", study.getCoord());
        study3.setStartTime(study2.getStartTime().seconds()+3*60*60);
        study3.setEndTime(study3.getStartTime().seconds()+10*60);

        //回家
        home.setStartTime(study3.getEndTime().seconds()+40*60);

        plan.addActivity(homeOrigin);
        Leg hSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hSTD);
        plan.addActivity(study1);
        Leg STDs=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDs);
        plan.addActivity(shopping);
        Leg sh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(sh);
        plan.addActivity(homeNoon);
        Leg hNSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(hNSTD);
        plan.addActivity(study2);
        Leg STDl=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDl);
        plan.addActivity(leisure);
        Leg lSTD=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(lSTD);
        plan.addActivity(study3);
        Leg STDh=_scenario.getPopulation().getFactory().createLeg(mode);
        plan.addLeg(STDh);
        plan.addActivity(home);

        return plan;
    }


    //endregion
}
