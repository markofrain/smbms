package pers.cgq.smbms.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.cgq.smbms.dao.UserMapper;
import pers.cgq.smbms.pojo.SupportUser;
import pers.cgq.smbms.pojo.User;
import pers.cgq.smbms.service.UserService;
@Service
public class UserServceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 验证登陆
	 * @return
	 */
	public User login(String userCode,String userPassword){
		User user=null;
		user=userMapper.login(userCode, userPassword);
		/*匹配密码*/
		if(null!=user){
			if(!user.getUserpassword().equals(userPassword)){
				user=null;
			}
		}
		return user;
	}

	/**
	 * 分页(non-Javadoc)
	 * @see UserService#getPage(int, int)
	 * 
	 */
	public List<SupportUser> getPage(int currentPageNo, int pageSize,String queryname,int queryUserRole,int id) {
		int start=(currentPageNo-1)*pageSize;
		return userMapper.getPage(start, pageSize,queryname,queryUserRole,id);
	}

	/**
	 * 获得用户个数
	 */
	public int getTotalCount(String userName,int userRole) {	
		return userMapper.getTotalCount(null==userName||userName==""?null:userName,userRole);
	}

	/**
	 * 检查此用户名有几个
	 */
	public int checkUserCode(String userCode) {
		return userMapper.checkUserCode(userCode);
	}

	/**
	 * 天价用户
	 */
	public boolean addUser(User user,String[] paths) {
		int count=userMapper.insert(user);
		if(count>0){
			return true;
		}else{
			File file=null;
			//删除文件
			if(paths[0]!=null&&!paths[0].equals("")){//不为空，有值
				file=new File(paths[0]);
				file.delete();
			}else if(paths[1]!=null&&!paths[1].equals("")){
				file=new File(paths[0]);
				file.delete();
			}
			return false;
		}
	}

	/**
	 * 通过id获得用户
	 */
	public User getUserById(int id) {
		return userMapper.selectByPrimaryKey(new Long(id));
	}

	/**
	 * 通过id删除用户
	 */
	public boolean deleteUserById(Long id) {
		int count=userMapper.deleteByPrimaryKey(id);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 通过id更新用户
	 */
	public boolean updateUser(User user) {
		int count=userMapper.updateByPrimaryKeySelective(user);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 通过用户编码查找id
	 */
	public int getIdByuserCode(String userCode) {
		if(userCode!=null){
			return userMapper.selectIdByuserCode(userCode);
		}else{
			return 0;
		}
	}
	
	/**
	 * 在删除user用户之前删除两张照片
	 * @param idFilePath 用户照片
	 * @param workFilePath 工作照片
	 * @param userId 用户编号
	 * @return
	 */
	public String delUserAgoDelFile(String idFilePath,String workFilePath,int userId){
		/*String idfilePath=user.getIdpicpath();
		String workFilePath=user.getWorkpicpath();*/
		System.out.println("Delete:::"+idFilePath);
		System.out.println("Delete:::"+workFilePath);
		if((idFilePath==null||idFilePath.equals(""))&&
				(workFilePath==null||workFilePath.equals(""))){//没有文件，直接删除
			if(deleteUserById(Long.valueOf(userId))){//删除成功
				return "true";
			}
		}else{//有文件
			//但不知道哪个有哪个没有
			if(null!=idFilePath){
				File file=new File(idFilePath);
				if(file.exists()){//判断是否存在文件
					if(file.delete()){//删除
						//删除成功，删除信息
						if(deleteUserById(Long.valueOf(userId))){
							return "true";
						}else{
							return "false";
						}
					}
				}else{
					//删除成功，删除信息
					if(deleteUserById(Long.valueOf(userId))){
						return "true";
					}else{
						return "false";
					}
				}
			}
			return delWorkFilePath(workFilePath,userId);
		
		}
		return "true";
	}
	
	public String delWorkFilePath(String workFilePath,int userId){
		//判断第二个
		if(null!=workFilePath){
			File file2=new File(workFilePath);
			if(file2.exists()){
				if(file2.delete()){//删除
					//删除成功，删除信息
					if(deleteUserById(Long.valueOf(userId))){
						return "true";
					}else{
						return "false";
					}
				}
			}else{
				//删除成功，删除信息
				if(deleteUserById(Long.valueOf(userId))){
					return "true";
				}else{
					return "false";
				}
			}
		}
		return "true";
	}
	
	
	


}
