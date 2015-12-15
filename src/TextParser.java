import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.SwingWorker;

public class TextParser 
{
	/* Variable*/
	private String _filename;
	private final Database _bibleDatabase;
	private final String bookName;
	private final String versionName;
	private BufferedReader _bufferedReader;
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	/* Constructor */
	public TextParser(String filename, Database db, String book, String version)
	{
		this._filename = filename;
		this._bibleDatabase = db;
		this.bookName = book;
		this.versionName = version;
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
			/*FileReader _file = new FileReader(this._filename);*/
			Reader _file = new InputStreamReader(new FileInputStream(this._filename),"UTF-8");
			_bufferedReader = new BufferedReader(_file);
			
			SwingWorker parseWorker = new SwingWorker()
			{
				@Override
				protected Object doInBackground() throws Exception 
				{
					String line;
					String[] data;
					int lineno=0;
					BibleVerse verse = new BibleVerse();
					
					while ((line = _bufferedReader.readLine()) != null) 
					{
						++lineno;
						pcs.firePropertyChange("pgbupdate", null, lineno);
						Insert(line);
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
			pcs.firePropertyChange("logupdate", null, "ERROR : " + e.getMessage());
		}
	}
	
	private Boolean Insert(String _text)
	{
		BibleVerse verse = new BibleVerse();
		String[] data = _text.split("\\|");
		try
		{
			if (data[0].equals(bookName) || bookName.equals("All"))
			{
				verse._Version = versionName;
				verse._book = data[0];
				verse._chapter = Integer.parseInt(data[1]); 
				verse._verse = Integer.parseInt(data[2]);
				verse._verseText = data[3];
				_bibleDatabase.Insert(verse);
				pcs.firePropertyChange("logupdate", null, data[0] + " " + data[1] + "," + data[2] + " - INSERTED.");
			}
			else
			{
				pcs.firePropertyChange("logupdate", null, data[0] + " " + data[1] + "," + data[2] + " - SKIPPED.");
			}
			return true;
		}
		catch(Exception e)
		{
			pcs.firePropertyChange("logupdate", null, "ERROR : " + e.getMessage());
			return false;
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
