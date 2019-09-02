package com.zhita.controller.source;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Source;
import com.zhita.model.manage.TongjiSorce;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.util.DateListUtil;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil2;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.Timestamps;
/**
 * 我们自己看的渠道统计
 * @author lhq
 * @{date} 2019年7月5日
 */
@Controller
@RequestMapping("/sourcetongji")
public class SourceTongjiController {
	@Autowired
	private IntSourceService intSourceService;
	
	//后台管理---我们自己看的统计数据——渠道表所有渠道
	@ResponseBody
	@RequestMapping("/queryByToday")
	public Map<String,Object> queryByToday(Integer companyId,Integer page)throws ParseException{
		String company=intSourceService.querycompany(companyId);
		List<TongjiSorce> listsource = new ArrayList<>();
		List<TongjiSorce> listsourcepage = new ArrayList<>();//经过分页后的数据集合
		PageUtil2 pageUtil=null;
		RedisClientUtil redisClientUtil=new RedisClientUtil();
		Date date=new Date();//得到当天时间
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf1=new SimpleDateFormat("yyyy/MM/dd");
		String today=sf.format(date);//(当天时间——年月日格式)
		
		String startTime = today;
		String startTimestamps = Timestamps.dateToStamp(startTime);
		String endTime = today;
		String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		
		//listsource=intSourceService.queryAllSourceByUser(companyId, startTimestamps, endTimestamps);
		for (int i = 0; i < listsource.size(); i++) {
			int sourceid=listsource.get(i).getSourceid();//渠道id
			String sourcename=listsource.get(i).getSourcename();//渠道名称
			float registernum=listsource.get(i).getRegisternum();//得到真实的注册数
			Integer companyid=listsource.get(i).getCompanyid();//公司id
			int uv=0;
			String cvr=null;
			if (redisClientUtil.getSourceClick(company + sourcename + sf1.format(date) + "xiaodaiKey") == null) {
				uv = 0;
			} else {
				uv = Integer.parseInt(redisClientUtil.getSourceClick(company + sourcename + sf1.format(date) + "xiaodaiKey"));
			}
			listsource.get(i).setUv(uv);//uv
			if ((registernum < 0.000001) || (uv == 0)) {
				cvr = 0 + "%";// 得到转化率
			} else {
				cvr = (new DecimalFormat("#.00").format(registernum / uv * 100)) + "%";// 得到uv到注册人数转化率
			}
			listsource.get(i).setCvr(cvr);//uv到注册的转化率
			listsource.get(i).setActivatecount(intSourceService.queryCount(sourceid,startTimestamps,endTimestamps));//激活数
			List<Integer> listuserid=intSourceService.queryUserid(sourceid);//查询当前渠道下的所有userid
			int authencount=0;//认证人数
			for (int j = 0; j < listuserid.size(); j++) {
				Integer userid=listuserid.get(j);
				int uatcount=intSourceService.queryIfExist(userid,startTimestamps,endTimestamps);
				int bankcount=intSourceService.queryIfExist1(userid,startTimestamps,endTimestamps);
				int operacount=intSourceService.queryIfExist2(userid,startTimestamps,endTimestamps);
				if((uatcount==1)||(bankcount==1)||(operacount==1)){
					authencount=authencount+1;
				}
			}
			listsource.get(i).setAuthencount(authencount);//当前渠道的认证人数
			Integer applynum=intSourceService.queryNum(companyId, sourceid,startTimestamps, endTimestamps);//申请人数
			listsource.get(i).setApplynum(applynum);//申请数
			String cvr1=null;
			if ((registernum < 0.000001) || (applynum == 0)) {
				cvr1 = 0 + "%";// 得到注册到申请转化率
			} else {
				cvr1 = (new DecimalFormat("#.00").format( applynum/ registernum * 100)) + "%";// 得到注册到申请转化率
			}
			listsource.get(i).setCvr1(cvr1);// 得到注册到申请转化率*/
			Integer machineauditpass=intSourceService.querypass(sourceid, startTimestamps, endTimestamps);
			listsource.get(i).setMachineauditpass(machineauditpass);//通过人数(包含机审通过和人审通过)
		/*	String cvr2=null;
			if ((registernum < 0.000001) || (machineauditpass == 0)) {
				cvr2 = 0 + "%";// 得到注册到借款转化率
			} else {
				cvr2 = (new DecimalFormat("#.00").format( machineauditpass/ registernum * 100)) + "%";// 得到注册到借款转化率
			}
			listsource.get(i).setCvr2(cvr2);// 得到注册到借款转化率*/
			
			int orderpass=intSourceService.queryorderpass(sourceid, startTimestamps, endTimestamps);
			listsource.get(i).setOrderpass(orderpass);//已借款人数
			String cvr2=null;
			if ((registernum < 0.000001) || (orderpass == 0)) {
				cvr2 = 0 + "%";// 得到借款率
			} else {
				cvr2 = (new DecimalFormat("#.00").format( orderpass/ registernum * 100)) + "%";// 得到借款率
			}
			listsource.get(i).setCvr2(cvr2);
		}
		
	  	if(listsource!=null && !listsource.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(listsource,page,10);
    		listsourcepage.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1,10,0);

    	}
	  	List<Source> sourcelist=intSourceService.querysource(companyId);	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listsourcepage", listsourcepage);
		map.put("sourcelist", sourcelist);
		map.put("pageutil", pageUtil);
    	return map;
	}
	
	//后台管理---我们自己看的统计数据——在用户表存在的渠道    某个时间段的统计信息
	@ResponseBody
	@RequestMapping("/queryByTimeslot")
	public Map<String,Object> queryByTimeslot(Integer companyId,Integer page,String dateStart,String dateEnd,Integer sourceid) throws ParseException{
		String company=intSourceService.querycompany(companyId);
		List<TongjiSorce> listsource = new ArrayList<>();
		List<TongjiSorce> listsourcepage = new ArrayList<>();//经过分页后的数据集合
		PageUtil2 pageUtil=null;
		RedisClientUtil redisClientUtil=new RedisClientUtil();
		
		String startTime = dateStart;
		String startTimestamps = Timestamps.dateToStamp(startTime);
		String endTime = dateEnd;
		String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		
		List<String> listdate=DateListUtil.getDays(dateStart, dateEnd);
		
		listsource=intSourceService.queryAllSourceBySouce(companyId, startTimestamps, endTimestamps, sourceid);
		for (int i = 0; i < listsource.size(); i++) {
			Integer sourceids=listsource.get(i).getSourceid();//渠道id
			String sourcename=listsource.get(i).getSourcename();//渠道名
			float registernum=listsource.get(i).getRegisternum();//真实的注册数
			Integer companyid=listsource.get(i).getCompanyid();//公司id
			int uv=0;
			String cvr=null;
			for (int j = 0; j < listdate.size(); j++) {
				int uvi=0;
				if (redisClientUtil.getSourceClick(company + sourcename + listdate.get(j).replace("-", "/") + "xiaodaiKey") == null) {
					uvi = 0;
				} else {
					uvi = Integer.parseInt(redisClientUtil.getSourceClick(company + sourcename + listdate.get(j).replace("-", "/") + "xiaodaiKey"));
				}
				uv=uv+uvi;
			}
			
			listsource.get(i).setUv(uv);//uv
			if ((registernum < 0.000001) || (uv == 0)) {
				cvr = 0 + "%";// 得到转化率
			} else {
				cvr = (new DecimalFormat("#.00").format(registernum / uv * 100)) + "%";// 得到uv到注册人数转化率
			}
			listsource.get(i).setCvr(cvr);//uv到注册的转化率
			listsource.get(i).setActivatecount(intSourceService.queryCount(sourceids,startTimestamps,endTimestamps));//激活数
			List<Integer> listuserid=intSourceService.queryUserid(sourceids);//查询当前渠道下的所有userid
			int authencount=0;//认证人数
			for (int j = 0; j < listuserid.size(); j++) {
				Integer userid=listuserid.get(j);
				int uatcount=intSourceService.queryIfExist(userid,startTimestamps,endTimestamps);
				int bankcount=intSourceService.queryIfExist1(userid,startTimestamps,endTimestamps);
				int operacount=intSourceService.queryIfExist2(userid,startTimestamps,endTimestamps);
				if((uatcount==1)||(bankcount==1)||(operacount==1)){
					authencount=authencount+1;
				}
			}
			listsource.get(i).setAuthencount(authencount);//当前渠道的认证人数
			Integer applynum=intSourceService.queryNum(companyId, sourceids,startTimestamps, endTimestamps);//申请人数
			listsource.get(i).setApplynum(applynum);//申请数
			String cvr1=null;
			if ((registernum < 0.000001) || (applynum == 0)) {
				cvr1 = 0 + "%";// 得到注册到申请转化率
			} else {
				cvr1 = (new DecimalFormat("#.00").format( applynum/ registernum * 100)) + "%";// 得到注册到申请转化率
			}
			listsource.get(i).setCvr1(cvr1);// 得到注册到申请转化率
			Integer machineauditpass=intSourceService.querypass(sourceids, startTimestamps, endTimestamps);
			listsource.get(i).setMachineauditpass(machineauditpass);//通过人数(包含机审通过和人审通过)
			int orderpass=intSourceService.queryorderpass(sourceids, startTimestamps, endTimestamps);
			listsource.get(i).setOrderpass(orderpass);//已借款人数
			String cvr2=null;
			if ((registernum < 0.000001) || (orderpass == 0)) {
				cvr2 = 0 + "%";// 得到借款率
			} else {
				cvr2 = (new DecimalFormat("#.00").format( orderpass/ registernum * 100)) + "%";// 得到借款率
			}
			listsource.get(i).setCvr2(cvr2);
		}
		
	  	if(listsource!=null && !listsource.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(listsource,page,10);
    		listsourcepage.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1,10,0);

    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listsourcepage", listsourcepage);
		map.put("pageutil", pageUtil);
    	return map;
	}
	
	// 后台管理---我们自己看的统计数据——在用户表存在的渠道    某个时间段每一天的详细信息  
	//例如01号-03号   外面显示的是01号到03号总的统计数据    里面显示的是01,02,03号每一天的详细统计数据
	@ResponseBody
	@RequestMapping("/queryAllPageDetail")
	public List<TongjiSorce> queryAllPageDetail(Integer companyId,Integer sourceid, String dateStart,String dateEnd) throws ParseException {
		String company=intSourceService.querycompany(companyId);
		RedisClientUtil redisClientUtil = new RedisClientUtil();
		
		List<TongjiSorce> listsource = new ArrayList<>();
		
		List<String> listdate=DateListUtil.getDays(dateStart, dateEnd);
		//List<String> listregistetimedate=new ArrayList<>();//(用来存时间戳转换后的时间)
		//List<String> listregistetime=intSourceService.qeuryAllUserRegistetime(companyId);
		/*for (int i = 0; i < listregistetime.size(); i++) {
			listregistetimedate.add(Timestamps.stampToDate1(listregistetime.get(i)));
		}
		HashSet h1 = new HashSet(listregistetimedate);
		listregistetimedate.clear();
		listregistetimedate.addAll(h1);
		
		listdate.retainAll(listregistetimedate);//两个集合的交集
*/		
		for (int i = 0; i < listdate.size(); i++) {
			String date=listdate.get(i);
			String startTime = date;
			String startTimestamps = Timestamps.dateToStamp(startTime);
			String endTime = date;
			String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
			
			
			
			TongjiSorce tongjiSorce=intSourceService.queryAllSourceByUserDetail(companyId, startTimestamps, endTimestamps, sourceid);
			
			int uv=0;//uv
			float registernum=0;//真实的注册数
			String cvr=null;//uv到注册的转化率
			if(tongjiSorce==null){
				tongjiSorce=new TongjiSorce();
				Source source=intSourceService.selectByid(sourceid);
				String sourcename=source.getSourcename();
				tongjiSorce.setSourcename(sourcename);//渠道名称
				tongjiSorce.setRegisternum(0);
				registernum=tongjiSorce.getRegisternum();//真实的注册数
				if(redisClientUtil.getSourceClick(company+sourcename+listdate.get(i).replace("-", "/")+"xiaodaiKey")==null){
					uv=0;
				}else {
					uv = Integer.parseInt(redisClientUtil.getSourceClick(company + sourcename + listdate.get(i).replace("-", "/") + "xiaodaiKey"));
				}
				tongjiSorce.setUv(uv);
			}else{
				registernum=tongjiSorce.getRegisternum();//真实的注册数
				String sourcename=tongjiSorce.getSourcename();//渠道名称
				if(redisClientUtil.getSourceClick(company+sourcename+listdate.get(i).replace("-", "/")+"xiaodaiKey")==null){
					uv=0;
				}else {
					uv = Integer.parseInt(redisClientUtil.getSourceClick(company + sourcename + listdate.get(i).replace("-", "/") + "xiaodaiKey"));
				}
				tongjiSorce.setUv(uv);
			}
			
			tongjiSorce.setDate(date);//日期
			
			if ((registernum < 0.000001) || (uv == 0)) {
				cvr = 0 + "%";// 得到转化率
			} else {
				cvr = (new DecimalFormat("#.00").format(registernum / uv * 100)) + "%";// 得到uv到注册人数转化率
			}
			tongjiSorce.setCvr(cvr);//uv到注册的转化率
			tongjiSorce.setActivatecount(intSourceService.queryCount(sourceid,startTimestamps,endTimestamps));//激活数
			
			List<Integer> listuserid=intSourceService.queryUserid(sourceid);//查询当前渠道下的所有userid
			int authencount=0;//认证人数
			for (int j = 0; j < listuserid.size(); j++) {
				Integer userid=listuserid.get(j);
				int uatcount=intSourceService.queryIfExist(userid,startTimestamps,endTimestamps);
				int bankcount=intSourceService.queryIfExist1(userid,startTimestamps,endTimestamps);
				int operacount=intSourceService.queryIfExist2(userid,startTimestamps,endTimestamps);
				if((uatcount==1)||(bankcount==1)||(operacount==1)){
					authencount=authencount+1;
				}
			}
			tongjiSorce.setAuthencount(authencount);//当前渠道的认证人数
			Integer applynum=intSourceService.queryNum(companyId, sourceid,startTimestamps, endTimestamps);//申请人数
			tongjiSorce.setApplynum(applynum);//申请数
			String cvr1=null;
			if ((registernum < 0.000001) || (applynum == 0)) {
				cvr1 = 0 + "%";// 得到注册到申请转化率
			} else {
				cvr1 = (new DecimalFormat("#.00").format( applynum/ registernum * 100)) + "%";// 得到注册到申请转化率
			}
			tongjiSorce.setCvr1(cvr1);// 得到注册到申请转化率
			Integer machineauditpass=intSourceService.querypass(sourceid, startTimestamps, endTimestamps);
			tongjiSorce.setMachineauditpass(machineauditpass);//通过人数(包含机审通过和人审通过)
			int orderpass=intSourceService.queryorderpass(sourceid, startTimestamps, endTimestamps);
			tongjiSorce.setOrderpass(orderpass);//已借款人数
			String cvr2=null;
			if ((registernum < 0.000001) || (orderpass == 0)) {
				cvr2 = 0 + "%";// 得到借款率
			} else {
				cvr2 = (new DecimalFormat("#.00").format( orderpass/ registernum * 100)) + "%";// 得到借款率
			}
			tongjiSorce.setCvr2(cvr2);
			listsource.add(tongjiSorce);//listsoruce里面将每一天的数据都存进去
		}
				
		return listsource;
	}
}
