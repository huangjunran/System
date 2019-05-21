package com.system.service.impl;

import com.system.mapper.CollegeMapper;
import com.system.mapper.SelectCourseMapper;
import com.system.mapper.StudentMapper;
import com.system.po.*;
import com.system.service.SelectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {
    @Autowired
    private SelectCourseMapper selectCourseMapper;

    @Autowired
    private StudentMapper studentMapper;

//    @Resource(name = "courseServiceImpl")
//    private CourseService courseService;
//根据标题查找

    @Override
    public SelectCustom findOne(SelectCustom selectCustom) throws Exception {
        return null;
    }

    @Override
    public List<SelectCustom> findAll() throws Exception {
        SelectExample selectExample = new SelectExample();
        SelectExample.Criteria criteria = selectExample.createCriteria();

        criteria.andCourseidIsNotNull();
        criteria.andStudentidIsNotNull();

        List<Select> list = selectCourseMapper.selectByExample(selectExample);
        List<SelectCustom> selectCustomsList = null;
        if (list != null) {
            selectCustomsList = new ArrayList<SelectCustom>();
            for (Select s: list) {
                SelectCustom selectCustom = new SelectCustom();
                BeanUtils.copyProperties(s, selectCustom);
                selectCustomsList.add(selectCustom);
            }
        }
        return selectCustomsList;
    }


    public List<SelectCustom> findByStudentid(Integer studentid) throws Exception {


        List<Select> list = new ArrayList<Select>();
               list= selectCourseMapper.selectByStudentId(studentid);
        List<SelectCustom> selectCustomsList = null;
        if (list != null) {
            selectCustomsList = new ArrayList<SelectCustom>();
            for (Select t: list) {
                SelectCustom selectCustom = new SelectCustom();
                BeanUtils.copyProperties(t, selectCustom);
                selectCustomsList.add(selectCustom);
            }
            return selectCustomsList;
        }else
            return null;
    }

    @Override
    public List<SelectCustom> findByCourseid(Integer courseid) throws Exception {
        List<Select> list = new ArrayList<Select>();
        list= selectCourseMapper.selectByCourseId(courseid);
        List<SelectCustom> selectCustomsList = null;
        if (list != null) {
            selectCustomsList = new ArrayList<SelectCustom>();
            for (Select t: list) {
                SelectCustom selectCustom = new SelectCustom();
                BeanUtils.copyProperties(t, selectCustom);
                selectCustomsList.add(selectCustom);
            }
            return selectCustomsList;
        }else
            return null;
    }

    public void save(SelectCustom selectCustom) throws Exception {
        selectCourseMapper.insert(selectCustom);
    }

    public void updateSelectList(SelectCustom selectCustom)throws Exception{
        selectCourseMapper.updateSelectList(selectCustom);
    }


    @Override
    public void remove(SelectCustom selectCustom) throws Exception {

        SelectExample example = new SelectExample();
        SelectExample.Criteria criteria = example.createCriteria();

        criteria.andCourseidEqualTo(selectCustom.getCourseid());
        criteria.andStudentidEqualTo(selectCustom.getStudentid());

       selectCourseMapper.deleteByExample(example);

    }


    @Override
    public void removeAll() throws Exception {
        selectCourseMapper.removeAll();
    }
}
