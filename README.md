# Ricocheckers

Ricocheckers is an original board game created for CS322 Artificial Intelligence at Skidmore College during the Spring of 2015. The game supports human vs. human, human vs. computer, and computer vs. computer play.

There are four included computer players that all use different AI algorithms:

#### RandomPlayer

Plays entirely randomly.

#### MinimaxPlayer

Uses the [Minimax](https://en.wikipedia.org/wiki/Minimax) decision rule.

#### AlphaBetaPlayer

Uses the Minimax algorithm optimized with [Alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning).

#### ReinforcementLearnerPlayer

Picks each move by determining the maximum value move. Value is computed based on 42 features of the board multiplied by weights for each feature generated by playing the game thousands of times. Features that signified high value ended up with higher weights after training.

# Rules of the Game

The rules are a combination of the movement rules of [RicochetRobots](https://boardgamegeek.com/boardgame/51/ricochet-robots) and the winning rules of Chinese Checkers. Each player has three pieces. Player 1's pieces are represented by a `1` and player 2's pieces are represented by a `2`. The board begins in this configuration: 

```
+---+---+---+---+---+---+---+---+---+---+---+
|                   |                       |
+   +   +   +   +   +   +   +   +   +   +   +
|                                           |
+   +---+   +   +   +   +   +   +   +   +   +
|                           |               |
+   +   +   +---+   +   +   +   +   +   +   +
|   |                                       |
+   +   +   +   +   +   +   +   +   +   +---+
|                     1   1     |           |
+   +   +   +   +   +---+---+   +   +   +   +
|                 1 |   | 2                 |
+   +   +   +   +---+---+   +   +   +   +   +
|           |     2   2                     |
+---+   +   +   +   +   +   +   +   +   +   +
|                                       |   |
+   +   +   +   +   +   +   +---+   +   +   +
|               |                           |
+   +   +   +   +   +   +   +   +   +---+   +
|                                           |
+   +   +   +   +   +   +   +   +   +   +   +
|                       |                   |
+---+---+---+---+---+---+---+---+---+---+---+
```
The starting spaces of Player 1's pieces are also the "goal" spaces of Player 2, and vice-versa. The objective of each player is to get his or her pieces into their own goal spaces before the other player. So Player 2 wins if he or she can get all three of their pieces to the spaces of Player 1's starting locations.

Players alternate moving one piece per turn. A piece can move in the four cardinal directions (NORTH, SOUTH, EAST, and WEST). When a player chooses the direction a piece will move, the piece will move in that direction until it either hits a wall on the board or another piece. So for example, from the starting configuration, if Player 1 wanted to move his leftmost piece, its two valid possible moves would be:

```
+---+---+---+---+---+---+---+---+---+---+---+
|                   |                       |
+   +   +   +   +   +   +   +   +   +   +   +
|                                           |
+   +---+   +   +   +   +   +   +   +   +   +
|                           |               |
+   +   +   +---+   +   +   +   +   +   +   +
|   |                                       |
+   +   +   +   +   +   +   +   +   +   +---+
|                     1   1     |           |
+   +   +   +   +   +---+---+   +   +   +   +
| 1                 |   | 2                 |
+   +   +   +   +---+---+   +   +   +   +   +
|           |     2   2                     |
+---+   +   +   +   +   +   +   +   +   +   +
|                                       |   |
+   +   +   +   +   +   +   +---+   +   +   +
|               |                           |
+   +   +   +   +   +   +   +   +   +---+   +
|                                           |
+   +   +   +   +   +   +   +   +   +   +   +
|                       |                   |
+---+---+---+---+---+---+---+---+---+---+---+
```
or 
```
+---+---+---+---+---+---+---+---+---+---+---+
|                 1 |                       |
+   +   +   +   +   +   +   +   +   +   +   +
|                                           |
+   +---+   +   +   +   +   +   +   +   +   +
|                           |               |
+   +   +   +---+   +   +   +   +   +   +   +
|   |                                       |
+   +   +   +   +   +   +   +   +   +   +---+
|                     1   1     |           |
+   +   +   +   +   +---+---+   +   +   +   +
|                   |   | 2                 |
+   +   +   +   +---+---+   +   +   +   +   +
|           |     2   2                     |
+---+   +   +   +   +   +   +   +   +   +   +
|                                       |   |
+   +   +   +   +   +   +   +---+   +   +   +
|               |                           |
+   +   +   +   +   +   +   +   +   +---+   +
|                                           |
+   +   +   +   +   +   +   +   +   +   +   +
|                       |                   |
+---+---+---+---+---+---+---+---+---+---+---+
```

The game can end two ways. If a player gets all of his or her pieces to their respective goal spaces before their opponent, they win. It does not matter which pieces are in which goal spaces, only that all three pieces are in all three goal spaces. If neither player has won after 100 turns, the game ends in a tie.