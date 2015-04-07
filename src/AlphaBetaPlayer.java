
public class AlphaBetaPlayer extends Player
{
	public int playerNumber;
	
	private int maxDepth;
	
	public AlphaBetaPlayer(int p, int maxDepth)
	{
		playerNumber = p;
		this.maxDepth = maxDepth;
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
			double curMin = minValue(g, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
//			System.out.println("curMin: " + curMin);
			if (curMin > max)
			{
				max = curMin;
				moveIndex = i;
			}
			g.undoMove();
		}
		System.out.println("max: " + max);
		g.makeMove(moves[moveIndex], playerNumber);
	}
	
	public double maxValue(Game g, double alpha, double beta, int depth)
	{
		if (g.isGameOver() || depth >= maxDepth) return g.gameValue(playerNumber);
		double val = Double.NEGATIVE_INFINITY;
		Move[] moves = g.getPossibleMoves(playerNumber);
		
		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] == null) break;
			g.makeMove(moves[i], playerNumber);
			double curVal = minValue(g, alpha, beta, depth+1);
			g.undoMove();
			if (curVal > val)
			{
				val = curVal;
			}
			if (val >= beta)
			{
				return val;
			}
			if (val > alpha)
			{
				alpha = val;
			}
		}
		return val;
	}
	
	private double minValue(Game g, double alpha, double beta, int depth)
	{
		if (g.isGameOver() || depth >= maxDepth) return g.gameValue(playerNumber);
		double val = Double.POSITIVE_INFINITY;
		Move[] moves = g.getPossibleMoves(playerNumber);
		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] == null) break;
			g.makeMove(moves[i], playerNumber%2+1);
			double curVal = maxValue(g, alpha, beta, depth+1);
			g.undoMove();
			if (curVal < val)
			{
				val = curVal;
			}
			if (val <= alpha)
			{
				return val;
			}
			if (val < beta)
			{
				beta = val;
			}
		}
		return val;
	}
}
