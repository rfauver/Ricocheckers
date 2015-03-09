
public class GamePiece 
{
	public IntVector2 coordinates;
	
	private Board board;
	private BoardCell currentCell;
	
	public GamePiece(IntVector2 coordinates, Board b)
	{
		this.coordinates = coordinates;
		board = b;
		
		currentCell = b.getCell(coordinates);
	}
}
