package application.model;

public class GameAI {
	
	int sign;

	public GameAI(int Sign) {
		sign = Sign;
	}
	
	public int makeMove(int[] board, int[] controlTab) {
		int index = 0;
		int best = -1;
		
		for (int i = controlTab.length - 1; i >= 0 ; i--) {
			int priority = priority(controlTab[i]);
			
			if (priority > best) {
				best = priority;
				index = i;
			}
				
		}
		
		if (board[4] == 0)
			return 4;
		
		if (index < 3) {
			if (board[index * 3] == 0)
				return index * 3;
			else if (board[index * 3 + 1] == 0)
				return index * 3 + 1;
			else if (board[index * 3 + 2] == 0)
				return index * 3 + 2;
		}
		else if (index < 6) {
			if (board[index - 3] == 0)
				return index - 3;
			else if (board[index] == 0)
				return index;
			else if (board[index + 3] == 0)
				return index + 3;
		}
		else {
			if (index == 6) {
				if (board[0] == 0)
					return 0;
				else if (board[8] == 0)
					return 8;
			}
			else {
				if (board[2] == 0)
					return 2;
				else if (board[6] == 0) 
					return 6;
			}
		}
		
		return findFreeCell(board);
	}
	
	private int priority(int value) {
		int priority = -1;
		
		switch(value) {
		case 2:
		case -2:
			if (value * sign > 0)
				priority = 4;
			else 
				priority = 3;
			break;
		case 1:
		case -1:
			if (value * sign > 0)
				priority = 2;
			else 
				priority = 1;
			break;
		case 0:
			priority = 0;
			break;
		}
		
		return priority;
	}
	
	int findFreeCell(int[] board) {
		for (int i = 0; i < board.length; i++)
			if (board[i] == 0)
				return i;
		
		return -1;
	}
	
}
