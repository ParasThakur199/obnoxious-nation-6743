package com.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DButil {
	public static Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/student_management_system";
		try {
			conn = DriverManager.getConnection(url,"root","root");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return conn;
		
	}
}
