package com.DAO;

import java.sql.SQLException;

import com.Model.Student;
import com.exception.StudentException;

public interface dao {
	public String registerStudent(Student student)throws SQLException;
}
