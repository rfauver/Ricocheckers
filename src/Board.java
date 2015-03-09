
public class Board 
{
	private IntVector2 dimensions;
	private BoardCell[][] cells;
	private final int pieceCount;
	
	public Board(IntVector2 dimensions, int pieceCount)
	{
		this.dimensions = dimensions;
		this.pieceCount = pieceCount;
		
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
		String[] pieces = FileLoader.readFile("pieces.txt", pieceCount);
		
		for (int i = 0; i < pieceCount; i++)
		{
			
		}
	}
	
	public BoardCell getCell(IntVector2 coords)
	{
		return cells[coords.x][coords.z];
	}
}
