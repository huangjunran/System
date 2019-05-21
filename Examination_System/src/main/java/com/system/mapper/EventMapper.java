package com.system.mapper;

import com.system.po.Event;
import com.system.po.EventExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventMapper {

    int countByExample(EventExample example);

    int deleteByExample(EventExample example);

    int deleteByPrimaryKey(String Eventid);

    int insert(Event record);

    int insertSelective(Event record);

    List<Event> selectByExample(EventExample example);

    Event selectByPrimaryKey(String eventid);

    List<Event> selectByTitle(String title);

    int updateByExampleSelective(@Param("record") Event record, @Param("example") EventExample example);

    int updateByExample(@Param("record") Event record, @Param("example") EventExample example);

    int updateByPrimaryKeySelective(Event record);

    int updateByPrimaryKey(Event record);

    int updateByid(Event record);
}
