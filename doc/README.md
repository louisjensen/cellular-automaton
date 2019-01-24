# cellsociety 

Put any written documents related to your project here, including lab discussions.

1. How does a Cell know what rules to apply for its simulation?
    
We will load the cell rules in a configuration file.

2. How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update?

We can keep track of every state each block would be in after its neighboring cells update it. Then we can pass this to a control that will pick the "best" state to be in.

3. What is the grid? Does it have any behaviors? Who needs to know about it?

The grid is shown visually and is a 2D array of cell objects. The simulation should update the grid. 

4. What information about a simulation needs to be the configuration file?

Size of grid, which simulation, types of cells, initial configuration.

5. How is the GUI updated after all the cells have been updated?

The grid tells the GUI its current state and the GUI will display that.



# RPS Exercise

Completed by Justin Kim, Louis Jensen, Louis Lee 

## Design Description


#### Game 
```java
    private Player p1;
    private Player p2;
    private int myMaxScore
    GameControl gc = new GameControl(p1, p2);

    
    main(){
        play(myMaxScore);
    )
    public void play(int maxScore){
        while (p1.getScore() < maxScore && p2.getScore() < maxScore){
            gc.rungame();
            gc.updateGrid("random string");
        }
        print(the winner);
        end();
    }
```

#### GameControl 
```java
    private Player p1;
    private Player p2;
    String[][] grid; //keeps track of what beats what

    public GameControl(Player p1, Player p2){
        this.p1 = p1;    
        this.p2 = p2;
    } 

    public runRound(){
        p1.prompt();
        p2.prompt();
        whoWins().increaseScore(); 
    }
    
    public void updateGrid(String weapon){
        //update the grid
    }
    private Player whoWins(){
        // get p1 choice and p2 choice and check who wins with the grid
    }
   
```

####
```java
    private String myChoice;
    private int myScore;

    public void prompt(){
        //get user input and set it to myChoice
    }
    
    public void increaseScore(){
        score++;
    }

    public String getChoice(){
        return myChoice;
    }
```

#### OR All classes
![This is cool, too bad you can't see it](crc-example.png "Our paper design")


## User Stories

#### New Game
```java
    Class1 manager;
    Class2 important;
    if (manager.doSomethingCool(important)) {
        important.coolStuff("this is boring");
    }
    else {
        important.updateStuff();
    }
```

#### Choose Weapon
    Done with the GameControl class. Prompt each player.

#### Win the round
    Use the lookup grid to determine which player wins

#### New Choice
    Update the grid.

#### Game Added
    Maintained in the while loop in the play(int maxScore) function.
