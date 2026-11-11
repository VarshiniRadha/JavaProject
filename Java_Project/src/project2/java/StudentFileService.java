package project2.java;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StudentFileService {
	public static String file_path = "students.txt";
	
	public static void savingToFile(Map<Integer, Student> students) throws IOException {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file_path))) {
			for(Student s : students.values())
			{
				bw.write(String.format("%d | %s | %s | %s | %.2f | %d | %s | %s \n" , s.getId(), s.getName(), s.getEmail(),
						s.getCourse().getCourseCode(),s.getMarks(),s.getAttendance(),s.getStatus().name(),s.getCourse().getCourseName()));
			}
		}
	}
	public static Map<Integer, Student> loadFromFile() throws IOException {
        Map<Integer, Student> students = new HashMap<>();
        File file = new File(file_path);
        if (!file.exists()) return students; 

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) 
            { 
                String[] tokens = line.split(" | ");  
                if (tokens.length == 8) 
                {
                    int id = Integer.parseInt(tokens[0]);
                    double marks = Double.parseDouble(tokens[4]);
                    int attendance = Integer.parseInt(tokens[5]);
                    StudentStatus status = StudentStatus.valueOf(tokens[6]);
                    Course course = new Course(tokens[3], tokens[7]);
                    Student s = new Student(id, tokens[1], tokens[2] , marks, attendance, status, course);
                    students.put(id, s);
                }
            }
        }
        return students;
    }
}