Cell Society (updated on 02/11/2019 after second Sprint)
====
This project implements a cellular automata simulator.

Names: Justin Kim, Louis Jensen, Louis Lee

### Timeline

Start Date: 2019/01/29

Finish Date: 2019/02/04 (first sprint) 2019/02/11 (Second Sprint)

Hours Spent: 70+ (each)

### Primary Roles

We worked as a group for the whole project and all contributed 
to each portion.

### Resources Used

Stack Overflow, Google Images, Example codes from lab

### Running the Program

Main class: RunGame.java

Data files needed: All images and css files are in the Resources Folder and configuration files are in the data folder. 
Images used in this project are NOT licensed. XML data files
necessary to run simulations are in the data folder.

Each simulation has at least one XML data file. Some of the simulations have multiple.
They are written so that the XML data file can set the initial configuration
as totally random, randomized based on the number of each type of cell that should be present,
or the state of each individual cell can be set in the data file.

Features implemented: 

1. GUI has 9 buttons: File upload, Shape type, Boundary type, Number of simulations, Initialize, Step(one step), Play button, Speed Control Button, Pause Button
2. Simulations implemented: GameOfLife, Percolation, PredatorPrey, Segregation, SpreadingOfFire, RPS, ForagingAnts 
3. Possible Cell Shapes: Triangle, Square(Rectangle), Hexagon 
4. Neighbors: Neighbors by points and by sides (8 or 4 for rectangle cell) 
5. Cell Boundaries: Regular, Toroidal
6. GUI special features: Pie charts representing the state of the grids. Can run multiple grids(1,2,4) at once. 


Assumptions or Simplifications: 

When the program is run the user must upload a file, select a shape, 
select an edge type, select the number of simulations, and
then press initialize for the grid to appear on the screen.

We are using Point object to represent relative place of Cells on the grid. Since the Point Object only accepts 
integer values, it is ideal to have number of rows that divide the grid size without any remainders. 
Due to randomly initializing the grid, when some states are significantly low, probability of those states appearing 
in bottom right corner exists(This doesn't happen that often, but does happen once in a while).

Known Bugs: 

1. We did implement Resource bundle, but for some unknown reason, resourcebundle creates error in Mac and Linux. On windows, it works fine. 
Thus, we have commented out resourcebundle in this version but users can run it just by uncommenting these lines and reading resourcebundle.

2. Grid size may vary slightly depending on the shape type due to the way, we construct different grid types. 

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

