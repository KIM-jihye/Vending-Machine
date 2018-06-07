import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {
	Container cp;
	JTextField nameField,priceField,stockField;
	JButton btnAdd,btnDel,btnMod,btnSave;
	DefaultTableModel model;
	JTable table;
	JScrollPane jsp;
	int SelectRow = -1;
	
	Menu(String title) {
		super(title);
		cp = this.getContentPane();
		this.setBounds(100,100,400,400);
		this.setDesign();
		this.setVisible(true);
	}
	
	public void setDesign() {
		JPanel pTop = new JPanel();
		cp.add("North",pTop);
		JPanel pBottom = new JPanel();
		cp.add("South",pBottom);
		// ���
		nameField = new JTextField(7);
		priceField = new JTextField(7);
		stockField = new JTextField(7);
		pTop.add(new JLabel("��ǰ��:"));
		pTop.add(nameField);
		pTop.add(new JLabel("����:"));
		pTop.add(priceField);
		pTop.add(new JLabel("���:"));
		pTop.add(stockField);
		// �߰�
		String[] title = {"��ǰ��","����","���"};
		model = new DefaultTableModel(title, 0);
		table = new JTable(model);
		table.addMouseListener(new TableEvent());
		jsp = new JScrollPane(table);
		cp.add("Center",jsp);
		// �ϴ�
		btnAdd = new JButton("�߰�");
		btnDel = new JButton("����");
		btnMod = new JButton("����");
		btnSave = new JButton("����");
		pBottom.add(btnAdd);
		pBottom.add(btnDel);
		pBottom.add(btnMod);
		pBottom.add(btnSave);
		btnAdd.addActionListener(this);
		btnDel.addActionListener(this);
		btnMod.addActionListener(this);
		btnSave.addActionListener(this);
		
		this.openMenu();
	}
	
	public void openMenu() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("./menu.dat");
			br = new BufferedReader(fr);
			int row = Integer.parseInt(br.readLine());
			int col = Integer.parseInt(br.readLine());
			String[] str = new String[col];
			model.setRowCount(0);
			for(int i=0; i<row; i++) {
				for(int j=0; j<col; j++) {
					String data = br.readLine();
					str[j] = data;
				}
				model.addRow(str);
			}
			br.close();
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
		} catch(NumberFormatException e1) {
			e1.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
	}
	
	class TableEvent extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			SelectRow = table.getSelectedRow();
			nameField.setText((String)table.getValueAt(SelectRow,0));
			priceField.setText((String)table.getValueAt(SelectRow,1));
			stockField.setText((String)table.getValueAt(SelectRow,2));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == btnAdd) {
			Vector<String> v = new Vector<String>();
			v.add(nameField.getText());
			v.add(priceField.getText());
			v.add(stockField.getText());
			model.addRow(v);
			// �Է°� �����ִºκ�
			nameField.setText("");
			priceField.setText("");
			stockField.setText("");
			nameField.requestFocus();	// Ŀ�� �̸����� ���ƿ�
		}
		else if(ob == btnDel) {
			if(SelectRow < 0) {
				JOptionPane.showMessageDialog(this, "������ ���� �����ϼ���", "����", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String msg = table.getValueAt(SelectRow, 0) + "�����Ͻðڽ��ϱ�?";
			int ans = JOptionPane.showConfirmDialog(this, msg, "����Ȯ�οϷ�", JOptionPane.YES_NO_OPTION);
			if(ans == JOptionPane.YES_OPTION) {
				model.removeRow(SelectRow);
				JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�");
				SelectRow = -1;
			}
		}
		else if(ob == btnMod) {
			if(SelectRow < 0) {
				JOptionPane.showMessageDialog(this, "������ ���� �����ϼ���", "����", JOptionPane.WARNING_MESSAGE);
				return;
			}
			int ans = JOptionPane.showConfirmDialog(this, table.getValueAt(SelectRow, 0) + "�����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
			if(ans == JOptionPane.YES_OPTION) {
				model.setValueAt(nameField.getText(), SelectRow, 0);
				model.setValueAt(priceField.getText(), SelectRow, 1);
				model.setValueAt(stockField.getText(), SelectRow, 2);
				
				SelectRow = -1;
				nameField.setText("");
				priceField.setText("");
				stockField.setText("");
				nameField.requestFocus();
			}
		}
		else if(ob == btnSave) {
			// ���� ����
			FileWriter fw = null;
			try {
				fw = new FileWriter("./menu.dat");
				fw.write(String.valueOf(model.getRowCount()) + "\n");
				fw.write(String.valueOf(model.getColumnCount()) + "\n");
				for(int i=0; i<model.getRowCount(); i++) {
					for(int j=0; j<model.getColumnCount(); j++) {
						String data = (String)model.getValueAt(i, j);
						fw.write(data + "\n");
					}
				}
			} catch(IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					fw.close();
				} catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}