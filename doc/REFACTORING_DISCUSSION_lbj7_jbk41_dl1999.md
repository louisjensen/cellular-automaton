# Refactoring Lab
Louis Jensen (lbj7), Justin Kim (jbk41), Louis Lee (dl199)

One of the positive aspects of our design so far is that we did not have duplicate code. 
The main thing that we chose to focus on for the refactoring in lab was to make an indivudal class 
for the cells in each simulation. Previously, there was only one cell class that each simulation used.
We considered continuing with this strategy but decided that it would be too difficult to implement for cells
that needed to keep track of several attributes. We decided that the best way to handle this and keep our
design flexible was to make cell an abstract class and extend it for each type of simulation.

Another thing that we are planning to refactor after our discussion is the main class. Our current main
class is just over 400 lines long. We plan to create another class that contains most of the functionality that 
the main class currently has so that the main class can be much shorter.