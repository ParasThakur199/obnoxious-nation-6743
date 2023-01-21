package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Model.BatchRecordDTO;
import com.Model.Course;
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

	@Override
	public boolean registerBatch(int cId, String cName, String sEmail) throws Exception {
		boolean answer = false;
		int lbatchId;
		int ltotalSeats;
		int lseatsFilled;
		int fbatchId;
		
		dao daoObj = new daoImpl();
		
		try(Connection conn = DButil.getConnection()) {
		boolean flag = false;
		boolean used = false;
		PreparedStatement ps = conn.prepareStatement("Select bId,totalSeats,SeatsFilled from batchSeats where cId= ?");
		ps.setInt(1, cId);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			lbatchId = rs.getInt("bId");
			ltotalSeats = rs.getInt("totalSeats");
			lseatsFilled = rs.getInt("seatsFilled");
			
			if(lseatsFilled < ltotalSeats && used == false) {
				used = true;
				flag = true;
				answer = true;
				fbatchId = lbatchId;
				
				PreparedStatement ps3 = conn.prepareStatement("update batchSeats set seatsFilled = (seatsFilled + 1)where cId=? AND bId = ?");
				ps3.setInt(1,cId);
				ps3.setInt(2,lbatchId);
				int x2 = ps3.executeUpdate();
				
				PreparedStatement ps2 = conn.prepareStatement("Insert into batch(batchNo, cId, StdEmail) values(?,?,?)");
				ps2.setInt(1,fbatchId);
				ps2.setInt(2,cId);
				ps2.setString(3,sEmail);
				
				int x = ps2.executeUpdate();
				System.out.println("Student"+daoObj.getSNameFromEmail(sEmail)+"with e-mail"+sEmail+"registered into course"+cName);
			}
		}
		if(!flag) {
			throw new CourseException("Seats not available for course"+cName+"batch not allocated for the course yet");
		}
		} 
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return answer;
	}

	@Override
	public boolean checkForStudent(String sEmail, String sPassword) throws StudentException {
		boolean answer = false;
		
		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("Select * from Students where sEmail=? AND sPassword=?");
			ps.setString(1,sEmail);
			ps.setString(2, sPassword);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				answer=true;
			}
		} catch (SQLException e) {
			throw new StudentException(e.getMessage());
		}
		
		return answer;
	}

	@Override
	public List<Course> getListOfCourses() throws SQLException, CourseException {
		List<Course> listOfCourses = new ArrayList<>();
		boolean isEmpty = true;
		
		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("Select * from Courses");
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				isEmpty = false;
				int cId = rs.getInt("cId");
				String cName = rs.getString("cName");
				int fees = rs.getInt("fees");
				String cInfo = rs.getString("cInfo");
				
				Course c = new Course(cId,cName,fees,cInfo);
				listOfCourses.add(c);
			}
		}
		catch(SQLException e) {
			throw new SQLException(e.getMessage());
		}
		if(isEmpty)throw new CourseException("No Course Present");
		return listOfCourses;
	}

	@Override
	public boolean checkForCourse(String cName) throws SQLException, CourseException {
		boolean answer = false;
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("select cName from courses where cname=?");
			ps.setString(1,cName);
			
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
		return answer;
	}

	@Override
	public int getCourseId(String cName) throws SQLException, CourseException {
		int answer = 0;
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps= conn.prepareStatement("select cId from courses where cName=?");
			ps.setString(1,cName);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				answer = rs.getInt("cId");
			}else {
				throw new CourseException("There is no "+cName+"course. Please Enter proper course name.");
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return answer;
	}

	@Override
	public BatchRecordDTO getBatchRecordForStudent(String sEmail) {
		BatchRecordDTO brdto = null;
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("Select * from batch where sEmail=?");
			ps.setString(1,sEmail);
		
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int batchId = rs.getInt("batchId");
				int batchNo = rs.getInt("batchNo");
				int cId = rs.getInt("cId");
				String sEmailr = rs.getString("sEmail");
				
				brdto = new BatchRecordDTO(batchId,batchNo,cId,sEmailr);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return brdto;
	}

	@Override
	public boolean studentDetailEdit(String sEmail, String sPassword, String sNewPassword, String sNewName,String sNewCourseName) throws SQLException, CourseException {
		boolean answer = false;
		
		dao daoObj = new daoImpl();
		BatchRecordDTO br = null;
		
		Connection conn = DButil.getConnection();
		int newCId = daoObj.getCourseId(sNewCourseName);
		try {
			daoObj.registerBatch(newCId, sNewCourseName, sEmail);
			answer = true;
			
			if(daoObj.getBatchRecordForStudent(sEmail)!=null) {
				br = daoObj.getBatchRecordForStudent(sEmail);
				int batchId = br.getBatchid();
				int batchNo = br.getBatchNo();
				
				if((Integer)br.getBatchNo() != null) {
					PreparedStatement ps = conn.prepareStatement("DELETE from batch where batchId=?");
					ps.setInt(1,batchId);
					
					int x = ps.executeUpdate();
					
					PreparedStatement ps2 = conn.prepareStatement("update batchSeats set seatsFilled = (seatsFilled-1) where bId = ?");
					ps2.setInt(1, batchNo);
					ps2.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.out.println("Check");
			throw new SQLException(e.getMessage());
		} catch (CourseException e) {
			System.out.println("Check");
			throw new CourseException(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return answer;
	}
	
	
	

}
