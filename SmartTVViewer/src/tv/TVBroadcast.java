package tv;

import java.util.Calendar;

public class TVBroadcast {
    private Calendar time;
    private TVSeries series;
    private TVChannel channel;

    public TVBroadcast(Calendar time, TVSeries series, TVChannel channel) {
	this.time = time;
	this.series = series;
	this.channel = channel;
    }

    public Calendar getTime() {
	return time;
    }

    public void setTime(Calendar time) {
	this.time = time;
    }

    public TVSeries getSeries() {
	return series;
    }

    public void setSeries(TVSeries series) {
	this.series = series;
    }

    public TVChannel getChannel() {
	return channel;
    }

    public void setChannel(TVChannel channel) {
	this.channel = channel;
    }

    @Override
    public String toString() {
	String hour = String.valueOf(time.get(Calendar.HOUR_OF_DAY));
	String minute = String.valueOf(time.get(Calendar.MINUTE));

	if(hour.length() < 2)
	    hour = '0' + hour;
	if(minute.length() < 2)
	    minute = '0' + minute;

	return "(" + hour + ":" + minute + ") " + series.getName() + " [" + channel.getName() + "]";
    }

}
