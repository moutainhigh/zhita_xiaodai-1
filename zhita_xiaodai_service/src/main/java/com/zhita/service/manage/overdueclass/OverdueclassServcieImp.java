package com.zhita.service.manage.overdueclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OverdueClassMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.BlackDemarcationLine;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.OverdueClass;

@Service
public class OverdueclassServcieImp implements IntOverdueclassService{
	@Autowired
	private OverdueClassMapper overdueClassMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理 --- 查询逾期等级表所有信息
    public Map<String,Object> queryAll(Integer companyId){
    	List<OverdueClass> list=overdueClassMapper.queryAll(companyId);
    	BlackDemarcationLine BlackDemarcationLine=overdueClassMapper.query();
    	Integer blackline=BlackDemarcationLine.getDemarcationline();//黑名单分界线
    	Map<String,Object> map=new HashMap<>();
    	map.put("overduelist", list);
    	map.put("blackline", blackline);	
    	return map;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(OverdueClass record){
    	int num=overdueClassMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据id查询出当前对象信息
    public OverdueClass selectByPrimaryKey(Integer id){
    	OverdueClass overdueClass=overdueClassMapper.selectByPrimaryKey(id);
    	return overdueClass;
    }
    
    //后台管理---修改保存功能
    public int updateByPrimaryKey(OverdueClass record){
    	int num=overdueClassMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---修改黑名单分界线的值
    public int update(Integer blacklinevalue){
    	int num=overdueClassMapper.update(blacklinevalue);
    	return num;
    }
}
