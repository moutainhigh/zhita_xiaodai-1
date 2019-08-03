package com.zhita.service.manage.chanpayQuickPay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.StatisticsDao;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Deferred;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Payment_record;
import com.zhita.model.manage.Repayment;
import com.zhita.util.Timestamps;


@Service
public class Chanpayserviceimp implements Chanpayservice{
	
	
	@Autowired
	private StatisticsDao stdao;

	
	/**
	 * 添加银行卡
	 */
	@Override
	public Integer AddBankcard(Bankcard bank) {
		return stdao.AddBankcard(bank);
	}

	/**
	 * 查询银行卡ID
	 */
	@Override
	public Integer SelectTrxId(Bankcard bank) {
		return stdao.SelectTrxId(bank);
	}

	
	/**
	 * 修改认证状态
	 */
	@Override
	public Integer UpdateChanpay(Integer id) {
		return stdao.UpdateStatu(id);
	}

	
	/**
	 * 添加还款记录
	 */
	@Override
	public Integer AddRepayment(Repayment repay) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		try {
			repay.setRepaymentDate(Timestamps.dateToStamp(sim.format(new Date())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		repay.setPaymentbtiao("畅捷支付");
		repay.setOrderid(stdao.SelectOrderId(repay.getOrderNumber()));
		System.out.println(repay.getOrderid());
		return stdao.AddRepay(repay);
	}

	
	/**
	 * 修改订单状态
	 */
	@Override
	public Integer UpdateOrders(Orders ord) {
		ord.setId(stdao.SelectOrderId(ord.getOrderNumber()));//根据编号查询订单ID
		Integer id = stdao.UpdateOrders(ord);//
		if(id != null){
			Integer delaytimes = 0;
			ord.setChenggNum(delaytimes);
			stdao.UpdateUser(ord);
		}else{
			return 0;
		}
		return 200;
	}
	
	
	
	/**
	 * 修改延期状态
	 */
	@Override
	public Integer UpdateDefeOrders(Orders ord) {
		Integer num = stdao.SelectUserdelayTimes(ord);
		Integer delaytimes = num+1;
		ord.setChenggNum(delaytimes);
		return stdao.UpdateUser(ord);
	}

	@Override
	public Integer AddPayment_record(Payment_record pay) {
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pay.setOrderId(stdao.SelectOrderId(pay.getOrderNumber()));
		pay.setRemittanceTime(sim.format(new Date()));
		pay.setProfessionalWork("放款");
		pay.setThirdparty_id(1);
		pay.setPaymentbtiao("畅捷支付");
		return stdao.AddPaymentRecord(pay);
	}

	@Override
	public Integer AddDeferred(Deferred defe) {
		defe.setDeleted("0");
		defe.setOrderid(stdao.SelectOrderId(defe.getOrderNumber()));
		return stdao.AddDeferred(defe);
	}

	@Override
	public Integer UpdatePayment(Payment_record pay) {
		return stdao.UpdatePaymentrecord(pay);
	}

	@Override
	public Bankcard SelectBank(Bankcard userId) {
		return stdao.SelectBanl(userId);
	}
	
	

	@Override
	public Orders OneOrders(Orders or) {
		return stdao.OneOrders(or);
	}

	@Override
	public Integer DeleteChan(Integer userId) {
		return stdao.DeleteChan(userId);
	}

	@Override
	public Integer SelectUserId(Integer userId) {
		return stdao.SelectUserId(userId);
	}
	
	
	
	
	

}
