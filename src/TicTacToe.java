import java.util.*;

public class TicTacToe implements Game {

	public boolean gameOver;
	public int winner; //0 for no winner, 1 for player 1, 2 for player 2
	
	public int[][] board;
	private Stack<Move> moveStack;
	
	public TicTacToe()
	{
		gameOver = false;
		winner = 0;
		board = new int[3][3];
		moveStack = new Stack<Move>();
	}
	
	public boolean isGameOver() 
	{
		for (int i = 0; i < board.length; i++)
		{
			if (board[i][0] != 0)
			{
				for (int j = 1; j < board[i].length; j++)
				{
					if (board[i][0] != board[i][j])
						break;
					else if (j == board[i].length - 1)
					{
						winner = board[i][0];
						gameOver = true;
						return true;
					}
				}
			}
			if (board[0][i] != 0)
			{
				for (int j = 1; j < board.length; j++)
				{
					if (board[0][i] != board[j][i])
						break;
					else if (j == board.length - 1)
					{
						winner = board[0][i];
						gameOver = true;
						return true;
					}
				}
			}
		}
		if (board[0][0] != 0)
		{
			for (int i = 0; i < board.length; i++)
			{
				if (board[0][0] != board[i][i])
					break;
				else if (i == board.length - 1)
				{
					winner = board[0][0];
					gameOver = true;
					return true;
				}
			}
		}
		if (board[0][board.length-1] != 0)
		{
			for (int i = 0; i < board.length; i++)
			{
				if (board[0][board.length-1] != board[i][board.length-1-i])
					break;
				else if (i == board.length - 1)
				{
					winner = board[0][board.length-1];
					gameOver = true;
					return true;
				}
			}
		}	
		if (getPossibleMoves(0)[0] == null)
		{
			winner = 0;
			gameOver = true;
			return true;
		}
		return false;
	}

	public double gameValue(int player) 
	{
		if (!gameOver || winner == 0) return 0;
		if (winner == player) return 1.0;
		else return -1.0;
	}

	public Move[] getPossibleMoves(int player) 
	{
		Move[] moves = new Move[board.length*board[0].length];
		int count = 0;
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if (board[i][j] == 0)
				{
					moves[count] = new Move(new IntVector2(i, j), null, null);
					count++;
				}
			}
		}
		return moves;
	}

	public void makeMove(Move m, int p) 
	{
		board[m.destination.x][m.destination.z] = p;
		moveStack.push(m);
	}

	public void undoMove() 
	{
		if (moveStack.isEmpty()) { return; }
		if (gameOver) gameOver = false;
		winner = 0;
		Move m = moveStack.pop();
		board[m.destination.x][m.destination.z] = 0;
	}
	
	public Board getBoard()
	{
		try { throw new Exception("Don't call this method"); } 
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public void emptyBoard()
	{
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				board[i][j] = 0;
			}
		}
	}

	public String toString()
	{
		String outString = "";
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				switch(board[i][j])
				{
				case 0: outString += "   ";
					break;
				case 1: outString += " X ";
					break;
				case 2: outString += " O ";
					break;
				default: outString += "   ";
				}
				if (j != board[i].length-1)
				{
					outString += "|";
				}
				else outString += "\n";
			}
			if (i != board.length-1)
			{
				outString += "---+---+---\n";
			}
			else outString += "\n";
		}
		if (gameOver)
		{
			if (winner == 0) outString += "Tie!\n";
			else outString += "Player " + winner + " Wins!\n";
		}
		return outString;
	}
}
