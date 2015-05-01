import java.util.Arrays;
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
		weights = new double[24];
//		if (weight file empty)
//		{
//			weights = new double[24];
//			generate weights;
//		}
//		else weights <- file;
	}
	
	public void makeMove(Game g)
	{
//		Move[] possibleMoves = g.getPossibleMoves(playerNumber);
//		double value = 0.0;
//		Move maxMove = null;
//	
//		for (Move move : possibleMoves)
//		{
//			g.makeMove(move, playerNumber);
//			double currentValue = 0.0;
//			calculateFeatures((AIGame) g);
//			for (each feature)
//			{
//				currentValue += feature[i] * weight[i]; 
//			}
//			
//			g.undoMove();
//			
//			if (currentValue > value)
//			{
//				maxMove = move;
//			}
//		}
//		
//
//		for (each feature)
//		{
//			weight[i] = weight[i] + alpha*(reward + newWeight[i] - weight[i])*feature[i]; 
//		}
//		
//		g.makeMove(maxMove, playerNumber);
//		Random rand = new Random();
//		if (rand.nextFloat() < epsilon)
//		{
//			g.undoMove();
//			g.makeMove(possibleMoves[rand.nextInt(possibleMoves.length)], playerNumber);
//		}
//		
//		if (moveCount%1000 == 0)
//		{
//			weights -> file;
//		}
		int[] features = calculateFeatures((AIGame) g);
		for (int f : features)
		{
			System.out.println(f);
		}
	}

	private int[] calculateFeatures(AIGame g) 
	{
		GamePiece[] myPieces = g.getBoard().getPlayerPieces(playerNumber);
		
		int[] result = new int[weights.length];
		int[] cols = new int[11];
		int[] rows = new int[11];
		int half, goals;
		
		cols = calculateCols(myPieces);
		rows = calculateRows(myPieces);
		half = calculateHalf(myPieces);
		goals = calculateGoals(myPieces, g);
		
		for (int i = 0; i < 11; i++)
		{
			result[i] = cols[i];
			result[i+11] = rows[i];
		}
		result[22] = half;
		result[23] = goals;
		
		return result;
	}
	
	private int[] calculateCols(GamePiece[] pieces)
	{
		int[] result = new int[11];
		Arrays.fill(result, 0);
		for (int i = 0; i < 11; i++)
		{
			for (GamePiece piece : pieces)
			{
				if (piece.coordinates.z == i)
				{
					result[i]++;
				}
			}
		}
		return result;
	}
	
	private int[] calculateRows(GamePiece[] pieces)
	{
		int[] result = new int[11];
		Arrays.fill(result, 0);
		for (int i = 0; i < 11; i++)
		{
			for (GamePiece piece : pieces)
			{
				if (piece.coordinates.x == i)
				{
					result[i]++;
				}
			}
		}
		return result;
	}
	
	private int calculateHalf(GamePiece[] pieces)
	{
		int result  = 0;
		int start, end;
		if (playerNumber == 2)
		{
			start = 0;
			end = 5;
		}
		else
		{
			start = 6;
			end = 11;
		}
		
		for (int i = start; i < end; i++)
		{
			for (GamePiece piece : pieces)
			{
				if (piece.coordinates.x == i)
				{
					result++;
				}
			}
		}
		return result;
	}
	
	private int calculateGoals(GamePiece[] pieces, AIGame g)
	{
		int result = 0;
		for (GamePiece piece : pieces)
		{
			for (BoardCell cell : g.getBoard().getPlayerStartingCells(playerNumber%2 + 1))
			{
				if (piece.coordinates.x == cell.coords.x && piece.coordinates.z == cell.coords.z)
					result++;
			}
		}
		return result;
	}
}
