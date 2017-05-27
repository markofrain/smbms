package pers.cgq.smbms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sun.org.glassfish.gmbal.ParameterNames;

import pers.cgq.smbms.pojo.SupportUser;
import pers.cgq.smbms.pojo.User;

@Repository
public interface UserMapper {
	/**
	 * 通过id删除用户
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);
	/**
	 *添加用户 
	 */
	int insert(User user);

	int insertSelective(User record);
	/**
	 * 通过Id查询用户
	 * @param id
	 * @return
	 */
	User selectByPrimaryKey(Long id);
	
	
	
	/**
	 * 通过用户编码查找ID
	 * @param user
	 * @return
	 */
	int selectIdByuserCode(String userCode);
	
	int updateByPrimaryKeySelective(User user);

//	int updateByPrimaryKey(User record);

	/**
	 * 登录
	 * 
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	User login(@Param("userCode") String userCode, @Param("userPassword") String userPassword);

	/**
	 * 分页
	 * 
	 * @param currentPageNo
	 * @param pageSize
	 * @param queryname 查询的名称，非必需-
	 * @param queryUserRole 查询的角色编号，非必需
	 * @param id用户编号，非必需
	 * @return
	 */
	List<SupportUser> getPage(@Param("currentPageNo") int currentPageNo,
                              @Param("pageSize") int pageSize,
                              @Param("queryName") String queryname,
                              @Param("queryUserRole") int queryUserRole,
                              @Param("id") int id);

	/**
	 * 获得用户个数
	 * @param userName 用户名，非必需
	 * @param UserRole 非必需
	 * @return
	 */
	int getTotalCount(@Param("userName") String userName, @Param("UserRole") int UserRole);
	/**
	 * 检查userCode是否重复，查看有0个还是1个
	 * @param userCode
	 * @return
	 */
	int checkUserCode(String userCode);

}