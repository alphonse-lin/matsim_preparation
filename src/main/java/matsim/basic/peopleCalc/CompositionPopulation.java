package matsim.basic.peopleCalc;

import matsim.db.CalculatePopulation;
import java.util.ArrayList;
import java.util.Random;

public class CompositionPopulation {
    private final Double[] _agePercent=new Double[]{4.3, 8.5, 24.13, 25.40, 34.37, 2.98, 0.32};
    private int[] _popInEachBuilding;

    public ArrayList<SinglePeople[]> popResult;

    public CompositionPopulation(CalculatePopulation result){

        _popInEachBuilding=CalcPopInBuilding(result.populationCount);
    }

    private int[] CalcPopInBuilding(Double[] popInput){
        var result=new int[popInput.length];
        for (int i = 0; i < popInput.length; i++) {
            result[i]= (int) Math.round(popInput[i]);
        }
        return result;
    }

    private SinglePeople CompositeSinglePop(int id, AGE age){
        var edu=CreateEduFromAge(age);
        var transModes=CreateTransFromAge(age);

        var single=new SinglePeople(id, age, edu, transModes);
        return single;
    }

    private TRANSPORTATION[] CreateTransFromAge(AGE age){

        switch (age){
            case age0_4:
                break;
            case age15_24:
                break;
            case age35_64:
                break;
            case age65_79:
                break;
            case age80_85:
                break;
        }
    }

    private AGE[] PercentageRandom(int[] popInput){
        double rate0=_agePercent[0];
        double rate1=_agePercent[1];
        double rate2=_agePercent[2];
        double rate3=_agePercent[3];
        double rate4=_agePercent[4];
        double rate5=_agePercent[5];
        double rate6=_agePercent[6];

        for (int i = 0; i < ; i++) {
            Random random = new Random();
            double randomNumber=random.nextDouble() * (100);
            if (randomNumber >= 0 && randomNumber <= rate0)
            {
                return AGE.age0_4;
            }
            else if (randomNumber >= rate0  && randomNumber <= rate0 + rate1)
            {
                return AGE.age5_14;
            }
            else if (randomNumber >= rate0 + rate1
                    && randomNumber <= rate0 + rate1 + rate2)
            {
                return AGE.age15_24;
            }
            else if (randomNumber >= rate0 + rate1 + rate2
                    && randomNumber <= rate0 + rate1 + rate2 + rate3)
            {
                return AGE.age25_34;
            }
            else if (randomNumber >= rate0 + rate1 + rate2 + rate3
                    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4)
            {
                return AGE.age35_64;
            }
            else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4
                    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
                    + rate5)
            {
                return AGE.age65_79;
            }
            else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4+rate5
                    && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
                    + rate5+rate6)
            {
                return AGE.age80_85;
            }
            return -1;
        }

    }

    private EDUCATION CreateEduFromAge(AGE age){

    }
}
