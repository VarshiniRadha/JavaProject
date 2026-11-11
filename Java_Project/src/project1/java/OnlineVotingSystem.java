package project1.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

class OnlineVoting{
	String id;
	String name;
	long votes;
	boolean isVoted;
	
	public OnlineVoting(String id, String name , long votes, boolean isVoted) {
        this.id = id;
        this.name = name;
        this.votes = votes;
        this.isVoted = false;
    }
	
}

class student{
	String id;
	String name;
	public student(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

class votingStatus{
	String id;
	boolean isVoted;
	public votingStatus(String id, boolean isVoted) {
        this.id = id;
        this.isVoted = isVoted;
    }
}

public class OnlineVotingSystem {
	
	static HashMap<String, String> student = new HashMap<>();
	static HashMap<String, Boolean> votingStatus = new HashMap<>(); 
	static HashMap<String, String> candidates = new HashMap<>(); 
    static HashMap<String, Integer> votesCount = new HashMap<>(); 
	static Scanner sc = new Scanner(System.in);
	
	
	public static void main(String[] args) {

		loadstudentsFromFile("src/project1/java/students.txt");
		loadcandidatesFromFile("src/project1/java/candidates.txt");
		System.out.println("Welcome to SSV Collage's Voting");
	    int choice;
	    do {
            try {
                System.out.println();
                System.out.println("1. Register/Login");
                System.out.println("2. Candidate List");
                System.out.println("3. Vote casting");
                System.out.println("4. View Total Votes");
                System.out.println("5. Display Result");
                System.out.println("6. Exit");
                System.out.print("\nEnter your choice : ");
                choice = sc.nextInt();
                sc.nextLine(); 
            
                switch(choice)
                {
                    case 1 -> Register();
                    case 2 -> CandidateList();
                    case 3 -> VoteCasting();
                    case 4 -> TotalVotes();
                    case 5 -> Result();
                    case 6 -> {
                        System.out.println("Thank you for using the system!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
            catch(InputMismatchException e){
                System.out.println("Entered another character sequence instead of numbers. Please try again!");
                sc.nextLine();
                choice = 0;
            }
        } while(choice != 6);
	}
		
	static void loadstudentsFromFile(String fileName) {
	        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) 
	        {
	            String line;
	            int loadedCount = 0;
				while ((line = br.readLine()) != null)
	            {
	                String[] data = line.split(",");
	                if (data.length == 2) {
	                    String studentId = data[0].trim();
	                    String studentName = data[1].trim();
	                    student.put(studentId, studentName);
	                    votingStatus.put(studentId, false); 
	                    loadedCount++;
	                }
	            }
	            System.out.println(loadedCount +" Students data loaded successfully");
	        } 
	        catch (IOException e) 
	        {	         
	            System.out.println("Failed to load students data " + e.getMessage());
	            System.exit(0);
	        }
	    }
	private static void loadcandidatesFromFile(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) 
		{
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) 
            {
                String[] data = line.split(",");
                if (data.length == 2) {
                    candidates.put(data[0].trim(), data[1].trim());
                    votesCount.put(data[0].trim(), 0); 
                    count++;
                }
            }
            System.out.println("System: " + count + " Candidates data loaded successfully.");
        } 
		catch (IOException e) 
		{
            System.out.println("Failed to load candidates file: " + e.getMessage());
            System.exit(0);
        }
		
	}

	static void Register() {
		System.out.print("Enter your student id : ");
		//sc.nextLine();
		String studentId = sc.nextLine().trim();
		if(student.containsKey(studentId))
		{
			System.out.println("Access Granted");
			System.out.println("Your registered name : "+ student.get(studentId));
			System.out.println("Voted Status: " + (votingStatus.get(studentId) ? "Already Voted " : "Eligible to Vote "));
		}
		else
		{
			System.out.println("Your ID is not registered");
		}
	}
	
	static void CandidateList() {
		System.out.println("Candidates for the collage presidential election are : ");
		candidates.forEach((id,name)-> System.out.println("ID : "+ id + " |  Name : "+ name));
	}
	
	static void VoteCasting() {
		System.out.print("Enter your Student ID to log in and vote: ");
		//sc.nextLine();
        String studentId = sc.nextLine().trim();
        
        if (!student.containsKey(studentId)) {
            System.out.println("Access Denied : Student ID '" + studentId + "' is not registered in the system.");
            return;
        }

        if (votingStatus.get(studentId)) {
            System.out.println("Access Denied : " + student.get(studentId) + ", you have already voted.");
            return;
        }

        System.out.println("\nWelcome, " + student.get(studentId) + "! Verification successful.");
        System.out.println("Please choose a candidate from the list below:");
        CandidateList();
        
        System.out.print("\nEnter the Candidate ID you want to vote for: ");
        String candidateId = sc.nextLine().trim();

        if (!candidates.containsKey(candidateId)) {
            System.out.println("Invalid Candidate Option Selected.");
            return;
        }
        
        int pastVotes = votesCount.get(candidateId);
        votesCount.put(candidateId, pastVotes + 1);
        votingStatus.put(studentId, true); 

        System.out.println("Thank you, " + student.get(studentId) + "! Your vote for " + candidates.get(candidateId) + " has been successfully recorded.");
	}
	
	static void TotalVotes() {
		System.out.println("\n--- Live Vote Dashboard ---");
        for (Map.Entry<String, Integer> entry : votesCount.entrySet()) {
            String candidateName = candidates.get(entry.getKey());
            System.out.println(candidateName + " (" + entry.getKey() + "): " + entry.getValue() + " votes");
        }
	}
	
    static void Result() {
        System.out.println("Final Election Summary");
        boolean votesPolled = votesCount.values().stream().anyMatch(v -> v > 0);
        if (!votesPolled) 
        {
            System.out.println("No results available. Total votes cast is currently 0.");
            return;
        }
        Map.Entry<String, Integer> winner = votesCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get();
        String winnerName = candidates.get(winner.getKey());

        Map.Entry<String, Integer> runner = votesCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) 
                .skip(1)                                                          
                .findFirst()                                                      
                .get();
        String runnerName = candidates.get(runner.getKey());

        System.out.println("Winner: " + winnerName + " (" + winner.getKey() + ") with " + winner.getValue() + " votes!");
        System.out.println("Runner: " + runnerName + " (" + runner.getKey() + ") with " + runner.getValue() + " votes!");
        String outputFilePath = "src/project1/java/election_report.txt";

        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(outputFilePath))) {
            writer.write("========================================================================================================\n");
            writer.write("          SSV COLLEGE OFFICIAL ELECTION REPORT      \n");
            writer.write("========================================================================================================\n\n");
            
            writer.write("🏆 ELECTION WINNER:\n");
            writer.write("Candidate Name : " + winnerName + "\n");
            writer.write("Candidate ID   : " + winner.getKey() + "\n");
            writer.write("Total Votes    : " + winner.getValue() + " votes\n\n");
            
            writer.write("🥈 ELECTION RUNNER-UP:\n");
            writer.write("Candidate Name : " + runnerName + "\n");
            writer.write("Candidate ID   : " + runner.getKey() + "\n");
            writer.write("Total Votes    : " + runner.getValue() + " votes\n\n\n");
            
            
            writer.write("📊 MASTER INVETORY LIVE BREAKDOWN:\n");
            writer.write("----------------------------------------------------\n");
            for (Map.Entry<String, Integer> entry : votesCount.entrySet()) {
                String name = candidates.get(entry.getKey());
                writer.write("• " + name + " (" + entry.getKey() + ") -> Polled: " + entry.getValue() + " votes\n");
            }
            writer.write("----------------------------------------------------\n");
            
        } 
        catch (IOException e) 
        {
            System.out.println("Could not write data to the target file path: " + e.getMessage());
        }
	}
}
