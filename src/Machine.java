import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Login extends JFrame implements ActionListener {
	private String password = "123";    // ��й�ȣ�� 123���� ���� 
	private JPasswordField passwordField;
	private JButton login;
	JLabel loginText = new JLabel();
	private boolean isLogin = false;    // �α����� �����ߴ��� �Ǻ�(�ʱⰪ�� ����)
	
	public Login() {
		JPanel passPanel = new JPanel();
		passwordField = new JPasswordField(10);
		loginText.setForeground(Color.RED);
		
		JLabel passLabel = new JLabel("PASSWORD");
		passLabel.setBounds(10, 10, 80, 25);
		login = new JButton("LOGIN");
		login.addActionListener(this);
		
		passPanel.add(passLabel);
		passPanel.add(passwordField);
		
		this.add(passPanel);
		this.add(login);
		this.add(loginText);
		
		setLayout(new FlowLayout());
		
		setTitle("LOGIN");
		setSize(300, 200);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e1) {
		if(e1.getSource() == login) {
			try {
				if(password.equals(passwordField.getText()))
					isLogin = true;
				else
					isLogin = false;
				if(isLogin) {
					loginText.setText("�α��εǾ����ϴ�. �ȳ��ϼ���.");
				}
				else {
					loginText.setText("PASSWORD�� �߸��Ǿ����ϴ�.");
				}
			} catch (Exception e2) {
				System.out.println("false");
			}
		}
	}
}

class Machine extends JFrame {    // �����Ű�� ����
	public Machine() {
		setTitle("Vending Machine");
		createMachineWindow();
		setSize(500,500);
		setVisible(true);
	}
	
	Menu menu = new Menu();
	
	public void createMachineWindow() {    // ���Ǳ� â ����
		JMenuBar menuBar = new JMenuBar();
		JMenuItem[] menuItem = new JMenuItem[3];
		String[] itemTitle = {"�޴� ����", "���� ����", "�ܵ� ����"};
		JMenu screenMenu = new JMenu("������");
		MenuActionListener listener = new MenuActionListener();
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(listener);
			screenMenu.add(menuItem[i]);
		}
		menuBar.add(screenMenu);
		setJMenuBar(menuBar);
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e4) {
			String cmd = e4.getActionCommand();
			
			switch(cmd) {
			case "�޴� ����":
				menu.createMenu();
				break;
			case "���� ����":
				break;
			case "�ܵ� ����":
				break;
			}
		}
	}
}