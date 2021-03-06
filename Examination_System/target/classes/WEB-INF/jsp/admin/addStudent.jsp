<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
					    	<h1 style="text-align: center;">添加学生信息</h1>
						</div>
				    </div>
				    <div class="panel-body">
						<form class="form-horizontal" role="form" action="/admin/addStudent" id="editfrom" method="post">
							  <div class="form-group">
							    <label for="inputEmail3" class="col-sm-2 control-label">学号</label>
							    <div class="col-sm-10">
							      <input type="number" class="form-control" id="inputEmail3" name="userid" placeholder="请输入学号"
								  <%--<c:if test='${student!=null}'>--%>
										 <%--value="${student.userid}"--%>
								  <%--</c:if>--%>
								  >
							    </div>
							  </div>
							  <div class="form-group">
							    <label for="inputPassword3" class="col-sm-2 control-label">姓名</label>
							    <div class="col-sm-10">
							      <input type="text" class="form-control" id="inputPassword3" name="username" placeholder="请输入姓名"
								  <c:if test='${student!=null}'>
										 value="${student.username}"
								  </c:if>>
							    </div>
							  </div>
							  <div class="form-group">
							    <label for="inputPassword3" class="col-sm-2 control-label">性别</label>
							    <div class="col-sm-10">
								    <label class="checkbox-inline">
									   	<input type="radio" name="sex" value="男" checked>男
									</label>
									<label class="checkbox-inline">
										<input type="radio" name="sex" value="女">女
									</label>
							    </div>
							  </div>
							  <div class="form-group">
							    <label for="inputphone" class="col-sm-2 control-label">联系方式</label>
								  <div class="col-sm-10">
									  <input type="number" class="form-control" id="inputphone" name="phone" placeholder="请输入联系方式"
									  <c:if test='${student!=null}'>
											 value="${student.phone}"
									  </c:if>>
								  </div>
							  </div>
								  <div class="form-group">
									  <label  class="col-sm-2 control-label" >入学时间</label>
									  <div class="col-sm-10">
										  <input id="inputgrade" name="grade" class="form-control" size="16" type="text"
										  <c:if test='${student!=null}'>
												 value="${student.grade}"
										  </c:if>>
									  </div>
								  </div>
							  <div class="form-group">
							    <label for="inputPassword3" class="col-sm-2 control-label" >所属院系</label>
							    <div class="col-sm-10">
								    <select class="form-control" name="collegeid">
										<c:forEach items="${collegeList}" var="item">
											<option value="${item.collegeid}">${item.collegename}</option>
										</c:forEach>
								    </select>
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
		$("#nav li:nth-child(3)").addClass("active")
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