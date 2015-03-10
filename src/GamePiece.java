
public class GamePiece 
{
	public IntVector2 coordinates;
	public int playerNumber;
	
	private Board board;
	private BoardCell currentCell;
	
	public GamePiece(IntVector2 coordinates, int playerNumber, Board b)
	{
		this.coordinates = coordinates;
		this.playerNumber = playerNumber;
		board = b;
		
		currentCell = b.getCell(coordinates);
	}
}
