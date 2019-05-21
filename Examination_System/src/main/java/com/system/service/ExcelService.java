package com.system.service;

import com.system.po.Student;
import com.system.po.StudentCustom;
import com.system.po.StudentExample;
import com.system.po.TeacherCustom;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

public interface ExcelService {

    public boolean insert(TeacherCustom teacherCustom);
    public boolean insert(StudentCustom studentCustom);


    String ajaxUploadTeacherExcel(HttpServletRequest request, HttpServletResponse response) throws Exception;
    String ajaxUploadStudentExcel(HttpServletRequest request, HttpServletResponse response) throws Exception;

    //
}
