package com.system.controller;

import com.system.exception.CustomException;
import com.system.po.*;
import com.system.service.*;


import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.annotation.Resource;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource(name = "studentServiceImpl")
    private StudentService studentService;

    @Resource(name = "teacherServiceImpl")
    private TeacherService teacherService;

    @Resource(name = "courseServiceImpl")
    private CourseService courseService;

    @Resource(name = "collegeServiceImpl")
    private CollegeService collegeService;

    @Resource(name = "eventServiceImpl")
    private EventService eventService;

    @Resource(name = "userloginServiceImpl")
    private UserloginService userloginService;

    @Resource(name = "selectServiceImpl")
    private SelectService selectService;

     @Resource(name = "selectedCourseServiceImpl")
    private SelectedCourseService selectedCourseService;


     /*-------------------------发送邮件-------------------*/
     @Autowired
     private JavaMailSender javaMailSender;
     @Value("${mail.smtp.username}")
     private String emailFrom;


    public void senMsg(String toEmail ,String subject,String text){
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人的邮箱地址
        message.setFrom(emailFrom);
        //收件人的邮箱地址
        message.setTo(toEmail);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(text);
        //发送邮件
        javaMailSender.send(message);
    }

    /*------------------------操作学生-------------------*/
    //  学生信息显示
    @RequestMapping("/showStudent")
    public String showStudent(Model model, Integer page) throws Exception {

        List<StudentCustom> list = null;
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(studentService.getCountStudent());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = studentService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = studentService.findByPaging(page);
        }

        model.addAttribute("studentList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showStudent";

    }

    //  添加学生信息页面显示
    @RequestMapping(value = "/addStudent", method = {RequestMethod.GET})
    public String addStudentUI(Model model) throws Exception {

        List<College> list = collegeService.finAll();
//        StudentCustom studentCustom=new StudentCustom();
//        model.addAttribute("student", studentCustom);
        model.addAttribute("collegeList", list);

        return "admin/addStudent";
    }

    // 添加学生信息操作
    @RequestMapping(value = "/addStudent", method = {RequestMethod.POST})
    public String addStudent(StudentCustom studentCustom, Model model) throws Exception {

        Boolean result = studentService.save(studentCustom);

        if (!result) {
            model.addAttribute("message", "学号重复");
            return "error";
        }
        //添加成功后，也添加到登录表
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(studentCustom.getUserid().toString());
        userlogin.setPassword("123");
        userlogin.setRole(2);
        userloginService.save(userlogin);

        //重定向
        return "redirect:/admin/showStudent";
    }

    // 修改学生信息页面显示
    @RequestMapping(value = "/editStudent", method = {RequestMethod.GET})
    public String editStudentUI(Integer id, Model model) throws Exception {
        if (id == null) {
            //加入没有带学生id就进来的话就返回学生显示页面
            return "redirect:/admin/showStudent";
        }
        StudentCustom studentCustom = studentService.findById(id);
        if (studentCustom == null) {
            throw new CustomException("未找到该名学生");
        }
        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);
        model.addAttribute("student", studentCustom);


        return "admin/editStudent";
    }

    // 修改学生信息处理
    @RequestMapping(value = "/editStudent", method = {RequestMethod.POST})
    public String editStudent(StudentCustom studentCustom) throws Exception {

        studentService.updataById(studentCustom.getUserid(), studentCustom);

        //重定向
        return "redirect:/admin/showStudent";
    }

    // 删除学生
    @RequestMapping(value = "/removeStudent", method = {RequestMethod.GET})
    private String removeStudent(Integer id) throws Exception {
        if (id == null) {
            //加入没有带学生id就进来的话就返回学生显示页面
            return "admin/showStudent";
        }
        //删除select信息
        List<SelectCustom> selectList=new ArrayList<SelectCustom>();
        selectList=selectService.findByStudentid(id);
        if(selectList!=null&&selectList.size()>0){
            for(SelectCustom s:selectList)
            {
                selectService.remove(s);
            }
        }
        //删除selected信息
        SelectedCourseCustom selectedcourse=selectedCourseService.findByStudentID(id);
        if(selectedcourse!=null){
            selectedCourseService.remove(selectedcourse);
            }
        //删除成员信息
        studentService.removeById(id);
        userloginService.removeByName(id.toString());


        return "redirect:/admin/showStudent";
    }

    // 搜索学生
    @RequestMapping(value = "selectStudent", method = {RequestMethod.POST})
    private String selectStudent(String findByName, Model model) throws Exception {

        List<StudentCustom> list = studentService.findByName(findByName);

        model.addAttribute("studentList", list);
        return "admin/showStudent";
    }

    /*------------------------操作事件-------------------*/
