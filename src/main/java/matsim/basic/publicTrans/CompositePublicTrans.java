package matsim.basic.publicTrans;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.vehicles.*;

import java.text.DecimalFormat;

public class CompositePublicTrans {

    //测试
    public static void main(String[] args) {
        VEHICLETYPE[] vehicletypes=new VEHICLETYPE[]{VEHICLETYPE.BUS,VEHICLETYPE.SUBWAY};
        int[] counts=new int[]{20,30};
        var debug=new CompositePublicTrans();
        String outputPath="E:\\114_temp\\008_代码集\\005_java\\matsim_preparation\\src\\main\\resources\\vehicles_mawan.xml";

        VehicleTypeManager vehicleTypeManager =new VehicleTypeManager(vehicletypes, counts);
        debug.createVehicles(vehicleTypeManager.VehicleTypes,counts);
        debug.transitVehiclesWriter(debug._vehicles,outputPath);
    }

    private Scenario _scenario;
    private Vehicles _vehicles;
    private VehiclesFactory _vehiclesFactory;

    //region 创建vehicles
    public CompositePublicTrans(){
        this._scenario= ScenarioUtils.createScenario(ConfigUtils.createConfig());
        inistateVehicles();
    }

    public void createVehicles(PresetVehicleType[] presetVehicleType, int[] nOfVehicles){
        int amount=nOfVehicles.length;
        for (int i = 0; i < amount; i++) {
            createVehicle(presetVehicleType[i],nOfVehicles[i]);
        }
    }

    public void createVehicle(PresetVehicleType presetVehicleType, int nOfVehicles){
        VehicleType vehicleType=this._vehiclesFactory.createVehicleType(Id.create(presetVehicleType.id,VehicleType.class));
        this._vehicles.addVehicleType(vehicleType);

        VehicleCapacity capacity=vehicleType.getCapacity();
        capacity.setSeats(presetVehicleType.seats);
        capacity.setStandingRoom(presetVehicleType.standingRoom);
        vehicleType.setLength(presetVehicleType.length);
        vehicleType.setWidth(presetVehicleType.width);
        vehicleType.setMaximumVelocity(presetVehicleType.maxVelocity);
        vehicleType.setNetworkMode(presetVehicleType.networkMode);

        DecimalFormat df=new DecimalFormat("000");

        for (int i = 0; i < nOfVehicles; i++) {
            String tempId=df.format(i);
            var vehicleId=String.format("%s_%s",tempId,presetVehicleType.vType);
            this._vehicles.addVehicle(this._vehiclesFactory.createVehicle(Id.createVehicleId(vehicleId),vehicleType));
        }
    }

    public void transitVehiclesWriter(Vehicles vehicles, String outputPath){
        var output=new MatsimVehicleWriter(vehicles);
        output.writeFile(outputPath);
    }

    private void inistateVehicles(){
        this._vehicles=_scenario.getTransitVehicles();
        this._vehiclesFactory=this._vehicles.getFactory();
    }
    //endregion

    //region 创建vehicleSchedule
    

    //endregion
}
