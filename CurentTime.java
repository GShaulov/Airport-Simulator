import java.awt.FlowLayout;
import java.time.ZonedDateTime;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CurentTime extends Thread {
    
    private JPanel timePanel;
    private JLabel label1;
    private String time;
     
    public CurentTime(JPanel timePanel){
    	this.timePanel = timePanel;
    	this.time = String.format("%tH:%<tM:%<tS ", ZonedDateTime.now());
    	this.label1 = new JLabel(String.format("<html><font size='5' color='black'>Current Time:</font><font size='5' color='blue'> %s </font></html>", this.time), JLabel.LEFT);
        this.timePanel.add(this.label1);
    	this.timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.timePanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(),  "Arrivals and Departures Online Table:") );            
    }
 
    @Override
    public void run() {
        try {
        	while(true){
            	this.label1.setText(String.format("<html><font size='5' color='black'>Current Time:</font><font size='5' color='blue'> %s </font></html>", this.time));
                Thread.sleep(1000);
            	this.time = String.format("%tH:%<tM:%<tS ", ZonedDateTime.now());
        	}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}