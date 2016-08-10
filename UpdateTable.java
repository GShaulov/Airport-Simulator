
public class UpdateTable extends Thread{
	
	private InfoTable infoTable;
	private String[] flight;
	Time time;
	private int option;
	
	UpdateTable(InfoTable infoTable, String[] flight, Time time, int option){
		setInfoTable(infoTable);
		setFlight(flight);
		setTime(time);
		setOption(option);
	}

	public InfoTable getInfoTable() {
		return infoTable;
	}

	public void setInfoTable(InfoTable infoTable) {
		this.infoTable = infoTable;
	}

	public String[] getFlight() {
		return flight;
	}

	public void setFlight(String[] flight) {
		this.flight = flight;
	}
	
	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public void addToInfoTable() throws InterruptedException {
		infoTable.addRow(flight);		
	}
	
	public void updateInfoTable() throws InterruptedException {
		infoTable.updateRow(flight);		
	}
	
	public void removeFromInfoTable() throws InterruptedException {
		infoTable.removeRow(flight);		
	}

	@Override
	public void run() {
		try {
			switch(this.option){
			
				//addToInfoTable
				case 1:
					sleep(this.time.getWaiting());
					System.out.printf("%s To %s: Flight added!\n", Time.now(), flight[1]);
					addToInfoTable();
					break;
					
				//UpdateInfoTable
				case 2:
					sleep(this.time.getWaiting());
					updateInfoTable();
					System.out.printf("%s To %s: Flight updated!\n", Time.now(), flight[1]);
					break;
					
				//removeFromInfoTable
				case 3:
					sleep(this.time.getWaiting());
					removeFromInfoTable();
					System.out.printf("%s To %s: Flight removed!\n", Time.now(), flight[1]);
					break;
					
				default:
					break;
			}
		} catch (InterruptedException e) {}	
	}
	
}
