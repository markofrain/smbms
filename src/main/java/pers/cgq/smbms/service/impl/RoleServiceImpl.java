package pers.cgq.smbms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.cgq.smbms.dao.RoleMapper;
import pers.cgq.smbms.pojo.Role;
import pers.cgq.smbms.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper; 
	
	/**
	 * 所有角色
	 */
	public List<Role> findAllRole() {
		return roleMapper.findAllRole();
	}

}
