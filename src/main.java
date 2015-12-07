import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLEditorKit.Parser;

import org.eclipse.swt.internal.webkit.JSClassDefinition;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class main extends JFrame 
{

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		
		try 
		{
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		setTitle("Bible Parser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		/* First row */
		JLabel lblFile = new JLabel("Select File to parser :");
		GridBagConstraints gbc_lblFile = new GridBagConstraints();
		gbc_lblFile.insets = new Insets(0, 5, 0, 5);
		gbc_lblFile.gridx = 1;
		gbc_lblFile.gridy = 1;
		contentPane.add(lblFile, gbc_lblFile);

		JTextField txtFile = new JTextField();
		txtFile.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_txtFile = new GridBagConstraints();
		gbc_txtFile.insets = new Insets(3, 0, 3, 5);
		gbc_txtFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFile.gridwidth = 2;
		gbc_txtFile.gridx = 2;
		gbc_txtFile.gridy = 1;
		contentPane.add(txtFile,gbc_txtFile);
		
		JButton btnBrowse = new JButton("Browse");

		GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
		gbc_btnBrowse.gridx = 4;
		gbc_btnBrowse.gridy = 1;
		contentPane.add(btnBrowse,gbc_btnBrowse);

		/* Second Row */
		JLabel lblBooks = new JLabel("Select Book :");
		GridBagConstraints gbc_lblBooks = new GridBagConstraints();
		gbc_lblBooks.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblBooks.insets = new Insets(0, 5, 0, 5);
		gbc_lblBooks.gridx = 1;
		gbc_lblBooks.gridy = 2;
		contentPane.add(lblBooks, gbc_lblBooks);
		
		String[] books = new String[]{"All","Genesis","Exodus","Leviticus","Numbers","Deuteronomy","Joshua","Judges","Ruth","1 Samuel","2 Samuel","1 Kings","2 Kings","1 Chronicles","2 Chronicles","Ezra","Nehemiah","Esther","Job","Psalms","Proverbs","Ecclesiastes","Song of Solomon","Isaiah","Jeremiah","Lamentations","Ezekiel","Daniel","Hosea","Joel","Amos","Obadiah","Jonah","Micah","Nahum","Habakkuk","Zephaniah","Haggai","Zechariah","Malachi","Matthew","Mark","Luke","John","Acts","Romans","1 Corinthians","2 Corinthians","Galatians","Ephesians","Philippians","Colossians","1 Thessalonians","2 Thessalonians","1 Timothy","2 Timothy","Titus","Philemon","Hebrews","James","1 Peter","2 Peter","1 John","2 John","3 John","Jude","Revelation"
};
		DefaultComboBoxModel<String> bookModel = new DefaultComboBoxModel<>(books);
		JComboBox cmbBooks = new JComboBox(bookModel);
		GridBagConstraints gbc_cmbBooks = new GridBagConstraints();
		gbc_cmbBooks.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbBooks.gridwidth = 2;
		gbc_cmbBooks.anchor = GridBagConstraints.NORTHWEST;
		gbc_cmbBooks.insets = new Insets(3,0,3,5);
		gbc_cmbBooks.gridx = 2;
		gbc_cmbBooks.gridy = 2;
		contentPane.add(cmbBooks,gbc_cmbBooks);
		
		JButton btnStart = new JButton("Start");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(5, 0, 0, 0);
		gbc_btnStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStart.anchor = GridBagConstraints.NORTH;
		gbc_btnStart.gridx = 4;
		gbc_btnStart.gridy = 2;
		contentPane.add(btnStart,gbc_btnStart);
		
		/* Third row */
		JLabel lbllog = new JLabel("Log :");
		GridBagConstraints gbc_lbllog = new GridBagConstraints();
		gbc_lbllog.anchor = GridBagConstraints.NORTHEAST;
		gbc_lbllog.insets = new Insets(0, 5, 0, 5);
		gbc_lbllog.gridx = 1;
		gbc_lbllog.gridy = 3;
		contentPane.add(lbllog, gbc_lbllog);
		
		JTextArea txtLog = new JTextArea();
		txtLog.setEditable(false);
		txtLog.setFont(new Font("Consolas", Font.PLAIN, 12));
		txtLog.setCaretPosition(txtLog.getDocument().getLength());
		
		JScrollPane logScrollPane = new JScrollPane();
		logScrollPane.setViewportBorder(UIManager.getBorder("Table.scrollPaneBorder"));
		GridBagConstraints gbc_logScrollPane = new GridBagConstraints();
		gbc_logScrollPane.weighty = 1.0;
		gbc_logScrollPane.gridwidth = 2;
		gbc_logScrollPane.insets = new Insets(3, 0, 3, 5);
		gbc_logScrollPane.fill = GridBagConstraints.BOTH;
		gbc_logScrollPane.anchor = GridBagConstraints.NORTH;
		gbc_logScrollPane.gridx = 2;
		gbc_logScrollPane.gridy = 3;
		logScrollPane.setViewportView(txtLog);
		contentPane.add(logScrollPane,gbc_logScrollPane);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(5, 0, 0, 0);
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancel.gridx = 4;
		gbc_btnCancel.gridy = 3;
		contentPane.add(btnCancel,gbc_btnCancel);

		/* Fourth row */
		JLabel lblStatus = new JLabel("Status :");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.fill = GridBagConstraints.VERTICAL;
		gbc_lblStatus.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblStatus.insets = new Insets(0, 5, 0, 5);
		gbc_lblStatus.gridx = 1;
		gbc_lblStatus.gridy = 4;
		contentPane.add(lblStatus, gbc_lblStatus);		

		JProgressBar pgbParser = new JProgressBar();
		pgbParser.setMinimum(1);
		pgbParser.setMaximum(31101);
		GridBagConstraints gbc_pgbParser = new GridBagConstraints();
		gbc_pgbParser.gridwidth = 2;
		gbc_pgbParser.insets = new Insets(3, 0, 3, 5);
		gbc_pgbParser.fill = GridBagConstraints.HORIZONTAL;
		gbc_pgbParser.anchor = GridBagConstraints.WEST;
		gbc_pgbParser.gridx = 2;
		gbc_pgbParser.gridy = 4;
		contentPane.add(pgbParser,gbc_pgbParser);

		this.addPropertyChangeListener(new updateStatusListener(txtLog,pgbParser));

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
			    	pcs.firePropertyChange("logupdate",null,"File selected to parse [" + txtFile.getText() + "]");
			    }				
			}
		});	
		
		btnStart.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Database bibleDb = new Database();
				bibleDb.addPropertyChangeListener(new updateStatusListener(txtLog, pgbParser));

				pcs.firePropertyChange("logupdate",null,"Started parsing [" + txtFile.getText() + "]");
				TextParser parser = new TextParser(txtFile.getText(),bibleDb,(String) cmbBooks.getSelectedItem());
				parser.addPropertyChangeListener(new updateStatusListener(txtLog, pgbParser));
				parser.parseFile();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				pcs.firePropertyChange("logupdate",null,"Parsing cancelled by user.");
			}
		});

		cmbBooks.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				@SuppressWarnings("unchecked")
				JComboBox<String> combo = (JComboBox<String>) e.getSource();
				pcs.firePropertyChange("logupdate", null,"[" + (String) combo.getSelectedItem() + "] selected.");
			}
		});
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
