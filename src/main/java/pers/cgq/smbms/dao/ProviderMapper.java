package pers.cgq.smbms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import pers.cgq.smbms.pojo.Provider;
@Repository
public interface ProviderMapper {
	
	/**
	 * 通过id删除供应商
	 * @param id 供应商id
	 * @return
	 */
    int deleteByPrimaryKey(int id);
    /**
     * 添加供应商
     * @param record
     * @return
     */
    int insert(Provider provider);
    
    int insertSelective(Provider record);
    /**
     * 根据供应商编号查询信息
     * @param id
     * @return
     */
    Provider selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(Provider record);
    /**
     * 根据供应商编号分更改信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Provider prov);
    
    /**
     * 根据
     * @param pageSize 页面大小
     * @param agoInfoCount 之前的数量
     * @param provCode 供应商编码
     * @param provName 供应商名称
     * 
     * @return
     */
    List<Provider> findProviderInfoPage(@Param("pageSize") int pageSize,
                                        @Param("agoInfoCount") int agoInfoCount,
                                        @Param("provCode") String provCode,
                                        @Param("provName") String provName);
    /**
     * 获得总记录数
     * @param provCode 供应商编码
     * @param provName 供应商名称
     * @return
     */
    int getAllCount(@Param("provCode") String provCode, @Param("provName") String provName);
    /**
     * 通过供应商编号查询其所有订单
     * @param provId
     * @return
     */
    int getBillCountByProvId(int provId);
    /**
     * 通过供应商编号查询供应商是否存在
     * @param provId
     * @return
     */
    int isExistProv(int provId);
    
    /**
     * 获得所有供应商的id和名称
     * @return
     */
    List<Provider> getIdAndName();
    
}