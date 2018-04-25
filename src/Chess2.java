import java.util.Scanner;
import java.util.Random;

// A rudimentary Chess game!
public class Chess2 {
	public static String chessBoard[][] = new String[9][9];
	public static boolean inCheckW, inCheckB, gameOver, canMove, tempCheck ,pawnMove, enPassant = false;
	public static boolean bKingLeft = true, bKingRight = true, wKingLeft = true, wKingRight = true, castle = false;
	public static int r, c, newR, newC, turn = 1, pieceCounterB, pieceCounterW, enPassantR, enPassantC, enPassantTurn; 
	public static Scanner input = new Scanner(System.in);
	public static Random number = new Random ();
	
	public static int iterations = 0;
	
	public static void main(String[] BiteMe){
		
		String firstRow[] = {null,"B RK", "B KT", "B BP", "B QN", "B KG", "B BP", "B KT", "B RK"};
		String secondRow[] = {null,"B PN", "B PN", "B PN", "B PN", "B PN", "B PN", "B PN", "B PN",};
		String seventhRow[] = {null,"W RK", "W KT", "W BP", "W QN", "W KG", "W BP", "W KT", "W RK"};
		String eighthRow[] = {null,"W PN", "W PN", "W PN", "W PN", "W PN", "W PN", "W PN", "W PN"};
		
		System.out.println();
		chessBoard[1] = firstRow; chessBoard[2] = secondRow;
		chessBoard[8] = seventhRow; chessBoard[7] = eighthRow;
		
		for (int row = 3; row < 7; row ++){
			for (int column = 1; column < 9; column++){
				chessBoard[row][column] = "    ";	
			}
		}
		System.out.println("\t\t    Welcome To Simple Chess!!");
		System.out.println("\t     Enter the numbers 1-8 for Row and Column");
		System.out.println();
		while (gameOver != true){
			display();
			//chessAI();
			setupPiece();
		}
		display();
		System.out.println();
		System.out.println("**CHECK MATE**");
	}
	// Displays the board
	public static void display(){
		System.out.println("       C1     C2     C3     C4     C5     C6     C7     C8  ");
		System.out.println("     ______ ______ ______ ______ ______ ______ ______ ______");
		for (int row = 1; row < chessBoard.length; row++){
			System.out.println("    |      |      |      |      |      |      |      |      |");
			System.out.print("R" + row + "  ");
			for (int column = 1; column < chessBoard[row].length; column++){
				System.out.print("| " + chessBoard[row][column] + " ");
			}
			System.out.println("| ");
			System.out.println("    |______|______|______|______|______|______|______|______|");
		}
	}
	// Moves the piece from it's row/column to it's new row/column
	public static void setupPiece(){
		System.out.println();
		if(turn % 2 == 0){
			System.out.println("Turn " + turn + ": Black's Move");
		}else{System.out.println("Turn " + turn + ": White's Move");}
		
		System.out.println("What piece do you want to move?");
		System.out.print("Row: ");
		r = input.nextInt();   
		System.out.print("Column: ");
		c = input.nextInt();
		System.out.println("Where do you want to move it");
		System.out.print("Row: ");
		newR = input.nextInt();
		System.out.print("Column: ");
		newC = input.nextInt();
		
		if (chessBoard[r][c] != "    " && chessBoard[r][c] != chessBoard[newR][newC]){
			if ((turn % 2 == 0 && color(r, c) == "black") || (turn % 2 == 1 && color(r, c) == "white")){
				rules();
				
				if (canMove == true){
					chessBoard[newR][newC] = chessBoard[r][c]; chessBoard[r][c]= "    ";
					checkPiece();
					castling();
					if (inCheckB == true && turn % 2 == 0){
						chessBoard[r][c] = chessBoard[newR][newC]; chessBoard[newR][newC]= "    ";
						System.out.println("**You Cannot Move There If Put In Check**");
					}else{
						if (inCheckW == true && turn % 2 == 1){
							chessBoard[r][c] = chessBoard[newR][newC]; chessBoard[newR][newC]= "    ";
							System.out.println("**You Cannot Move There If Put In Check**");
						}else{
						if (inCheckB == true && turn % 2 == 1 || inCheckW == true && turn % 2 == 0){
							System.out.println("**CHECK**");
							}
							if (inCheckB == true || inCheckW == true || pieceCounterB < 2 || pieceCounterW < 2){
								checkGame();
								
							}
							if (inCheckB == false && turn % 2 == 0 || inCheckW == false && turn % 2 == 1){
								turn++;
							}
						}
					}
				}else{System.out.println("**Illegal Move**");}
				
			}else{System.out.println("**It is not " + color(r, c) + "'s turn**");}
		}else{System.out.println("**Illegal Move**");}
		System.out.println();
		
		System.out.println("Iterations: " + iterations + "\n");
		iterations = 0;
	}
	//The rules for each piece
	public static void rules(){
		canMove = false;
		
		iterations++;
		
		if (chessBoard[1][5] != "B KG"){
			bKingLeft = false; bKingRight = false;
		}
		if (chessBoard[8][5] != "W KG"){
			wKingLeft = false; wKingRight = false;
		}
		if (chessBoard[1][1] != "B RK"){
			bKingLeft = false;
		}
		if (chessBoard[1][8] != "B RK"){
			bKingRight = false;
		}
		if (chessBoard[8][1] != "W RK"){
			wKingLeft = false;
		}
		if (chessBoard[8][8] != "W RK"){
			wKingRight = false;
		}
		
		if (chessBoard[r][c] == "B RK" || chessBoard[r][c] == "W RK"){
			if (newR == r || newC == c){
				move();
			}
		}
		
		if (chessBoard[r][c] == "B KT" || chessBoard[r][c] == "W KT"){
			if((color(r, c) == "black" && color(newR, newC) != "black") || (color(r, c) == "white" && color(newR, newC) != "white")){
				if ((newR-r==2&&newC-c==1) || (newR-r==2&&newC-c==-1) || (newR-r==1&&newC-c==2) || (newR-r==1&&newC-c==-2)){
					canMove = true;
				}else{
					if ((newR-r==-2&&newC-c==1) || (newR-r==-2&&newC-c==-1) || (newR-r==-1&&newC-c==2) || (newR-r==-1&&newC-c==-2)){
						canMove = true;
					}
				}
			}
		}
		
		if (chessBoard[r][c] == "B BP" || chessBoard[r][c] == "W BP"){
			if (newR - r == newC - c || newR - r == -newC + c){
				move();
			}
		}
		
		if (chessBoard[r][c] == "B QN" || chessBoard[r][c] == "W QN"){
			if (newR - r == newC - c || newR - r == -newC + c || newR == r || newC == c){
				move();
			}
		}
		
		if (chessBoard[r][c] == "B KG" || chessBoard[r][c] == "W KG"){
			if ((newR - r) <= 1 && (newC - c) <= 1 && (newR - r) >= -1 && (newC - c) >= -1){
				move();
			}
			if (chessBoard[r][c] == "B KG"){
				if (newR == 1 && newC == 3 && bKingLeft == true){
					if (chessBoard[1][2] == "    " && chessBoard[1][3] == "    " && chessBoard[1][4] == "    "){
						canMove = true; castle = true;
					}
				}
				if (newR == 1 && newC == 7 && bKingRight == true){
					if (chessBoard[1][6] == "    " && chessBoard[1][7] == "    "){
						canMove = true; castle = true;
					}
				}
			}
			if (chessBoard[r][c] == "W KG"){
				if (newR == 8 && newC == 3 && wKingLeft == true){
					if (chessBoard[8][2] == "    " && chessBoard[8][3] == "    " && chessBoard[8][4] == "    "){
						canMove = true; castle = true;
					}
				}
				if (newR == 8 && newC == 7 && wKingRight == true){
					if (chessBoard[8][6] == "    " && chessBoard[8][7] == "    "){
						canMove = true; castle = true;
					}
				}
			}
		}
		
		if (chessBoard[r][c] == "B PN"){
			if (enPassant == true && pawnMove == true && enPassantTurn+1 == turn){
				if (newR == enPassantR+1 && newC == enPassantC){
					canMove = true;
					chessBoard[enPassantR][enPassantC] = "    ";
					enPassant = false;
				}
			}
			if((r == 2 && newR - r < 3)  && (newC == c && newR != 1)){
				if(newR == r+1 && chessBoard[newR][newC] == "    " || newR == r+2 && chessBoard[r+2][c] == "    "){
					canMove = true;
					if (newR == r+2 && pawnMove == true){
						enPassant = true; enPassantR = newR; enPassantC = newC;
						enPassantTurn = turn;
					}
				}
			}
			if(newR - r == 1 && newC == c){
				if(chessBoard[r+1][c] == "    "){
					canMove = true;
				}
			}
			if(color(newR, newC) == "white"){
				if (c < 8 && chessBoard[newR][newC] == chessBoard[r+1][c+1]){
					canMove = true;
				}
				
				if (c > 1 && chessBoard[newR][newC] == chessBoard[r+1][c-1]){
					canMove = true;
				}
			}
			if (newR == 8 && canMove == true && pawnMove == true){
				System.out.println("**What piece do you want?**");
				System.out.println("Rook = 1, Knight = 2, Bishop = 3, Queen = 4");
				int newPiece = input.nextInt();
				
				switch(newPiece){
				case 1: 
					chessBoard[r][c] = "B RK";
					canMove = true;
					break;
				case 2:
					chessBoard[r][c] = "B KT";
					canMove = true;
					break;
				case 3:
					chessBoard[r][c] = "B BP";
					canMove = true;
					break;
				case 4:
					chessBoard[r][c] = "B QN";
					canMove = true;
					break;
				}
			}
		}
		
		if (chessBoard[r][c] == "W PN"){
			if (enPassant == true && pawnMove == true && enPassantTurn+1 == turn){
				if (newR == enPassantR-1 && newC == enPassantC){
					canMove = true;
					chessBoard[enPassantR][enPassantC] = "    ";
					enPassant = false;
				}
			}
			if((r == 7 && newR - r > -3) && (newC == c && newR != 8)){
				if((newR == r-1 && chessBoard[r-1][c] == "    ") || (newR == r-2 && chessBoard[r-2][c] == "    ")){
					canMove = true;
					if (newR == r-2 && pawnMove == true){
						enPassant = true; enPassantR = newR; enPassantC = newC;
						enPassantTurn = turn;
					}
				}
			}
			if(newR - r == -1 && newC == c){
				if(chessBoard[r-1][c] == "    "){
					canMove = true;
				}
			}
			if(color(newR, newC) == "black"){
				if (c < 8 && chessBoard[newR][newC] == chessBoard[r-1][c+1]){
					canMove = true;
				}
				
				if (c > 1 && chessBoard[newR][newC] == chessBoard[r-1][c-1]){
					canMove = true;
				}
			}
			if(newR == 1 && canMove == true && pawnMove == true){
				System.out.println("**What piece do you want?**");
				System.out.println("Rook = 1, Knight = 2, Bishop = 3, Queen = 4");
				int newPiece = input.nextInt();
				
				switch(newPiece){
				case 1: 
					chessBoard[r][c] = "W RK";
					canMove = true;
					break;
				case 2:
					chessBoard[r][c] = "W KT";
					canMove = true;
					break;
				case 3:
					chessBoard[r][c] = "W BP";
					canMove = true;
					break;
				case 4:
					chessBoard[r][c] = "W QN";
					canMove = true;
					break;
				}
			}
		}
	}
	
