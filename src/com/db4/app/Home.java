package com.db4.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;



public class Home extends JFrame implements ActionListener{

	
	
	private JPanel contentPane;
	private final JLayeredPane layeredPane = new JLayeredPane();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	
	String cmd = null;

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private static QueryTableModel TableModel = new QueryTableModel();
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 752, 473);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		layeredPane.setBounds(369, 0, 367, 208);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		initiate_db_conn();
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		layeredPane.add(panel_1, "name_404985013877600");
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Electricity");
		lblNewLabel.setBounds(159, 5, 94, 14);
		panel_1.add(lblNewLabel);
		
		JLabel lblId = new JLabel("Consumer ID");
		lblId.setBounds(32, 43, 79, 14);
		panel_1.add(lblId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(32, 67, 58, 14);
		panel_1.add(lblName);
		
		JLabel lblId_1_1 = new JLabel("Paid Amount");
		lblId_1_1.setBounds(32, 92, 70, 14);
		panel_1.add(lblId_1_1);
		
		textField = new JTextField();
		textField.setBounds(113, 40, 96, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(113, 64, 96, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(113, 89, 96, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setForeground(Color.DARK_GRAY);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{ 	
					int balance=0;
					rs = stmt.executeQuery("SELECT balance from subscriber_info where cust_id="+textField.getText());
					 while (rs.next()) {
					      balance = rs.getInt("balance");
					   }
					int bal = Integer.parseInt(textField_2.getText())+balance;
					
					String updateTemp ="UPDATE subscriber_info SET name = '"+textField_1.getText()+
							"', balance = "+bal+
							" where cust_id = "+textField.getText();
					
					
					stmt.executeUpdate(updateTemp);
					//these lines do nothing but the table updates when we access the db.
					rs = stmt.executeQuery("SELECT * from subscriber_info ");
					rs.next();
					rs.close();	
				}
				catch (SQLException sqle){
					System.err.println("Error with  update:\n"+sqle.toString());
				}
				finally{
					TableModel.refreshFromDB(stmt);
				}
			}
		});
		btnSubmit.setBounds(113, 120, 96, 23);
		panel_1.add(btnSubmit);
		
		JButton btnExportElectricityData = new JButton("Export Electricity Data");
		btnExportElectricityData.setForeground(Color.DARK_GRAY);
		btnExportElectricityData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmd = "select * from subscriber_info where subscriptions like '%Electricity%';";
				System.out.println(cmd);
				try{					
					rs= stmt.executeQuery(cmd); 	
					writeToFile(rs,"ElectricityData.csv");
				}
				catch(Exception e1){e1.printStackTrace();}
			
			}
		});
		btnExportElectricityData.setBounds(76, 169, 191, 23);
		panel_1.add(btnExportElectricityData);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		layeredPane.add(panel_2, "name_405015882654900");
		panel_2.setLayout(null);
		
		JLabel lblWifi = new JLabel("Wifi");
		lblWifi.setBounds(173, 5, 57, 14);
		panel_2.add(lblWifi);
		
		JLabel lblConsumerId = new JLabel("Consumer ID");
		lblConsumerId.setBounds(42, 34, 72, 14);
		panel_2.add(lblConsumerId);
		
		JLabel lblPlan = new JLabel("Plan");
		lblPlan.setBounds(42, 84, 57, 14);
		panel_2.add(lblPlan);
		
		JLabel lblPaidAmount = new JLabel("Paid Amount");
		lblPaidAmount.setBounds(42, 109, 72, 14);
		panel_2.add(lblPaidAmount);
		
		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(42, 59, 48, 14);
		panel_2.add(lblName_1);
		
		textField_3 = new JTextField();
		textField_3.setBounds(122, 31, 96, 20);
		panel_2.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(122, 56, 96, 20);
		panel_2.add(textField_4);
		textField_4.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(Color.DARK_GRAY);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Vodafone", "air3", "Docomo"}));
		comboBox.setBounds(122, 80, 96, 22);
		panel_2.add(comboBox);
		
		textField_5 = new JTextField();
		textField_5.setBounds(122, 106, 96, 20);
		panel_2.add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnSubmit_1 = new JButton("Submit");
		btnSubmit_1.setForeground(Color.DARK_GRAY);
		btnSubmit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{ 	
					int balance=0;
					rs = stmt.executeQuery("SELECT balance from subscriber_info where cust_id="+textField_3.getText());
					 while (rs.next()) {
					      balance = rs.getInt("balance");
					   }
					int bal = Integer.parseInt(textField_5.getText())+balance;
					
					String updateTemp ="UPDATE subscriber_info SET name = '"+textField_4.getText()+
							"', plan = '"+comboBox.getSelectedItem().toString()+
							"', balance = "+bal+
							" where cust_id = "+textField_3.getText();
					
					
					stmt.executeUpdate(updateTemp);
					//these lines do nothing but the table updates when we access the db.
					rs = stmt.executeQuery("SELECT * from subscriber_info ");
					rs.next();
					rs.close();	
				}
				catch (SQLException sqle){
					System.err.println("Error with  update:\n"+sqle.toString());
				}
				finally{
					TableModel.refreshFromDB(stmt);
				}
			}
		});
		btnSubmit_1.setBounds(122, 137, 96, 23);
		panel_2.add(btnSubmit_1);
		
		JButton btnNewButton_1 = new JButton("Export WiFi Data");
		btnNewButton_1.setForeground(Color.DARK_GRAY);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmd = "select * from subscriber_info where subscriptions like '%WiFi%';";
				System.out.println(cmd);
				try{					
					rs= stmt.executeQuery(cmd); 	
					writeToFile(rs,"WiFiData.csv");
				}
				catch(Exception e1){e1.printStackTrace();}
			}
		});
		btnNewButton_1.setBounds(95, 171, 148, 23);
		panel_2.add(btnNewButton_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		layeredPane.add(panel_3, "name_405022395279500");
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Mobile");
		lblNewLabel_1.setBounds(167, 5, 69, 14);
		panel_3.add(lblNewLabel_1);
		
		JLabel lblMobileNo = new JLabel("Mobile No.");
		lblMobileNo.setBounds(46, 37, 81, 14);
		panel_3.add(lblMobileNo);
		
		JLabel lblName_2 = new JLabel("Name");
		lblName_2.setBounds(46, 62, 48, 14);
		panel_3.add(lblName_2);
		
		JLabel lblBillPaid = new JLabel("Bill paid");
		lblBillPaid.setBounds(46, 87, 48, 14);
		panel_3.add(lblBillPaid);
		
		textField_6 = new JTextField();
		textField_6.setBounds(119, 34, 96, 20);
		panel_3.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(119, 59, 96, 20);
		panel_3.add(textField_7);
		textField_7.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setBounds(119, 84, 96, 20);
		panel_3.add(textField_8);
		textField_8.setColumns(10);
		
		JButton btnSubmit_2 = new JButton("Submit");
		btnSubmit_2.setForeground(Color.DARK_GRAY);
		btnSubmit_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{ 	
					int balance=0;
					rs = stmt.executeQuery("SELECT balance from subscriber_info where mobile="+textField_6.getText());
					 while (rs.next()) {
					      balance = rs.getInt("balance");
					   }
					int bal = Integer.parseInt(textField_8.getText())+balance;
					
					String updateTemp ="UPDATE subscriber_info SET name = '"+textField_7.getText()+
							"', balance = "+bal+
							" where mobile = "+textField_6.getText();
					
					
					stmt.executeUpdate(updateTemp);
					
					rs = stmt.executeQuery("SELECT * from subscriber_info ");
					rs.next();
					rs.close();	
				}
				catch (SQLException sqle){
					System.err.println("Error with  update:\n"+sqle.toString());
				}
				finally{
					TableModel.refreshFromDB(stmt);
				}
			}
		});
		btnSubmit_2.setBounds(119, 115, 96, 23);
		panel_3.add(btnSubmit_2);
		
		JButton btnNewButton_2 = new JButton("Export Mobile Data");
		btnNewButton_2.setForeground(Color.DARK_GRAY);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmd = "select * from subscriber_info where subscriptions like '%Mobile%';";
				System.out.println(cmd);
				try{					
					rs= stmt.executeQuery(cmd); 	
					writeToFile(rs,"MobileData.csv");
				}
				catch(Exception e1){e1.printStackTrace();}
			}
		});
		btnNewButton_2.setBounds(82, 173, 168, 23);
		panel_3.add(btnNewButton_2);
		
		JPanel new_cus = new JPanel();
		layeredPane.add(new_cus, "name_412303509449700");
		new_cus.setLayout(null);
		
		JLabel lblAddCustomer = new JLabel("Add Customer");
		lblAddCustomer.setBounds(149, 5, 101, 14);
		new_cus.add(lblAddCustomer);
		
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setBounds(44, 32, 101, 14);
		new_cus.add(lblCustomerName);
		
		JLabel lblNewLabel_2 = new JLabel("Balance");
		lblNewLabel_2.setBounds(44, 57, 69, 14);
		new_cus.add(lblNewLabel_2);
		
		JLabel lblPlan_1 = new JLabel("Plan");
		lblPlan_1.setBounds(44, 82, 48, 14);
		new_cus.add(lblPlan_1);
		
		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setBounds(44, 107, 48, 14);
		new_cus.add(lblMobile);
		
		textField_9 = new JTextField();
		textField_9.setBounds(146, 29, 96, 20);
		new_cus.add(textField_9);
		textField_9.setColumns(10);
		
		textField_10 = new JTextField();
		textField_10.setBounds(146, 54, 96, 20);
		new_cus.add(textField_10);
		textField_10.setColumns(10);
		
		textField_11 = new JTextField();
		textField_11.setBounds(146, 79, 96, 20);
		new_cus.add(textField_11);
		textField_11.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setBounds(146, 104, 96, 20);
		new_cus.add(textField_12);
		textField_12.setColumns(10);
		
		JLabel lblSubscriptions = new JLabel("Subscriptions");
		lblSubscriptions.setBounds(44, 132, 79, 14);
		new_cus.add(lblSubscriptions);
		
		JCheckBox elec = new JCheckBox("Electricity");
		elec.setBounds(121, 131, 97, 23);
		new_cus.add(elec);
		
		JCheckBox wifi = new JCheckBox("WiFi");
		wifi.setBounds(121, 152, 97, 23);
		new_cus.add(wifi);
		
		JCheckBox mobile = new JCheckBox("Mobile");
		mobile.setBounds(121, 178, 97, 23);
		new_cus.add(mobile);
		
		JButton btnSubmit_3 = new JButton("Submit");
		btnSubmit_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String subs = "";
				if(elec.isSelected()) {
					subs+=elec.getText();
				}
				if(wifi.isSelected()) {
					subs+="&"+wifi.getText();
				}
				if(mobile.isSelected()) {
					subs+="&"+mobile.getText();
				}
				System.out.println(subs);
				try
				{
					
					
					String updateTemp ="INSERT INTO subscriber_info VALUES("+
					null +",'"+textField_9.getText()+"',"+textField_10.getText()+",'"+textField_11.getText()+"','"+textField_12.getText()+"','"+subs+"');";

					stmt.executeUpdate(updateTemp);

				}
				catch (SQLException sqle)
				{
					System.err.println("Error with  insert:\n"+sqle.toString());
				}
				finally
				{
					TableModel.refreshFromDB(stmt);
				}
			}
		});
		btnSubmit_3.setBounds(237, 178, 93, 23);
		new_cus.add(btnSubmit_3);
		
		JPanel left = new JPanel();
		left.setBackground(Color.LIGHT_GRAY);
		left.setBounds(10, 0, 349, 208);
		contentPane.add(left);
		left.setLayout(null);
		
		JButton btnI = new JButton("Electricity");
		btnI.setForeground(Color.DARK_GRAY);
		btnI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				layeredPane.removeAll();
				layeredPane.add(panel_1);
				layeredPane.repaint();
				layeredPane.revalidate();

			}
		});
		btnI.setBounds(10, 88, 97, 23);
		left.add(btnI);
		
		JButton btnU = new JButton("WiFi");
		btnU.setForeground(Color.DARK_GRAY);
		btnU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panel_2);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		btnU.setBounds(117, 88, 89, 23);
		left.add(btnU);
		
		JButton btnD = new JButton("Mobile");
		btnD.setForeground(Color.DARK_GRAY);
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(panel_3);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		btnD.setBounds(216, 88, 89, 23);
		left.add(btnD);
		
		JButton btnNewCustomer = new JButton("New Customer");
		btnNewCustomer.setForeground(Color.DARK_GRAY);
		btnNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(new_cus);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		btnNewCustomer.setBounds(10, 11, 161, 23);
		left.add(btnNewCustomer);
		
		JButton btnNewButton = new JButton("Delete Customer");
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String updateTemp ="DELETE FROM subscriber_info WHERE cust_id = "+textField_13.getText()+";"; 
					stmt.executeUpdate(updateTemp);

				}
				catch (SQLException sqle)
				{
					System.err.println("Error with delete:\n"+sqle.toString());
				}
				finally
				{
					TableModel.refreshFromDB(stmt);
				}
			}
		});
		btnNewButton.setBounds(10, 45, 161, 23);
		left.add(btnNewButton);
		
		textField_13 = new JTextField();
		textField_13.setBounds(188, 46, 124, 20);
		left.add(textField_13);
		textField_13.setColumns(10);
		
		JButton btnExportCustomerData = new JButton("Export All Customer Data");
		btnExportCustomerData.setForeground(Color.DARK_GRAY);
		btnExportCustomerData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmd = "select * from subscriber_info;";
				System.out.println(cmd);
				try{					
					rs= stmt.executeQuery(cmd); 	
					writeToFile(rs,"alldata.csv");
				}
				catch(Exception e1){e1.printStackTrace();}
			}
		});
		btnExportCustomerData.setBounds(10, 131, 218, 23);
		left.add(btnExportCustomerData);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 219, 716, 204);
		contentPane.add(scrollPane);
		
		table_2 = new JTable(TableModel);
		scrollPane.setViewportView(table_2);
		table_2.setBackground(SystemColor.activeCaption);
		
		
	
		TableModel.refreshFromDB(stmt);
	}
	
	public void initiate_db_conn()
	{
		try
		{
			
			String url="jdbc:mysql://localhost:3306/newjava";

			con = DriverManager.getConnection(url, "root", "root");
			System.out.println("Connected!");
			//Create a generic statement which is passed to the TestInternalFrame1
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n"+e.getMessage());
		}
	}
	private void writeToFile(ResultSet rs,String name){
		try{
			System.out.println("In writeToFile");
			FileWriter outputFile = new FileWriter(name);
			PrintWriter printWriter = new PrintWriter(outputFile);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			for(int i=0;i<numColumns;i++){
				printWriter.print(rsmd.getColumnLabel(i+1)+",");
			}
			printWriter.print("\n");
			while(rs.next()){
				for(int i=0;i<numColumns;i++){
					printWriter.print(rs.getString(i+1)+",");
				}
				printWriter.print("\n");
				printWriter.flush();
			}
			printWriter.close();
		}
		catch(Exception e){e.printStackTrace();}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
