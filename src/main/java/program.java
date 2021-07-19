import matsim.IO.dataIO;
import matsim.basic.population;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class program {
    public static void main(String[] args) throws IOException {
        var filePath="src/main/resources/building.geojson";
        var result=dataIO.Read(filePath);
    }
}
