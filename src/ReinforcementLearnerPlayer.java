import java.util.Random;


public class ReinforcementLearnerPlayer extends Player 
{
	public int playerNumber;
	
	private int moveCount = 0;
	private double[] weights;
	private double epsilon = 0.1;
	
	public ReinforcementLearnerPlayer(int p)
	{
		playerNumber = p;
		if (weight file empty)
		{
			weights = new double[24];
			generate weights;
		}
		else weights <- file;
	}
	
	public void makeMove(Game g)
	{
		Move[] possibleMoves = g.getPossibleMoves(playerNumber);
		double value = 0.0;
		Move maxMove = null;
	
		for (Move move : possibleMoves)
		{
			g.makeMove(move, playerNumber);
			double currentValue = 0.0;
			calculateFeatures((AIGame) g);
			for (each feature)
			{
				currentValue += feature[i] * weight[i]; 
			}
			
			g.undoMove();
			
			if (currentValue > value)
			{
				maxMove = move;
			}
		}
		

		for (each feature)
		{
			weight[i] = weight[i] + alpha*(reward + newWeight[i] - weight[i])*feature[i]; 
		}
		
		g.makeMove(maxMove, playerNumber);
		Random rand = new Random();
		if (rand.nextFloat() < epsilon)
		{
			g.undoMove();
			g.makeMove(possibleMoves[rand.nextInt(possibleMoves.length)], playerNumber);
		}
		
		if (moveCount%1000 == 0)
		{
			weights -> file;
		}
	}

	private double[] calculateFeatures(AIGame g) 
	{
		GamePiece[] myPieces = g.getBoard().getPlayerPieces(playerNumber);
		
		double[] result = new double[weights.length];
		double[] cols = new double[11];
		double[] rows = new double[11];
		double half, goals;
		
		cols = calculateCols(myPieces);
		rows = calculateRows(myPieces);
		half = calculateHalf(myPieces);
		goals = calculateGoals(myPieces);
		
		
		return null;
	}
	
	private double[] calculateCols(GamePiece[] pieces)
	{
		
	}
	
	private double[] calculateRows(GamePiece[] pieces)
	{
		
	}
	
	private double calculateHalf(GamePiece[] pieces)
	{
		
	}
	
	private double calculateGoals(GamePiece[] pieces)
	{
		
	}
}
