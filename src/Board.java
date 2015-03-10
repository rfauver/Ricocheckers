import java.util.ArrayList;

public class Board 
{
	private BoardCell[][] cells;
	private GamePiece[] pieces;
	private final IntVector2 dimensions;
	private final int pieceCount;
	
	public Board(IntVector2 dimensions, int pieceCount)
	{
		this.dimensions = dimensions;
		this.pieceCount = pieceCount;
		
		cells = new BoardCell[dimensions.x][dimensions.z];
		pieces = new GamePiece[pieceCount];
		
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
				cells[i][j] = new BoardCell(new IntVector2(i, j), walls[i*j + j], this);
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
}
