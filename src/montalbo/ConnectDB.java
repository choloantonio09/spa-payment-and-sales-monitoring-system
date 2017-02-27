package montalbo;

import java.sql.*;

public class ConnectDB {
	
	public String user = "root";
	public String pass = "1234";
	public String url = "jdbc:mysql://localhost:3306/montalbo";
	public Connection con;
	public ResultSet rs;
	public Statement smt;
	public PreparedStatement pst;
	
	public ConnectDB() throws SQLException
	{
		con = DriverManager.getConnection(url,user,pass);
	}

}
