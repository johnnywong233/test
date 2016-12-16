package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Testtttt {

	public static void main(String[] args) throws ParseException {
			String string = "2016-08-08 00:00:00 ";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(string);
			System.out.println(date);
	}

}
