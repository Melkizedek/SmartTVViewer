package tv;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;

public class TVChannel {
    private String name;
    private File source;

    private HashMap<Calendar, TVSeries> broadcasts;

    public TVChannel(String name, File file) {
	this.name = name;
	this.source = file;
    }

    public void addBroadcast(Calendar time, TVSeries series) {
	broadcasts.put(time, series);
    }

    public HashMap<Calendar, TVSeries> getBroadcasts() {
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

}
