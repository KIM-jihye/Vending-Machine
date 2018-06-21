import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		String menuBar = e.getActionCommand();
		Object button = e.getSource();
		
		switch(menuBar) {
		
		case "皋春 包府":
			break;
			
		case "概免 包府":
			break;
			
		case "儡捣 包府":
			break;
			
		}
		
		for(int i=0; i<12; i++) {
			if(button == menuButton[i]) {
				
			}
		}
		if(button == btnInputMoney) {
			
		}
		
	}
}
