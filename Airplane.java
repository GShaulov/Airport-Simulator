package Update_18112016;



public class Airplane extends Thread{

	//variables
	private static long idGenirator;
	private long id;
	private Pilot pilot;
	private String flightNumber;
	private Airport airportOfDeparture;
	private Airport airportOfDestination;
	private boolean finishedTakingOff;
	private boolean finishedLanding;
	private Time scheduledTime;
	private Time checkInTime;
	private Time boardingTime;
	private Time arrivalTime;
	private Time expectedTime;
	private Time finalTime;
	private String[] statuses={
			"<html><font color='#ffffff'>ON TIME</font></html>",
			"<html><font color='#ffffff'>CHECK-IN</font></html>",
			"<html><font color='#00ff00'>BOARDIND</font></html>",
			"<html><font color='#ffaa00'>BOARDING-OVER</font></html>",
			"<html><font color='#ffdd00'>DELAYED</font></html>",
			"<html><font color='#ffffff'>ON-RUNWAY</font></html>",
			"<html><font color='#00ff00'>DEPARTURED</font></html>",
			"<html><font color='#ffffff'>ON TIME</font></html>",
			"<html><font color='#ffdd00'>EXPECTED</font></html>",
			"<html><font color='#ffffff'>LANDING</font></html>",
			"<html><font color='#00ff00'>LANDED</font></html>"};
	private String[][] flight;

	//constructor
	public Airplane(Pilot pilot, String flightNumber, Airport airportOfDeparture, Airport airportOfDestination, Time scheduledTime, Time arrivalTime) {
		setId(++idGenirator);
		setPilot(pilot);
		setFlightNumber(flightNumber);
		setAirportOfDeparture(airportOfDeparture);
		setAirportOfDestination(airportOfDestination);
		setFinishedTakingOff(false);
		setFinishedLanding(false);
		setScheduledTime(scheduledTime);
		setCheckInTime(new Time(scheduledTime.getTime().minusSeconds(20)));
		setBoardingTime(new Time(scheduledTime.getTime().minusSeconds(10)));
		setArrivalTime(arrivalTime);
		setExpectedTime(scheduledTime);
		setFinalTime(arrivalTime);
		setFlight(new String[2][6]);
	}

	public void run(){
		try {
			addToDepartures();
			checkIn();
			boarding();
			while(!finishedTakingOff){
					tryToTakeOff();
			}
			addToArrivals();
			fly();
			while(!finishedLanding){
				tryToLand();	
			}
		} catch (InterruptedException e) {}
	}

	private void addToDepartures() throws InterruptedException{
		setDepartureStatus(this.statuses[0]);
		System.out.printf("%s From %s: New flight, %s - %s. Check-In Time is %s.\n", Time.now(), flightNumber, airportOfDeparture.getCity(), airportOfDestination.getCity(), this.checkInTime);
		this.airportOfDeparture.addToDepartures(this);
		sleep(this.checkInTime.getWaiting());
	}
	
	private void checkIn() throws InterruptedException {
		setDepartureStatus(this.statuses[1]);
		System.out.printf("%s From %s: %s starts.\n", Time.now(), flightNumber, "CHECK-IN");
		this.airportOfDeparture.updateDeparture(this);
		sleep(boardingTime.getWaiting());
	}
	
	private void boarding() throws InterruptedException {
		setDepartureStatus(this.statuses[2]);
		System.out.printf("%s From %s: %s finished. %s starts.\n", Time.now(), flightNumber, "CHECK-IN", "BOARDIND");
		this.airportOfDeparture.updateDeparture(this);
		
		sleep(getRandom());
		setDepartureStatus(this.statuses[3]);
		System.out.printf("%s From %s: %s. Ask for free runway.\n", Time.now(), flightNumber, "BOARDING-OVER");
		this.airportOfDeparture.updateDeparture(this);
		sleep(scheduledTime.getWaiting());
	}
	
