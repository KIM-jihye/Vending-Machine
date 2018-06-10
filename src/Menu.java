import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener {
	String[] title = {"상품명","가격","재고"};
	Container cp;
	JTextField nameField,priceField,stockField;
	JButton btnAdd,btnDel,btnMod,btnSave;
	DefaultTableModel model = new DefaultTableModel(title, 0);
	JTable table;
	JScrollPane jsp;
	int SelectRow = -1;
	boolean soldOut = false; 
	
	Menu(String menuTitle) {
		super(menuTitle);
		if(menuTitle == "메뉴 관리") {
			cp = this.getContentPane();
			this.setBounds(100,100,400,400);
			this.setDesign();
			this.setVisible(true);
		}
		else {
		}
	}
	
	public void setDesign() {
		JPanel pTop = new JPanel();
		cp.add("North",pTop);
		JPanel pBottom = new JPanel();
		cp.add("South",pBottom);
		// 상단
		nameField = new JTextField(7);
		priceField = new JTextField(7);
		stockField = new JTextField(7);
		pTop.add(new JLabel("상품명:"));
		pTop.add(nameField);
		pTop.add(new JLabel("가격:"));
		pTop.add(priceField);
		pTop.add(new JLabel("재고:"));
		pTop.add(stockField);
		// 중간
		table = new JTable(model);
		table.addMouseListener(new TableEvent());
		jsp = new JScrollPane(table);
		cp.add("Center",jsp);
		// 하단
		btnAdd = new JButton("추가");
		btnDel = new JButton("삭제");
		btnMod = new JButton("수정");
		btnSave = new JButton("저장");
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
		int row,col;
		try {
			File menuFile = new File("./menu.txt");
			if(!menuFile.exists()) {
				menuFile.createNewFile();
			}
			fr = new FileReader("./menu.txt");
			br = new BufferedReader(fr);
			row = Integer.parseInt(br.readLine());
			col = Integer.parseInt(br.readLine());
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
			row = 0;
			col = 0;
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
	
	public void addMenu() {
		boolean existMenu=false;
		
		if(model.getRowCount() >= 12) {
			JOptionPane.showMessageDialog(this, "12개가 최대 개수입니다.", "오류", JOptionPane.WARNING_MESSAGE);
		}
		else {
			for(int i=0; i<model.getRowCount(); i++) {
				existMenu = model.getValueAt(i,0).equals(nameField.getText());
				if(existMenu)	break;
			}
			if(existMenu) {
				JOptionPane.showMessageDialog(this, "이미 존재하는 메뉴입니다.", "오류", JOptionPane.WARNING_MESSAGE);
				// 입력값 지워주는부분
				nameField.setText("");
				priceField.setText("");
				stockField.setText("");
				nameField.requestFocus();	// 커서 이름으로 돌아옴
			}
			else {
				Vector<String> v = new Vector<String>();
				
				try {
					int priceToInt = Integer.parseInt(priceField.getText());
					int stockToInt = Integer.parseInt(stockField.getText());
					if(priceToInt<=0 || stockToInt<=0 || nameField.getText().equals("")) {
						JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다.", "오류", JOptionPane.WARNING_MESSAGE);
					}
					else {
						v.add(nameField.getText());
						v.add(priceField.getText());
						v.add(stockField.getText());
						model.addRow(v);
					}
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다.", "오류", JOptionPane.WARNING_MESSAGE);
				} finally {
					// 입력값 지워주는부분
					nameField.setText("");
					priceField.setText("");
					stockField.setText("");
					nameField.requestFocus();	// 커서 이름으로 돌아옴
				}
			}
		}
	}
	
	public void deleteMenu() {
		if(SelectRow < 0) {
			JOptionPane.showMessageDialog(this, "삭제할 행을 선택하세요", "오류", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String msg = table.getValueAt(SelectRow, 0) + " 를 삭제하시겠습니까?";
		int ans = JOptionPane.showConfirmDialog(this, msg, "삭제확인완료", JOptionPane.YES_NO_OPTION);
		if(ans == JOptionPane.YES_OPTION) {
			model.removeRow(SelectRow);
			JOptionPane.showMessageDialog(this, "삭제되었습니다");
			SelectRow = -1;
			
			// 입력값 지워주는부분
			nameField.setText("");
			priceField.setText("");
			stockField.setText("");
			nameField.requestFocus();	// 커서 이름으로 돌아옴
		}
	}
	
	public void modMenu() {
		boolean existMenu=false;
		
		if(SelectRow < 0) {
			JOptionPane.showMessageDialog(this, "수정할 행을 선택하세요", "오류", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int ans = JOptionPane.showConfirmDialog(this, table.getValueAt(SelectRow, 0) + " 를 수정하시겠습니까?", "수정", JOptionPane.YES_NO_OPTION);
		if(ans == JOptionPane.YES_OPTION) {
			for(int i=0; i<model.getRowCount(); i++) {
				existMenu = model.getValueAt(i,0).equals(nameField.getText());
				if(existMenu)	break;
			}
			if(existMenu && !model.getValueAt(SelectRow,0).equals(nameField.getText())) {
				JOptionPane.showMessageDialog(this, "이미 존재하는 메뉴입니다.", "오류", JOptionPane.WARNING_MESSAGE);
				// 입력값 지워주는부분
				nameField.setText("");
				priceField.setText("");
				stockField.setText("");
				nameField.requestFocus();	// 커서 이름으로 돌아옴
			}
			
			else {
				try {
					int priceToInt = Integer.parseInt(priceField.getText());
					int stockToInt = Integer.parseInt(stockField.getText());
					if(priceToInt<=0 || stockToInt<=0 || nameField.getText().equals("")) {
						JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다.", "오류", JOptionPane.WARNING_MESSAGE);
					}
					else {
						model.setValueAt(nameField.getText(), SelectRow, 0);
						model.setValueAt(priceField.getText(), SelectRow, 1);
						model.setValueAt(stockField.getText(), SelectRow, 2);
					}
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다.", "오류", JOptionPane.WARNING_MESSAGE);
				} finally {
					// 입력값 지워주는부분
					nameField.setText("");
					priceField.setText("");
					stockField.setText("");
					nameField.requestFocus();	// 커서 이름으로 돌아옴
				}
			}
		}
	}
	
	public void saveMenu() {
		// 파일 저장
		FileWriter fw = null;
		try {
			fw = new FileWriter("./menu.txt");
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
	
	public boolean isSoldOut() {
		return soldOut;
	}
	
	public void stockReduction(String selectMenu) {
		FileReader fr = null;
		BufferedReader br = null;
		int changeRow=0,changeStock=0,changeNewStock=0;
		String stringStock="";
		try {
			fr = new FileReader("./menu.txt");
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
			
			for(int i=0; i<row; i++) {
				if(selectMenu.equals((String)model.getValueAt(i, 0))) {
					changeRow = i;
				}
			}
			changeStock = Integer.parseInt((String)model.getValueAt(changeRow, 2));
			if(changeStock <= 0) {
				soldOut = true;
			}
			else {
				changeNewStock = changeStock - 1;
				stringStock = String.valueOf(changeNewStock);
				model.setValueAt(stringStock, changeRow, 2);
				this.saveMenu();
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		if(ob == btnAdd) {
			this.addMenu();
		}
		else if(ob == btnDel) {
			this.deleteMenu();
		}
		else if(ob == btnMod) {
			this.modMenu();
		}
		else if(ob == btnSave) {
			this.saveMenu();
		}
	}
}