package application.model;

public class Board {

	private int[] controlTab;
	private int[] board;
	private int movesMade;
	
	public Board() {
		board = new int[9];
		controlTab = new int[8];
		movesMade = 0;
	}
	
	public int makeMove(int cell, int sign) {
		board[cell - 1] = sign;
		movesMade++;
		
		controlTab[(cell - 1) / 3] += sign;
		controlTab[((cell - 1) % 3) + 3] += sign;
		
		if ((cell - 1) % 2 == 0) {
			if (cell == 5) {
				controlTab[6] += sign;
				controlTab[7] += sign;
			}
			else if ((cell - 1) % 4 == 0)
				controlTab[6] += sign;
			else
				controlTab[7] += sign;
		}
		
		for (int i = 0; i < controlTab.length; i++)
			if (Math.abs(controlTab[i]) == 3)
				return 1;
		
		if (movesMade == 9)
			return 2;
		
		return 0;
	}
	
	public int[] copyControlTab() {
		return controlTab.clone();
	}
	
	public int[] copyBoard() {
		return board.clone();
	}
}
