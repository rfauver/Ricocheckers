
public class Main
{
	public static void main(String[] args) 
	{

		// Change to HumanPlayer to play game
		Player p1 = new AlphaBetaPlayer(1, 4);
		ReinforcementLearnerPlayer p2 = new ReinforcementLearnerPlayer(2);
		int moves = 0;
		int p1Wins = 0;
		int p2Wins = 0;
		int draws = 0;
		int p1WinMoves = 0;
		int p2WinMoves = 0;
			
		// control number of games played
		for (int i = 0; i < 100; i++)
		{
			Ricocheckers g = new Ricocheckers();
			moves = 0;
			while(!g.gameOver)
			{
				p1.makeMove(g);
				moves++;
				g.gameOver = g.isGameOver();
				System.out.println(g.getBoard().toString());
				if (g.gameOver) break;
				p2.makeMove(g);
				g.gameOver = g.isGameOver();
				System.out.println(g.getBoard().toString());
			}
			if (g.winner == 1)
			{
				p1Wins++;
				p1WinMoves += moves;
			}
			else if (g.winner == 2)
			{
				p2Wins++;
				p2WinMoves += moves;
			}
			else
			{
				draws++;
			}
		}
		System.out.println("Player 1 won " + p1Wins + " times with an average of " + ((double)p1WinMoves/(double)p1Wins) + " moves per win");
		System.out.println("Player 2 won " + p2Wins + " times with an average of " + ((double)p2WinMoves/(double)p2Wins) + " moves per win");
		System.out.println("There were " + draws + " draws");
	}
}
