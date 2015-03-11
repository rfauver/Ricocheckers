
public class GamePiece 
{
	public IntVector2 coordinates;
	public int playerNumber;
	public int index;
	
	private BoardCell currentCell;
	private final BoardCell startingCell;
	
	public GamePiece(IntVector2 coordinates, int playerNumber, Board b, int index)
	{
		this.coordinates = coordinates;
		this.playerNumber = playerNumber;
		this.index = index;
		
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
