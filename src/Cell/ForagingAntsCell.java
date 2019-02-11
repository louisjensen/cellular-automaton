package Cell;

import javafx.scene.shape.Polygon;

import java.awt.*;
import java.util.ArrayList;

public class ForagingAntsCell extends Cell {

    ArrayList<Ants> myAntsList;
    double myHomePheromone;
    double myFoodPheromone;
    final static int howmuchpheromone = 2;
    final static double evaporationrate = 0.05;


    public ForagingAntsCell(Polygon shape){

        super(shape);
        myAntsList = new ArrayList<Ants>();
    }

    public void increaseHomePheromone(){
        int HomeAnts=0;
        for(int a=0; a<myAntsList.size(); a++){
            if(!myAntsList.get(a).gethasfoodstate()){
                HomeAnts +=1;
            }
        }
        myHomePheromone += howmuchpheromone * HomeAnts;

    }

    public void increaseeFoodPheromone(){
        int FoodAnts=0;
        for(int a=0; a<myAntsList.size(); a++){
            if(myAntsList.get(a).gethasfoodstate()){
                FoodAnts +=1;
            }
        }
        myFoodPheromone += howmuchpheromone * FoodAnts;
    }

    public void evaporate(){
        myHomePheromone = myHomePheromone * (1 - evaporationrate);
        myFoodPheromone = myFoodPheromone * (1-evaporationrate);
    }

    public ArrayList<Ants> getMyAntsList() {
        return myAntsList;
    }

    public double getHomePheromone(){
        return myHomePheromone;
    }
    public void setHomePheromone(double p){
        myHomePheromone = p;
    }

    public double getFoodPheromone(){
        return myFoodPheromone;
    }
    public void setFoodPheromone(double p){
        myFoodPheromone = p;
    }



    public void addAnt(Ants myAnt){
        myAntsList.add(myAnt);
    }
    public  void removeAnt(Ants myAnt){
        myAntsList.remove(myAnt);
    }

}
