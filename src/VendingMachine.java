import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class VendingMachine {
	int capacity=12;
	
	boolean Login() {
		private String password = "123";    // ��й�ȣ�� 123���� ���� 
		private JPasswordField passwordField;
		private JButton login;
		JLabel loginText = new JLabel();
		private boolean isLogin = false;    // �α����� �����ߴ��� �Ǻ�(�ʱⰪ�� ����)
		int num=0;    // num���� ���� �޴�����,�ܾװ���,��������� ����
		
		num = managerNum;
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
}
