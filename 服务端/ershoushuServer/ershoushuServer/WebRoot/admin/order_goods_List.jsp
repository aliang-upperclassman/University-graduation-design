<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'news_List.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css" href="css/base.css" />
	</head>

	<body style="text-align: center" onload="noteAlert()">

		<table class="table">
			<thead>
				<tr>
					<td style="width: 5%;" class="gvtitle">
						序号
					</td>

					<td style="width: 10%;" class="gvtitle">
						书籍名称
					</td>
					<td style="width: 10%;" class="gvtitle">
						封面
					</td>

					<td style="width: 10%;" class="gvtitle">
						购买数量
					</td>
					<td style="width: 15%;" class="gvtitle">
						价格
					</td>
				</tr>
				<c:forEach var="list" items="${ordergoodsList}" varStatus="status">
					<tr>
						<td>
							${status.count}
						</td>
						<td>
							${list.GOODSNAME}
						</td>

						<td>
							<img alt="${list.GOODSNAME}" src="${list.IMGPATH}"
								style="width: 100px; height: 60px" />

						</td>

						<td>
							${list.BUYNUM}
						</td>
						<td>
							${list.SUMPRICE}
						</td>

					</tr>
				</c:forEach>
			</thead>
			<tbody>
			</tbody>
		</table>

	</body>
</html>
<script>
	//获得标识并显示：             
	function noteAlert() {

		var alertNote = "${alertNote}";
		if (alertNote == "1") {
			alert("操作成功!");

		} else if (alertNote == "0") {
			alert("操作失败，请联系管理员!");
		}
	}
</script>