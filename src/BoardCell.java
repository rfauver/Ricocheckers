
public class BoardCell 
{
	public IntVector2 coords;
	
	private BoardCellEdge[] edges = new BoardCellEdge[Direction.values().length];
	
	public BoardCell(IntVector2 coordinates)
	{
		coords = coordinates;
		
		for (int i = 0; i < edges.length; i++)
		{
			if () //is wall
			{
				edges[i] = new BoardWall(this, adjacent, Direction.toDirection(i));
			}
			else //is passage
			{
				edges[i] = new BoardPassage(this, adjacent, Direction.toDirection(i));
			}
		}
	}
}
