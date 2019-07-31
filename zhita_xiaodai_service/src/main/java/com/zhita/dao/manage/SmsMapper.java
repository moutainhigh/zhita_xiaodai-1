package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.Shortmessage;
import com.zhita.model.manage.SmsSendRequest;

public interface SmsMapper {
	
	
	Integer AddSms(Shortmessage shor);
	
	
	List<Shortmessage> DayShortMessage(Shortmessage companyid);

	
	List<Shortmessage> AllDayShortMessage(Shortmessage shor);
	
	
	List<Shortmessage> DayTotalCount(Integer companyid);
	
	
	List<String> AllPhone(SmsSendRequest sm);
	
	
	List<Shortmessage> AllController(String collection_time);
	
	
	Integer SelectTimeSize(Shortmessage shor);
	
	
	List<String> AllRegist(Integer companyId);
}
