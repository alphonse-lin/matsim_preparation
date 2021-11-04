package matsim.basic.publicTrans;

public class PresetVehicleType {
    private VEHICLETYPE _vehicletype;
    public String id;
    public String vType;
    public int seats;
    public int standingRoom;
    public float length;
    public float width;
    public float accessTime;
    public float egreeTime;
    public String doorOperation;
    public String passengerCarEquivalents;
    public float maxVelocity;
    public int timeSpan;
    public String networkMode;

    public PresetVehicleType(VEHICLETYPE vehicletype){
        attachVehicle(vehicletype);
    }

    private void attachVehicle(VEHICLETYPE vehicletype){
        switch (vehicletype){
            case TAXI:
                taxiType();
                break;
            case BUS:
                busType();
                break;
            case TRAM:
                tramType();
                break;
            case SUBWAY:
                subwayType();
                break;

        }
    }

    //region create different transport type
    //Todo 细化的士
    private void taxiType(){
        this._vehicletype=VEHICLETYPE.BUS;
        this.id="type_taxi";
        this.seats=4;
        this.standingRoom=0;
        this.length=4f;
        this.width=1.6f;
        this.accessTime=1.0f;
        this.egreeTime=1.0f;
        this.doorOperation="serial";
        this.passengerCarEquivalents="1.0";
        //this.timeSpan=15;
    }

    private void busType(){
        this._vehicletype=VEHICLETYPE.BUS;
        this.id="type_bus";
        this.vType="B";
        this.seats=30;
        this.standingRoom=70;
        this.length=12f;
        this.width=2.5f;
        this.accessTime=1.0f;
        this.egreeTime=1.0f;
        this.doorOperation="serial";
        this.passengerCarEquivalents="1.0";
        this.maxVelocity=16.67f;//meter per second
        this.networkMode="pt";
        this.timeSpan=15;
    }

    //Todo 细化电车
    private void tramType(){
        this._vehicletype=VEHICLETYPE.BUS;
        this.id="type_tram";
        this.seats=30;
        this.standingRoom=70;
        this.length=12f;
        this.width=2.5f;
        this.accessTime=1.0f;
        this.egreeTime=1.0f;
        this.doorOperation="serial";
        this.passengerCarEquivalents="1.0";
        this.networkMode="pt";
        this.timeSpan=15;
    }

    private void subwayType(){
        this._vehicletype=VEHICLETYPE.SUBWAY;
        this.id="type_subway";
        this.vType="S";
        this.seats=600;
        this.standingRoom=840;
        this.length=120f;
        this.width=3f;
        this.accessTime=1.0f;
        this.egreeTime=1.0f;
        this.doorOperation="serial";
        this.passengerCarEquivalents="1.0";
        this.maxVelocity=22.22f;//meter per second
        this.networkMode="pt";
        this.timeSpan=4;
    }
    //endregion
}
