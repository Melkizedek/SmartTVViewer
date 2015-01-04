package user;

import java.util.ArrayList;

public class Parent extends User {
    private ArrayList<Child> children;

    public Parent(String name, String password) {
	super(name, password);
	children = new ArrayList<Child>();
    }

    public boolean addChild(Child child) {
	children.add(child);

	return true;
    }

    public ArrayList<Child> getChildren() {
	return children;
    }
}
