import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

@SuppressWarnings("serial")
class Login extends JFrame implements ActionListener {
	private String password = "123";    // ��й�ȣ�� 123���� ���� 
	private JPasswordField passwordField;
	private JButton login;
	JLabel loginText = new JLabel();
	private boolean isLogin = false;    // �α����� �����ߴ��� �Ǻ�(�ʱⰪ�� ����)
	int num=0;    // num���� ���� �޴�����,�ܾװ���,��������� ����
	
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
	JTextField input500Field,input100Field,input50Field,input10Field;
	JButton btnInputMoney;
	JButton[] menuButton = new JButton[12];
	JLabel[] priceLabel = new JLabel[12];
	String[] menuArr,priceArr,arr;    // menuArr:�޴��� �迭, priceArr:���� �迭, arr:menuArr�� priceArr�� ����� ���� �ʱ�迭
	String printMessage1 = "�����Ͻ� ǰ���� �����ϼ���.";    // �ȳ� �޽���
	String selectMenu,selectPrice;    // selectMenu:����� ���� �޴�, selectPrice:����� ���� �޴��� ����
	int price=0;    // ����� ���� �޴��� ����
	boolean isBuy=false;    // ���� �޴����� �ݾ��� ���� �־����� �Ǵ�
	boolean isSuccess=true;    // �ݾ��� �߸� �־����� �Ǵ�
	
