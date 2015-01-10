package objek;

public class User 
{
	private String username, password;
	private boolean isAdmin;

	public User(String username, String password, boolean isAdmin) 
	{
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public User(User usr) 
	{
		this.username = usr.getUsername();
		this.password = usr.getPassword();
		this.isAdmin = usr.isAdmin();
	}

	public String getUsername() 
	{
		return username;
	}

	public boolean isAdmin() 
	{
		return isAdmin;
	}

	public String getPassword() 
	{
		return password;
	}
}
