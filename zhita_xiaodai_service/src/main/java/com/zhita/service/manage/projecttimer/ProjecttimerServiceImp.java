package com.zhita.service.manage.projecttimer;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhita.dao.manage.ProjecttimerMapper;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.OverdueClass;
import com.zhita.model.manage.OverdueSettings;
import com.zhita.util.DateListUtil;
import com.zhita.util.Timestamps;

@Service
public class ProjecttimerServiceImp implements IntProjecttimerService{
	@Autowired
	private ProjecttimerMapper projecttimerMapper;
	
	//后台管理----查询订单表     所有逾期中的订单(定时任务     控制逾期)
	@Transactional
	public void queryAllover(){
		Integer companyId = 3;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = sdf.format(d);//当天时间（年月日时分秒格式）
		
		List<Orders> list=projecttimerMapper.queryAllover(companyId);
		for (int i = 0; i < list.size(); i++) {
			String shouldReturnTime = Timestamps.stampToDate(list.get(i).getShouldReturnTime());//应还时间（String类型）
			try {
				if(sdf.parse(date).getTime()>sdf.parse(shouldReturnTime).getTime()){//转成long类型比较
					System.out.println("当前时间大于应还时间");//test完成要删除
					Integer orderid = list.get(i).getId();
					projecttimerMapper.updateover(orderid);//修改订单状态为逾期
					
					Date shoulddate = sdf.parse(shouldReturnTime);//应还时间（date类型）
					Integer overdueNumberOfDays = DateListUtil.differentDaysByMillisecond(shoulddate,d);//逾期天数
					
					List<OverdueSettings> listover = projecttimerMapper.queryAllOversett(companyId);//查询逾期设置表所有信息
					List<BigDecimal> list1 = new ArrayList<BigDecimal>();
					for (int j = 0; j < listover.size(); j++) {
						if(overdueNumberOfDays<=listover.get(j).getOverduehowmanydaysage()){
							list1.add(listover.get(j).getPenaltyinterestrates());
						}
					}
					BigDecimal interestPenaltyDay = list1.get(0);//日均罚息（0.1%）
					BigDecimal interestPenaltySum = list.get(i).getRealityBorrowMoney().multiply(interestPenaltyDay).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
					BigDecimal interestInAll = list.get(i).getInterestSum().add(interestPenaltySum);//总利息
					
					projecttimerMapper.upaorderdetail(overdueNumberOfDays, interestPenaltyDay, interestPenaltySum, interestInAll, list.get(i).getId());
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//后台管理----控制逾期等级（定时任务）
	public void upaoverclass(){
		Integer companyId = 3;
		List<Orders> listord = projecttimerMapper.queryAlloverdue(companyId);
		List<OverdueClass> listover = projecttimerMapper.queryAlloverclass(companyId);
		for (int i = 0; i < listord.size(); i++) {
			Integer overdueNumberOfDays = Integer.parseInt(listord.get(i).getOverdueNumberOfDays());//逾期天数
			for (int j = 0; j < listover.size(); j++) {
				String describes = listover.get(j).getDescribes();//对应逾期天数(区间段)
				String[] describesstr = describes.split("-");
				Integer describesStart = Integer.parseInt(describesstr[0]);
				Integer describesEnd = Integer.parseInt(describesstr[1]);
				if(overdueNumberOfDays>=describesStart&&overdueNumberOfDays<=describesEnd){
					projecttimerMapper.upaoverclass(listover.get(j).getGrade(), listord.get(i).getId());
				}
			}
		}
		
	}
	
	//后台管理----控制逾期超过30天，打入黑名单（定时任务）
	public void addblack(){
		Integer companyId = 3;
		List<Orders> list = projecttimerMapper.queryAlloverdue(companyId);
		int blackline=projecttimerMapper.queryblackline();//黑名单分界线值
		for (int i = 0; i < list.size(); i++) {
			Integer overdueNumberOfDays = Integer.parseInt(list.get(i).getOverdueNumberOfDays());//逾期天数
			if(overdueNumberOfDays>=blackline){
				projecttimerMapper.upaBlacklistStatus(list.get(i).getUserId());//添加黑名单(修改当前用户的黑名单状态)
			}
		}
	}
}