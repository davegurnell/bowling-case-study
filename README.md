# Case Study: Bowling

Solve the problem below as a group using TDD.

The problem is taken from [codingdojo.org](http://codingdojo.org/kata/Bowling/).

## Problem Specification

Create a program, which,
given a valid line of ten-pin bowling scores,
produces the total score for the game.

You can make the following assumptions:

- All rolls in the score card are valid.
- The number of rolls and frames is valid.
- You only need to calculate a final score
  (not intermediate scores).

How bowling scores are calculated:

- Each game, or "line" of bowling, includes ten turns, or "frames" for the bowler.

- In each frame, the bowler gets up to two tries to knock down all the pins.

- If in two tries, they fail to knock them all down, their score for that frame is the total number of pins knocked down in his two tries.

- If in two tries they knock them all down, this is called a "spare" and their score for the frame is ten plus the number of pins knocked down on their next throw (in their next turn).

- If on their first try in the frame they knock down all the pins, this is called a "strike". Their turn is over, and their score for the frame is ten plus the simple total of the pins knocked down in his next two rolls.

- If they get a spare or strike in the last (tenth) frame, they get to throw one or two more bonus balls, respectively. These bonus throws are taken as part of the same turn. If the bonus throws knock down all the pins, the process does not repeat: the bonus throws are only used to calculate the score of the final frame.

- The game score is the total of all frame scores.

More info on the rules here: [How to Score for Bowling](http://www.topendsports.com/sport/tenpin/scoring.htm).

## Suggested Test Cases

"X" indicates a strike, "/" indicates a spare, "-" indicates a miss:

- `X X X X X X X X X X X X` (12 rolls: 12 strikes) = 10 frames \* 30 points = 300
- `9- 9- 9- 9- 9- 9- 9- 9- 9- 9-` (20 rolls: 10 pairs of 9 and miss) = 10 frames \* 9 points = 90
- `5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/ 5/5` (21 rolls: 10 pairs of 5 and spare, with a final 5) = 10 frames \* 15 points = 150

## Rules of Mob Programming

- One keyboard, one screen
- One driver, many navigators
- Rotate driver every 15 minutes
- Everyone gets time at the keyboard
- Complete each story (plan, code, test) before moving on
- Take breaks when you need to

## Rules of TDD

- Don't write any production code
  unless it is to make a failing unit test pass.

- Don't write any more of a unit test
  than is sufficient to fail (compilation failures are failures).

- Don't write any more production code
  than is sufficient to pass the one failing unit test.

## References

- http://codingdojo.org/kata/Bowling/
- https://ronjeffries.com/xprog/articles/acsbowling/
- http://www.topendsports.com/sport/tenpin/scoring.htm
