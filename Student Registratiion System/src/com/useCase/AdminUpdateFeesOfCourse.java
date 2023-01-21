package com.useCase;

import java.util.Scanner;

import com.dao.dao;
import com.dao.daoImpl;

public class AdminUpdateFeesOfCourse {
	
	public AdminUpdateFeesOfCourse() {
		Scanner sc = new Scanner(System.in);
		dao daoObj = new daoImpl();
		System.out.println("Please Enter Course Name :");
		String cName = sc.next();
		System.out.println("Enter fees to update:");
		int fees = sc.nextInt();
		daoObj.adminUpdateFeesOfCourse(cName, fees);
	}
}	
