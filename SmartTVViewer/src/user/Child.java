package user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tv.TVChannel;

public class Child extends User {
    private Parent parent;
    private ArrayList<TVChannel> bannedChannels;
    private Calendar fromTime;
    private Calendar toTime;
    private long maxTime;
    
    SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm", Locale.GERMANY);

    public Child(String name, String password, Parent parent) {
	super(name, password);
	this.parent = parent;
	this.maxTime = -1;
    }
    
    public boolean isInTimeRestriction(){
	Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        Date date = parseDate(hour + ":" + minute);
        Date dateCompareOne = parseDate(fromTime.get(Calendar.HOUR_OF_DAY) + ":" + fromTime.get(Calendar.MINUTE));
        Date dateCompareTwo = parseDate(toTime.get(Calendar.HOUR_OF_DAY) + ":" + toTime.get(Calendar.MINUTE));

        if ( dateCompareOne.before( date ) && dateCompareTwo.after(date)) {
            return true;
        }
	
	return false;
    }

    private Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
    
    public static boolean isUnderMaxTime(){
	return true;
    }
    
    public void setBannedChannels(ArrayList<TVChannel> channels){
	this.bannedChannels = channels;
    }
    
    public ArrayList<TVChannel> getBannedChannels(){
	return this.bannedChannels;
    }

    public Parent getParent() {
	return parent;
    }

    public void setParent(Parent parent) {
	this.parent = parent;
    }

    public Calendar getFromTime() {
        return fromTime;
    }

    public void setFromTime(Calendar fromTime) {
        this.fromTime = fromTime;
    }

    public Calendar getToTime() {
        return toTime;
    }

    public void setToTime(Calendar toTime) {
        this.toTime = toTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

}
