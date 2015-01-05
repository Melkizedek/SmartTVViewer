package test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tv.TVChannel;
import user.*;
import database.DataBase;

public class DatabaseTest {

	private DataBase db;
	private Parent parent, parent2, parent3;
	private Child child, child2, child3;
	private TVChannel channel1, channel2, channel3, channel4;
	
	@Before
	public void setUp() throws Exception {
		db = DataBase.getInstance();
		
		parent = new Parent("Parent", "pw");
		parent2 = new Parent("Parent2", "asdpjfsdfj");
		parent3 = new Parent("Parent3", "cool");
		child = new Child("Child", "pw", parent);
		child2 = new Child("Child2", "cool^2", parent3);
		child3 = new Child("Child3", "adlj", parent2);
		channel1 = new TVChannel("channel 1", null);
		channel2 = new TVChannel("channel 2", null);
		channel3 = new TVChannel("channel 3", null);
		channel4 = new TVChannel("channel 4", null);
		
		//InsertTestData();
	}

	public void InsertTestData() {
		db.addParent(parent);
		db.addParent(parent2);
		db.addParent(parent3);
		db.addChild(child);
		db.addChild(child2);
		db.addChild(child3);
		
		db.addChannelRestriction(channel1, child2);
		db.addChannelRestriction(channel2, child2);
		db.addChannelRestriction(channel3, child2);
		db.addChannelRestriction(channel4, child3);
	}

	@Test
	public void testLogin() {
		System.out.println("\n Login Test");
		db.login("wrong", "password");
		User child = db.login("Child", "pw");
		Assert.assertTrue(child instanceof Child);
		Assert.assertFalse(child instanceof Parent);
		
		User parent = db.login("Parent", "pw");
		Assert.assertFalse(parent instanceof Child);
		Assert.assertTrue(parent instanceof Parent);
		
		Child childUser = (Child) child;
		Parent parentUser = (Parent) parent;
		
		System.out.println("Name: " + childUser.getName() + " Password: "+childUser.getPassword() + "Parent: "+childUser.getParent().getName());
		System.out.println("Name: " + parentUser.getName() + "Password: "+parentUser.getPassword());
	}
	
	@Test
	public void testGetParents() {
		System.out.println("\n GetParents Test");
		
		ArrayList<Parent> parents = db.getParents();
		
		for(Parent p: parents) {
			System.out.println("Name: " + p.getName() + " Password: "+p.getPassword());
		}
	}
	
	@Test
	public void testMaxTime() {
		long expected = 3500;
		db.setMaxTime(child, expected);
		Assert.assertEquals(expected, db.getMaxTime(child));
		Assert.assertEquals(0, db.getMaxTime(child2));
		Assert.assertEquals(0, db.getActualTime(child));
	}
	
	@Test
	public void testRestrictedChannel() {
		
		System.out.println("\n Restricted Channels Test");
		System.out.println("Child: ");
		for(String channelName : db.getRestrictedChannels(child)) {
			System.out.println(channelName + " ");
		}
		
		System.out.println("Child2: ");
		for(String channelName : db.getRestrictedChannels(child2)) {
			System.out.println(channelName + " ");
		}
		
		System.out.println("Child3: ");
		for(String channelName : db.getRestrictedChannels(child3)) {
			System.out.println(channelName + " ");
		}
		
		testMultipleChannelRestrictions();
	}
	
	public void testMultipleChannelRestrictions() {
		List<TVChannel> tvChannels = new ArrayList<TVChannel>();
		tvChannels.add(channel3);
		tvChannels.add(channel4);
		
		db.addChannelRestrictions(tvChannels, child);
		db.addChannelRestrictions(tvChannels, child2);
		tvChannels.add(channel1);
		db.addChannelRestrictions(tvChannels, child3);
		
		System.out.println("\n Multiple Restricted Channels Test");
		System.out.println("Child: ");
		for(String channelName : db.getRestrictedChannels(child)) {
			System.out.println(channelName + " ");
		}
		
		System.out.println("Child2: ");
		for(String channelName : db.getRestrictedChannels(child2)) {
			System.out.println(channelName + " ");
		}
		
		System.out.println("Child3: ");
		for(String channelName : db.getRestrictedChannels(child3)) {
			System.out.println(channelName + " ");
		}
	}
	
