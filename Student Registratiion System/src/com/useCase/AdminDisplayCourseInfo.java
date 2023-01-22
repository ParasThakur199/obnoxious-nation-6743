package com.useCase;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.Model.Course;
import com.color.ConsoleColors;
import com.dao.dao;
import com.dao.daoImpl;
import com.exceptions.CourseException;

public class AdminDisplayCourseInfo {
	public AdminDisplayCourseInfo()throws SQLException,CourseException {
		Scanner sc = new Scanner(System.in);
		dao daoObj = new daoImpl();
		List<Course> lisOfCourses = daoObj.getListOfCourses();
		System.out.println(ConsoleColors.ORANGE+"List of courses- "+ConsoleColors.RESET);
		lisOfCourses.forEach(c ->{
			System.out.println(c.getCname());
		});
		System.out.println(ConsoleColors.ORANGE+"Enter course name to get it's information"+ConsoleColors.RESET);
		String cName = sc.next();
		daoObj.adminDisplayCourseInfo(cName);
	}
}
