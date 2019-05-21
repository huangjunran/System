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
						<h1 class="col-md-5">事件列表</h1>
						<form class="bs-example bs-example-form col-md-5" role="form" style="margin: 20px 0 10px 0;" action="/teacher/selectEvent" id="form1" method="post">
							<div class="input-group">
								<input type="text" class="form-control" placeholder="请输入关键字" name="findByTitle">
								<span class="input-group-addon btn" onclick="document.getElementById('form1').submit" id="sub">搜索</span>
							</div>
						</form>


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
						</tr>
					</c:forEach>
					</tbody>
				</table>
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


	$("#sub").click(function () {
		$("#form1").submit();
	});
</script>
</html>