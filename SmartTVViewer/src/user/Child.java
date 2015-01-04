package user;

public class Child extends User {
    private Parent parent;

    // restrictions...

    public Child(String name, String password, Parent parent) {
	super(name, password);
	this.parent = parent;
    }

    public Parent getParent() {
	return parent;
    }

    public void setParent(Parent parent) {
	this.parent = parent;
    }

}
