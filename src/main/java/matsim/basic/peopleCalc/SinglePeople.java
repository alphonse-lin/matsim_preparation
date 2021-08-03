package matsim.basic.peopleCalc;

public class SinglePeople {
    public int id;
    public AGE age;
    public EDUCATION edu;

    public TRANSPORTATION trans01;
    public TRANSPORTATION trans02;
    public TRANSPORTATION trans03;

    public SinglePeople(int Id, AGE Age, EDUCATION Edu, TRANSPORTATION[] TransModes){
        id=Id;
        age=Age;
        edu=Edu;
        trans01=TransModes[0];
        trans02=TransModes[1];
        trans03=TransModes[2];
    }
}
