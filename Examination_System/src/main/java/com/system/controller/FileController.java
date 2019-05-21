package com.system.controller;

import com.system.po.SelectedCourseCustom;
import com.system.service.ExcelService;
import com.system.service.SelectedCourseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;


@Controller
@RequestMapping("/file")
public class FileController {

    @Resource(name = "excelServiceImpl")
    private ExcelService excelService;

    @Resource(name = "selectedCourseServiceImpl")
    private SelectedCourseService selectedCourseService;

    //excel导入
//    @ResponseBody
//    @RequestMapping(value="fileUpload", produces = "application/text; charset=utf-8")
//    public String UploadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        return excelService.ajaxUploadTeacherExcel(request, response);
//    }

    // 批量导入教师信息
    @RequestMapping(value="/ajaxUploadTeacher", produces = "application/text; charset=utf-8",method = {RequestMethod.POST})
    public void ajaxUploadExcel(@RequestParam("upfile") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg = excelService.ajaxUploadTeacherExcel(request, response);
        response.setContentType("text/html;charset=UTF-8");//这些设置必须要放在getWriter的方法之前，
        response.getWriter().print(msg);
    }

    //批量导入学生信息
    @RequestMapping(value="/ajaxUploadStudent", produces = "application/text; charset=utf-8",method = {RequestMethod.POST})
    public void ajaxUploadExcel2(@RequestParam("upfile") MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg = excelService.ajaxUploadStudentExcel(request, response);
        response.setContentType("text/html;charset=UTF-8");//这些设置必须要放在getWriter的方法之前，
        response.getWriter().print(msg);
    }


    //用户上传文件
    @RequestMapping(value="/uploadFile",produces = "application/text; charset=utf-8",method = {RequestMethod.POST})
    public String uploadFile(@RequestParam("upfile") MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获取年份
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));

        //文件名加工
        //获取文件名字
        String originnalFilename=file.getOriginalFilename();
        String fileName=year+"_"+originnalFilename.substring(0,originnalFilename.lastIndexOf("."));
        String newFileName=fileName+originnalFilename.substring(originnalFilename.lastIndexOf("."));

        //构建文件路径
        String uploadPath="C:\\Users\\Administrator\\Desktop\\BS\\file\\word"+File.separator;
       // System.out.println(uploadPath);
        File newfile=new File(uploadPath+newFileName);
        file.transferTo(newfile);



        //获取当前用户
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();

        SelectedCourseCustom selectedCourseCustom=selectedCourseService.findByStudentID(Integer.valueOf(username));

        selectedCourseCustom.setAds(uploadPath+newFileName);
        selectedCourseService.updataOne(selectedCourseCustom);

        response.setContentType("text/html;charset=UTF-8");//这些设置必须要放在getWriter的方法之前，
        response.getWriter().print("成功");
        return "student/overCourse";
    }

    @RequestMapping(value="/downloadFile", method = {RequestMethod.GET})
    public String downloadFile(int id, HttpServletResponse response)throws Exception {
       response.setCharacterEncoding("UTF-8");
//       路径
        String downloadPath="C:\\Users\\Administrator\\Desktop\\BS\\file\\download"+File.separator;
//        获取文件名
        SelectedCourseCustom selectedCourseCustom=selectedCourseService.findByStudentID(id);
        String ads=selectedCourseCustom.getAds();
        String filename=ads.substring(ads.lastIndexOf("\\")+1);
        Path path= Paths.get(ads);


        File file = new File(ads);
//        路径是否存在
        if(file.exists()){
//           添加文件后缀
            String fileSuffix=filename.substring(filename.lastIndexOf(".")+1);
            response.setContentType("application/"+fileSuffix);
            try{
                // 设置响应头，控制浏览器下载该文件
                response.setHeader("content-disposition", "attachment;filename="
                        + URLEncoder.encode(filename, "UTF-8"));
                // 读取要下载的文件，保存到文件输入流
                FileInputStream in = new FileInputStream(ads);
                // 创建输出流
                OutputStream out = response.getOutputStream();
                // 创建缓冲区
                byte buffer[] = new byte[1024];
                int len = 0;
                // 循环将输入流中的内容读取到缓冲区当中
                while ((len = in.read(buffer)) > 0) {
                    // 输出缓冲区的内容到浏览器，实现文件下载
                    out.write(buffer, 0, len);
                }
                // 关闭文件输入流
                in.close();
                // 关闭输出流
                out.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
                return "teacher/showCourse";


    }



//
}
