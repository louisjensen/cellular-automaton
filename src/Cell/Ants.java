package Cell;

import javafx.geometry.Pos;

import java.awt.*;

public class Ants {

    boolean hasfood;
    Integer direction;
    int X_pos;
    int Y_pos;
    Point myLocation;


    public Ants(Point point){
        myLocation = point;
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

    public Point getPoint(){
        return myLocation;
    }



}
