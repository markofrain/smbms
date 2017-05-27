package pers.cgq.smbms.service;

import java.util.List;

import pers.cgq.smbms.pojo.Role;

public interface RoleService {
	/**
	 * 查找所有角色
	 * @return
	 */
	List<Role> findAllRole();
}
