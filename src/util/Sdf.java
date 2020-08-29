package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sdf {
	static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static final Date parse(String date) throws ParseException { //receives a string and formats it to a date
		return sdf.parse(date);
	}
	public static final String format(Date date) { //receives a date and converts it to a string
		return sdf.format(date);
	}
}
