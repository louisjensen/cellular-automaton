Cell society(updated on 02/11/2019 after second Sprint)
====
This project implements a cellular automata simulator.

Names: Justin Kim, Louis Jensen, Louis Lee

### Timeline

Start Date: 2019/01/29

Finish Date: 2019/02/04 (first sprint) 2019/02/11 (Second Sprint)

Hours Spent: 70+(individually)

### Primary Roles

We worked as a group for the whole project. 

### Resources Used

Stack Overflow, Google Images. Example codes from lab

### Running the Program

Main class: RunGame.class

Data files needed: All images are css files are in the Resources Folder and configuration files are in the data folder. 
Images used in this project are NOT licensed. 

Interesting data files: x

Features implemented: 

1. GUI has 9 buttons: File upload, Shape type, Boundary type, Number of simulations, Initialize, Step(one step), Play button, Speed Control Button, Pause Button
2. Games(Scenarios) implemented: GameOfLife, Percolation, PredatorPrey, Segregation, SpreadingOfFire, RPS, ForagingAnts 
3. Shapes: Triangle, Square(Rectangle), Hexagon 
4. Neighbors: Nneighbors by points and by sides. 
5. Boundaries: Regular, Toroidal
6. GUI special features: Pie charts representing the state of the grids. Can run multiple grids(1,2,4) at once. 


Assumptions or Simplifications: 

We are using Point object to represent relative place of Cells on the grid. Since the Point Object only accepts 
integer values, it is ideal to have number of rows that divide the grid size without any remainders. 
Due to randomly initializing the grid, when some states are significantly low, probability of those states appearing 
in bottom right corner exists(This doesn't have that often, but does happen once in a while).

Known Bugs: 

1. We did implement Resource bundle, but for some unknown reason, resourcebundle creates error in Mac and Linux. On windows, it works fine. 
Thus, we have commented out resourcebundle in this version but users can run it just by uncommenting one line(reading resourcebundle) in the visualization method.

2. Grid size may vary slightly depending on the shape type due to the way, we construct different methods. 

3. Screen size may have to be adjusted based on the pixel size of users screen. (User only has to change one line. We have placed other GUI components all based on the screesize)

Extra credit: 

1. We simplified the cell class by minimalizing the number of variables within the cell class. 

2. Alert buttons are implemented. 

### Notes

We all sincerely enjoyed this project in that we achieved so much as a group. We worked as a team, and were able to implement 
most of the requirements. We learned a lot. 

### Impressions

We split up the work very evenly and distributed the amount of work over many days to reduce cramming.
We also learned that it is almost impossible just to divide the work by front end and back end since all the group
members had to understand the whole project to finish individual parts. Moreover, debugging is much easier if members 
all know how the code is structured., 

