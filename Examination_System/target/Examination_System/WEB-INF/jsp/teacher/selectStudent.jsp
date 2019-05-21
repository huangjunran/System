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
					    	<h1 style="text-align: center;">选择学生信息</h1>
						</div>
				    </div>
				    <div class="panel-body">
						<form class="form-horizontal" role="form" action="/teacher/selectStudent" id="editfrom" method="post">
							  <div class="form-group">
							    <label  class="col-sm-2 control-label">课题号</label>
							    <div class="col-sm-10">
							      <input readonly="readonly"  type="number" class="form-control"  value="${course.courseid}" name="courseid" placeholder="请输入课程号">
							    </div>
							  </div>
							  <div class="form-group">
							    <label  class="col-sm-2 control-label">课题名称</label>
									<div class="col-sm-10">
							      <input readonly="readonly" type="text" class="form-control"  name="coursename" value="${course.coursename}" placeholder="请输入课程名称">
							    </div>
							  </div>
							  <%--<div class="form-group">--%>
								  <%--<label   class="col-sm-2 control-label">指导老师</label>--%>
								  <%--<div class="col-sm-10">--%>
									  <%--<input readonly="readonly"  type="number" class="form-control"  value="${course.teacherid}" name="teacherid">--%>
								  <%--</div>--%>
							  <%--</div>--%>
							<div class="form-group">
								<label  readonly="readonly"  class="col-sm-2 control-label" >选课学生编号</label>
								<div class="col-sm-10">
									<c:choose>
										<c:when test="${course.studentid!=0}">
											<input readonly="readonly"  type="studentid" class="form-control"  value="${course.studentid}" name="studentid">
										</c:when>

										<c:otherwise >
										<select class="form-control" name="studentid">
											<c:forEach items="${studentList}" var="item">
												<option value="${item.userid}">${item.userid}  ${item.username}</option>
											</c:forEach>
										</select>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label" >所属院系</label>
								<div class="col-sm-10">
									<select readonly="readonly" class="form-control" name="collegeid">
										<c:forEach items="${collegeList}" var="item">
											<option value="${item.collegeid}">${item.collegename}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label readonly="readonly" class="col-sm-2 control-label">得分</label>
								<div class="col-sm-10">
									<input type="number" class="form-control" name="score" value="${course.score}" placeholder="请输入学分">
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

        var collegeSelect = $("#college option");
        for (var i=0; i<collegeSelect.length; i++) {
            if (collegeSelect[i].value == '${course.collegeid}') {
                collegeSelect[i].selected = true;
            }
        }
        var teacheridSelect = $("#teacherid option");
        for (var i=0; i<teacheridSelect.length; i++) {
            if (teacheridSelect[i].value == '${course.teacherid}') {
                teacheridSelect[i].selected = true;
            }
        }

	</script>
</html>