	@Test
	public void testTimeRestriction() {
		Calendar begin = new GregorianCalendar();
		begin.clear();
		begin.set(Calendar.HOUR_OF_DAY, 1);
	    begin.set(Calendar.MINUTE, 30);
	    
	    Calendar end = new GregorianCalendar();
	    end.set(Calendar.HOUR_OF_DAY, 4);
	    end.set(Calendar.MINUTE, 45);
	    
	    db.addTimeRestriction(begin, end, child2);
	    
	    Calendar actualBegin = db.getTimeRestrictionBegin(child2);
	    Calendar actualEnd = db.getTimeRestrictionEnd(child2);
	    
	    Assert.assertEquals(begin.get(Calendar.HOUR_OF_DAY), actualBegin.get(Calendar.HOUR_OF_DAY));
	    Assert.assertEquals(begin.get(Calendar.MINUTE), actualBegin.get(Calendar.MINUTE));
	    Assert.assertEquals(end.get(Calendar.HOUR_OF_DAY), actualEnd.get(Calendar.HOUR_OF_DAY));
	    Assert.assertEquals(end.get(Calendar.MINUTE), actualEnd.get(Calendar.MINUTE));
	    
	    Assert.assertEquals(null, db.getTimeRestrictionBegin(child3));
	    Assert.assertEquals(null, db.getTimeRestrictionEnd(child3));
	    
	    begin.clear();
		begin.set(Calendar.HOUR_OF_DAY, 17);
	    begin.set(Calendar.MINUTE, 00);
	    end.set(Calendar.HOUR_OF_DAY, 23);
	    end.set(Calendar.MINUTE, 55);
	    
	    db.addTimeRestriction(begin, end, child);
	    db.addTimeRestriction(begin, end, child2);
	    
	    actualBegin = db.getTimeRestrictionBegin(child);
	    actualEnd = db.getTimeRestrictionEnd(child);
	    Assert.assertEquals(begin.get(Calendar.HOUR_OF_DAY), actualBegin.get(Calendar.HOUR_OF_DAY));
	    Assert.assertEquals(begin.get(Calendar.MINUTE), actualBegin.get(Calendar.MINUTE));
	    Assert.assertEquals(end.get(Calendar.HOUR_OF_DAY), actualEnd.get(Calendar.HOUR_OF_DAY));
	    Assert.assertEquals(end.get(Calendar.MINUTE), actualEnd.get(Calendar.MINUTE));
	    
	    actualBegin = db.getTimeRestrictionBegin(child2);
	    actualEnd = db.getTimeRestrictionEnd(child2);
	    Assert.assertEquals(begin.get(Calendar.HOUR_OF_DAY), actualBegin.get(Calendar.HOUR_OF_DAY));
	    Assert.assertEquals(begin.get(Calendar.MINUTE), actualBegin.get(Calendar.MINUTE));
	    Assert.assertEquals(end.get(Calendar.HOUR_OF_DAY), actualEnd.get(Calendar.HOUR_OF_DAY));
	    Assert.assertEquals(end.get(Calendar.MINUTE), actualEnd.get(Calendar.MINUTE));
	    
	    Assert.assertEquals(null, db.getTimeRestrictionBegin(child3));
	    Assert.assertEquals(null, db.getTimeRestrictionEnd(child3));
	}
	
	@Test
	public void testActualTime() {
		long expected = 1000;
		Calendar now = Calendar.getInstance();
		db.setActualTimeDay(expected, now, child3);
		Assert.assertEquals(expected, db.getActualTime(child3));
		Assert.assertEquals(now.get(Calendar.DAY_OF_MONTH), db.getDay(child3).get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(now.get(Calendar.MONTH), db.getDay(child3).get(Calendar.MONTH));
	}
	
	@Test
	public void testDate() {
		System.out.println("\n test with calendar");
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 4);
		calendar.set(Calendar.MINUTE, 30);
		System.out.println("Hours: " + calendar.get(Calendar.HOUR_OF_DAY) + " Minutes: " + calendar.get(Calendar.MINUTE));
	}
}
