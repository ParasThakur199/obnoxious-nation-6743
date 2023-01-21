package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.Model.BatchRecordDTO;
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
}
