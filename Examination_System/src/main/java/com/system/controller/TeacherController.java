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
import sun.util.calendar.LocalGregorianCalendar;

import javax.annotation.Resource;
import javax.swing.text.Document;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Resource(name = "teacherServiceImpl")
    private TeacherService teacherService;

    @Resource(name = "studentServiceImpl")
    private StudentService studentService;


    @Resource(name = "courseServiceImpl")
    private CourseService courseService;

    @Resource(name = "eventServiceImpl")
    private EventService eventService;

    @Resource(name = "collegeServiceImpl")
    private CollegeService collegeService;

    @Resource(name = "selectedCourseServiceImpl")
    private SelectedCourseService selectedCourseService;

    @Resource(name = "selectServiceImpl")
    private SelectService selectService;

    // 显示通知信息
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

        return "teacher/showEvent";
    }

    //搜索课题
    @RequestMapping(value = "selectEvent", method = {RequestMethod.POST})
    private String selectEvent(String findByTitle, Model model) throws Exception {
        List<EventCustom> list = eventService.findByTitle(findByTitle);

        model.addAttribute("eventList", list);
        return "teacher/showEvent";
    }


    // 显示我的课题
    @RequestMapping(value = "/showCourse")
    public String stuCourseShow(Model model) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        List<CourseCustom> list = courseService.findByTeacherID(Integer.parseInt(username));
        for(CourseCustom c:list){
            if(!c.getStudentid().equals(0)){
               List<SelectedCourseCustom> s=selectedCourseService.findByCourseID(c.getCourseid());
                c.setAds(s.get(0).getAds());
            }
        }
        model.addAttribute("courseList", list);

        return "teacher/showCourse";
    }

//修改题目
//显示事件
@RequestMapping(value="/changeTitleMessage",method=RequestMethod.GET)
@ResponseBody
public JSONObject changeTitleMessage(int id) throws Exception {
    JSONObject result = new JSONObject();
    result.put("data","");

//     获取当前课程信息
    Event event =new Event();
    event=eventService.findById("2");
    Date date = new Date();
    date.getTime() ;
    if(event.getExecuted()==1) {
        //前小于后返回 -1   前大于后返回 1
        if(event.getStarttime().compareTo(date)<0&&event.getEndtime().compareTo(date)>0){
            result.put("data","");
        }else {
            result.put("data", "设置题目未开始");
        }
    }else{
        result.put("data", "设置题目未开始.");
    }
    return result;
}


    @RequestMapping(value = "/editCourse",method = {RequestMethod.GET})
    public String editCourseUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/teacher/showCourse";
        }

        List<SelectCustom> selectList=new ArrayList<SelectCustom>();
        selectList=selectService.findByCourseid(id);

        List<SelectCustom> newselectList=new ArrayList<SelectCustom>();
        List<StudentCustom> studentCustomsList=new ArrayList<>();
        if(selectList!=null) {
            for(SelectCustom s:selectList){
                if(s.getPass()==0){
                    newselectList.add(s);
                    studentCustomsList.add(studentService.findById(s.getStudentid()));
                }else if(Condition(s.getCourseid())&&s.getPass()==1){
                    newselectList.add(s);
                    studentCustomsList.add(studentService.findById(s.getStudentid()));
                }
            }

            model.addAttribute("studentList", studentCustomsList);
        }

        CourseCustom courseCustom = courseService.findById(id);
        if (courseCustom == null) {
            throw new CustomException("未找到该课题");
        }
        List<College> list = collegeService.finAll();



        model.addAttribute("collegeList", list);
        model.addAttribute("course", courseCustom);

        return "teacher/editCourse";
    }
//更改课题信息
    @RequestMapping(value = "/editCourse", method = {RequestMethod.POST})
    public String editCourse(CourseCustom courseCustom) throws Exception {

        int id=courseCustom.getCourseid();
        courseCustom.setPass(0);
        courseService.upadteById(id,courseCustom);

        return "redirect:/teacher/showCourse";
    }



    //显示更改学生页面
    @RequestMapping(value = "/selectStudent",method = {RequestMethod.GET})
    public String selectStudentUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/teacher/showCourse";
        }

        //根据id寻找课题信息
        CourseCustom courseCustom = courseService.findById(id);
        if (courseCustom == null) {
            throw new CustomException("未找到该课题");
        }
        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);
        model.addAttribute("course", courseCustom);


        if(courseCustom.getStudentid()==0) {
            List<SelectCustom> selectList = new ArrayList<SelectCustom>();
            selectList = selectService.findByCourseid(id);

            List<SelectCustom> newselectList = new ArrayList<SelectCustom>();
            List<StudentCustom> studentCustomsList = new ArrayList<>();
            if (selectList != null) {
                for (SelectCustom s : selectList) {
                    if (s.getPass() == 0) {
                        newselectList.add(s);
                        studentCustomsList.add(studentService.findById(s.getStudentid()));
                    } else if (Condition(s.getCourseid()) && s.getPass() == 1) {
                        newselectList.add(s);
                        studentCustomsList.add(studentService.findById(s.getStudentid()));
                    }
                }

                model.addAttribute("studentList", studentCustomsList);
            }

        }

        return "teacher/selectStudent";
    }



