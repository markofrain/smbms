package pers.cgq.smbms.service;

import java.util.List;

import pers.cgq.smbms.pojo.Provider;

public interface ProviderService {
	
	/**
	 * 获得所有信息数量
	 * @param provCode 供应商编码
	 * @param provName 供应商名称
	 * @return
	 */
	int getAllCount(String provCode, String provName);
	/**
	 * 分页显示
	 * @param pageSize
	 * @param currentPageNo
	 * @param provCode
	 * @param provName
	 * @return
	 */
	List<Provider> findProviderInfoPage(int pageSize, int currentPageNo, String provCode, String provName);
	/**
	 * 添加供应商
	 * @param provider
	 * @return
	 */
	boolean addProvider(Provider provider);
	/**
	 * 通过供应商id删除供应商信息
	 * @param provId 供应商ID
	 * @return -2,供应商不存在;
	 * 			x,供应商存在订单,返回订单数;
	 * 			0,供应商删除成功
	 * 			-1,供应商删除失败
	 * 将返回值设为1以下，以便于返回订单数的时候不会冲突
	 */
	int delProviderById(int provId);
	/**
	 * 通过供应商编号查找信息
	 * @param provId 供应商编号
	 * @return
	 */
	Provider getProviderById(int provId);
	/**
	 * 更新供应商信息
	 * @param prov
	 * @return
	 */
	boolean updateProvider(Provider prov);
	
	/**
	 * 获得供应商的id和name
	 * @return
	 */
	List<Provider> getIdAndName();
}
