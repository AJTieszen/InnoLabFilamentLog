package Main;

import java.io.File;
import java.sql.*;

import javax.swing.JOptionPane;

import net.ucanaccess.jdbc.UcanaccessSQLException;

public class Database {
	private static Connection db_connection = null;
	
	public static void setup() {
		try {
//			Create database connection
	    	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	
//	    	Create directory if not present
	    	String path = Settings.getDirectory();
	    	File dir = new File(path);
	    	if (dir.exists() && !dir.isDirectory()) {
	    		dir.delete();
	    		dir.mkdir();
	    	}
	    	if (!dir.exists()) {
	    		dir.mkdir();
	    	}
	    	
//	    	Open database file
	    	db_connection = DriverManager.getConnection(connPath + ";newdatabaseversion=V2010");
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	Create tables
	    	String sqls[] = {
	    			"Create Table Budgets( id text(10) PRIMARY KEY, name text(60), usage long, brought long, remaining long ) ",
	    			"Create Table Projects( ticket text(15) PRIMARY KEY, date text(10), netid text(10) REFERENCES Budgets(id) , name text(60), project text(20), usage long, material text(5) )"
	    			};
	    	
	    	for (String sql : sqls)
	    	try {
	    		stmt.executeUpdate(sql);
	    	}
	    	catch (UcanaccessSQLException e) {
	    		if (e.getMessage().indexOf("bject name already exists:") > 0) {
	    			System.out.println("INFO: Table already exists.");
	    		}
	    		else {
	    			e.printStackTrace();
	    		}
	    	}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	public static void logPrint(String date, String netid, String name, String project, String ticket, int usage, String material) {
    	try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if ticket is already in database
	    	String sql = "select * from Projects where ticket = '" + ticket + "'";
	    	ResultSet result = stmt.executeQuery(sql);
	    	boolean ticketExists = result.next();
	    	
	    	if (!ticketExists) {
//		    	Write to database
		    	sql = "insert into Projects values('" + ticket + "', '" + date + "', '" + netid + "', '" + name + "', '" + project + "', " + usage + ", '" + material + "')";
		    	stmt.executeUpdate(sql);
		    	
		    	// Display values in program
		    	int row = Main.projectTable.getRowCount();
		    	Main.projectTable.setValueAt(date, row, 0);
		    	Main.projectTable.setValueAt(netid, row, 1);
		    	Main.projectTable.setValueAt(name, row, 2);
		    	Main.projectTable.setValueAt(project, row, 3);
		    	Main.projectTable.setValueAt(ticket, row, 4);
		    	Main.projectTable.setValueAt(usage, row, 5);
		    	Main.projectTable.setValueAt(material, row, 6);
	    	}
	    	else {
	    		JOptionPane.showMessageDialog(null, "Ticket #" + ticket + " already exists.");
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	public static void logUser(String netid, String name, int limit) {
//		Calculate fields
		int usage = 0;
		int brought = 0;
		int remaining = limit;
		
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if user is already in database
	    	String sql = "select * from Budgets where id = '" + netid + "'";
	    	ResultSet result = stmt.executeQuery(sql);
	    	boolean userExists = result.next();
	    	
	    	if(!userExists) {
//	    		Write to database
	    		sql = "insert into Budgets values('" + netid + "', '" + name + "', " + usage + ", " + brought + ", " + remaining + ")";
	    		stmt.executeUpdate(sql);
	    		
//	    		Display values in program
	    		int row = Main.budgetTable.getRowCount();
	    		Main.budgetTable.setValueAt(netid, row, 0);
	    		Main.budgetTable.setValueAt(name, row, 1);
	    		Main.budgetTable.setValueAt(usage, row, 2);
	    		Main.budgetTable.setValueAt(brought, row, 3);
	    		Main.budgetTable.setValueAt(remaining, row, 4);
	    	}
	    	else {
	    		JOptionPane.showMessageDialog(null, "User " + name + " already exists.");	    		
	    	}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
	}
	public static void updateUser(String netid, int deltaUsage, int deltaBrought) {		
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if user is already in database
	    	String sql = "select * from Budgets where id = '" + netid + "'";
	    	ResultSet result = stmt.executeQuery(sql);
	    	boolean userExists = result.next();
	    	
	    	if(userExists) {
//	    		Get current values
	    		int usage = (int) result.getObject("usage");
	    		int brought = (int) result.getObject("brought");
	    		int remaining = (int) result.getObject("remaining");
	    		
//	    		Calculate new values
	    		usage += deltaUsage;
	    		brought += deltaBrought;
	    		remaining += deltaBrought - deltaUsage;
	    		
	    		sql = "update Budgets set usage = " + usage + ", brought = " + brought + ", remaining = " + remaining + " where id = '" + netid + "'";
	    		stmt.executeUpdate(sql);
	    		refresh();
	    	}
	    	else {
	    		JOptionPane.showMessageDialog(null, "User " + netid + " not found.");	    		
	    	}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
	}
	public static void modifyUser(String netid, String name, int usage, int brought, int remaining) {		
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if user is already in database
	    	String sql = "select * from Budgets where id = '" + netid + "'";
	    	ResultSet result = stmt.executeQuery(sql);
	    	boolean userExists = result.next();
	    	
	    	if(userExists) {
	    		sql = "update Budgets set name = '" + name + "', usage = " + usage + ", brought = " + brought + ", remaining = " + remaining + " where id = '" + netid + "'";
	    		stmt.executeUpdate(sql);
	    		refresh();
	    	}
	    	else {
	    		JOptionPane.showMessageDialog(null, "User " + netid + " not found.");	    		
	    	}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
	}
	public static void modifyPrint(String ticket, String date, String netid, String name, String material, int amount) {		
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if user is already in database
	    	String sql = "select * from Budgets where id = '" + netid + "'";
	    	ResultSet result = stmt.executeQuery(sql);
	    	boolean userExists = result.next();
	    	
	    	if(userExists) {
	    		sql = "update Projects set date = '" + date + "', netid = '" + netid + "', name = '" + name + "', material = '" + material + "', usage = " + amount + " where ticket = '" + ticket + "'";
	    		stmt.executeUpdate(sql);
	    		refresh();
	    	}
	    	else {
	    		JOptionPane.showMessageDialog(null, "User " + netid + " not found.");	    		
	    	}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
	}
	
	public static boolean checkUserExists(String netid) {
		boolean userExists = false;
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if user is already in database
	    	String sql = "select * from Budgets where id = '" + netid + "'";
	    	ResultSet result = stmt.executeQuery(sql);
	    	userExists = result.next();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
    	return userExists;
	}
	
	public static void refresh() {
		Main.statMessage.setText("Refreshing Database");
		Main.projectTable.setModel(new ProjectTableModel());
		Main.budgetTable.setModel(new BudgetTableModel());
		Database.readProjects();
		Database.readBudgets();
		
		if (!Main.showNetID) {
			Main.showHideNetID();
			Main.showHideNetID();
		}
		Main.statMessage.setText("OK");
	}
	
	public static void readProjects() {
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if ticket is already in database
	    	String sql = "select * from Projects order by date desc";
	    	ResultSet result = stmt.executeQuery(sql);
	    	
	    	while(result.next()) {
	    		int row = Main.projectTable.getRowCount();
	    		Main.projectTable.setValueAt(result.getObject("date"), row, 0);
	    		Main.projectTable.setValueAt(result.getObject("netid"), row, 1);
	    		Main.projectTable.setValueAt(result.getObject("name"), row, 2);
	    		Main.projectTable.setValueAt(result.getObject("project"), row, 3);
	    		Main.projectTable.setValueAt(result.getObject("ticket"), row, 4);
	    		Main.projectTable.setValueAt(result.getObject("usage"), row, 5);
	    		Main.projectTable.setValueAt(result.getObject("material"), row, 6);
	    	}
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	public static void readBudgets() {
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if ticket is already in database
	    	String sql = "select * from Budgets order by remaining asc";
	    	ResultSet result = stmt.executeQuery(sql);
	    	
	    	while(result.next()) {
	    		int row = Main.budgetTable.getRowCount();
	    		Main.budgetTable.setValueAt(result.getObject("id"), row, 0);
	    		Main.budgetTable.setValueAt(result.getObject("name"), row, 1);
	    		Main.budgetTable.setValueAt(result.getObject("usage"), row, 2);
	    		Main.budgetTable.setValueAt(result.getObject("brought"), row, 3);
	    		Main.budgetTable.setValueAt(result.getObject("remaining"), row, 4);
	    	}
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	public static String getUserName(String netid) {
		String userName = "";
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if user is already in database
	    	String sql = "select * from Budgets where id = '" + netid + "'";
	    	ResultSet result = stmt.executeQuery(sql);
	    	result.next();
	    	userName = result.getObject("name").toString();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
		return userName;
	}
	public static ResultSet search(String target, String column, String table) {
		ResultSet result = null;
		try {
//    		Create database connection
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	String connPath = "jdbc:ucanaccess://" + Settings.getFilePath() + ";singleconnection=true";
	    	db_connection = DriverManager.getConnection(connPath);
	    	Statement stmt = db_connection.createStatement();
	    	
//	    	check if user is already in database
	    	String sql = "select * from " + table + " where " + column + " = '" + target + "'";
	    	result = stmt.executeQuery(sql);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
	    	try {
				if (db_connection != null) db_connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}				
		}
		
		return result;
	}
}

