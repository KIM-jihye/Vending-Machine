import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
class Login extends JFrame implements ActionListener {
	private String password = "123";    // 비밀번호를 123으로 설정 
	private JPasswordField passwordField;
	private JButton login;
	JLabel loginText = new JLabel();
	private boolean isLogin = false;    // 로그인이 성공했는지 판별(초기값은 실패)
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
					loginText.setText("로그인되었습니다. 안녕하세요.");
					switch(num) {
					case 1:
						new Menu("메뉴 관리");
						break;
					case 2:
						new Sales("매출 관리");
						break;
					case 3:
						new Money("잔돈 관리");
						break;
					}
				}
				else {
					loginText.setText("PASSWORD가 잘못되었습니다.");
				}
				passwordField.setText("");
			} catch (Exception e2) {
				System.out.println("false");
			}
		}
	}
}

@SuppressWarnings("serial")
class Machine extends JFrame {    // 실행시키면 실행
	Container c;
	JTextField input500Field,input100Field,input50Field,input10Field;
	JButton btnInputMoney;
	JButton[] menuButton = new JButton[12];
	JLabel[] priceLabel = new JLabel[12];
	String[] menuArr;
	String[] priceArr;
	String[] arr;
	String printMessage1 = "구매하실 품목을 선택하세요.";;
	String printMessage2;
	
	public Machine() {
		setTitle("Vending Machine");
		c = this.getContentPane();
		createMachineWindow();
		setSize(450,600);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createMachineWindow() {    // 자판기 창 생성
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(null);
		JPanel moneyInputPanel = new JPanel();
		moneyInputPanel.setLayout(new FlowLayout(20));
		JPanel moneyPanel = new JPanel();
		moneyPanel.setLayout(new BorderLayout(20,10));
		c.add(BorderLayout.EAST, new JLabel());
		c.add(BorderLayout.WEST, new JLabel());
		c.add(BorderLayout.CENTER, menuPanel);
		c.add(BorderLayout.SOUTH, moneyPanel);
		
		// 메뉴바 생성
		JMenuBar menuBar = new JMenuBar();
		JMenuItem[] menuItem = new JMenuItem[3];
		String[] itemTitle = {"메뉴 관리", "매출 관리", "잔돈 관리"};
		JMenu screenMenu = new JMenu("관리자");
		
		MenuBarActionListener barAction = new MenuBarActionListener();
		for(int i=0; i<menuItem.length; i++) {
			menuItem[i] = new JMenuItem(itemTitle[i]);
			menuItem[i].addActionListener(barAction);
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
		input500Field = new JTextField(3);
		input100Field = new JTextField(3);
		input50Field = new JTextField(3);
		input10Field = new JTextField(3);
		btnInputMoney = new JButton("투입");

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
			case "메뉴 관리":
				new Login(1);
				break;
			case "매출 관리":
				new Login(2);
				break;
			case "잔돈 관리":
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
			for(int i=0; i<row; i++) {
				if(i>11) break;
				for(int j=0; j<col; j++) {
					String data = br.readLine();
					arr[j] = data;
				}
				menuArr[i] = arr[0];
				priceArr[i] = arr[1];
				menuButton[i] = new JButton(menuArr[i]);
				priceLabel[i] = new JLabel(priceArr[i] + "원");
				MenuActionListener listener = new MenuActionListener();
				menuButton[i].setFont(new Font("돋움체", Font.PLAIN, 12));
				priceLabel[i].setFont(new Font("돋움체", Font.PLAIN, 12));

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
				
				menuButton[i].addActionListener(listener);
				panel.add(menuButton[i]);
				panel.add(priceLabel[i]);
			}
			
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
		} catch(NumberFormatException e1) {
			e1.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
	}
	
	void buyMessagePrint(String selectMenu, String selectPrice) {
		printMessage1 = selectMenu + "를 선택하셨습니다. " + selectPrice + "원이 필요합니다.";
		int ans = JOptionPane.showConfirmDialog(this, printMessage1, "구매확인", JOptionPane.YES_NO_OPTION);
		if(ans == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(this, selectPrice + "원이 필요합니다.");
		}
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e5) {
			Object ob = e5.getSource();
			
			for(int i=0; i<12; i++) {
				if(ob == menuButton[i]) {
//					Sales sales = new Sales("매출 추가");
//					sales.addSales(menuArr[i], priceArr[i]);
//					sales.saveSales();
					buyMessagePrint(menuArr[i], priceArr[i]);
				}
			}
			if(ob == btnInputMoney) {
				Money money = new Money();
				
			}
		}
	}
}