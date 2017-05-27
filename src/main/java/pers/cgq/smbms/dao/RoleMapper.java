package pers.cgq.smbms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import pers.cgq.smbms.pojo.Role;
@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    /**
     * 查找所有角色
     * @return
     */
    List<Role> findAllRole();
}