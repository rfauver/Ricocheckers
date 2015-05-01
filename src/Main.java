
public class Main
{
	public static void main(String[] args) 
	{
		AIGame AIG = new AIGame();
//		Player p1 = new AlphaBetaPlayer(1, 4);
		Player p2 = new AlphaBetaPlayer(2, 4);
		int moves = 0;
		
//		GamePiece testPiece = AIG.getBoard().getPlayerPieces(1)[0];
//		System.out.println(testPiece.coordinates.x + "  " + testPiece.coordinates.z);
//		
//		BoardCell[] testGoal = new BoardCell[1];
//		testGoal[0] = AIG.getBoard().getCell(new IntVector2(8,9));
//		System.out.println(AIG.BFS(testPiece, testGoal)[0]);
		
//		GamePiece[] pieces = AIG.getBoard().getPieces();
//		for (int i = 0; i < pieces.length; i++)
//		{
//			if (pieces[i].playerNumber == 1)
//			{
//				Move temp = new Move(new IntVector2(i, i), pieces[i].coordinates, pieces[i]);
//				AIG.makeMove(temp, 1);
//			}
//			else 
//			{
//				Move temp = new Move(new IntVector2(0, i+1), pieces[i].coordinates, pieces[i]);
//				AIG.makeMove(temp, 2);
//			}
//		}
//		p2.makeMove(AIG);
//		p2.makeMove(AIG);
//		p2.makeMove(AIG);
		String[] s = new String[1];
		s[0] = AIG.getBoard().toString();
//		System.out.println(AIG.gameValue(2));
		
		Player p1 = new ReinforcementLearnerPlayer(1);

		p1.makeMove(AIG);
		
//		while(!AIG.gameOver)
//		{
//			p1.makeMove(AIG);
//			moves++;
//			AIG.gameOver = AIG.isGameOver();
//			System.out.println(AIG.getBoard().toString());
//			if (AIG.gameOver) break;
//			p2.makeMove(AIG);
//			AIG.gameOver = AIG.isGameOver();
//			System.out.println(AIG.getBoard().toString());
//		}
//		System.out.println("game over in " + moves + " moves");
	}
}
