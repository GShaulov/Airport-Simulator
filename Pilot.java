

public class Pilot {
	private static long idGenirator;
	private long id;
	private String name;
	private int rank;
	private int takingOffTime;
	private int landingTime;
	private int[][] timeRange ={ {200,400},{100,110},{90,100},{80,90},{70,80},{60,70},{50,60},{40,50},{30,40},{20,30},{10,20},{5,10} }; 
	
	public Pilot(String name, int rank){
		setId(++idGenirator);
		setName(name);
		setRank(rank);
		setTakingOffTime();
		setLandingTime();
	}

	public long getIdGenirator(){
		return idGenirator;
	}
	
	public void setIdGenirator(long id){
		idGenirator=id;
	}
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id=id;
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public int getRank(){
		return this.rank;
	}
	
	public void setRank(int rank){
		this.rank=rank;
	}
			
	public int getTakingOffTime() {
		return this.takingOffTime;
	}

	public void setTakingOffTime() {
		this.takingOffTime = getTime();
	}
	
	public void setTakingOffTime(int takingOffTime) {
		this.takingOffTime = takingOffTime;
	}

	public int getLandingTime() {
		return this.landingTime;
	}

	public void setLandingTime() {
		this.landingTime = getTime();
	}

	public void setLandingTime(int landingTime) {
		this.landingTime = landingTime;
	}
	
	public int[][] getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(int[][] timeRange) {
		this.timeRange = timeRange;
	}

	private int getTime(){
		int x = this.timeRange[this.rank][0];
		int y = this.timeRange[this.rank][1];
		int time = ((int)(Math.random()*Math.abs(y-x)+1)+x)*1000;
		return time;
	}
	
	@Override
	public String toString() {
		return "Pilot " + this.id + ",  " + this.name + " [" + this.rank + " rank], TakingOff: " + this.takingOffTime + " Landing: " + this.landingTime;
	}
	
}