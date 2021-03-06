<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>事件信息显示</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入bootstrap -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <!-- 引入JQuery  bootstrap.js-->
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>

    <%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>

</head>
<body>
<!-- 顶栏 -->

<!-- 中间主体 --><jsp:include page="top.jsp"></jsp:include>
<div class="container" id="content">
    <div class="row">
        <jsp:include page="menu.jsp"></jsp:include>
        <div class="col-md-10">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <h1 class="col-md-5">事件列表管理</h1>
                        <form class="bs-example bs-example-form col-md-5" role="form" style="margin: 20px 0 10px 0;" action="/admin/selectEvent" id="form1" method="post">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="请输入关键字" name="findByTitle">
                                <span class="input-group-addon btn" onclick="document.getElementById('form1').submit" id="sub">搜索</span>
                            </div>
                        </form>
                        <button class="btn btn-default col-md-2" style="margin-top: 20px" onClick="location.href='/admin/addEvent'">
                            添加事件信息
                            <sapn class="glyphicon glyphicon-plus"/>
                        </button>

                    </div>
                </div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>事件编号</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>事件内容</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach  items="${eventList}" var="item">
                        <tr>
                            <td>${item.eventid}</td>
                            <td><fmt:formatDate value="${item.starttime}" dateStyle="medium" /></td>
                            <td><fmt:formatDate value="${item.endtime}" dateStyle="medium" /></td>
                            <td>${item.title}</td>
                            <c:choose>
                                <c:when test="${item.executed==1}"><td style="color:blue">正在执行</td></c:when>
                                <c:when test="${item.executed==0}"><td>未开始</td></c:when>
                                <c:otherwise><td style="color: red">已结束</td></c:otherwise>
                            </c:choose>

                            <td>
                                <button class="btn btn-default btn-xs btn-info" onClick="location.href='/admin/editEvent?id=${item.eventid}'">修改</button>
                                <c:choose>
                                    <c:when test="${item.executed==0}">
                                        <button class="btn btn-default btn-xs btn-info "  onClick="location.href='/admin/executedChange?id=${item.eventid}'">开始</button>
                                    </c:when>
                                     <c:when test="${item.executed==1}">
                                        <button class="btn btn-default btn-xs btn-warning "  onClick="location.href='/admin/executedChange?id=${item.eventid}'">结束</button>
                                    </c:when>
                                     <c:otherwise>
                                         <button class="btn btn-default btn-xs btn-primary" onClick="location.href='/admin/executedChange?id=${item.eventid}'">重置</button>
                                     </c:otherwise>
                                 </c:choose>
                                <button class="btn btn-default btn-xs btn-danger btn-primary" onClick="location.href='/admin/removeEvent?id=${item.eventid}'">删除</button>
                                <!--弹出框-->
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="panel-footer">
                    <c:if test="${pagingVO != null}">
                        <nav style="text-align: center">
                            <ul class="pagination">
                                <li><a href="/admin/showEvent?page=${pagingVO.upPageNo}">&laquo;上一页</a></li>
                                <li class="active"><a href="">${pagingVO.curentPageNo}</a></li>
                                <c:if test="${pagingVO.curentPageNo+1 <= pagingVO.totalCount}">
                                    <li><a href="/admin/showEvent?page=${pagingVO.curentPageNo+1}">${pagingVO.curentPageNo+1}</a></li>
                                </c:if>
                                <c:if test="${pagingVO.curentPageNo+2 <= pagingVO.totalCount}">
                                    <li><a href="/admin/showEvent?page=${pagingVO.curentPageNo+2}">${pagingVO.curentPageNo+2}</a></li>
                                </c:if>
                                <c:if test="${pagingVO.curentPageNo+3 <= pagingVO.totalCount}">
                                    <li><a href="/admin/showEvent?page=${pagingVO.curentPageNo+3}">${pagingVO.curentPageNo+3}</a></li>
                                </c:if>
                                <c:if test="${pagingVO.curentPageNo+4 <= pagingVO.totalCount}">
                                    <li><a href="/admin/showEvent?page=${pagingVO.curentPageNo+4}">${pagingVO.curentPageNo+4}</a></li>
                                </c:if>
                                <li><a href="/admin/showEvent?page=${pagingVO.totalCount}">最后一页&raquo;</a></li>
                            </ul>
                        </nav>
                    </c:if>
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
    $("#nav li:nth-child(1)").addClass("active")

    <c:if test="${pagingVO != null}">
    if (${pagingVO.curentPageNo} == ${pagingVO.totalCount}) {
        $(".pagination li:last-child").addClass("disabled")
    };

    if (${pagingVO.curentPageNo} == ${1}) {
        $(".pagination li:nth-child(1)").addClass("disabled")
    };
    </c:if>

    function confirmd() {
        var msg = "您真的确定要删除吗？！";
        if (confirm(msg)==true){
            return true;
        }else{
            return false;
        }
    }

    $("#sub").click(function () {
        $("#form1").submit();
    });
</script>
</html>