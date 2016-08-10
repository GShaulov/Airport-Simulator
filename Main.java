import java.util.ArrayList;

//tester
public class Main {
    public static void main(String[] args){
    	ArrayList<Airport> airports = new ArrayList<>();
    	airports.add(new Airport("DME, Domodedovo(Moscow)", new InfoTable("Departures"), new InfoTable("Arrivals"), 1));
    	airports.add(new Airport("TLV, Ben-Gurion(Tel-Aviv)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("TXL, Tegel(Berlin)", new InfoTable("Departures"), new InfoTable("Arrivals"), 3));
    	airports.add(new Airport("MRV, Min-Vody(Mineralnye-Vody)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("CDG, Charles de Gaulle(Paris)", new InfoTable("Departures"), new InfoTable("Arrivals"), 1));

    	for(int i=0;i<5;i++){
    		for(int j=0; j<5; j++){
        		int sec = (int) (Math.random()*60)+10;
        		Time sheduled = new Time(Time.now().plusSeconds(sec+i));
        		Time arrival = sheduled.plusSeconds(60);
    			Pilot pilot = new Pilot("Pilot"+i+""+j, ((int)Math.random()*10+1));
    			if(i==j)	continue;
        		Airplane plane = new Airplane(pilot,"Ly10"+i+""+j, airports.get(i), airports.get(j), sheduled, arrival);
    		    plane.start();
    		}
    	}
    }   	
}