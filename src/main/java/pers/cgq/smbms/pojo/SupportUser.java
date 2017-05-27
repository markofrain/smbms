package pers.cgq.smbms.pojo;

import org.springframework.stereotype.Component;

@Component
public class SupportUser extends User {
	private String roleName;
	private int age;
	


	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
