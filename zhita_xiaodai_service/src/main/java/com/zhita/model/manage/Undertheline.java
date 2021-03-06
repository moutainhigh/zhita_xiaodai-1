package com.zhita.model.manage;

import java.math.BigDecimal;


//线下调账
public class Undertheline {
	
	private Integer id;
	
	private String underthe_time;//调账时间
	
	private Integer finance_id;//财务操作人ID
	
	private String project_name;//项目名
	
	private String repayment;//渠道
	
	private BigDecimal income;//收入
	
	private BigDecimal expenditure;//支出
	
	private String repaymentnumber;//还款流水号
	
	private String remarks;//备注
	
	private String account;
	
	private Integer orderId;
	
	private String name;
	
	private String phone;
	
	private BigDecimal makeLoans;
	
	private String orderNumber;
	
	
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getMakeLoans() {
		return makeLoans;
	}

	public void setMakeLoans(BigDecimal makeLoans) {
		this.makeLoans = makeLoans;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnderthe_time() {
		return underthe_time;
	}

	public void setUnderthe_time(String underthe_time) {
		this.underthe_time = underthe_time;
	}

	public Integer getFinance_id() {
		return finance_id;
	}

	public void setFinance_id(Integer finance_id) {
		this.finance_id = finance_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getRepayment() {
		return repayment;
	}

	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
	}

	public String getRepaymentnumber() {
		return repaymentnumber;
	}

	public void setRepaymentnumber(String repaymentnumber) {
		this.repaymentnumber = repaymentnumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
