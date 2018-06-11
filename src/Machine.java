import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

@SuppressWarnings("serial")
class Login extends JFrame implements ActionListener {
	private String password = "123";    // 비밀번호를 123으로 설정 
	private JPasswordField passwordField;
	private JButton login;
	JLabel loginText = new JLabel();
	private boolean isLogin = false;    // 로그인이 성공했는지 판별(초기값은 실패)
	int num=0;    // num값에 따라 메뉴관리,잔액관리,매출관리로 나뉨
	
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
	String[] menuArr,priceArr,arr;    // menuArr:메뉴명 배열, priceArr:가격 배열, arr:menuArr과 priceArr을 만들기 위한 초기배열
	String printMessage1 = "구매하실 품목을 선택하세요.";    // 안내 메시지
	String selectMenu,selectPrice;    // selectMenu:사용자 선택 메뉴, selectPrice:사용자 선택 메뉴의 가격
	int price=0;    // 사용자 선택 메뉴의 가격
	boolean isBuy=false;    // 선택 메뉴보다 금액을 많이 넣었는지 판단
	boolean isSuccess=true;    // 금액을 잘못 넣었는지 판단
	
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
		menuPanel.setBackground(Color.LIGHT_GRAY);
		JPanel moneyInputPanel = new JPanel();
		moneyInputPanel.setLayout(new FlowLayout(20));
		moneyInputPanel.setBackground(Color.WHITE);
		JPanel moneyPanel = new JPanel();
		moneyPanel.setBackground(Color.WHITE);
		moneyPanel.setLayout(new BorderLayout(20,10));
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
		printMessage1 = selectMenu + "를 선택하셨습니다. ";
		int ans = JOptionPane.showConfirmDialog(this, printMessage1, "구매확인", JOptionPane.YES_NO_OPTION);
		if(ans == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(this, selectPrice + "원이 필요합니다.");
		}
		else {
			this.selectMenu = null;
			selectMenu = null;
		}
	}
	
	public void buyMessage2Print(String selectMenu) {
		JOptionPane.showMessageDialog(this, selectMenu + " 의 구매가 완료되었습니다.");
	}
	
	public void errorMessage1() {
		JOptionPane.showMessageDialog(this, "메뉴를 선택하세요.", "오류", JOptionPane.WARNING_MESSAGE);
	}
	
	public void errorMessage2() {
		JOptionPane.showMessageDialog(this, "금액이 부족합니다.", "오류", JOptionPane.WARNING_MESSAGE);
	}
	
	public void soldOutMessage() {
		JOptionPane.showMessageDialog(this, "이 품목은 매진되었습니다.", "매진", JOptionPane.WARNING_MESSAGE);
	}
	
	public void changeMessagePrint() {
		Money money = new Money("잔돈 계산");
		int[] inputChangeArr = new int[4];
		int[] ouputChangeArr = new int[5];
		try {
			inputChangeArr[0] = Integer.parseInt(input500Field.getText());
			inputChangeArr[1] = Integer.parseInt(input100Field.getText());
			inputChangeArr[2] = Integer.parseInt(input50Field.getText());
			inputChangeArr[3] = Integer.parseInt(input10Field.getText());
			
			if(inputChangeArr[0]<0 || inputChangeArr[1]<0 || inputChangeArr[2]<0 || inputChangeArr[3]<0) {
				JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다.", "경고", JOptionPane.WARNING_MESSAGE);
				isSuccess = false;
			}
			else {
				ouputChangeArr = money.changeMoney(inputChangeArr,price);
				if(ouputChangeArr[4] < 0) {
					this.errorMessage2();
					isSuccess = false;
				}
				else {
					printMessage1 = "거스름돈 : 500원 " + Integer.toString(ouputChangeArr[0]) +"개, "+ "100원 " + Integer.toString(ouputChangeArr[1])+"개, "
			                                 + "50원 " + Integer.toString(ouputChangeArr[2]) +"개, " + "10원 " + Integer.toString(ouputChangeArr[3])+"개";
					JOptionPane.showMessageDialog(this, printMessage1, "거스름돈", JOptionPane.WARNING_MESSAGE); 
				}
			}
			// 입력 창 비움
			input500Field.setText("");
			input100Field.setText("");
			input50Field.setText("");
			input10Field.setText("");
			input500Field.requestFocus();
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			isSuccess = false;
		}
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e5) {
			Object ob = e5.getSource();
			Menu menu = new Menu("구매");
			
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
					Money money = new Money("잔돈 계산");
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
								Sales sales = new Sales("매출 추가");
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