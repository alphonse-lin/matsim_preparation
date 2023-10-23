package matsim.basic.eventCalc;

public class SingleEvent {
    public int LinkID;
    public double StartTime;
    public double FreeSpeed;
    public double FlowCapaicty;

    public SingleEvent(int LinkID, double StartTime, double FreeSpeed, double FlowCapaicty){
        this.LinkID=LinkID;
        this.StartTime=StartTime;
        this.FreeSpeed=FreeSpeed;
        this.FlowCapaicty=FlowCapaicty;
    }
}
