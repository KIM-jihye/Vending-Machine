import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public interface Window {
	void setDesign();
}

class ManageWindow implements Window {
	public void setDesign() {
		
	}
}

@SuppressWarnings("serial")
class UserWindow extends JFrame implements Window {
	MyActionListener barActionListener = new MyActionListener();
	MyActionListener btnActionListener = new MyActionListener();
	JTextField input500Field,input100Field,input50Field,input10Field;
	JButton btnInputMoney;
	Container c;
	
	public UserWindow() {
		setTitle("Vending Machine");
		c = this.getContentPane();
		setDesign();
		setSize(450,600);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setDesign() {
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setBackground(Color.LIGHT_GRAY);
		
		JPanel moneyInputPanel = new JPanel();
		moneyInputPanel.setLayout(new FlowLayout(20));
		moneyInputPanel.setBackground(Color.WHITE);
		
		JPanel moneyPanel = new JPanel();
		moneyPanel.setLayout(new BorderLayout(20,10));
		moneyPanel.setBackground(Color.WHITE);
		
		c.add(BorderLayout.CENTER, menuPanel);
		c.add(BorderLayout.SOUTH, moneyPanel);
		
		// 메뉴바 생성
		JMenuBar menuBar = new JMenuBar();
		JMenuItem[] menuItem = new JMenuItem[3];
		String[] itemTitle = {"메뉴 관리", "매출 관리", "잔돈 관리"};
		JMenu screenMenu = new JMenu("관리자");
		
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(barActionListener);
			screenMenu.add(menuItem[i]);
		}
		menuBar.add(screenMenu);
		setJMenuBar(menuBar);
		
		// 자판기 메뉴 창 생성
		this.printMenu(menuPanel);
		
		// USER 사용 창 생성
		JLabel printLabel = new JLabel(printMessage1);
		
		JLabel label500 = new JLabel("500원:");
		JLabel label100 = new JLabel("100원:");
		JLabel label50 = new JLabel("50원:");
		JLabel label10 = new JLabel("10원:");
		JTextField input500Field = new JTextField(3);
		JTextField input100Field = new JTextField(3);
		JTextField input50Field = new JTextField(3);
		JTextField input10Field = new JTextField(3);
		JButton btnInputMoney = new JButton("투입");
		btnInputMoney.setBackground(Color.WHITE);
		btnInputMoney.addActionListener(btnActionListener);
		
		moneyPanel.add(printLabel,"North");
		
		moneyInputPanel.add(label500);
		moneyInputPanel.add(input500Field);
		moneyInputPanel.add(label100);
		moneyInputPanel.add(input100Field);
		moneyInputPanel.add(label50);
		moneyInputPanel.add(input50Field);
		moneyInputPanel.add(label10);
		moneyInputPanel.add(input10Field);
		moneyInputPanel.add(btnInputMoney);
		
		moneyPanel.add(moneyInputPanel,"Center");
	}
	
	public void printMenu(JPanel panel) {
		
	}
}