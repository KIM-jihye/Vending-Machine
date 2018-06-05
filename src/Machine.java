import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;

class Login extends JFrame implements ActionListener {
    private String password = "123";
    private JTextField tf;
    private JPasswordField pf;
    private JButton login;
    JLabel loginText = new JLabel();
    private boolean isLogin = false;
    Menu menu = new Menu();
    Money money = new Money();
    public Login() {
     JPanel passPanel = new JPanel();
     tf = new JTextField(12);
     pf = new JPasswordField(10);
     loginText.setForeground(Color.RED); 
     
     JLabel passLabel = new JLabel("PASSWORD");
     passLabel.setBounds(10, 10, 80, 25);
     login = new JButton("LOGIN");
     login.addActionListener(this);
     

     passPanel.add(passLabel);
     passPanel.add(pf);


     this.add(passPanel);
     this.add(login);
     this.add(loginText);
     
     setLayout(new FlowLayout());

     setTitle("LOGIN");
     setSize(300, 200);

     setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
     // TODO Auto-generated method stub
     if (e.getSource() == login) {
      try {
       if (password.equals(pf.getText()))
        isLogin = true;
       else
        isLogin = false;
       if (isLogin) {
        loginText.setText("�α��εǾ����ϴ�. �ȳ��ϼ���.");
        menu.addMenu();
       // money.addMoney();
       } else {
        loginText.setText("ID �Ǵ� password�� �߸��Ǿ����ϴ�.");
       }
      } catch (Exception e1) {
       System.out.println("false");
      }
     }
    }
}

public class Machine extends JFrame {
   //private JLabel imgLabel1 = new JLabel();
   public Machine() {
      setTitle("Vending Machine");
      createMenu();   //�޴� ����
      setSize(500,500);
      setVisible(true);
   }
   
   Menu menu = new Menu();
   Money money = new Money();
   public void createMenu() {
      
      JMenuBar menuBar = new JMenuBar();
      JMenuItem[] menuItem = new JMenuItem[5];
      String[] itemTitle = {"�޴� �߰�", "�޴� ����", "��� Ȯ��|�߰�", "�ܵ�  �߰�", "����"};
      JMenu screenMenu1 = new JMenu("������");
      JMenu screenMenu2 = new JMenu("�����");
      MenuActionListener listener = new MenuActionListener();
      for(int i=0; i<menuItem.length; i++) {
         
         menuItem[i] = new JMenuItem(itemTitle[i]);
         menuItem[i].addActionListener(listener);
         screenMenu1.add(menuItem[i]);
      }
      
      menuBar.add(screenMenu1);
      menuBar.add(screenMenu2);
      setJMenuBar(menuBar);
      
   }
   
   class MenuActionListener implements ActionListener {
      public void actionPerformed(ActionEvent e1) {
      String cmd = e1.getActionCommand();
      
      switch(cmd) {
      case "�޴� �߰�":
         new Login();
          menu.addMenu();
         break;
         
      case "�޴� ����":
        
        
      case "��� Ȯ��|�߰�":
        
      
      case "�ܵ� �߰�":
       money.addMoney();
    
       
      case "����":
        
      }
   }
}
}