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
		return false;
	}

	public double gameValue(int player) 
	{
		return 0;
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

	}

	public void undoMove() 
	{

	}

}
