package matsim.db;

public class BasePopulation {

    public String id;
    public Double layerMin;
    public Double layerMax;
    public Double people;
    public Double farMin;
    public Double farMax;
    public Double maxDensity;
    public Double minGreen;
    public Double maxHeight;
    public String funcName;
    public String relativeName;

    public BasePopulation(String Id, Double LayerMin, Double LayerMax, Double People, Double FARMin, Double FARMax,
                          Double MaxDensity, Double MinGreen, Double MaxHeight, String FuncName, String RelativeName
    )
    {
        id = Id;
        layerMin = LayerMin;
        layerMax = LayerMax;
        people = People;
        farMin = FARMin;
        farMax = FARMax;
        maxDensity = MaxDensity;
        minGreen = MinGreen;
        maxHeight = MaxHeight;
        funcName = FuncName;
        relativeName = RelativeName;
    }
}
