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
		GamePiece[] player1Pieces = board.getPlayerPieces(1);
		GamePiece[] player2Pieces = board.getPlayerPieces(2);

		for (int i = 0; i < player1Pieces.length; i++)
		{
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
		if (!gameOver || winner == 0) return 0;
		if (winner == player) return 1.0;
		else return -1.0;
	}

	public Move[] getPossibleMoves(int player) 
	{
		GamePiece[] pieces = board.getPlayerPieces(player);
		ArrayList<Move> moves = new ArrayList<Move>();

		for (int i = 0; i < pieces.length; i++)
		{
			BoardCellEdge[] edges = pieces[i].getCurrentCell().getEdges();
			for (int j = 0; j < edges.length; j++)
			{
				BoardCell currentCell = edges[j].cell;
				if (edges[j] instanceof BoardWall || edges[j].adjCell.piece != null) {}
				else
				{
					while (currentCell.getEdges()[j] instanceof BoardPassage && currentCell.getEdges()[j].adjCell.piece == null)
					{
						currentCell = currentCell.getEdges()[j].adjCell;
					}
					moves.add(new Move(currentCell.coords, pieces[i]));
				}
			}
		}
		return null;
	}

	public void makeMove(Move move, int player) 
	{
		board.getCell(move.piece.getCurrentCell().coords).piece = null;
		board.getCell(move.destination).piece = move.piece;

		move.piece.setCurrentCell(board.getCell(move.destination));

		moveStack.push(move);
	}

	public void undoMove() 
	{
		if (moveStack.isEmpty()) { return; }
		if (gameOver) gameOver = false;
		winner = 0;
		Move move = moveStack.pop();

		move.piece.getCurrentCell().piece = null;
		if (moveStack.empty())
		{
			move.piece.getStartingCell().piece = move.piece;
			move.piece.setCurrentCell(move.piece.getStartingCell());
		}
		else 
		{
			board.getCell(moveStack.peek().destination).piece = move.piece;
			move.piece.setCurrentCell(board.getCell(moveStack.peek().destination));
		}
	}

}