	private void tryToTakeOff() throws InterruptedException{
		if(this.airportOfDeparture.getRunway().length==0){
			int time = getRandom();
			setExpectedTime(Time.now().plusSeconds(time/1000));
			setDepartureStatus(this.statuses[4]);
			System.out.printf("%s From %s: There is no free runway. %s until %s. \n", Time.now(), flightNumber, "DELAYED", Time.now().plusSeconds(time/1000));
			this.airportOfDeparture.updateDeparture(this);
			sleep(time);
			return;			
		}else{
			for(int i=0; i<this.airportOfDeparture.getRunway().length; i++){
				if(!this.airportOfDeparture.getRunway()[i].tryAcquire()){
					if(i==this.airportOfDeparture.getRunway().length-1){
						int time = getRandom();
						setExpectedTime(Time.now().plusSeconds(time/1000));
						setDepartureStatus(this.statuses[4]);
						System.out.printf("%s From %s: There is no free runway. %s until %s. \n", Time.now(), flightNumber, "DELAYED", Time.now().plusSeconds(time/1000));
						this.airportOfDeparture.updateDeparture(this);
						sleep(time);
						return;			
					}
				}
				else{
					setDepartureStatus(this.statuses[5]);
					setDepartureRunway(i+1);
					System.out.printf("%s To %s: Have clear to take off on runway %s.\n", Time.now(), flightNumber, getDepartureRunway() );
					this.airportOfDeparture.updateDeparture(this);
					System.out.printf("%s From %s: %s. Taking off on %s. Schedule time %s.\n", Time.now(), flightNumber, "ON-RUNWAY", getDepartureRunway(), Time.now().plusSeconds(pilot.getTakingOffTime()/1000));
					
					sleep(pilot.getTakingOffTime());
					
					this.airportOfDeparture.getRunway()[i].release();
					this.finishedTakingOff=true;
					setExpectedTime(Time.now());
					setDepartureStatus(this.statuses[6]);
					System.out.printf("%s From %s: Departured. \n", Time.now(), flightNumber);
					this.airportOfDeparture.updateDeparture(this);
					return;			
				}
			}
		}
	}
	
	private void addToArrivals() throws InterruptedException{
		setArrivalStatus(this.statuses[7]);
		this.arrivalTime = this.expectedTime.plusSeconds(60);
		this.finalTime = this.arrivalTime;

		System.out.printf("%s From %s: Flight %s - %s.Schedule Landing Time %s.\n", Time.now(), flightNumber, airportOfDeparture.getCity(), airportOfDestination.getCity(), this.arrivalTime);
		this.airportOfDestination.addToArrivals(this);
		sleep(this.arrivalTime.getWaiting());
	}
	
	private void fly() throws InterruptedException{
		sleep(1000);
		System.out.printf("%s From %s: Ask for free runway to land.\n", Time.now(), flightNumber);
	}
	
	private void tryToLand() throws InterruptedException{
		if(this.airportOfDestination.getRunway().length==0){
			int time = getRandom();
			setArrivalStatus(this.statuses[8]);
			System.out.printf("%s From %s: There is no free runway. %s until %s. \n", Time.now(), flightNumber, "EXPECTED", Time.now().plusSeconds(time/1000));
			setFinalTime(Time.now().plusSeconds(time/1000));
			this.airportOfDestination.updateArrival(this);
			sleep(time);
			return;	
		}else{
			for(int i=0; i<this.airportOfDestination.getRunway().length; i++){
				if(!this.airportOfDestination.getRunway()[i].tryAcquire()){
					if(i==this.airportOfDestination.getRunway().length-1){
						int time = getRandom();
						setArrivalStatus(this.statuses[8]);
						System.out.printf("%s From %s: There is no free runway. %s until %s. \n", Time.now(), flightNumber, "EXPECTED", Time.now().plusSeconds(time/1000));
						setFinalTime(Time.now().plusSeconds(time/1000));
						this.airportOfDestination.updateArrival(this);
						sleep(time);
						return;			
					}
				}
				else{ 
					setArrivalStatus(this.statuses[9]);
					setArrivalRunway(i+1);
					System.out.printf("%s From %s: LANDING. Schedule Time is %s. \n", Time.now(), flightNumber, Time.now().plusSeconds(pilot.getLandingTime()/1000));
					this.airportOfDestination.updateArrival(this);
					sleep(pilot.getLandingTime());
					this.airportOfDestination.getRunway()[i].release();
					this.finishedLanding=true;
					setFinalTime(Time.now());
					setArrivalStatus(this.statuses[10]);
					System.out.printf("%s From %s: LANDED. \n", Time.now(), flightNumber);
					this.airportOfDestination.updateArrival(this);
					return;				
				}
			}
		}
	}
	
