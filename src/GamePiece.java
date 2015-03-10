
public class GamePiece 
{
	public IntVector2 coordinates;
	public int playerNumber;
	
	private Board board;
	private BoardCell currentCell;
	private final BoardCell startingCell;
	
	public GamePiece(IntVector2 coordinates, int playerNumber, Board b)
	{
		this.coordinates = coordinates;
		this.playerNumber = playerNumber;
		board = b;
		
		currentCell = b.getCell(coordinates);
		startingCell = currentCell;
	}
	
	public BoardCell getCurrentCell()
	{
		return currentCell;
	}
	
	public void setCurrentCell(BoardCell cell)
	{
		currentCell = cell;
	}
	
	public BoardCell getStartingCell()
	{
		return startingCell;
	}
}
