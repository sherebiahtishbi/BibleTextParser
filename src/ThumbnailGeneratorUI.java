import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import java.awt.SystemColor;

public class ThumbnailGeneratorUI extends JPanel 
{
	public ThumbnailGeneratorUI()
	{
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
		JLabel lblHeader = new JLabel();
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
		JLabel lblFolder = new JLabel();
		lblFolder.setText("Select image folder");
		GridBagConstraints gbc_lblFolder = new GridBagConstraints();
		gbc_lblFolder.insets = new Insets(3, 0, 3, 5);
		gbc_lblFolder.gridx = 0;
		gbc_lblFolder.gridy = rowNum;
		this.add(lblFolder, gbc_lblFolder);
		
		JTextField txtFolder = new JTextField();
		GridBagConstraints gbc_txtFolder = new GridBagConstraints();
		gbc_txtFolder.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFolder.gridx=1;
		gbc_txtFolder.gridy = rowNum;
		gbc_txtFolder.gridwidth = 4;
		this.add(txtFolder, gbc_txtFolder);
		
		JButton btnFolder = new JButton();
		btnFolder.setText("Browse Folders");
		GridBagConstraints gbc_btnFolder = new GridBagConstraints();
		gbc_btnFolder.gridx = 5;
		gbc_btnFolder.gridy = rowNum;
		this.add(btnFolder, gbc_btnFolder);
	}

	private void setThirdRow()
	{
		int rowNum = 2;
		JLabel lblProgress = new JLabel();
		lblProgress.setText("Progress");
		GridBagConstraints gbc_lblProgress = new GridBagConstraints();
		gbc_lblProgress.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblProgress.insets = new Insets(5, 0, 3, 5);
		gbc_lblProgress.gridx = 0;
		gbc_lblProgress.gridy = rowNum;
		this.add(lblProgress, gbc_lblProgress);
		
		JProgressBar pgbThumb = new JProgressBar();
		pgbThumb.setMinimum(0);
		pgbThumb.setMaximum(100);
		GridBagConstraints gbc_pgbThumb = new GridBagConstraints();
		gbc_pgbThumb.insets = new Insets(5, 0, 3, 5);
		gbc_pgbThumb.anchor = GridBagConstraints.NORTH;
		gbc_pgbThumb.fill = GridBagConstraints.HORIZONTAL;
		gbc_pgbThumb.gridx=1;
		gbc_pgbThumb.gridy = rowNum;
		gbc_pgbThumb.gridwidth = 4;
		this.add(pgbThumb, gbc_pgbThumb);
	}

}
