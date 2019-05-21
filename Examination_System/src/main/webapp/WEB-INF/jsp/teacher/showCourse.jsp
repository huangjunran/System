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
	<script src="/js/MsgBox.js"></script>
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
					    	<h1 class="col-md-5">我负责的课题</h1>

						</div>
				    </div>
				    <table class="table table-bordered">
					        <thead>
					            <tr>
									<th>课题号</th>
									<th>课题名称</th>
									<th>学生编号</th>
									<th>得分</th>
									<th>是否通过审查</th>
									<th>论文是否提交</th>
									<th>操作</th>
					            </tr>
					        </thead>
					        <tbody>
							<c:forEach  items="${courseList}" var="item">
								<tr>
									<td>${item.courseid}</td>
									<td>${item.coursename}</td>
									<td>${item.studentid}</td>
									<td>${item.score}</td>
									<td>
										<c:if test="${item.pass==1}">通  过</c:if>
										<c:if test="${item.pass==0}">未通过</c:if>
									</td>
									<c:choose>
										<c:when test="${item.ads==null}">
											<td>
												未提交
											</td>
										</c:when>
										<c:when test="${item.ads!=''}">
											<td>
												<button class="btn btn-default btn-xs btn-info" onClick="location.href='/file/downloadFile?id=${item.studentid}'">下载</button>
											</td>
										</c:when>
										<c:otherwise>
											<td>未提交</td>
										</c:otherwise>
									</c:choose>
									<td>
										<button class="btn btn-default btn-xs btn-info" onClick="changeTitleMessage(${item.courseid})">更改题目</button>
										<%--<button class="btn btn-default btn-xs btn-info" onClick="location.href='/teacher/selectStudent?id=${item.courseid}'">选择学生</button>--%>
										<button class="btn btn-default btn-xs btn-info" onClick="selectStuMessage(${item.courseid})">选择学生</button>
									<%--<button class="btn btn-default btn-xs btn-info" onClick="location.href='/teacher/StudentMessage?id=${item.courseid}'">查看学生详情</button>--%>
										<button class="btn btn-default btn-xs btn-info" onClick="showStuMessage(${item.courseid})">查看学生详情</button>

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
									<li><a href="/student/showCourse?page=${pagingVO.upPageNo}">&laquo;上一页</a></li>
									<li class="active"><a href="">${pagingVO.curentPageNo}</a></li>
									<c:if test="${pagingVO.curentPageNo+1 <= pagingVO.totalCount}">
										<li><a href="/student/showCourse?page=${pagingVO.curentPageNo+1}">${pagingVO.curentPageNo+1}</a></li>
									</c:if>
									<c:if test="${pagingVO.curentPageNo+2 <= pagingVO.totalCount}">
										<li><a href="/student/showCourse?page=${pagingVO.curentPageNo+2}">${pagingVO.curentPageNo+2}</a></li>
									</c:if>
									<c:if test="${pagingVO.curentPageNo+3 <= pagingVO.totalCount}">
										<li><a href="/student/showCourse?page=${pagingVO.curentPageNo+3}">${pagingVO.curentPageNo+3}</a></li>
									</c:if>
									<c:if test="${pagingVO.curentPageNo+4 <= pagingVO.totalCount}">
										<li><a href="/student/showCourse?page=${pagingVO.curentPageNo+4}">${pagingVO.curentPageNo+4}</a></li>
									</c:if>
									<li><a href="/student/showCourse?page=${pagingVO.totalCount}">最后一页&raquo;</a></li>
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
		<%--设置菜单中--%>
		$("#nav li:nth-child(2)").addClass("active")
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

		// //向后台请求选课
		function changeTitleMessage (id) {
			$.ajax({
				type: "get",  // 请求方式
				url: "/teacher/changeTitleMessage" , // 目标资源
				data: "id="+id, // 请求参数
				dataType: "json",  // 服务器响应的数据类型
				contentType: "application/json;charset=utf-8",
				success : function (data) {  // readystate == 4 && status == 200

					if(data.data!=""){
						// alert(data.data)  // data是一个dom对象, 先将其转化为jquery对象alert(data)  // data是一个dom对象, 先将其转化为jquery对象
						$.MsgBox.Alert("消息",data.data);
					}else{
						window.location.href=('../../teacher/editCourse?id='+id);
					}
				},
				error:function(arg1){
					alert("error"+arg1)
				}
			});
		};
		function showStuMessage (id) {
			$.ajax({
				type: "get",  // 请求方式
				url: "/teacher/showStudentMessage" , // 目标资源
				data: "id="+id, // 请求参数
				dataType: "json",  // 服务器响应的数据类型
				contentType: "application/json;charset=utf-8",
				success : function (data) {  // readystate == 4 && status == 200

					if(data.data!=""){
						// alert(data.data)  // data是一个dom对象, 先将其转化为jquery对象alert(data)  // data是一个dom对象, 先将其转化为jquery对象
						$.MsgBox.Alert("消息",data.data);
					}else{
						window.location.href=('../../teacher/StudentMessage?id='+id);
					}
				},
				error:function(arg1){
					alert("error"+arg1)
				}
			});
		};
		function selectStuMessage(id) {
			$.ajax({
				type: "get",  // 请求方式
				url: "/teacher/selectStudentMessage" , // 目标资源
				data: "id="+id, // 请求参数
				dataType: "json",  // 服务器响应的数据类型
				contentType: "application/json;charset=utf-8",
				success : function (data) {  // readystate == 4 && status == 200
					if(data.data!=""){
						// alert(data.data)  // data是一个dom对象, 先将其转化为jquery对象alert(data)  // data是一个dom对象, 先将其转化为jquery对象
						$.MsgBox.Alert("消息",data.data);
					}else{
						window.location.href=('../../teacher/selectStudent?id='+id);
					}
				},
				error:function(arg1){
					alert("error"+arg1)
				}
			});
		}
	</script>
</html>