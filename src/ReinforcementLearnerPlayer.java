import java.util.Arrays;
import java.util.Random;


public class ReinforcementLearnerPlayer extends Player 
{
	public int playerNumber;
	
	private int moveCount = 0;
	private double[] weights;
	private double epsilon = 0.1;
	private double alpha = 0.5;
	private Move[] lastTwoMoves = new Move[2];
	
	public ReinforcementLearnerPlayer(int p)
	{
		playerNumber = p;
		weights = new double[24];
		weights = importWeights();
		if (weights[0] == -1)
		{
			Random rand = new Random();
			for (int i = 0; i < weights.length; i++)
			{
				weights[i] = rand.nextDouble();
			}
			exportWeights();
		}
		
	}

	public void makeMove(Game g)
	{
		moveCount++;
		Move[] possibleMoves = g.getPossibleMoves(playerNumber);
		double value = 0.0;
		Move maxMove = null;
		double[] startFeatures = calculateFeatures((AIGame) g);
		double[] nextFeatures = new double[weights.length];
	
		for (Move move : possibleMoves)
		{
			g.makeMove(move, playerNumber);
			double currentValue = 0.0;
			double[] currentFeatures = calculateFeatures((AIGame) g);
			for (int i = 0; i < currentFeatures.length; i++)
			{
				currentValue += currentFeatures[i] * weights[i]; 
			}
			
			g.undoMove();
			
			if (currentValue > value && (lastTwoMoves[1] == null || move.destination != lastTwoMoves[1].destination))
			{
				maxMove = move;
				nextFeatures = currentFeatures;
			}
		}
		
		// Reward is number of pieces in goal / 3
		double reward = nextFeatures[23];
		
		double gap;
		for (int i = 0; i < startFeatures.length; i++)
		{
			gap = 1.0 - weights[i];
			weights[i] = Math.max(0, weights[i] + alpha*(reward + nextFeatures[i] - startFeatures[i])*nextFeatures[i]);
		}
		
		Random rand = new Random();
		if (rand.nextFloat() >= epsilon && maxMove != null)
		{
			g.makeMove(maxMove, playerNumber);
		}
		else
		{
			g.makeMove(possibleMoves[rand.nextInt(possibleMoves.length)], playerNumber);
		}
		
		lastTwoMoves[1] = lastTwoMoves[0];
		lastTwoMoves[0] = maxMove;
		
		if (moveCount%10000 == 0)
		{
			exportWeights();
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
	
	private double[] calculateCols(GamePiece[] pieces)
	{
		double[] result = new double[11];
		Arrays.fill(result, 0);
		for (int i = 0; i < 11; i++)
		{
			double distance = Math.abs(5-i)-1;
			if (distance <= 0)
			{
				distance = 1;
			}
			for (GamePiece piece : pieces)
			{
				if (piece.coordinates.z == i)
				{
					result[i] += (1.0/3.0)/distance;
				}
			}
		}
		return result;
	}
	
	private double[] calculateRows(GamePiece[] pieces)
	{
		double[] result = new double[11];
		Arrays.fill(result, 0);
		for (int i = 0; i < 11; i++)
		{
			double distance = Math.abs(5-i)-1;
			if (distance <= 0)
			{
				distance = 1;
			}
			for (GamePiece piece : pieces)
			{
				if (piece.coordinates.x == i)
				{
					result[i] += (1.0/3.0)/distance;
				}
			}
		}
		return result;
	}
	
	private double calculateHalf(GamePiece[] pieces)
	{
		double result  = 0;
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
					result += 1;
				}
			}
		}
		return result;
	}
	
	private double calculateGoals(GamePiece[] pieces, AIGame g)
	{
		double result = 0;
		for (GamePiece piece : pieces)
		{
			for (BoardCell cell : g.getBoard().getPlayerStartingCells(playerNumber%2 + 1))
			{
				if (piece.coordinates.x == cell.coords.x && piece.coordinates.z == cell.coords.z)
					result += 1;
			}
		}
		return result;
	}
	
	public void exportWeights()
	{
		String[] toFile = new String[weights.length];
		for (int i = 0; i < weights.length; i++)
		{
			toFile[i] = "" + weights[i];
		}
		FileLoader.writeFile("weights" + playerNumber + ".txt", toFile);
	}
	
	private double[] importWeights() 
	{
		double[] weightDoubles = new double[weights.length];
		String[] weightStrings = FileLoader.readFile("weights" + playerNumber + ".txt", weights.length);
		
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
