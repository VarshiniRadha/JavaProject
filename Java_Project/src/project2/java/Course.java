package project2.java;

public class Course {
	private String courseCode;
	private String courseName;
	
	public String getCourseCode() 
	{
		return courseCode;
	}
	
	public Course(String courseCode, String courseName) 
	{
		this.courseCode = courseCode;
		this.courseName = courseName;
	}
	
	public String getCourseName() 
	{
		return courseName;
	}
	
	@Override
    public String toString() 
	{ 
		return courseCode + " - " + courseName; 
	}
}
