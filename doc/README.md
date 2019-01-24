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
