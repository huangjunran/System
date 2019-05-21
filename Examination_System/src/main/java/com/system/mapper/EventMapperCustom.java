package com.system.mapper;

import com.system.po.EventCustom;
import com.system.po.PagingVO;

import java.util.List;

public interface  EventMapperCustom {

    //分页查询事件信息
    List<EventCustom> findByPaging(PagingVO pagingVO) throws Exception;
}
