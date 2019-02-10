package Cell;

import javafx.geometry.Pos;

import java.awt.*;

public class Ants {

    boolean hasfood;
    int X_pos;
    int Y_pos;
    Point myDirection;


    public Ants(Point point){
        X_pos = point.x;
        Y_pos = point.y;
    }

    public boolean gethasfoodstate(){
        return hasfood;
    }

    public boolean dropfood(){
        hasfood = false;
        return hasfood;
    }

    public boolean pickupfood(){
        hasfood = true;
        return hasfood;
    }

    public Point getDirection(){
        return myDirection;
    }


}
