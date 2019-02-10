package Cell;

import javafx.geometry.Pos;

import java.awt.*;

public class Ants {

    boolean hasfood;
    Point myDirection;


    public Ants(Point point){
        myDirection = point;
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
