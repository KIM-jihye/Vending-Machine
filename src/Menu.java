import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTable;

public class Menu extends JFrame implements ActionListener {
   
   JTextField name = new JTextField(5);     //��ǰ �̸�
   JTextField stock = new JTextField(5);    //����
   JTextField price = new JTextField(5);    //����
   JButton btnAdd = new JButton("�߰�");
   JButton btnDel = new JButton("����");
   JButton btnRev = new JButton("����"); 
   JPanel pan = new JPanel();
   JScrollPane jsp = new JScrollPane();
   Container c;
   int inputStock;
   int inputPrice;
   int SelectRow = -1;
   JTable table;
   DefaultTableModel model;

   public Menu() {
      setTitle("��ǰ �߰�|����|����");
      c = this.getContentPane();
     pan.add(new JLabel("��ǰ ��"));
      pan.add(name);
      pan.add(new JLabel("����"));
      pan.add(price);
      pan.add(new JLabel("���"));
      pan.add(stock);
   
      pan.add(btnAdd);
      pan.add(btnDel);
      pan.add(btnRev);
   }
   
   public void addMenu() {
      setLayout(new BorderLayout());
      String title[] = {"��ǰ��","����","����"};
      model = new DefaultTableModel(title,0);
      table = new JTable(model);
      table.addMouseListener(new TableEvent());
      add(pan,BorderLayout.NORTH);
      add(new JScrollPane(table),BorderLayout.CENTER);
      
      btnAdd.addActionListener(this);
      btnDel.addActionListener(this);
      btnRev.addActionListener(this);
      
      setSize(700,500);
      setVisible(true);
   }
   class TableEvent extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
         SelectRow = table.getSelectedRow();
         name.setText((String)table.getValueAt(SelectRow,0));
         price.setText((String)table.getValueAt(SelectRow,1));
         stock.setText((String)table.getValueAt(SelectRow,2));
      }
   }
   public void actionPerformed(ActionEvent e) {
      if(e.getSource()==btnAdd) {
         if(name.getText() == ""||price.getText() == "" ||stock.getText()== "") {
            JOptionPane.showMessageDialog(this,"�߰��� ��ǰ ���� �Է�");
         }
         Vector<String> v = new Vector<>();
         v.add(name.getText());
         v.add(price.getText());
         v.add(stock.getText());
        
         inputPrice = Integer.parseInt(price.getText());
         inputStock = Integer.parseInt(stock.getText());
         
         v.add(String.valueOf(inputPrice));
         v.add(String.valueOf(inputStock));
         model.addRow(v);
         
         //�Է°� �����ִ� �κ�
         name .setText("");
         price.setText("");
         stock.setText("");
         name.requestFocus();   //Ŀ�� �̸����� ���ƿ�
      }
      else if(e.getSource()==btnRev) {
        if(SelectRow == -1) {
            JOptionPane.showMessageDialog(this,"������ �� ����");
           return;
        }
        inputPrice = Integer.parseInt(price.getText());
        inputStock = Integer.parseInt(stock.getText());
        
        model.setValueAt(name.getText(),SelectRow,0);
        model.setValueAt(inputPrice,SelectRow,1);
        model.setValueAt(inputStock,SelectRow,2);
        
        SelectRow = -1;    //SelectRow�ʱ�ȭ
        
        //�Է°� �����ִ� �κ�
        name .setText("");
        price.setText("");
        stock.setText("");
        name.requestFocus();   //Ŀ�� �̸����� ���ƿ�
        
      }
      else if(e.getSource() == btnDel) {
         if(SelectRow == -1) {
            JOptionPane.showMessageDialog(this,"������ �� ����");
            return;
         }
         model.removeRow(SelectRow);
         SelectRow = -1;
      }
   }
}