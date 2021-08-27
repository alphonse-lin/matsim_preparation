package matsim.basic;

import matsim.IO.CSVManager;
import matsim.basic.facilityCalc.SingleFacility;

import java.util.ArrayList;
import java.util.List;

public class FacilityCollection {
    public List<String[]> HomeType;
    public List<String[]> WorkType;
    public List<String[]> LeisureType;
    public List<String[]> ShoppingType;
    public List<String[]> StudyType;

    public int TotalCount;

    private String _csvPath;
    private List<String[]> _readData;

    public FacilityCollection(String csvPath,boolean ledgendExist){
        this._csvPath=csvPath;
        ReadCSV(ledgendExist);
        ExtractValue();
    }

    private void ReadCSV(boolean ledgendExist){
        this._readData= CSVManager.Read(this._csvPath);
        int count=0;
        count=(ledgendExist)?this._readData.size()-1:this._readData.size();

        this.TotalCount=count;
        this.HomeType=new ArrayList<>();
        this.WorkType=new ArrayList<>();
        this.LeisureType=new ArrayList<>();
        this.ShoppingType=new ArrayList<>();
        this.StudyType=new ArrayList<>();
    }

    private void ExtractValue(){
        int count=this._readData.size();
        for (int i = 0; i <count ; i++) {
            var singleFacility=this._readData.get(i);
            String type= singleFacility[0];
            switch (type){
                case "Home":
                    this.HomeType.add(singleFacility);
                    break;
                case "Work":
                    this.WorkType.add(singleFacility);
                    break;
                case "Leisure":
                    this.LeisureType.add(singleFacility);
                    break;
                case "Shopping":
                    this.ShoppingType.add(singleFacility);
                    break;
                case "Study":
                    this.StudyType.add(singleFacility);
                    break;
            }
        }
    }
}