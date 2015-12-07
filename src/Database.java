import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database 
{
	
	private final String url = "jdbc:mysql://localhost:3306/hymns";
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
			System.out.println(e.getMessage());
		}
	}
	
	public Boolean Insert(BibleVerse _verse)
	{
		if (_verse == null) return false;
		
		try
		{
			CallableStatement stmt = this._connection.prepareCall("{call addVerse(?,?,?,?,?)}");
			stmt.setString("_version", _verse._Version);
			stmt.setString("_book", _verse._book);
			stmt.setInt("_chapter", _verse._chapter);
			stmt.setInt("_verseno", _verse._verse);
			stmt.setString("_versetext", _verse._verseText);
			stmt.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return true;
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


