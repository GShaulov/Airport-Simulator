
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.concurrent.Semaphore;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Airport{

	//variables
	private String name;
	private String city;
	private JPanel topPanel;
	private ImageIcon departuresIcon;	
	private ImageIcon arrivalsIcon;
	private InfoTable departures;
	private InfoTable arrivals;
	private JComponent departuresTable;
	private JComponent arrivalsTable;
	private Semaphore[] runway;
    private JTabbedPane tabbedPane;
	private JFrame frame;

	//constructor
    public Airport(String name, InfoTable departures, InfoTable arrivals,  int runways) {
    	setName(name);
    	setCity(name.substring(name.indexOf('(')+1, name.indexOf(')')));
    	setTime();
    	setDeparturesIcon(new ImageIcon("res/departures2.png"));
    	setArrivalsIcon(new ImageIcon("res/arrivals2.png"));
    	setDepartures(departures);
    	setArrivals(arrivals);
    	setRunway(runways);
    	setTabbedPane(); 
    	setFrame();
    }    

    public String getName(){
    	return this.name;							
    }
    
    public void setName(String name){
    	this.name=name;								
    }
    
    public String getCity(){
    	return this.city;							
    }
    
    public void setCity(String city){
    	this.city=city;								
    }
    
    public JPanel getTopPanel(){
    	return this.topPanel;						
    }
    
	public void setTopPanel(JPanel topPanel){
		this.topPanel = topPanel;					
	}
	
	public ImageIcon getDeparturesIcon(){
		return departuresIcon;						
	}

	public void setDeparturesIcon(ImageIcon departuresIcon){
		this.departuresIcon = departuresIcon;		
	}
	
	public ImageIcon getArrivalsIcon(){
		return arrivalsIcon;						
	}

	public void setArrivalsIcon(ImageIcon arrivalsIcon){
		this.arrivalsIcon = arrivalsIcon;			
	}	
	
	public InfoTable getDepartures(){
		return this.departures;						
	}

	public void setDepartures(InfoTable departures){
		this.departures = departures;
		this.departuresTable = departures.getPanel();
	}
	
	public InfoTable getArrivals(){
		return this.arrivals;						
	}
	
	public void setArrivals(InfoTable arrivals){
		this.arrivals = arrivals;
		this.arrivalsTable = arrivals.getPanel();
	}	
	
	public JComponent getDeparturesTable(){
		return this.departuresTable;				
	}

	public void setDeparturesTable(JComponent departuresTable){
		this.departuresTable = departuresTable;		
	}
	
	public JComponent getArrivalsTable(){
		return this.arrivalsTable;					
	}
	
	public void setArrivalsTable(JComponent arrivalsTable){
		this.arrivalsTable = arrivalsTable;			
	}
	
	public Semaphore[] getRunway(){
		return this.runway;							
	}
	
	public void setRunway(Semaphore[] runway){
		this.runway = runway;						
	}

    public void setRunway(int runways){
    	this.runway = new Semaphore[runways];
    	for(int i =0; i<runways; i++)
    		this.runway[i] = new Semaphore(1);
    }	
	
    public JTabbedPane getTabbedPane(){
		return tabbedPane;							
	}

	public void setTabbedPane(JTabbedPane tabbedPane){
		this.tabbedPane = tabbedPane;				
	}

	private void setTabbedPane(){
    	this.tabbedPane = new JTabbedPane();     
        this.tabbedPane.addTab("", this.departuresIcon, this.departuresTable, "Departures");
        this.tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        this.tabbedPane.addTab("", this.arrivalsIcon, this.arrivalsTable, "Arrivals");
        this.tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
        this.tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);		
	}
	
	public JFrame getFrame(){
		return this.frame;						
	}

	public void setFrame(JFrame frame){
		this.frame = frame;							
	}

	private void setFrame(){
		this.frame = new JFrame((name+" Airport"));
	    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.frame.add(this.topPanel, BorderLayout.NORTH);
	    this.frame.add(this.tabbedPane, BorderLayout.CENTER);
	    this.frame.setSize(730, 400);
	    this.frame.setVisible(true);		
	}
    
    public void setTime(){
    	this.topPanel = new JPanel();
    	CurentTime t = new CurentTime(this.topPanel);
    	t.start();
    }

	public void addToDepartures(Airplane airplane) throws InterruptedException{
    	new UpdateTable(this.departures, airplane.getFlight()[0],Time.now(), 1).start();	
	}
	
	public void addToArrivals(Airplane airplane) throws InterruptedException{
    	new UpdateTable(this.arrivals, airplane.getFlight()[1], Time.now(), 1).start();	
	}

	public void updateDeparture(Airplane airplane) throws InterruptedException{
    	new UpdateTable(this.departures, airplane.getFlight()[0], Time.now(), 2).start();
    	if(airplane.getFlight()[0][3].equals("<html><font color='#00ff00'>DEPARTURED</font></html>"))
        	new UpdateTable(this.departures, airplane.getFlight()[0], Time.now().plusSeconds(60), 3).start();
	}

	public void updateArrival(Airplane airplane) throws InterruptedException{
    	new UpdateTable(this.arrivals, airplane.getFlight()[1], Time.now(), 2).start();
    	if(airplane.getFlight()[1][3].equals("<html><font color='#00ff00'>LANDED</font></html>"))
        	new UpdateTable(this.arrivals, airplane.getFlight()[1], Time.now().plusSeconds(60), 3).start();
	}
	
	public void removeDeparture(Airplane airplane, Time time) throws InterruptedException{
    	new UpdateTable(this.departures, airplane.getFlight()[0], time , 3).start();	
	}
	
	public void removeArrival(Airplane airplane, Time time) throws InterruptedException{
    	new UpdateTable(this.arrivals, airplane.getFlight()[1], time, 3).start();	
	}

}