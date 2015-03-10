
public interface Game 
{	
	public boolean isGameOver();
	
	public double gameValue(int player);

	public Move[] getPossibleMoves(int player);
	
	public void makeMove(Move move, int player); // 1 or 2
	
	public void undoMove();
}