//选择学生
    @RequestMapping(value="/selectStudentMessage",method=RequestMethod.GET)
    @ResponseBody
    public JSONObject selectStuMsge(int id) throws Exception {
        JSONObject result = new JSONObject();
        result.put("data", "");

        Event event = new Event();
        event = eventService.findById("4");

        Event event2 = new Event();
        event2 = eventService.findById("6");


        Date date = new Date();
        date.getTime();
        if (event.getExecuted() == 1||event2.getExecuted() == 1) {
            //前小于后返回 -1   前大于后返回 1
            Boolean eventboolean1=(event.getStarttime().compareTo(date)<0 && event.getEndtime().compareTo(date)>0);
            Boolean eventboolean2=(event2.getStarttime().compareTo(date)<0 && event2.getEndtime().compareTo(date)>0);

            if (eventboolean1||eventboolean2) {

                CourseCustom courseCustom = new CourseCustom();
                courseCustom = courseService.findById(id);

               List<SelectedCourseCustom> selectedCourseCustomList=new ArrayList<SelectedCourseCustom>();
                selectedCourseCustomList=selectedCourseService.findByCourseID(courseCustom.getCourseid());

                if(selectedCourseCustomList.size()>0){
                    result.put("data","已有学生选择该课题");
                } else{
                    if (courseCustom.getPass() == 1) {
                        //获取当前课程信息
                        List<SelectCustom> selectCustomList = new ArrayList<>();
                        selectCustomList = selectService.findByCourseid(id);
                        //筛选第一志愿为该课程的学生
                        selectCustomList = selectStudentList(selectCustomList, 0);

                        if (selectCustomList.size() > 0) {
                            result.put("data", "");
                        } else {
                            result.put("data", "该课程无学生选择");
                             }
                    } else {
                        result.put("data", "该课程未通过审核");
                        }
                    }
            }else {
                result.put("data", "选择学生未开始");
            }
        }else{
            result.put("data", "选择学生未开始.");
        }
        return result;
    }


    //更改学生
    @RequestMapping(value = "/selectStudent", method = {RequestMethod.POST})
    public String selectStudent(CourseCustom courseCustom) throws Exception {

        //更新course表
        int id=courseCustom.getCourseid();
        courseService.upadteById(id,courseCustom);

        //更新selected表
        SelectedCourseCustom selected=new SelectedCourseCustom();
        selected.setCourseid(courseCustom.getCourseid());
        selected.setStudentid(courseCustom.getStudentid());
        selected.setAds("");
        selectedCourseService.save(selected);

        // 删除select表中信息
        List<SelectCustom> selectCustomList=new ArrayList<>();
        selectCustomList=selectService.findAll();
        for(SelectCustom s:selectCustomList){
            if(s.getStudentid().equals(courseCustom.getStudentid())){
                selectService.remove(s);
            }
            if(s.getCourseid().equals(courseCustom.getCourseid())){
                selectService.remove(s);
            }
        }
        return "redirect:/teacher/showCourse";
    }

 //显示学生josn
 @RequestMapping(value="/showStudentMessage",method=RequestMethod.GET)
 @ResponseBody
 public JSONObject showStuMsge(int id) throws Exception {
     JSONObject result = new JSONObject();
     result.put("data","");

//     获取当前课程信息
        CourseCustom courseCustom=new CourseCustom();
        courseCustom=courseService.findById(id);

        if(courseCustom.getStudentid()>0){
            result.put("data","");
        }else{
            result.put("data"," 暂无学生信息");
        }
        return result;
    }


    // 显示学生Message
    @RequestMapping(value = "/StudentMessage")
    public String gradeCourse(Integer id, Model model) throws Exception {
//        List<SelectedCourseCustom> selectedCourseCustom=selectedCourseService.findByCourseID(id);

        CourseCustom courseCustom=new CourseCustom();
        courseCustom=courseService.findById(id);
        if(courseCustom!=null)
        {
            Integer studentid=courseCustom.getStudentid();
           StudentCustom studentCustom=studentService.findById(studentid);
           List<College> collegeList=collegeService.finAll();
           for(College c:collegeList){
               if(c.getCollegeid()==studentCustom.getCollegeid()){
                   studentCustom.setcollegeName(c.getCollegename());
               }
           }
            model.addAttribute("student", studentCustom);
        }
        return "teacher/showStudentMessage";

    }


    //修改密码
    @RequestMapping(value = "/passwordRest")
    public String passwordRest() throws Exception {
        return "teacher/passwordRest";
    }

    // 筛选志愿条件
    public List<SelectCustom> selectStudentList(List<SelectCustom> selectCustomsList,Integer pass)
    {
        List<SelectCustom> newList=new ArrayList<SelectCustom>();
        if(selectCustomsList!=null)
        {
            for(SelectCustom selectCustom:selectCustomsList){
                if(selectCustom.getPass()==0){
                    newList.add(selectCustom);
                }
            }
            return newList;
        }else {
            return null;
        }
    }

    //进入第二志愿条件 之一course在第一志愿中没有出现
    public boolean Condition(Integer courseid) throws Exception {

        List<SelectCustom> selectList=new ArrayList<SelectCustom>();
        selectList=selectService.findByCourseid(courseid);
        List<SelectCustom> newList=new ArrayList<SelectCustom>();
        for(SelectCustom s:selectList){
            if(s.getPass()==0){
                newList.add(s);
            }
        }
        if(newList.size()==0){
            return true;
        }else{
            return false;
        }

    }
}
