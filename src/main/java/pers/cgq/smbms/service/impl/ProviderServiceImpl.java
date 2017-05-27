package pers.cgq.smbms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.cgq.smbms.dao.ProviderMapper;
import pers.cgq.smbms.dao.UserMapper;
import pers.cgq.smbms.pojo.Provider;
import pers.cgq.smbms.service.ProviderService;
import pers.cgq.smbms.tools.Constants;
@Service
public class ProviderServiceImpl implements ProviderService {
	@Autowired
	private ProviderMapper providerMapper;

	/**
	 * 获得总记录数
	 */
	public int getAllCount(String provCode,String provName) {
		return providerMapper.getAllCount(null==provCode||provCode==""?null:provCode, null==provName||provName==""?null:provName);
	}

	@Override
	public List<Provider> findProviderInfoPage(int pageSize, int currentPageNo, String provCode, String provName) {
		int agoInfoCount=(currentPageNo-1)*pageSize;
		return providerMapper.findProviderInfoPage(pageSize,agoInfoCount, null==provCode||provCode==""?null:provCode, null==provName||provName==""?null:provName);
	}

	/**
	 * 添加供应商
	 */
	public boolean addProvider(Provider provider) {
		
		if(providerMapper.insert(provider)>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 通过供应商id删除供应商信息
	 * @param provId 供应商ID
	 * @return -2,供应商不存在;
	 * 			x,供应商存在订单,返回订单数;
	 * 			0,供应商删除成功
	 * 			-1,供应商删除失败
	 * 将返回值设为1以下，以便于返回订单数的时候不会冲突
	 */
	public int delProviderById(int provId) {
		//通过供应商获得订单数量
		int count = providerMapper.getBillCountByProvId(provId);
		//查询供应商是否存在
		int isExist = providerMapper.isExistProv(provId);
		if(isExist==0){ //供应商不存在
			return -2;
		}else if(count>0){ //供应商存在订单,返回值为订单数
			return count;
		}else{
			if(providerMapper.deleteByPrimaryKey(provId)>0){
				return 0; //没有订单，删除成功
			}else{
				return -1; //没有订单，删除失败
			}
		}
	}

	/**
	 * 通过编号获得信息
	 */
	public Provider getProviderById(int provId) {
		return providerMapper.selectByPrimaryKey(provId);
	}

	/**
	 * 更新供应商信息
	 */
	public boolean updateProvider(Provider prov) {
		if(providerMapper.updateByPrimaryKey(prov)>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 获得供应商的id和name
	 */
	@Override
	public List<Provider> getIdAndName() {
		return providerMapper.getIdAndName();
	}

}
