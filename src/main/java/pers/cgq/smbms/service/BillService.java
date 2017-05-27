package pers.cgq.smbms.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import pers.cgq.smbms.pojo.Bill;
import pers.cgq.smbms.pojo.SupportBill;
import pers.cgq.smbms.pojo.SupportUser;

public interface BillService {
	/**
	 * 获得所有数量
	 * @return
	 */
	int getAllCount(String productName, String isPayment, String provId);
	
	/**
     * 分页获得信息
     * @param pageSize 页面大小
     * @param currentNo 当前第几页
     * @param isPayment 是否付款
     * @param productName 商品名称
     * @param provId 供应商id
     * @return
     */
    List<SupportBill> findBillInfoPage(int pageSize,
                                       int currentNo,
                                       String isPayment,
                                       String productName,
                                       String provId);
    /**
     * 添加bill
     * @param bill
     * @return
     */
    boolean addBill(Bill bill);
    /**
     * 通过id删除bill
     * @param billId
     * @return
     * 		1 删除成功
     * 		-1 删除失败
     * 		0 不存在
     */
    int deleteBill(Integer billId);
    
    /**
     * 获得bill
     * @param billId id
     * @return
     */
    SupportBill getBill(Integer billId);
    /**
     * 更新bill
     * @param bill
     * @return
     */
    boolean updateBill(Bill bill);
    
}
