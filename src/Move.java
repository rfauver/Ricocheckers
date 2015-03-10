
public class Move 
{
	public IntVector2 destination;
	public GamePiece piece;
	
	public Move(IntVector2 destination, GamePiece piece)
	{
		this.destination = destination;
		this.piece = piece;
	}
}
