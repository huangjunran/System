package com.system.service;

import com.system.po.EventCustom;

import java.util.List;

/**
 * Event事件Service层
 */
public interface EventService {

    //根据id更新事件信息
    void updateById(String id, EventCustom timeCustom) throws Exception;

    //根据id删除事件信息
    void removeById(String id) throws Exception;

    //获取分页查询事件信息
    List<EventCustom> findByPaging(Integer toPageNo) throws Exception;

    //保存事件信息
    Boolean save(EventCustom timeCustom) throws Exception;

    //获取事件总数
    int getCountEvent() throws Exception;

    //根据id查询
    EventCustom findById(String id) throws Exception;

    //根据事件名字查询
    List<EventCustom> findByTitle(String title) throws Exception;

    //获取全部事件
    List<EventCustom> findAll() throws Exception;
}
