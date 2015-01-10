package objek;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Transaksi 
{
	private int no_transaksi;
	private Vector<Detil_Transaksi> detilTransaksi = new Vector<Detil_Transaksi>();
	private Date tgl;
	private User username;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh:ss");

	public Transaksi(int no_transaksi, Vector<Detil_Transaksi> detilTransaksi, Date tgl,
			User username) 
	{
		this.no_transaksi = no_transaksi;
		this.detilTransaksi = detilTransaksi;
		this.tgl = tgl;
		this.username = username;
	}

	public Transaksi(Date tgl, User username) 
	{
		this.tgl = tgl;
		this.username = username;
	}

	public Transaksi(int no_transaksi) 
	{
		this.no_transaksi = no_transaksi;
	}

	public int getno_transaksi() 
	{
		return no_transaksi;
	}

	public Vector<Detil_Transaksi> getDetilTransaksi() 
	{
		return detilTransaksi;
	}

	public Date getTgl() 
	{
		return tgl;
	}

	public String getTglAsString() 
	{
		return formatter.format(tgl.getTime());
	}

	public User getUsername() 
	{
		return username;
	}

	public int getTotalItem() 
	{
		int total = 0;
		for (int i = 0; i < detilTransaksi.size(); i++) {
			total += detilTransaksi.get(i).getquantity();
		}
		return total;
	}

	public int getTotalHrg() 
	{
		int total = 0;
		for (int i = 0; i < detilTransaksi.size(); i++) {
			total += detilTransaksi.get(i).getBarang().getHarga()
					* detilTransaksi.get(i).getquantity();
		}
		return total;
	}

	public void addDetilTransaksi(Detil_Transaksi dt) 
	{
		detilTransaksi.add(dt);
	}
}