	public Machine() {
		setTitle("Vending Machine");
		c = this.getContentPane();
		createMachineWindow();
		setSize(450,600);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createMachineWindow() {    // ���Ǳ� â ����
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setBackground(Color.LIGHT_GRAY);
		JPanel moneyInputPanel = new JPanel();
		moneyInputPanel.setLayout(new FlowLayout(20));
		moneyInputPanel.setBackground(Color.WHITE);
		JPanel moneyPanel = new JPanel();
		moneyPanel.setBackground(Color.WHITE);
		moneyPanel.setLayout(new BorderLayout(20,10));
		c.add(BorderLayout.CENTER, menuPanel);
		c.add(BorderLayout.SOUTH, moneyPanel);
		
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
		this.printMenu(menuPanel);
		
		// USER ��� â ����		
		
		JLabel printLabel = new JLabel(printMessage1);
		
		JLabel label500 = new JLabel("500��:");
		JLabel label100 = new JLabel("100��:");
		JLabel label50 = new JLabel("50��:");
		JLabel label10 = new JLabel("10��:");
		input500Field = new JTextField(3);
		input100Field = new JTextField(3);
		input50Field = new JTextField(3);
		input10Field = new JTextField(3);
		btnInputMoney = new JButton("����");
		btnInputMoney.setBackground(Color.WHITE);

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
		int row,col;
		
		try {
			fr = new FileReader("./menu.txt");
			br = new BufferedReader(fr);
			row = Integer.parseInt(br.readLine());
			col = Integer.parseInt(br.readLine());
			arr = new String[col];
			for(int i=0; i<row; i++) {
				if(i>11) break;
				for(int j=0; j<col; j++) {
					String data = br.readLine();
					arr[j] = data;
				}
				menuArr[i] = arr[0];
				priceArr[i] = arr[1];
				menuButton[i] = new JButton(menuArr[i]);
				priceLabel[i] = new JLabel(priceArr[i] + "��");
				MenuActionListener listener = new MenuActionListener();
				menuButton[i].setFont(new Font("����ü", Font.PLAIN, 12));
				priceLabel[i].setFont(new Font("����ü", Font.PLAIN, 12));

				if(i<3) {
					menuButton[i].setBounds(130*i+30, 30, 100, 30);
					priceLabel[i].setBounds(130*i+30, 60, 100, 30);
				}
				else if(i<6) {
					menuButton[i].setBounds(130*(i-3)+30, 130, 100, 30);
					priceLabel[i].setBounds(130*(i-3)+30, 160, 100, 30);
				}
				else if(i<9) {
					menuButton[i].setBounds(130*(i-6)+30, 230, 100, 30);
					priceLabel[i].setBounds(130*(i-6)+30, 260, 100, 30);
				}
				else {
					menuButton[i].setBounds(130*(i-9)+30, 330, 100, 30);
					priceLabel[i].setBounds(130*(i-9)+30, 360, 100, 30);
				}
				menuButton[i].setBackground(Color.WHITE);
				menuButton[i].addActionListener(listener);
				panel.add(menuButton[i]);
				panel.add(priceLabel[i]);
			}
			
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
		} catch(NumberFormatException e1) {
			row = 0;
			col = 0;
		} catch(IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void buyMessage1Print(String selectMenu, String selectPrice) {
		printMessage1 = selectMenu + "�� �����ϼ̽��ϴ�. ";
		int ans = JOptionPane.showConfirmDialog(this, printMessage1, "����Ȯ��", JOptionPane.YES_NO_OPTION);
		if(ans == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(this, selectPrice + "���� �ʿ��մϴ�.");
		}
		else {
			this.selectMenu = null;
			selectMenu = null;
		}
	}
	
	public void buyMessage2Print(String selectMenu) {
		JOptionPane.showMessageDialog(this, selectMenu + " �� ���Ű� �Ϸ�Ǿ����ϴ�.");
	}
	
	public void errorMessage1() {
		JOptionPane.showMessageDialog(this, "�޴��� �����ϼ���.", "����", JOptionPane.WARNING_MESSAGE);
	}
	
	public void errorMessage2() {
		JOptionPane.showMessageDialog(this, "�ݾ��� �����մϴ�.", "����", JOptionPane.WARNING_MESSAGE);
	}
	
	public void soldOutMessage() {
		JOptionPane.showMessageDialog(this, "�� ǰ���� �����Ǿ����ϴ�.", "����", JOptionPane.WARNING_MESSAGE);
	}
	
	public void changeMessagePrint() {
		Money money = new Money("�ܵ� ���");
		int[] inputChangeArr = new int[4];
		int[] ouputChangeArr = new int[5];
		try {
			inputChangeArr[0] = Integer.parseInt(input500Field.getText());
			inputChangeArr[1] = Integer.parseInt(input100Field.getText());
			inputChangeArr[2] = Integer.parseInt(input50Field.getText());
			inputChangeArr[3] = Integer.parseInt(input10Field.getText());
			
			if(inputChangeArr[0]<0 || inputChangeArr[1]<0 || inputChangeArr[2]<0 || inputChangeArr[3]<0) {
				JOptionPane.showMessageDialog(this, "�߸� �Է��ϼ̽��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
				isSuccess = false;
			}
			else {
				ouputChangeArr = money.changeMoney(inputChangeArr,price);
				if(ouputChangeArr[4] < 0) {
					this.errorMessage2();
					isSuccess = false;
				}
				else {
					printMessage1 = "�Ž����� : 500�� " + Integer.toString(ouputChangeArr[0]) +"��, "+ "100�� " + Integer.toString(ouputChangeArr[1])+"��, "
			                                 + "50�� " + Integer.toString(ouputChangeArr[2]) +"��, " + "10�� " + Integer.toString(ouputChangeArr[3])+"��";
					JOptionPane.showMessageDialog(this, printMessage1, "�Ž�����", JOptionPane.WARNING_MESSAGE); 
				}
			}
			// �Է� â ���
			input500Field.setText("");
			input100Field.setText("");
			input50Field.setText("");
			input10Field.setText("");
			input500Field.requestFocus();
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "�߸� �Է��ϼ̽��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
			isSuccess = false;
		}
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e5) {
			Object ob = e5.getSource();
			Menu menu = new Menu("����");
			
			for(int i=0; i<12; i++) {
				if(ob == menuButton[i]) {
					selectMenu = menuArr[i];
					selectPrice = priceArr[i];
					price = Integer.parseInt(priceArr[i]);
					buyMessage1Print(selectMenu, selectPrice);
				}
			}
			if(ob == btnInputMoney) {
				if(selectMenu == null) {
					errorMessage1();
				}
				else {
					Money money = new Money("�ܵ� ���");
					isBuy = money.successChange();
					if(isBuy) {
						menu.stockReduction(selectMenu);
						if(menu.isSoldOut()) {
							soldOutMessage();
							selectMenu = null;
						}
						else {
							changeMessagePrint();
							if(isSuccess) {
								Sales sales = new Sales("���� �߰�");
								sales.addSales(selectMenu, selectPrice);
								sales.saveSales();
								buyMessage2Print(selectMenu);
								selectMenu = null;
							}
						}
					}
					else {
						errorMessage2();
					}
				}
			}
		}
	}
}