import java.util.Timer;

public class Time {
	public static void main(String[] args) { 
		Timer timer = new Timer();  
        timer.schedule(new Vote(), 0, 60*60000);  
	}


}
