package pers.cgq.smbms.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String,Date> {
	private String datePatten;
	
	public StringToDateConverter(String datePatten){
		System.out.println("StringToDateConverter Convert"+datePatten);
		this.datePatten=datePatten;
	}
	
	/**
	 * 转换方法
	 */
	public Date convert(String s) {
		Date date=null;
		try {
			date=new SimpleDateFormat(datePatten).parse(s);
			System.out.println("StringToDateConverter converter date:"+date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
