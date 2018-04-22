package javautils.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

public class MySQLConfiguration {

	Connection connect;
	String user;
	String pw;
	String ip;

	public MySQLConfiguration(String user, String pw, String ip, String... database)
			throws SQLException, ClassNotFoundException {
		this.user = user;
		this.pw = pw;
		this.ip = ip;
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(
				"jdbc:mysql://" + ip + "/" + (database.length > 0 && database[0] != null ? database[0] : "") + "?user="
						+ user + "&password=" + pw + "&autoReconnect=true");
		connect.setAutoCommit(true);
	}

	public Connection getConnection() {
		return connect;
	}

	public MySQLConfiguration(String user, String pw) throws ClassNotFoundException, SQLException {
		this(user, pw, "localhost");
	}

	public void close() throws SQLException {
		connect.close();
	}

	@SuppressWarnings("unused")
	private void closeInternal() {
		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MySQLConfiguration createDatabase(String db) {
		try {
			Statement s = connect.createStatement();
			s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + db);
			s.close();
			MySQLConfiguration mysql = new MySQLConfiguration(user, pw, ip);
			mysql.connect = DriverManager
					.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pw);
			mysql.connect.setAutoCommit(true);
			if (!connect.isClosed()) {
				connect.close();
			}
			return mysql;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isClosed() {
		try {
			return connect.isClosed();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public MySQLConfiguration selectDatabase(String db) {
		try {
			MySQLConfiguration mysql = new MySQLConfiguration(user, pw, ip);
			mysql.connect = DriverManager
					.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pw);
			mysql.connect.setAutoCommit(true);
			if (!connect.isClosed()) {
				connect.close();
			}
			return mysql;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean createTable(Table t) {
		try {
			Statement s = connect.createStatement();
			s.executeUpdate(t.getString());
		} catch (Exception e) {
			System.out.println(t.getString());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateEntry(Entry e) {
		try {
			PreparedStatement s = connect.prepareStatement(e.getString());
			int i = 1;
			for (String st : e.getValues()) {
				s.setString(i, st);
				i++;
			}
			for (String st : e.getValues()) {
				s.setString(i, st);
				i++;
			}
			s.executeUpdate();
		} catch (Exception es) {
			es.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param path
	 *            Syntax: TABLE.ID[.SELECT] ||
	 *            TABLE.NAME=VALUE[.NAME=VALUE(.NAME=VALUE)]
	 * @param select
	 *            SELECTOR Syntax: * || name[,id(,bla)]
	 * @return ResultSet
	 */
	public ResultSet get(String table, String select, String... where_) throws SQLException, InterruptedException {
		String where = "";
		ArrayList<String> arguments = new ArrayList<String>();
		for (int i = 0; i < where_.length; i = i + 2) {
			String n = where_[i];
			String v = where_[i + 1];
			if (where.isEmpty()) {
				where = "" + n + "=?";
				arguments.add(v);
			} else {
				where = where + " and " + "" + n + "=?";
				arguments.add(v);
			}
		}

		String se = "SELECT " + select + " FROM " + table + (where.isEmpty() ? "" : " WHERE " + where);
		PreparedStatement ps = connect.prepareStatement(se);
		for (int i = 1; i <= arguments.size(); i++) {
			ps.setString(i, arguments.get(i - 1));
		}
		ResultSet rs = ps.executeQuery();
		rs.first();
		return rs;
	}

	public boolean delete(String table, String... where_) throws SQLException {
		String where = "";
		ArrayList<String> arguments = new ArrayList<String>();
		for (int i = 0; i < where_.length; i = i + 2) {
			String n = where_[i];
			String v = where_[i + 1];
			if (where.isEmpty()) {
				where = "" + n + "=?";
				arguments.add(v);
			} else {
				where = where + " and " + "" + n + "=?";
				arguments.add(v);
			}
		}

		String se = "DELETE FROM " + table + (where.isEmpty() ? "" : " WHERE " + where);
		PreparedStatement ps = connect.prepareStatement(se);
		for (int i = 1; i <= arguments.size(); i++) {
			ps.setString(i, arguments.get(i - 1));
		}
		boolean b = ps.execute();
		return b;
	}

	public boolean update(String table, String[] names, String[] values) throws SQLException {
		String se = "";
		LinkedList<String> arguments = new LinkedList<String>();
		String val = "";
		for(String s : names) {
			se+="," + s;
		}
		se = se.substring(1);
		for(String s : values) {
			val+=",?";
			arguments.add(s);
		}
		val = val.substring(1);

		String sql = "REPLACE into " + table + " (" + se + ") VALUES (" + val + ")";
		System.out.println(sql);
		PreparedStatement ps = connect.prepareStatement(sql);
		for (int i = 1; i <= arguments.size(); i++) {
			ps.setString(i, arguments.get(i - 1));
		}
		boolean b = ps.execute();
		return b;
	}

	public boolean hasTable(String name) {
		try {
			PreparedStatement ps = connect
					.prepareStatement("SELECT * FROM information_schema.tables WHERE table_name = '?' LIMIT 1;");
			ps.setString(1, name);
			return ps.executeQuery().first();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}