// 事件页面显示
    @RequestMapping("/showEvent")
    public String showEvent(Model model, Integer page) throws Exception {

        System.out.println("----------------------------");
        List<EventCustom> list = new ArrayList<EventCustom>();
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数

        pagingVO.setTotalCount(eventService.getCountEvent());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = eventService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = eventService.findByPaging(page);
        }

        model.addAttribute("eventList", list);

        model.addAttribute("pagingVO", pagingVO);

        return "admin/showEvent";
    }

    // 添加事件信息
    @RequestMapping(value = "/addEvent", method = {RequestMethod.GET})
    public String addEventUI(Model model) throws Exception {

        return "admin/addEvent";
    }

    // 添加事件信息处理
    @RequestMapping(value = "/addEvent", method = {RequestMethod.POST})
    public String addEvent(EventCustom eventCustom, Model model) throws Exception {
        Boolean result = eventService.save(eventCustom);

        if (!result) {
            model.addAttribute("message", "序号重复");
            return "error";
        }

        //重定向
        return "redirect:/admin/showEvent";
    }

    // 修改事件信息页面显示
    @RequestMapping(value = "/editEvent", method = {RequestMethod.GET})
    public String editEventUI(String id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showEvent";
        }
        EventCustom eventCustom = eventService.findById(id);

        model.addAttribute("event", eventCustom);
        return "admin/editEvent";
    }

    // 修改事件信息页面处理
    @RequestMapping(value = "/editEvent", method = {RequestMethod.POST})
    public String editEvnet(EventCustom eventCustom) throws Exception {

        eventService.updateById(eventCustom.geteventid(), eventCustom);

        //重定向
        return "redirect:/admin/showEvent";
    }

    //更改executed
    @RequestMapping(value = "/executedChange")
    public String executedChange(String id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showEvent";
        }
        EventCustom eventCustom = eventService.findById(id);
        //结束1轮选题开始随机匹配

        if(eventCustom.geteventid().equals(("3"))&&eventCustom.getExecuted()==1){
            //select表第一志愿自动匹配
            selectAutomatch(0);
        }
        //教师结束选择学生后
        if(eventCustom.geteventid().equals(("4"))&&eventCustom.getExecuted()==1){
            //select表第2志愿自动匹配
            selectAutomatch(1);
            //select表第3志愿自动匹配
            selectAutomatch(2);
        }

        if(eventCustom.geteventid().equals(("5"))&&eventCustom.getExecuted()==1){
            //select表第一志愿自动匹配
            selectAutomatch(0);
        }
        //教师结束选择学生后
        if(eventCustom.geteventid().equals(("6"))&&eventCustom.getExecuted()==1){
            //select表第2志愿自动匹配
            selectAutomatch(1);
            //select表第3志愿自动匹配
            selectAutomatch(2);
        }

        if(eventCustom.getExecuted()==0) {
            eventCustom.setExecuted(1);
            eventService.updateById(eventCustom.geteventid(), eventCustom);

            if(eventCustom.geteventid().equals(("2"))){
                List<TeacherCustom> list=teacherService.findAll();
                for(TeacherCustom t:list){
                    senMsg(t.getMail(),"设置选题","设置选题题目任务已经开始，请登录系统进行设置");
                }
            }
            if(eventCustom.geteventid().equals(("4"))){
                List<TeacherCustom> list=teacherService.findAll();
                for(TeacherCustom t:list){
                    senMsg(t.getMail(),"一轮选题","第一轮选择选题学生任务已经开始，请登录系统进行设置");
                }
            }
            if(eventCustom.geteventid().equals(("6"))){
                List<TeacherCustom> list=teacherService.findAll();
                for(TeacherCustom t:list){
                    senMsg(t.getMail(),"二轮选题","第一轮选择选题学生任务已经开始,请于前登录系统进行设置");
                }
            }
        }else if(eventCustom.getExecuted()==1){
            eventCustom.setExecuted(2);
        }
        else{
            //当事件1重置时,重置事件
            if(eventCustom.geteventid().equals("1")){
                List<EventCustom> list=eventService.findAll();
                for(EventCustom e:list){
                    e.setExecuted(0);
                    eventService.updateById(e.geteventid(),e);
                }
                //重置数据库
                selectedCourseService.removeAll();
                selectService.removeAll();
                courseService.removeAll();
                studentService.removeAll();
                teacherService.removeAll();
                userloginService.removeExecpetAdmin();
            }
            eventCustom.setExecuted(0);
        }

        eventService.updateById(eventCustom.geteventid(), eventCustom);
        return "redirect:/admin/showEvent";
    }





    //删除事件
    @RequestMapping("/removeEvent")
    public String removeEvent(String id) throws Exception {
        if (id == null) {
            //加入没有带事件id就进来的话就返回教师显示页面
            return "admin/showEvent";
        }
        eventService.removeById(id);

        return "redirect:/admin/showEvent";
    }

    //搜索事件
    @RequestMapping(value = "selectEvent", method = {RequestMethod.POST})
    private String selectEvent(String findByTitle, Model model) throws Exception {
        List<EventCustom> list = eventService.findByTitle(findByTitle);

        System.out.println(list.size());
        model.addAttribute("eventList", list);
        return "admin/showEvent";
    }


    /*------------------------操作教师----------------------------------------------------------------------------------*/
    // 教师页面显示
    @RequestMapping("/showTeacher")
    public String showTeacher(Model model,Integer page) throws Exception {

        List<TeacherCustom> list = new ArrayList<TeacherCustom>();
        //页码对象
        PagingVO pagingVO = new PagingVO();
        //设置总页数
        pagingVO.setTotalCount(teacherService.getCountTeacher());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = teacherService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = teacherService.findByPaging(page);
        }

        model.addAttribute("teacherList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showTeacher";

    }

    // 添加教师信息
    @RequestMapping(value = "/addTeacher", method = {RequestMethod.GET})
    public String addTeacherUI(Model model) throws Exception {

        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);

        return "admin/addTeacher";
    }

    // 添加教师信息处理
    @RequestMapping(value = "/addTeacher", method = {RequestMethod.POST})
    public String addTeacher(TeacherCustom teacherCustom, Model model) throws Exception {

        Boolean result = teacherService.save(teacherCustom);

        if (!result) {
            model.addAttribute("message", "工号重复");
            return "error";
        }
        //添加成功后，也添加到登录表
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(teacherCustom.getUserid().toString());
        userlogin.setPassword("123");
        userlogin.setRole(1);
        userloginService.save(userlogin);

        for (int i = 1; i <= teacherCustom.getTitleCount(); i++) {
            CourseCustom course = new CourseCustom();
            int id = i + teacherCustom.getUserid() * 100;
            course.setCourseid(id);
            course.setCoursename("");
            course.setTeacherid(teacherCustom.getUserid());
            course.setStudentid(0);
            course.setCollegeid(teacherCustom.getCollegeid());
            course.setScore(0);
            course.setPass(0);
            courseService.save(course);
        }


        //重定向
        return "redirect:/admin/showTeacher";
    }

    // 修改教师信息页面显示
    @RequestMapping(value = "/editTeacher", method = {RequestMethod.GET})
    public String editTeacherUI(Integer id, Model model) throws Exception {


        if (id == null) {
            return "redirect:/admin/showTeacher";
        }
        TeacherCustom teacherCustom = teacherService.findById(id);
        if (teacherCustom == null) {
            throw new CustomException("未找到该名教师");
        }
        List<College> list = collegeService.finAll();

        model.addAttribute("collegeList", list);
        model.addAttribute("teacher", teacherCustom);


        return "admin/editTeacher";
    }

    // 修改教师信息页面处理
    @RequestMapping(value = "/editTeacher", method = {RequestMethod.POST})
    public String editTeacher(TeacherCustom teacherCustom) throws Exception {

//        int countNum=courseService.getCountByTeacherID(teacherCustom.getUserid());
//        if(teacherCustom.getTitleCount()!=countNum)
//        {
//            if(courseService.removeById(teacherCustom.getUserid()))
//            {
//                for(int i=1;i<=teacherCustom.getTitleCount();i++) {
//                    CourseCustom course=new CourseCustom();
//                    int id=i+teacherCustom.getUserid()*1000000;a
//                    course.setCourseid(id);
//                    course.setTeacherid(teacherCustom.getUserid());
//
//                    Boolean result2=courseService.save(course);
//                    System.out.println("成功添加"+result2+"--------");
//                }
//
//            }
//        }
        teacherService.updateById(teacherCustom.getUserid(), teacherCustom);

        //重定向
        return "redirect:/admin/showTeacher";
    }

    //删除教师
    @RequestMapping("/removeTeacher")
    public String removeTeacher(Integer id) throws Exception {
        if (id == null) {
            //加入没有带教师id就进来的话就返回教师显示页面
            return "admin/showTeacher";
        }
        teacherService.removeById(id);
        userloginService.removeByName(id.toString());

        return "redirect:/admin/showTeacher";
    }

    //搜索教师
    @RequestMapping(value = "selectTeacher", method = {RequestMethod.POST})
    private String selectTeacher(String findByName, Model model) throws Exception {

        List<TeacherCustom> list = teacherService.findByName(findByName);

        model.addAttribute("teacherList", list);
        return "admin/showTeacher";
    }

    /*------------------------操作课程-------------------*/

    // 课程信息显示
    @RequestMapping("/showCourse")
    public String showCourse(Model model,Integer page) throws Exception {

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
        int length = list.size();
        System.out.println(length);

        model.addAttribute("courseList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "admin/showCourse";

    }

    //添加课程
    @RequestMapping(value = "/addCourse", method = {RequestMethod.GET})
    public String addCourseUI(Model model) throws Exception {

        List<TeacherCustom> list = teacherService.findAll();
        List<College> collegeList = collegeService.finAll();

        model.addAttribute("collegeList", collegeList);
        model.addAttribute("teacherList", list);

        return "admin/addCourse";
    }

    // 添加课程信息处理
    @RequestMapping(value = "/addCourse", method = {RequestMethod.POST})
    public String addCourse(CourseCustom courseCustom, Model model) throws Exception {

        courseCustom.setPass(0);
        Boolean result = courseService.save(courseCustom);

        if (!result) {
            model.addAttribute("message", "课程号重复");
            return "error";
        }
        //重定向
        return "redirect:/admin/showCourse";
    }

    // 修改教师信息页面显示
    @RequestMapping(value = "/editCourse", method = {RequestMethod.GET})
    public String editCourseUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showCourse";
        }
        CourseCustom courseCustom = courseService.findById(id);
        if (courseCustom == null) {
            throw new CustomException("未找到该课程");
        }
        List<TeacherCustom> list = teacherService.findAll();
        List<College> collegeList = collegeService.finAll();

