package pa.graphics;

import pa.entity.City;
import pa.util.MapUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImagePanel extends JPanel {
    private final HighlightData highlightData;
    private BufferedImage image;

    public ImagePanel(HighlightData highlightData) {
        this.highlightData = highlightData;
        image = new BufferedImage(900, 900, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        java.util.List<Border> borders = Border.readFromJSON("borders.json");

        for (Border border : borders) {
            for (var point : border.getPoints()) {
                Point2D projected = MapUtil.SphericalMercatorProject(point.getX(), point.getY());
                Point transformed = MapUtil.boundingBoxScale(projected, image.getWidth(), image.getHeight());
                g.drawOval(transformed.x, transformed.y, 1, 1);
            }
        }

        g.dispose();
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (image != null) {
            g2d.drawImage(image, 0, 0, this);
        }


        for(var cty : highlightData.getCityList()) {
            if(highlightData.isSelected(cty))
                g2d.setColor(Color.blue);
            else
                g2d.setColor(Color.red);

            Point2D projected = MapUtil.SphericalMercatorProject(cty);
            Point transformed = MapUtil.boundingBoxScale(projected, image.getWidth(), image.getHeight());
            g2d.fillOval(transformed.x - 5, transformed.y - 5, 10, 10);
        }

        //for(var pair : pairs) {
        //    Point2D coords1 = Util.SphericalMercatorProject(pair.from);
        //    Point2D coords2 = Util.SphericalMercatorProject(pair.to);
        //    Point point1 = Util.boundingBoxScale(coords1, image.getWidth(), image.getHeight());
        //    Point point2 = Util.boundingBoxScale(coords2, image.getWidth(), image.getHeight());
        //    g2d.fillOval(point1.x + mapOffX - 5, point1.y + mapOffY - 5, 10, 10);
        //    g2d.fillOval(point2.x + mapOffX - 5, point2.y + mapOffY - 5, 10, 10);
        //    g2d.drawLine(point1.x + mapOffX , point1.y + mapOffY, point2.x + mapOffX , point2.y + mapOffY );
        //    g2d.drawString(pair.from.getName(), point1.x + mapOffX, point1.y + mapOffY + 30);
        //    g2d.drawString(pair.to.getName(), point2.x + mapOffX, point2.y + mapOffY + 30);
        //}

        //Point pnt = Util.boundingBoxScale(Util.SphericalMercatorProject(0, 0), image.getWidth(), image.getHeight());
        //g2d.fillOval(pnt.x + mapOffX - 5, pnt.y + mapOffY - 5, 10, 10);

        g2d.dispose();
    }
}
