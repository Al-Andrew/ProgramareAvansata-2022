package pa;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImagePanel extends JPanel {
    class CityPair {
        City from;
        City to;

        public CityPair(City from, City to) {
            this.from = from;
            this.to = to;
        }
    }
    java.util.List<CityPair> pairs = new ArrayList<>();

    private BufferedImage image;

    public ImagePanel() {
        try {
            image = ImageIO.read(new File("./mapp.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addCityPair(City c1, City c2) {
        pairs.add(new CityPair(c1, c2));
    }

    public void clearPairs() {
        pairs.clear();
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
        g2d.setColor(new Color(255, 0 ,0));
        g2d.setStroke(new BasicStroke(5));
        g2d.setFont(new Font("Arial", getFont().getStyle(), 15));

        final int mapOffX = 35;
        final int mapOffY = 35;

        for(var pair : pairs) {
            Point2D coords1 = Util.SphericalMercatorProject(pair.from);
            Point2D coords2 = Util.SphericalMercatorProject(pair.to);
            Point point1 = Util.boundingBoxScale(coords1, image.getWidth(), image.getHeight());
            Point point2 = Util.boundingBoxScale(coords2, image.getWidth(), image.getHeight());
            g2d.fillOval(point1.x + mapOffX - 5, point1.y + mapOffY - 5, 10, 10);
            g2d.fillOval(point2.x + mapOffX - 5, point2.y + mapOffY - 5, 10, 10);
            g2d.drawLine(point1.x + mapOffX , point1.y + mapOffY, point2.x + mapOffX , point2.y + mapOffY );
            g2d.drawString(pair.from.getName(), point1.x + mapOffX, point1.y + mapOffY + 30);
            g2d.drawString(pair.to.getName(), point2.x + mapOffX, point2.y + mapOffY + 30);
        }

        Point pnt = Util.boundingBoxScale(Util.SphericalMercatorProject(0, 0), image.getWidth(), image.getHeight());
        g2d.fillOval(pnt.x + mapOffX - 5, pnt.y + mapOffY - 5, 10, 10);

        g2d.dispose();
    }
}
