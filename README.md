# Summary

Klotski, also called 华容道 in Chinese, is a block-sliding puzzle game.  You can find more information on [wikipedia](https://en.wikipedia.org/wiki/Klotski).  This is a Java program that uses width-first search to find the best solution to the puzzle.  It is basically a simple version of [Dijkstra algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm). A sample output of the program is in SampleOutput.html.

# Implementation Notes

The program counts moving one block two steps consecutively as one move.  It takes some extra work for the program than just countiing the single-step moves.  

There are actually two programs here, using two different data structure.  One uses a conventional object model.  Another uses a 64-bit long data type to represent the image of the board.  This allows filtering out invalid moves efficiently by using bit operations.  For the classic layout 横刀立马, it takes < 0.1 second on a PC with Intel i7-6500U 2.5GHz CPU to solve the puzzle.  The program using a more conventional object model takes about 0.4 seconds.

Here is the idea of how to represent the board in bits: My initial thought was to use 1 for an occupied cell and 0 for an empty one.  Then the classic board of 横刀立马 would look like this:
 
    1111
    1111
    1001 
    1111 
    1001
 
The problem for this representation is that it has ambiguity. To remove the ambiguity, imagine there is a space between each cell, both horizontally and vertically. Expand the space into an extra cell by itself. This spacing cell is 1 if the neighboring cells belong to one block. Then 横刀立马 looks like the following:
 
    1011101 <- Row 1
    1011101 <- Spacing
    1011101 <- Row 2
    0000000 <- Spacing
    1011101 <- Row 3
    1000001 <- Spacing
    1010101 <- Row 4
    0000000 <- Spacing
    1000001 <- Row 5
 
There are 9 x 7 = 63 bits and can be stored in one 64-bit long variable. With the bit image, we can use bit operation to move it left, right, up and down. We can use this to filter out invalid moves efficiently.



