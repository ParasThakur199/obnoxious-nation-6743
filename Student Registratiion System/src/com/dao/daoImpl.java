package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Model.Student;
import com.Utility.DButil;
import com.exceptions.CourseException;
import com.exceptions.StudentException;

public class daoImpl implements dao{

	@Override
	public String registerStudent(Student student) throws StudentException, SQLException {
		
		String answer = "Student Already Exists !!";
		
		String sEmail = student.getsEmail();
		String sPassword = student.getsPassword();
		String sName = student.getsName();
		
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("Insert into students values(?,?,?)");
			ps.setString(1,sEmail);
			ps.setString(2,sPassword);
			ps.setString(3, sName);
			
			int x = ps.executeUpdate();
			answer = x + "Student Statements Inserted";

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return answer;
	}

//	@Override
//	public boolean registerBatch(int cId, String cName, String sEmail) throws Exception {
//		boolean answer = false;
//		int lbatchId;
//		int ltotalSeats;
//		int lseatsFilled;
//		int fbatchId;
//		
//		dao daoObj = new daoImpl();
//		Connection conn = DButil.getConnection();
//		try {
//		boolean flag = false;
//		boolean used = false;
//		PreparedStatement ps = conn.prepareStatement("Select cId,cName,stdEmail from batchSeats where cId= ?");
//		ps.setInt(1, cId);
//		ResultSet rs = ps.executeQuery();
//		
//		while(rs.next()) {
//			lbatchId = rs.getInt("bId");
//			ltotalSeats = rs.getInt("totalSeats");
//			lseatsFilled = rs.getInt("seatsFilled");
//			
//			if(lseatsFilled < ltotalSeats && used == false) {
//				used = true;
//				flag = true;
//				answer = true;
//				fbatchId = lbatchId;
//				
//				PreparedStatement ps3 = conn.prepareStatement("update batchSeats set seatsFilled = (seatsFilled + 1)where cId=? AND bId = ?");
//				ps3.setInt(1,cId);
//				ps3.setInt(2,lbatchId);
//				int x2 = ps3.executeUpdate();
//				
//				PreparedStatement ps2 = conn.prepareStatement("Insert into batch(batchNo, cId, StdEmail) values(?,?,?)");
//				ps2.setInt(1,fbatchId);
//				ps2.setInt(2,cId);
//				ps2.setString(3,stdEmail);
//				
//				int x = ps2.executeUpdate();
//				System.out.println("Student"+daoObj.getStdNameFromEmail(stdEmail)+"with e-mail"+stdEmail+"registered into course"+cName);
//			}
//		}
//		if(!flag) {
//			throw new CourseException("Seats not available for course"+cName+"batch not allocated for the course yet");
//		}
//		} catch (SQLException e) {
//			throw new SQLException(e.getMessage());
//		}
//		catch(Exception e) {
//			throw new Exception(e.getMessage());
//		}
//		return answer;
//	}
	
	

}
