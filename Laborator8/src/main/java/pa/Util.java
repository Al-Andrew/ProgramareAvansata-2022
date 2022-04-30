package pa;

import java.awt.*;
import java.awt.geom.Point2D;

public class Util {
    static double computeDistanceBetween(City c1, City c2) {
        double rad = 6371; // Earth rad in km
        double lat1rad = Math.toRadians(c1.getLatitude());
        double long1rad = Math.toRadians(c1.getLongitude());
        double lat2rad = Math.toRadians(c2.getLatitude());
        double long2rad = Math.toRadians(c2.getLongitude());

        double dLat = lat1rad - lat2rad;
        double dLong = long1rad - long2rad;

        double a = Math.pow(Math.sin(dLat / 2.0), 2) + Math.cos(lat1rad) * Math.cos(lat2rad) * Math.pow(Math.sin(dLong / 2.0), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return c * rad;
    }

    static Point2D SphericalMercatorProject(double latitude, double longitude) {
        final double RADIUS_MAJOR = 6378137.0;
        final double RADIUS_MINOR = 6356752.3142;
        double x = Math.toRadians(longitude) * RADIUS_MAJOR;
        double y = Math.log(Math.tan(Math.PI / 4 + Math.toRadians(latitude) / 2)) * RADIUS_MAJOR;

        return new Point2D.Double(x,y);
    }


    static Point2D SphericalMercatorProject(City city) {
        return SphericalMercatorProject(city.getLatitude(), city.getLongitude());
    }

    static Point boundingBoxScale(Point2D input, int width, int height) {
        //(left, bottom, right, top)
        //(-20037508.34, -23810769.32, 20037508.34, 23810769.32).
        double xuv = (input.getX() + 20037508.34) / (20037508.34 + 23810769.32);
        double yuv = 1 - ((input.getY() + 23810769.32) / (20037508.34 + 23810769.32));

        return new Point((int)(xuv * width), (int)(yuv * height) );
    }
}
