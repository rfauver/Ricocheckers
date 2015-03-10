import java.util.ArrayList;

public class Board 
{
	private BoardCell[][] cells;
	private GamePiece[] pieces;
	private final IntVector2 dimensions;
	private final int pieceCount;
	private final IntVector2[] startingPositions;
	
	public Board(IntVector2 dimensions, int pieceCount)
	{
		this.dimensions = dimensions;
		this.pieceCount = pieceCount;
		
		cells = new BoardCell[dimensions.x][dimensions.z];
		pieces = new GamePiece[pieceCount];
		startingPositions = new IntVector2[pieceCount];
		
		createCells();
		
		createPieces();
	}
	
	private void createCells()
	{
		String[] walls = FileLoader.readFile("walls.txt", dimensions.x * dimensions.z);
		for (int i = 0; i < dimensions.x; i++)
		{
			for (int j = 0; j < dimensions.z; j++)
			{
				cells[i][j] = new BoardCell(new IntVector2(i, j), walls[i*dimensions.x + j], this);
			}
		}
	}
	
	private void createPieces()
	{
		String[] piecesString = FileLoader.readFile("pieces.txt", pieceCount);
		int tempX = 0, tempZ = 0, tempPlayer = 1, paramIndex = 0;
		
		for (int i = 0; i < pieceCount; i++)
		{
			String temp = "";
			paramIndex = 0;
			for (int j = 0; piecesString[i].charAt(j) != ';'; j++)
			{
				if (piecesString[i].charAt(j) == ',')
				{
					switch (paramIndex)
					{
					case 0:
						tempX = Integer.parseInt(temp);
						break;
					case 1:
						tempZ = Integer.parseInt(temp);
						break;
					default:
						break;
					}
					temp = "";
					paramIndex++;
				}
				else 
				{
					temp += piecesString[i].charAt(j);
				}
			}
			tempPlayer = Integer.parseInt(temp);
			IntVector2 coords = new IntVector2(tempX, tempZ);
			pieces[i] = new GamePiece(coords, tempPlayer, this);
			startingPositions[i] = coords;
			getCell(coords).piece = pieces[i];
		}
	}
	
	public BoardCell getCell(IntVector2 coords)
	{	
		if (coords.x >= dimensions.x || coords.z >= dimensions.z || coords.x < 0 || coords.z < 0)
		{
			return null;
		}
		else return cells[coords.x][coords.z];
	}
	
	public GamePiece[] getPieces()
	{
		return pieces;
	}
	
	public GamePiece[] getPlayerPieces(int playerNumber)
	{
		ArrayList<GamePiece> playerPieces = new ArrayList<GamePiece>();
		
		for (int i = 0; i < pieces.length; i++)
		{
			if (pieces[i].playerNumber == playerNumber)
			{
				playerPieces.add(pieces[i]);
			}
		}
		
		return playerPieces.toArray(new GamePiece[playerPieces.size()]);
	}
	
	public IntVector2[] getPlayerStartingPositions(int playerNumber)
	{
		ArrayList<IntVector2> playerStartingPositions = new ArrayList<IntVector2>();
		
		for (int i = 0; i < startingPositions.length; i++)
		{
			if (pieces[i].playerNumber == playerNumber)
			{
				playerStartingPositions.add(startingPositions[i]);
			}
		}
		
		return playerStartingPositions.toArray(new IntVector2[playerStartingPositions.size()]);
	}
	
	public String toString()
	{
		String outString = "+---+---+---+---+---+---+---+---+---+---+---+\n";
		
		for (int i = 0; i < dimensions.x; i++)
		{
			for (int j = 0; j < dimensions.z; j++)
			{
				if (j == 0)
					outString += "|";
				
				if (cells[i][j].piece == null)
					outString += "   ";
				else if (cells[i][j].piece.playerNumber == 1)
					outString += " 1 ";
				else if (cells[i][j].piece.playerNumber == 2)
					outString += " 2 ";
				else outString += "   ";
				
				if (j != dimensions.z-1)
				{
					if (cells[i][j].getEdge(Direction.EAST) instanceof BoardWall)
						outString += "|";
					else outString += " ";
				}
				else
				{
					if (cells[i][j].getEdge(Direction.EAST) instanceof BoardWall)
						outString += "|\n";
					else outString += " \n";
				}
			}
			for (int j = 0; j < dimensions.z; j++)
			{
				if (j == 0)
					outString += "+";
				if (cells[i][j].getEdge(Direction.SOUTH) instanceof BoardWall)
					outString += "---+";
				else outString += "   +";
				if (j == dimensions.z-1)
					outString += "\n";
			}
		}
		return outString;
	}
}
