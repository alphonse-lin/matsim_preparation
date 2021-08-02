package matsim.basic.networkCalc;

public class SingleLink {
    public int Id;
    public SingleNode from;
    public SingleNode to;
    public Double length;
    public int freeSpeed;//最大速度
    public int capacity;//可容纳的最大车辆数
    public Double permlanes; //车道数
    public int oneway;//单向 or 双向
    public String modes;//在这段路，可允许的车辆类型 modes="car, bike, taxi".
}
