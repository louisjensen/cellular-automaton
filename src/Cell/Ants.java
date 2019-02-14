package Cell;

import java.awt.*;

public class Ants {

    /**
     * @author:  Louis Lee, Justin Kim
     */
    private boolean hasFood;
    private Point myDirection;
    private int myLives;

    public Ants(Point point, int startingLives){
        myDirection = point;
        myLives = startingLives;
        hasFood = false;
    }

    /**
     * Returns a boolean based on the state of the ant
     * @return true if the ant is looking for food
     */
    public boolean getHasFoodState(){
        return hasFood;
    }

    /**
     * changes the state of the ant from going home to looking for food
     */
    public void dropFood(){
        hasFood = false;
    }

    /**
     * changes the state of the ant from looking for food to going home
     */
    public void pickupFood(){
        hasFood = true;
    }

    /**
     * get the facing direction of the ant
     * @return Point that contains row and col components of direction
     */
    public Point getDirection(){
        return myDirection;
    }

    /**
     * decrement the life of the ant by one
     */
    public void loseLife(){
        myLives --;
    }

    /**
     * check if ant is dead
     * @return true if ant has 0 lives
     */
    public boolean isDead(){
        return (myLives == 0);
    }

    /**
     * change the direction of the ant
     * @param point
     */
    public void setDirection(Point point){
        myDirection = point;
    }
}
