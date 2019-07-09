package com.zhita.service.manage.liftingamount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.LiftingAmountMapper;
import com.zhita.model.manage.LiftingAmount;

@Service
public class LiftingamountServiceImp implements IntLiftingamountServcie{
	@Autowired
	private LiftingAmountMapper liftingAmountMapper;
	
	//后台管理---查询续借提额表所有信息
    public List<LiftingAmount> queryAll(Integer comapnyId){
    	List<LiftingAmount> list=liftingAmountMapper.queryAll(comapnyId);
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(LiftingAmount record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=liftingAmountMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据当前id查询当前对象信息
    public  LiftingAmount selectByPrimaryKey(Integer id){ 
    	LiftingAmount liftingAmount=liftingAmountMapper.selectByPrimaryKey(id);
    	return liftingAmount;
    }
    
    //后台管理---更新保存
    public int updateByPrimaryKey(LiftingAmount record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=liftingAmountMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---修改当前对象的假删除状态
	 public int upaFalseDel(String operator,Integer id){
		 String operationtime=System.currentTimeMillis()+"";
		 int num=liftingAmountMapper.upaFalseDel(operator, operationtime, id);
		 return num;
	 }
}
