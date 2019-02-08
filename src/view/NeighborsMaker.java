
package view;

import java.awt.*;
import java.util.ArrayList;

public class NeighborsMaker {

    private String myGridType;
    private String mySimulation;

    final ArrayList<Point> rectangleTouch = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
        add(new Point( 1, 1));
        add(new Point( 1,-1));
        add(new Point(-1, 1));
        add(new Point(-1,-1));
    }};

    final ArrayList<Point> rectangleSide = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0,-1));
        add(new Point( 1, 0));
        add(new Point(-1, 0));
    }};

    final ArrayList<Point> triangleTouchPointy = new ArrayList<Point>(){{
        add(new Point( 1, 0));
        add(new Point( 2, 0));
        add(new Point( 2, 1));
        add(new Point( 1, 1));
        add(new Point( 0, 1));
        add(new Point( -1, 1));
        add(new Point( -2, 1));
        add(new Point( -1, 1));
        add(new Point( -2, 0));
        add(new Point( -1, 0));
        add(new Point( -1, -1));
        add(new Point( 0, -1));
        add(new Point( 1, -1));
    }};

    final ArrayList<Point> triangleTouchFlat = new ArrayList<Point>(){{
        add(new Point( -1, 0));
        add(new Point( -1, 1));
        add(new Point( -1, 2));
        add(new Point( 0, 1));
        add(new Point( 0, 2));
        add(new Point( 1, 1));
        add(new Point( 1, 0));
        add(new Point( 1, -1));
        add(new Point( 0, -1));
        add(new Point( 0, -2));
        add(new Point( -1, -2));
        add(new Point( -1, -1));
    }};

    final ArrayList<Point> triangleSideFlat = new ArrayList<Point>(){{
        add(new Point( 0, 1));
        add(new Point( 0, -1));
        add(new Point( -1, 0));
    }};
    



    public NeighborsMaker(){
    }

    final ArrayList<Point> hexagon = new ArrayList<Point>(){{
        add(new Point(-1, -1));
        add(new Point(-1, 0));
        add(new Point(-1, 1));
        add(new Point(0, -1));
        add(new Point(1, 0));
        add(new Point(0,1));
    }};

    final ArrayList<Point> triangleSidePointy = new ArrayList<Point>(){{
        add(new Point(0, -1));
        add(new Point(0, 1));
        add(new Point(1, 0));
    }};
}
