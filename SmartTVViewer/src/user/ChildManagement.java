package user;

import java.util.ArrayList;
import java.util.Calendar;

import database.DataBase;
import tv.TVChannel;
import util.Initializer;

public class ChildManagement {
    public static Child selectedChild;

    public static void setRestrictions(Calendar fromTime, Calendar toTime, long maxTime, ArrayList<TVChannel> channels){
	DataBase db = DataBase.getInstance();
	
	if(channels != null){
	    selectedChild.setBannedChannels(channels);
	    db.addChannelRestrictions(channels, selectedChild);
	}
	if(fromTime != null && toTime != null){
	    selectedChild.setFromTime(fromTime);
	    selectedChild.setToTime(toTime);
	    
	    db.addTimeRestriction(fromTime, toTime, selectedChild);
	}
	if(maxTime >= 0){
	    selectedChild.setMaxTime(maxTime);
	    
	    db.setMaxTime(selectedChild, maxTime);
	}
    }
    
    public static User getRestrictions(Child child){
	DataBase db = DataBase.getInstance();
	
	ArrayList<String> channelsString = db.getRestrictedChannels(child);
	ArrayList<TVChannel> channels = new ArrayList<TVChannel>();
	
	for(int i = 0; i < channelsString.size(); i++){
	    for(int j = 0; j < Initializer.tvChannelList.size(); j++){
		if(Initializer.tvChannelList.get(j).toString().equals(channelsString.get(i).toString())){
		    channels.add(Initializer.tvChannelList.get(j));
		    break;
		}
	    }
	}
	child.setBannedChannels(channels);
	child.setMaxTime(db.getMaxTime(child));
	child.setFromTime(db.getTimeRestrictionBegin(child));
	child.setToTime(db.getTimeRestrictionEnd(child));
	
	Calendar today = Calendar.getInstance();
	Calendar c = db.getDay(child);
	if(c != null && c.get(Calendar.DAY_OF_WEEK) == today.get(Calendar.DAY_OF_WEEK)){
	    child.setTimeWatched(db.getActualTime(child));
	}else{
	    child.setTimeWatched(0);
	}
	
	return child;
    }
}
