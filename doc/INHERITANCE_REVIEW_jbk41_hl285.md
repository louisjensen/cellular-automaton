# Part 1
1. What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
The visualization encapsulates the Grid and other buttons that will control what is shown on the board. However, the Grid class is encapsulated so that the visualization class does not see how the Grid visualization is shown. The Simulation class is encapsulated so that the Grid class never sees how it actually updates cells. The Grid is passed to the simulation along with the cell that needs to be updated. The simulation determines, based on the cell and its neighbors, what the next state is. The simulation class passes back to the Grid a new cell.

2. What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
The simulation class should be a super class and any class that extends it should redefine the ruleset for how to update a cell. The Grid class is a super class to account for varying shapes of grids.

3. What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating? 
Visualization and Grid should be closed. Visualization should be completely closed. Grid should be closed except for the lookup table that determines what Simulation to make based on the XML file.

4. Why do you think your design is good (also define what your measure of good is)?
I think it is good because it is very modular. To add a new type of simulation, neither the Grid nor Visualization class need to be updated.

#Part 2
1. How is your area linked to/dependent on other areas of the project?
The Grid class passes visuals to the Visualization class and the Grid class asks the Simulation class how to update its cells.

2. Are these dependencies based on the other class's behavior or implementation?
The Grid's behavior is the same. How updating works is determined by the Simulation class.

3. Go over one pair of super/sub classes in detail to see if there is room for improvement. 
The only major part of the sub classes will be the arrangement and shape of the grid.

# Part 3
1. Come up with at least five use cases for your part (most likely these will be useful for both teams).
When the play button is clicked, the Grid is continuously updated. When the pause button is pressed, the Grid stops updating continuously. When the step button is played, the next state of the Grid is shown.

2. What feature/design problem are you most excited to work on?
After design, I am looking forward to work on simulation as it is the most algorithmic.

3. What feature/design problem are you most worried about working on?
I am also worried about the simulation as the algorithms for updating may be difficult.