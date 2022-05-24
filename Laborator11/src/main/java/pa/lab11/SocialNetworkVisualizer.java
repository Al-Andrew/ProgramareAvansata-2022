package pa.lab11;

import org.jfree.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SocialNetworkVisualizer {

    static String exportSVG(){
        SVGGraphics2D g2 = new SVGGraphics2D(1000, 1000);


        Set<String> userNames = SocialNetwork.the().getAllUsers().stream().map((Person::getName)).collect(Collectors.toSet());
        Map<String, Integer> radii = SocialNetwork.the().getAllUsers().stream()
                .collect(Collectors.toMap(Person::getName,
                        (Person p) -> SocialNetwork.the().getFriendshipsForUser(p.id).size()));
        Integer maxRad = radii.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get().
                getValue();
        java.util.List<Friendship> friendshipList = SocialNetwork.the().getAllFriendships()
                .stream().filter((Friendship fr) -> fr.friend1 != null && fr.friend2 != null).collect(Collectors.toList());
        Map<String, Point2D> nodes = new HashMap<>();

        float angularDistance = (float) (2*Math.PI / userNames.size());
        float currentAngle = 0f;
        float arrangementRadius = 400;
        float nodeRadius = 35;

        g2.translate(450, 450);

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
        for(var frnd : friendshipList) {
            var from = nodes.get(frnd.friend1.name);
            var to = nodes.get(frnd.friend2.name);
            g2.setColor(Color.BLUE);
            g2.drawLine((int) from.getX(), (int) from.getY(), (int) to.getX(), (int) to.getY());
        }
        g2.translate(-nodeRadius/2, -nodeRadius/2);
        for(var entry : nodes.entrySet()) {
            int x = (int) entry.getValue().getX();
            int y = (int) entry.getValue().getY();

            Color clr = Color.getHSBColor( ((float) radii.get(entry.getKey()) / (float) maxRad), 1.0f, 1.0f);
            g2.setColor(clr);
            g2.fillOval(x, y, (int) nodeRadius, (int) nodeRadius);
            g2.setColor(Color.BLACK);
            g2.drawString(entry.getKey(), x * 1.1f, y * 1.1f + 20);
        }

        String doc = g2.getSVGDocument();
        g2.dispose();
        return doc;
    }
}
