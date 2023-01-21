package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.Model.BatchRecordDTO;
import com.Model.BatchSeatDTO;
import com.Model.Course;
import com.Model.Student;
import com.exceptions.CourseException;
import com.exceptions.StudentException;

public interface dao {
	
	public String registerStudent(Student student)throws StudentException , SQLException;
	
	public boolean registerBatch(int cId, String cName, String sEmail) throws SQLException, CourseException, Exception;
	
	public boolean checkForStudent(String sEmail,String sPassword)throws StudentException;
	
	public List<Course> getListOfCourses() throws SQLException,CourseException;
	
	public boolean checkForCourse(String cName) throws SQLException ,CourseException;
	
	public int getCourseId(String cName)throws SQLException , CourseException;
	
	public boolean studentDetailEdit(String sEmail,String sPassword,String sNewPassword,String sNewName,String sNewCourse)throws SQLException,CourseException;
	
	public BatchRecordDTO getBatchRecordForStudent(String sEmail);
	
	public void editStudentProfile (String sEmail, String nSPassword, String nSName) throws SQLException;
	
	public List<BatchSeatDTO> getBatchSeatdetails () throws SQLException;
	
	public String getCourseName (int cId) throws SQLException;
	
	public void displayCourseAvailableWithOrWithoutSeats (String option) throws SQLException;
	
	public boolean checkForAdmin ();
	
	public void displayHomePageOptionToUser();
	
	public void displayOptionsForAdmin();
	
	public void adminAddNewCourse();
}
