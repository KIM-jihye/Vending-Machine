import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class VendingMachine {
	int capacity=12;
	
	boolean Login() {
		private String password = "123";    // 비밀번호를 123으로 설정 
		private JPasswordField passwordField;
		private JButton login;
		JLabel loginText = new JLabel();
		private boolean isLogin = false;    // 로그인이 성공했는지 판별(초기값은 실패)
		int num=0;    // num값에 따라 메뉴관리,잔액관리,매출관리로 나뉨
		
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
