package com.pearl.graphics;

import com.pearl.records.Tile;
import com.raylib.Jaylib;
import com.raylib.Raylib;

public class RaycastingVisibility extends Visibility {

    public RaycastingVisibility(Tile[][] map) {
        super(map);
    }

    private void traceLine(Raylib.Vector2 origin, int x1, int y1, boolean[][] vis) {
        float rate = 1.f / 100.f;
        float dx = (x1 - origin.x()) * rate;
        float dy = (y1 - origin.y()) * rate;
        float x = origin.x();
        float y = origin.y();
        while( Math.abs((float)x1 - x) > 0.1f && Math.abs((float) y1 - y) > 0.1f ) {
            setVisible(vis, (int)x, (int)y);
            if(blocksVision((int) x, (int) y)) {
                break;
            }
            x += dx;
            y += dy;
        }
    }

    @Override
    public boolean[][] compute(Raylib.Vector2 origin, int rangeLimit) {
        boolean[][] ret = getBlankVisibilityMap();

        setVisible(ret, (int) origin.x(), (int) origin.y());
        Jaylib.Rectangle area = new Jaylib.Rectangle(Math.max(origin.x() - rangeLimit, 0),
                Math.max(origin.y() - rangeLimit, 0),
                Math.min(rangeLimit * 2 + 1, map[0].length - 1),
                Math.min(rangeLimit * 2 + 1, map.length - 1));

        for (int x = (int) area.x(); x < area.x() + area.width(); ++x) // cast rays towards the top and bottom of the area
        {
            traceLine(origin, x, (int) area.y(), ret);
            traceLine(origin, x, (int) (area.y() + area.height()), ret);
        }
        for (int y = (int) area.y() + 1; y < area.y() + area.height() - 1; y++) // and to the left and right
        {
            traceLine(origin, (int) area.x(), y, ret);
            traceLine(origin, (int) (area.x() + area.width()), y, ret);
        }
        return ret;
    }
}
