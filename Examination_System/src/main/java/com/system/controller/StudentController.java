package com.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.system.exception.CustomException;
import com.system.po.*;
import com.system.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
@RequestMapping(value = "/student")
public class StudentController {

    @Resource(name = "courseServiceImpl")
    private CourseService courseService;

    @Resource(name = "studentServiceImpl")
    private StudentService studentService;

    @Resource(name = "teacherServiceImpl")
    private TeacherService teacherService;

    @Resource(name = "eventServiceImpl")
    private EventService eventService;

    @Resource(name = "selectedCourseServiceImpl")
    private SelectedCourseService selectedCourseService;

    @Resource(name = "selectServiceImpl")
    private SelectService selectService;

    @RequestMapping("/showEvent")
    public String showEvent(Model model, Integer page) throws Exception {

        List<EventCustom> list = new ArrayList<EventCustom>();
        list=eventService.findAll();

        Date date = new Date();
        date.getTime() ;
        List<EventCustom> newlist= new ArrayList<EventCustom>();

        for (EventCustom event:list) {
            Date startTime=event.getStarttime();
            Date endTime=event.getEndtime();

            if(date.before(endTime)&&date.after(startTime)){
                newlist.add(event);
            }
            System.out.println(newlist.size());
        }

        System.out.println(newlist.size());

        model.addAttribute("eventList", newlist);

        return "student/showEvent";
    }

    //搜索课题
    @RequestMapping(value = "selectEvent", method = {RequestMethod.POST})
    private String selectEvent(String findByTitle, Model model) throws Exception {
        List<EventCustom> list = eventService.findByTitle(findByTitle);

        model.addAttribute("eventList", list);
        return "teacher/showEvent";
    }

    @RequestMapping(value = "/showCourse")
    public String stuCourseShow(Model model, Integer page) throws Exception {

        List<CourseCustom> list = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(courseService.getCountCouse());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = courseService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = courseService.findByPaging(page);
        }

        List<CourseCustom> newlist =new ArrayList<CourseCustom>();

        for(CourseCustom course:list) {
            if(course.getPass()!=0){
                String name=teacherService.findById(course.getTeacherid()).getUsername();
                course.setTeacherName(name);
                newlist.add(course);
            }
        }

        model.addAttribute("courseList", newlist);
        model.addAttribute("pagingVO", pagingVO);

        return "student/showCourse";
    }

    //显示选题列表
    @RequestMapping(value = "/selectCourse")
    public String selectCourse(Model model) throws Exception {

        List<SelectCustom> list =new ArrayList<SelectCustom>();
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        list=selectService.findByStudentid(Integer.parseInt(username));
        for(SelectCustom select:list)
        {
            CourseCustom courseCustom=courseService.findById(select.getCourseid());
            TeacherCustom teacher=teacherService.findById(courseCustom.getTeacherid());
            courseCustom.setTeacherName(teacher.getUsername());
            select.setCourseCustom(courseCustom);
        }

        model.addAttribute("selectList", list);

        return "student/selectCourse";
    }

