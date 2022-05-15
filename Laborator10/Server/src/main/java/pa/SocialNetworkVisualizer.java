package pa;

import org.jfree.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SocialNetworkVisualizer {

    static public void exportSVG(SocialNetwork network, String file) throws IOException {
        SVGGraphics2D g2 = new SVGGraphics2D(600, 600);

        Set<String> userNames = network.getUsers();
        Map<String, Point2D> nodes = new HashMap<>();

        float angularDistance = (float) (2*Math.PI / userNames.size());
        float currentAngle = 0f;
        float arrangementRadius = 200;
        float nodeRadius = 20;

        g2.translate(300, 300);

        for(var user : userNames) {
            Point2D constructed;
            constructed = new Point2D.Float();
            float x = (float) (Math.cos(currentAngle)*arrangementRadius);
            float y = (float) (Math.sin(currentAngle)*arrangementRadius);
            constructed.setLocation(x, y);

            nodes.put(user, constructed);
            currentAngle += angularDistance;
        }
        g2.translate(nodeRadius/2, nodeRadius/2);
        for(var frnd : network.getFriendShips()) {
            var from = nodes.get(frnd.friend1);
            var to = nodes.get(frnd.friend2);
            g2.setColor(Color.BLUE);
            g2.drawLine((int) from.getX(), (int) from.getY(), (int) to.getX(), (int) to.getY());
        }
        g2.translate(-nodeRadius/2, -nodeRadius/2);
        for(var entry : nodes.entrySet()) {
            int x = (int) entry.getValue().getX();
            int y = (int) entry.getValue().getY();
            g2.setColor(Color.RED);
            g2.fillOval(x, y, (int) nodeRadius, (int) nodeRadius);
            g2.setColor(Color.BLACK);
            g2.drawString(entry.getKey(), x, y);
        }

        Writer writer = new FileWriter(file);
        String doc = g2.getSVGDocument();
        writer.write(doc);
        writer.flush();
        g2.dispose();
    }
}
