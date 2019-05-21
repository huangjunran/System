package com.system.service.impl;

import com.system.mapper.*;
import com.system.po.*;
import com.system.service.ExcelService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.ColorUIResource;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UserloginMapper userloginMapper;

    @Override
    public boolean insert(TeacherCustom teacherCustom) {

        if(teacherMapper.insert(teacherCustom)==0){
            return false;
        }
        else{
            return true;
        }
    }


    @Override
    public boolean insert(StudentCustom studentCustom) {
        if(studentMapper.insert(studentCustom)==0){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String ajaxUploadTeacherExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("upfile");
            if(file.isEmpty()){
                try {
                    throw new Exception("文件不存在！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            InputStream in =null;
            try {
                in = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<List<Object>> listob = null;
            try {
                listob = new ExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
            } catch (Exception e) {
                e.printStackTrace();
            }

            //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
            for (int i = 0; i < listob.size(); i++) {
                List<Object> lo = listob.get(i);
                TeacherCustom vo = new TeacherCustom();
            /*这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            User j = null;
			try {
				j = userMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("没有新增");
			}*/
                //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了

                vo.setUserid(Integer.valueOf(String.valueOf(lo.get(0))));     // 表格的第一列   注意数据格式需要对应实体类属性
                vo.setUsername(String.valueOf(lo.get(1)));   // 表格的第二列
                vo.setSex(String.valueOf(lo.get(2)));
                vo.setMail(String.valueOf(lo.get(3)));
                vo.setPhone(String.valueOf(lo.get(4)));
                vo.setTitle(String.valueOf(lo.get(5)));
                vo.setTitleCount(Integer.valueOf(String.valueOf(lo.get(6))));
                vo.setCollegeid(Integer.valueOf(String.valueOf(lo.get(7))));


                //vo.setRegTime(Date.valueOf(String.valueOf(lo.get(2))));
                //由于数据库中此字段是datetime，所以要将字符串时间格式：yyyy-MM-dd HH:mm:ss，转为Date类型
//                if (lo.get(2) != null && lo.get(2) != "") {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    vo.setRegTime(sdf.parse(String.valueOf(lo.get(2))));
//                }else {
//                    vo.setRegTime(new Date());
//                }
//                System.out.println("从excel中读取的实体类对象："+ vo);
                teacherMapper.insert(vo);
//			if(j == null)
////			{
////		            userMapper.insert(vo);
////			}
////			else
////			{
////		            userMapper.updateByPrimaryKey(vo);
////			}


                //添加课题
                if(vo.getTitleCount()>0){
                    for(int j=1;j<=vo.getTitleCount();j++){
                        Course course=new Course();
                        Integer teacherid=vo.getUserid();
                        course.setCourseid(teacherid*100+j);
                        course.setCoursename("");
                        course.setTeacherid(teacherid);
                        course.setStudentid(0);
                        course.setCollegeid(vo.getCollegeid());
                        course.setScore(0);
                        course.setPass(0);

                      courseMapper.insert(course);
                    }
                }
                //添加登录
                Userlogin userLogin=new Userlogin();
                userLogin.setUsername(vo.getUserid().toString());
                userLogin.setPassword("123");
                userLogin.setRole(1);


                userloginMapper.insert(userLogin);
            }
            return "文件导入成功！";
    }

    @Override
    public String ajaxUploadStudentExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("upfile");
        if(file.isEmpty()){
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        InputStream in =null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            StudentCustom vo = new StudentCustom();
            /*这里是主键验证，根据实际需要添加，可要可不要，加上之后，可以对现有数据进行批量修改和导入
            User j = null;
			try {
				j = userMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(lo.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("没有新增");
			}*/
            //vo.setUserId(Integer.valueOf(String.valueOf(lo.get(0))));  // 刚开始写了主键，由于主键是自增的，又去掉了，现在只有批量插入的功能，不能对现有数据进行修改了

            vo.setUserid(Integer.valueOf(String.valueOf(lo.get(0))));     // 表格的第一列   注意数据格式需要对应实体类属性
            vo.setUsername(String.valueOf(lo.get(1)));   // 表格的第二列
            vo.setSex(String.valueOf(lo.get(2)));
            vo.setPhone(Integer.valueOf(String.valueOf(lo.get(3))));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            date=format.parse(String.valueOf(lo.get(4)));
            vo.setGrade(date);
            vo.setCollegeid(Integer.valueOf(String.valueOf(lo.get(5))));


            //vo.setRegTime(Date.valueOf(String.valueOf(lo.get(2))));
            //由于数据库中此字段是datetime，所以要将字符串时间格式：yyyy-MM-dd HH:mm:ss，转为Date类型
//                if (lo.get(2) != null && lo.get(2) != "") {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    vo.setRegTime(sdf.parse(String.valueOf(lo.get(2))));
//                }else {
//                    vo.setRegTime(new Date());
//                }
//                System.out.println("从excel中读取的实体类对象："+ vo);
            studentMapper.insert(vo);
//			if(j == null)
////			{
////		            userMapper.insert(vo);
////			}
////			else
////			{
////		            userMapper.updateByPrimaryKey(vo);
////			}
            //添加登录
            Userlogin userLogin=new Userlogin();
            userLogin.setUsername(vo.getUserid().toString());
            userLogin.setPassword("123");
            userLogin.setRole(2);
            userloginMapper.insert(userLogin);
        }
        return "文件导入成功！";
    }


}