
public class BoardCell 
{
	public IntVector2 coords;

	private Board board;
	private BoardCellEdge[] edges = new BoardCellEdge[Direction.values().length];

	public BoardCell(IntVector2 coordinates, String walls, Board b)
	{
		coords = coordinates;

		boolean[] wall = new boolean[4];

		int index = 0;
		while (walls.charAt(index) != ';')
		{
			switch (walls.charAt(index))
			{
			case 'N':
				wall[0] = true;
				break;
			case 'E':
				wall[1] = true;
				break;
			case 'S':
				wall[2] = true;
				break;
			case 'W':
				wall[3] = true;
				break;
			default:
				break;
			}
			index++;
		}

		for (int i = 0; i < edges.length; i++)
		{
			if (wall[i]) //is wall
			{
				edges[i] = new BoardWall(this, b.adjacent(this, Direction.toDirection(i)), Direction.toDirection(i));
			}
			else //is passage
			{
				edges[i] = new BoardPassage(this, b.adjacent(this, Direction.toDirection(i)), Direction.toDirection(i));
			}
		}	
	}
}
