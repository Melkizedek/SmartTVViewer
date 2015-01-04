package util;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import tv.*;

public class Initializer {
    private ArrayList<TVChannel> tvChannelList;
    private ArrayList<TVSeries> tvSeriesList;

    private Initializer() {
	initializeTVSeries();
	initializeTVChannels();	
    }

    public static void initialize() {
	new Initializer();
    }

    private void initializeTVChannels() {
	tvChannelList = new ArrayList<TVChannel>();
	
	TVChannel c1 = new TVChannel("channel 1", new File("video/Meshuggah Face of Wall Street.mp4"));
	TVChannel c2 = new TVChannel("channel 2", new File("video/Meshuggah Face of Wall Street.mp4"));
	TVChannel c3 = new TVChannel("channel 3", new File("video/Meshuggah Face of Wall Street.mp4"));
	
	Calendar cal = Calendar.getInstance();
	
	cal.set(0, 0, 0, 0, 0, 0);
	c1.addBroadcast(cal, tvSeriesList.get(0));
	
	cal.set(0, 0, 0, 8, 30, 0);
	c1.addBroadcast(cal, tvSeriesList.get(0));
	
	cal.set(0, 0, 0, 17, 0, 0);
	c1.addBroadcast(cal, tvSeriesList.get(0));
	
	tvChannelList.add(c1);
	tvChannelList.add(c2);
	tvChannelList.add(c3);
    }

    private void initializeTVSeries() {
	tvSeriesList = new ArrayList<TVSeries>();
	
	tvSeriesList.add(new TVSeries("series 1"));
	tvSeriesList.add(new TVSeries("series 2"));
	tvSeriesList.add(new TVSeries("series 3"));
    }

    public ArrayList<TVChannel> getTvChannelList() {
	return tvChannelList;
    }

    public ArrayList<TVSeries> getTvSeriesList() {
	return tvSeriesList;
    }

}
