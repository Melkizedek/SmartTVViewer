package util;

import java.io.File;
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

		TVChannel c1 = new TVChannel("Channel 1", new File("video/video01.mp4"));
		TVChannel c2 = new TVChannel("Channel 2", new File("video/video02.mp4"));
		TVChannel c3 = new TVChannel("Channel 3", new File("video/video03.mp4"));

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		c1.addBroadcast(cal, tvSeriesList.get(0));

		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 8);
		cal.set(Calendar.MINUTE, 30);
		c1.addBroadcast(cal, tvSeriesList.get(1));

		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 17);
		cal.set(Calendar.MINUTE, 0);
		c1.addBroadcast(cal, tvSeriesList.get(2));

		cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() + 1000 * 5 * 60);
		c1.addBroadcast(cal, tvSeriesList.get(2));

		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		c2.addBroadcast(cal, tvSeriesList.get(3));

		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 14);
		cal.set(Calendar.MINUTE, 45);
		c2.addBroadcast(cal, tvSeriesList.get(4));

		cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() + 1000 * 6 * 60);
		c2.addBroadcast(cal, tvSeriesList.get(4));

		tvChannelList.add(c1);
		tvChannelList.add(c2);
		tvChannelList.add(c3);
	}

	private void initializeTVSeries() {
		tvSeriesList = new ArrayList<TVSeries>();

		tvSeriesList.add(new TVSeries("Series 1"));
		tvSeriesList.add(new TVSeries("Series 2"));
		tvSeriesList.add(new TVSeries("Series 3"));
		tvSeriesList.add(new TVSeries("Series 4"));
		tvSeriesList.add(new TVSeries("Series 5"));
		tvSeriesList.add(new TVSeries("Series 6"));
	}
}
