package matsim.basic.networkCalc;

import org.locationtech.jts.geom.Point;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SingleNode {
    private int Id;
    private Double coordX;
    private Double coordY;

    public HashMap<String,String> xmlStringDic;

    public SingleNode(int Id, Point pt){
        this.Id=Id;
//        this.coordX=Round(pt.getX());
//        this.coordY=Round(pt.getY());
        this.coordX=pt.getX();
        this.coordY=pt.getY();

        this.xmlStringDic=ConstructDic();
    }

    private double Round(double data){
        String s = String.format("%.6f", data);
        return Double.parseDouble(s);
    }

    private LinkedHashMap<String,String> ConstructDic(){
        LinkedHashMap<String,String> result=new LinkedHashMap<>();
        result.put("id",String.valueOf(Id));
        result.put("x",String.valueOf(coordX));
        result.put("y",String.valueOf(coordY));
        return result;
    }
}
