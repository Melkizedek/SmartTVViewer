package code;

import java.util.Calendar;
import gui.SmartTVViewerView;
import tv.TVBroadcast;

public class Reminder implements Runnable {
    private TVBroadcast broadcast;
    private int reminder;

    public Reminder(TVBroadcast broadcast, int reminder) {
	this.broadcast = broadcast;
	this.reminder = reminder;
    }

    @Override
    public void run() {
	long reminderTimeInMilliseconds = 3600000
		* broadcast.getTime().get(Calendar.HOUR_OF_DAY) + 60000
		* broadcast.getTime().get(Calendar.MINUTE);

	Calendar curTime = Calendar.getInstance();
	curTime.set(Calendar.HOUR_OF_DAY, 0);
	curTime.set(Calendar.MINUTE, 0);
	curTime.set(Calendar.SECOND, 0);
	curTime.set(Calendar.MILLISECOND, 0);

	long tmpTime = curTime.getTimeInMillis();
	long curTimeInMilliseconds = System.currentTimeMillis() - tmpTime;

	try {
	    Thread.sleep(reminderTimeInMilliseconds - curTimeInMilliseconds
		    - (reminder * 60000));

	    SmartTVViewerView.displayReminder(broadcast.getSeries().toString()
		    + " will start in " + reminder + " minutes on "
		    + broadcast.getChannel().toString());
	} catch(InterruptedException e) {
	    e.printStackTrace();
	}
    }

}
