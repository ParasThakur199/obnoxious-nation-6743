package com.useCase;

import java.util.Scanner;

import com.color.ConsoleColors;
import com.dao.dao;
import com.dao.daoImpl;

public class AdminCreateBatchunderaCourse {

	public AdminCreateBatchunderaCourse() {
		Scanner sc = new Scanner(System.in);
		
		dao daoObj = new daoImpl();
		System.out.println(ConsoleColors.ORANGE+"Enter Course name :"+ConsoleColors.RESET);
		String cName = sc.next().toUpperCase();
		System.out.println(ConsoleColors.ORANGE+"Enter Batch no:"+ConsoleColors.RESET);
		int bId = sc.nextInt();
		System.out.println(ConsoleColors.ORANGE+"Enter total seats in this batch: "+ConsoleColors.RESET);
		int totalSeats = sc.nextInt();
		daoObj.adminCreateBatchunderaCourse(cName, bId, totalSeats);
		
	}
}
