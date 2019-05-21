<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<title>课题信息显示</title>

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- 引入bootstrap -->
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<!-- 引入JQuery  bootstrap.js-->
	<script src="/js/jquery-3.2.1.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/ajaxfileupload.js"></script>
	<%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>

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
					    	<h1 class="col-md-5">我的课题</h1>
						</div>
				    </div>
					<div class="input-group">
						<form method="post"  enctype="multipart/form-data" id="form2" >
							<table class="table">
								<tr>
									<td> <input  class="file" id="upfile" type="file" name="upfile" accept=".doc,.docx" /></td>
									<td><input class="btn btn-default " id="btn" name="btn" type="button"  class="btn " value="上传文件" /></td>
								</tr>
							</table>
						</form>
					</div>
				    <table class="table table-bordered">
					        <thead>
					            <tr>
									<th>课题号</th>
									<th>课题名称</th>
									<th>指导老师编号</th>
									<th>指导老师姓名</th>
									<th>邮箱</th>
									<th>联系方式</th>
									<th>是否提交论文</th>
									<th>成绩</th>
					            </tr>
					        </thead>
					        <tbody>
								<c:if test="${selectedCourse!=null}">
									<tr>
										<td>${selectedCourse.courseid}</td>
										<td>${selectedCourse.courseCustom.coursename}</td>
										<td>${selectedCourse.teacherCustom.userid}</td>
										<td>${selectedCourse.teacherCustom.username}</td>

										<td>${selectedCourse.teacherCustom.mail}</td>
										<td>${selectedCourse.teacherCustom.phone}</td>
											<c:choose>
												<c:when test="${selectedCourse.ads==''}">
													<td style="color:red">未提交</td>
												</c:when>
												<c:otherwise>
													<td>已提交</td>
												</c:otherwise>
											</c:choose>
										<td style="color: red">${selectedCourse.courseCustom.score}</td>
									</tr>
								</c:if>
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
		$("#nav li:nth-child(4)").addClass("active");
		<%--设置菜单中--%>
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

		// ajax提交excel
		$(document).ready(function(){
			$("#btn").click(function(){
				if(checkData()){
					$.ajaxFileUpload({
						url:"/file/uploadFile",
						type:"POST",
						dataType: "text",
						fileElementId :"upfile",
						success:function (data) {
							console.log(data);
							$("#upfile").val("");
							window.location.href=('../../student/overCourse');
						},
						error:function(erro){
							console.log(erro);
						}
					});
				}
			});
		});
		function checkData(){
			var fileDir = $("#upfile").val();
			var suffix = fileDir.substr(fileDir.lastIndexOf("."));

			if("" == fileDir){
				alert("选择需要提交的文件！");
				return false;
			}
			if(".doc" != suffix && ".docx" != suffix ){
				alert("选择Word格式的文件导入！");
				return false;
			}
			return true;
		}
	</script>
</html>