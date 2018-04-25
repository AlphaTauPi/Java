package sudoku;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private static final long serialVersionUID = -5791741981137853059L;
	private static final int X_OFFSET = 16, Y_OFFSET = 38;
	
	public Main() {
		
	}
	
	public void initialize() {
		this.setTitle("Sudoku Solver");
		this.setSize(610 + X_OFFSET, 610 + Y_OFFSET);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setLayout(null);
		
		new GamePanel(this);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.initialize();
	}
	
}
