package sudoku;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class NumbersPanel extends JPanel {

	private static final long serialVersionUID = 4049423533237095214L;
	
	public NumbersPanel(JFrame main) {
		main.add(this);
		this.setSize(300, 600);
		this.setLocation(620, 10);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}
	
}
