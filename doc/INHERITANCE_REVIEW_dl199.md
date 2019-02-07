#Part1
Donghun Louis Lee
dl199

<<<<<<< HEAD

We are hiding all the rules related to specific cases. Each simulation class will contain rules for each cases and will inherit the Abstract simulation class.


Abstract class will be simulation class and each simulations will inherit the basic


Visualization class will be closed and the other classes will be open.


Errors might occur when the simulation reaches the end state. We?ll check the ending state and end the simulation if necessary.


We have minimalized dependencies.


#Part2


We tried to minimize dependencies throughout the whole code. For example, filreader class will only be connected to the visualization class and visualization class will be connected to the grid class. Elements of the grid will be cells and grid will call simulation class to update the grid.


This dependencies are based on the other class?s behavior.


If we break down the classes more, we can minimize the dependencies; however, this will increase the total number of classes.


We focused more on the grid class, because grid class will take care of looping through the grid and updating each cells


#Part3
1.


I will be working on the visualization and the simulation method


Some specific cases in the simulation might be difficult to implement
=======
1. We are hiding all the rules related to specific cases. Each simulation class will contain rules for each cases and will inherit the Abstract simulation class.

2. Abstract class will be simulation class and each simulations will inherit the basic

3. Visualization class will be closed and the other classes will be open.

4. Errors might occur when the simulation reaches the end state. We?ll check the ending state and end the simulation if necessary.

5. We have minimalized dependencies. 


#Part2 
1. We tried to minimize dependencies throughout the whole code. For example, filreader class will only be connected to the visualization class and visualization class will be connected to the grid class. Elements of the grid will be cells and grid will call simulation class to update the grid.

2. This dependencies are based on the other class?s behavior.


3. If we break down the classes more, we can minimize the dependencies; however, this will increase the total number of classes.

4. We focused more on the grid class, because grid class will take care of looping through the grid and updating each cells

#Part3
1. 

2. I will be working on the visualization and the simulation method 

3. Some specific cases in the simulation might be difficult to implement
>>>>>>> 9b6a32a363c9fd6ac35658e7ed30df550ebd736b
