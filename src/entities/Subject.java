package entities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import enums.STATUS;
import exceptions.InvalidValueException;
/*
 * function "addDay()" 
 * function "editSubject()"
*/
public class Subject {
	private String name;
	private Double goal;
	private ArrayList<Day> days = new ArrayList<>();
	
	public Subject(String name, Double goal) {
		this.name = name;
		this.goal = goal;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Day> getDays() {
		return days;
	}
	public Double getGoal() {
		return goal;
	}
	public void setGoal(Double goal) {
		this.goal = goal;
	}

	public void addDay() throws ParseException, InvalidValueException { 
		//workin' on this:
		//we forgot to handle the scenario where the user enters an answer other than y or n
		System.out.println("\nADD DAY");
		Scanner sc = new Scanner(System.in);
		Day day = null;
		System.out.print("Studied? (y/n): ");
		String ch = sc.nextLine().substring(0,1);
		switch(ch) {
			case "y":
				day = new Day(STATUS.valueOf("yes"));
				break;
			case "n":
				day = new Day(STATUS.valueOf("no"));
				break;
			default:
				throw new InvalidValueException("Error: You entered an invalid answer!");
		}
		do {
			System.out.println("Press 'd' to change date (default is today) - 'c' to add a comment - 'f' to finish.");
			System.out.print("Choice: ");
			ch = sc.nextLine().substring(0,1);
			if (ch.equals("d")){
				System.out.print("New date (dd/mm/yyyy: ");
				String date = sc.nextLine();
				day.setDate(date);
			} else if (ch.equals("c")) {
				System.out.print("Comment: ");
				String comment = sc.nextLine();
				day.setComment(comment);
			}
		}
		while(ch.equals("f") == false);
			
		days.add(day);
		System.out.println("Day added: ");
		System.out.println(day.toString());
		System.out.println("Done.\n");
	}
	public double calcPercentage() { //involves something related to array iteration //done
		double y = 0;
		double n = 0;
		for(Day d : days) {
			if(d.getStatus().toString().equals("yes")) {
				y += 1;
			}else {
				n += 1;
			}
		}
		double p = (y/(y+n))*100;
		return p;
	}
	public String countDaysStudied() { // done
		int y = 0;
		int n = 0;
		for(Day d : days) {
			if(d.getStatus().toString().equals("yes")) {
				y += 1;
			}else {
				n += 1;
			}
		}
		return "Yes: " + y + " | No: " + n;
	}
	public boolean checkGoal() { //return true if within the goal, else false // done
		double percentage = calcPercentage();
		// if percentage <= goal return true, else false
		if(percentage < goal) {
			return false;
		} else {
			return true;
		}
	}
	public String checkGoalString() { //returns a string saying whether you're accomplishing the goal or not // done
		if(checkGoal() == false) {
			return "(not in goal)";
		}else {
			return "(within the goal!)";
		}
	}
	public String seeSubject() { //returns all info about the subject, mainly all the days added // done
		StringBuilder sb = new StringBuilder();
		System.out.println("\nSEE SUBJECT");
		sb.append("Info about: " + name + "\n");
		sb.append("Goal: " + goal + "\n");
		sb.append("Days: " + days.toString() + "\n");
		System.out.println("Done.\n");
		return sb.toString();
	}
	public void editSubject() { //done
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEDIT SUBJECT");
		System.out.println("Hello sir, what would you like to do? \n'n' to edit name - 'g' to edit goal - 'e' to exit.");
		String ch;
		do{
			System.out.print("Answer: ");
			ch = sc.nextLine();
			switch(ch) {
			case "n": //edit name
				System.out.print("Enter name: ");
				String name = sc.nextLine();
				this.name = name;
				break;
			case "g": //edit goal
				System.out.print("New goal: ");
				double goal = sc.nextDouble();
				sc.nextLine();
				this.goal = goal;
			}
		}
		while(ch.equals("e") == false);
		System.out.println("Done.\n");
	}
	
	public String toString() {
		return name + ", " + "goal: " + goal + "%";
	}
}
