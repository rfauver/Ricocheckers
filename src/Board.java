
public class Board 
{
	private IntVector2 dimensions;
	private BoardCell[][] cells;
	
	public Board(IntVector2 dimensions)
	{
		String[] walls = FileLoader.readFile("walls.txt", dimensions.x * dimensions.y);
		this.dimensions = dimensions;
		for (int i = 0; i < dimensions.x; i++)
		{
			for (int j = 0; j < dimensions.z; j++)
			{
				cells[i][j] = new BoardCell(new IntVector2(i, j), walls[i*j + j], this);
			}
		}
	}
	
	public BoardCell adjacent(BoardCell cell, Direction d)
	{
		return null;
	}
}
