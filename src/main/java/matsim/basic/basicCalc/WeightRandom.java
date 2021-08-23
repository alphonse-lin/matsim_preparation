package matsim.basic.basicCalc;

import matsim.basic.peopleCalc.AGE;

import java.util.*;

public class WeightRandom<E> {
    private List<WeighedElement> weighedElements;

    public void initWeight(E[] keys, Double[] weights){
        if (keys==null||weights==null||keys.length!=weights.length){
            return;
        }

        weighedElements=new ArrayList<WeighedElement>();

        for (int i = 0; i < keys.length; i++) {
            weighedElements.add(new WeighedElement<E>(keys[i],weights[i]));
        }
        
        rangeWeightElements();
        //printRvs();
    }


    private void rangeWeightElements() {
        if (weighedElements.size()==0){
            return;
        }

        WeighedElement ele0=weighedElements.get(0);
        ele0.setThresholdLow(0d);
        ele0.setThresholdHigh(ele0.getWeight());

        for (int i = 1; i < weighedElements.size(); i++) {
            WeighedElement curElement=weighedElements.get(i);
            WeighedElement preElement=weighedElements.get(i-1);

            curElement.setThresholdLow(preElement.getThresholdHigh());
            curElement.setThresholdHigh(curElement.getThresholdLow() + curElement.getWeight());
        }
    }

    public WeighedElement getElementByRandomValue(Double rv){
        for (WeighedElement e:weighedElements){
            if (rv>=e.getThresholdLow()&&rv<e.getThresholdHigh()){
                return e;
            }
        }
        return null;
    }

    public Double getMaxRandomValue(){
        if(weighedElements == null || weighedElements.size() == 0){
            return null;
        }
        return weighedElements.get(weighedElements.size() - 1).getThresholdHigh();
    }

    public void printRvs() {
        for (WeighedElement e:weighedElements){
            System.out.println(e.toString());
        }
    }
}
