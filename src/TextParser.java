import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.SwingWorker;

public class TextParser 
{
	/* Variable*/
	private String _filename;
	private Database _bibleDatabase;
	private BufferedReader _bufferedReader;
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	/* Constructor */
	public TextParser(String filename, Database db)
	{
		this._filename = filename;
		this._bibleDatabase = db;
	}
	
	public void parseFile()
	{
		if (this._filename.length()==0) { return; }

		if (this._bibleDatabase.connected)
		{
			pcs.firePropertyChange("logupdate", null, "Bible database connected successfully.");
		}
		else 
		{
			pcs.firePropertyChange("logupdate", null, "ERROR : Bible database connection failed.");
		}
				
		try 
		{
			FileReader _file = new FileReader(this._filename);	
			_bufferedReader = new BufferedReader(_file);
			
			SwingWorker parseWorker = new SwingWorker()
			{
				@Override
				protected Object doInBackground() throws Exception 
				{
					String line;
					String[] data;
					int lineno=0;
					
					while ((line = _bufferedReader.readLine()) != null) 
					{
						++lineno;
						if (lineno > 50) break;
						
						data = line.split("\\|");
						pcs.firePropertyChange("logupdate", null, line);
						pcs.firePropertyChange("logupdate", null, "Working on " + data[0] + " " + data[1] + "," + data[2]);
					}
					_bufferedReader.close();
					return null;
				}

				@Override
				protected void done()
				{
					pcs.firePropertyChange("logupdate", null, "Parsing complete.");
				}
			};
			parseWorker.execute();
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
