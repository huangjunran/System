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
						<h1 style="text-align: center;">添加课题信息</h1>
					</div>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" role="form" action="/admin/addCourse" id="editfrom" method="post">
						<div class="form-group">
							<label for="inputid" class="col-sm-2 control-label">课题号</label>
							<div class="col-sm-10">
								<input type="number" class="form-control" id="inputid" name="courseid" placeholder="请输入课题号">
							</div>
						</div>
						<div class="form-group">
							<label for="inputtitle" class="col-sm-2 control-label">课题名称</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="inputtitle" name="coursename" placeholder="请输入课题名称">
							</div>
						</div>
						<div class="form-group">
							<label  class="col-sm-2 control-label" >指导老师编号</label>
							<div class="col-sm-10">
								<select class="form-control" name="teacherid">
									<c:forEach items="${teacherList}" var="item">
										<option value="${item.userid}">${item.username}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label  class="col-sm-2 control-label" name="grade">选课学生编号</label>
							<div class="col-sm-10">
								<select class="form-control" name="studentid">
									<c:forEach items="${stdentList}" var="item">
										<option value="${item.userid}">${item.username}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label  class="col-sm-2 control-label" name="grade">所属院系</label>
							<div class="col-sm-10">
								<select class="form-control" name="collegeid">
									<c:forEach items="${collegeList}" var="item">
										<option value="${item.collegeid}">${item.collegename}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label  class="col-sm-2 control-label">得分</label>
							<div class="col-sm-10">
								<input type="number" class="form-control" name="score" placeholder="请输入得分">
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
    $("#nav li:nth-child(2)").addClass("active")


</script>
</html>