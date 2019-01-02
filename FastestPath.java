import java.util.*;

public class FastestPath 
{
	public static int rT, cT, rD, cD, rSize, cSize, movesMade;
	public static String bestMove = "";
	
	public static void main(String[] arg)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Row size of grid please:");
		rSize = Integer.parseInt(sc.nextLine());
		char[][] board = new char[rSize][];
		System.out.println("Grid please:");
		for(int i=0; i<rSize; i++)
		{
			String line = sc.nextLine(); 
			board[i] = line.toCharArray();
			for(int l=0; l<line.length();l++)
			{
				if(board[i][l] == 'T')
				{
					rT = i;
					cT = l;
				}
				else if(board[i][l] == 'D')
				{
					rD = i;
					cD = l;
				}
			}
		}
		cSize = board[0].length;
		System.out.println();
		board[rT][cT] = 'X';
		if(possible(board))
			move(rT, cT, "", board);
		
		if(bestMove.isEmpty())
			System.out.println("Impossible. The number of moves attempted: " + format(movesMade+""));
		else
			System.out.println("Moves: " + bestMove + " (" + bestMove.length() + ")\n" + "The number of moves attempted: " + format(movesMade+"") + "\n\n" + translate(board, bestMove, rT, cT));
	}
  
 	//F = foward, B = backward, L = left, R = right, M = mine, X = been
	//T = turtle, C = castle (can't move into), I = ice (one move to mine), D = diamond (goal destination). 
	/*
..........
.CT.......
.IC....C..
...I......
IC...II...
.CC.C.....
CI.C.C..IC
.C..C...C.
...I...D..
	 */
/*
....................
....II..............
....II..............
....TII.............
....III.............
.IIII.C.............
......I.............
....................
...CCC..C.C.........
....................
....................
....................
....................
................D...
....................
 */
	public static void move(int r, int c, String turns, char[][] board)
	{
		if(board[r][c] == 'D')
		{
			if(turns.length() < bestMove.length() || bestMove.isEmpty())
				bestMove = turns;
			return;
		}
		if(!bestMove.isEmpty() && turns.length() + Math.abs(r-rD) + Math.abs(c-cD) >= bestMove.length())
			return;
		if(r-1 >= 0 && board[r-1][c] != 'X' && board[r-1][c] != 'C')
			move(r-1, c , board[r-1][c] == 'I'? turns+"MF":turns+"F", setBoard(r-1, c, board));
		if(r+1 < rSize && board[r+1][c] != 'X' && board[r+1][c] != 'C')
			move(r+1, c, board[r+1][c] == 'I'? turns+"MB":turns+"B", setBoard(r+1, c, board));		
		if(c-1 >= 0 && board[r][c-1] != 'C' && board[r][c-1] != 'X')
			move(r, c-1 , board[r][c-1] == 'I'? turns+"ML":turns+"L", setBoard(r, c-1, board));	
		if(c+1 < cSize && board[r][c+1] != 'C' && board[r][c+1] != 'X')
			move(r, c+1 , board[r][c+1] == 'I'? turns+"MR":turns+"R", setBoard(r, c+1, board));

	}
	public static char[][] setBoard(int r, int c, char[][] board)
	{
		movesMade++;
		char[][] clone = new char[rSize][];
		for(int i=0;i<board.length;i++)
			clone[i] = board[i].clone();
		if(clone[r][c] == 'D')
			return clone;
		clone[r][c] = 'X';
		return clone;
	}
	public static boolean possible(char[][] board)
	{
		if(rD-1 >= 0 && board[rD-1][cD] != 'C')
			return true;
		if(rD+1 < rSize && board[rD+1][cD] != 'C')
			return true;	
		if(cD-1 >= 0 && board[rD][cD-1] != 'C')
			return true;
		if(cD+1 < cSize && board[rD][cD+1] != 'C')
			return true;
		return false;
	}
	public static String translate(char[][] board, String turns, int r, int c)
	{ 
		board[r][c] = 'T';
		for(int i=0; i<turns.length()-1; i++)
		{
			char l = turns.charAt(i);
			if(l == 'F')
			{
				board[r-1][c] = '|';
				r--;
			}
			else if(l == 'B')
			{
				board[r+1][c] = '|';
				r++;
			}
			else if(l == 'L')
			{
				board[r][c-1] = '-';
				c--;
			}
			else if(l == 'R')
			{
				board[r][c+1] = '-';
				c++;
			}
		}
		String complete = "";
		for(char[] line : board)
		{
			for(char l : line)
				complete += l;
			complete += "\n";
		}
		return complete;
	}
	public static String format(String a)
	{
		if(a.length() > 3)
		{
			int start = a.length()%3;
			if(start == 0)
				start = 3;
			String formatted = a.substring(0, start);
			for(int i=start; i<a.length(); i+=3)
				formatted += "," + a.substring(start, start+=3);
			return formatted;
			
		}
		return a;
	}
}