	public static void move(){
		int block = 0;
		int counterX, counterY;
		counterX = r; counterY = c;
		
		iterations++;
		
		while (newR != counterX || newC != counterY){
			if (counterX < newR){
				counterX++;
			}
			if (counterX > newR){
				counterX--;
			}
			if (counterY < newC){
				counterY++;
			}
			if (counterY > newC){
				counterY--;
			}
			if (chessBoard[counterX][counterY] != "    "){
				block++;
			}
		}
		if ((chessBoard[r][c] == "B RK") || (chessBoard[r][c] == "B BP") || (chessBoard[r][c] == "B QN") || (chessBoard[r][c] == "B KG")){
			if (color(newR, newC) == "white"){
				block--;
			}
		}
		if ((chessBoard[r][c] == "W RK") || (chessBoard[r][c] == "W BP") || (chessBoard[r][c] == "W QN") || (chessBoard[r][c] == "W KG")){
			if (color(newR, newC) == "black"){
				block--;
			}
		}
		if (block < 1){
			canMove = true;
		}
	}
	
	public static String color(int x, int y){
		String theColor = "    ";
		if (chessBoard[x][y] == "W PN" || chessBoard[x][y] == "W RK" || chessBoard[x][y] == "W KT" || 
			chessBoard[x][y] == "W BP" || chessBoard[x][y] == "W QN" || chessBoard[x][y] == "W KG"){
			theColor = "white";
		}else{
			if (chessBoard[x][y] == "B PN" || chessBoard[x][y] == "B RK" || chessBoard[x][y] == "B KT" || 
					chessBoard[x][y] == "B BP" || chessBoard[x][y] == "B QN" || chessBoard[x][y] == "B KG"){
				theColor = "black";
			}
		}
		return theColor;
	}
	
