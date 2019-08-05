package com.zhita.service.manage.user;

import java.util.Map;

import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.UserLikeParameter;

public interface IntUserService {
	//后台管理----用户列表(公司id，page,姓名，手机号，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	public Map<String, Object> queryUserList(UserLikeParameter userLikeParameter);
	
	//后台管理---添加黑名单
	public int insertBlacklist(Integer companyId,Integer userId,Integer operator);
	
	//后台管理---解除黑名单
	public int removeBlacklist(Integer companyId,Integer userId);
	
	//后台管理----订单 查询（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id  用户id）
  	public Map<String,Object> queryAllOrdersByUserid(OrderQueryParameter orderQueryParameter);
  	
	//后台管理----订单 查询（公司id  page，姓名，手机号，身份证号，注册开始时间，注册结束时间     渠道id）
  	public Map<String, Object> queryAllOrdersByUserid1(OrderQueryParameter orderQueryParameter);
  	
	//后台管理---用户认证信息
	public Map<String,Object> queryUserAttesta(Integer userid);

	public void updateScore(int score, int userId, String shareOfState);

	public int getRiskControlPoints(int userId);

	public String getshareOfState(int userId);

	public int getdelayTimes(int userId);

	public void updateshareOfState(int userId, String shareOfState);

	public void setuser(int userId, String timStamp, String applynumber);


}
