package tampilan;

import java.awt.*;

import javax.swing.*;

import sistem.*;
import sup.tampilan.CustActionListener;

public class Login extends JFrame 
{

	final private Corel core;

	private JButton btnLogin;
	private JTextField txUsr;
	private JPasswordField txPsw;
	private JLabel lblUsr, lblPsw;

	private Container container;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public Login(Corel core) 
	{
		super("Login");
		this.core = core;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(265, 175);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setResizable(false);
		
		ImageIcon back = new ImageIcon(Login.class.getResource("/gambar/orange1.jpg"));
				
		JLabel labelHeader = new JLabel("Aplikasi Penjualan Toko Obat Herbal");
		labelHeader.setBounds(25,0,250,35);
		
		container = this.getContentPane();
		container.setLayout(null);
		container.setBackground(Color.orange);
		btnLogin = new JButton("Login");
		txUsr = new JTextField(15);
		txPsw = new JPasswordField(15);
		lblUsr = new JLabel("Username");
		lblPsw = new JLabel("Password");
		
		lblUsr.setHorizontalAlignment(JLabel.RIGHT);
		lblPsw.setHorizontalAlignment(JLabel.RIGHT);

		lblUsr.setBounds(10, 25, 60, 50);
		txUsr.setBounds(75, 40, 170, 25);
		lblPsw.setBounds(10, 62, 60, 50);
		txPsw.setBounds(75, 75, 170, 25);
		btnLogin.setBounds(165, 108, 80, 25);

		btnLogin.addActionListener(new CustActionListener(core, this, btnLogin));
		container.add(labelHeader);
		container.add(lblUsr);
		container.add(txUsr);
		container.add(lblPsw);
		container.add(txPsw);
		container.add(btnLogin);
		JLabel Background = new JLabel();
		Background.setBounds(-45,0,320,223);
		getContentPane().add(Background);
		Background.setIcon(back);
		
		
		
	}

	public String getUser() 
	{
		return txUsr.getText();
	}

	public String getPass() 
	{
		return txPsw.getText();
	}
}
