package code;

import gui.SmartTVViewerView;
import tv.TVBroadcast;
import user.Child;
import user.UserManagement;

public class SmartTVViewer {
    public static void setReminder(TVBroadcast broadcast, int reminder) {
	new Thread(new Reminder(broadcast, reminder)).start();
    }

    public static void startTimeRestrictionCheck() {
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		for(;;) {
		    if(!((Child) UserManagement.user).isInTimeRestriction()){
			SmartTVViewerView.notInTimeRestriction(null);
		    }
		    try {
			Thread.sleep(10000);
		    } catch(InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	}).start();
    }
}
