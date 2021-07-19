package matsim.IO;

import org.geotools.feature.FeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.io.*;
import org.geotools.geojson.*;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;


public class dataIO {
    /**
     * @description Read geojson file
     */
    public static Coordinate[][] Read(String filePath) throws IOException {
        var result=Parse(filePath);
        var featureIterator=result.features();

        for (int i = 0; i < result.size(); i++) {
            SimpleFeature feature=(SimpleFeature) featureIterator.next();

            var data=feature.getAttribute("totalArea");
            System.out.println(String.format("%s:%s", i+"", data+""));
        }
    }

    /**
     * @description Write geojson file
    */
    public static void Write(Geometry geometry){
    }

    private static FeatureCollection<SimpleFeatureType, SimpleFeature> Parse(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
        return jsonToFeatureCollection(content);
    }

    private static final FeatureCollection<SimpleFeatureType, SimpleFeature> jsonToFeatureCollection(String jsonContent) throws IOException{
        GeometryJSON geometryJson = new GeometryJSON(15);
        FeatureJSON featureJson = new FeatureJSON(geometryJson);
        Reader stringReader = null;
        @SuppressWarnings("rawtypes")
        FeatureCollection featureCollection = null;

        try{
            stringReader = new StringReader(jsonContent);
            featureCollection = featureJson.readFeatureCollection(stringReader);
        }finally{
            stringReader.close();
        }
        return featureCollection;
    }

}
