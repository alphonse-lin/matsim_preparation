package matsim.basic.facilityCalc;

public class SingleFacility {
    private String _type;

    public String buildingID;
    public Double coordX;
    public Double coordY;
    public String start;
    public String end;
    public FACILITY siteType;

    public SingleFacility(String Type, String BuildingID, double CoordX, double CoordY){
        this._type=Type;
        this.buildingID=BuildingID;
        this.coordX=CoordX;
        this.coordY=CoordY;

        AttachValue(this._type);
    }

    private void AttachValue(String type){
        switch (type){
            case "C0":
                this.start="8";
                this.end="21";
                this.siteType=FACILITY.Shopping;
                break;
            case "C1":
                this.start="10";
                this.end="23";
                this.siteType=FACILITY.Leisure;
                break;
            case "C2":
                this.start="8";
                this.end="21";
                this.siteType=FACILITY.Work;
                break;
            case "H0":
                this.start="0";
                this.end="8";
                this.siteType=FACILITY.Work;
                break;
            case "H1":
                this.start="8";
                this.end="18";
                this.siteType=FACILITY.Work;
                break;
            case "H2":
                this.start="18";
                this.end="23";
                this.siteType=FACILITY.Work;
                break;
            case "R":
                this.start="0";
                this.end="23";
                this.siteType=FACILITY.Home;
                break;
            case "O0":
                this.start="8";
                this.end="18";
                this.siteType=FACILITY.Work;
                break;
            case "O1":
                this.start="9";
                this.end="21";
                this.siteType=FACILITY.Work;
                break;
            case "O2":
                this.start="10";
                this.end="23";
                this.siteType=FACILITY.Work;
                break;
            case "M0":
                this.start="0";
                this.end="8";
                this.siteType=FACILITY.Work;
                break;
            case "M1":
                this.start="8";
                this.end="18";
                this.siteType=FACILITY.Work;
                break;
            case "M2":
                this.start="18";
                this.end="23";
                this.siteType=FACILITY.Work;
                break;
            case "W0":
                this.start="8";
                this.end="8";
                this.siteType=FACILITY.Work;
                break;
            case "W1":
                this.start="8";
                this.end="18";
                this.siteType=FACILITY.Work;
                break;
            case "W2":
                this.start="18";
                this.end="23";
                this.siteType=FACILITY.Work;
                break;
            case "School0":
                this.start="8";
                this.end="17";
                this.siteType=FACILITY.Study;
                break;
            case "School1":
                this.start="8";
                this.end="22";
                this.siteType=FACILITY.Study;
                break;
            case "School2":
                this.start="8";
                this.end="21";
                this.siteType=FACILITY.Work;
                break;
        }
    }
}
