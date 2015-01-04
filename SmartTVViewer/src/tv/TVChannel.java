package tv;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TVChannel {
    private String name;
    private File source;

    private ArrayList<TVBroadcast> broadcasts;

    public TVChannel(String name, File file) {
	this.name = name;
	this.source = file;
	
	broadcasts = new ArrayList<TVBroadcast>();
    }

    public void addBroadcast(Calendar time, TVSeries series) {
	broadcasts.add(new TVBroadcast(time, series));
    }

    public ArrayList<TVBroadcast> getBroadcasts() {
	return broadcasts;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public File getFile() {
	return source;
    }

    public void setFile(File file) {
	this.source = file;
    }

    @Override
    public String toString() {
	return name;
    }

}
