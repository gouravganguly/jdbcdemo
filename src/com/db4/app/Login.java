package com.db4.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.db4.database.ConnectionDB;

import java.awt.Color;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Canvas;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	int xx, xy;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setUndecorated(true);
					frame.setVisible(true);
					ConnectionDB cn = new ConnectionDB();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 298);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		initiate_db_conn();
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(0, 0, 211, 373);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				 xx = e.getX();
			     xy = e.getY();
			}
		});
		lblNewLabel_1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            Login.this.setLocation(x - xx, y - xy); 
			}
		});
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/images/bg.jpg")));
		
		Button button = new Button("Login");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{ 	
					rs = stmt.executeQuery("SELECT * from login where username='"+textField.getText()+"' and password='"+textField_1.getText()+"'");
					 if (rs.next()) {
					    Home h = new Home();
					    h.setVisible(true);
					   }
					rs.next();
					rs.close();	
				}
				catch (SQLException sqle){
					System.err.println("Wrong id or password:\n"+sqle.toString());
				}
				
			}
		});
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(241, 57, 83));
		button.setBounds(254, 174, 139, 28);
		contentPane.add(button);
		
		textField = new JTextField();
		textField.setBounds(254, 70, 139, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(254, 89, 139, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel = new JLabel("USERNAME");
		lblNewLabel.setBounds(254, 45, 77, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(254, 101, 77, 14);
		contentPane.add(lblPassword);
		
		textField_1 = new JTextField();
		textField_1.setBounds(254, 123, 139, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(392, 141, -136, 2);
		contentPane.add(separator_1);
		
		JLabel lblNewLabel_2 = new JLabel("   X");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		lblNewLabel_2.setForeground(new Color(241, 57, 83));
		lblNewLabel_2.setBounds(427, 0, 21, 14);
		contentPane.add(lblNewLabel_2);
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
}
