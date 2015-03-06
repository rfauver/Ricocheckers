
public class Main
{

	public static void main(String[] args) 
	{
		TicTacToe ttt = new TicTacToe();
		ttt.emptyBoard();
		Player p1 = new AlphaBetaPlayer(1);
		Player p2 = new RandomPlayer(2);
		
		while(!ttt.gameOver)
		{
			p1.makeMove(ttt);
			ttt.gameOver = ttt.isGameOver();
			System.out.println(ttt.toString());
			if (ttt.gameOver) break;
			p2.makeMove(ttt);
			ttt.gameOver = ttt.isGameOver();
			System.out.println(ttt.toString());
		}
	}
}