//    // 选课操作
    @RequestMapping(value="/stuSelect",method=RequestMethod.GET)
    @ResponseBody
    public JSONObject stuSelect(int id) throws Exception {
        JSONObject result = new JSONObject();
        result.put("data","");

        Event event =new Event();

        List<SelectedCourseCustom> selectedCourseCustomList=new ArrayList<SelectedCourseCustom>();
        selectedCourseCustomList=selectedCourseService.findAll();

        if(selectedCourseCustomList.size()==0)
        {
            event = eventService.findById("3");
//            result.put("data","第1轮");
//            return  result;
        }else {
            event = eventService.findById("5");
//            result.put("data","第2轮");
//            return  result;
        }

        Date date = new Date();
        date.getTime() ;
        if(event.getExecuted()==1) {
            //前小于后返回 -1   前大于后返回 1
            if(event.getStarttime().compareTo(date)<0&&event.getEndtime().compareTo(date)>0) {


                //获取当前用户
                Subject subject = SecurityUtils.getSubject();
                String username = (String) subject.getPrincipal();

                //已经选择好了
                SelectedCourseCustom selectedCourseCustom = new SelectedCourseCustom();
                selectedCourseCustom.setCourseid(id);
                selectedCourseCustom.setStudentid(Integer.parseInt(username));
                SelectedCourseCustom s = selectedCourseService.findOne(selectedCourseCustom);
                if (s != null) {
                    result.put("data", "你已有正在进行的选题，不能再选");
                    //          throw new CustomException("你已有正在进行的选题，不能再选");
                } else {
                    SelectCustom selectCustom = new SelectCustom();
                    List<SelectCustom> selectList = new ArrayList<SelectCustom>();
                    selectList = selectService.findByStudentid(Integer.parseInt(username));

                    //选题数量限制
                    if (selectList.size() < 3) {
                        selectCustom.setCourseid(id);
                        selectCustom.setStudentid(Integer.parseInt(username));
                        selectCustom.setPass(0);
                        boolean bool = true;
                        //选题查重
                        if (selectList.size() != 0) {
                            for (SelectCustom sc : selectList) {
                                if (sc.getCourseid() == id) {
                                    bool = false;
                                    result.put("data", "该课题已选择");
                                    //                          throw new CustomException("该课题已选择");
                                }
                            }

                        } else {
                            bool = false;
                            selectService.save(selectCustom);
                            result.put("data", "成功选择");
                        }
                        if (bool) {
                            selectService.save(selectCustom);
                            result.put("data", "成功选择");
                        }
                    } else {
                        result.put("data", "选题个数达到上限");
                        //              throw new CustomException("选题个数达到上限");
                    }
                }
                setLevel(Integer.parseInt(username));
            }else{
                result.put("data","选题尚未开始");
            }
        }else{
            result.put("data","选题尚未开始");
        }
        return result;
    }

    public void setLevel(Integer studentid) throws Exception {

        List<SelectCustom> selectList=new ArrayList<SelectCustom>();
        selectList=selectService.findByStudentid(studentid);

        for(int i=0;i<selectList.size();i++)
        {
            SelectCustom selectCustom=new SelectCustom();
            selectCustom=selectList.get(i);
            selectCustom.setPass(i);
            selectService.updateSelectList(selectCustom);
        }
    }



    // 退选操作
    @RequestMapping(value = "/outCourse")
    public String outCourse(int id) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        SelectCustom selectCustom = new SelectCustom();
        selectCustom.setCourseid(id);
        selectCustom.setStudentid(Integer.parseInt(username));

        selectService.remove(selectCustom);

        return "redirect:/student/selectCourse";
    }

    // 已选课程
    @RequestMapping(value = "/selectedCourse")
    public String selectedCourse(Model model) throws Exception {
        //获取当前用户名
        Subject subject = SecurityUtils.getSubject();
//        System.out.println((String) subject.getPrincipal());


        StudentCustom studentCustom = studentService.findStudentAndSelectCourseListByName((String) subject.getPrincipal());

        List<SelectedCourseCustom> list = studentCustom.getSelectedCourseList();

        model.addAttribute("selectedCourseList", list);

        return "student/selectCourse";
    }

    // 最终题目
    @RequestMapping(value = "/overCourse")
    public String overCourse(Model model) throws Exception {

//        获取当前用户name
        Subject subject = SecurityUtils.getSubject();
        Integer studentid= Integer.parseInt((String)subject.getPrincipal());

        SelectedCourseCustom selectedCourseCustom=new SelectedCourseCustom();
        selectedCourseCustom=selectedCourseService.findByStudentID(studentid);

     if(selectedCourseCustom!=null) {
         CourseCustom courseCustom = new CourseCustom();
         courseCustom = courseService.findById(selectedCourseCustom.getCourseid());


         TeacherCustom teacherCustom = new TeacherCustom();
         teacherCustom = teacherService.findById(courseCustom.getTeacherid());

         selectedCourseCustom.setTeacherCustom(teacherCustom);
         selectedCourseCustom.setCourseCustom(courseCustom);
     }

//       添加返回值失败，不能识别
        model.addAttribute("selectedCourse", selectedCourseCustom);
        return "student/overCourse";
    }

    //修改密码
    @RequestMapping(value = "/passwordRest")
    public String passwordRest() throws Exception {
        return "student/passwordRest";
    }



}
