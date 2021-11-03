package matsim.basic.publicTrans;

import matsim.basic.basicCalc.BasicCalculation;
import org.matsim.api.core.v01.Id;
import org.matsim.vehicles.*;

import java.util.Arrays;
import java.util.stream.IntStream;

public class VehicleManager {
    public VehicleTypeToXML[] VehicleTypes;
    public SingleVehicle[] Vehicles;

    public VehicleManager(VEHICLETYPE[] vehicletypes, int[] counts){
        VehicleTypes=generateVTypeXML(vehicletypes);
        Vehicles=combineVehicles(VehicleTypes, counts);
    }

    private VehicleTypeToXML[] generateVTypeXML(VEHICLETYPE[] vehicletypes){
        int count=vehicletypes.length;
        var result=new VehicleTypeToXML[count];
        for (int i = 0; i < count; i++) {
            result[i]=new VehicleTypeToXML(vehicletypes[i]);
        }
        return result;
    }

    private SingleVehicle[] combineVehicles(VehicleTypeToXML[] vehicletypes, int[] counts){
        int num=counts.length;
        SingleVehicle[] result=new SingleVehicle[BasicCalculation.sum(counts)];
        int start=0;
        for (int i = 0; i < num; i++) {
            var tempVehicles=createVehicles(vehicletypes[i],counts[i]);
            if (i!=0){start=counts[i]-counts[i-1];}
            for (int j = 0; j <tempVehicles.length ; j++) {
                result[j+start]=tempVehicles[j];
            }
        }
        return result;
    }

    private SingleVehicle[] createVehicles(VehicleTypeToXML vehicletype, int count){
        SingleVehicle[] result=new SingleVehicle[count];
        for (int i = 0; i < count; i++) {
            result[i]=new SingleVehicle(i,vehicletype);
        }
        return result;
    }
}
