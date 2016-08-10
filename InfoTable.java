
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import java.util.concurrent.Semaphore;

//import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InfoTable {
	
	//variables
	private String[] columnNames = {"SCHEDULE TIME", "FLIGHT", "FROM/TO", "STATUS", "EXPECTED TIME", "GATE"};
	private Object[][] rowData;
	private DefaultTableModel model;
	private JTable table;
	private JPanel panel;
	private Vector<String> flights;
	private Semaphore tableLock;

	//constructur
	public InfoTable(String title){
		setRowData(new Object[0][0]);
		setModel(new DefaultTableModel(this.rowData, this.columnNames));
		setFlights(new Vector<String>());
		setTableLock(new Semaphore(1));
		this.table = new JTable(this.model){
			private static final long serialVersionUID = 8374272609169247444L;
			
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		this.table.getTableHeader().setReorderingAllowed(false);
	    this.table.setBackground(new Color(0,0,225));
	    this.table.getColumnModel().getColumn(0).setPreferredWidth(100);//time
	    this.table.getColumnModel().getColumn(1).setPreferredWidth(60);//flight
	    this.table.getColumnModel().getColumn(2).setPreferredWidth(190);//from/to
	    this.table.getColumnModel().getColumn(3).setPreferredWidth(110);//status
	    this.table.getColumnModel().getColumn(4).setPreferredWidth(100);//time
	    this.table.getColumnModel().getColumn(5).setPreferredWidth(40);//gate
	    
	    JTableHeader header = this.table.getTableHeader();
	    header.setFont(new Font("Dialog", Font.CENTER_BASELINE, 12));
	    header.setBackground(Color.black);
	    header.setForeground(Color.yellow);
	    
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.setFont(new Font("Microsoft JhengHei", Font.BOLD, 13));
		this.table.setForeground(Color.WHITE);
		this.panel = new JPanel(new BorderLayout ());
		this.panel.add(new JScrollPane(this.table));
		this.panel.setSize(730, 400);
	}
	
	public String[] getColumnNames() 					{		return columnNames;					}

	public void setColumnNames(String[] columnNames) 	{		this.columnNames = columnNames;		}

	public Object[][] getRowData()	 					{		return rowData;						}

	public void setRowData(Object[][] rowData) 			{		this.rowData = rowData;				}

	public DefaultTableModel getModel() 				{		return model;						}

	public void setModel(DefaultTableModel model) 		{		this.model = model;					}

	public JTable getTable() 							{		return table;						}

	public void setTable(JTable table) 					{		this.table = table;					}

	public void setPanel(JPanel panel) 					{		this.panel = panel;					}

	public JPanel getPanel()							{		return this.panel;					}
	
	public Vector<String> getFlights() 					{		return flights;						}

	public void setFlights(Vector<String> flights) 		{		this.flights = flights;				}

	public Semaphore getTableLock() 					{		return tableLock;					}

	public void setTableLock(Semaphore tableLock) 		{		this.tableLock = tableLock;			}

	public synchronized void addRow(String[] flight) throws InterruptedException {
		while(!this.tableLock.tryAcquire())
			wait();
		int index = this.flights.indexOf(flight[1]);
		if(index==-1){
			this.flights.add(flight[1]);
			this.model.addRow(flight);
		}
		else{
			updateRow(flight);
		}
		this.tableLock.release();
		notify();
	}
	
	public synchronized void removeRow(String[] flight) throws InterruptedException {
		while(!this.tableLock.tryAcquire())
			wait();
		int index = this.flights.indexOf(flight[1]);
		if(index!=-1){
			this.flights.remove(index);
		    this.model.removeRow(index);
		}
		this.tableLock.release();
		notify();
	}
	
	public synchronized void updateRow(String[] flight) throws InterruptedException {
		while(!this.tableLock.tryAcquire())
			wait();
		int index = this.flights.indexOf(flight[1]);
		if(index==-1){
			addRow(flight);
		}
		else{
			for(int i=0; i<flight.length; i++)
				model.setValueAt(flight[i], index, i);
		}
		this.tableLock.release();
		notify();

	}	

}