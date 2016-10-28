package joda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JodaTest {
	public static void main(String args[]) {
		//TODO
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		String hehe = dateFormat.format(cal.getTime());
		System.out.println(hehe);
	}


}