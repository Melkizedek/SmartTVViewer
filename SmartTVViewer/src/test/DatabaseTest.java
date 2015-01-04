package test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
	
	@Before
	public void setUp() throws Exception {
		db = DataBase.getInstance();
		
		parent = new Parent("Parent", "pw");
		parent2 = new Parent("Parent2", "asdpjfsdfj");
		parent3 = new Parent("Parent3", "cool");
		child = new Child("Child", "pw", parent);
		child2 = new Child("Child2", "cool^2", parent3);
		child3 = new Child("Child3", "adlj", parent2);
		
		InsertTestData();
	}

	public void InsertTestData() {
		db.addParent(parent);
		db.addParent(parent2);
		db.addParent(parent3);
		db.addChild(child);
		db.addChild(child2);
		db.addChild(child3);
		
		db.addChannelRestriction(new TVChannel("channel 1", null), child2);
		db.addChannelRestriction(new TVChannel("channel 2", null), child2);
		db.addChannelRestriction(new TVChannel("channel 3", null), child2);
		db.addChannelRestriction(new TVChannel("channel 4", null), child3);
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
	public void maxTime() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 3);
		calendar.set(Calendar.MINUTE, 45);
		
		db.setMaxTime(child, calendar.getTime());
		
		calendar.clear();
		calendar = db.getMaxTime(child);
		Assert.assertEquals(3, calendar.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(45, calendar.get(Calendar.MINUTE));
		
		calendar.clear();
		calendar = db.getMaxTime(child2);
		Assert.assertEquals(null, calendar);
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
	}
	
	@Test
	public void testDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 4);
		calendar.set(Calendar.MINUTE, 30);
		System.out.println("Hours: " + calendar.get(Calendar.HOUR_OF_DAY) + " Minutes: " + calendar.get(Calendar.MINUTE));
	}
}
