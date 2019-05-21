package com.system.po;

public class SelectCustom extends Select {
    //新增Student 对象字段
    private StudentCustom studentCustom;
    private TeacherCustom teacherCustom;
    private CourseCustom courseCustom;


    public CourseCustom getCourseCustom() {
        return courseCustom;
    }

    public void setCourseCustom(CourseCustom courseCustom) {
        this.courseCustom = courseCustom;
    }

    public StudentCustom getStudentCustom() {
        return studentCustom;
    }

    public void setStudentCustom(StudentCustom studentCustom) {
        this.studentCustom = studentCustom;
    }

    public TeacherCustom getTeacherCustom() {
        return teacherCustom;
    }

    public void setTeacherCustom(TeacherCustom teacherCustom) {
        this.teacherCustom = teacherCustom;
    }
}
