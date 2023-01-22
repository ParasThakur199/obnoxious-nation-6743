package com.useCase;

import java.util.Scanner;

import com.color.ConsoleColors;
import com.dao.dao;
import com.dao.daoImpl;

public class AdminUpdateFeesOfCourse {
	
	public AdminUpdateFeesOfCourse() {
		Scanner sc = new Scanner(System.in);
		dao daoObj = new daoImpl();
		System.out.println(ConsoleColors.ORANGE+"Please Enter Course Name :"+ConsoleColors.RESET);
		String cName = sc.next();
		System.out.println(ConsoleColors.ORANGE+"Enter fees to update:"+ConsoleColors.RESET);
		int fees = sc.nextInt();
		daoObj.adminUpdateFeesOfCourse(cName, fees);
	}
}	
