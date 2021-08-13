package matsim.basic.networkCalc;

import org.locationtech.jts.geom.Point;

import java.util.HashMap;

public class SingleNode {
    private int Id;
    private Double coordX;
    private Double coordY;

    public HashMap<String,String> xmlStringDic;

    public SingleNode(int Id, Point pt){
        this.Id=Id;
        this.coordX=pt.getX();
        this.coordY=pt.getY();

        this.xmlStringDic=ConstructDic();
    }

    private HashMap<String,String> ConstructDic(){
        HashMap<String,String> result=new HashMap<>();
        result.put("id",String.valueOf(Id));
        result.put("x",String.valueOf(coordX));
        result.put("y",String.valueOf(coordY));
        return result;
    }
}
