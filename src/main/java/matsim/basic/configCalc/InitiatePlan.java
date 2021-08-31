package matsim.basic.configCalc;

import matsim.basic.peopleCalc.LIFETYPE;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;

import java.util.Random;

public class InitiatePlan {
    public Activity Work;
    public Activity Home;
    public Activity Study;
    public Person Person;

    //private Scenario _scenario;

    public InitiatePlan(Scenario scenario, int index, Coord coordHome, Coord[] coordActivity, Double[]relatedTime, String type, LIFETYPE lifetype){
        switch (lifetype){
            case WORKONLY:
                run_workonly(scenario, index, coordHome,coordActivity[0], relatedTime,type);
                break;
            case STUDYONLY:
                run_studyonly(scenario, index, coordHome,coordActivity[0],relatedTime,type);
                break;
            case STUDYANDWORK:
                run_studyAndWork(scenario,index,coordHome,coordActivity[0],coordActivity[1],relatedTime,type);
                break;
            case NOACTIVITY:
                run_noActivity();
                break;
        }
    }

    private void run_workonly(Scenario scenario, int index,Coord coordHome, Coord coordWork, Double[]relatedTime,String type){
        var personID= Id.createPersonId(type+index);
        Person person=scenario.getPopulation().getFactory().createPerson(personID);

        Double wOpenTime=relatedTime[0];
        Double wCloseTime=relatedTime[1];
        Random rnd=new Random();
        Double wOpenPeriod=wCloseTime-wCloseTime;

        //创建所有计划通用的活动
        Activity work=scenario.getPopulation().getFactory().createActivityFromCoord("w", coordWork);

        //创建一天中的最后一个活动：回家
        Activity home=scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",coordHome);

        //开关时间调整: 标准下班时间前后1-2个小时
        if(wOpenPeriod<=8){
            double shift=rnd.nextDouble()*2;
            work.setStartTime(3600*(wOpenTime+(shift)));
            work.setEndTime(3600*(wCloseTime+(shift)));
        }else if(wOpenPeriod>8 && wOpenPeriod<=12){
            double shift=rnd.nextDouble()*2;
            work.setStartTime(3600*(wOpenTime+(shift)));
            work.setEndTime(3600*(wCloseTime+(shift)));
        }else{
            double shift=rnd.nextDouble()*1;
            work.setStartTime(3600*(wOpenTime+(shift)));
            work.setEndTime(3600*(wCloseTime+(shift)));
        }

        this.Work=work;
        this.Home=home;
    }

    private void run_studyonly(Scenario scenario, int index,Coord coordHome, Coord coordStudy, Double[]relatedTime,String type){
        var personID= Id.createPersonId(type+index);
        Person person=scenario.getPopulation().getFactory().createPerson(personID);
        Double sOpenTime=relatedTime[0];
        Double sCloseTime=relatedTime[1];

        Random rnd=new Random();

        //创建所有计划通用的活动
        Activity study=scenario.getPopulation().getFactory().createActivityFromCoord("study", coordStudy);

        //创建一天中的最后一个活动：回家
        Activity home=scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",coordHome);

        //开关时间调整: 标准上下课时间前后0.5-1个小时
        Double sOpenPeriod=sCloseTime-sCloseTime;
        if(sOpenPeriod<=10){
            double shift=rnd.nextDouble()*0.5;
            study.setStartTime(3600*(sOpenTime+(shift)));
            study.setEndTime(3600*(sCloseTime+(shift)));
        }else{
            double shift=rnd.nextDouble();
            study.setStartTime(3600*(sOpenTime+(shift)));
            study.setEndTime(3600*(sCloseTime+(shift)));
        }

        this.Study=study;
        this.Home=home;
    }

    private void run_studyAndWork(Scenario scenario, int index,Coord coordHome, Coord coordWork, Coord coordStudy, Double[]relatedTime,String type){
        var personID= Id.createPersonId(type+index);
        Person person=scenario.getPopulation().getFactory().createPerson(personID);

        Double wOpenTime=relatedTime[0];
        Double wCloseTime=relatedTime[1];
        Double sOpenTime=relatedTime[2];
        Double sCloseTime=relatedTime[3];

        Random rnd=new Random();


        //创建所有计划通用的活动
        Activity work=scenario.getPopulation().getFactory().createActivityFromCoord("w", coordWork);

        //创建一天中的最后一个活动：回家
        Activity home=scenario.getPopulation().getFactory()
                .createActivityFromCoord("home",coordHome);

        //开关时间调整: 标准下班时间前后1-2个小时
        Double wOpenPeriod=wCloseTime-wCloseTime;
        if(wOpenPeriod<=8){
            double shift=rnd.nextDouble()*2;
            work.setStartTime(3600*(wOpenTime+(shift)));
            work.setEndTime(3600*(wCloseTime+(shift)));
        }else if(wOpenPeriod>8 && wOpenPeriod<=12){
            double shift=rnd.nextDouble()*2;
            work.setStartTime(3600*(wOpenTime+(shift)));
            work.setEndTime(3600*(wCloseTime+(shift)));
        }else{
            double shift=rnd.nextDouble()*1;
            work.setStartTime(3600*(wOpenTime+(shift)));
            work.setEndTime(3600*(wCloseTime+(shift)));
        }

        //创建所有计划通用的活动
        Activity study=scenario.getPopulation().getFactory().createActivityFromCoord("study", coordStudy);

        //开关时间调整: 标准上下课时间前后0.5-1个小时
        Double sOpenPeriod=sCloseTime-sCloseTime;
        if(sOpenPeriod<=10){
            double shift=rnd.nextDouble()*0.5;
            study.setStartTime(3600*(sOpenTime+(shift)));
            study.setEndTime(3600*(sCloseTime+(shift)));
        }else{
            double shift=rnd.nextDouble();
            study.setStartTime(3600*(sOpenTime+(shift)));
            study.setEndTime(3600*(sCloseTime+(shift)));
        }

        this.Work=work;
        this.Study=study;
        this.Home=home;
    }

    private void run_noActivity(){

    }
}
