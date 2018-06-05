import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTable;

public class Menu extends JFrame implements ActionListener {
   
   JTextField name = new JTextField(5);     //상품 이름
   JTextField stock = new JTextField(5);    //개수
   JTextField price = new JTextField(5);    //가격
   JButton btnAdd = new JButton("추가");
   JButton btnDel = new JButton("삭제");
   JButton btnRev = new JButton("수정"); 
   JPanel pan = new JPanel();
   JScrollPane jsp = new JScrollPane();
   Container c;
   int inputStock;
   int inputPrice;
   int SelectRow = -1;
   JTable table;
   DefaultTableModel model;

   public Menu() {
      setTitle("제품 추가|수정|삭제");
      c = this.getContentPane();
     pan.add(new JLabel("상품 명"));
      pan.add(name);
      pan.add(new JLabel("가격"));
      pan.add(price);
      pan.add(new JLabel("재고"));
      pan.add(stock);
   
      pan.add(btnAdd);
      pan.add(btnDel);
      pan.add(btnRev);
   }
   
   public void addMenu() {
      setLayout(new BorderLayout());
      String title[] = {"상품명","가격","개수"};
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
            JOptionPane.showMessageDialog(this,"추가할 상품 정보 입력");
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
         
         //입력값 지워주는 부분
         name .setText("");
         price.setText("");
         stock.setText("");
         name.requestFocus();   //커서 이름으로 돌아옴
      }
      else if(e.getSource()==btnRev) {
        if(SelectRow == -1) {
            JOptionPane.showMessageDialog(this,"수정할 열 선택");
           return;
        }
        inputPrice = Integer.parseInt(price.getText());
        inputStock = Integer.parseInt(stock.getText());
        
        model.setValueAt(name.getText(),SelectRow,0);
        model.setValueAt(inputPrice,SelectRow,1);
        model.setValueAt(inputStock,SelectRow,2);
        
        SelectRow = -1;    //SelectRow초기화
        
        //입력값 지워주는 부분
        name .setText("");
        price.setText("");
        stock.setText("");
        name.requestFocus();   //커서 이름으로 돌아옴
        
      }
      else if(e.getSource() == btnDel) {
         if(SelectRow == -1) {
            JOptionPane.showMessageDialog(this,"삭제할 열 선택");
            return;
         }
         model.removeRow(SelectRow);
         SelectRow = -1;
      }
   }
}