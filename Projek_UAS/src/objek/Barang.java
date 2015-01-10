package objek;

public class Barang {
	private int stok, harga;
	private String nama, id, idSupp;

	public Barang(String id, String nama, String idSupp, int harga, int stok)
	{
		this.id=id;
		this.nama=nama;
		this.idSupp=idSupp;
		this.stok=stok;
		this.harga=harga;
	}
	
	public Barang(String nama, String idSupp, int harga, int stok)
	{
		this.nama=nama;
		this.idSupp=idSupp;
		this.stok=stok;
		this.harga=harga;
	}

	public String getId() 
	{
		return id;
	}

	public int getStok() 
	{
		return stok;
	}

	public int getHarga() 
	{
		return harga;
	}

	public String getNama() 
	{
		return nama;
	}

	public String getidSupp() 
	{
		return idSupp;
	}
}
