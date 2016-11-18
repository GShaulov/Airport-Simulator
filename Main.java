import java.util.ArrayList;

//tester
public class Main {
    public static void main(String[] args){
    	char[] ab = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    	ArrayList<Airport> airports = new ArrayList<>();
    	airports.add(new Airport("DME, Domodedovo(Moscow)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("TLV, Ben-Gurion(Tel-Aviv)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("TXL, Tegel(Berlin)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("MRV, Min-Vody(Mineralnye-Vody)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("CDG, Charles de Gaulle(Paris)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("NAL, Nalchik Airport(Nalchik))", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("MIA, Miami International Airport(Miami)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("AER, Adler Airport(Sochi))", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("MUC, Munich Airport(Munich))", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));
    	airports.add(new Airport("BKK, Suvarnabhumi Airport(Bangkok)", new InfoTable("Departures"), new InfoTable("Arrivals"), 2));

    	for(int i=0;i<10;i++){
    		for(int j=0; j<10; j++){
        		int sec = (int) (Math.random()*60)+10;
        		int fly = (int) (Math.random()*25);
        		Time sheduled = new Time(Time.now().plusSeconds(sec+i));
        		Time arrival = new Time(Time.now().plusSeconds(sec+i+60));
    			Pilot pilot = new Pilot("Pilot"+i+""+j, ((int)Math.random()*10+1));
    			if(i==j)	continue;
        		Airplane plane = new Airplane(pilot,""+ab[fly]+ab[fly+1]+i+"0"+j, airports.get(i), airports.get(j), sheduled, arrival);
    		    plane.start();
    		}
    	}
    }   	
}

