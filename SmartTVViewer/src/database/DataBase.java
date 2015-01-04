package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import user.*;

public class DataBase {
	
	private static final String URL = "jdbc:derby:./smartTvViewerDb";
	
	//  create statements
	private static final String SQL_VIEWER_CREATE = "CREATE TABLE Viewer "
			+ "(vname VARCHAR(100), password VARCHAR(100), maxTime TIMESTAMP, parent VARCHAR(100), "
			+ "PRIMARY KEY(vname), FOREIGN KEY (parent) REFERENCES Viewer(vname))";
	
	private static final String SQL_TIMERESTRICTION_CREATE = "CREATE TABLE TimeRestriction "
			+ "(vname VARCHAR(100), beginTime TIMESTAMP, endTime TIMESTAMP, "
			+ "PRIMARY KEY (vname), FOREIGN KEY (vname) REFERENCES Viewer(vname))";
	
	private static final String SQL_CHANNELRESTRICTION_CREATE = "CREATE TABLE ChannelRestriction "
			+ "(vname VARCHAR(100), channelName VARCHAR(100), "
			+ "PRIMARY KEY (vname), FOREIGN KEY (vname) REFERENCES Viewer(vname))";
	
	// insert statements
	private static final String SQL_VIEWER_INSERT = "INSERT INTO Viewer "
			+ "(vname, password) VALUES (?, ?)";
	
	private static final String SQL_VIEWER_CHILD_INSERT = "INSERT INTO Viewer "
			+ "(vname, password, parent) VALUES (?, ?, ?)";
	
	private static final String SQL_VIEWER_LOGIN = "SELECT vname, password, parent FROM Viewer "
			+ "WHERE vname = ? AND password = ?";
	
	private static final String SQL_VIEWER_PARENTS_SELECT = "SELECT vname, password, parent FROM Viewer "
			+ "WHERE parent IS NULL";
			
	private static final String SQL_VIEWER_MAXTIME_UPDATE = "UPDATE Viewer SET maxTime = ? "
			+ "WHERE vname = ? AND parent IS NOT NULL";
	
	
	
	private static final DataBase instance;
	
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			instance = new DataBase();
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	public static DataBase getInstance() {
		return instance;
	}
	
	private final Connection connection;
	
	private final PreparedStatement viewerInsert;
	private final PreparedStatement viewerInsertChild;
	private final PreparedStatement viewerLogin;
	private final PreparedStatement viewerSelectParents;
	private final PreparedStatement viewerUpdateMaxTime;
	
	private DataBase() throws SQLException {
		Connection c;
		try {
			c = DriverManager.getConnection(URL);
		} catch(SQLException e) {
			c = DriverManager.getConnection(URL + ";create=true");
			
			// creating tables
			Statement statement = c.createStatement();
			statement.executeUpdate(SQL_VIEWER_CREATE);
			statement.executeUpdate(SQL_TIMERESTRICTION_CREATE);
			statement.executeUpdate(SQL_CHANNELRESTRICTION_CREATE);
		}
		
		connection = c;
		
		// prepare Statements
		viewerInsert = connection.prepareStatement(SQL_VIEWER_INSERT);
		viewerInsertChild = connection.prepareStatement(SQL_VIEWER_CHILD_INSERT);
		viewerLogin = connection.prepareStatement(SQL_VIEWER_LOGIN);
		viewerSelectParents = connection.prepareStatement(SQL_VIEWER_PARENTS_SELECT);
		viewerUpdateMaxTime = connection.prepareStatement(SQL_VIEWER_MAXTIME_UPDATE);
	}
	
	public void addParent(Parent parent) {
		synchronized(SQL_VIEWER_INSERT) {
			try {
				viewerInsert.setString(1, parent.getName());
				viewerInsert.setString(2, parent.getPassword());
				int rowsChanged = viewerInsert.executeUpdate();
				assert rowsChanged == 1;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					viewerInsert.clearParameters();
				} catch (SQLException e) {}
			}
		}
	}
	
		
	public void addChild(Child child) {
		synchronized(SQL_VIEWER_CHILD_INSERT) {
			try {
				viewerInsertChild.setString(1, child.getName());
				viewerInsertChild.setString(2, child.getPassword());
				viewerInsertChild.setString(3, child.getParent().getName());
				int rowsChanged = viewerInsertChild.executeUpdate();
				assert rowsChanged == 1;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					viewerInsertChild.clearParameters();
				} catch (SQLException e) {}
			}
		}
	}
	
	public User login(String name, String password) {
		synchronized(viewerLogin) {
			try {
				viewerLogin.setString(1, name);
				viewerLogin.setString(2, password);
				ResultSet result = viewerLogin.executeQuery();
				
				if(result.next() == true) {
					String resultName = result.getString("vname");
					String resultPassword = result.getString("password");
					String resultParent = result.getString("parent");
					
					if(resultParent == null)
						return new Parent(resultName, resultPassword);
					else {
						Parent parent = new Parent(resultParent, "");
						return new Child(resultName, resultPassword, parent);
					}
				}
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					viewerLogin.clearParameters();
				} catch (SQLException e) {}
			}
		}
		
		return null;
	}
	
	public ArrayList<Parent> getParents() {
		ArrayList<Parent> parents = new ArrayList<Parent>();
		
		synchronized(viewerSelectParents) {
			try {
				ResultSet result = viewerSelectParents.executeQuery();
				
				while(result.next()) {
					String name = result.getString("vname");
					String password = result.getString("password");
					Parent parent = new Parent(name, password);
					parents.add(parent);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					viewerSelectParents.clearParameters();
				} catch (SQLException e) {}
			}
		}
		
		return parents;
	}
	
	public void setMaxTime(Child child, Date maxTime) {
		synchronized(SQL_VIEWER_CHILD_INSERT) {
			try {
				Timestamp maxTimeTimestamp = new Timestamp(maxTime.getTime());
				viewerUpdateMaxTime.setTimestamp(1, maxTimeTimestamp);
				viewerUpdateMaxTime.setString(2, child.getName());
				int rowsChanged = viewerUpdateMaxTime.executeUpdate();
				assert rowsChanged == 1;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					viewerUpdateMaxTime.clearParameters();
				} catch (SQLException e) {}
			}
		}
	}
}
