package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import entities.Subject;
import exceptions.InvalidValueException;
/* COOL IDEA: 
 * It would be interesting if the program used a text file as a database, updating
 * this file at each new entry or alteration made.
 * This would implicate the necessity of reading and understanding the file, that is,
 * analysing the pattern and converting to data ready to be used by the program. When
 * the program needs to save the data, after the necessary changes, then it converts 
 * the date it has and saves in the same file. 
 * I'm not sure if this explanation makes sense or even if it's understandable
 * but in my mind it is.*/

public class Program {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ParseException {
		ArrayList<Subject> subjects = new ArrayList<>();
		mainScreen(subjects);
	}
	
	public static void mainScreen(ArrayList<Subject> subjects) throws ParseException {
		Scanner sc = new Scanner(System.in);
		System.out.println(":::: World's most incredible student helper ::::");
		System.out.println("Press 'a' to add a new subject - 'd' to add a day to a subject - 's' to see the statistics."
						+ "\nPress 'e' to edit a subject - 'x' to see all info of a subject - 'l' to list all subjects"
						+ "\nPress 'b' to save it into a pseudo-database");
		while(true) {
			System.out.print("Answer? ");
			String ch = sc.nextLine();
			switch(ch) {
			case "a": //adds a subject // done
				addSubject(subjects);
				break;
				
			case "d": //adds a new day to a subject //done
				try {
					System.out.print("Which subject? ");
					String sub = sc.nextLine().toLowerCase();
					int index = subjectExists(subjects, sub);
					//acessa o obj na pos retornada pelo subjectExists() e chama o metodo addDay() dele;
					subjects.get(index).addDay();
					System.out.println("Day added");
					break;
				} catch (IndexOutOfBoundsException i) {
					System.out.println("Error! This subject doesn't exist.");
					break;
				} catch (InvalidValueException i) {
					System.out.println(i.getMessage());
					i.printStackTrace();
					break;
				}
				
			case "s": //shows the statistics //done.
				statistics(subjects);
				break;
				
			case "e"://Press 'e' to edit a subject // done.
				try {
					System.out.print("Which subject? ");
					String sub = sc.nextLine();
					int index = subjectExists(subjects, sub);
					subjects.get(index).editSubject();
					break;
				} catch (IndexOutOfBoundsException i) {
					System.out.println("Error! This subject doesn't exist.");
					break;
				}
				
			case "x"://sees all info of a subject //fixed
				try {
					System.out.print("Which subject? ");
					String sub = sc.nextLine();
					int index = subjectExists(subjects, sub);
					System.out.println(subjects.get(index).seeSubject());
					break;
				} catch (IndexOutOfBoundsException i) {
					System.out.println("Error! This subject doesn't exist.");
					break;
				}
			
			case "l"://done
				listAll(subjects);
				break;
			case "b":
				exportToDB(subjects);
			}
		}
	}
	public static void listAll(ArrayList<Subject> subjects) {//done
		System.out.println("\nALL SUBJECTS:");
		StringBuilder sb = new StringBuilder();
		for (Subject s : subjects) {
			sb.append(s.toString() + ".\n");
		}
		sb.append("\nDone.\n");
		System.out.println(sb.toString());
	}
	public static int subjectExists(ArrayList<Subject> subjects, String sub) throws ParseException {//done
		int subject = -1; //-1 is doesn't exist
		for(Subject s : subjects) {
			if(s.getName().toLowerCase().equals(sub)) {
				subject = subjects.indexOf(s);
			}
		}
		return subject;
	}
	public static void addSubject(ArrayList<Subject> subjects) throws ParseException { //done
		System.out.println("\nADD SUBJECT");
		System.out.print("Subject name: "); 
		String name = sc.nextLine();
		
		//checks if a subject with the same name already exists, there can't be subjects with the same name
		while(subjectExists(subjects, name) != -1) { 
			System.out.println("Name already used! Type another one!");
			System.out.print("Subject name: "); 
			name = sc.nextLine();
		}
		
		System.out.print("Goal (Ex: 70 or 65,5): ");
		Double goal = sc.nextDouble();
		sc.nextLine();
		Subject sub = new Subject(name, goal);
		subjects.add(sub);
		JOptionPane.showMessageDialog(null, "Done");
		System.out.println("Done.\n");
	}
	public static void statistics(ArrayList<Subject> subjects) { // done.
		/* Will be shown for each subject.
		 * - Percentage of attendance and the days studied and not studied. -- calcPercentage()
		 * - Whether it is or it isn't within the previous defined goal. -- checkGoal()
		 */
		System.out.println(showStatistics(subjects));
		System.out.println("Do you want to save them to a file? y/n");
		char ch = sc.nextLine().charAt(0);
		if(ch == 'y') {
			StatsToFile(subjects);
		}
		JOptionPane.showMessageDialog(null, "Done");
	}
	public static String showStatistics(ArrayList<Subject> subjects) {//done
		StringBuilder sb = new StringBuilder();
		System.out.println("\nSTATISTICS");
		for(Subject s : subjects) {
			sb.append(s.getName() + "\n");
			sb.append("Attendance: " + String.format("%.2f", s.calcPercentage()) + "% " + String.format("%s\n", s.checkGoalString()));
			sb.append(s.countDaysStudied());
			sb.append("\n-\n");
		}
		return sb.toString();
	}
	public static void StatsToFile(ArrayList<Subject> subjects) {
		//It saves the data returned by showStatistics() in a file in the Desktop //done
		File path = new File("C:\\Users\\Thiago\\Desktop\\out.txt");
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
			bw.write(showStatistics(subjects));
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void exportToDB(ArrayList<Subject> subjects) {
	/* This function saves all the data into a .txt file to use it as a database.
	 * Should be understood as "data" all subjects, the days they contain and the contents
	 * of these days (date, comment and status).
	 * This obviously would require a well-defined pattern to format the generated output,
	 * both for writing and reading, because we want the program to be able to read the content
	 * it saved itself.*/	
		File path = new File("C:\\Users\\Thiago\\Desktop");
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(path+"\\text.txt"))){
			StringBuilder sb = new StringBuilder();
			
			System.out.println("EXPORT TO DB");
			for(Subject s : subjects) {
				sb.append(s.seeSubject() + "\n");	
			}
			
			bw.write(sb.toString());
			
			System.out.println("Done.");
		}
		catch(IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
}
