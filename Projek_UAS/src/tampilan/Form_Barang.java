package tampilan;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import objek.Barang;
import sistem.*;
import sup.tampilan.CustActionListener;
import sup.tampilan.CustKeyListener;

public class Form_Barang extends JFrame 
{
	private Corel core;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextField tfId, tfNama, tfidSupp, tfHarga, tfStok;
	private JTable tbl;
	private JLabel lbId, lbNama, lbIdSupp, lbHarga, lbStok;

	private Vector<Barang> barang = new Vector<Barang>();
	private Vector<String> nmBarang = new Vector<String>();

	public Form_Barang(final Corel core) 
	{
		super("Formulir Barang");
		this.core = core;
		setResizable(false);

		setSize(526, 406);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		getContentPane().setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(Color.orange);
		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);
		ImageIcon back = new ImageIcon(Form_Barang.class.getResource("/gambar/orange1.jpg"));

		JMenu menuUser = new JMenu(
				core.getLoggedInUser().isAdmin() ? "Admin " : " "
						+ core.getLoggedInUser().getUsername());
		JMenuItem miLogOut = new JMenuItem("Log Out");
		miLogOut.addActionListener(new CustActionListener(core, this, miLogOut,
				CustActionListener.LOGOUT));

		JMenu menuTrans = new JMenu("Transaksi");
		JMenuItem miTransData = new JMenuItem("Data Transaksi");
		miTransData.addActionListener(new CustActionListener(core, this,
				miTransData, CustActionListener.SHOW_DATA_TRANSAKSI));
		
		JMenu menuBarang = new JMenu("Barang");		
		JMenuItem miBarangCetak = new JMenuItem("Cetak Barang");
		miBarangCetak.addActionListener(new CustActionListener(core, this,
				miBarangCetak, CustActionListener.CETAK_BARANG));
		
		JMenu menuUsers = new JMenu("User");
		JMenuItem miUsers = new JMenuItem("Data Users");
		miUsers.addActionListener(new CustActionListener(core, this,
				miUsers, CustActionListener.SHOW_DATA_USERS));
		
				
		menu.add(menuUser);
		menuUser.add(miLogOut);

		menu.add(menuTrans); //miTransCetak
		
		menuTrans.add(miTransData);
		menu.add(menuBarang);//miBarangData
		
		menuBarang.add(miBarangCetak);
		
		menu.add(menuUsers);
		menuUsers.add(miUsers);
		
		ResultSet rs = Operator.getListBarang(core.getConnection());
		try {
			while (rs.next()) 
			{
				barang.add(new Barang(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		tbl = new JTable(Operator.resultSetToTableModel(Operator
				.getListBarang(core.getConnection())));
		Operator.disableTableEdit(tbl);
		JPanel panTbl = new JPanel();

		panTbl.setLayout(new BorderLayout());
		panTbl.setBackground(Color.white);
		panTbl.add(new JScrollPane(tbl), BorderLayout.CENTER);
		tfId = new JTextField();
		tfNama = new JTextField();
		tfidSupp = new JTextField();
		tfHarga = new JTextField();
		tfStok = new JTextField();

		tfId.setBounds(115,225, 170, 20);
		tfNama.setBounds(115, 250, 170, 20);
		tfidSupp.setBounds(115, 275, 170, 20);
		
		tfHarga.setBounds(115, 300, 170, 20);
		tfStok.setBounds(115, 325, 170, 20);
		tfStok.addKeyListener(new CustKeyListener(core, this, tfStok,
				CustKeyListener.NUMBER_ONLY));

		panTbl.setBounds(10, 10, 500, 200);

		lbId = new JLabel("Id Barang");
		lbNama = new JLabel("Nama Barang");
		lbIdSupp = new JLabel("IdSupp Barang");
		lbHarga = new JLabel("Harga Barang");
		lbStok = new JLabel("Stok satuan");

		lbId.setBounds(10, 225, 100, 20);
		lbId.setHorizontalAlignment(JLabel.LEFT);
		lbNama.setBounds(10, 250, 100, 20);
		lbNama.setHorizontalAlignment(JLabel.LEFT);
		lbIdSupp.setBounds(10, 275, 100, 20);
		lbIdSupp.setHorizontalAlignment(JLabel.LEFT);
		lbHarga.setBounds(10, 300, 100, 20);
		lbHarga.setHorizontalAlignment(JLabel.LEFT);
		lbStok.setBounds(10, 325, 100, 20);
		lbStok.setHorizontalAlignment(JLabel.LEFT);

		JButton buttonTambah = new JButton("Tambah");
		JButton buttonDelete = new JButton("Delete");

		buttonTambah.setBounds(300, 263, 80, 20);
		buttonTambah.addActionListener(new CustActionListener(core, this,tbl,
				buttonTambah, CustActionListener.TAMBAH_BARANG));
		buttonDelete.setBounds(300, 290, 80, 20);
		buttonDelete.addActionListener(new CustActionListener(core, this,tbl,
				buttonDelete, CustActionListener.HAPUS_BARANG));
		
		container.add(tfId);
		container.add(tfNama);
		container.add(tfidSupp);
		container.add(tfHarga);
		container.add(tfStok);
		container.add(panTbl);
		container.add(lbId);
		container.add(lbNama);
		container.add(lbIdSupp);
		container.add(lbHarga);
		container.add(lbStok);

		container.add(buttonDelete);
		container.add(buttonTambah);
		
		JLabel Background = new JLabel();
		Background.setBounds(-270,0,800,406);
		getContentPane().add(Background);	
		Background.setIcon(back);
	}

	public Vector<Barang> getListBarang() 
	{
		return barang;
	}

	public Barang getSelectedBarang() 
	{
		return barang.get(tbl.getSelectedRow());
	}

	public void submitToDB() 
	{
		if (Operator.tambahBarang(getBarang(), core.getConnection()))
		{
			JOptionPane.showMessageDialog(this, "Data telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		
		((DefaultTableModel)tbl.getModel()).addRow(new Object[]{tfId.getText(),tfNama.getText(),tfidSupp.getText(),tfHarga.getText(),tfStok.getText()});

		tfId.setText("");
		tfNama.setText("");
		tfidSupp.setText("");
		tfHarga.setText("");
		tfStok.setText("");
	}

	public void resetForm() 
	{
		tfId.setText("");
		tfNama.setText("");
		tfidSupp.setText("");
		tfHarga.setText("");
		tfStok.setText("");

		if (tbl.getSelectedRow() >= 0) 
		{
			((DefaultTableModel) tbl.getModel())
					.removeRow(tbl.getSelectedRow());
		}
	}


	public Barang getBarang() 
	{
		return new Barang(tfId.getText(),tfNama.getText(),tfidSupp.getText(),Integer.parseInt(tfHarga.getText()),Integer.parseInt(tfStok.getText()));
	}	
}
