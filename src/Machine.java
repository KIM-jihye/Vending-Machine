import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
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
	
	@SuppressWarnings("deprecation")
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
						new Menu("�޴� ����");
						break;
					case 2:
						new Sales("���� ����");
						break;
					case 3:
						new Money("�ܵ� ����");
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

@SuppressWarnings("serial")
class Machine extends JFrame {    // �����Ű�� ����
	Container c;
	JTextField input500Field;
	JTextField input100Field;
	JTextField input50Field;
	JTextField input10Field;
	JButton btnInputMoney;
	JButton[] menuButton = new JButton[12];
	String[] menuArr;
	String[] priceArr;
	String[] arr;
	
	public Machine() {
		super("Vending Machine");
		c = this.getContentPane();
		c.setLayout(new BorderLayout());
		createMachineWindow();
		setSize(450,600);
//		super.pack();
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createMachineWindow() {    // ���Ǳ� â ����
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new FlowLayout());
		c.add(BorderLayout.CENTER,menuPanel);
		JPanel moneyPanel = new JPanel();
		moneyPanel.setLayout(new BorderLayout());
		c.add(BorderLayout.SOUTH,moneyPanel);
		
		// �޴��� ����
		JMenuBar menuBar = new JMenuBar();
		JMenuItem[] menuItem = new JMenuItem[3];
		String[] itemTitle = {"�޴� ����", "���� ����", "�ܵ� ����"};
		JMenu screenMenu = new JMenu("������");
		
		MenuBarActionListener barAction = new MenuBarActionListener();
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(barAction);
			screenMenu.add(menuItem[i]);
		}
		menuBar.add(screenMenu);
		setJMenuBar(menuBar);
		
		// ���Ǳ� �޴� â ����
		menuPanel.setLayout(null);
		this.printMenu(menuPanel);
		
		// USER ��� â ����
		JLabel label500 = new JLabel("500��:");
		JLabel label100 = new JLabel("100��:");
		JLabel label50 = new JLabel("50��:");
		JLabel label10 = new JLabel("10��:");
		input500Field = new JTextField(3);
		input100Field = new JTextField(3);
		input50Field = new JTextField(3);
		input10Field = new JTextField(3);
		btnInputMoney = new JButton("����");

	    moneyPanel.add(label500);
	    moneyPanel.add(input500Field);
	    moneyPanel.add(label100);
	    moneyPanel.add(input100Field);
	    moneyPanel.add(label50);
	    moneyPanel.add(input50Field);
	    moneyPanel.add(label10);
	    moneyPanel.add(input10Field);
	    moneyPanel.add(btnInputMoney);
	    
	    MenuActionListener menuAction = new MenuActionListener();
	    btnInputMoney.addActionListener(menuAction);
		}
	
	class MenuBarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e4) {
			String cmd = e4.getActionCommand();
			
			switch(cmd) {
			case "�޴� ����":
				new Login(1);
				break;
			case "���� ����":
				new Login(2);
				break;
			case "�ܵ� ����":
				new Login(3);
				break;
			}
		}
	}
	
	public void printMenu(JPanel panel) {
		FileReader fr = null;
		BufferedReader br = null;
		menuArr = new String[12];
		priceArr = new String[12];
		
		try {
			fr = new FileReader("./menu.txt");
			br = new BufferedReader(fr);
			int row = Integer.parseInt(br.readLine());
			int col = Integer.parseInt(br.readLine());
			arr = new String[col];
			MenuActionListener listener = new MenuActionListener();
			for(int i=0; i<row; i++) {
				if(i>11) break;
				for(int j=0; j<col; j++) {
					String data = br.readLine();
					arr[j] = data;
				}
				menuArr[i] = arr[0];
				priceArr[i] = arr[1];
				menuButton[i] = new JButton(menuArr[i]);
				menuButton[i].setFont(new Font("����ü", Font.PLAIN, 12));
//				if(i<3) {
//					menuButton[i].setBounds(130*i+30, 30, 100, 30);
//					menuButton[i].addActionListener(listener);
//				}
//				else if(i<6) {
//					menuButton[i].setBounds(130*(i-3)+30, 130, 100, 30);
//					menuButton[i].addActionListener(listener);
//				}
//				else if(i<9) {
//					menuButton[i].setBounds(130*(i-6)+30, 230, 100, 30);
//					menuButton[i].addActionListener(listener);
//				}
//				else {
//					menuButton[i].setBounds(130*(i-9)+30, 330, 100, 30);
//					menuButton[i].addActionListener(listener);
//				}
				menuButton[i].addActionListener(listener);
				panel.add(menuButton[i]);
			}
			
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
		} catch(NumberFormatException e1) {
			e1.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e5) {
			Object ob = e5.getSource();
			
			for(int i=0; i<12; i++) {
				if(ob == menuButton[i]) {
					Sales sales = new Sales("���� �߰�");
					sales.addSales(menuArr[i], priceArr[i]);
					sales.saveSales();
				}
			}
			if(ob == btnInputMoney) {
				Money money = new Money("�ܵ� ���");
				
			}
		}
	}
}