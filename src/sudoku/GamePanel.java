package sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class GamePanel extends JPanel implements MouseListener, KeyListener {

	private static final long serialVersionUID = -2152501557423073881L;
	private static final int WIDTH = 600, HEIGHT = 600;
	private static final int OFFSET = 30;
	
	private boolean squareSelected = false;
	private int squareSize;
	private int selRow, selCol;
	
	private Grid grid;
	
	public GamePanel(JFrame main) {
		main.add(this);
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(10, 10);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
		
		grid = new Grid(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawGrid(g2);
		if(grid != null) {
			drawNumbers(g2);
			if(squareSelected) {
				drawSelectedTile(g2);
			}
		}
	}
	
	private void drawGrid(Graphics2D g2) {
		int x1, x2, y1, y2;
		squareSize = (WIDTH - OFFSET * 2) / 9;
		
		for(int i = 0; i <= 9; i++) {
			// horizontal
			x1 = OFFSET + ((i == 3 || i == 6) ? 2 : 0);
			x2 = WIDTH - OFFSET - ((i == 3 || i == 6) ? 2 : 0);
			y1 = OFFSET + squareSize * i;
			y2 = y1;
			
			g2.setStroke(new BasicStroke((i == 3 || i == 6) ? 6 : 2));
			g2.drawLine(x1, y1, x2, y2);
			
			// vertical
			x1 = OFFSET + squareSize * i;
			x2 = x1;
			y1 = OFFSET + ((i == 3 || i == 6) ? 2 : 0);
			y2 = HEIGHT - OFFSET - ((i == 3 || i == 6) ? 2 : 0);
			
			g2.setStroke(new BasicStroke((i == 3 || i == 6) ? 6 : 2));
			g2.drawLine(x1, y1, x2, y2);
		}
		
		// Reset the stroke for the component
		g2.setStroke(new BasicStroke(1));
	}
	
	private void drawNumbers(Graphics2D g2) {
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				int number = 0;
				
				
				if(grid.getValidTile(row, col) > 0) {
					g2.setColor(Color.BLACK);
					number = grid.getValidTile(row, col);
				} else if(grid.getInvalidTile(row, col) > 0) {
					g2.setColor(Color.RED);
					number = grid.getInvalidTile(row, col);
				}
				
				if(number > 0) {
					int x = OFFSET + 22 + squareSize * col;
					int y = OFFSET + 43 + squareSize * row;
					
					g2.setFont(new Font("Arial", Font.CENTER_BASELINE, 35));
					g2.drawString(Integer.toString(number), x, y);
					g2.setColor(Color.BLACK);
				}
			}
		}
	}
	
	private void drawSelectedTile(Graphics2D g2) {
		int x = (selCol * squareSize) + OFFSET + 1;
		int y = (selRow * squareSize) + OFFSET + 1;
		int width = squareSize - 2; int height = squareSize - 2;
		
		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(6));
		g2.drawRect(x, y, width, height);
		
		// Reset the stroke for the component
		g2.setStroke(new BasicStroke(1));
	}

	public void mouseClicked(MouseEvent me) {}
	
	public void mouseEntered(MouseEvent me) {}
	
	public void mouseExited(MouseEvent me) {}
	
	public void mouseReleased(MouseEvent me) {}
	
	public void mousePressed(MouseEvent me) {
		int x = me.getX(); int y = me.getY();
		int row = selRow; int col = selCol;
		boolean tileSelected = this.squareSelected;
		
		if((x >= OFFSET) && (x <= WIDTH - OFFSET) && (y >= OFFSET) && (y <= HEIGHT - OFFSET)) {
			row = (y - OFFSET) / squareSize;
			col = (x - OFFSET) / squareSize;
			
			if(tileSelected) {
				if(row == selRow && col == selCol) {
					this.squareSelected = false;
				}
			} else {
				this.squareSelected = true;
			}
			
			if(tileSelected != this.squareSelected || row != selRow || col != selCol) {
				this.repaint();
			}
			
			selRow = row; selCol = col;
		}
	}

	public void keyReleased(KeyEvent ke) {}

	public void keyTyped(KeyEvent ke) {}
	
	public void keyPressed(KeyEvent ke) {
		int keyCode = ke.getKeyCode();
		int number = 0;
		
		if(squareSelected) {
			if(keyCode >= 48 && keyCode <= 57) {
				number = keyCode - 48;
			} else if(keyCode >= 96 && keyCode <= 105) {
				number = keyCode - 96;
			}
			
			if(number != 0) {
				grid.setTile(selRow, selCol, number);
				grid.updateSquares();
			}
			
			advanceSquare();
		}
	}
	
	public void advanceSquare() {
		if(selCol < 8) {
			selCol++;
		} else if(selRow < 8) {
			selRow++;
			selCol = 0;
		} else {
			squareSelected = false;
		}
		
		repaint();
	}
}
