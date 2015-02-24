
public class BoardCellEdge 
{
	public BoardCell cell, adjCell;
	public Direction dir;
	
	public BoardCellEdge(BoardCell cell, BoardCell adjacentCell, Direction direction)
	{
		this.cell = cell;
		adjCell = adjacentCell;
		dir = direction;
	}
}
