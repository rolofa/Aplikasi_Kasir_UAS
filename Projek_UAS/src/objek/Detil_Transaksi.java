package objek;

public class Detil_Transaksi 
{
	private Barang barang;
	private int quantity;
	private Transaksi transaksi;

	public Detil_Transaksi(Transaksi transaksi, Barang barang, int quantity) 
	{
		this.transaksi = transaksi;
		this.barang = barang;
		this.quantity = quantity;
	}

	public Detil_Transaksi(Barang barang, int quantity) 
	{
		this.barang = barang;
		this.quantity = quantity;
	}

	public Barang getBarang() 
	{
		return barang;
	}

	public int getquantity()
	{
		return quantity;
	}

	public Transaksi getTransaksi() 
	{
		return transaksi;
	}

}
