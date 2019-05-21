package com.system.service.impl;

import com.system.exception.CustomException;
import com.system.mapper.EventMapper;
import com.system.mapper.EventMapperCustom;
import com.system.po.*;
import com.system.service.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventMapperCustom eventMapperCustom;


    //根据id更新事件信息
    public void updateById(String id, EventCustom eventCustom) throws Exception {

       eventMapper.updateByPrimaryKey(eventCustom);
    }

    public void removeById(String id) throws Exception {

        eventMapper.deleteByPrimaryKey(id);
    }



    //根据页面进行跳转跳转
    public List<EventCustom> findByPaging(Integer toPageNo) throws Exception {
        PagingVO pagingVO = new PagingVO();
        pagingVO.setToPageNo(toPageNo);

        List<EventCustom> list = eventMapperCustom.findByPaging(pagingVO);

        return list;
    }


    //添加事件时 判断是否id重复
    public Boolean save(EventCustom eventCustom) throws Exception {
        Event event = eventMapper.selectByPrimaryKey(eventCustom.geteventid());
        if (event == null) {
            eventCustom.setExecuted(0);
            eventMapper.insert(eventCustom);

            return true;
        }
        return false;
    }


    //获取event事件的总数
    public int getCountEvent() throws Exception {
        //自定义查询对象
        EventExample eventExample = new EventExample();
        //通过criteria构造查询条件
        EventExample.Criteria criteria = eventExample.createCriteria();
        criteria.andEventidIsNotNull();

        return eventMapper.countByExample(eventExample);
    }


    public EventCustom findById(String id) throws Exception {
        Event event = eventMapper.selectByPrimaryKey(id);
        EventCustom eventCustom = null;
        if (event != null) {
            eventCustom = new EventCustom();
            BeanUtils.copyProperties(event, eventCustom);
        }

        return eventCustom;
    }


    //根据标题查找
    public List<EventCustom> findByTitle(String title) throws Exception {

        String key = "%"+title+"%";
        List<Event> list = eventMapper.selectByTitle(key);
        List<EventCustom> eventCustomsList = null;
        if (list != null) {
            eventCustomsList = new ArrayList<EventCustom>();
            for (Event t: list) {
                EventCustom eventCustom = new EventCustom();
                BeanUtils.copyProperties(t, eventCustom);
                eventCustomsList.add(eventCustom);

            }
        }
        return eventCustomsList;
    }


    //查找所有事件信息
    public List<EventCustom> findAll() throws Exception {
       EventExample eventExample=new EventExample();
       EventExample.Criteria criteria=eventExample.createCriteria();
        criteria.andTitleIsNotNull();

       List<Event> list=eventMapper.selectByExample(eventExample);
        List<EventCustom> eventCustomsList = null;
        if (list != null) {
            eventCustomsList = new ArrayList<EventCustom>();
            for (Event t: list) {
                EventCustom eventCustom = new EventCustom();
                BeanUtils.copyProperties(t, eventCustom);
                eventCustomsList.add(eventCustom);
            }
        }
        return eventCustomsList;
    }

}