	public static void checkPiece(){
		int tempR = r, tempC = c, tempNewR = newR, tempNewC = newC;
		inCheckB = false; inCheckW = false; pawnMove = false;
		pieceCounterB = 0; pieceCounterW = 0;
		
		iterations++;
		
		for(int row = 1; row < chessBoard.length; row++){
			for (int column = 1; column < chessBoard[row].length; column++){
				
				if (color(row, column) == "black"){
					pieceCounterB++;
				}else{
					if (color(row, column) == "white"){
						pieceCounterW++;
					}
				}
				
				for (int newRow = 1; newRow < chessBoard.length; newRow++){
					for (int newColumn = 1; newColumn < chessBoard[newRow].length; newColumn++){
						r = row; c = column; newR = newRow; newC = newColumn;
						rules();
						
						if (chessBoard[r][c] != "B KG" && chessBoard[newR][newC] == "B KG" && canMove == true){
							inCheckB = true;
						}
						if (chessBoard[r][c] != "W KG" && chessBoard[newR][newC] == "W KG" && canMove == true){
							inCheckW = true;
						}
					}
				}		
			}
		}
		pawnMove = true;
		r = tempR; c = tempC; newR = tempNewR; newC = tempNewC;
	}
	
	public static void castling(){
		if (inCheckB == false && castle == true && chessBoard[1][3] == "B KG" && turn % 2 == 0){
			chessBoard[1][4] = "B RK";  chessBoard[1][1] = "    ";
			castle = false;
		}
		if (inCheckB == false && castle == true && chessBoard[1][7] == "B KG" && turn % 2 == 0){
			chessBoard[1][6] = "B RK";  chessBoard[1][8] = "    ";
			castle = false;
		}
		if (inCheckW == false && castle == true && chessBoard[8][3] == "W KG" && turn % 2 == 1){
			chessBoard[8][4] = "W RK";  chessBoard[8][1] = "    ";
			castle = false;
		}
		if (inCheckW == false && castle == true && chessBoard[8][7] == "W KG" && turn % 2 == 1){
			chessBoard[8][6] = "W RK";  chessBoard[8][8] = "    ";
			castle = false;
		}
	}
	
