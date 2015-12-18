import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.SystemColor;

public class BibleParserUI extends JPanel 
{
	private String[] books = new String[]{"All","Genesis","Exodus","Leviticus","Numbers","Deuteronomy","Joshua","Judges","Ruth","1 Samuel","2 Samuel","1 Kings","2 Kings","1 Chronicles","2 Chronicles","Ezra","Nehemiah","Esther","Job","Psalms","Proverbs","Ecclesiastes","Song of Solomon","Isaiah","Jeremiah","Lamentations","Ezekiel","Daniel","Hosea","Joel","Amos","Obadiah","Jonah","Micah","Nahum","Habakkuk","Zephaniah","Haggai","Zechariah","Malachi","Matthew","Mark","Luke","John","Acts","Romans","1 Corinthians","2 Corinthians","Galatians","Ephesians","Philippians","Colossians","1 Thessalonians","2 Thessalonians","1 Timothy","2 Timothy","Titus","Philemon","Hebrews","James","1 Peter","2 Peter","1 John","2 John","3 John","Jude","Revelation"
	};
	private String[] versions = new String[]{"KJV","Gujarati"};
	
	/* set all controls */
	private JLabel lblFile, lblVersion, lblBooks, lbllog, lblStatus, lblPercent;
	private JTextField txtFile;
	private JButton btnBrowse, btnStart, btnCancel;
	private JComboBox cmbVersions, cmbBooks;
	private JTextArea txtLog;
	private JScrollPane logScrollPane;
	private JProgressBar pgbParser;
	private JPanel contentPane;
	
	/* set property change listeners */
	PropertyChangeSupport pChangeSupport = new PropertyChangeSupport(this);
	
