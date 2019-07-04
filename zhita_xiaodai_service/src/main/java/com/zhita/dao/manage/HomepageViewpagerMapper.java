package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.HomepageViewpager;

public interface HomepageViewpagerMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(HomepageViewpager record);

    int insertSelective(HomepageViewpager record);
    
    //后台管理---编辑功能（根据id查询当前对象信息）
    HomepageViewpager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HomepageViewpager record);
    
    //后台管理---编辑功能
    int updateByPrimaryKey(HomepageViewpager record);
    
    //后台管理----查询首页轮播图所有信息
    List<HomepageViewpager> queryAll(Integer companyId);
    
    //后台管理---修改当前对象假删除状态
    int updateFalDel(Integer id);
    
    //后台管理---通过id  查询当前对象的首页轮播图图片
    String queryViewpagerpicture(Integer id);
}