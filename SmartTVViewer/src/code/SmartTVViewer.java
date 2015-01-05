package code;

import tv.TVBroadcast;

public class SmartTVViewer{
    public static void setReminder(TVBroadcast broadcast, int reminder){
	new Thread(new Reminder(broadcast, reminder)).start();
    }
}
