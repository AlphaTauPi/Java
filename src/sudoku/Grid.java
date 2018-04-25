package sudoku;

import utilities.Arrays;

public class Grid {
	
	private int[][] validGrid;
	private int[][] invalidGrid;
	
	public Grid(GamePanel game) {
		validGrid = new int[9][9];
		invalidGrid = new int[9][9];
	}
	
	public int[][] getValidGrid() {
		return Arrays.clone2DArray(validGrid);
	}
	
	public int[][] getInvalidGrid() {
		return Arrays.clone2DArray(invalidGrid);
	}
	
	public int getValidTile(int row, int col) {
		return validGrid[row][col];
	}
	
	public int getInvalidTile(int row, int col) {
		return invalidGrid[row][col];
	}
	
	public void setTile(int row, int col, int number) {
		if(isValidNumber(row, col, number)) {
			validGrid[row][col] = number;
			invalidGrid[row][col] = 0;
		} else {
			validGrid[row][col] = 0;
			invalidGrid[row][col] = number;
		}
	}
	
	public void updateSquares() {
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				boolean validStatus = (validGrid[row][col] > 0) ? true : false;
				int number = (validStatus) ? validGrid[row][col] : invalidGrid[row][col];
				
				if(number > 0) {
					if(isValidNumber(row, col, number) != validStatus) {
						setTile(row, col, number);
					}
				}
			}
		}
	}
	
	private boolean isValidNumber(int row, int col, int number) {
		int[][] testGrid = getValidGrid();
		
		testGrid[row][col] = number;
		
		if(!isValidRow(testGrid, row)) {
			return false;
		}
		if(!isValidCol(testGrid, col)) {
			return false;
		}
		if(!isValidSquare(testGrid, row, col)) {
			return false;
		}
		
		return true;
	}
	
	private boolean isValidRow(int[][] testGrid, int row) {
		if(isUniqueSet(testGrid[row])) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isValidCol(int[][] testGrid, int col) {
		int[] columnSet = new int[9];
		
		for(int i = 0; i < 9; i++) {
			columnSet[i] = testGrid[i][col];
		}
		
		if(isUniqueSet(columnSet)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isValidSquare(int[][] testGrid, int row, int col) {
		int startRow = 0, startCol = 0;
		int[] squareSet = new int[9];
		
		Double rowDouble = Math.floor(row / 3) * 3;
		startRow = rowDouble.intValue();
		Double colDouble = Math.floor(col / 3) * 3;
		startCol = colDouble.intValue();
		
		for(int i = 0; i < 3; i++) {
			for(int k = 0; k < 3; k++) {
				squareSet[i * 3 + k] = testGrid[startRow + i][startCol + k];
			}
		}
		
		if(isUniqueSet(squareSet)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isUniqueSet(int[] set) {
		for(int i = 0; i < set.length - 1; i++) {
			for(int k = i + 1; k < set.length; k++) {
				if(set[i] > 0 && set[i] == set[k]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
}
