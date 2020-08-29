package entities;

import java.text.ParseException;
import java.util.Date;
import enums.STATUS;
import util.Sdf;

public class Day {
	private Date date; //if I need to use calendar, will change to it
	private STATUS status;
	private String comment;
	
	public Day(STATUS status) {
		this.date = new Date();
		this.status = status;
	}
	public Day(Date date, STATUS status) {
		this.date = date;
		this.status = status;
	}
	public Day(Date date, STATUS status, String comment) {
		this.date = date;
		this.status = status;
		this.comment = comment;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(String d) throws ParseException {
		this.date = Sdf.parse(d);
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String toString() {
		return "Date: " + Sdf.format(date) + " Status: " + status.toString() + " Comment: " + comment;
	}

}
