import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Menu extends JFrame implements ActionListener {    // �޴� ���� ��ư ������ ����
	JTextField nameField = new JTextField(5);
	JTextField priceField = new JTextField(5);
	JTextField stockField = new JTextField(5);
	JButton btnAdd = new JButton("�߰�");
	JButton btnDel = new JButton("����");
	JButton btnEdit = new JButton("����");
	JPanel panel = new JPanel();
	JScrollPane jsp = new JScrollPane();
	Container c;
	int inputStock;
	int inputPrice;
	int SelectRow=-1;
	DefaultTableModel menuModel;
	JTable menuTable;
	
	public Menu() {    // �޴� ���� ��ư ������ ����
		setTitle("�޴� ����");
		createMenuWindow();
		setSize(600,600);
		setVisible(true);
	}
	
	public void createMenuWindow() {    // �޴� ���� ��ư ������ ����
		c = this.getContentPane();

		setLayout(new BorderLayout());
	
		panel.add(new JLabel("��ǰ��:"));
		panel.add(nameField);
		panel.add(new JLabel("����:"));
		panel.add(priceField);
		panel.add(new JLabel("���:"));
		panel.add(stockField);
		panel.add(btnAdd);
		panel.add(btnDel);
		panel.add(btnEdit);
		
		String title[] = {"��ǰ��","����","����"};
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