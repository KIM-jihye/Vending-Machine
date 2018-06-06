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
	int num=0;
	
	public Login(int managerNum) {
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

	public boolean getLogin() {
		return isLogin;
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
					switch(num) {
					case 1:
						Menu menu = new Menu("�޴� ����");
						break;
					case 2:
						Sales sales = new Sales("���� ����");
						break;
					case 3:
						Money money = new Money();
						money.addMoney();
						break;
					}
				}
				else {
					loginText.setText("PASSWORD�� �߸��Ǿ����ϴ�.");
				}
				passwordField.setText("");
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createMachineWindow() {    // ���Ǳ� â ����
		JMenuBar menuBar = new JMenuBar();
		JMenuItem[] menuItem = new JMenuItem[3];
		String[] itemTitle = {"�޴� ����", "���� ����", "�ܵ� ����"};
		JMenu screenMenu = new JMenu("������");
		Container c = this.getContentPane();
		JPanel panel = new JPanel();
		
		MenuActionListener listener = new MenuActionListener();
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(listener);
			screenMenu.add(menuItem[i]);
		}
		menuBar.add(screenMenu);
		setJMenuBar(menuBar);
		
		this.printMenu(panel);
		c.add(panel);
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e4) {
			String cmd = e4.getActionCommand();
			Login login;
			
			switch(cmd) {
			case "�޴� ����":
				login = new Login(1);
				break;
			case "���� ����":
				login = new Login(2);
				break;
			case "�ܵ� ����":
				login = new Login(3);
				break;
			}
		}
	}
	
	public void printMenu(JPanel panel) {
		FileReader fr = null;
		BufferedReader br = null;
		JButton menuButton;
		try {
			fr = new FileReader("./menu.dat");
			br = new BufferedReader(fr);
			int row = Integer.parseInt(br.readLine());
			int col = Integer.parseInt(br.readLine());
			String[] str = new String[col];
			for(int i=0; i<row; i++) {
				for(int j=0; j<col; j++) {
					String data = br.readLine();
					str[j] = data;
				}
				menuButton = new JButton(str[0]);
				panel.add(menuButton);
			}
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
		} catch(NumberFormatException e1) {
			e1.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
	}
}