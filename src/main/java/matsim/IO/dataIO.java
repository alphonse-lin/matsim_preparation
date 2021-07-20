package matsim.IO;

import org.geotools.feature.FeatureCollection;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;

import org.locationtech.jts.geom.Geometry;

import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;


public class dataIO {

    /**
     * @description Read geojson file
     */
    public static GeoJSONData Read(String filePath, String[] attrList) throws IOException {
        var result=Parse(filePath);
        GeoJSONData data=new GeoJSONData(result, attrList);
        return data;
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

