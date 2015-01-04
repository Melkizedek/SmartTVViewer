package user;

public class UserManagement {
    //public static User user;
    public static String user;
    
    public static boolean login(String name, char[] password){
	if(name.equals("test") && password[0] == '1' && password[1] == '2' && password[2] == '3'){
	    return true;
	}
	
	return false;
    }
    
    //String parent --> User parent!!!!!!!!
    public static boolean createNewUser(String name, char[] password, boolean child, String parent){
	if(name.equals("test")){
	    return false;
	}
	
	return true;
    }
    
    public static boolean isChild(){
	
	
	return false;
    }
    
//    public static User getChildren(){
//	
//    }
//    
//    public static User getParents(){
//	
//    }
}
