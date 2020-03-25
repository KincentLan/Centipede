	• A section summarizing what changes you made based on feedback on your previous Centipede submission. This section should mention which data structures, methods, or entire classes were changed, and why, and where in your code graders should look to find the updates.

- Based on the feedback from last assignment, we made two major changes. 
- We abstracted our two game state classes (BGameState and CGameState) to the GameState class, because they share some same fields (gnome, garden, width, and height) and methods (makeScene(), toCGameState(), endGame(), and score()). The two states share the same functionality, but for different world phase of the game. More importantly, we decided to use impworld for this assignment, and we needed a wrapper class GameMaster that has GameState as its field to make it possible for the transition between the two game states.
- We deleted the unnecessary ATileVisitor (an abstract class visitor), because the abstract classed are never instantiated and the purpose of the visitor pattern is to discriminate instantiated classes. All the functional objects are in the FunctionObject File.  
	• A paragraph explaining whether you chose to continue using funworld, or whether you switched to impworld, and why 
- We decided to switch to impworld, because we want to take advantage of mutation and the built-in ArrayLists. We think it makes sense to mutate the object itself instead of making a new object every time, since a lot of things are changing, and mutation also saves a lot of time because we are only considering the change of one (or more) fields at a time.

- ArrayLists were also very helpful. We used generic lists for the last assignment, and had to write a lot of function objects for different purposes. ArrayList got rid of most of the function objects and made our design much cleaner.

	• A section explaining the high-level design choices you made, what main classes are in your design and what they are for.

- The highest level class in our design is the GameMaster class. It is a wrapper class that extends World with a field of game state. It handles the changes in the game state(either the set up/BGameState or the actual game/CGameState) and mutates the game state itself, and makes the transition from BGameState to CGameState possible.

- We also have two game state classes: BGameState for the setup, and CGameState for the actual game. Both BGameState and the CGameState mutates its fields as all the elements interact with each other and the user (through mouse click and keyboard press). We separated them into two classes because the player and the garden behaves differently in the two states, and CGameState has darts, water balloons, and centipedes, but BGameState does not. 
	• A section explaining your overall approach to testing, and what scenarios were most challenging to test for and why

- Since we are using impworld, mutation makes the testing a little bit challenging. We basically tested the targeting field before and after we call the method (mutation) to make sure the methods behave as we desired.

- Centipede move method was the most difficult to test because it entailed a lot of moving parts (i.e. different methods that mutated different things). It was not uncommon to see centipede's move method mutate almost all of the fields in that Centipede, and so testing for those mutations was quite difficult. Since we also mutate things in an ArrayList, we had to show those elements in that ArrayList also mutated so it required a lot of thinking to do so. 
	• A section explaining what you would do differently, had you known about all the parts of the assignment from the beginning (rather than them being revealed over time). 
- We would design BodySeg class differently to incorporate different values of speed when designing our move method for that class. Since we never had speed in mind until it became an issue, everything broke down and the move method had to be bandage patched in order to move correctly instead of it moving correctly in the first place.

- We would also change how to represent our board since we were able to change our implementation tools. Using an ArrayList of an ArrayList of ITile's would make it magnitudes easier to reference the next tile instead of manually searching for it each time. Because of this, detecting tiles ahead proved challenging.


DESIGN CHOICES:
Dandelion health:
We represents a dandelion with full health value as a bright yellow solid square. As the dandelion's health value decreases, it has a less bright yellow color. The discoloration isn't very obvious, but it is there.

PebblePile: 
We did not choose to draw the picture for the entire pile, but we assume that the pile expands out of just a single tile, and the pebble's four neighbors still affect the centipede's movement.

We decided that the centipede's speed can be affected by multiple pebble piles, which means we allow the centipede's speed halved twice if it is moving through two overlapping pebble tiles. This was our own design choice, since the assignment was not clear enough.