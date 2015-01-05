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
import java.util.List;

import tv.TVChannel;
import user.*;

public class DataBase {

	private static final String URL = "jdbc:derby:./smartTvViewerDb";

	// create statements
	private static final String SQL_VIEWER_CREATE = "CREATE TABLE Viewer "
			+ "(vname VARCHAR(100), password VARCHAR(100), maxTime BIGINT, parent VARCHAR(100), "
			+ "PRIMARY KEY(vname), FOREIGN KEY (parent) REFERENCES Viewer(vname))";

	private static final String SQL_TIMERESTRICTION_CREATE = "CREATE TABLE TimeRestriction "
			+ "(vname VARCHAR(100), beginTime TIMESTAMP, endTime TIMESTAMP, "
			+ "PRIMARY KEY (vname), FOREIGN KEY (vname) REFERENCES Viewer(vname))";

	private static final String SQL_CHANNELRESTRICTION_CREATE = "CREATE TABLE ChannelRestriction "
			+ "(vname VARCHAR(100), channelName VARCHAR(100), "
			+ "PRIMARY KEY (vname, channelName), FOREIGN KEY (vname) REFERENCES Viewer(vname))";

	// insert statements
	private static final String SQL_VIEWER_INSERT = "INSERT INTO Viewer "
			+ "(vname, password) VALUES (?, ?)";

	private static final String SQL_VIEWER_CHILD_INSERT = "INSERT INTO Viewer "
			+ "(vname, password, parent) VALUES (?, ?, ?)";

	private static final String SQL_VIEWER_LOGIN = "SELECT vname, password, parent FROM Viewer "
			+ "WHERE vname = ? AND password = ?";

	private static final String SQL_VIEWER_PARENTS_SELECT = "SELECT vname, password, parent FROM Viewer "
			+ "WHERE parent IS NULL";

	private static final String SQL_VIEWER_PARENTS_OF_CHILD_SELECT = "SELECT vname, password, parent FROM Viewer "
			+ "WHERE parent = ?";

	private static final String SQL_VIEWER_MAXTIME_SELECT = "SELECT maxTime FROM Viewer "
			+ "WHERE vname = ?";

	private static final String SQL_VIEWER_MAXTIME_UPDATE = "UPDATE Viewer SET maxTime = ? "
			+ "WHERE vname = ? AND parent IS NOT NULL";

	private static final String SQL_TIMERESTRICTION_INSERT = "INSERT INTO TimeRestriction "
			+ "(vname, beginTime, endTime) VALUES (?, ?, ?)";

	private static final String SQL_CHANNELRESTRICTION_INSERT = "INSERT INTO ChannelRestriction "
			+ "(vname, channelName) VALUES (?, ?)";

	private static final String SQL_CHANNELRESTRICTION_SELECT = "SELECT channelName FROM ChannelRestriction "
			+ "WHERE vname = ?";

	private static final String SQL_CHANNELRESTRICTION_DELETE = "DELETE FROM channelRestriction "
			+ "WHERE vname = ?";

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
	private final PreparedStatement viewerSelectChildrenOfParent;
	private final PreparedStatement viewerSelectMaxTime;
	private final PreparedStatement viewerUpdateMaxTime;
	private final PreparedStatement timeRestrictionInsert;
	private final PreparedStatement channelRestrictionInsert;
	private final PreparedStatement channelRestrictionSelect;
	private final PreparedStatement channelRestrictionDelete;

