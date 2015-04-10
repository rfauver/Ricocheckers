
public class Main
{
	public static void main(String[] args) 
	{
		AIGame AIG = new AIGame();
		Player p1 = new AlphaBetaPlayer(1, 6);
		Player p2 = new AlphaBetaPlayer(2, 6);
		int times = 0;
		
		while(!AIG.gameOver)
		{
			if (times >= 25)
			{
				System.out.println();
			}
			p1.makeMove(AIG);
			AIG.gameOver = AIG.isGameOver();
			System.out.println(AIG.getBoard().toString());
			if (AIG.gameOver) break;
			p2.makeMove(AIG);
			AIG.gameOver = AIG.isGameOver();
			System.out.println(AIG.getBoard().toString());
			times++;
		}
	}
}
