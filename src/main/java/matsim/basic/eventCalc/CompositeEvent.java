package matsim.basic.eventCalc;

import matsim.IO.CSVManager;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.network.NetworkChangeEvent;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.NetworkChangeEventsWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompositeEvent {
    private Network _network;
    private ArrayList<SingleEvent> _events;

    public static void main(String[] args) throws IOException {
//        String outputDir="src/main/resources/debug"+ File.separator;
//        String networkChangeEventXML=outputDir+"temp_network.xml";
//        ChangeEventWithSmallValue(networkChangeEventXML);

//        String outputDir="src/main/resources/output_ucl/001/r_-1/1000"+ File.separator;
//        String networkXML=outputDir+"network_output.xml";
//        var result=ReadNetworkFile(networkXML);

//        String outputDir="src/main/resources/debug"+ File.separator;
//        String networkXML=outputDir+"networkChange.csv";
//        var result=ReadEventCSV(networkXML);
//        System.out.println("finished");


        String networkPath="src/main/resources/debug/tq38_london_strategy/static_waittodry/network.xml";
        String csvPath="src/main/resources/debug/tq38_london_strategy/static_waittodry/sequenced_networkChange.csv";
        String outputPath="src/main/resources/debug/tq38_london_strategy/static_waittodry/networkChangeEvents.xml";
        CompositeEvent compositeEvent=new CompositeEvent(networkPath,csvPath,outputPath);

//        String path = "src/main/resources/debug/tq38_london_strategy/dynamic_12_00_pm/eventCSV";
//        String outputDir="src/main/resources/debug/tq38_london_strategy/dynamic_12_00_pm/eventXML";
//        String networkPath="src/main/resources/debug/tq38_london_strategy/dynamic_12_00_pm/network.xml";
//        File file = new File(path);
//        File[] fs = file.listFiles();
//        for(File f:fs){
//            String csvPath=f.getAbsolutePath();
//            String outputPath=outputDir+File.separator+f.getName().split("\\.")[0]+".xml";
//            CompositeEvent compositeEvent=new CompositeEvent(networkPath,csvPath,outputPath);
////            String csvPath="src/main/resources/debug/tq38_london_strategy/dynamic/sequenced_networkChange.csv";
////            String outputPath="src/main/resources/debug/tq38_london_strategy/dynamic/networkChangeEvents.xml";
////            CompositeEvent compositeEvent=new CompositeEvent(networkPath,csvPath,outputPath);
//        }

    }

    public CompositeEvent(String networkFilePath, String csvFilePath, String outputFilePath) throws IOException {
        this._network=ReadNetworkFile(networkFilePath);
        this._events=ReadEventCSV(csvFilePath);
        ChangeEventWithSmallValue(outputFilePath);
    }

    //读取network.xml
    public Network ReadNetworkFile(String filename){
        Network network = NetworkUtils.readNetwork(filename);
        return network;
    }

    //读取event.csv
    public ArrayList<SingleEvent> ReadEventCSV(String csvFilePath){
        var data=CSVManager.Read(csvFilePath);
        var events =new ArrayList<SingleEvent>();
        int count=data.size();
        for (int i = 1; i <count ; i++) {
            var singleEvent=data.get(i);
            //var singleFacility=temp_singleFacility.split(",");
            int linkId= Integer.parseInt((singleEvent[0]));
            double time= Double.parseDouble(singleEvent[1]);
            double freeSpeed= Double.parseDouble(singleEvent[2]);
            double flowCapacity= Double.parseDouble(singleEvent[3]);
            SingleEvent event=new SingleEvent(linkId,time,freeSpeed,flowCapacity);
            events.add(event);
        }
        return events;
    }

    public void ChangeEventWithSmallValue(String outputFilePath) {
        final Network network = this._network;
        List<NetworkChangeEvent> changeEvents = new ArrayList<>();

        int count=this._events.size();
        for (int i = 0; i < count; i++) {
            var tempEvent=this._events.get(i);
            final Link link = network.getLinks().get(Id.create(tempEvent.LinkID, Link.class));
            final NetworkChangeEvent event = new NetworkChangeEvent(tempEvent.StartTime);
//            event.setFreespeedChange(
//                    new NetworkChangeEvent.ChangeValue(NetworkChangeEvent.ChangeType.FACTOR, tempEvent.FreeSpeed));
            event.setFlowCapacityChange(
                    new NetworkChangeEvent.ChangeValue(NetworkChangeEvent.ChangeType.ABSOLUTE_IN_SI_UNITS, tempEvent.FlowCapaicty));
//                    new NetworkChangeEvent.ChangeValue(NetworkChangeEvent.ChangeType.FACTOR, tempEvent.FlowCapaicty));
            event.addLink(link);
            changeEvents.add(event);
        }

        new NetworkChangeEventsWriter().write(outputFilePath, changeEvents);
    }



}
