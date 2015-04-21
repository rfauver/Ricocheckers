
public class AlphaBetaPlayer extends Player
{
	public int playerNumber;
	
	private int maxDepth;
	
	private Move[] lastTwoMoves = new Move[2];
	
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
			if (lastTwoMoves[1] != null && moves[i].destination == lastTwoMoves[1].destination)
			{
				if (i < moves.length-1)
					moves[i] = moves[i+1];
				else 
					moves[i] = moves[i-1];
			}
			g.makeMove(moves[i], playerNumber);
			double curMin = minValue(g, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
//			System.out.println("curMin: " + curMin + "\t\tmove: " + moves[i].destination.x + " " + moves[i].destination.z);
			if (curMin > max)
			{
				max = curMin;
				moveIndex = i;
			}
			g.undoMove();
		}
		System.out.println("max: " + max);
		lastTwoMoves[1] = lastTwoMoves[0];
		lastTwoMoves[0] = moves[moveIndex];
		g.makeMove(moves[moveIndex], playerNumber);
	}
	
	private double maxValue(Game g, double alpha, double beta, int depth)
	{
		if (g.isGameOver() || depth >= maxDepth) return g.gameValue(playerNumber);
		double val = Double.NEGATIVE_INFINITY;
		Move[] moves = g.getPossibleMoves(playerNumber);
		
		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] == null) break;
			g.makeMove(moves[i], playerNumber);
			double curVal = Math.max(val, minValue(g, alpha, beta, depth+1));
//			System.out.println("max  " + curVal + "\tdepth  " + (depth+1));
			g.undoMove();
			if (curVal > val)
			{
				val = curVal;
			}
			if (val > alpha)
			{
				alpha = val;
			}
			if (val >= beta)
			{
				return val;
			}
		}
		return val;
	}
	
	private double minValue(Game g, double alpha, double beta, int depth)
	{
		if (g.isGameOver() || depth >= maxDepth) return g.gameValue(playerNumber);
		double val = Double.POSITIVE_INFINITY;
		Move[] moves = g.getPossibleMoves(playerNumber%2+1);
		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] == null) break;
			g.makeMove(moves[i], playerNumber%2+1);
			double curVal = Math.min(val, maxValue(g, alpha, beta, depth+1));
//			System.out.println("min  " + curVal);
			g.undoMove();
			if (curVal < val)
			{
				val = curVal;
			}
			if (val < beta)
			{
				beta = val;
			}
			if (val <= alpha)
			{
				return val;
			}
		}
		return val;
	}
}