	public static void checkGame() {
		int tempRow = r, tempCol = c, tempNewRow = newR, tempNewCol = newC;
		int checkMate = 0;
		pawnMove = false;
		
		for (int row = 1; row < chessBoard.length; row++){
			for (int column = 1; column < chessBoard[row].length; column++){
				
				if (color(row, column) == "black" && turn % 2 == 1 || color(row, column) == "white" && turn % 2 == 0){
					
					for (int newRow = 1; newRow < chessBoard.length; newRow++) {
						for (int newColumn = 1; newColumn < chessBoard[newRow].length; newColumn++){
							
							iterations++;
							
							r = row; c = column; newR = newRow; newC = newColumn;
							rules();
							if (canMove == true && chessBoard[r][c] != chessBoard [newR][newC]){
								String tempPiece = chessBoard[newR][newC];
								chessBoard[newR][newC] = chessBoard[r][c]; chessBoard[r][c]= "    ";
								checkPiece();
								chessBoard[r][c] = chessBoard[newR][newC]; chessBoard[newR][newC]= tempPiece;
								if (inCheckB == false && turn % 2 == 1 || inCheckW == false && turn % 2 == 0){
									checkMate++;
								}
							}
						}
					}
				}
			}
		}
		pawnMove = true;
		r = tempRow; c = tempCol; newR = tempNewRow; newC = tempNewCol;
		if (checkMate < 1) {
			gameOver = true;
		}
	}
	
	/*public static void chessAI () {
		int blackNumber = 0;
		
		for (int row = 1; row < chessBoard.length; row++){
			for (int column = 1; column < chessBoard[row].length; column++){
				
				if (color(row, column) == "black"){
					blackNumber++;
				}
			}
		}
		int blackPosition[][] = new int[blackNumber+1][2];
		blackNumber = 0;
		
		for (int row = 1; row < chessBoard.length; row++){
			for (int column = 1; column < chessBoard[row].length; column++){
				
				if (color(row, column) == "black"){
					
					for (int newRow = 1; newRow < chessBoard.length; newRow++) {
						for (int newColumn = 1; newColumn < chessBoard[newRow].length; newColumn++){
							
						}
					}
				}
			}
		}
		int blackPiece = number.nextInt(blackNumber);
	}*/
}