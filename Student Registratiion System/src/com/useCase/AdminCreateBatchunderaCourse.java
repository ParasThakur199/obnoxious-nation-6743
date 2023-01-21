package com.useCase;

import java.util.Scanner;

import com.dao.dao;
import com.dao.daoImpl;

public class AdminCreateBatchunderaCourse {

	public AdminCreateBatchunderaCourse() {
		Scanner sc = new Scanner(System.in);
		
		dao daoObj = new daoImpl();
		System.out.println("Enter Course name:");
		String cName = sc.next().toUpperCase();
		System.out.println("Enter Batch no:");
		int bId = sc.nextInt();
		System.out.println("Enter total seats in this batch: ");
		int totalSeats = sc.nextInt();
		daoObj.adminCreateBatchunderaCourse(cName, bId, totalSeats);
		
	}
}
