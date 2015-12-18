import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ThumbnailGeneratorUI extends JPanel 
{
	String[] validImageExtensions = new String[]{"JPG","PNG"};
	
	/**
	 * property change support
	 */
	PropertyChangeSupport pChangeSupport = new PropertyChangeSupport(this);
		
	/**
	 * All panel controls
	 */
	private JLabel lblHeader, lblFolder, lblProgress, lblLog;
	private JTextField txtFolder;
	private JTextArea txtLog;
	private JButton btnFolder, btnStart;
	private JProgressBar pgbThumb;
	private JPanel contentPane; 
	private JScrollPane scrollLog;
	
	public ThumbnailGeneratorUI()
	{
		contentPane = this;
		/** 
		 * Set basic panel layout 
		 */
		setBasicLayout();
		
		/** 
		 * row wise layout 
		 */
		setFirstRow();
		setSecondRow();
		setThirdRow();
		setFourthRow();
		
		/**
		 * setup property change listener
		 */
		this.pChangeSupport.addPropertyChangeListener(new updateStatusListener(txtLog, pgbThumb, null));
		
		/** 
		 * btnBrowse - action listener 
		 */
		btnFolder.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg) 
			{
			    JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new File("c:\\My Data\\HTMLApps"));
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    int returnVal = chooser.showOpenDialog(contentPane);
			    if(returnVal == JFileChooser.APPROVE_OPTION) 
			    {
			    	txtFolder.setText(chooser.getSelectedFile().getAbsolutePath());
			    	pChangeSupport.firePropertyChange("logupdate",null,"File selected to parse [" + txtFolder.getText() + "]");
			    }				
			}
		});
		
		/**
		 * starts thumbnail generation process
		 */
		btnStart.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				GenerateThumbnails(txtFolder.getText());
			}
		});
	}
	
	private void setBasicLayout()
	{
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_thumbPane = new GridBagLayout();
		gbl_thumbPane.columnWidths = new int[]{0, 0, 0};
		gbl_thumbPane.rowHeights = new int[]{0, 0, 0};
		gbl_thumbPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_thumbPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gbl_thumbPane);
	}
	
	private void setFirstRow()
	{
		int rowNum = 0;
		lblHeader = new JLabel();
		lblHeader.setText("Thumbnail Generator");
		lblHeader.setFont(new Font("Consolas",Font.BOLD, 22));
		lblHeader.setForeground(SystemColor.desktop);
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.insets = new Insets(10, 10, 10, 10);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = rowNum;
		gbc_lblHeader.gridwidth = 6;
		this.add(lblHeader,gbc_lblHeader);
	}
	
	private void setSecondRow()
	{
		int rowNum = 1;
		lblFolder = new JLabel();
		lblFolder.setText("Select image folder");
		GridBagConstraints gbc_lblFolder = new GridBagConstraints();
		gbc_lblFolder.insets = new Insets(3, 0, 3, 5);
		gbc_lblFolder.gridx = 0;
		gbc_lblFolder.gridy = rowNum;
		this.add(lblFolder, gbc_lblFolder);
		
		txtFolder = new JTextField();
		GridBagConstraints gbc_txtFolder = new GridBagConstraints();
		gbc_txtFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFolder.gridx=1;
		gbc_txtFolder.gridy = rowNum;
		gbc_txtFolder.gridwidth = 4;
		this.add(txtFolder, gbc_txtFolder);
		
		btnFolder = new JButton();
		btnFolder.setText("Browse Folders");
		GridBagConstraints gbc_btnFolder = new GridBagConstraints();
		gbc_btnFolder.insets = new Insets(0, 5, 0, 5);
		gbc_btnFolder.gridx = 5;
		gbc_btnFolder.gridy = rowNum;
		this.add(btnFolder, gbc_btnFolder);
	}

	private void setThirdRow()
	{
		int rowNum = 2;
		lblProgress = new JLabel();
		lblProgress.setText("Progress");
		GridBagConstraints gbc_lblProgress = new GridBagConstraints();
		gbc_lblProgress.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblProgress.insets = new Insets(5, 0, 3, 5);
		gbc_lblProgress.gridx = 0;
		gbc_lblProgress.gridy = rowNum;
		this.add(lblProgress, gbc_lblProgress);
		
		pgbThumb = new JProgressBar();
		pgbThumb.setStringPainted(true);
		pgbThumb.setMinimum(0);
		pgbThumb.setMaximum(100);
		GridBagConstraints gbc_pgbThumb = new GridBagConstraints();
		gbc_pgbThumb.insets = new Insets(5, 0, 0, 0);
		gbc_pgbThumb.anchor = GridBagConstraints.NORTH;
		gbc_pgbThumb.fill = GridBagConstraints.HORIZONTAL;
		gbc_pgbThumb.gridx=1;
		gbc_pgbThumb.gridy = rowNum;
		gbc_pgbThumb.gridwidth = 4;
		this.add(pgbThumb, gbc_pgbThumb);
		
		btnStart = new JButton("Generate");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.insets = new Insets(3, 5, 0, 5);
		gbc_btnStart.gridx = 5;
		gbc_btnStart.gridy = rowNum;
		this.add(btnStart,gbc_btnStart);
	}
	
	private void setFourthRow()
	{
		int rowNum = 3;
		lblLog = new JLabel("Log");
		GridBagConstraints gbc_lblLog = new GridBagConstraints();
		gbc_lblLog.gridx = 0;
		gbc_lblLog.gridy = rowNum;
		gbc_lblLog.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblLog.insets = new Insets(5, 0, 3, 5);
		this.add(lblLog,gbc_lblLog);
		
		txtLog = new JTextArea();
		
		scrollLog = new JScrollPane();
		GridBagConstraints gbc_scrollLog = new GridBagConstraints();
		gbc_scrollLog.weighty = 1.0;
		gbc_scrollLog.gridx = 1;
		gbc_scrollLog.gridy = rowNum;
		gbc_scrollLog.insets = new Insets(5, 0, 0, 0);
		gbc_scrollLog.anchor = GridBagConstraints.NORTH;
		gbc_scrollLog.gridwidth = 4;
		gbc_scrollLog.fill = GridBagConstraints.BOTH;
		scrollLog.setViewportView(txtLog);
		
		this.add(scrollLog,gbc_scrollLog);
	}

	private void GenerateThumbnails(String path)
	{
		try 
		{
			File dir = new File(path);
			if (!dir.exists()) 
			{
				pChangeSupport.firePropertyChange("logupdate",null,"Error : Folder [" + path + "] does not exist.");
				return;
			}
			
			File[] images = dir.listFiles();
			pgbThumb.setMinimum(0);
			pgbThumb.setMaximum(images.length);
			
			int fIndex=0;
			String thumnailName;
			
			for(File file : images)
			{
				pChangeSupport.firePropertyChange("pgbupdate", null,++fIndex);
				
				if (file.isFile() && validImage(file.getName()))
				{
					pChangeSupport.firePropertyChange("logupdate", null, "Valid image file ["+file.getAbsolutePath()+"]");
					BufferedImage image = ImageIO.read(file);
					
					File thumbDir = new File(path + "\\thumbnails\\");
					
					if (!thumbDir.exists()) thumbDir.mkdir();
					
					thumnailName = path + "\\thumbnails\\" + fileNameWithoutExtension(file.getName()) + "_thumb.png";
					System.out.println(thumnailName);	
					
					File thumbFile = new File(thumnailName);
					
					if (!thumbFile.exists())
					{
						Thumbnails.of(file).size(100, 100).toFile(thumnailName);
						pChangeSupport.firePropertyChange("logupdate", null, "Thumbnail already exist for ["+file.getAbsolutePath()+"]");
					}
				}
				else 
				{
					pChangeSupport.firePropertyChange("logupdate", null, "Skipped ["+file.getName()+"]");
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private boolean validImage(String filename)
	{
		if (filename.lastIndexOf(".") == -1 && filename.lastIndexOf(".") == 0)
		{
			return false;
		}
		
		if (filename.contains("_thumb")) return false;
		
		String fileExtension = filename.substring(filename.lastIndexOf(".")+1);
		
		return (Arrays.asList(validImageExtensions).contains(fileExtension.toUpperCase())) ? true : false;
	}
	
	private String fileNameWithoutExtension(String filename)
	{
		String fileExtension = filename.substring(filename.lastIndexOf("."));
		return filename.substring(0,filename.indexOf(fileExtension));
	}
}
