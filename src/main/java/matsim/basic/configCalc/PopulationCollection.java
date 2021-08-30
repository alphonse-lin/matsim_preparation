package matsim.basic.configCalc;

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
        for (int i = 0; i <count ; i++) {
            var singleFacility=this._readData.get(i);
            String type= singleFacility[8];
            switch (type){
                case "workonly":
                    this.WorkOnlyType.add(singleFacility);
                    break;
                case "studyonly":
                    this.StudyOnlyType.add(singleFacility);
                    break;
                case "studyandwork":
                    this.StudyAndWorkType.add(singleFacility);
                    break;
                case "noactivity":
                    this.NoActivityType.add(singleFacility);
                    break;
            }
        }
    }
}
