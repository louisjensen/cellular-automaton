package Cell;

import javafx.scene.shape.Polygon;

import java.awt.*;
import java.util.ArrayList;

public class ForagingAntsCell extends Cell {

    ArrayList<Ants> myAntsList;
    double HomePheromone;
    double FoodPheromone;
    final static int howmuchpheromone =2;
    final static double evaporationrate = 0.05;
    Point myLocation;


    public ForagingAntsCell(Polygon shape){
        super(shape);
    }

    public void increaseHomePheromone(){
        int HomeAnts=0;
        for(int a=0; a<myAntsList.size(); a++){
            if(!myAntsList.get(a).gethasfoodstate()){
                HomeAnts +=1;
            }
        }
        HomePheromone += howmuchpheromone * HomeAnts;

    }

    public void increaseeFoodPheromone(){
        int FoodAnts=0;
        for(int a=0; a<myAntsList.size(); a++){
            if(myAntsList.get(a).gethasfoodstate()){
                FoodAnts +=1;
            }
        }
        FoodPheromone += howmuchpheromone * FoodAnts;
    }

    public void evaporate(){
        HomePheromone = HomePheromone * (1-evaporationrate);
        FoodPheromone = FoodPheromone * (1-evaporationrate);
    }

    public Integer getNumberofAnts(){
        return myAntsList.size();
    }

    public ArrayList<Ants> getMyAntsList() {
        return myAntsList;
    }

    public double getHomePheromone(){
        return HomePheromone;
    }

    public double getFoodPheromone(){
        return FoodPheromone;
    }

    public void addAnt(Ants myAnt){
        myAntsList.add(myAnt);
    }
    public  void removeAnt(Ants myAnt){
        myAntsList.remove(myAnt);
    }

    public Point getMyLocation(){
        return myLocation;
    }

}
