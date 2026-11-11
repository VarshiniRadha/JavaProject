package project2.java;

public class Student {
	private int id;
	private String name;
	private String email;
	private double marks;
	private int attendance; 
    private StudentStatus status;
    private Course course;
    
    public Student(int id, String name, String email, double marks, int attendance, StudentStatus status, Course course) {
    	this.id = id;
    	this.name = name;
    	this.email = email;
    	this.marks = marks;
        this.attendance = attendance;
        this.status = status;
        this.course = course; 	
    }
    
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public double getMarks() {
		return marks;
	}
	public int getAttendance() {
		return attendance;
	}
	public StudentStatus getStatus() {
		return status;
	}
	public Course getCourse() {
		return course;
	}
	
	private String calculateGrade() {
		String grade = null;
		if(marks >= 90)
		{
			grade = "A+";
		}
		else if(marks >= 80 && marks <= 89)
		{
			grade = "A";
		}
		else if(marks >= 70 && marks <= 79)
		{
			grade = "B+";
		}
		else if(marks >= 60 && marks <= 69)
		{
			grade = "B";
		}
		else if(marks >= 50 && marks <= 59)
		{
			grade = "C";
		}
		else if(marks >= 40 && marks <= 49)
		{
			grade = "D";
		}	
		else
		{
			grade = "Fail";
		}
		return grade;
	}
	
	@Override
    public String toString() {
        return String.format("ID: %d | Name: %-10s | Course: %-15s | Marks: %.2f (Grade: %s) | Attendance: %d | Status: %s", 
                id, name, course.getCourseName(), marks, calculateGrade(), attendance, status);
    }

	public void setMarks(double marks) {
		this.marks = marks;
		
	}

	public void setAttendance(int attendance) {
		this.attendance = attendance;
		
	}

}
