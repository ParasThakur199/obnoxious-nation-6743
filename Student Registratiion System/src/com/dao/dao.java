package com.dao;

import java.sql.SQLException;

import com.Model.Student;
import com.exceptions.CourseException;
import com.exceptions.StudentException;

public interface dao {
	
	public String registerStudent(Student student)throws StudentException , SQLException;
	
//	public boolean registerBatch(int cId, String cName, String sEmail) throws SQLException, CourseException, Exception;
}
