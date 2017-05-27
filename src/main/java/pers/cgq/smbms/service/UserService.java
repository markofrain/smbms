package pers.cgq.smbms.service;

import java.util.List;

import pers.cgq.smbms.pojo.SupportUser;
import pers.cgq.smbms.pojo.User;

public interface UserService {
	
	//登录
	User login(String userCode, String userPassword);
	/**
	 * 获得当前当前页的信息
	 * @param currentPageNo
	 * @param pageSize
	 * @param queryname
	 * @param queryUserRole
	 * @param id
	 * @return
	 */
	List<SupportUser> getPage(int currentPageNo, int pageSize, String queryname, int queryUserRole, int id);
	/**
	 * 获得记录总数
	 * @param userName 用户名
	 * @param userRole 用户角色编码
	 * @return
	 */
	int getTotalCount(String userName, int userRole);
	/*检查当前用户名有几个*/
	int checkUserCode(String userCode);
	/*添加用户*/
	boolean addUser(User user, String[] paths);
	/**
	 * 通过id获得用户信息
	 * @param id
	 * @return
	 */
	User getUserById(int id);
	/**
	 * 通过id删除用户信息
	 * @param id
	 * @return
	 */
	boolean deleteUserById(Long id);
	/**
	 * 通过id更新用户
	 * @param id
	 * @return
	 */
	boolean updateUser(User user);
	/**
	 * 通过userCode查找ID
	 * @param userCode
	 * @return
	 */
	int getIdByuserCode(String userCode);
	
	
	/**
	 * 在删除user用户之前删除两张照片
	 * @param idFilePath 用户照片
	 * @param workFilePath 工作照片
	 * @param userId 用户编号
	 * @return
	 */
	public String delUserAgoDelFile(String idFilePath, String workFilePath, int userId);
	

}