	public long getId(){
		return id;									
	}

	public void setId(long id){
		this.id = id;								
	}

	public Pilot getPilot(){
		return pilot;							
	}
	
	public void setPilot(Pilot pilot){
		this.pilot = pilot;						
	}

	public String getFlightNumber(){
		return flightNumber;							
	}
	
	public void setFlightNumber(String flightNumber){
		this.flightNumber = flightNumber;	
	}
	
	public void setDepartureStatus(String status){
		this.flight[0][3] = status;	
	}

	public void setArrivalStatus(String status){
		this.flight[1][3] = status;	
	}
	
	public String getDepartureRunway(){
		return this.flight[0][5];
	}
	
	public void setDepartureRunway(int i){
		this.flight[0][5] = "D" + i;
	}

	public String getArrivalRunway(){
		return this.flight[1][5];
	}
	
	public void setArrivalRunway(int i){
		this.flight[1][5] = "A" + i;	
	}
	
	public Airport getAirportOfDeparture(){
		return airportOfDeparture;						
	}

	public void setAirportOfDeparture(Airport airportOfDeparture){
		this.airportOfDeparture = airportOfDeparture;	
	}

	public Airport getAirportOfDestination(){
		return airportOfDestination;						
	}

	public void setAirportOfDestination(Airport airportOfDestination){
		this.airportOfDestination = airportOfDestination;
	}

	public boolean isFinishedTakingOff(){
		return finishedTakingOff;			
	}

	public void setFinishedTakingOff(boolean finishedTakingOff){
		this.finishedTakingOff = finishedTakingOff;		
	}

	public boolean isFinishedLanding(){
		return finishedLanding;							
	}

	public void setFinishedLanding(boolean finishedLanding){
		this.finishedLanding = finishedLanding;		
	}

	public Time getScheduledTime(){
		return scheduledTime;					
	}

	public void setScheduledTime(Time scheduledTime){
		this.scheduledTime = scheduledTime;					
	}

	public Time getCheckInTime(){
		return checkInTime;				
	}

	public void setCheckInTime(Time checkInTime){
		this.checkInTime = checkInTime;			
	}

	public Time getBoardingTime(){
		return boardingTime;
	}

	public void setBoardingTime(Time boardingTime){
		this.boardingTime = boardingTime;			
	}

	public Time getArrivalTime(){
		return arrivalTime;						
	}

	public void setArrivalTime(Time arrivalTime){
		this.arrivalTime = arrivalTime;					
	}

	public Time getExpectedTime(){
		return expectedTime;								
	}

	public void setExpectedTime(Time expectedTime){
		this.expectedTime = expectedTime;		
	}
	
	public Time getFinalTime(){
		return finalTime;								
	}

	public void setFinalTime(Time finalTime){
		this.finalTime = finalTime;		
	}

	public String[] getStatuses(){
		return statuses;									
	}

	public void setStatuses(String[] statuses){
		this.statuses = statuses;					
	}

	public String[][] getFlight(){
		setDepartureFlight();
		setArrivalFlight();
		return this.flight;
	}

	public void setFlight(String[][] flight){
		this.flight = flight;								
	}
	
	public void setDepartureFlight(){
		try{
			this.flight[0][0]=""+this.scheduledTime;
			this.flight[0][1]=this.flightNumber;
			this.flight[0][2]=this.airportOfDestination.getCity();
			this.flight[0][4]=""+this.expectedTime;
		}catch(Exception e){}
	}
	
	public void setArrivalFlight(){
		try{
			this.flight[1][0]=""+this.arrivalTime;
			this.flight[1][1]=this.flightNumber;
			this.flight[1][2]=this.airportOfDeparture.getCity();
			this.flight[1][4]=""+this.finalTime;
		}catch(Exception e){}
	}
	
	private int getRandom(){
		int time = (5+(int)(Math.random()*5))*1000;
		return time;
	}
}
