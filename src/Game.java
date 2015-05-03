
public interface Game 
{	
	public boolean gameOver = false;

	public boolean isGameOver();
	
	public double gameValue(int player);
	
	public Board getBoard();

	public Move[] getPossibleMoves(int player);
	
	public void makeMove(Move move, int player); // 1 or 2
	
	public void undoMove();
}
