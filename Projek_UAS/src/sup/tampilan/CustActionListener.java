package sup.tampilan;

import java.awt.event.*;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.sun.org.apache.bcel.internal.generic.FMUL;

import objek.*;
import sistem.*;
import tampilan.*;

public class CustActionListener implements ActionListener {

	public final static int LOGIN = 0, FORM_TRANSAKSI_ADDBARANG = 1,
			FORM_TRANSAKSI_SUBMIT = 2, FORM_TRANSAKSI_SELECTITEM = 3,
			LOGOUT = 4, SHOW_DATA_BARANG = 5, SHOW_DATA_USERS = 6,SHOW_DATA_TRANSAKSI = 7,
			CETAK_BARANG = 8, HAPUS_BARANG = 9, TAMBAH_BARANG = 10,
			HAPUS_TRANS = 11;
	private Corel core;

	private JFrame jf;
	private Login frmLogin;
	private Report frmReport;
	private Form_Barang frmFormBarang;
	private Data_Users frmDataUsers;
	private Form_Transaksi frmFormTrans;
	private Data_Transaksi frmDataTrans;
	private Data_Barang frmDataBarang;

	private JButton btn;
	private JComboBox cb;
	private JMenuItem mi;
	private JTable tbl;

	private JTextField tf;

	private int mode;

	public CustActionListener(Corel core, Login frm, JButton btn) {
		this.core = core;
		this.frmLogin = frm;
		this.btn = btn;
		mode = LOGIN;
	}

	public CustActionListener(Corel core, Form_Transaksi frm, JButton btn) {
		this.core = core;
		this.frmFormTrans = frm;
		this.btn = btn;
		this.mode = FORM_TRANSAKSI_SUBMIT;
	}

	public CustActionListener(Form_Transaksi frm, JButton btn) {
		this.frmFormTrans = frm;
		this.btn = btn;
		this.mode = FORM_TRANSAKSI_ADDBARANG;
	}

	public CustActionListener(Form_Transaksi frm, JComboBox cb) {
		this.frmFormTrans = frm;
		this.cb = cb;
		mode = FORM_TRANSAKSI_SELECTITEM;
	}

	public CustActionListener(Corel core, JFrame jf, JMenuItem mi, int mode) {
		this.core = core;
		this.jf = jf;
		this.mi = mi;
		this.mode = mode;
	}

	public CustActionListener(Corel core, Form_Barang frm, JMenuItem mi,
			int mode) {
		this.core = core;
		this.frmFormBarang = frm;
		this.mi = mi;
		this.mode = mode;
	}

	public CustActionListener(Corel core, Form_Barang frm, JTable tbl,JButton btn,
			int mode) {
		this.core = core;
		this.frmFormBarang = frm;
		this.btn = btn;
		this.tbl = tbl;
		this.mode = mode;
	}
	
	public CustActionListener(Corel core, Data_Users frm, JTable tbl,JButton btn,
			int mode) {
		this.core = core;
		this.frmDataUsers = frm;
		this.btn = btn;
		this.tbl = tbl;
		this.mode = mode;
	}

	public CustActionListener(Corel core, Data_Transaksi frm, JTable tbl,
			JButton btn, int mode) 
	{
		this.core = core;
		this.tbl = tbl;
		this.frmDataTrans = frm;
		this.btn = btn;
		this.mode = mode;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (mode) {
		case LOGIN:
			User user = Operator.checkLogin(new User(frmLogin.getUser(),
					frmLogin.getPass(), false), core.getConnection());
			if (user == null) {
				JOptionPane.showMessageDialog(frmLogin,
						"Username atau password tidak tepat");
			} else {
				frmLogin.setVisible(false);
				core.login(user);
			}
			break;
		case FORM_TRANSAKSI_SELECTITEM:
			int index = cb.getSelectedIndex();
			frmFormTrans.fillFormByIndex(index);
			break;
		case FORM_TRANSAKSI_ADDBARANG:
			frmFormTrans.addBarangToTable(new Detil_Transaksi(frmFormTrans
					.getSelectedBarang(), frmFormTrans.getQtyBarang()));
			break;
		case FORM_TRANSAKSI_SUBMIT:
			frmFormTrans.submitToDB();
			break;
		case LOGOUT:
			core.logout();
			break;
		case SHOW_DATA_BARANG:
			if (core.frmDataBarang == null) {
			} else {
				core.frmDataBarang.dispose();
			}
			core.frmDataBarang = new Data_Barang(core);
			core.frmDataBarang.setVisible(true);
			break;
		case SHOW_DATA_USERS:
			if (core.frmDataUsers == null) {
			} else {
				core.frmDataUsers.dispose();
			}
			core.frmDataUsers = new Data_Users(core);
			core.frmDataUsers.setVisible(true);
			break;
		case SHOW_DATA_TRANSAKSI:
			if (core.frmDataTrans == null) {
			} else {
				core.frmDataTrans.dispose();
			}
			core.frmDataTrans = new Data_Transaksi(core);
			core.frmDataTrans.setVisible(true);
			break;
		case CETAK_BARANG:
			core.printReport(frmFormBarang.getListBarang());
			break;
		case TAMBAH_BARANG:
			frmFormBarang.submitToDB();
			
			break;
		case HAPUS_BARANG:
			if(tbl == null){
			}else{
				if (Operator.hapusBarang(frmFormBarang.getSelectedBarang(),
						core.getConnection())) {
					JOptionPane.showMessageDialog(frmFormBarang,
							"Data barang dihapus");
				}
				frmFormBarang.resetForm();
			}
			break;
		case HAPUS_TRANS:
			if(tbl == null){
			}else{
				if (Operator.hapusTransaksi(frmDataTrans.getTransaksi(),
						core.getConnection())) {
					JOptionPane.showMessageDialog(frmDataTrans,
							"Data transaksi dihapus");
				}
				frmDataTrans.resetForm();
			}
			break;
		}

	}
}
