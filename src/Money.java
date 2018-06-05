import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.*;

public class Money extends JFrame implements ActionListener {
   
   JButton btnAdd = new JButton("�߰�");
   JButton btnRev = new JButton("����");
   JButton btnRmo = new JButton("����");
   JTextField change500 = new JTextField(3);
   JTextField change100 = new JTextField(3);
   JTextField change50 = new JTextField(3);
   JTextField change10 = new JTextField(3);
   JPanel pan = new JPanel(); 
   JScrollPane jsp = new JScrollPane();
   Container c;
   int c500;
   int c100;
   int c50;
   int c10;
   int SelectRow = -1;
   JTable table;
   DefaultTableModel model;
   
      public Money() {
      setTitle("�ܵ� �߰�");
      c = this.getContentPane();    
      pan.add(new JLabel("500�� "));
      pan.add(change500);
      pan.add(new JLabel("100�� "));
      pan.add(change100);
      pan.add(new JLabel("50�� "));
      pan.add(change50);
      pan.add(new JLabel("10�� "));
      pan.add(change10);
      
      pan.add(btnAdd); 
      pan.add(btnRev);
      pan.add(btnRmo);
      }
      
      public void addMoney() {
     setLayout(new BorderLayout());
      String title[] = {"500��","100��","50��","10��"}; 
      //String num[] =  {"0","0","0","0"};
      model = new DefaultTableModel(title,0);
      table = new JTable(model);
      table.addMouseListener(new TableEvent());
      //jsp= new JScrollPane(table);
      add(pan, BorderLayout.NORTH);
      
      add(new JScrollPane(table), BorderLayout.CENTER);
      btnAdd.addActionListener(this);
      btnRev.addActionListener(this);
      btnRmo.addActionListener(this);
      setSize(700,500);
      setVisible(true);
      }
   
      class TableEvent extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
         SelectRow = table.getSelectedRow();  //���õ� ��� ���� �ε����� �����ϴ� ������ �迭. ���� ���õǾ� ���� ���� ���� �� ������ �迭 ���
         change500.setText((String)table.getValueAt(SelectRow, 0));
         change100.setText((String)table.getValueAt(SelectRow, 1));
         change50.setText((String)table.getValueAt(SelectRow, 2));
         change10.setText((String)table.getValueAt(SelectRow, 3));
         
         }
      }
      
      public void actionPerformed(ActionEvent e) {
     
       if(e.getSource() == btnAdd) {
        Vector<String> v = new Vector<>();
         v.add(change500.getText());
         v.add(change100.getText());
         v.add(change50.getText());
         v.add(change10.getText());
         c500 = Integer.parseInt(change500.getText());
         c100 = Integer.parseInt(change100.getText());
         c50 = Integer.parseInt(change50.getText());
         c10 = Integer.parseInt(change10.getText());
        
         v.add(String.valueOf(c500));
         v.add(String.valueOf(c100));
         v.add(String.valueOf(c50));
         v.add(String.valueOf(c10));
         model.addRow(v);
         
         //�Է°� �����ִ� �κ�
         change500.setText("");
         change100.setText("");
         change50.setText("");
         change10.setText("");
         change500.requestFocus();    //Ŀ��  500������  ���ƿ�
         /*
         model.setValueAt(c500,SelectRow,0);
         model.setValueAt(change100,SelectRow,1);
         model.setValueAt(change50,SelectRow,2);
         model.setValueAt(change10,SelectRow,3);
         */
         }
       
       else if(e.getSource() == btnRev) {
          if(SelectRow == -1) {
             JOptionPane.showConfirmDialog(this, "������ ���� ������ �ּ���","����Ȯ��",JOptionPane.INFORMATION_MESSAGE);
             return;
          }
          
          c500 = Integer.parseInt(change500.getText());
            c100 = Integer.parseInt(change100.getText());
            c50 = Integer.parseInt(change50.getText());
            c10 = Integer.parseInt(change10.getText());
             
           model.setValueAt(c500,SelectRow,0);
          model.setValueAt(c100,SelectRow,1);
          model.setValueAt(c50,SelectRow,2);
          model.setValueAt(c10,SelectRow,3);
          
          SelectRow = -1;       //selectRow�ʱ�ȭ
          //�Է°� ����� �κ�
          change500.setText("");
          change100.setText("");
          change50.setText("");
          change10.setText("");
          change500.requestFocus();      //Ŀ�� 500�� �Է����� ���ƿ�
          
       }
       
       else if(e.getSource() == btnRmo) {
          if(SelectRow == -1) {
             JOptionPane.showMessageDialog(this,"������ �� ����");
             return;
          }
             model.removeRow(SelectRow);
             SelectRow = -1;   
             
       }
    }
}

