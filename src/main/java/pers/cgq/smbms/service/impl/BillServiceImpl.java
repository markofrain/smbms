package pers.cgq.smbms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.cgq.smbms.dao.BillMapper;
import pers.cgq.smbms.pojo.Bill;
import pers.cgq.smbms.pojo.SupportBill;
import pers.cgq.smbms.pojo.SupportUser;
import pers.cgq.smbms.service.BillService;
@Service
public class BillServiceImpl implements BillService {
	
	@Autowired
	private BillMapper billMapper;

	/**
	 * 获得所有记录数
	 */
	public int getAllCount(String productName,String isPayment,String provId) {
		
		return billMapper.getAllCount(null==productName||productName.equals("")?null:productName,
									null==isPayment||isPayment.equals("")||isPayment.equals("0")?null:Integer.valueOf(isPayment), 
									null==provId||provId.equals("")||provId.equals("0")?null:Integer.valueOf(provId));
	}

	/**
     * 分页获得信息
     * @param pageSize 页面大小
     * @param currentNo 当前第几页
     * @param isPayment 是否付款
     * @param productName 商品名称
     * @param provId 供应商id
     * @return
     */
	public List<SupportBill> findBillInfoPage(int pageSize, int currentNo, String isPayment, String productName,
			String provId) {
		int agoInfoCount=(currentNo-1)*pageSize;
		return billMapper.findBillInfoPage(pageSize, agoInfoCount, 
								null==isPayment||isPayment.equals("")||isPayment.equals("0")?null:Integer.valueOf(isPayment),
								null==productName||productName.equals("")?null:productName, 
								null==provId||provId.equals("")||provId.equals("0")?null:Integer.valueOf(provId));
	}

	@Override
	public boolean addBill(Bill bill) {
		if(billMapper.insert(bill)>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 通过id删除bill
	 */
	public int deleteBill(Integer billId) {
		int count = billMapper.checkExist(billId);
		if(count>0){//存在
			if(billMapper.deleteByPrimaryKey(billId)>0){
				return 1;//删除成功
			}else{
				return -1;//删除失败
			}
		}else{
			return 0;//不存在
		}
	}

	@Override
	public SupportBill getBill(Integer billId) {
		return billMapper.selectByPrimaryKey(billId);
	}

	@Override
	public boolean updateBill(Bill bill) {
		int count = billMapper.updateByPrimaryKey(bill);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	
}
