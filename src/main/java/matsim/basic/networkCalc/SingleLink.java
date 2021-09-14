package matsim.basic.networkCalc;

import org.locationtech.jts.geom.LineString;

import javax.sound.sampled.Line;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SingleLink {
    private final Double[] Speed=new Double[]{19.44,13.88,11.11,8.33,27.77,16.66};//70,50,40,30,100,60
    private final Double ServericeRate=0.7;
    private final Integer[] Capacity=new Integer[]{1500,1055,850,650,2100,1250};
    private final Integer[] LaneNum=new Integer[]{3,3,2,1,1,1};//车道数
    private final Integer[] OneWayNum=new Integer[]{2,2,2,1,1,1};//往返车道

    public ROADLEVEL roadlevel;
    public LineString line;
    public int Id;
    public int from;
    public int to;
    public double length;
    public double freeSpeed;//最大速度 m/s
    public double capacity;//可容纳的最大车辆数
    public int permlanes; //车道数
    public int oneway;//单向 or 双向
    public int onewayNum;//单向 or 双向
    public String modes;//在这段路，可允许的车辆类型 modes="car, bike, taxi".

    public HashMap<String,String> xmlStringDic;

    public SingleLink(int Id, int from, int to, Double length,
                      Double freeSpeed, Double capacity, int permlanes, int oneway, String modes,ROADLEVEL roadlevel){
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

        this.xmlStringDic=ConstructDic();
    }

    public SingleLink(int id, int from, int to, ROADLEVEL roadlevel, LineString line){
        this.Id=id;
        this.line=line;
        this.from=from;
        this.to=to;
        this.roadlevel=roadlevel;

        AttachValueIntoLink();
        Initiate();
        this.xmlStringDic=ConstructDic();
    }

    private void Initiate(){
        this.freeSpeed=FreeSpeedCalc(this.roadlevel);
        this.capacity=CapacityCalc(this.roadlevel);
        this.permlanes=PermlanesCalc(this.roadlevel);
        this.onewayNum=OnewayCalc(this.roadlevel);
        this.modes=ModesCalc(this.roadlevel);
    }

    private LinkedHashMap<String,String> ConstructDic(){
        LinkedHashMap<String,String> result=new LinkedHashMap<>();
        result.put("id",String.valueOf(this.Id));
        result.put("from",String.valueOf(this.from));
        result.put("to",String.valueOf(this.to));
        result.put("length",String.valueOf(this.length));
        result.put("freespeed",String.valueOf(this.freeSpeed));
        result.put("capacity",String.valueOf(this.capacity));
        result.put("permlanes",String.valueOf(this.permlanes));
//        result.put("oneway",String.valueOf(this.oneway));
        result.put("oneway",String.valueOf(1));
        result.put("modes",this.modes);
        return result;
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
            case RAILWAY:
                speed=Speed[4];
                break;
            case BUSLANE:
                speed=Speed[5];
                break;
        }
        return speed;
    }

    //based on greenberg model
    private double CapacityCalc(ROADLEVEL level){
        double capacity=0;
        double length=this.length;
        switch (level){
            case EXPRESSWAY:
                capacity=Capacity[0]*length;
                break;
            case MAINROAD:
                capacity=Capacity[1]*length;
                break;
            case SECONDARYROAD:
                capacity=Capacity[2]*length;
                break;
            case BRANCH:
                capacity=Capacity[3]*length;
                break;
            case RAILWAY:
                capacity=Capacity[4]*length;
                break;
            case BUSLANE:
                capacity=Capacity[5]*length;
        }
        return capacity;
    }

    private int PermlanesCalc(ROADLEVEL level){
        int lanesNum=0;
        switch (level){
            case EXPRESSWAY:
                lanesNum=LaneNum[0];
                break;
            case MAINROAD:
                lanesNum=LaneNum[1];
                break;
            case SECONDARYROAD:
                lanesNum=LaneNum[2];
                break;
            case BRANCH:
                lanesNum=LaneNum[3];
                break;
            case RAILWAY:
                lanesNum=LaneNum[4];
                break;
            case BUSLANE:
                lanesNum=LaneNum[5];
                break;
        }
        return lanesNum;
    }

    //To Do 完成双倍线
    private Integer OnewayCalc(ROADLEVEL level){
        int oneway=0;
        switch (level){
            case EXPRESSWAY:
                oneway=OneWayNum[0];
                break;
            case MAINROAD:
                oneway=OneWayNum[1];
                break;
            case SECONDARYROAD:
                oneway=OneWayNum[2];
                break;
            case BRANCH:
                oneway=OneWayNum[3];
                break;
            case RAILWAY:
                oneway=OneWayNum[4];
                break;
            case BUSLANE:
                oneway=OneWayNum[5];
        }
        return oneway;
    }

    private String ModesCalc(ROADLEVEL level){
        String mode="";
        switch (level){
            case EXPRESSWAY:
                mode="car, taxi";
                break;
            case MAINROAD:
                mode="car, taxi";
                break;
            case SECONDARYROAD:
                mode="car,taxi,walk";
                break;
            case BRANCH:
                mode="car,taxi,walk";
                break;
            case RAILWAY:
                mode="train";
            case BUSLANE:
                mode="bus";
        }
        return mode;
    }
}
