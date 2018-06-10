import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import java.io.*;
//import java.util.*;

@SuppressWarnings("serial")
public class Money extends JFrame implements ActionListener {
	Container cp;
	JTextField change500,change100,change50, change10;
	JButton btnMod,btnSave;
	DefaultTableModel model;
	JTable table;
	JScrollPane jsp;
	int[] ouputMoney;
	int SelectRow = -1;
	int c500;
	int c100;
	int c50;
	int c10;
    
	Money(String title) {
		super(title);
		if(title == "잔돈 관리") {
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
		//상단

		change500 = new JTextField(3);
		change100 = new JTextField(3);
		change50 = new JTextField(3);
		change10 = new JTextField(3);

		pTop.add(new JLabel("500원"));
		pTop.add(change500);
		pTop.add(new JLabel("100원"));
		pTop.add(change100);
		pTop.add(new JLabel("50원"));
		pTop.add(change50);
		pTop.add(new JLabel("10원"));
		pTop.add(change10);
      
		//테이블
		String[] title = {"500원","100원","50원","10원"};
		String[] num = {"0","0","0","0"};
      
		model = new DefaultTableModel(title,0);
		model.addRow(num);
		table = new JTable(model);
		table.addMouseListener(new TableEvent());
		jsp = new JScrollPane(table);
		cp.add("Center",jsp);

		//하단
		btnMod = new JButton("수정");
		btnSave = new JButton("저장");

		pBottom.add(btnMod);
		pBottom.add(btnSave);

		btnMod.addActionListener(this);
		btnSave.addActionListener(this);

		this.openMoney();
	}
   
	public void openMoney() {
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader("./money.txt");
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
		public void mouseClicked(MouseEvent e) {
			SelectRow = table.getSelectedRow();
			change500.setText((String)table.getValueAt(SelectRow,0));
			change100.setText((String)table.getValueAt(SelectRow,1));
			change50.setText((String)table.getValueAt(SelectRow,2));
			change10.setText((String)table.getValueAt(SelectRow,3));
		}
	}
	public void actionPerformed(ActionEvent e) {
	   if(e.getSource() == btnMod) {
    	  this.modMoney();
      }
      else if(e.getSource() == btnSave) {
    	  this.saveMoney();
      }
   }
   
	public void modMoney() {
	   if(SelectRow < 0) {
		   JOptionPane.showMessageDialog(this,"수정할 행을 선택하세요","오류",JOptionPane.WARNING_MESSAGE);
		   return;
	   }
	   try {
		   c500 = Integer.parseInt(change500.getText());
		   c100 = Integer.parseInt(change100.getText());
		   c50 = Integer.parseInt(change50.getText());
		   c10 = Integer.parseInt(change10.getText());
		   if(c500<0 || c100<0 || c50<0 || c10<0) {
			   JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다.", "오류", JOptionPane.WARNING_MESSAGE);
		   }
		   else {
			   model.setValueAt(c500,SelectRow,0);
			   model.setValueAt(c100,SelectRow,1);
			   model.setValueAt(c50,SelectRow,2);
			   model.setValueAt(c10,SelectRow,3);
		   }
	   } catch(NumberFormatException e) {
		   JOptionPane.showMessageDialog(this, "잘못 입력하셨습니다.", "오류", JOptionPane.WARNING_MESSAGE);
	   } finally {
		   SelectRow = -1;       //selectRow초기화
		   //입력값 지우는 부분
		   change500.setText("");
		   change100.setText("");
		   change50.setText("");
		   change10.setText("");
		   change500.requestFocus();      //커서 500원 입력으로 돌아옴
	   }
	}
   
	public void saveMoney() {
		//파일 저장
		FileWriter fw = null;
		try {
			fw = new FileWriter("./money.txt");
			fw.write(String.valueOf(model.getRowCount()) + "\n");
			fw.write(String.valueOf(model.getColumnCount()) + "\n");
			for(int i=0; i<model.getRowCount(); i++) {
				for(int j=0; j<model.getColumnCount(); j++) {
					int data = (Integer)model.getValueAt(i,j);
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
	
	public int[] changeMoney(int[] inputMoney) {
		
		return ouputMoney;
	}
}