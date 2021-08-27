package matsim.basic.peopleCalc;

import matsim.basic.basicCalc.WeightRandom;
import matsim.db.CalculatePopulation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CompositePopulation {
    private final AGE[] _ageGroup=new AGE[]{
        AGE.age0_4,AGE.age5_14,AGE.age15_24,AGE.age25_34,AGE.age35_64,AGE.age65_79,AGE.age80_85};
    private final Double[] _agePercent=new Double[]{0.043,0.085,0.2413,0.2540,0.3437,0.0298,0.0032};

    private int[] _popInEachBuilding;
    private AGE[][] _popAge;

    public SinglePeople[][] popResult;
    
    public CompositePopulation(CalculatePopulation result){
        _popInEachBuilding=CalcPopInBuilding(result.populationCount);
        _popAge=PercentageRandom(_popInEachBuilding);

        popResult=CalcAllPeople(_popAge);
    }

    /**
     * @description 转化成string
      * @Param: null
     * @return  
    */
    public static List<String[]> ExportAsCSVStringArray(SinglePeople[][] popResult){
        List<String[]> popResultString=new ArrayList<>();

        for (int i = 0; i <popResult.length ; i++) {
            for (int j = 0; j < popResult[i].length; j++) {
                var temp=TransferPeopleIntoStringArray(popResult[i][j]);
                popResultString.add(temp);
            }
        }
        return popResultString;
    }

    public static List<String> ExportAsCSVString(SinglePeople[][] popResult){
        List<String> popResultString=new ArrayList<>();

        for (int i = 0; i <popResult.length ; i++) {
            for (int j = 0; j < popResult[i].length; j++) {
                var temp=TransferPeopleIntoString(popResult[i][j]);
                popResultString.add(temp);
            }
        }
        return popResultString;
    }

    private static String[] TransferPeopleIntoStringArray(SinglePeople singleP){
        String[] result=new String[]{
                String.valueOf(singleP.id),String.valueOf(singleP.buildingID),String.valueOf(singleP.personID),
                singleP.age.toString(),singleP.edu.toString(),
                singleP.trans01.toString().toLowerCase(),singleP.trans02.toString().toLowerCase(),singleP.trans03.toString().toLowerCase(),singleP.lifetype.toString().toLowerCase()
        };
        return result;
    }

    private static String TransferPeopleIntoString(SinglePeople singleP){
        String result=
                String.valueOf(singleP.id)+","+String.valueOf(singleP.buildingID)+","+String.valueOf(singleP.personID)+","+
                singleP.age.toString()+","+singleP.edu.toString()+","+
                singleP.trans01.toString().toLowerCase()+","+singleP.trans02.toString().toLowerCase()+","+singleP.trans03.toString().toLowerCase()+","+
                singleP.lifetype.toString().toLowerCase();
                ;
        return result;
    }

    /**
     * @description 数据处理，生成人口
      * @Param: null
     * @return  
    */
    private int[] CalcPopInBuilding(Double[] popInput){
        var result=new int[popInput.length];
        for (int i = 0; i < popInput.length; i++) {
            result[i]= (int) Math.round(popInput[i]);
        }
        return result;
    }
    private AGE[][] PercentageRandom(int[] popInput){
        WeightRandom<AGE> wr=new WeightRandom<AGE>();
        wr.initWeight(_ageGroup,_agePercent);

        int count=popInput.length;
        AGE[][] result=new AGE[count][];

        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int countInEach=popInput[i];
            result[i]=new AGE[countInEach];
            for(int j = 0; j < countInEach; j++){
                Double rv = r.nextDouble();
                AGE tempAge=(AGE)wr.getElementByRandomValue(rv).getKey();
                result[i][j]=tempAge;
            }
        }

        return result;
    }
    
    private SinglePeople[][] CalcAllPeople(AGE[][] popAgeInput){
        int count=popAgeInput.length;
        SinglePeople[][] result=new SinglePeople[count][];
        int k=0;

        for (int i = 0; i < popAgeInput.length; i++) {
            result[i]=new SinglePeople[popAgeInput[i].length];
            for (int j = 0; j < popAgeInput[i].length; j++) {
                result[i][j]=CompositeSinglePop(k,i,j,popAgeInput[i][j]);
                k++;
            }
        }
        return result;
    }

    /**
     * @description 基于概率，自动生成人口（学历、出行工具）
      * @Param: null
     * @return  
    */
    private SinglePeople CompositeSinglePop(int id, int buildingId, int personId, AGE age){
        var edu=CreateEduFromAge(age);
        var lifeType=CreateLifeTypeFromAgeEdu(age,edu.education);
        var single=new SinglePeople(id,buildingId,personId,age, edu.education, edu.transportation,lifeType);
        return single;
    }

    /**
     * @description 基于概率，自动生成edu+trans
      * @Param: null
     * @return  
    */
    private EDU_ADD_TRANS CreateEduFromAge(AGE age){
        EDUCATION result=null;
        TRANSPORTATION[] transportation=new TRANSPORTATION[3];

        switch (age){
            case age0_4:
                result=EDUCATION.KINDERGARTEN;
                transportation=CreateTransFromEdu(result);
                break;
            case age5_14:
                result=EDUCATION.PRIMARYSCH;
                transportation=CreateTransFromEdu(result);
                break;
            case age15_24:
                WeightRandom<EDUCATION> wr_0=new WeightRandom<EDUCATION>();
                wr_0.initWeight(new EDUCATION[]{
                        EDUCATION.PRIMARYSCH,EDUCATION.JHIGHSCH,EDUCATION.SHIGHSCH,EDUCATION.UNIVERSITY},
                        new Double[]{0.3,0.3,0.2,0.2});
                Random r_0 = new Random();
                Double rv_0 = r_0.nextDouble();
                result=(EDUCATION) wr_0.getElementByRandomValue(rv_0).getKey();
                transportation=CreateTransFromEdu(result);
                break;
            case age25_34:
                WeightRandom<EDUCATION> wr_1=new WeightRandom<EDUCATION>();
                wr_1.initWeight(new EDUCATION[]{
                                EDUCATION.PRIMARYSCH,EDUCATION.JHIGHSCH,EDUCATION.SHIGHSCH,EDUCATION.UNIVERSITY},
                        new Double[]{0.2,0.2,0.2,0.4});
                Random r_1 = new Random();
                Double rv_1 = r_1.nextDouble();
                result=(EDUCATION) wr_1.getElementByRandomValue(rv_1).getKey();
                transportation=CreateTransFromEdu(result);
                break;
            case age35_64:
                WeightRandom<EDUCATION> wr_2=new WeightRandom<EDUCATION>();
                wr_2.initWeight(new EDUCATION[]{
                                EDUCATION.PRIMARYSCH,EDUCATION.JHIGHSCH,EDUCATION.SHIGHSCH,EDUCATION.UNIVERSITY},
                        new Double[]{0.2,0.3,0.2,0.3});
                Random r_2 = new Random();
                Double rv_2 = r_2.nextDouble();
                result=(EDUCATION) wr_2.getElementByRandomValue(rv_2).getKey();
                transportation=CreateTransFromEdu(result);
                break;
            case age65_79:
                WeightRandom<EDUCATION> wr_3=new WeightRandom<EDUCATION>();
                wr_3.initWeight(new EDUCATION[]{
                                EDUCATION.PRIMARYSCH,EDUCATION.JHIGHSCH,EDUCATION.SHIGHSCH,EDUCATION.UNIVERSITY},
                        new Double[]{0.3,0.4,0.2,0.1});
                Random r_3 = new Random();
                Double rv_3 = r_3.nextDouble();
                result=(EDUCATION) wr_3.getElementByRandomValue(rv_3).getKey();
                transportation=CreateTransFromEdu(result);
                break;
            case age80_85:
                WeightRandom<EDUCATION> wr_4=new WeightRandom<EDUCATION>();
                wr_4.initWeight(new EDUCATION[]{
                                EDUCATION.PRIMARYSCH,EDUCATION.JHIGHSCH,EDUCATION.SHIGHSCH,EDUCATION.UNIVERSITY},
                        new Double[]{0.4,0.3,0.2,0.1});
                Random r_4 = new Random();
                Double rv_4 = r_4.nextDouble();
                result=(EDUCATION) wr_4.getElementByRandomValue(rv_4).getKey();
                transportation=CreateTransFromEdu(result);
                break;
        }
        EDU_ADD_TRANS finalResult=new EDU_ADD_TRANS(result, transportation);
        return finalResult;
    }

    /**
     * @description 基于概率，自动生成出行工具模型
      * @Param: null
     * @return
    */
    private TRANSPORTATION[] CreateTransFromEdu(EDUCATION education){
        TRANSPORTATION[] result=new TRANSPORTATION[3];
        switch (education){
            case KINDERGARTEN:
                WeightRandom<TRANSPORTATION> wr_0=new WeightRandom<TRANSPORTATION>();
                wr_0.initWeight(new TRANSPORTATION[]{
                                TRANSPORTATION.WALK,TRANSPORTATION.TRAIN,TRANSPORTATION.BUS,TRANSPORTATION.CAR},
                        new Double[]{0.85,0.05,0.05,0.05});
                Random r_0 = new Random();
                for (int i = 0; i < 3; i++) {
                    Double rv_0 = r_0.nextDouble();
                    result[i]=(TRANSPORTATION) wr_0.getElementByRandomValue(rv_0).getKey();
                }
                break;
            case PRIMARYSCH:
                WeightRandom<TRANSPORTATION> wr_1=new WeightRandom<TRANSPORTATION>();
                wr_1.initWeight(new TRANSPORTATION[]{
                                TRANSPORTATION.WALK,TRANSPORTATION.TRAIN,TRANSPORTATION.BUS,TRANSPORTATION.CAR},
                        new Double[]{0.5,0.2,0.2,0.1});
                Random r_1 = new Random();
                for (int i = 0; i < 3; i++) {
                    Double rv_0 = r_1.nextDouble();
                    result[i]=(TRANSPORTATION) wr_1.getElementByRandomValue(rv_0).getKey();
                }
                break;
            case JHIGHSCH:
                WeightRandom<TRANSPORTATION> wr_2=new WeightRandom<TRANSPORTATION>();
                wr_2.initWeight(new TRANSPORTATION[]{
                                TRANSPORTATION.WALK,TRANSPORTATION.TRAIN,TRANSPORTATION.BUS,TRANSPORTATION.CAR},
                        new Double[]{0.4,0.2,0.2,0.2});
                Random r_2 = new Random();
                for (int i = 0; i < 3; i++) {
                    Double rv_0 = r_2.nextDouble();
                    result[i]=(TRANSPORTATION) wr_2.getElementByRandomValue(rv_0).getKey();
                }
                break;
            case SHIGHSCH:
                WeightRandom<TRANSPORTATION> wr_3=new WeightRandom<TRANSPORTATION>();
                wr_3.initWeight(new TRANSPORTATION[]{
                                TRANSPORTATION.WALK,TRANSPORTATION.TRAIN,TRANSPORTATION.BUS,TRANSPORTATION.CAR},
                        new Double[]{0.3,0.2,0.2,0.3});
                Random r_3 = new Random();
                for (int i = 0; i < 3; i++) {
                    Double rv_0 = r_3.nextDouble();
                    result[i]=(TRANSPORTATION) wr_3.getElementByRandomValue(rv_0).getKey();
                }
                break;
            case UNIVERSITY:
                WeightRandom<TRANSPORTATION> wr_4=new WeightRandom<TRANSPORTATION>();
                wr_4.initWeight(new TRANSPORTATION[]{
                                TRANSPORTATION.WALK,TRANSPORTATION.TRAIN,TRANSPORTATION.BUS,TRANSPORTATION.CAR},
                        new Double[]{0.3,0.2,0.1,0.4});
                Random r_4 = new Random();
                for (int i = 0; i < 3; i++) {
                    Double rv_0 = r_4.nextDouble();
                    result[i]=(TRANSPORTATION) wr_4.getElementByRandomValue(rv_0).getKey();
                }
                break;
        }
        return result;
    }

    private TRANSPORTATION[] CreateTransFromAge(AGE age){
        TRANSPORTATION[] result=new TRANSPORTATION[3];
        switch (age){
            case age0_4:
                result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.BUS,TRANSPORTATION.TRAIN};
                break;
            case age5_14:
                result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.BUS,TRANSPORTATION.TRAIN};
                break;
            case age15_24:
                int percentage_0=30;
                Random random_0 = new Random();
                int i_0 = random_0.nextInt(99);
                if(i_0 >=0 &&i_0<percentage_0)
                    result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.CAR,TRANSPORTATION.TRAIN};
                else
                    result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.BUS,TRANSPORTATION.TRAIN};
                break;
            case age25_34:
                int percentage_1=50;
                Random random_1 = new Random();
                int i_1 = random_1.nextInt(99);
                if(i_1 >=0 &&i_1<percentage_1)
                    result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.CAR,TRANSPORTATION.TRAIN};
                else
                    result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.BUS,TRANSPORTATION.TRAIN};
                break;
            case age35_64:
                int percentage_2=70;
                Random random_2 = new Random();
                int i_2 = random_2.nextInt(99);
                if(i_2>=0 && i_2<percentage_2)
                    result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.CAR,TRANSPORTATION.TRAIN};
                else
                    result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.BUS,TRANSPORTATION.TRAIN};
                break;
            case age65_79:
                result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.BUS,TRANSPORTATION.TRAIN};
                break;
            case age80_85:
                result=new TRANSPORTATION[]{TRANSPORTATION.WALK,TRANSPORTATION.BUS,TRANSPORTATION.TRAIN};
                break;
        }
        return result;
    }

    /**
     * @description 基于概率，自动生成生活模式
      * @Param: age, edu
     * @return  
    */
    private LIFETYPE CreateLifeTypeFromAgeEdu(AGE age, EDUCATION edu){
        LIFETYPE result=null;
        switch (age){
            case age0_4:
                result=LIFETYPE.NOACTIVITY;
                break;
            case age5_14:
                result=LIFETYPE.STUDYONLY;
                break;
            case age15_24:
                switch (edu){
                    case PRIMARYSCH:
                        result=LIFETYPE.WORKONLY;
                    case JHIGHSCH:
                        WeightRandom<LIFETYPE> wr_0=new WeightRandom<LIFETYPE>();
                        wr_0.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYONLY},
                                new Double[]{0.2,0.8});
                        Random r_0 = new Random();
                        result=(LIFETYPE) wr_0.getElementByRandomValue(r_0.nextDouble()).getKey();
                        break;
                    case SHIGHSCH:
                        WeightRandom<LIFETYPE> wr_1=new WeightRandom<LIFETYPE>();
                        wr_1.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYONLY},
                                new Double[]{0.3,0.7});
                        Random r_1 = new Random();
                        result=(LIFETYPE) wr_1.getElementByRandomValue(r_1.nextDouble()).getKey();
                        break;
                    case UNIVERSITY:
                        result=LIFETYPE.WORKONLY;
                }
            case age25_34:
                switch (edu){
                    case PRIMARYSCH:
                        WeightRandom<LIFETYPE> wr_0=new WeightRandom<LIFETYPE>();
                        wr_0.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYANDWORK},
                                new Double[]{0.5,0.5});
                        Random r_0 = new Random();
                        result=(LIFETYPE) wr_0.getElementByRandomValue(r_0.nextDouble()).getKey();
                        break;
                    case JHIGHSCH:
                        WeightRandom<LIFETYPE> wr_1=new WeightRandom<LIFETYPE>();
                        wr_1.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYANDWORK},
                                new Double[]{0.6,0.4});
                        Random r_1 = new Random();
                        result=(LIFETYPE) wr_1.getElementByRandomValue(r_1.nextDouble()).getKey();
                        break;
                    case SHIGHSCH:
                        WeightRandom<LIFETYPE> wr_2=new WeightRandom<LIFETYPE>();
                        wr_2.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYANDWORK},
                                new Double[]{0.6,0.4});
                        Random r_2 = new Random();
                        result=(LIFETYPE) wr_2.getElementByRandomValue(r_2.nextDouble()).getKey();
                        break;
                    case UNIVERSITY:
                        WeightRandom<LIFETYPE> wr_3=new WeightRandom<LIFETYPE>();
                        wr_3.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYONLY, LIFETYPE.STUDYANDWORK},
                                new Double[]{0.4,0.3,0.3});
                        Random r_3 = new Random();
                        result=(LIFETYPE) wr_3.getElementByRandomValue(r_3.nextDouble()).getKey();
                        break;
                }
                break;
            case age35_64:
                switch (edu){
                    case PRIMARYSCH:
                        WeightRandom<LIFETYPE> wr_0=new WeightRandom<LIFETYPE>();
                        wr_0.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYANDWORK},
                                new Double[]{0.7,0.3});
                        Random r_0 = new Random();
                        result=(LIFETYPE) wr_0.getElementByRandomValue(r_0.nextDouble()).getKey();
                        break;
                    case JHIGHSCH:
                        WeightRandom<LIFETYPE> wr_1=new WeightRandom<LIFETYPE>();
                        wr_1.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYANDWORK},
                                new Double[]{0.7,0.3});
                        Random r_1 = new Random();
                        result=(LIFETYPE) wr_1.getElementByRandomValue(r_1.nextDouble()).getKey();
                        break;
                    case SHIGHSCH:
                        WeightRandom<LIFETYPE> wr_2=new WeightRandom<LIFETYPE>();
                        wr_2.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY,LIFETYPE.STUDYANDWORK},
                                new Double[]{0.7,0.3});
                        Random r_2 = new Random();
                        result=(LIFETYPE) wr_2.getElementByRandomValue(r_2.nextDouble()).getKey();
                        break;
                    case UNIVERSITY:
                        WeightRandom<LIFETYPE> wr_3=new WeightRandom<LIFETYPE>();
                        wr_3.initWeight(new LIFETYPE[]{
                                        LIFETYPE.WORKONLY, LIFETYPE.STUDYANDWORK},
                                new Double[]{0.7,0.3});
                        Random r_3 = new Random();
                        result=(LIFETYPE) wr_3.getElementByRandomValue(r_3.nextDouble()).getKey();
                        break;
                }
                break;
            case age65_79:
                result=LIFETYPE.NOACTIVITY;
                break;
            case age80_85:
                result=LIFETYPE.NOACTIVITY;
                break;
        }
        return result;
    }
}
