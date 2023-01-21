package com.useCase;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.Model.Course;
import com.dao.dao;
import com.dao.daoImpl;
import com.exceptions.CourseException;

public class AdminDeleteCourse {
	public AdminDeleteCourse()throws SQLException,CourseException {
		Scanner sc = new Scanner(System.in);
		
		dao daoObj = new daoImpl();
		List<Course>listOfCourses = daoObj.getListOfCourses();
		System.out.println("List of Courses");
		listOfCourses.forEach(c->{
			System.out.println(c.getCname());
		});
		System.out.println("Enter course name to delete:");
		String cName = sc.next();
		daoObj.adminDeleteCourse(cName);
	}
}
