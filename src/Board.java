import java.util.ArrayList;

public class Board 
{
	private BoardCell[][] cells;
	private GamePiece[] pieces;
	private final IntVector2 dimensions;
	private final int pieceCount;
	private final BoardCell[] startingCells;
	
	public Board(IntVector2 dimensions, int pieceCount)
	{
		this.dimensions = dimensions;
		this.pieceCount = pieceCount;
		
		cells = new BoardCell[dimensions.x][dimensions.z];
		pieces = new GamePiece[pieceCount];
		startingCells = new BoardCell[pieceCount];
		
		createCells();
		
		createPieces();
	}
	
	private void createCells()
	{		
		for (int i = 0; i < dimensions.x; i++)
		{
			for (int j = 0; j < dimensions.z; j++)
			{
				cells[i][j] = new BoardCell(new IntVector2(i, j), this);
			}
		}
		
		String[] walls = FileLoader.readFile("walls.txt", dimensions.x * dimensions.z);
		for (int i = 0; i < dimensions.x; i++)
		{
			for (int j = 0; j < dimensions.z; j++)
			{
				cells[i][j].createEdges(walls[i*dimensions.x + j]);
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
			pieces[i] = new GamePiece(coords, tempPlayer, this, i);
			startingCells[i] = pieces[i].getStartingCell();
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
	
	public BoardCell[] getPlayerStartingCells(int playerNumber)
	{
		ArrayList<BoardCell> playerStartingCells = new ArrayList<BoardCell>();
		
		for (int i = 0; i < startingCells.length; i++)
		{
			if (pieces[i].playerNumber == playerNumber)
			{
				playerStartingCells.add(startingCells[i]);
			}
		}
		
		return playerStartingCells.toArray(new BoardCell[playerStartingCells.size()]);
	}
	
	public void setPieces(GamePiece[] pieceConfiguration)
	{
		for (int i = 0; i < pieces.length; i++)
		{
			pieces[i] = pieceConfiguration[i];
		}
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
