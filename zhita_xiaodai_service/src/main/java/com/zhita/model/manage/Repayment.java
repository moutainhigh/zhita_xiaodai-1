package com.zhita.model.manage;

import java.math.BigDecimal;
import java.util.List;


//还款表
public class Repayment {
	
	private Integer id;//还款ID
	
	private Integer orderid;//订单ID
	
	private BigDecimal repaymentMoney;//还款金额
	
	private String repaymentDate;//还款时间
	
	private Integer repayment_Count;//还款订单数
	
	private String pipelinenumber;//流水号
	
	private String repaymentSource;//还款渠道 (支付方式 微信  支付宝  银行卡)
	
	private List<Integer> ids;
	
	private Integer repaymeny_collectiondata;//逾期还款率
	
	private Integer collectionMemberId;//催收员ID
	
	private Integer collection_count;//逾期还款笔数
	
	private BigDecimal repaymentSumMoney;//还款总金额
	
	private BigDecimal Collection_money;//逾期还款金额
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public BigDecimal getRepaymentMoney() {
		return repaymentMoney;
	}

	public void setRepaymentMoney(BigDecimal repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}

	public String getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public Integer getRepayment_Count() {
		return repayment_Count;
	}

	public void setRepayment_Count(Integer repayment_Count) {
		this.repayment_Count = repayment_Count;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Integer getCollectionMemberId() {
		return collectionMemberId;
	}

	public void setCollectionMemberId(Integer collectionMemberId) {
		this.collectionMemberId = collectionMemberId;
	}

	public Integer getCollection_count() {
		return collection_count;
	}

	public void setCollection_count(Integer collection_count) {
		this.collection_count = collection_count;
	}

	public BigDecimal getRepaymentSumMoney() {
		return repaymentSumMoney;
	}

	public void setRepaymentSumMoney(BigDecimal repaymentSumMoney) {
		this.repaymentSumMoney = repaymentSumMoney;
	}

	public Integer getRepaymeny_collectiondata() {
		return repaymeny_collectiondata;
	}

	public void setRepaymeny_collectiondata(Integer repaymeny_collectiondata) {
		this.repaymeny_collectiondata = repaymeny_collectiondata;
	}

	public BigDecimal getCollection_money() {
		return Collection_money;
	}

	public void setCollection_money(BigDecimal collection_money) {
		Collection_money = collection_money;
	}

	public String getPipelinenumber() {
		return pipelinenumber;
	}

	public void setPipelinenumber(String pipelinenumber) {
		this.pipelinenumber = pipelinenumber;
	}

	public String getRepaymentSource() {
		return repaymentSource;
	}

	public void setRepaymentSource(String repaymentSource) {
		this.repaymentSource = repaymentSource;
	}
	
	
	
}
