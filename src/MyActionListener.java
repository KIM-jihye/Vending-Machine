import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		String menuBar = e.getActionCommand();
		Object button = e.getSource();
		
		switch(menuBar) {
		
		case "�޴� ����":
			break;
			
		case "���� ����":
			break;
			
		case "�ܵ� ����":
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
