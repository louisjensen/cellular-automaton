package Cell;

import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;

public class ForagingAntsCell extends Cell {

    /**
     * @author Louis Lee, Justin Kim
     */

    private List<Ants> myAntsList;
    private double myHomePheromone;
    private double myFoodPheromone;

    final static int howmuchpheromone = 2;
    final static double evaporationrate = 0.03;

    /**
     * Sets shape and initializes an ArrayList to hold the ants
     * @param shape
     */
    public ForagingAntsCell(Polygon shape){
        super(shape);
        myAntsList = new ArrayList<Ants>();
        myHomePheromone = 0;
        myFoodPheromone = 0;
    }

    /**
     * increases myHomePheromone based on how many ants are looking for home in the cell
     */
    public void increaseHomePheromone(){
        int HomeAnts=0;
        for(int a=0; a<myAntsList.size(); a++){
            if(!myAntsList.get(a).getHasFoodState()){
                HomeAnts +=1;
            }
        }
        myHomePheromone += howmuchpheromone * HomeAnts;

    }

    /**
     * increases myFoodPheromone based on how many ants are looking for food in the cell
     */
    public void increaseFoodPheromone(){
        int FoodAnts=0;
        for(int a=0; a<myAntsList.size(); a++){
            if(myAntsList.get(a).getHasFoodState()){
                FoodAnts +=1;
            }
        }
        myFoodPheromone += howmuchpheromone * FoodAnts;
    }

    /**
     * decrements myHomePheromone and myFoodPheromone
     */
    public void evaporate(){
        myHomePheromone = myHomePheromone * (1-evaporationrate);
        myFoodPheromone = myFoodPheromone * (1-evaporationrate);
    }

    /**
     * return list of ants in the cell
     * @return
     */
    public List<Ants> getMyAntsList() {
        return myAntsList;
    }

    /**
     * gets the amount of home pheromone in the cell
     * @return myHomePheromone
     */
    public double getHomePheromone(){
        return myHomePheromone;
    }

    /**
     * sets the amount of home pheromone in the cell
     * @param p
     */
    public void setHomePheromone(double p){
        myHomePheromone = p;
    }

    /**
     * gets the amount of food pheromone in the cell
     * @return myFoodPheromone
     */
    public double getFoodPheromone(){
        return myFoodPheromone;
    }

    /**
     * sets the amount of food pheromone in the cell
     * @param p
     */
    public void setFoodPheromone(double p){
        myFoodPheromone = p;
    }

    /**
     * add an additional ant object to the list of ants in the cell
     * @param myAnt
     */
    public void addAnt(Ants myAnt){
        myAntsList.add(myAnt);
    }

    /**
     * remove an ant instance from the list of ants in the cell
     * @param myAnt
     */
    public  void removeAnt(Ants myAnt){
        myAntsList.remove(myAnt);
    }

}
