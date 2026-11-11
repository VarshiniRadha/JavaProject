package project2.java;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentManagementSystem {
     public static void main(String[] args) {
         StudentService service = new StudentService();
         Scanner sc = new Scanner(System.in);
         int choice = 0;
         
         do {
             try {
                 System.out.println("\n===== COACHING CENTER DASHBOARD =====");
                 System.out.println("1. Enroll New Student ");
                 System.out.println("2. Display All Registered Profiles");
                 System.out.println("3. Locate Student Record by ID");
                 System.out.println("4. Locate Student Record by Name");
                 System.out.println("5. Update Student Score");
                 System.out.println("6. Update Attendance");
                 System.out.println("7. Remove a Student Registration");
                 System.out.println("8. Display Current Academy Topper");
                 System.out.println("9. Exit Application");
                 System.out.println();
                 System.out.print("Enter code from 1-9 : ");

                 choice = sc.nextInt();
                 sc.nextLine(); 
                 
                 switch(choice) {
                    case 1 : 
                        System.out.print("Enter ID          : ");
                        int id = sc.nextInt();
                        sc.nextLine(); 
                        System.out.print("Enter Name        : ");
                        String name = sc.nextLine();
                        System.out.print("Enter email ID    : ");
                        String email = sc.nextLine();
                        System.out.print("Enter score       : ");
                        double score = sc.nextDouble();
                        sc.nextLine(); // Clear buffer
                        System.out.print("Enter Course code : ");
                        String cCode = sc.nextLine();
                        System.out.print("Enter Course Name : ");
                        String cName = sc.nextLine();
                        int attendance = 0;
                        
                        Course c = new Course(cCode, cName);
                        Student s = new Student(id, name, email, score, attendance, StudentStatus.ACTIVE, c);
                        service.addStudent(s);
                        System.out.println("Student data added successfully!!!");
                        break;
                        
                    case 2 : 
                        service.displayAll(); 
                        break;
                    
                    case 3 : 
                        System.out.print("Enter the ID to locate the student : ");
                        int searchID = sc.nextInt();
                        sc.nextLine();                       
                        Student s1 = service.searchById(searchID);
                        if(s1 != null) {
                            System.out.println(s1);
                        } else {
                            System.out.println("No record found");
                        }
                        break;
                        
                    case 4 : 
                        System.out.print("Enter the name to locate the student : ");
                        String searchName = sc.nextLine();
                        service.searchByName(searchName);
                        break;
                        
                    case 5 : 
                        System.out.print("Enter the student ID you want to update : ");
                        int updateID = sc.nextInt();
                        System.out.print("Enter the student updated mark          : ");
                        double updateScore = sc.nextDouble();
                        sc.nextLine(); 
                        System.out.println(service.updateMarks(updateID, updateScore) ? "Score Updated" : "Student ID not found");
                        break;
                        
                    case 6 :
                        System.out.print("Enter the student ID you want to update    : ");
                        int updateID1 = sc.nextInt();
                        System.out.print("Enter the number of total session attended : ");
                        int updateAttendance = sc.nextInt();
                        sc.nextLine();
                        System.out.println(service.updateAttendance(updateID1, updateAttendance) ? "Attendance Updated" : "Student ID not found");
                        break;
                        
                    case 7 :
                        System.out.print("Enter the student ID you want to remove : ");
                        int deleteID = sc.nextInt();
                        sc.nextLine(); 
                        System.out.println(service.removeStudent(deleteID) ? "Student data deleted" : "Student ID not found");
                        break;
                    
                    case 8 : 
                        service.displayTopper(); 
                        break;
                    
                    case 9 : 
                        System.out.println("Exited from the application.");
                        break;
                    
                    default : 
                        System.out.println("Invalid choice enter value from 1 to 9!");
                 }
             }
             catch(Exception e) 
             {
                 if (e instanceof InputMismatchException || e.getMessage() == null) 
                 {
                     System.out.println("Please type numbers only!");
                 } 
                 else {
                     System.out.println("❌ Message: " + e.getMessage());
                 }
                 sc.nextLine(); 
                 choice = 0;    
             }
       } while(choice != 9);
       
       sc.close();
     }
}
