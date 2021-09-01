package matsim.basic.plansCalc;

import matsim.IO.CSVManager;

import java.util.ArrayList;
import java.util.List;

public class PopulationCollection {
    public List<String[]> WorkOnlyType;
    public List<String[]> StudyOnlyType;
    public List<String[]> StudyAndWorkType;
    public List<String[]> NoActivityType;
    public int TotalCount;

    private List<String[]> _readData;
    private String _csvPath;

    public PopulationCollection(String csvPath,boolean ledgendExist){
        this._csvPath=csvPath;
        ReadCSV(ledgendExist);
        ExtractValue();
    }

    private void ReadCSV(boolean ledgendExist){
        this._readData= CSVManager.Read(this._csvPath);
        int count=0;
        count=(ledgendExist)?this._readData.size()-1:this._readData.size();

        this.TotalCount=count;
        this.WorkOnlyType=new ArrayList<>();
        this.StudyOnlyType=new ArrayList<>();
        this.StudyAndWorkType=new ArrayList<>();
        this.NoActivityType=new ArrayList<>();
    }

    private void ExtractValue(){
        int count=this._readData.size();
        for (int i = 1; i <count ; i++) {
            var singlePop=this._readData.get(i);
            String type= singlePop[8];
            switch (type){
                case "workonly":
                    this.WorkOnlyType.add(singlePop);
                    break;
                case "studyonly":
                    this.StudyOnlyType.add(singlePop);
                    break;
                case "studyandwork":
                    this.StudyAndWorkType.add(singlePop);
                    break;
                case "noactivity":
                    this.NoActivityType.add(singlePop);
                    break;
            }
        }
    }
}
