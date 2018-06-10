import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class Sales extends JFrame {
	Container cp = this.getContentPane();
	String[] title = {"상품명","가격"};
	DefaultTableModel model = new DefaultTableModel(title, 0);
	JTable table = new JTable(model);
	JScrollPane jsp;
	int SelectRow = -1;
	int salesSum = 0;
	
	Sales(String title) {
		super(title);
		if(title == "매출 관리") {
			this.setBounds(100,100,400,400);
			this.setDesign();
			this.setVisible(true);
		}
		if(title == "매출 추가") {
		}
	}
	
	public void setDesign() {
		JLabel salesSumLabel = new JLabel("매출 합계 : " + this.getSalesSum());
		JPanel panel = new JPanel();
		panel.add(salesSumLabel);
		cp.add("South",panel);
		
		jsp = new JScrollPane(table);
		cp.add("Center",jsp);
		
		this.openSales();
	}
	
	
	public void openSales() {
		FileReader fr = null;
		BufferedReader br = null;
		int row=this.getRowNum();
		try {
			File salesFile = new File("./sales.txt");
			if(!salesFile.exists()) {
				salesFile.createNewFile();
			}
			fr = new FileReader("./sales.txt");
			br = new BufferedReader(fr);
			
			String[] str = new String[2];
			model.setRowCount(0);

			for(int i=0; i<row; i++) {
				for(int j=0; j<2; j++) {
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
	
	public int getRowNum() {
		FileReader fr = null;
		BufferedReader br = null;
		int num=0;
		try {
			File salesFile = new File("./sales.txt");
			if(!salesFile.exists()) {
				salesFile.createNewFile();
			}
			fr = new FileReader("./sales.txt");
			br = new BufferedReader(fr);
			
			while(br.readLine() != null) {
				num++;
			}
			
			br.close();
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
		} catch(NumberFormatException e1) {
			e1.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		return num/2;
	}
	
	public int getSalesSum() {
		FileReader fr = null;
		BufferedReader br = null;
		int row=this.getRowNum();
		try {
			File salesFile = new File("./sales.txt");
			if(!salesFile.exists()) {
				salesFile.createNewFile();
			}
			fr = new FileReader("./sales.txt");
			br = new BufferedReader(fr);
			
			String[] str = new String[2];

			for(int i=0; i<row; i++) {
				for(int j=0; j<2; j++) {
					String data = br.readLine();
					str[j] = data;
				}
				salesSum = salesSum + Integer.parseInt(str[1]);
			}
			br.close();
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
		} catch(NumberFormatException e1) {
			e1.printStackTrace();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		return salesSum;
	}
	
	public void saveSales() {
		// 파일 저장
		FileWriter fw = null;
		try {
			fw = new FileWriter("./sales.txt",true);
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
	
	
	public void addSales(String selectMenu, String selectPrice) {
			Vector<String> v = new Vector<String>();
			v.add(selectMenu);
			v.add(selectPrice);
			model.addRow(v);
	}
}
