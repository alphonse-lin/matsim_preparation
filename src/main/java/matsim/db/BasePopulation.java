package matsim.db;

public class BasePopulation {

    public String id;
    public int layerMin;
    public int layerMax;
    public int people;
    public double farMin;
    public double farMax;
    public double maxDensity;
    public double minGreen;
    public double maxHeight;
    public String funcName;
    public String relativeName;

    public BasePopulation(String Id, int LayerMin, int LayerMax, int People, double FARMin, double FARMax,
                          double MaxDensity, double MinGreen, double MaxHeight, String FuncName, String RelativeName
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
