import java.util.Arrays;
import java.util.Random;


public class ReinforcementLearnerPlayer extends Player 
{
	public int playerNumber;
	
	private int moveCount = 0;
	private double[] weights;
	private double epsilon = 0.0;
	private double alpha = 0.05;
	private Move[] lastTwoMoves = new Move[2];
	
	public ReinforcementLearnerPlayer(int p)
	{
		playerNumber = p;
		weights = new double[42];
		weights = importWeights();
		if (weights[0] == -1)
		{
			// start weights initialized to 0 if file is empty
			Arrays.fill(weights, 0);
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
	
		// determine the maximum value move
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
		
		// make a random move epsilon percent of the time or if there is no most valuable move
		Random rand = new Random();
		if (rand.nextFloat() >= epsilon && maxMove != null)
		{
			g.makeMove(maxMove, playerNumber);
		}
		else
		{
			// if there is no most valuable move, update weights based on random move
			g.makeMove(possibleMoves[rand.nextInt(possibleMoves.length)], playerNumber);
			if (maxMove == null)
			{
				nextFeatures = calculateFeatures((AIGame) g);
			}
		}
		
		// Reward is number of pieces in goal / 3
		double reward = nextFeatures[41];
		double maxWeight = 0;
		for (int i = 0; i < startFeatures.length; i++)
		{
			// updating weights
			weights[i] = Math.max(0, weights[i] + alpha*(reward + nextFeatures[i]*weights[i] - startFeatures[i]*weights[i])*nextFeatures[i]);
			if (weights[i] > maxWeight)
				maxWeight = weights[i];
		}
		
		// scaling by maximum weight to keep weights between 0 and 1
		if (maxWeight > 0.0)
		{
			for (int i = 0; i < startFeatures.length; i++)
			{
				weights[i] = weights[i]/maxWeight;
			}
		}
		
		lastTwoMoves[1] = lastTwoMoves[0];
		lastTwoMoves[0] = maxMove;
		
		// export weights every million moves
		if (moveCount%1000000 == 0)
		{
			exportWeights();
		}
	}

	private double[] calculateFeatures(AIGame g) 
	{
		GamePiece[] myPieces = g.getBoard().getPlayerPieces(playerNumber);
		
		double[] result = new double[weights.length];
		double[] cols = new double[20];
		double[] rows = new double[20];
		double half, goals;
		
		cols = calculateCols(g, myPieces);
		rows = calculateRows(g, myPieces);
		half = calculateHalf(myPieces);
		goals = calculateGoals(g, myPieces);
		
		for (int i = 0; i < cols.length; i++)
		{
			result[i] = cols[i];
			result[i+cols.length] = rows[i];
		}
		result[40] = half;
		result[41] = goals;

		return result;
	}
	
	private double[] calculateCols(AIGame g, GamePiece[] pieces)
	{
		double[] result = new double[20];
		Arrays.fill(result, 0);
		int index = 0;
		BoardCell currentCell = g.getBoard().getCell(new IntVector2(0,0));
		for (int i = 0; i < 11; i++)
		{
			currentCell = g.getBoard().getCell(new IntVector2(0, i));
			while(currentCell != null)
			{
				if (currentCell.coords.x == 5 && currentCell.coords.z == 5)
				{
					currentCell = currentCell.adjacent(Direction.SOUTH);
				}
				if (currentCell.piece != null && currentCell.piece.playerNumber == playerNumber)
				{
					result[index] += (1.0/3.0);
				}

				if (currentCell.getEdge(Direction.SOUTH) instanceof BoardWall)
				{
					index++;
				}
				currentCell = currentCell.adjacent(Direction.SOUTH);
			}
		}
		return result;
	}
	
	private double[] calculateRows(AIGame g, GamePiece[] pieces)
	{
		double[] result = new double[20];
		Arrays.fill(result, 0);
		int index = 0;
		BoardCell currentCell = g.getBoard().getCell(new IntVector2(0,0));
		for (int i = 0; i < 11; i++)
		{
			currentCell = g.getBoard().getCell(new IntVector2(i, 0));
			while(currentCell != null)
			{
				if (currentCell.coords.x == 5 && currentCell.coords.z == 5)
				{
					currentCell = currentCell.adjacent(Direction.EAST);
				}
				if (currentCell.piece != null && currentCell.piece.playerNumber == playerNumber)
				{
					result[index] += (1.0/3.0);
				}

				if (currentCell.getEdge(Direction.EAST) instanceof BoardWall)
				{
					index++;
				}
				currentCell = currentCell.adjacent(Direction.EAST);
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
				if (piece.coordinates.x == i || (piece.coordinates.x == 5 && piece.coordinates.z == i))
				{
					result += 1.0/3.0;
				}
			}
		}
		return result;
	}
	
	private double calculateGoals(AIGame g, GamePiece[] pieces)
	{
		double result = 0;
		for (GamePiece piece : pieces)
		{
			for (BoardCell cell : g.getBoard().getPlayerStartingCells(playerNumber%2 + 1))
			{
				if (piece.coordinates.x == cell.coords.x && piece.coordinates.z == cell.coords.z)
				{
					result += 1.0/3.0;
				}
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
