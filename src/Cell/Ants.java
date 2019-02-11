package Cell;

import javafx.geometry.Pos;

import java.awt.*;

public class Ants {

    private boolean hasFood;
    private Point myDirection;
    private int myLives;



    public Ants(Point point, int startingLives){
        myDirection = point;
        myLives = startingLives;
        hasFood = false;
    }

    public Ants(Ants ant){
        myDirection = ant.myDirection;
        myLives = ant.myLives;
        hasFood = ant.hasFood;
    }



    public boolean getHasFoodState(){
        return hasFood;
    }

    public void dropFood(){
        hasFood = false;
    }

    public void pickupFood(){
        hasFood = true;
    }

    public Point getDirection(){
        return myDirection;
    }

    public void loseLife(){
        myLives --;
    }

    public boolean isDead(){
        return (myLives == 0);
    }

    public void setDirection(Point point){
        myDirection = point;
    }
}
