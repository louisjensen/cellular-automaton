CompSci 308: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci308/current/assign/02_simulation/):

Design Review
=======

### High Level Design

Our code has four main components(Visualization, Configuration, Simulation, Grid). 
* Visualization handles everything related to GUI but does not hold any substantial data. Visualization only stores data 
about edgetype, shapetype, number of simulations in strings so they can used to make actual grid. 

* Configuration handles everything about reading in the xml files. After reading in the file, the data will be stored in a map, 
and this will be passed on to the grid class to make grid accordingly. 

* Grid class is where all important data is stored. Grid class will first read in necessary info to initialize grids from the Visualization and Configuration, 
and initialize two grids(current and next). Then everytime update is called, Grid class will ask the simulation class how to update
each cells based on the simulation. Then it will update the cells and send that data to the visualization class. 

* Simulation doesn't hold data but has all the rules how grids should be updated. Thus we have a abstract simulation class and 
each time we implement a new game, we just have to make new class extending simulation class. 

### Adding New Features
To add a new kind of simulation, we have to modify xml parser class and add a new simulation and cell class. Because we store info of xml
parser in a map, if the new simulation doesn't have more variables than what we currently have, xml parser doesn't have to be modified. 
New simulation class will have algorithms how to update the grid. This can be done through overriding getNextCell and Update method

### Major Design Choices
The biggest design decisions we made were how to implement 
the grid of cells and how to implement each simulation.
For the grid of cells we decided to use a 2D array to keep
track of each cell. We made that choice so that we would
be able to loop through the 2D array to update the states of each cell.
 We also considered keeping track of the grid using a map
 but we concluded that it would be easier for us to monitor
 neighbors of each cell within a 2D array. 
 
 The other major design choice we made was to implement an
 abstract simulation class and extend it for each simulation
 that we we're going to run in our code. That way we could use
 methods that would all be the same for each simulation but override
 the methods that would be different, such as how to update the grid.
 
 ### Assumptions
 In our code we assume that any XML file with one of the seven valid
 simulation names will contain valid information about the
 states and parameters will be correct. 