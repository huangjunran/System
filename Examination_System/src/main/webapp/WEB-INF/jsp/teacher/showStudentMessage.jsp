<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>


<!DOCTYPE html>
<html>
<head>
    <title></title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入bootstrap -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/css/bootstrap-datetimepicker.min.css"  />

    <!-- 引入JQuery  bootstrap.js-->
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/bootstrap-datetimepicker.js"></script>
    <script src="/js/bootstrap-datepicker.zh-CN.js"></script>
</head>
<body>
<!-- 顶栏 -->
<jsp:include page="top.jsp"></jsp:include>
<!-- 中间主体 -->
<div class="container" id="content">
    <div class="row">
        <jsp:include page="menu.jsp"></jsp:include>
        <div class="col-md-10">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <h1 style="text-align: center;">学生信息</h1>
                    </div>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>学号</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>联系方式</th>
                            <th>入学时间</th>
                            <th>学院</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${student.userid}</td>
                                <td>${student.username}</td>
                                <td>${student.sex}</td>
                                <td>${student.phone}</td>
                                <td><fmt:formatDate value="${student.grade}" dateStyle="medium" /></td>
                                <td>${student.getcollegeName()}</td>
                            </tr>
                        </tbody>
                    </table>
                    
                </div>

            </div>

        </div>
    </div>
</div>
<div class="container" id="footer">
    <div class="row">
        <div class="col-md-12"></div>
    </div>
</div>
</body>
<script type="text/javascript">

    $("#nav li:nth-child(2)").addClass("active")

    var collegeSelect = $("#college option");
    for (var i=0; i<collegeSelect.length; i++) {
        if (collegeSelect[i].value == '${student.collegeid}') {
            collegeSelect[i].selected = true;
        }
    };
    $('#inputgrade').datetimepicker({

        format: 'yyyy-mm-dd',
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        initialDate:new Date(),
    });
</script>
</html>