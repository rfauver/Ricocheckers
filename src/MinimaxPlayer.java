
public class MinimaxPlayer extends Player 
{
	public int playerNumber;
	
	public MinimaxPlayer(int p)
	{
		playerNumber = p;
	}
	
	public void makeMove(Game g)
	{
		double max = Double.NEGATIVE_INFINITY;
		int moveIndex = 0;
		Move[] moves = g.getPossibleMoves(playerNumber);
		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] == null) break;
			g.makeMove(moves[i], playerNumber);
			double curMin = minValue(g);
			if (curMin > max)
			{
				max = curMin;
				moveIndex = i;
			}
			g.undoMove();
		}
		g.makeMove(moves[moveIndex], playerNumber);
	}
	
	private double maxValue(Game g)
	{
		if (g.isGameOver()) return g.gameValue(playerNumber);
		double val = Double.NEGATIVE_INFINITY;
		Move[] moves = g.getPossibleMoves(playerNumber);
		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] == null) break;
			g.makeMove(moves[i], playerNumber);
			double curVal = minValue(g);
			g.undoMove();
			if (curVal > val)
			{
				val = curVal;
			}
		}
		return val;
	}
	
	private double minValue(Game g)
	{
		if (g.isGameOver()) return g.gameValue(playerNumber);
		double val = Double.POSITIVE_INFINITY;
		Move[] moves = g.getPossibleMoves(playerNumber);
		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] == null) break;
			g.makeMove(moves[i], playerNumber%2+1);
			double curVal = maxValue(g);
			g.undoMove();
			if (curVal < val)
			{
				val = curVal;
			}
		}
		return val;
	}
}
