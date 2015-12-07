import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database 
{
	
	private final String url = "jdbc:mysql://192.168.1.133:3306/hymns";
	private final String userId = "root";
	private final String userPwd = "admin";
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public Database()
	{
		try 
		{
			String driver = "com.mysql.jdbc.driver";
			Class.forName(driver).newInstance();
		} 
		catch (Exception e) 
		{
			
		}
	}

	public Boolean connect()
	{
		Boolean retval=false;
		try
		{
			Connection _connection = DriverManager.getConnection(url,userId,userPwd);
			if (!_connection.isClosed())
			{
				retval = true;
			}
		}
		catch (Exception e)
		{
			
		}
		return retval;
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


