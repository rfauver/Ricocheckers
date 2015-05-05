import java.util.*;

public class AIGame implements Game 
{
	public boolean gameOver;
	public int winner; //0 for no winner, 1 for player 1, 2 for player 2

	private Board board;
	private Stack<Move> moveStack; 

	public AIGame()
	{
		gameOver = false;
		winner = 0;
		moveStack = new Stack<Move>();
		board = new Board(new IntVector2(11, 11), 6);
	}

	public boolean isGameOver() 
	{
		// see if move limit has been reached
		if (moveStack.size() > 200)
		{
			gameOver = true;
			return true;
		}
		GamePiece[] player1Pieces = board.getPlayerPieces(1);
		GamePiece[] player2Pieces = board.getPlayerPieces(2);

		for (int i = 0; i < player1Pieces.length; i++)
		{
			// check if player 1 has not won yet
			if (player1Pieces[i].coordinates != player2Pieces[0].getStartingCell().coords
			 && player1Pieces[i].coordinates != player2Pieces[1].getStartingCell().coords
			 && player1Pieces[i].coordinates != player2Pieces[2].getStartingCell().coords)
			{
				break;
			}
			if (i == player1Pieces.length-1)
			{
				winner = 1;
				gameOver = true;
				return true;
			}
		}
		for (int i = 0; i < player2Pieces.length; i++)
		{
			// check if player 2 has not won yet
			if (player2Pieces[i].coordinates != player1Pieces[0].getStartingCell().coords
			 && player2Pieces[i].coordinates != player1Pieces[1].getStartingCell().coords
			 && player2Pieces[i].coordinates != player1Pieces[2].getStartingCell().coords)
			{
				return false;
			}
		}
		winner = 2;
		gameOver = true;
		return true;
	}

	public double gameValue(int player) 
	{
		if (!gameOver) 
		{
			GamePiece[] pieces = board.getPlayerPieces(player);
			BoardCell[] goals = board.getPlayerStartingCells((player%2)+1);
			ArrayList<GamePiece> unfinishedPieces = new ArrayList<GamePiece>();
			ArrayList<BoardCell> unfilledGoals = new ArrayList<BoardCell>();
			
			// find pieces that are not already in goal
			for (int i = 0; i < pieces.length; i++)
			{
				boolean isGoal = false;
				for (int j = 0; j < goals.length; j++)
				{
					if (pieces[i].coordinates == goals[j].coords)
					{
						isGoal = true;
					}
				}
				if (!isGoal) unfinishedPieces.add(pieces[i]);
			}
			
			// find goals that are empty
			for (int i = 0; i < goals.length; i++)
			{
				if (goals[i].piece == null)// || goals[i].piece.playerNumber == (player%2)+1)
				{
					unfilledGoals.add(goals[i]);
				}
			}
			
			pieces = unfinishedPieces.toArray(new GamePiece[unfinishedPieces.size()]);
			goals = unfilledGoals.toArray(new BoardCell[unfilledGoals.size()]);
			int[][] distanceToGoals = new int[pieces.length][goals.length];
			double value = 0;
			
			//calculate shortest paths from each piece to each goal
			for (int i = 0; i < pieces.length; i++)
			{	
				distanceToGoals[i] = BFS(pieces[i], goals);
			}
			
			// greedily match each piece to a goal
			for (int i = 0; i < goals.length; i++)
			{
				int minMoveIndex = -1;
				int minPieceIndex = -1;
				int minDistance = Integer.MAX_VALUE;
				for (int j = 0; j < distanceToGoals.length; j++)
				{
					for (int k = 0; k < distanceToGoals[j].length; k++)
					{
						if (distanceToGoals[j][k] < minDistance)
						{
							minDistance = distanceToGoals[j][k];
							minPieceIndex = j;
							minMoveIndex = k;
						}
					}	
				}
				if (minPieceIndex >= 0 && minMoveIndex >= 0)
				{
					if (distanceToGoals[minPieceIndex][minMoveIndex] >= 0)
					{
						// create value by average distance of each piece to goal
						value += (1.0/((double)distanceToGoals[minPieceIndex][minMoveIndex]+1))/(double)pieces.length;
					}
					for (int j = 0; j < distanceToGoals.length; j++)
					{
						distanceToGoals[j][minMoveIndex] = Integer.MAX_VALUE;
					}
					for (int j = 0; j < distanceToGoals[minPieceIndex].length; j++)
					{
						distanceToGoals[minPieceIndex][j] = Integer.MAX_VALUE;
					}
				}
			}
			return value;
		}
		else if (winner == 0) return -1.0;		// value is -1 for both players in a draw
		else if (winner == player) return 1.0;	// value is 1 for winning player
		else return -1.0;						// value is -1 for losing player
	}

