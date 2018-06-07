import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
//import java.awt.event.*;
import java.io.*;
//import java.util.*;

@SuppressWarnings("serial")
public class Sales extends JFrame {
	Container cp;
	DefaultTableModel model;
	JTable table;
	JScrollPane jsp;
	int SelectRow = -1;
	int salesSum;
	
	
	Sales(String title) {
		super(title);
		cp = this.getContentPane();
		this.setBounds(100,100,400,400);
		this.setDesign();
		this.setVisible(true);
	}
	
	public void setDesign() {
		JPanel pBottom = new JPanel();
		cp.add("South",pBottom);
		// 중간
		String[] title = {"상품명","가격","재고","매출 합계"};
		model = new DefaultTableModel(title, 0);
		table = new JTable(model);
		jsp = new JScrollPane(table);
		cp.add("Center",jsp);
		
		this.openSales();
	}
	
	public void openSales() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("./sales.dat");
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
	
	public void saveSales() {
		// 파일 저장
		FileWriter fw = null;
		try {
			fw = new FileWriter("./sales.dat");
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
	
	public void addSales() {
		BufferedWriter bw = null;
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter("./sales.dat");
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);

			fw.write(String.valueOf(model.getRowCount()) + "\n");
			fw.write(String.valueOf(model.getColumnCount()) + "\n");
			for(int i=0; i<model.getRowCount(); i++) {
				for(int j=0; j<model.getColumnCount(); j++) {
					String data = (String)model.getValueAt(i, j);
					pw.write(data + "\n");
				}
			}
		} catch(IOException e2) {
			e2.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}
}
