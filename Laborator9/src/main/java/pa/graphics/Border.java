package pa.graphics;

import javafx.geometry.Point2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Border {
    private List<javafx.geometry.Point2D> points;

    static public List<Border> readFromJSON(String path) {
        List<Border> res = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Object obj = null;
        try {
            obj = jsonParser.parse(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray countries = (JSONArray) obj;
        for (var country : countries) {
            Border constructed = new Border();
            constructed.points = parseCountryToPointList((JSONObject) country);
            res.add(constructed);
        }

        return res;
    }

    static private java.util.List<javafx.geometry.Point2D> parseSetToPoints(JSONArray coordinates) {
        List<javafx.geometry.Point2D> ret = new ArrayList<>();
        for (var point : (JSONArray) coordinates) {
            JSONArray pt = (JSONArray) point;
            double x,y;
            x = ((Number) pt.get(1)).doubleValue();
            y = ((Number) pt.get(0)).doubleValue();

            ret.add(new javafx.geometry.Point2D(x, y));
        }
        return ret;
    }

    static private java.util.List<javafx.geometry.Point2D> parseCountryToPointList(JSONObject employee) {
        List<javafx.geometry.Point2D> ret = new ArrayList<>();

        JSONObject fields = (JSONObject) employee.get("fields");
        JSONObject shape = (JSONObject) fields.get("geo_shape");
        String type = ((String) shape.get("type"));
        //System.out.println(fields.get("name"));

        if (type.equals("Polygon")) {
            JSONArray sets = (JSONArray) shape.get("coordinates");
            for (var set : (JSONArray) sets) {
                ret.addAll(parseSetToPoints((JSONArray) set));
            }
        } else if (type.equals("MultiPolygon")) {
            JSONArray sets = (JSONArray) ((JSONArray) shape.get("coordinates"));
            for (var set : sets) {
                for (var poly : (JSONArray) set) {
                    //System.out.println(poly);
                    ret.addAll(parseSetToPoints((JSONArray)poly));
                }
            }

        }
        return ret;
    }

    public List<Point2D> getPoints() {
        return points;
    }
}
