package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Cell{

    private int myRow;
    private int myCol;
    private int myState;
    private int mySizeX; // length/width (it is a square)
    private int mySizeY;
    private Rectangle myImage;
    private int myEnergy;

    public Cell(int row, int col, int sizeX, int sizeY, int state){
        myRow = row;
        myCol = col;
        mySizeX = sizeX;
        mySizeY = sizeY;
        myState = state;
        myImage = new Rectangle(mySizeX, mySizeY, Color.WHITE);
        myImage.setStroke(Color.BLACK);
    }

    
    public int getRow(){
        return myRow;
    }
    public int getCol(){
        return myCol;
    }
    public int getState(){
        return myState;
    }

    public Rectangle getImage(){
        return myImage;
    }

    public int getSizeX(){
        return mySizeX;
    }
    public int getSizeY(){
        return mySizeY;
    }


    //add this line to the cell class
    public void ChangeState(){

    }

    public void setColor(Paint color){
        myImage.setFill(color);
    }

    public void setState(int state){
        myState = state;
    }

    public void decreaseEnergy(){
        myEnergy -= 2;
    }

    public void setEnergy(int energy){
        myEnergy = energy;
    }
}
