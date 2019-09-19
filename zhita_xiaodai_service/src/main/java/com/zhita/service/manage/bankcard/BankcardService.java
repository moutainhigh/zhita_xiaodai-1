package com.zhita.service.manage.bankcard;

import java.util.Map;

import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.Orders;

public interface BankcardService {
	
	Map<String, Object> AddBankcard(Bankcard bank);
	
	
	Map<String, Object> AddOrders(Orders orde);


	Map<String, Object> SelectBankCard(Integer companyId);
	
	
	Integer GetBanktype(String bankcardTypeName);
	
	

}
