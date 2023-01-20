package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.Model.Student;
import com.Utility.DButil;

public class daoImpl implements dao{

	@Override
	public String registerStudent(Student student) throws SQLException {
		
		String answer = "Student Already Exists !!";
		
		String StdEmail = student.getStdEmail();
		String StdPassword = student.getStdPassword();
		String StdName = student.getStdName();
		
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("Insert into students values(?,?,?)");
			ps.setString(1,StdEmail);
			ps.setString(2,StdPassword);
			ps.setString(3, StdName);
			
			int x = ps.executeUpdate();
			answer = x + "Student Statements Inserted";

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return answer;
	}

}
