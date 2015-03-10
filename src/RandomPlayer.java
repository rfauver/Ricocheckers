import java.util.Random;

public class RandomPlayer extends Player 
{
	public int playerNumber;

	public RandomPlayer(int p)
	{
		playerNumber = p;
	}
	
	public void makeMove(Game g)
	{
		int i = 0;
		for (; i < g.getPossibleMoves(playerNumber).length; i++)
		{
			if (g.getPossibleMoves(playerNumber)[i] == null)
			{
				i--;
				break;
			}
		}
		Random r = new Random();
		int moveIndex;
		if (i == 0) moveIndex = i;
		else moveIndex = r.nextInt(i);
		Move m = g.getPossibleMoves(playerNumber)[moveIndex];
		g.makeMove(m, playerNumber);
	}
}
