package matsim.basic.publicTrans;

import matsim.IO.XMLManager;
import org.jdom2.Element;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.vehicles.*;

import java.text.DecimalFormat;

public class CompositePublicTrans {
    private Scenario _scenario;
    private Vehicles _vehicles;
    private VehicleTypeToXML[] _vehicleTypeToXML;
    private int[] _counts;


    public static void main(String[] args) {
        VEHICLETYPE[] vehicletypes=new VEHICLETYPE[]{VEHICLETYPE.BUS,VEHICLETYPE.SUBWAY};
        var debug=new CompositePublicTrans(vehicletypes, new int[]{20,20});
        String outputPath="E:\\114_temp\\008_代码集\\005_java\\matsim_preparation\\src\\main\\resources\\vehicles_mawan.xml";

        debug.transitVehiclesWriter(debug._vehicles,outputPath);
    }

    public CompositePublicTrans(VEHICLETYPE[] vehicletypes, int[] counts){
        this._scenario= ScenarioUtils.createScenario(ConfigUtils.createConfig());
        VehicleManager vehicleManager=new VehicleManager(vehicletypes, counts);
        this._vehicleTypeToXML=vehicleManager.VehicleTypes;
        this._counts=counts;

        this._vehicles=createVehicles(_scenario,_vehicleTypeToXML[0],counts[0]);
    }

    public Vehicles createVehicles(Scenario scenario, VehicleTypeToXML vehicleTypeToXML, int nOfVehicles){
        Vehicles vehicles=scenario.getTransitVehicles();
        VehiclesFactory vb=vehicles.getFactory();
        VehicleType vehicleType=vb.createVehicleType(Id.create(vehicleTypeToXML.id,VehicleType.class));
        vehicles.addVehicleType(vehicleType);

        VehicleCapacity capacity=vehicleType.getCapacity();
        capacity.setSeats(vehicleTypeToXML.seats);
        capacity.setStandingRoom(vehicleTypeToXML.standingRoom);
        vehicleType.setLength(vehicleTypeToXML.length);
        vehicleType.setWidth(vehicleTypeToXML.width);
        vehicleType.setMaximumVelocity(vehicleTypeToXML.maxVelocity);
        vehicleType.setNetworkMode(vehicleTypeToXML.networkMode);

        DecimalFormat df=new DecimalFormat("000");

        for (int i = 0; i < nOfVehicles; i++) {
            String tempId=df.format(i);
            var vehicleId=String.format("%s_%s",tempId,vehicleTypeToXML.vType);
            vehicles.addVehicle(vb.createVehicle(Id.createVehicleId(vehicleId),vehicleType));
        }
        return vehicles;
    }

    public void transitVehiclesWriter(Vehicles vehicles, String outputPath){
        var output=new MatsimVehicleWriter(vehicles);
        output.writeFile(outputPath);
    }
}
