package comUtility;

import java.sql.Connection;
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
		
		return null;
		
	}
}
