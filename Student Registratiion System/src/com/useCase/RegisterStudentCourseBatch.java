package com.useCase;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.Model.Student;
import com.color.ConsoleColors;
import com.dao.dao;
import com.dao.daoImpl;
import com.exceptions.CourseException;
import com.exceptions.StudentException;

public class RegisterStudentCourseBatch {
	
	public RegisterStudentCourseBatch() {
		Scanner sc = new Scanner(System.in);
		Student s = null;
		String cName = null;
		
		try{	
			System.out.println(ConsoleColors.ORANGE+"Enter your Email :"+ConsoleColors.RESET);
			String sEmail = sc.next();
			System.out.println(ConsoleColors.ORANGE+"Enter your Password :"+ConsoleColors.RESET);
			String sPassword = sc.next();
			System.out.println(ConsoleColors.ORANGE+"Enter your Name :"+ConsoleColors.RESET);
			String sName = sc.next();				
			
			s = new Student(sEmail, sPassword, sName);
			
			dao daoObj = new daoImpl();
			
			try {
				Boolean ans = daoObj.checkForStudent(sEmail, sPassword);
				if(!ans) {
					daoObj.registerStudent(s);
					System.out.println();
					System.out.println(ConsoleColors.GREEN_BACKGROUND+"Student "+ sName +" with e-mail "+ sEmail +" registstered successfully"+ConsoleColors.RESET);
					System.out.println();
									
					System.out.println(ConsoleColors.BLUE_BOLD+"Enter yes to join a course. ");
					System.out.println("Enter no to quit"+ConsoleColors.RESET);
					String opt = sc.next();
					
					if(opt.equalsIgnoreCase("yes")) {
						System.out.println(ConsoleColors.ORANGE+"List of courses available :"+ConsoleColors.RESET);
						System.out.println(ConsoleColors.PURPLE_BRIGHT);
						daoObj.displayCourseAvailableWithOrWithoutSeats("includeslno");
						System.out.println(ConsoleColors.RESET);
						System.out.println("Enter the name of the course name you want to join:");

						String sDecision2 = sc.next().toUpperCase();
						int cId = daoObj.getCourseId(sDecision2);
						if(daoObj.checkForCourse(sDecision2)) {
							daoObj.registerBatch(cId, sDecision2, sEmail);
						}
						else {
							System.out.println("Such course "+ sDecision2 +" doesn't exist");
							
						}
					}
					else throw new CourseException("Have a nice day " +sName +" .");
				}
				else System.out.println(ConsoleColors.YELLOW_BACKGROUND+"Student Already Exists"+ConsoleColors.RESET);
			}
			catch(StudentException e){
				System.out.println(e.getMessage());
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			} catch (CourseException e1) {
				System.out.println(e1.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
			}
	}
}