//
////        已有选题学生列表
//        List<StudentCustom> newList=new ArrayList<StudentCustom>();
////        无课题学生列表
//        List<StudentCustom> newList2=new ArrayList<StudentCustom>();
//        List<StudentCustom> studentList=studentService.findAll();
//        List<SelectedCourseCustom> selectedCourseCustomList=selectedCourseService.findAll();
//        for(StudentCustom stu: studentList){
//            for(SelectedCourseCustom sel: selectedCourseCustomList){
//                if(sel.getStudentid().equals(stu.getUserid())){
//                    newList.add(stu);
//                }
//            }
//        }
//        for(StudentCustom stu: studentList){
//            for(StudentCustom sel: newList){
//               if(stu.getUserid().equals(sel.getUserid())){
//                   studentList.remove(stu);
//               }
//            }
//        }



        model.addAttribute("teacherList", list);
        model.addAttribute("collegeList", collegeList);
//        model.addAttribute("studentList", newList2);
        model.addAttribute("course", courseCustom);
        return "admin/editCourse";
    }

    // 修改信息页面处理
    @RequestMapping(value = "/editCourse", method = {RequestMethod.POST})
    public String editCourse(CourseCustom courseCustom) throws Exception {

        courseService.upadteById(courseCustom.getCourseid(), courseCustom);

        if(!courseCustom.getStudentid().equals(0)){
          //更新course表中studentID信息

            SelectedCourseCustom newselected = new SelectedCourseCustom();
            newselected.setCourseid(courseCustom.getCourseid());
            newselected.setStudentid(courseCustom.getStudentid());
            newselected.setAds("");
            selectedCourseService.save(newselected);

            //删除selecttable中关于studentid的所有信息,以及courseid的所有信息

            List<SelectCustom> newselectlist = new ArrayList<SelectCustom>();
            newselectlist = selectService.findAll();
            for (SelectCustom s : newselectlist) {
                if (s.getStudentid().equals(courseCustom.getStudentid())) {
                    selectService.remove(s);
                }
                if (s.getCourseid() == courseCustom.getCourseid()) {
                    selectService.remove(s);
                }
            }
        }
        //重定向
        return "redirect:/admin/showCourse";
    }

    //审题通过处理
    @RequestMapping(value = "/passCourse", method = {RequestMethod.GET})
    public String passCourse(Integer id) throws Exception {
        if (id == null) {
            return "redirect:/admin/showCourse";
        }
        CourseCustom courseCustom = courseService.findById(id);

        int num = courseCustom.getPass();
        if (0 != num) {
            courseCustom.setPass(0);
        } else {
            courseCustom.setPass(1);
        }
        courseService.upadteById(id, courseCustom);

        //重定向
        return "redirect:/admin/showCourse";
    }

    //一键审题通过处理
    @RequestMapping(value = "/coursePass")
    public String coursePass() throws Exception {
        List<CourseCustom> courseCustomList = courseService.findAll();
        for(CourseCustom c:courseCustomList) {
        if(c.getCoursename()!="") {
            c.setPass(1);
        }
            courseService.upadteById(c.getCourseid(), c);
        }

        //重定向
        return "redirect:/admin/showCourse";
    }



    // 删除课程信息
    @RequestMapping("/removeCourse")
    public String removeCourse(Integer id) throws Exception {
        if (id == null) {
            //加入没有带教师id就进来的话就返回教师显示页面
            return "admin/showCourse";
        }
        courseService.removeById(id);

        return "redirect:/admin/showCourse";
    }



    //搜索课程
    @RequestMapping(value = "selectCourse", method = {RequestMethod.POST})
    private String selectCourse(String findByName, Model model) throws Exception {

        List<CourseCustom> list = courseService.findByName(findByName);

        model.addAttribute("courseList", list);
        return "admin/showCourse";
    }

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<其他操作>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    // 普通用户账号密码重置
    @RequestMapping("/userPasswordRest")
    public String userPasswordRestUI() throws Exception {
        return "admin/userPasswordRest";
    }

    // 普通用户账号密码重置处理
    @RequestMapping(value = "/userPasswordRest", method = {RequestMethod.POST})
    public String userPasswordRest(Userlogin userlogin) throws Exception {

        Userlogin u = userloginService.findByName(userlogin.getUsername());

        if (u != null) {
            if (u.getRole() == 0) {
                throw new CustomException("该账户为管理员账户，没法修改");
            }
            u.setPassword(userlogin.getPassword());
            userloginService.updateByName(userlogin.getUsername(), u);
        } else {
            throw new CustomException("没找到该用户");
        }

        return "admin/userPasswordRest";
    }

    // 本账户密码重置
    @RequestMapping("/passwordRest")
    public String passwordRestUI() throws Exception {
        return "admin/passwordRest";
    }









    // 一轮匹配
    @RequestMapping("/startMapping")
    public String startMapping() throws Exception {

        return "redirect:/admin/showCourse";
    }




    //select表第一志愿自动匹配
    public void selectAutomatch(Integer pass) throws Exception {

        //根据教师表查询教师id
        List<TeacherCustom> teacherlist = new ArrayList<TeacherCustom>();
        teacherlist = teacherService.findAll();
        //根据教师id查询 course中的courseid
        for (TeacherCustom teacher : teacherlist) {
            List<CourseCustom> courselist = new ArrayList<CourseCustom>();
            courselist = courseService.findByTeacherID(teacher.getUserid());

            //根据courseid查询 select中的信息
            for (CourseCustom course : courselist) {

                //查询出某教师 其中某课题 的学生选题列表 selectlist
                List<SelectCustom> selectlist = new ArrayList<SelectCustom>();
                selectlist = selectService.findByCourseid(course.getCourseid());

                //分出志愿
                List<SelectCustom> firstselectlist = new ArrayList<SelectCustom>();
                for (SelectCustom s : selectlist) {
                    if (s.getPass() == pass) {
                        firstselectlist.add(s);
                    }
                }
                if (firstselectlist.size() == 1) {
                    SelectCustom onlySelect = new SelectCustom();
                    onlySelect = firstselectlist.get(0);
                    //第一志愿只有一人
                    //更新course表中studentID信息
                    //更新selectedcourse表所有信息
                    CourseCustom newcustom = new CourseCustom();
                    newcustom = courseService.findById(onlySelect.getCourseid());
                    newcustom.setStudentid(onlySelect.getStudentid());
                    courseService.upadteById(onlySelect.getCourseid(), newcustom);

                    SelectedCourseCustom newselected = new SelectedCourseCustom();
                    newselected.setCourseid(onlySelect.getCourseid());
                    newselected.setStudentid(onlySelect.getStudentid());
                    newselected.setAds("");
                    selectedCourseService.save(newselected);

                    //删除selecttable中关于studentid的所有信息,以及courseid的所有信息

                    List<SelectCustom> newselectlist = new ArrayList<SelectCustom>();
                    newselectlist = selectService.findAll();
                    for (SelectCustom s : newselectlist) {
                        if (s.getStudentid().equals(onlySelect.getStudentid())) {
                            selectService.remove(s);
                        }
                        if (s.getCourseid() == onlySelect.getCourseid()) {
                            selectService.remove(s);
                        }
                    }
                }
            }
        }
    }
}

