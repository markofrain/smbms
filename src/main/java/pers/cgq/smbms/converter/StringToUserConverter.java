package pers.cgq.smbms.converter;

import org.springframework.core.convert.converter.Converter;

import pers.cgq.smbms.pojo.User;

public class StringToUserConverter implements Converter<String, User> {

	/**
	 * 将String类型转换成User类型
	 */
	public User convert(String str) {
		User user = new User();
		if (null != str) {
			String[] strs = str.split("-");
			if (strs != null && strs.length == 2) {
				user.setUsercode(strs[0]);
				user.setUserpassword(strs[1]);
			}
		}
		return user;
	}

}
