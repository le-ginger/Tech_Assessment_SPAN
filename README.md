# Aiden Diederiks
## <b>SPAN Digital</b> - BE technical assessment

# Getting started:

1. Download the repository files to you local machine.
2. Extract the files to a directory of your choice.
3. To run the program, open your terminal and use command `javac Main.java` to compile the project files. Then use command `java Main` to run the program and follow the on screen prompts.

## Please note:
- The input file needs to be in the relative directory of the project files for the program to work as expected.

The solution was developed on the Mac operating system and should be usable on Windows or Linux, though I have not tested on those platforms.

---

## The Problem

Create a production ready, maintainable, testable command-line application that will calculate the ranking table for a league.

### The rules

In this league, a draw (tie) is worth 1 point and a win is worth 3 points. A loss is worth 0 points.
If two or more teams have the same number of points, they should have the same rank and be printed in alphabetical order (as in the tie for 3rd place in the sample data).

<u>Sample input:</u>
`Lions 3, Snakes 3
`Tarantulas 1, FC Awesome 0
`Lions 1, FC Awesome 1
`Tarantulas 3, Snakes 1
`Lions 4, Grouches 0

<u>Expected output:</u>
`1. Tarantulas, 6 pts
`2. Lions, 5 pts
`3. FC Awesome, 1 pt
`3. Snakes, 1 pt
`5. Grouches, 0 pts

