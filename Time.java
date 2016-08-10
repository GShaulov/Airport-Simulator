import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Time {
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	private ZonedDateTime time;
	
	public Time(int year, int month, int day, int hour, int minute, int second){
		setYear(year);
		setMonth(month);
		setDay(day);
		setHour(hour);
		setMinute(minute);
		setSecond(second);
		setTime();
	}
	
	public Time(int hour, int minute, int second){
		ZonedDateTime t = ZonedDateTime.now();
		setYear(t.getYear());
		setMonth(t.getMonthValue());
		setDay(t.getDayOfMonth());
		setHour(hour);
		setMinute(minute);
		setSecond(second);
		setTime();
	}
	
	public Time(ZonedDateTime time) {
		setTime(time);
	}
	
	public Time(Time time) {
		setYear(time.getYear());
		setMonth(time.getMonth());
		setDay(time.getDay());
		setHour(time.getHour());
		setMinute(time.getMinute());
		setSecond(time.getSecond());
		setTime();	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		if(year>0)
			this.year = year;
	}

	public Time minusYears(long years) {
		setTime(this.time.minusYears(years));
		return this;
	}
	
	public Time plusYears(long years) {
		setTime(this.time.plusYears(years));
		return this;
	}
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		if(month>0 && month<13)
			this.month = month;
	}
	
	public Time minusMonths(long months) {
		setTime(this.time.minusMonths(months));
		return this;
	}
		
	public Time plusMonths(long months) {
		setTime(this.time.plusMonths(months));
		return this;
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		if(day>0 && day<32){
			if(this.month==1 || this.month==3 || this.month==5 || this.month==7 || this.month==8 || this.month==10 || this.month==12){
				this.day = day;
			}else 
				if(this.month!=2 && day<31){
					this.day = day;			
				}else
					if(day<30 && year%4==0){
						this.day = day;
					}else
						if(day<29){
							this.day = day;
						}
		}
	}
	
	public Time minusDays(long days) {
		setTime(this.time.minusDays(days));
		return this;
	}
		
	public Time plusDays(long days) {
		setTime(this.time.plusDays(days));
		return this;
	}
		
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		if(hour>=0 && hour<=23)
			this.hour = hour;
	}

	public Time minusHours(long hours) {
		setTime(this.time.minusHours(hours));
		return this;
	}
		
	public Time plusHours(long hours) {
		setTime(this.time.plusHours(hours));
		return this;
	}
		
	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		if(minute>=0 && minute<=59)
			this.minute = minute;
	}

	public Time minusMinutes(long minutes) {
		setTime(this.time.minusMinutes(minutes));
		return this;
	}
		
	public Time plusMinutes(long minutes) {
		setTime(this.time.plusMinutes(minutes));
		return this;
	}
	
	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		if(second>=0 && second<=59)
			this.second = second;
	}

	public Time minusSeconds(long seconds) {
		setTime(this.time.minusSeconds(seconds));
		return this;
	}
	
	public Time plusSeconds(long seconds) {
		setTime(this.time.plusSeconds(seconds));
		return this;
	}
	
	public ZonedDateTime getTime() {
		return time;
	}

	public void setTime(ZonedDateTime time) {
		this.year=time.getYear();
		this.month=time.getMonthValue();
		this.day=time.getDayOfMonth();
		this.hour=time.getHour();
		this.minute=time.getMinute();
		this.second=time.getSecond();
		this.time = time;
	}
	
	public void setTime(){
		this.time = ZonedDateTime.of(year, month, day, hour, minute, second, 0, ZoneId.of("Asia/Jerusalem"));
	}
	
	public long getWaiting(){
		long time = this.time.toInstant().toEpochMilli()- ZonedDateTime.now().toInstant().toEpochMilli();
		if(time<0)
			return 0;
		return time;
	}
	
	public static Time now(){
		return new Time(ZonedDateTime.now());
	}
	
	@Override
	public String toString() {
		return String.format("%tH:%<tM:%<tS", this.time);
	}
	
	public void printDate() {
		String s = String.format("%td.%<tm.%<tY %<tH:%<tM:%<tS", this.time);
		System.out.println(s);
	}	
}
