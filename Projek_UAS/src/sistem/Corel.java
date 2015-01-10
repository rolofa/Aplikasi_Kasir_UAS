package sistem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import javax.swing.table.*;

import tampilan.*;
import objek.*;

public class Corel 
{
	
	config config = new config();
	final public static boolean GUI = true, CUI = false;
	final public static int BARANG = 0, DETIL_TRANSAKSI = 1, TRANSAKSI = 2,
			USER = 3;

	public Login frmLogin = new Login(this);
	public Report frmReport;
	public Form_Transaksi frmFormTrans;
	public Form_Barang frmFormBarang;
	public Data_Transaksi frmDataTrans;
	public Data_Barang frmDataBarang;
	public Data_Users frmDataUsers;

	private Connection con;
	private User loggedUser;

	private static Calendar tgl = Calendar.getInstance();
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"E, dd MMMM yyyy");

	public Corel(boolean GUI) 
	{
		tesKoneksi();
		if (GUI) {
			frmLogin.setVisible(true);
		} else {
			
		}
	}

	private void tesKoneksi() 
	{
		try {
			Class.forName(config.Database_Driver);
			con = DriverManager.getConnection(config.URL, config.username, config.password);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
	}

	public void printReport(Transaksi trns) 
	{
		int ID = Operator.getLastIDTrans(con);

		String header = "\n"
				+ "\t\t     Herbal Shop"
				+ "\n\t\t  STIKOM Surabaya "
				+ "\n\t \t\t  No. "
				+ ID
				+ "\nKasir : "
				+ loggedUser.getUsername()
				+ "\n=================================================================", data = "", footer = "\n"
				+ "\n---------------------------------------"
				+ "\nTotal : "
				+ trns.getTotalItem()
				+ " Item      "
				+ trns.getTotalHrg()
				+ "\n================================================================="
				+ "\nTgl " + trns.getTglAsString();
		for (int i = 0; i < trns.getDetilTransaksi().size(); i++) 
		{
			Detil_Transaksi dt = trns.getDetilTransaksi().get(i);
			data = data + "\n" + dt.getBarang().getNama() + "\t"
					+ dt.getquantity() + "x\t" + dt.getquantity()
					* dt.getBarang().getHarga();
		}
		frmReport = new Report(this,
				new String[] { header, data, footer });
		frmReport.setVisible(true);
	}

	public void printReport(Vector<Barang> brg) 
	{
		String header = "\n"
				+ "\t\t     Herbal Shop"
				+ "\n\t\t  STIKOM Surabaya"
				+ "\n\t   Stok barang tgl "
				+ getDateAsString()
				+ "\nNama : "
				+ loggedUser.getUsername()
				+ "\n===============================================================", data = "", footer = "\n===============================================================";

		
		for (int i = 0; i < brg.size(); i++) {
			
			Barang _brg = brg.get(i);
			data = data + "\n  " + _brg.getNama();
			for (int a = 0; a < 25 - _brg.getNama().length(); a++) 
			{
				data = data + " ";
			}
			
			data = data + _brg.getStok();
			for (int a = 0; a < 10 - ("" + _brg.getStok()).length(); a++) 
			{
				data = data + " ";
			}			
			
			
			data += _brg.getHarga();
		}
		frmReport = new Report(this,
				new String[] { header, data, footer });
		frmReport.setVisible(true);
	}

	public void login(User usr) 
	{
		this.loggedUser = new User(usr);
		if (usr.isAdmin()) 
		{
			frmFormBarang = new Form_Barang(this);
			frmFormBarang.setVisible(true);
		} else {
			frmFormTrans = new Form_Transaksi(this);
			frmFormTrans.setVisible(true);
		}
	}

	public void logout() 
	{
		if (loggedUser.isAdmin()) 
		{
			frmFormBarang.dispose();
		} else {
			frmFormTrans.dispose();
		}
		frmLogin.dispose();
		frmLogin = new Login(this);
		frmLogin.setVisible(true);
		loggedUser = null;
	}

	public User getLoggedInUser() 
	{
		return loggedUser;
	}

	public Connection getConnection() 
	{
		return con;
	}

	public Date getDate() 
	{
		return (Date) tgl.getTime();
	}

	public String getDateAsString() 
	{
		return formatter.format(tgl.getTime());
	}

	public void reloadForm() 
	{

	}
}
