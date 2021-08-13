package matsim.basic.networkCalc;

import org.locationtech.jts.geom.LineString;

import javax.sound.sampled.Line;
import java.util.HashMap;

public class SingleLink {
    private final Double[] Speed=new Double[]{19.44,13.88,11.11,8.33};//70,50,40,30
    private final Double ServericeRate=0.7;
    private final Integer[] Capacity=new Integer[]{4000,2900,2300,1700};
    private final Integer[] laneNum=new Integer[]{2,2,1,1};//车道数

    private ROADLEVEL roadlevel;
    private LineString line;
    private int Id;
    private int from;
    private int to;
    private Double length;
    private int freeSpeed;//最大速度 m/s
    private int capacity;//可容纳的最大车辆数
    private Double permlanes; //车道数
    private int oneway;//单向 or 双向
    private String modes;//在这段路，可允许的车辆类型 modes="car, bike, taxi".

    public HashMap<String,String> xmlDic;

    public SingleLink(int Id, int from, int to, Double length,
                      int freeSpeed, int capacity, Double permlanes, int oneway, String modes,ROADLEVEL roadlevel){
        this.Id=Id;
        this.from=from;
        this.to=to;
        this.length=length;
        this.freeSpeed=freeSpeed;
        this.capacity=capacity;
        this.permlanes=permlanes;
        this.oneway=oneway;
        this.modes=modes;
        this.roadlevel=roadlevel;
    }

    public SingleLink(int id, int from, int to, ROADLEVEL roadlevel, LineString line){
        this.Id=id;
        this.line=line;
        this.from=from;
        this.to=to;
        this.roadlevel=roadlevel;
    }

    /**
     * @description attach value into link
      * @Param: null
     * @return  
    */
    private void AttachValueIntoLink(){
        this.length=this.line.getLength();
    }

    private double FreeSpeedCalc(ROADLEVEL level){
        double speed=0;
        switch (level){
            case EXPRESSWAY:
                speed=Speed[0];
                break;
            case MAINROAD:
                speed=Speed[1];
                break;
            case SECONDARYROAD:
                speed=Speed[2];
                break;
            case BRANCH:
                speed=Speed[3];
                break;
        }
        return speed;
    }

    //based on greenberg model
    private double CapacityCalc(ROADLEVEL level){
        double capacity=0;
        switch (level){
            case EXPRESSWAY:
                capacity=Capacity[0]*ServericeRate;
                break;
            case MAINROAD:
                capacity=Capacity[1]*ServericeRate;
                break;
            case SECONDARYROAD:
                capacity=Capacity[2]*ServericeRate;
                break;
            case BRANCH:
                capacity=Capacity[3]*ServericeRate;
                break;
        }
        return capacity;
    }


    private void PermlanesCalc(ROADLEVEL level){
        double capacity=0;
        switch (level){
            case EXPRESSWAY:
                capacity=Capacity[0]*ServericeRate;
                break;
            case MAINROAD:
                capacity=Capacity[1]*ServericeRate;
                break;
            case SECONDARYROAD:
                capacity=Capacity[2]*ServericeRate;
                break;
            case BRANCH:
                capacity=Capacity[3]*ServericeRate;
                break;
        }
        return capacity;
    }

    private void OnewayCalc(ROADLEVEL level){

    }

    private void ModesCalc(){

    }


}
