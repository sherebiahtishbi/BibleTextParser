import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database 
{
	
	private final String url = "jdbc:mysql://localhost:3306/fbcb";
	private final String userId = "root";
	private final String userPwd = "admin";
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private Connection _connection;
	public Boolean connected = false;
	
	public Database()
	{
		try 
		{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
			this._connection = DriverManager.getConnection(url,userId,userPwd);
			connected = true;
		} 
		catch (Exception e) 
		{
			
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
	}
}


