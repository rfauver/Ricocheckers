
public class Move 
{
	public IntVector2 from;
	public IntVector2 destination;
	public GamePiece piece;
	
	public Move(IntVector2 destination, IntVector2 from, GamePiece piece)
	{
		this.destination = destination;
		this.from = from;
		this.piece = piece;
	}
}
