package sistem;

import java.sql.*;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import objek.*;

public class Operator 
{

	public static ResultSet getListBarang(Connection con) 
	{
		return select(con, "SELECT * FROM produk ORDER BY id");
	}

	public static ResultSet getListUser(Connection con) 
	{
		return select(con, "SELECT * FROM users ORDER BY username");
	}
	
	public static ResultSet getListTransaksi(Connection con) 
	{
		return select(con, "SELECT * FROM transaksi ORDER BY no_transaksi");
	}

	public static ResultSet getListDetilTransaksi(Connection con,
			int idTransaksi) 
	{
		return select(con, "SELECT * FROM detilTransaksi WHERE no_transaksi="
				+ idTransaksi);
	}

	public static int getLastIDTrans(Connection con) 
	{
		ResultSet rs = select(con, "SELECT MAX(no_transaksi) FROM transaksi");
		try {
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	public static boolean tambahBarang(Barang brg, Connection con) 
	{
		return update(
				con,
				"INSERT INTO produk " + "VALUES('" + brg.getId() + "','"
						+ brg.getNama() + "','" + brg.getidSupp() + "','"
						+ brg.getHarga() + "'," + brg.getStok() + ")");
	}

	public static boolean tambahTransaksi(Transaksi trns, Connection con) 
	{
		boolean returnValue = true;
		int id = getLastIDTrans(con) + 1;
		returnValue = update(con, "INSERT INTO transaksi VALUES(" + id + ", '"
				+ trns.getTglAsString() + "', '" + trns.getUsername().getUsername()
				+ "')");
		for (int i = 0; i < trns.getDetilTransaksi().size(); i++) 
		{
			returnValue = returnValue
					& update(con, "INSERT INTO detilTransaksi "
							+ "VALUES("
							+ id
							+ ",'"+trns.getDetilTransaksi().get(i).getBarang(). getId() + "',"
							+ trns.getDetilTransaksi().get(i).getquantity() + ")");
			returnValue = returnValue
					& update(con, "UPDATE produk SET stok = stok-"
							+ trns.getDetilTransaksi().get(i).getquantity()
							+ " WHERE id='"
							+ trns.getDetilTransaksi().get(i).getBarang()
									.getId()+"'");
		}
		return returnValue;
	}

	public static boolean hapusBarang(Barang brg, Connection con) 
	{
		return update(con, "DELETE FROM produk WHERE id='" + brg.getId()+"'");
	}

	public static boolean hapusTransaksi(Transaksi trns, Connection con) 
	{
		boolean returnData = true;
		if (trns == null) {
			return false;
		} else {
			returnData = returnData
					& update(con,
							"DELETE FROM detilTransaksi WHERE no_transaksi="
									+ trns.getno_transaksi());
			returnData = returnData
					& update(con, "DELETE FROM transaksi WHERE no_transaksi="
							+ trns.getno_transaksi());
		}
		return returnData;
	}

	public static User checkLogin(User usr, Connection con) 
	{
		ResultSet rs = select(con, "SELECT admin FROM users "
				+ "WHERE username='" + usr.getUsername() + "' AND pass='"
				+ usr.getPassword() + "'");
		try {
			if (rs.next()) 
			{
				return new User(usr.getUsername(), usr.getPassword(),
						rs.getBoolean("admin"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}

	private static ResultSet select(Connection con, String query) 
	{
		ResultSet rs = null;
		try {
			rs = con.createStatement().executeQuery(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return rs;
	}

	private static boolean update(Connection con, String query)
	{
		try {
			con.createStatement().executeUpdate(query);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public static DefaultTableModel resultSetToTableModel(ResultSet rs)
	{
		try {
			ResultSetMetaData meta = rs.getMetaData();

			Vector<String> col = new Vector<String>();
			int columnCount = meta.getColumnCount();
			for (int column = 1; column <= columnCount; column++)
			{
				col.add(meta.getColumnName(column));
			}

			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
				{
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);
			}

			return new DefaultTableModel(data, col);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return new DefaultTableModel();
	}

	public static void disableTableEdit(JTable tbl)
	{
		for (int c = 0; c < tbl.getColumnCount(); c++) 
		{
			Class<?> col_class = tbl.getColumnClass(c);
			tbl.setDefaultEditor(col_class, null);
		}
	}
}
