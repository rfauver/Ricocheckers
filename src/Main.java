
public class Main
{

	public static void main(String[] args) 
	{
//		AIGame AIG = new AIGame();
//		Player p1 = new AlphaBetaPlayer(1, 10);
//		Player p2 = new RandomPlayer(2);
//		
//		while(!AIG.gameOver)
//		{
//			p1.makeMove(AIG);
//			AIG.gameOver = AIG.isGameOver();
//			System.out.println(AIG.getBoard().toString());
//			if (AIG.gameOver) break;
//			p2.makeMove(AIG);
//			AIG.gameOver = AIG.isGameOver();
//			System.out.println(AIG.getBoard().toString());
//		}
		
		AIGame g = new AIGame();
		
		System.out.println(g.getBoard().toString());
		

		g.makeMove(g.getPossibleMoves(1)[0], 1);
		g.makeMove(g.getPossibleMoves(1)[2], 1);
		g.makeMove(g.getPossibleMoves(1)[0], 1);
		g.makeMove(g.getPossibleMoves(1)[1], 1);
		g.makeMove(g.getPossibleMoves(1)[0], 1);
		g.makeMove(g.getPossibleMoves(1)[0], 1);
		System.out.println(g.getBoard().toString());

		

		System.out.println(g.gameValue(1));
		

	}
}
