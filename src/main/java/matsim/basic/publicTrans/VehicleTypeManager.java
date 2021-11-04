package matsim.basic.publicTrans;

import matsim.basic.basicCalc.BasicCalculation;

public class VehicleTypeManager {
    public PresetVehicleType[] VehicleTypes;
//    public SingleVehicle[] Vehicles;

    public VehicleTypeManager(VEHICLETYPE[] vehicletypes, int[] counts){
        VehicleTypes=generateVTypeXML(vehicletypes);
//        Vehicles=combineVehicles(VehicleTypes, counts);
    }

    private PresetVehicleType[] generateVTypeXML(VEHICLETYPE[] vehicletypes){
        int count=vehicletypes.length;
        var result=new PresetVehicleType[count];
        for (int i = 0; i < count; i++) {
            result[i]=new PresetVehicleType(vehicletypes[i]);
        }
        return result;
    }

    //region 创建singleVehicle
//    private SingleVehicle[] combineVehicles(PresetVehicleType[] vehicletypes, int[] counts){
//        int num=counts.length;
//        SingleVehicle[] result=new SingleVehicle[BasicCalculation.sum(counts)];
//        int start=0;
//        for (int i = 0; i < num; i++) {
//            var tempVehicles=createVehicles(vehicletypes[i],counts[i]);
//            if (i!=0){start=counts[i]-counts[i-1];}
//            for (int j = 0; j <tempVehicles.length ; j++) {
//                result[j+start]=tempVehicles[j];
//            }
//        }
//        return result;
//    }
//
//    private SingleVehicle[] createVehicles(PresetVehicleType vehicletype, int count){
//        SingleVehicle[] result=new SingleVehicle[count];
//        for (int i = 0; i < count; i++) {
//            result[i]=new SingleVehicle(i,vehicletype);
//        }
//        return result;
//    }
    //endregion
}
