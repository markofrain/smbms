package pers.cgq.smbms.tools;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
/**
 * GenderTypeHandler，将性别从数据库中数字(1,2)转换成男女
 * @author 光奇
 *
 */
public class GenderTypeHandler extends BaseTypeHandler<String> {

	/**
	 * 获取结果后,进行数据更改,通过列名
	 */
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		if(rs!=null){
			if(rs.getInt(columnName)==1){//在rs中通过列名获得这个值是否为1
				return "女";
			}else if(rs.getInt(columnName)==1){
				return "男";
			}
		}
		return rs.getString(columnName);
	}

	/**
	 * 获取结果后,进行数据更改,通过列位置
	 */
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		if(rs!=null){
			if(rs.getInt(columnIndex)==1){//在rs中通过列名获得这个值是否为1
				return "女";
			}else if(rs.getInt(columnIndex)==1){
				return "男";
			}
		}
		return rs.getString(columnIndex);
	}

	/**
	 * 执行存储过程时
	 */
	public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 在查询结果之前设置值
	 */
	public void setNonNullParameter(PreparedStatement ps, int i, String paramter, JdbcType jdbcType) throws SQLException {
		if(paramter!=null){
			if(paramter.equals("女")){//当传入的参数是女时，为其sql对应参数设置为1
				ps.setInt(i, 1);
			}else if(paramter.equals("男")){
				ps.setInt(i, 1);
			}
		}
		
	}

}
