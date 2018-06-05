import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Menu extends JFrame implements ActionListener {    // �޴� ���� ��ư ������ ����
	Container c;
	JTextField nameField,priceField,stockField;
	JButton btnAdd,btnDel,btnEdit;
	DefaultTableModel model;
	JTable menuTable;
	JScrollPane scrollPane;
	int SelectRow=-1;
	
	public Menu(String title) {    // �޴� ���� ��ư ������ ����
		super(title);
		c = this.getContentPane();
		this.setBounds(100,100,400,400);
		this.createMenuWindow();
		setVisible(true);
	}
	
	public void createMenuWindow() {    // �޴� ���� ��ư ������ ����
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

	public void addMenu() {    // Add��ư ������ ����
		if(nameField.getText() == ""||priceField.getText() == "" ||stockField.getText()== "") {
			JOptionPane.showMessageDialog(this,"�߰��� ��ǰ ���� �Է�");
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
        
        //�Է°� �����ִ� �κ�
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
        nameField.requestFocus();   // Ŀ�� �̸����� ���ƿ�
	}
	
	public void deleteMenu() {
		if(SelectRow == -1) {
            JOptionPane.showMessageDialog(this,"������ �� ����");
            return;
         }
         menuModel.removeRow(SelectRow);
         SelectRow = -1;
	}
	
	public void editMenu() {
		if(SelectRow == -1) {
			JOptionPane.showMessageDialog(this,"������ �� ����");
			return;
		}
		inputPrice = Integer.parseInt(priceField.getText());
		inputStock = Integer.parseInt(stockField.getText());
        
		menuModel.setValueAt(nameField.getText(),SelectRow,0);
		menuModel.setValueAt(inputPrice,SelectRow,1);
		menuModel.setValueAt(inputStock,SelectRow,2);
        
		SelectRow = -1;    // SelectRow�ʱ�ȭ
        
		// �Է°� �����ִ� �κ�
		nameField.setText("");
		priceField.setText("");
		stockField.setText("");
		nameField.requestFocus();	// Ŀ�� �̸����� ���ƿ�
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