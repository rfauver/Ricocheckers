
public class Player 
{
	public int playerNumber;
	
	public Player(){}
	
	public Player(int p)
	{
		playerNumber = p;
	}
	
	public void makeMove(Game g)
	{
		Move m = g.getPossibleMoves()[0];
		g.makeMove(m, playerNumber);
	}
}
