import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Menu extends JFrame implements ActionListener {    // 메뉴 관리 버튼 누르면 실행
	JTextField nameField = new JTextField(5);
	JTextField priceField = new JTextField(5);
	JTextField stockField = new JTextField(5);
	JButton btnAdd = new JButton("추가");
	JButton btnDel = new JButton("삭제");
	JButton btnEdit = new JButton("수정");
	JPanel panel = new JPanel();
	JScrollPane jsp = new JScrollPane();
	Container c;
	int inputStock;
	int inputPrice;
	int SelectRow=-1;
	DefaultTableModel menuModel;
	JTable menuTable;
	
	public Menu() {    // 메뉴 관리 버튼 누르면 실행
		setTitle("메뉴 관리");
		createMenuWindow();
		setSize(600,600);
		setVisible(true);
	}
	
	public void createMenuWindow() {    // 메뉴 관리 버튼 누르면 실행
		c = this.getContentPane();

		setLayout(new BorderLayout());
	
		panel.add(new JLabel("상품명:"));
		panel.add(nameField);
		panel.add(new JLabel("가격:"));
		panel.add(priceField);
		panel.add(new JLabel("재고:"));
		panel.add(stockField);
		panel.add(btnAdd);
		panel.add(btnDel);
		panel.add(btnEdit);
		
		String title[] = {"상품명","가격","개수"};
		menuModel = new DefaultTableModel(title,0);
		menuTable = new JTable(menuModel);
		menuTable.addMouseListener(new TableEvent());
		add(panel,BorderLayout.NORTH);
		add(new JScrollPane(menuTable),BorderLayout.CENTER);

		btnAdd.addActionListener(this);
		btnDel.addActionListener(this);
		btnEdit.addActionListener(this);
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