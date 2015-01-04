package user;

public class UserManagement {
    public static User user;
    
    public static boolean login(String name, char[] password){
	
	
	return true;
    }
    
    public static boolean createNewUser(String name, char[] password, boolean child, Parent parent){
	//database!!!
	
	User user;
	if(child){
	    user = new Child(name, password.toString(), parent);
	} else{
	    user = new Parent(name, password.toString());
	}	
	return true;
    }
    
    public static boolean isChild(){
	if(user instanceof Child){
	    return true;
	}	
	return false;
    }
}
