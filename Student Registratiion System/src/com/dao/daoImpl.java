package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.Model.Admin;
import com.Model.BatchRecordDTO;
import com.Model.BatchSeatDTO;
import com.Model.Course;
import com.Model.Student;
import com.Utility.DButil;
import com.color.ConsoleColors;
import com.exceptions.CourseException;
import com.exceptions.StudentException;

public class daoImpl implements dao{

	@Override
	public String registerStudent(Student student) throws StudentException, SQLException {
		
		String answer = "Student Already Exists !!";
		
		String sEmail = student.getsEmail();
		String sPassword = student.getsPassword();
		String sName = student.getsName();
		
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("Insert into students values(?,?,?)");
			ps.setString(1,sEmail);
			ps.setString(2,sPassword);
			ps.setString(3, sName);
			
			int x = ps.executeUpdate();
			answer = x + "Student Statements Inserted";

		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return answer;
	}

	@Override
	public boolean registerBatch(int cId, String cName, String sEmail) throws Exception {
		boolean answer = false;
		int lbatchId;
		int ltotalSeats;
		int lseatsFilled;
		int fbatchId;
		
		dao daoObj = new daoImpl();
		
		try(Connection conn = DButil.getConnection()) {
		boolean flag = false;
		boolean used = false;
		PreparedStatement ps = conn.prepareStatement("Select bId,totalSeats,SeatsFilled from batchSeats where cId= ?");
		ps.setInt(1, cId);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			lbatchId = rs.getInt("bId");
			ltotalSeats = rs.getInt("totalSeats");
			lseatsFilled = rs.getInt("seatsFilled");
			
			if(lseatsFilled < ltotalSeats && used == false) {
				used = true;
				flag = true;
				answer = true;
				fbatchId = lbatchId;
				
				PreparedStatement ps3 = conn.prepareStatement("update batchSeats set seatsFilled = (seatsFilled + 1)where cId=? AND bId = ?");
				ps3.setInt(1,cId);
				ps3.setInt(2,lbatchId);
				int x2 = ps3.executeUpdate();
				
				PreparedStatement ps2 = conn.prepareStatement("Insert into batch(batchNo, cId, sEmail) values(?,?,?)");
				ps2.setInt(1,fbatchId);
				ps2.setInt(2,cId);
				ps2.setString(3,sEmail);
				
				int x = ps2.executeUpdate();
				System.out.println(ConsoleColors.GREEN_BACKGROUND+"Student "+daoObj.getSNameFromEmail(sEmail)+" with Email "+sEmail+" registered into course "+cName+ConsoleColors.RESET);
			}
		}
		if(!flag) {
			throw new CourseException(ConsoleColors.RED_BACKGROUND_BRIGHT+"Seats not available for course"+cName+"batch not allocated for the course yet"+ConsoleColors.RESET);
		}
		} 
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return answer;
	}

	@Override
	public boolean checkForStudent(String sEmail, String sPassword) throws StudentException {
		boolean answer = false;
		
		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("Select * from Students where sEmail=? AND sPassword=?");
			ps.setString(1,sEmail);
			ps.setString(2, sPassword);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				answer=true;
			}
		} catch (SQLException e) {
			throw new StudentException(e.getMessage());
		}
		
		return answer;
	}

	@Override
	public List<Course> getListOfCourses() throws SQLException, CourseException {
		List<Course> listOfCourses = new ArrayList<>();
		boolean isEmpty = true;
		
		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("Select * from Courses");
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				isEmpty = false;
				int cId = rs.getInt("cId");
				String cName = rs.getString("cName");
				int fees = rs.getInt("fees");
				String cInfo = rs.getString("cInfo");
				
				Course c = new Course(cId,cName,fees,cInfo);
				listOfCourses.add(c);
			}
		}
		catch(SQLException e) {
			throw new SQLException(e.getMessage());
		}
		if(isEmpty)throw new CourseException("No Course Present");
		return listOfCourses;
	}

	@Override
	public boolean checkForCourse(String cName) throws SQLException, CourseException {
		boolean answer = false;
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("select cName from courses where cname=?");
			ps.setString(1,cName);
			
			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				answer = true;
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
		return answer;
	}

	@Override
	public int getCourseId(String cName) throws SQLException, CourseException {
		int answer = 0;
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps= conn.prepareStatement("select cId from courses where cName=?");
			ps.setString(1,cName);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				answer = rs.getInt("cId");
			}else {
				throw new CourseException(ConsoleColors.YELLOW_BACKGROUND+"There is no "+cName+" course. Please Enter proper course name."+ConsoleColors.RESET);
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return answer;
	}

	@Override
	public BatchRecordDTO getBatchRecordForStudent(String sEmail) {
		BatchRecordDTO brdto = null;
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("Select * from batch where sEmail=?");
			ps.setString(1,sEmail);
		
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int batchId = rs.getInt("batchId");
				int batchNo = rs.getInt("batchNo");
				int cId = rs.getInt("cId");
				String sEmailr = rs.getString("sEmail");
				
				brdto = new BatchRecordDTO(batchId,batchNo,cId,sEmailr);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return brdto;
	}

	@Override
	public boolean studentDetailEdit(String sEmail, String sPassword, String sNewPassword, String sNewName,String sNewCourseName) throws SQLException, CourseException {
		boolean answer = false;
		
		dao daoObj = new daoImpl();
		BatchRecordDTO br = null;
		
		Connection conn = DButil.getConnection();
		int newCId = daoObj.getCourseId(sNewCourseName);
		try {
			daoObj.registerBatch(newCId, sNewCourseName, sEmail);
			answer = true;
			
			if(daoObj.getBatchRecordForStudent(sEmail)!=null) {
				br = daoObj.getBatchRecordForStudent(sEmail);
				int batchId = br.getBatchid();
				int batchNo = br.getBatchNo();
				
				if((Integer)br.getBatchNo() != null) {
					PreparedStatement ps = conn.prepareStatement("DELETE from batch where batchId=?");
					ps.setInt(1,batchId);
					
					int x = ps.executeUpdate();
					
					PreparedStatement ps2 = conn.prepareStatement("update batchSeats set seatsFilled = (seatsFilled-1) where bId = ?");
					ps2.setInt(1, batchNo);
					ps2.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.out.println("Check");
			throw new SQLException(e.getMessage());
		} catch (CourseException e) {
			System.out.println("Check");
			throw new CourseException(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return answer;
	}

	@Override
	public void editStudentProfile(String sEmail, String nSPassword, String nSName) throws SQLException {
		dao daoObj = new daoImpl();
		
		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("update students set sPassword=?,sName=? where sEmail=?");
			ps.setString(1,nSPassword);
			ps.setString(2,nSName);
			ps.setString(3, sEmail);
			
			ps.executeUpdate();
			System.out.println(ConsoleColors.GREEN_BACKGROUND+"Student "+daoObj.getSNameFromEmail(sEmail)+" with Email "+" updated with new password and name"+ConsoleColors.RESET);
		}
		catch(SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public List<BatchSeatDTO> getBatchSeatdetails() throws SQLException {
		List<BatchSeatDTO> listOfBS = new ArrayList<>();
		BatchSeatDTO bs = null;
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from batchSeats");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int bId = rs.getInt("bId");
				int cId = rs.getInt("cId");
				int totalSeats = rs.getInt("totalSeats");
				int SeatsFilled = rs.getInt("SeatsFilled");
				
				bs = new BatchSeatDTO(bId,cId,totalSeats,SeatsFilled);
				listOfBS.add(bs);
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return listOfBS;
	}

	@Override
	public String getCourseName(int cId) throws SQLException {
		String cName = null;
		
		try(Connection conn = DButil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("Select cName from courses where cId = ?");
			ps.setInt(1,cId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				cName = rs.getString("cName");
			}
		} 
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return cName;
	}

	@Override
	public void displayCourseAvailableWithOrWithoutSeats(String option) throws SQLException {

		dao daoObj = new daoImpl();
		List<BatchSeatDTO> listOfBS;
		try {
		
		listOfBS = daoObj.getBatchSeatdetails();
		int n = listOfBS.size();
		int[] cIdIndex = new int[n];
		int[] seatsFilled = new int[n];
		int[] totalSeats = new int[n];
		
		for(int i=0; i<n; i++) {
			cIdIndex[i] = listOfBS.get(i).getcId();
			seatsFilled[i] = listOfBS.get(i).getSeatsFilled();
			totalSeats[i] = listOfBS.get(i).getTotalSeats();
		}
		
		for(int i=0;i<n;i++) {
			for(int j=i+1; j<n; j++) {
				if(cIdIndex[i] == cIdIndex[j]) {
					seatsFilled[i] = seatsFilled[i] + seatsFilled[j];
					totalSeats[i] = totalSeats[i] +totalSeats[j];
					totalSeats[j] = 0;
					
				}
			}
		}
		
		if(option.equalsIgnoreCase("includeSeat"))
		{
		System.out.println();
		System.out.println(ConsoleColors.BLUE_BOLD+"Course name  |  Seats available"+ConsoleColors.RESET);

		for(int i=0; i<n; i++) {
			if(totalSeats[i] != 0 && (totalSeats[i] != seatsFilled[i]) && cIdIndex[i] > 0)
			System.out.println(daoObj.getCourseName(cIdIndex[i] ) +"         " + (totalSeats[i] - seatsFilled[i]) );
		}
		}
		
		else if(option.equalsIgnoreCase("includeslno"))
		{
		System.out.println(ConsoleColors.PURPLE_BRIGHT+"S.no - Course name"+ConsoleColors.RESET);
		int slno =1;
		for(int i=0; i<n; i++) {
			if(totalSeats[i] != 0&& (totalSeats[i] != seatsFilled[i]))
			{
				String cName = daoObj.getCourseName(cIdIndex[i]);
				if(cName!= null) {
					System.out.println(slno++ +" -> " + cName);

				}
			}			
		}
		}
		}
		
		catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
	}

	@Override
	public boolean checkForAdmin() {
		List<Admin> listOfAdmins = new ArrayList<>();
		listOfAdmins.add(new Admin("paras","12345"));
		listOfAdmins.add(new Admin("qwerty","12345"));
		boolean isPresent = false;
		
		Scanner sc = new Scanner(System.in);
		System.out.println(ConsoleColors.BLUE_ITALIC+"Login to profile-"+ConsoleColors.RESET);
		System.out.println(ConsoleColors.ORANGE+"Enter your Username : "+ConsoleColors.RESET);
		String aName = sc.next();
		System.out.println(ConsoleColors.ORANGE+"Enter your Password : "+ConsoleColors.RESET);
		String aPassword = sc.next();
		
		Admin ad = new Admin(aName,aPassword);
		
		if(listOfAdmins.contains(ad))isPresent=true;
		return isPresent;
	}

	@Override
	public void displayHomePageOptionToUser() {
		System.out.println();
		System.out.println();
		System.out.println("Please choose your profile by entering the number below-");
		System.out.println();
		System.out.println(ConsoleColors.PURPLE_BRIGHT+"+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+"+"\n"
											  +"| 1. Login as Administrator            |"+"\n"
											  +"| 2. Register in a course as a student |"+"\n"
											  +"| 3. Login as Student                  |"+"\n"
											  +"+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+"+ConsoleColors.RESET);
		
	}

	@Override
	public void displayOptionsForAdmin() {
		System.out.println(ConsoleColors.GREEN_BACKGROUND+"Login Successfully !!"+ConsoleColors.RESET);
		System.out.println();
		System.out.println(ConsoleColors.PURPLE_BRIGHT+"+-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=--=-=-=-=-=-=-=-+");
		System.out.println("| Welcome Admin!                                  |");
		System.out.println("|                                                 |");
		System.out.println("| 1. Add a new Courses                            |");
		System.out.println("| 2. Update Fees of course.                       |");	
		System.out.println("| 3. Delete  a course from any Training session.  |");
		System.out.println("| 4. Search information about a course.           |");
		System.out.println("| 5. Create Batch under a course.                 |");
		System.out.println("| 6. Allocate students in a Batch under a course. |");
		System.out.println("| 7. Update total seats of a batch.               |");
		System.out.println("| 8. View the students of every batch.            |");
		System.out.println("|                                                 |");
		System.out.println("+-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=--=-=-=-=-=-=-=-+"+ConsoleColors.RESET);
	}

	@Override
	public void adminAddNewCourse() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println(ConsoleColors.ORANGE+"Enter Course ID :"+ConsoleColors.RESET);
		int cId = sc.nextInt();
		System.out.println(ConsoleColors.ORANGE+"Enter Course Name :"+ConsoleColors.RESET);
		String cName = sc.next();
		System.out.println(ConsoleColors.ORANGE+"Enter Course fees:"+ConsoleColors.RESET);
		int fees = sc.nextInt();
		sc.nextLine();
		System.out.println(ConsoleColors.ORANGE+"Enter course information:"+ConsoleColors.RESET);
		String cInfo = sc.nextLine();
		
		try(Connection conn = DButil.getConnection()){
			
			PreparedStatement ps = conn.prepareStatement("Insert into courses values (?, ?, ?, ?)");
			ps.setInt(1, cId);
			ps.setString(2, cName.toUpperCase());
			ps.setInt(3, fees);
			ps.setString(4, cInfo);
			
			int x = ps.executeUpdate();
			
			System.out.println(ConsoleColors.GREEN_BACKGROUND+"Course "+ cName +" inserted into database successfully."+ConsoleColors.RESET);

		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void adminUpdateFeesOfCourse(String cName, int fees) {
		dao daoObj = new daoImpl();
		
		try(Connection conn = DButil.getConnection()){
			int cId = daoObj.getCourseId(cName);
			PreparedStatement ps = conn.prepareStatement("update courses set fees = ? where cId = ?");
			ps.setInt(1, fees);
			ps.setInt(2, cId);
			ps.executeUpdate();
			
			System.out.println(ConsoleColors.GREEN_BACKGROUND+"Course "+cName +" is updated with fees "+ fees+"."+ConsoleColors.RESET);
			
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		catch (CourseException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void adminDeleteCourse(String cName) {
		dao daoObj = new daoImpl();

		try(Connection conn = DButil.getConnection()){
			int cId = daoObj.getCourseId(cName);

			PreparedStatement ps2 = conn.prepareStatement("delete from batchSeats where cId = ?");
			ps2.setInt(1, cId);
			PreparedStatement ps = conn.prepareStatement("delete from courses where cId = ?");
			ps.setInt(1, cId);
			ps2.executeUpdate();
			ps.executeUpdate();
			
			System.out.println(ConsoleColors.GREEN_BACKGROUND+cName + " course deleted successfully."+ConsoleColors.RESET);
			System.out.println(cName + " deleted in every training session.");
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		catch (CourseException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void adminDisplayCourseInfo(String cName) {
		dao daoObj = new daoImpl();

		try(Connection conn = DButil.getConnection()){
			int cId = daoObj.getCourseId(cName);

			PreparedStatement ps = conn.prepareStatement("select * from courses where cId = ?");
			ps.setInt(1, cId);
			ResultSet rs =  ps.executeQuery();
			
			if(rs.next()) {
				int cIdl = rs.getInt("cId");
				String cNamel = rs.getString("cName");
				int fees = rs.getInt("fees");
				String cInfo = rs.getString("cInfo");
				System.out.println(ConsoleColors.BLUE+"+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+");
				System.out.println("                                                       ");
				System.out.println(" Course Information for "+ cName +" -                    ");
				System.out.println();
				System.out.println(" CourseID: "+ cIdl+"                                           ");
				System.out.println(" Course Name: "+ cNamel+"                                      ");
				System.out.println(" Course fee: "+ fees+"                                      ");
				System.out.println(" Course information: "+ cInfo+"                           ");
				System.out.println("                                                       ");
				System.out.println("+-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-+"+ConsoleColors.RESET);
			}
			
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		catch (CourseException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void adminCreateBatchunderaCourse(String cName, int bId, int totalSeats) {
		dao daoObj = new daoImpl();
		
		try(Connection conn = DButil.getConnection()){
			int cId = daoObj.getCourseId(cName);
			PreparedStatement ps = conn.prepareStatement("insert into batchSeats values (?, ?, ?, ?)");
			ps.setInt(1, bId);
			ps.setInt(2, cId);
			ps.setInt(3, totalSeats);
			ps.setInt(4, 0);
			ps.executeUpdate();
			System.out.println(ConsoleColors.GREEN_BACKGROUND+"Batch of batch ID "+ bId +" created under course "+ cName + " with seat capacity of "+ totalSeats+ConsoleColors.RESET);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (CourseException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void adminAllocateStudentsInaBatchUnderaCourse() {
		dao daoObj = new daoImpl();
		Scanner sc = new Scanner(System.in);
		
		try(Connection conn = DButil.getConnection()){
			List<Student> listOfStudents = daoObj.getListOfStudents();
			if(listOfStudents.size()==0)System.out.println("No student has enrolled yet.");
			else {
				
				System.out.println(ConsoleColors.ORANGE+"List of students in our Institution-"+ConsoleColors.RESET);
				System.out.println(ConsoleColors.BLUE);
				listOfStudents.forEach(s -> System.out.println(s));
				System.out.println(ConsoleColors.RESET);
				System.out.println(ConsoleColors.CYAN_ITALIC+"Process to allocate Student in a Batch Under a course"+ConsoleColors.RESET);
				System.out.println(ConsoleColors.ORANGE+"Enter the Student E-mail: "+ConsoleColors.RESET);
				String sEmail = sc.next();
				String sPassword = daoObj.getStudentPassword(sEmail);
				
				boolean existence = daoObj.checkForStudent(sEmail, sPassword);
				
				if(existence) {
					System.out.println(ConsoleColors.ORANGE+"Enter course name to allocate into batch othe course:"+ConsoleColors.RESET);
					daoObj.displayCourseAvailableWithOrWithoutSeats("includeslno");
					String cName = sc.next();
					int cId = daoObj.getCourseId(cName);
					
					daoObj.registerBatch(cId, cName, sEmail);
					
				}
				else throw new StudentException("Student "+ daoObj.getSNameFromEmail(sEmail) +" with e-mail "+ sEmail + " not found");
				
			}
			
			
			
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (StudentException e) {
			System.out.println(e.getMessage());
		} catch (CourseException e) {
			System.out.println(e.getMessage());
		} 
		catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		
	}

	@Override
	public List<Student> getListOfStudents() {
		List<Student> listOfStudents = new ArrayList<>();

		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("select * from students");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				String sEmail = rs.getString("sEmail");
				String sPassword = rs.getString("sPassword");
				String sName = rs.getString("sName");
				Student s = new Student(sEmail, sPassword, sName);
				listOfStudents.add(s);
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		return listOfStudents;
		
	}

	@Override
	public String getStudentPassword(String sEmail) throws StudentException {
		dao daoObj = new daoImpl();
		String answer = null;
		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("select sPassword from students where sEmail = ?");
			ps.setString(1, sEmail);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				answer = rs.getString("sPassword"); 
			}
			else throw new StudentException(ConsoleColors.RED_BACKGROUND_BRIGHT+"Student "+  daoObj.getSNameFromEmail(sEmail) +" with E-mail "+ sEmail +" does not exists"+ConsoleColors.RESET);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		
		return answer;
	}

	@Override
	public void adminUpdateTotalSeatsOfaBatch() {
		Scanner sc = new Scanner(System.in);
		
		
		try(Connection conn = DButil.getConnection()){
			dao daoObj = new daoImpl();
			
			List<BatchSeatDTO> listOfBatchSeatDetails = daoObj.getBatchSeatdetails();
			
			if(listOfBatchSeatDetails.size()==0) {
				System.out.println(ConsoleColors.YELLOW_BACKGROUND_BRIGHT+"There are no batches created yet. Please create one to edit it."+ConsoleColors.RESET);
			}
			else {
				System.out.println(ConsoleColors.GREEN_BACKGROUND+"Batches available:"+ConsoleColors.RESET);
				listOfBatchSeatDetails.forEach(b -> {
					System.out.println(b.getbId());
				});
				System.out.println();
				System.out.println(ConsoleColors.ORANGE+"Enter Batch number :"+ConsoleColors.RESET);
				int bId = sc.nextInt();
				System.out.println(ConsoleColors.ORANGE+"Enter new capacity of Seats:"+ConsoleColors.RESET);
				int newTotalSeats = sc.nextInt();
				PreparedStatement ps = conn.prepareStatement("update batchSeats set totalSeats = ? where bId = ?");
				ps.setInt(1, newTotalSeats);
				ps.setInt(2, bId);
				ps.executeUpdate();
				System.out.println(ConsoleColors.GREEN_BACKGROUND+"BatchID "+ bId +" updated to seat capacity "+newTotalSeats+ConsoleColors.RESET);
			}
			
		}	
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void adminViewStudentsOfEveryBatch() {
		dao daoObj = new daoImpl();
		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("select batchNo, sEmail from batch order by batchNo");
			ResultSet rs = ps.executeQuery();
			boolean isEmpty = true;
			while(rs.next()) {
				if(isEmpty)System.out.println("BatchNo.  |  Student name");
				isEmpty = false;

				int batchNo = rs.getInt("batchNo");
				String sEmail = rs.getString("sEmail");
				System.out.println(batchNo +"            "+ daoObj.getSNameFromEmail(sEmail));
			}
			if(isEmpty)System.out.println(ConsoleColors.RED_BOLD+"No students available in a batch (or) no batches available. Please check for these conditions."+ConsoleColors.RESET);
			else {
				System.out.println();
				System.out.println(ConsoleColors.GREEN_BOLD +"All Batches along with their students displayed."+ConsoleColors.RESET);
			}

		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public List<String> getSEmailListFromBatchWhoIsIntoACourse() {
		List<String> SEmailListFromBatchWhoIsIntoACourse = new ArrayList<>();

		try(Connection conn = DButil.getConnection()){

			PreparedStatement ps = conn.prepareStatement("select sEmail from batch");
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				String sEmail = rs.getString("sEmail");
				SEmailListFromBatchWhoIsIntoACourse.add(sEmail);
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		return SEmailListFromBatchWhoIsIntoACourse;
	}

	@Override
	public String getSNameFromEmail(String sEmail) {
		String name = null;
		
		try(Connection conn = DButil.getConnection()){
			PreparedStatement ps = conn.prepareStatement("select sName from students where sEmail = ?");
			ps.setString(1, sEmail);
			ResultSet rs= ps.executeQuery();
			
			if(rs.next()) {
				name = rs.getString("sName");
			}
			else throw new StudentException(ConsoleColors.RED_BACKGROUND_BRIGHT+"Student with e-mail "+ sEmail+" does not exists"+ConsoleColors.RESET);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (StudentException e) {
			System.out.println(e.getMessage());
		} 
		
		return name;
	}
	

}
