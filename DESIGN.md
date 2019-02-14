CompSci 308: Simulation Project Analysis
===================

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci308/current/assign/02_simulation/):

Design Review
=======

### Overall Design

1. Our code has four main components(Visualization, Configuration, Simulation, Grid). 
* Visualization handles everything related to GUI but does not hold any substantial data. Visualization only stores data 
about edgetype, shapetype, number of simulations in strings so they can used to make actual grid. 

* Configuration handles everything about reading in the xml files. After reading in the file, the data will be stored in a map, 
and this will be passed on to the grid class to make grid accordingly. 

* Grid class is where all important data is stored. Grid class will first read in necessary info to initialize grids from the Visualization and Configuration, 
and initialize two grids(current and next). Then everytime update is called, Grid class will ask the simulation class how to update
each cells based on the simulation. Then it will update the cells and send that data to the visualization class. 

* Simulation doesn't hold data but has all the rules how grids should be updated. Thus we have a abstract simulation class and 
each time we implement a new game, we just have to make new class extending simulation class. 

2. To add a new kind of simulation, we have to modify xml parser class and add a new simulation class. Because we store info of xml
parser in a map, if the new simulation doesn't have more variables than what we currently have, xml parser doesn't have to be modified. 
New simulation class will have algorithms how to update the grid. This can be done through overriding getNextCell and Update method

3. Code is generally consistent. This is mainly because we always worked in a group, so we could discuss and review our code everyday. Moreover, 
because Justin and I both worked on simulation, it was necessary for us to maintain same logic. 
As for the variable names, when we were planning the project, we wrote down all the variables as much as possible so we would be using 
same naming conventions. I think this went very well. 