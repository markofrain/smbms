package pers.cgq.smbms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import pers.cgq.smbms.pojo.Bill;
import pers.cgq.smbms.pojo.SupportBill;
import pers.cgq.smbms.pojo.SupportUser;
@Repository
public interface BillMapper {
	
	/**
	 * 删除Bill
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);
    /**
     * 添加bill
     * @param record
     * @return
     */
    int insert(Bill bill);

    int insertSelective(Bill record);
    /**
     * 查询信息
     * @param id
     * @return
     */
    SupportBill selectByPrimaryKey(Integer id);
    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Bill record);
    
    /**
     * 分页获得信息
     * @param pageSize 页面大小
     * @param agoInfoCount 以前的页数
     * @param isPayment 是否付款
     * @param productName 商品名称
     * @param provId 供应商id
     * @return
     */
    List<SupportBill> findBillInfoPage(@Param("pageSize") int pageSize,
                                       @Param("agoInfoCount") int agoInfoCount,
                                       @Param("isPayment") Integer isPayment,
                                       @Param("productName") String productName,
                                       @Param("provId") Integer provId);
    
    /**
     * 获得总记录数
     * @param productName 商品名
     * @param isPayment 是否付款
     * @param provId 供应商编号
     * @return
     */
    int getAllCount(@Param("productName") String productName,
                    @Param("isPayment") Integer isPayment,
                    @Param("provId") Integer provId);
    
    /**
     * 检查bill是否存在
     * @param billId
     * @return
     */
    int checkExist(int billId);
    
    
}