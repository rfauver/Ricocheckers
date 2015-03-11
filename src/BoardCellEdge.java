
public class BoardCellEdge 
{
	public BoardCell cell, adjCell;
	public Direction dir;
	
	public BoardCellEdge(BoardCell cell, BoardCell adjacentCell, Direction direction)
	{
		this.cell = cell;
		adjCell = adjacentCell;
		dir = direction;
		if (adjCell != null && adjCell.adjacent(dir.opposite()) == null)
			adjCell.getEdge(dir.opposite()).adjCell = cell;
		dir = direction;
	}
}
