import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Menu extends JFrame implements ActionListener {    // 메뉴 관리 버튼 누르면 실행
	Container c;
	JTextField nameField,priceField,stockField;
	JButton btnAdd,btnDel,btnEdit;
	DefaultTableModel model;
	JTable menuTable;
	JScrollPane scrollPane;
	int SelectRow=-1;
	
	public Menu(String title) {    // 메뉴 관리 버튼 누르면 실행
		super(title);
		c = this.getContentPane();
		this.setBounds(100,100,400,400);
		this.createMenuWindow();
		setVisible(true);
	}
	
	public void createMenuWindow() {    // 메뉴 관리 버튼 누르면 실행
		JPanel pTop = new JPanel();
	}
	
	class TableEvent extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			SelectRow = menuTable.getSelectedRow();
			nameField.setText((String)menuTable.getValueAt(SelectRow,0));
			priceField.setText((String)menuTable.getValueAt(SelectRow,1));
			stockField.setText((String)menuTable.getValueAt(SelectRow,2));
	      }
	}

	public void addMenu() {    // Add버튼 누르면 실행
		if(nameField.getText() == ""||priceField.getText() == "" ||stockField.getText()== "") {
			JOptionPane.showMessageDialog(this,"추가할 상품 정보 입력");
		}
		
		Vector<String> v = new Vector<>();
        v.add(nameField.getText());
        v.add(priceField.getText());
        v.add(stockField.getText());
       
        inputPrice = Integer.parseInt(priceField.getText());
        inputStock = Integer.parseInt(stockField.getText());
        
        v.add(String.valueOf(inputPrice));
        v.add(String.valueOf(inputStock));
        menuModel.addRow(v);
        
        //입력값 지워주는 부분
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
        nameField.requestFocus();   // 커서 이름으로 돌아옴
	}
	
	public void deleteMenu() {
		if(SelectRow == -1) {
            JOptionPane.showMessageDialog(this,"삭제할 열 선택");
            return;
         }
         menuModel.removeRow(SelectRow);
         SelectRow = -1;
	}
	
	public void editMenu() {
		if(SelectRow == -1) {
			JOptionPane.showMessageDialog(this,"수정할 열 선택");
			return;
		}
		inputPrice = Integer.parseInt(priceField.getText());
		inputStock = Integer.parseInt(stockField.getText());
        
		menuModel.setValueAt(nameField.getText(),SelectRow,0);
		menuModel.setValueAt(inputPrice,SelectRow,1);
		menuModel.setValueAt(inputStock,SelectRow,2);
        
		SelectRow = -1;    // SelectRow초기화
        
		// 입력값 지워주는 부분
		nameField.setText("");
		priceField.setText("");
		stockField.setText("");
		nameField.requestFocus();	// 커서 이름으로 돌아옴
	}
	
	public void actionPerformed(ActionEvent e3) {
		if(e3.getSource() == btnAdd) {
			addMenu();
		}
		
		else if(e3.getSource() == btnDel) {
			deleteMenu();
		}
		
		else if(e3.getSource() == btnEdit) {
			editMenu();
		}
	}
}