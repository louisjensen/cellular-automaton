# Team Disscussion - Inheritance Review
## (lbj7, lot, dwl23)

### Part 1
1. We are hiding the visualization of the GUI from other
parts of the program. If we write it correctly we shouldn't 
have to change it for any of the different simulations.
2. The simulation will use inheritance and will be an 
abstract class, and each simulation that we run will extend 
that class.
3. The grid class and simulation classes will be open
because they need to access each other to run the simulation.
4. An error that could occur would be if the user tried to
load a file that was not a valid configuration file. 
To prevent this we will have the program throw an error 
if the file chosen is not one of the valid files.
5. We think that our design is good because it should be
fairly simple to add other simulations with different rules.


### Part 2
1. Each area is dependent on each other. Right now I am working
on the configuration part and the simulation needs that so 
that it can begin the simulation and the visualization needs 
it so that it can display the simulation. The configuration part
is the part that depends least on other parts of the project.
2. The behavior of the simulation will depend on the 
configuration because it needs to know which simulation to
run and which rules to follow.
3. This is a necessary dependency but we have limited the dependency
on simulation by making the visualization class closed.
4. We haven't completely written the super/sub classes for
the simulation yet but an idea we got through the discussion
is monitor the neighbors of each cell through a list, rather
than a 2D array, to improve flexibility.

### Part 3
1. Use Cases
    * Create one configuration file
    * Load one configuration file
    * Parse through configuration file
    * Use data from configuration file to create grid
    * Choose which configuration file to load of several
2. I am most excited to implement the back end and create
the rules for the game and decide how to update each cell.
3. I am most worried about creating the program while
simultaneously allowing it to be flexible for the second 
half of the project.