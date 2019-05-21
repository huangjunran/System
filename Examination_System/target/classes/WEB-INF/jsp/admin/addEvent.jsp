<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title></title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入bootstrap -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
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
                        <h1 style="text-align: center;">添加事件信息</h1>
                    </div>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="/admin/addEvent" id="editfrom" method="post">
                        <div class="form-group">
                            <label for="inputid" class="col-sm-2 control-label">事件序号</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="inputid" name="eventid" placeholder="请输入序号" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputtitle" class="col-sm-2 control-label">事件标题</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="inputtitle" name="title" placeholder="请输入标题" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label  class="col-sm-2 control-label" >开始时间</label>
                            <div class="col-sm-10">
                                <input id="inputtime1" name="starttime" class="form-control" size="16" type="text"
                                <c:if test='${event!=null}'>
                                       value="${event.starttime}"
                                </c:if> readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">结束时间</label>
                            <div class="col-sm-10">
                                <input id="inputtime2" name="endtime" class="form-control" size="16" type="text"
                                <c:if test='${event!=null}'>
                                       value="${event.endtime}"
                                </c:if> readonly>
                            </div>
                        </div>
                        <div class="form-group" style="text-align: center">

                            <button class="btn btn-default" type="submit">提交</button>
                            <button class="btn btn-default" type="reset">重置</button>

                        </div>
                    </form>
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
    $("#nav li:nth-child(1)").addClass("active");

    $('#inputtime1').datetimepicker({

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
    $('#inputtime2').datetimepicker({

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