	private DataBase() throws SQLException {
		Connection c;
		try {
			c = DriverManager.getConnection(URL);
		} catch (SQLException e) {
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
		viewerInsertChild = connection
				.prepareStatement(SQL_VIEWER_CHILD_INSERT);
		viewerLogin = connection.prepareStatement(SQL_VIEWER_LOGIN);
		viewerSelectParents = connection
				.prepareStatement(SQL_VIEWER_PARENTS_SELECT);
		viewerSelectChildrenOfParent = connection
				.prepareStatement(SQL_VIEWER_PARENTS_OF_CHILD_SELECT);
		viewerSelectMaxTime = connection
				.prepareStatement(SQL_VIEWER_MAXTIME_SELECT);
		viewerUpdateMaxTime = connection
				.prepareStatement(SQL_VIEWER_MAXTIME_UPDATE);
		timeRestrictionInsert = connection
				.prepareStatement(SQL_TIMERESTRICTION_INSERT);
		channelRestrictionInsert = connection
				.prepareStatement(SQL_CHANNELRESTRICTION_INSERT);
		channelRestrictionSelect = connection
				.prepareStatement(SQL_CHANNELRESTRICTION_SELECT);
		channelRestrictionDelete = connection
				.prepareStatement(SQL_CHANNELRESTRICTION_DELETE);
	}

	public boolean addParent(Parent parent) {
		boolean insertSuccessfull = false;

		synchronized (SQL_VIEWER_INSERT) {
			try {
				viewerInsert.setString(1, parent.getName());
				viewerInsert.setString(2, parent.getPassword());
				int rowsChanged = viewerInsert.executeUpdate();
				if (rowsChanged == 1)
					insertSuccessfull = true;
			} catch (SQLException e) {
				insertSuccessfull = false;
			} finally {
				try {
					viewerInsert.clearParameters();
				} catch (SQLException e) {
				}
			}
		}

		return insertSuccessfull;
	}

	public boolean addChild(Child child) {
		boolean insertSuccessfull = false;

		synchronized (SQL_VIEWER_CHILD_INSERT) {
			try {
				viewerInsertChild.setString(1, child.getName());
				viewerInsertChild.setString(2, child.getPassword());
				viewerInsertChild.setString(3, child.getParent().getName());
				int rowsChanged = viewerInsertChild.executeUpdate();
				if (rowsChanged == 1)
					insertSuccessfull = true;
			} catch (SQLException e) {
				insertSuccessfull = false;
			} finally {
				try {
					viewerInsertChild.clearParameters();
				} catch (SQLException e) {
				}
			}
		}

		return insertSuccessfull;
	}

	public User login(String name, String password) {
		synchronized (viewerLogin) {
			try {
				viewerLogin.setString(1, name);
				viewerLogin.setString(2, password);
				ResultSet result = viewerLogin.executeQuery();

				if (result.next()) {
					String resultName = result.getString("vname");
					String resultPassword = result.getString("password");
					String resultParent = result.getString("parent");

					if (resultParent == null)
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
				} catch (SQLException e) {
				}
			}
		}

		return null;
	}

	public ArrayList<Parent> getParents() {
		ArrayList<Parent> parents = new ArrayList<Parent>();

		synchronized (viewerSelectParents) {
			try {
				ResultSet result = viewerSelectParents.executeQuery();

				while (result.next()) {
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
				} catch (SQLException e) {
				}
			}
		}

		return parents;
	}

	public ArrayList<Child> getChildrenOfParent(Parent parent) {
		ArrayList<Child> children = new ArrayList<Child>();

		synchronized (viewerSelectChildrenOfParent) {
			try {
				viewerSelectChildrenOfParent.setString(1, parent.getName());
				ResultSet result = viewerSelectChildrenOfParent.executeQuery();

				while (result.next()) {
					String name = result.getString("vname");
					String password = result.getString("password");
					Child child = new Child(name, password, parent);
					children.add(child);
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					viewerSelectParents.clearParameters();
				} catch (SQLException e) {
				}
			}
		}

		return children;
	}

	public long getMaxTime(Child child) {
		long maxTime = 0;

		synchronized (viewerSelectMaxTime) {
			try {
				viewerSelectMaxTime.setString(1, child.getName());
				ResultSet result = viewerSelectMaxTime.executeQuery();

				if (result.next())
					maxTime = result.getLong("maxTime");
			} catch (SQLException e) {
			} finally {
				try {
					viewerSelectMaxTime.clearParameters();
				} catch (SQLException e) {
				}
			}
		}

		return maxTime;
	}

	public void setMaxTime(Child child, long maxTime) {
		synchronized (SQL_VIEWER_CHILD_INSERT) {
			try {
				viewerUpdateMaxTime.setLong(1, maxTime);
				viewerUpdateMaxTime.setString(2, child.getName());
				int rowsChanged = viewerUpdateMaxTime.executeUpdate();
				assert rowsChanged == 1;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					viewerUpdateMaxTime.clearParameters();
				} catch (SQLException e) {
				}
			}
		}
	}

	public boolean addTimeRestriction(Date begin, Date end, Child child) {
		boolean insertSuccessfull = false;

		synchronized (SQL_TIMERESTRICTION_INSERT) {
			try {
				timeRestrictionInsert.setString(1, child.getName());
				timeRestrictionInsert.setTimestamp(2,
						new Timestamp(begin.getTime()));
				timeRestrictionInsert.setTimestamp(3,
						new Timestamp(end.getTime()));

				int rowsChanged = timeRestrictionInsert.executeUpdate();
				if (rowsChanged == 1)
					insertSuccessfull = true;
			} catch (SQLException e) {
				insertSuccessfull = false;
			} finally {
				try {
					timeRestrictionInsert.clearParameters();
				} catch (SQLException e) {
				}
			}
		}

		return insertSuccessfull;
	}

	public boolean addChannelRestriction(TVChannel tvChannel, Child child) {
		boolean insertSuccessfull = false;

		synchronized (SQL_CHANNELRESTRICTION_INSERT) {
			try {
				channelRestrictionInsert.setString(1, child.getName());
				channelRestrictionInsert.setString(2, tvChannel.getName());

				int rowsChanged = channelRestrictionInsert.executeUpdate();
				if (rowsChanged == 1)
					insertSuccessfull = true;
			} catch (SQLException e) {
				insertSuccessfull = false;
			} finally {
				try {
					channelRestrictionInsert.clearParameters();
				} catch (SQLException e) {
				}
			}
		}

		return insertSuccessfull;
	}

	public void addChannelRestrictions(List<TVChannel> tvChannels, Child child) {
		deleteChannelRestrictions(child);
		
		for(TVChannel tvChannel : tvChannels) {
			addChannelRestriction(tvChannel, child);
		}
	}

	public void deleteChannelRestrictions(Child child) {
		synchronized (channelRestrictionDelete) {
			try {
				channelRestrictionDelete.setString(1, child.getName());
				channelRestrictionDelete.executeUpdate();
			} catch (SQLException e) {
			} finally {
				try {
					channelRestrictionDelete.clearParameters();
				} catch (SQLException e) {
				}
			}
		}
	}

	public ArrayList<String> getRestrictedChannels(Child child) {
		ArrayList<String> channels = new ArrayList<String>();

		synchronized (channelRestrictionSelect) {
			try {
				channelRestrictionSelect.setString(1, child.getName());
				ResultSet result = channelRestrictionSelect.executeQuery();

				while (result.next()) {
					String channelName = result.getString("channelName");
					channels.add(channelName);
				}
			} catch (SQLException e) {
			} finally {
				try {
					channelRestrictionSelect.clearParameters();
				} catch (SQLException e) {
				}
			}
		}

		return channels;
	}
}
