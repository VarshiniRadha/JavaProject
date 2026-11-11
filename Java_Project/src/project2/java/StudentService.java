package project2.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentService {

    private Map<Integer, Student> studentMap; 

    public StudentService() {
        try {
            this.studentMap = StudentFileService.loadFromFile();
            System.out.println("System Security: Loaded " + studentMap.size() + " profiles successfully.");
        } 
        catch (IOException e) 
        {
            System.out.println("Warning : Datas not loaded");
            this.studentMap = new HashMap<>();
        }
    }

    public void addStudent(Student student) throws IllegalArgumentException {
        if (studentMap.containsKey(student.getId())) 
        {
            throw new IllegalArgumentException("Error : "+student.getId() + " already exists");
        }
        if (student.getMarks() < 0 || student.getMarks() > 100) 
        {
            throw new IllegalArgumentException("Marks should lie below 0 to 100");
        }
        studentMap.put(student.getId(), student); 
        saveChanges(); 
    }

    public void displayAll() {
        if (studentMap.isEmpty()) 
        {
            System.out.println("No matching student profile details recorded inside our center registers.");
            return;
        }
        studentMap.values().forEach(System.out::println);
    }

    public Student searchById(int id) {
        return studentMap.get(id);
    }

    public void searchByName(String nameSnippet) {
        long matchCount = studentMap.values().stream()
                .filter(s -> s.getName().toLowerCase().contains(nameSnippet.toLowerCase()))
                .peek(System.out::println)
                .count();
        if (matchCount == 0) System.out.println("Zero profiles found ");
    }

    public boolean updateMarks(int id, double newMarks) {
        if (newMarks < 0 || newMarks > 100) throw new IllegalArgumentException("Invalid marks boundary constraint.");
        Student s = studentMap.get(id);
        if (s != null) {
            s.setMarks(newMarks);
            saveChanges();
            return true;
        }
        return false;
    }

    public boolean updateAttendance(int id, int addedSessions) {
        Student s = studentMap.get(id);
        if (s != null) 
        {
            s.setAttendance(s.getAttendance() + addedSessions);
            saveChanges();
            return true;
        }
        return false;
    }

    public boolean removeStudent(int id) {
        if (studentMap.remove(id) != null) 
        {
            saveChanges();
            return true;
        }
        return false;
    }

    public void displayTopper() {
        studentMap.values().stream()
                .max((s1, s2) -> Double.compare(s1.getMarks(), s2.getMarks()))
                .ifPresentOrElse(
                        topper -> System.out.println("Topper Profile details -> " + topper),
                        () -> System.out.println("Empty inventory registries."));
    }

    public void saveChanges() {
        try {
            StudentFileService.savingToFile(studentMap);
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
