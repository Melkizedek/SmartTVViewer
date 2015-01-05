package user;

import java.util.ArrayList;

import tv.TVChannel;
import util.Initializer;
import database.DataBase;

public class UserManagement {
    public static User user;

    public static boolean login(String name, char[] password) {
	DataBase db = DataBase.getInstance();
	User login = db.login(name, String.valueOf(password));

	if(login != null) {
	    user = login;

	    if(user instanceof Parent) {
		ArrayList<Child> children = db
			.getChildrenOfParent((Parent) user);
		for(int i = 0; i < children.size(); i++) {
		    ((Parent) user).addChild(children.get(i));
		}
	    }else{
		user = ChildManagement.getRestrictions((Child)user);		
	    }
	    return true;
	}
	return false;
    }

    public static boolean createNewUser(String name, char[] password,
	    boolean child, Parent parent) {
	User user;

	if(child) {
	    user = new Child(name, String.valueOf(password), parent);
	    DataBase.getInstance().addChild((Child) user);
	}
	else {
	    user = new Parent(name, String.valueOf(password));
	    DataBase.getInstance().addParent((Parent) user);
	}
	return true;
    }

    public static boolean isChild() {
	if(user instanceof Child) {
	    return true;
	}
	return false;
    }
}
