package test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import user.*;
import database.DataBase;

public class DatabaseTest {

	private DataBase db;
	
	@Before
	public void setUp() throws Exception {
		db = DataBase.getInstance();
		//InsertTestData();
	}

	public void InsertTestData() {
		Parent parent = new Parent("Parent", "pw");
		db.addParent(parent);
		Parent parent2 = new Parent("Parent2", "asdpjfsdfj");
		db.addParent(parent2);
		Parent parent3 = new Parent("Parent3", "cool");
		db.addParent(parent3);
		Child child = new Child("Child", "pw", parent);
		db.addChild(child);
		Child child2 = new Child("Child2", "cool^2", parent3);
		db.addChild(child2);
		Child child3 = new Child("Child3", "adlj", parent2);
		db.addChild(child3);
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
	public void testDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 4);
		calendar.set(Calendar.MINUTE, 30);
		System.out.println("Hours: " + calendar.get(Calendar.HOUR_OF_DAY) + " Minutes: " + calendar.get(Calendar.MINUTE));
	}
}
