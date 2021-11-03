package matsim.basic.publicTrans;

import java.text.DecimalFormat;

public class SingleVehicle {
    public String id;
    public String type;
    public VehicleTypeToXML VehicleType;

    public SingleVehicle(int id, VehicleTypeToXML vehicleType){
        DecimalFormat df=new DecimalFormat("000");
        String tempId=df.format(id);
        this.id=String.format("%s_%s",tempId,vehicleType.vType);
        this.type=vehicleType.id;
    }
}
