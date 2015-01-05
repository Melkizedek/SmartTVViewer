package user;

import java.util.ArrayList;
import java.util.Calendar;

import tv.TVChannel;

public class Child extends User {
    private Parent parent;
    private ArrayList<TVChannel> bannedChannels;
    private Calendar fromTime;
    private Calendar toTime;
    private long maxTime;

    public Child(String name, String password, Parent parent) {
	super(name, password);
	this.parent = parent;
	this.maxTime = -1;
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
