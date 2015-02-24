
public class Board 
{
	private IntVector2 dimensions;
	private BoardCell[][] cells;
	
	public Board(IntVector2 dimensions)
	{
		this.dimensions = dimensions;
		for (int i = 0; i < dimensions.x; i++)
		{
			for (int j = 0; j < dimensions.z; j++)
			{
				cells[i][j] = new BoardCell(new IntVector2(i, j));
			}
		}
	}
}
