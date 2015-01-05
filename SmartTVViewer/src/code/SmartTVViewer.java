package code;

import gui.SmartTVViewerView;
import tv.TVBroadcast;
import user.Child;
import user.UserManagement;

public class SmartTVViewer {
    public static boolean playing;

    public static void increaseTimeWatched() {
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		for(;;) {
		    if(playing) {
			if(!((Child) UserManagement.user).isUnderMaxTime()) {
			    SmartTVViewerView.notInTimeRestriction(null);
			}
			try {
			    Thread.sleep(2000);
			    ((Child) UserManagement.user).incTimeWatched(2000);
			    System.out.println(((Child) UserManagement.user)
				    .getTimeWatched());
			} catch(InterruptedException e) {
			    e.printStackTrace();
			}
		    }
		    else {
			break;
		    }
		}
	    }
	}).start();
    }

    public static void setReminder(TVBroadcast broadcast, int reminder) {
	new Thread(new Reminder(broadcast, reminder)).start();
    }

    public static void startTimeRestrictionCheck() {
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		for(;;) {
		    if(!((Child) UserManagement.user).isInTimeRestriction()) {
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