	public BibleParserUI()
	{
		contentPane = this;
		/* basic layout */
		setBasicLayout();
		
		/* rowwise layout */
		setFirstRow();
		setSecondRow();
		setThirdRow();
		setFourthRow();
		setFifthRow();
		
		/** 
		 * add property change listener 
		 */
		this.pChangeSupport.addPropertyChangeListener(new updateStatusListener(txtLog,pgbParser,lblPercent));
		
		/** 
		 * btnBrowse - action listener 
		 */
		btnBrowse.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg) 
			{
			    JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new File("c:\\My Data\\HTMLApps"));
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(contentPane);
			    if(returnVal == JFileChooser.APPROVE_OPTION) 
			    {
			    	txtFile.setText(chooser.getSelectedFile().getAbsolutePath());
			    	pChangeSupport.firePropertyChange("logupdate",null,"File selected to parse [" + txtFile.getText() + "]");
			    }				
			}
		});			
		
		btnStart.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Database bibleDb = new Database();
				bibleDb.addPropertyChangeListener(new updateStatusListener(txtLog, pgbParser,lblPercent));

				pChangeSupport.firePropertyChange("logupdate",null,"Started parsing [" + txtFile.getText() + "]");
				TextParser parser = new TextParser(txtFile.getText(),bibleDb,(String) cmbBooks.getSelectedItem(),(String) cmbVersions.getSelectedItem());
				parser.addPropertyChangeListener(new updateStatusListener(txtLog, pgbParser,lblPercent));
				parser.parseFile();
			}
		});		
		
		btnCancel.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				pChangeSupport.firePropertyChange("logupdate",null,"Parsing cancelled by user.");
			}
		});
		
		cmbBooks.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				@SuppressWarnings("unchecked")
				JComboBox<String> combo = (JComboBox<String>) e.getSource();
				pChangeSupport.firePropertyChange("logupdate", null,"[" + (String) combo.getSelectedItem() + "] selected.");
			}
		});		
	}
	
	private void setBasicLayout()
	{
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gbl_contentPane);	
		
		JLabel lblHeader = new JLabel();
		lblHeader.setText("Bible Parser");
		lblHeader.setFont(new Font("Consolas",Font.BOLD, 22));
		lblHeader.setForeground(SystemColor.desktop);
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.insets = new Insets(10, 10, 10, 10);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		gbc_lblHeader.gridwidth = 6;
		this.add(lblHeader,gbc_lblHeader);		
	}
	
	private void setFirstRow()
	{
		/* First row */
		lblFile = new JLabel("Select File to parser :");
		GridBagConstraints gbc_lblFile = new GridBagConstraints();
		gbc_lblFile.insets = new Insets(0, 5, 0, 5);
		gbc_lblFile.gridx = 1;
		gbc_lblFile.gridy = 1;
		this.add(lblFile, gbc_lblFile);

		txtFile = new JTextField();
		txtFile.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_txtFile = new GridBagConstraints();
		gbc_txtFile.insets = new Insets(3, 0, 3, 5);
		gbc_txtFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFile.gridwidth = 2;
		gbc_txtFile.gridx = 2;
		gbc_txtFile.gridy = 1;
		this.add(txtFile,gbc_txtFile);
		
		btnBrowse = new JButton("Browse");

		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.gridx = 4;
		gbc_btnBrowse.gridy = 1;
		this.add(btnBrowse,gbc_btnBrowse);
	}
	
	private void setSecondRow()
	{
		/* Second Row */
		lblVersion = new JLabel("Select Version :");
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblVersion.insets = new Insets(0, 5, 0, 5);
		gbc_lblVersion.gridx = 1;
		gbc_lblVersion.gridy = 2;
		this.add(lblVersion, gbc_lblVersion);
		
		
		DefaultComboBoxModel<String> versionModel = new DefaultComboBoxModel<>(versions);
		cmbVersions = new JComboBox(versionModel);
		GridBagConstraints gbc_cmbVersions = new GridBagConstraints();
		gbc_cmbVersions.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbVersions.gridwidth = 2;
		gbc_cmbVersions.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbVersions.insets = new Insets(3,0,3,5);
		gbc_cmbVersions.gridx = 2;
		gbc_cmbVersions.gridy = 2;
		this.add(cmbVersions,gbc_cmbVersions);
	}
	
	private void setThirdRow()
	{
		/* Third Row */
		lblBooks = new JLabel("Select Book :");
		GridBagConstraints gbc_lblBooks = new GridBagConstraints();
		gbc_lblBooks.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblBooks.insets = new Insets(0, 5, 0, 5);
		gbc_lblBooks.gridx = 1;
		gbc_lblBooks.gridy = 3;
		this.add(lblBooks, gbc_lblBooks);
		
		
		DefaultComboBoxModel<String> bookModel = new DefaultComboBoxModel<>(books);
		cmbBooks = new JComboBox(bookModel);
		GridBagConstraints gbc_cmbBooks = new GridBagConstraints();
		gbc_cmbBooks.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbBooks.gridwidth = 2;
		gbc_cmbBooks.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbBooks.insets = new Insets(3,0,3,5);
		gbc_cmbBooks.gridx = 2;
		gbc_cmbBooks.gridy = 3;
		this.add(cmbBooks,gbc_cmbBooks);
		
		btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(5, 0, 0, 0);
		gbc_btnStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStart.anchor = GridBagConstraints.NORTH;
		gbc_btnStart.gridx = 4;
		gbc_btnStart.gridy = 3;
		this.add(btnStart,gbc_btnStart);		
	}
	
	private void setFourthRow()
	{
		/* Fourth row */
		lbllog = new JLabel("Log :");
		GridBagConstraints gbc_lbllog = new GridBagConstraints();
		gbc_lbllog.anchor = GridBagConstraints.NORTHEAST;
		gbc_lbllog.insets = new Insets(0, 5, 0, 5);
		gbc_lbllog.gridx = 1;
		gbc_lbllog.gridy = 4;
		this.add(lbllog, gbc_lbllog);
		
		txtLog = new JTextArea();
		txtLog.setEditable(false);
		txtLog.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtLog.setCaretPosition(txtLog.getDocument().getLength());
		
		logScrollPane = new JScrollPane();
		logScrollPane.setViewportBorder(UIManager.getBorder("Table.scrollPaneBorder"));
		GridBagConstraints gbc_logScrollPane = new GridBagConstraints();
		gbc_logScrollPane.weighty = 1.0;
		gbc_logScrollPane.gridwidth = 2;
		gbc_logScrollPane.insets = new Insets(3, 0, 3, 5);
		gbc_logScrollPane.fill = GridBagConstraints.BOTH;
		gbc_logScrollPane.anchor = GridBagConstraints.NORTH;
		gbc_logScrollPane.gridx = 2;
		gbc_logScrollPane.gridy = 4;
		logScrollPane.setViewportView(txtLog);
		this.add(logScrollPane,gbc_logScrollPane);
		
		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(5, 0, 0, 0);
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancel.gridx = 4;
		gbc_btnCancel.gridy = 4;
		this.add(btnCancel,gbc_btnCancel);		
	}
	
	private void setFifthRow()
	{
		/* Fifth row */
		lblStatus = new JLabel("Status :");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.fill = GridBagConstraints.VERTICAL;
		gbc_lblStatus.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblStatus.insets = new Insets(0, 5, 0, 5);
		gbc_lblStatus.gridx = 1;
		gbc_lblStatus.gridy = 5;
		this.add(lblStatus, gbc_lblStatus);		

		pgbParser = new JProgressBar();
		pgbParser.setMinimum(1);
		pgbParser.setMaximum(31101);
		GridBagConstraints gbc_pgbParser = new GridBagConstraints();
		gbc_pgbParser.gridwidth = 2;
		gbc_pgbParser.insets = new Insets(3, 0, 3, 5);
		gbc_pgbParser.fill = GridBagConstraints.HORIZONTAL;
		gbc_pgbParser.anchor = GridBagConstraints.WEST;
		gbc_pgbParser.gridx = 2;
		gbc_pgbParser.gridy = 5;
		this.add(pgbParser,gbc_pgbParser);
		
		lblPercent = new JLabel();
		lblPercent.setHorizontalAlignment(SwingConstants.LEFT);
		lblPercent.setFont(new Font("Consolas", Font.BOLD, 16));
		GridBagConstraints gbc_lblPercent = new GridBagConstraints();
		gbc_lblPercent.insets = new Insets(3, 0, 3, 5);
		gbc_lblPercent.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPercent.anchor = GridBagConstraints.WEST;
		gbc_lblPercent.gridx = 4;
		gbc_lblPercent.gridy = 5;
		this.add(lblPercent,gbc_lblPercent);			
	}
}
