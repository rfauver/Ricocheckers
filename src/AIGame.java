import java.util.Stack;

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

	public Move[] getPossibleMoves() 
	{
		return null;
	}

	public void makeMove(Move move, int player) 
	{

	}

	public void undoMove() 
	{

	}

}
