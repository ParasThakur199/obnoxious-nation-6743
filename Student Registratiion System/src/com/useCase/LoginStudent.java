package com.useCase;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.color.ConsoleColors;
import com.dao.dao;
import com.dao.daoImpl;
import com.exceptions.CourseException;
import com.exceptions.StudentException;

public class LoginStudent {
	
	public LoginStudent() {
		Scanner sc = new Scanner(System.in);
		System.out.println(ConsoleColors.ORANGE+"Please Enter your Email :"+ConsoleColors.RESET);
		String sEmail = sc.next();
		System.out.println(ConsoleColors.ORANGE+"Please Enter your Password :"+ConsoleColors.RESET);
		String sPassword = sc.next();
		
		dao daoObj = new daoImpl();
		try {
			boolean isStudentPresent = daoObj.checkForStudent(sEmail, sPassword);
			
			if(isStudentPresent) {
				System.out.println(ConsoleColors.GREEN_BACKGROUND+"Login Successfully !!"+ConsoleColors.RESET);
				System.out.println();
				System.out.println(ConsoleColors.PURPLE_BRIGHT+"+-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=--=-=-=-=-=-=-+");
				System.out.println("| Welcome "+ daoObj.getSNameFromEmail(sEmail)+"                                         |");
				System.out.println("|                                                       |");
				System.out.println("| Please select-                                        |");
				System.out.println("| 1. Update my details                                  |");
				System.out.println("| 2. View all available courses and seat availability   |");
				System.out.println("|                                                       |");
				System.out.println("+-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=--=-=-=-=-=-=-+"+ConsoleColors.RESET);

				int opt = sc.nextInt();
				
				if(opt == 1) {
					System.out.println(ConsoleColors.ORANGE+"Enter your new password :"+ConsoleColors.RESET);
					String sNewPassword = sc.next();
					sc.nextLine();
					System.out.println(ConsoleColors.ORANGE+"Enter your new name :"+ConsoleColors.RESET);
					String sNewName = sc.nextLine();
					daoObj.editStudentProfile(sEmail, sNewPassword, sNewName);
					
					System.out.println(ConsoleColors.BLUE_BOLD+"Enter yes to change course :");
					System.out.println("Enter no to leave"+ConsoleColors.RESET);
					String opt2 = sc.next().toLowerCase();
					
					if(opt2.equalsIgnoreCase("yes")) {
						System.out.println(ConsoleColors.ORANGE+"Enter your new course :"+ConsoleColors.RESET);
						String sNewCourse = sc.next().toUpperCase();
						List<String>SEmailListFromBatchWhoIsIntoACourse = daoObj.getSEmailListFromBatchWhoIsIntoACourse();
						
						if(SEmailListFromBatchWhoIsIntoACourse.contains(sEmail)) {
							boolean check = daoObj.studentDetailEdit(sEmail, sPassword, sNewPassword, sNewName, sNewCourse);
						}
						else
						{
							int cId = daoObj.getCourseId(sNewCourse);
							daoObj.registerBatch(cId, sNewCourse, sEmail);
							System.out.println(ConsoleColors.GREEN_BOLD+"Student name "+sNewName+ " registered into course "+ sNewCourse+ConsoleColors.RESET);
						}
							
					}
						
				}
				else if(opt == 2) {
					
					daoObj.displayCourseAvailableWithOrWithoutSeats("includeSeat");
					
				}
				
			}
			else System.out.println(ConsoleColors.RED_BACKGROUND_BRIGHT+"Student with e-mail "+ sEmail +" does not exists"+ConsoleColors.RESET);
			
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		catch (StudentException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		catch (CourseException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {		
			System.out.println(e.getMessage());
		}
	}
}
