package matsim.basic.peopleCalc;

public class SinglePeople {
    public int id;
    public int buildingID;
    public int personID;
    public AGE age;
    public EDUCATION edu;

    public TRANSPORTATION trans01;
    public TRANSPORTATION trans02;
    public TRANSPORTATION trans03;

    public LIFETYPE lifetype;

    public SinglePeople(int Id,int BuildingID, int PersonID,AGE Age, EDUCATION Edu, TRANSPORTATION[] TransModes, LIFETYPE Lifetype){
        id=Id;
        buildingID=BuildingID;
        personID=PersonID;
        age=Age;
        edu=Edu;
        trans01=TransModes[0];
        trans02=TransModes[1];
        trans03=TransModes[2];
        lifetype=Lifetype;
    }
}