	// all the possible moves for a player's pieces
	public Move[] getPossibleMoves(int player) 
	{
		GamePiece[] pieces = board.getPlayerPieces(player);
		ArrayList<Move> moves = new ArrayList<Move>();

		for (int i = 0; i < pieces.length; i++)
		{
			BoardCell[] destinationCells = getPossibleMovesFromCell(pieces[i].getCurrentCell(), player);
			for (BoardCell cell : destinationCells)
			{
				moves.add(new Move( cell.coords, pieces[i].getCurrentCell().coords, pieces[i]));
			}
		}
		return moves.toArray(new Move[moves.size()]);
	}
	
	// all of the possible moves from a given cell for a player
	private BoardCell[] getPossibleMovesFromCell(BoardCell cell, int playerNumber)
	{
		BoardCellEdge[] edges = cell.getEdges();
		ArrayList<BoardCell> destinationCells = new ArrayList<BoardCell>();
		for (int i = 0; i < edges.length; i++)
		{			
			BoardCell destCell = cellAtDirection(cell, Direction.toDirection(i));
			if (destCell != null)
			{
				destinationCells.add(destCell);
			}
		}
		return destinationCells.toArray(new BoardCell[destinationCells.size()]);
	}
	
	// the move from a certain cell in a given direction
	private BoardCell cellAtDirection(BoardCell fromCell, Direction dir)
	{
		BoardCell destCell = null;
		BoardCell currentCell = fromCell;
		if (fromCell.getEdge(dir) instanceof BoardPassage && fromCell.getEdge(dir).adjCell.piece == null)
		{
			while (currentCell.getEdge(dir) instanceof BoardPassage && currentCell.getEdge(dir).adjCell.piece == null)
			{
				currentCell = currentCell.getEdge(dir).adjCell;
			}
			destCell = currentCell;
		}
		return destCell;
	}
	
	public int turnsRemaining()
	{
		return moveStack.size()/2;
	}

	public void makeMove(Move move, int player) 
	{
		board.getCell(move.piece.getCurrentCell().coords).piece = null;
		board.getCell(move.destination).piece = move.piece;

		move.piece.setCurrentCell(board.getCell(move.destination));
		move.piece.coordinates = move.destination;

		moveStack.push(move);
	}
	
	// make move given a piece and a direction
	public boolean makeMove(GamePiece piece, Direction dir)
	{
		BoardCell destination = cellAtDirection(piece.getCurrentCell(), dir);
		if (destination != null)
		{
			Move move = new Move(destination.coords, piece.getCurrentCell().coords, piece);
			makeMove(move, piece.playerNumber);
			return true;
		}
		else return false;
	}

	public void undoMove() 
	{
		if (moveStack.isEmpty()) { return; }
		if (gameOver) 
		{
			gameOver = false;
			winner = 0;
		}
		winner = 0;
		Move move = moveStack.pop();
		GamePiece piece = move.piece;

		piece.getCurrentCell().piece = null;
		piece.setCurrentCell(board.getCell(move.from));
		piece.getCurrentCell().piece = piece;
		piece.coordinates = move.from;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	// Breadth First Search to calculate value of game
	private int[] BFS(GamePiece piece, BoardCell[] goals)
	{
		BoardCell startingCell = board.getCell(piece.coordinates);
		BoardCell currentCell = startingCell;
		startingCell.piece = null;
		int[] distances = new int[goals.length];
		Arrays.fill(distances, Integer.MAX_VALUE);
		int found = 0;
		
		HashMap<BoardCell, Integer> hashmap = new HashMap<BoardCell, Integer>();
		Queue<BoardCell> q = new LinkedList<BoardCell>();
		
		q.add(currentCell);
		hashmap.put(currentCell, 0);
		
		while (!q.isEmpty())
		{
			if (found == goals.length)
			{
				startingCell.piece = piece;
				return distances;
			}
			currentCell = q.poll();
			for (int i = 0; i < goals.length; i++)
			{
				if (currentCell == goals[i] && distances[i] == Integer.MAX_VALUE)
				{
					distances[i] = hashmap.get(currentCell);
					found++;
				}
			}
			BoardCell[] movableCells = getPossibleMovesFromCell(currentCell, piece.playerNumber);
			for (int i = 0; i < movableCells.length; i++)
			{	
				if (!hashmap.containsKey(movableCells[i]))
				{								
					q.add(movableCells[i]);
					hashmap.put(movableCells[i], hashmap.get(currentCell)+1);
				}
			}
		}
		startingCell.piece = piece;
		return distances;
	}
}
