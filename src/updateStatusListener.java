import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class updateStatusListener implements PropertyChangeListener
{

	JLabel lblStatus = new JLabel(); 
	JTextArea txtLog = new JTextArea();
	JLabel lblPercent = new JLabel();
	JProgressBar pgbParser = new JProgressBar();
	
	public updateStatusListener(JTextArea txt,JProgressBar pgb, JLabel lblPcnt)
	{
		txtLog = txt;
		pgbParser = pgb;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) 
	{
		if (e.getPropertyName() == "pgbupdate")
		{
			if (e.getNewValue() != null)
			{
				int pcnt = (((int) e.getNewValue())*100)/31102; 
				pgbParser.setValue((int) e.getNewValue());
				lblPercent.setText(pcnt + " %");
			}
		}
		
		if (e.getPropertyName() == "logupdate")
		{
			if (e.getNewValue() != null)
			{
				txtLog.append(e.getNewValue().toString()+"\n");
				txtLog.setCaretPosition(txtLog.getDocument().getLength());
			}
		}
	}
}
