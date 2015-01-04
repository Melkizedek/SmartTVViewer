package util;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import tv.*;

public class Initializer {
    public static ArrayList<TVChannel> tvChannelList;
    public static ArrayList<TVSeries> tvSeriesList;

    private Initializer() {
	initializeTVSeries();
	initializeTVChannels();
    }

    public static void initialize() {
	new Initializer();
    }

    private void initializeTVChannels() {
	tvChannelList = new ArrayList<TVChannel>();
	

	TVChannel c1 = new TVChannel("channel 1", new File(
		"video/video01.mp4"));
	TVChannel c2 = new TVChannel("channel 2", new File(
		"video/video02.mp4"));
	TVChannel c3 = new TVChannel("channel 3", new File(
		"video/video03.mp4"));

	Calendar cal = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();
	Calendar cal3 = Calendar.getInstance();

	cal.set(2015, 1, 1, 0, 0, 0);
	c1.addBroadcast(cal, tvSeriesList.get(0));

	cal2.set(2015, 1, 1, 8, 30, 0);
	c1.addBroadcast(cal2, tvSeriesList.get(1));

	cal3.set(2015, 1, 1, 17, 0, 0);
	c1.addBroadcast(cal3, tvSeriesList.get(2));

	tvChannelList.add(c1);
	tvChannelList.add(c2);
	tvChannelList.add(c3);
    }

    private void initializeTVSeries() {
	tvSeriesList = new ArrayList<TVSeries>();

	tvSeriesList.add(new TVSeries("series 1"));
	tvSeriesList.add(new TVSeries("series 2222222222222222222222222"));
	tvSeriesList.add(new TVSeries("series 3"));

    }
}
