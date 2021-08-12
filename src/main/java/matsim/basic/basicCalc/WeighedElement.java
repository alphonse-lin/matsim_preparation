package matsim.basic.basicCalc;

public class WeighedElement<E> {
    private E key;
    private Double weight;
    private Double thresholdLow;
    private Double thresholdHigh;

    public WeighedElement(){}

    public WeighedElement(E key, Double weight){
        this.key=key;
        this.weight=weight;
    }

    public E getKey(){
        return key;
    }

    public void setKey(E key){
        this.key=key;
    }

    public Double getWeight(){
        return weight;
    }

    public void setWeight(Double weight){
        this.weight=weight;
    }

    public Double getThresholdLow(){
        return thresholdLow;
    }

    public void setThresholdLow(Double thresholdLow){
        this.thresholdLow=thresholdLow;
    }

    public Double getThresholdHigh(){
        return thresholdHigh;
    }

    public void setThresholdHigh(Double thresholdHigh){
        this.thresholdHigh=thresholdHigh;
    }

    public String toString(){
        return "key:"+this.key+"weight:"+this.weight+"low:"+this.thresholdLow+" heigh:"+this.thresholdHigh;
    }

}
