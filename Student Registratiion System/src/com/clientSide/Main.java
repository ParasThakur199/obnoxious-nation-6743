package com.clientSide;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.color.ConsoleColors;
import com.dao.dao;
import com.dao.daoImpl;
import com.useCase.AdminAddNewCourse;
import com.useCase.AdminAllocateStudentsInaBatchUnderaCourse;
import com.useCase.AdminCreateBatchunderaCourse;
import com.useCase.AdminDeleteCourse;
import com.useCase.AdminDisplayCourseInfo;
import com.useCase.AdminUpdateFeesOfCourse;
import com.useCase.AdminUpdateTotalSeatsOfaBatch;
import com.useCase.AdminViewStudentsOfEveryBatch;
import com.useCase.LoginStudent;
import com.useCase.RegisterStudentCourseBatch;

public class Main {
	
	public static final dao DAO_OBJ = new daoImpl();

	public static void main(String[] args) throws Exception {
		System.out.println();
		System.out.println(ConsoleColors.YELLOW_BACKGROUND_BRIGHT+"   WELCOME TO AUTOMATED STUDENT REGISTRATION SYSTEM !!"+ConsoleColors.RESET);
//		dao DAO_OBJ = DAO_OBJ;

		Scanner sc = new Scanner(System.in);
		
		DAO_OBJ.displayHomePageOptionToUser();

		try {
			
		int profile = sc.nextInt(); 
		
		if(profile == 1){
			
			if(DAO_OBJ.checkForAdmin()) {
				DAO_OBJ.displayOptionsForAdmin();
				
				int aOpt = sc.nextInt();
				
				switch (aOpt) {
				case 1: {
					AdminAddNewCourse a = new AdminAddNewCourse();		
					break;
				}
				case 2: {
					AdminUpdateFeesOfCourse a = new AdminUpdateFeesOfCourse();
					break;

				}
				case 3: {
					AdminDeleteCourse a = new AdminDeleteCourse();
					break;

				}
				case 4: {
					AdminDisplayCourseInfo a = new AdminDisplayCourseInfo();
					break;

				}
				case 5: {
					AdminCreateBatchunderaCourse a = new AdminCreateBatchunderaCourse();		
					break;
				}
				case 6: {
					AdminAllocateStudentsInaBatchUnderaCourse a = new AdminAllocateStudentsInaBatchUnderaCourse();
					break;
				}
				case 7: {
					AdminUpdateTotalSeatsOfaBatch a = new AdminUpdateTotalSeatsOfaBatch();
					break;
				}
				case 8: {
					AdminViewStudentsOfEveryBatch a = new AdminViewStudentsOfEveryBatch();
					break;
				}
				
				default:
					throw new IllegalArgumentException("Unexpected value: " + aOpt+ " enter valid options please.");
				}
			}
			else System.out.println(ConsoleColors.RED_BACKGROUND_BRIGHT+"Please Enter Correct ID and Password !!"+ConsoleColors.RESET);
			
		}
		
		else if(profile == 2) {
			RegisterStudentCourseBatch rscb = new RegisterStudentCourseBatch();
		}
		else if(profile == 3) {
			LoginStudent ls = new LoginStudent();
		}

		else System.out.println("Please enter valid input 1, 2 or 3");
		
		}
		catch(InputMismatchException ime) {
			System.out.println("Please enter valid input");
		}
		
		
	}
}
