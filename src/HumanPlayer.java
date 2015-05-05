import javax.swing.JOptionPane;


public class HumanPlayer extends Player
{
	public int playerNumber;
	
	private GamePiece activePiece;
	private int activePieceIndex;
	
	public HumanPlayer(int p)
	{
		playerNumber = p;
	}
	
	public void makeMove(Game g)
	{
		if(activePiece == null)
		{
			activePiece = g.getBoard().getPlayerPieces(playerNumber)[0];
			activePieceIndex = 0;
		}
		
		boolean moveMade = false;
		
		while (!moveMade)
		{
			activePiece.playerSymbol = "X";
			System.out.println((100 - ((AIGame)g).turnsRemaining()) + " turns remaining");
			System.out.println(g.getBoard().toString());
			activePiece.playerSymbol = Integer.toString(activePiece.playerNumber);
			String response = JOptionPane.showInputDialog("Please press enter to switch pieces or NORTH, SOUTH, EAST, or WEST to move the active piece in that direction");
			System.out.println(response);
			if (response.trim().isEmpty())
			{
				changeActivePiece(g);
			}
			else
			{
				try
				{
					moveMade = ((AIGame) g).makeMove(activePiece, Direction.valueOf(response.toUpperCase()));
					if (!moveMade)
					{
						throw new IllegalArgumentException();
					}
				}
				catch (IllegalArgumentException e)
				{
					JOptionPane.showMessageDialog(null, "Invalid move, try again.");
				}
			}
		}
	}
	
	private void changeActivePiece(Game g)
	{
		activePieceIndex++;
		if (activePieceIndex >= g.getBoard().getPlayerPieces(playerNumber).length)
		{
			activePieceIndex = 0;
		}
		
		activePiece = g.getBoard().getPlayerPieces(playerNumber)[activePieceIndex];
	}
}
