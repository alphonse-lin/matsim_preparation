package matsim.IO;

import org.geotools.feature.FeatureCollection;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.util.*;

public class GeoJSONData {
    private FeatureCollection<SimpleFeatureType, SimpleFeature> _inputFeature;
    private String[] _attrList;

    public Map<String, String[]> attrDataDic;
    public Geometry[] geometries;

    GeoJSONData(FeatureCollection<SimpleFeatureType, SimpleFeature> result, String[] attrList) {
        _inputFeature=result;
        _attrList=attrList;
        parse();
    }

    GeoJSONData(FeatureCollection<SimpleFeatureType, SimpleFeature> result) {
        _inputFeature=result;
        _attrList=new String[0];
        parse();
    }

    private void parse(){
        var featureIterator=_inputFeature.features();
        var count=_inputFeature.size();

        attrDataDic=new HashMap<>();
        geometries=new Geometry[count];
        for (int i = 0; i < count; i++) {
            SimpleFeature feature=(SimpleFeature) featureIterator.next();
            //get attribute data
            if (_attrList.length!=0){
                for (int j = 0; j < _attrList.length; j++) {
                    var tempAttr=_attrList[j];
                    var singleAttrData=feature.getAttribute(tempAttr)+"";

                    if (attrDataDic.containsKey(tempAttr)){
                        attrDataDic.get(tempAttr)[i]=singleAttrData;
                    }else{
                        attrDataDic.put(tempAttr, new String[count]);
                        attrDataDic.get(tempAttr)[0]=singleAttrData;
                    }
                }
            }
            //get geometry
            geometries[i]=(Geometry) feature.getDefaultGeometry();
        }
    }
}
