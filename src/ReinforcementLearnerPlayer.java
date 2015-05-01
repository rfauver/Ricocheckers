import java.util.Arrays;
import java.util.Random;


public class ReinforcementLearnerPlayer extends Player 
{
	public int playerNumber;
	
	private int moveCount = 0;
	private double[] weights;
	private double epsilon = 0.1;
	private double alpha = 0.8;
	
	public ReinforcementLearnerPlayer(int p)
	{
		playerNumber = p;
		weights = new double[24];
		weights = importWeights();
		if (weights[0] == -1)
		{
			String[] toFile = new String[weights.length];
			Random rand = new Random();
			for (int i = 0; i < weights.length; i++)
			{
				weights[i] = rand.nextDouble();
				toFile[i] = "" + weights[i];
			}
			FileLoader.writeFile("weights.txt", toFile);
		}
		
	}

	public void makeMove(Game g)
	{
		Move[] possibleMoves = g.getPossibleMoves(playerNumber);
		double value = 0.0;
		Move maxMove = null;
		int[] startFeatures = calculateFeatures((AIGame) g);
		int[] nextFeatures = new int[weights.length];
	
		for (Move move : possibleMoves)
		{
			g.makeMove(move, playerNumber);
			double currentValue = 0.0;
			int[] currentFeatures = calculateFeatures((AIGame) g);
			for (int i = 0; i < currentFeatures.length; i++)
			{
				System.out.println(currentValue);
				currentValue += currentFeatures[i] * weights[i]; 
			}
			
			g.undoMove();
			
			if (currentValue > value)
			{
				maxMove = move;
				nextFeatures = currentFeatures;
			}
		}
		
		// Reward is number of pieces in goal / 3
		double reward = ((double) nextFeatures[23])/3.0;
		
		for (int i = 0; i < startFeatures.length; i++)
		{
			weights[i] = weights[i] + alpha*(reward + nextFeatures[i] - startFeatures[i])*startFeatures[i]; 
		}
		
		if (maxMove == null)
		{
			System.out.println(moveCount);
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
			String[] toFile = new String[weights.length];
			for (int i = 0; i < weights.length; i++)
			{
				toFile[i] = "" + weights[i];
			}
			FileLoader.writeFile("weights.txt", toFile);
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
	
	
	private double[] importWeights() 
	{
		double[] weightDoubles = new double[weights.length];
		String[] weightStrings = FileLoader.readFile("weights.txt", weights.length);
		
		if(weightStrings[0] == null)
		{
			weightDoubles[0] = -1;
			return weightDoubles;
		}
		for (int i = 0; i < weights.length; i++)
		{
			weightDoubles[i] = Double.parseDouble(weightStrings[i]);
		}
		
		return weightDoubles;
	}
}
