<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

		<title>My JSP 'words_List.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/base.css" />

	</head>

	<body>
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin-bottom: 8px">
			<tr>
				<td>
					<div style='float: left'>
						<img height="16" src="images/add.png" width="16" />
						&nbsp;
						<strong>书籍评价</strong>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" background="images/sp_bg.gif" style='padding: 0px'>
				</td>
			</tr>
		</table>
			 
		<c:forEach var="list" items="${newsreplyList}" varStatus="status">
		<table border="0" cellpadding="0" cellspacing="0" align="center"
			style="border-top: #e8e8e8 1px solid; left: 1px; width: 90%; position: relative; top: 0px; height: 32px">
			<tr>
				<td
					style="background-image: url(images/showbj.gif); width: 21px; height: 27px; border-left: #e8e8e8 1px solid;"
					align="center">
					<img alt="a" src="images/001.gif" style="position: relative" />
				</td>
				<td align="right" colspan="2"
					style="border-right: #e8e8e8 1px solid; background-image: url(images/showbj.gif); width: 680px; height: 27px">
					<div
						style="left: 2px; width: 450px; position: relative; top: 0px; height: 26px; text-align: left">
						<!--<a href="mailto:cc@mail.cnpc.com"> <img alt="a"
								src="images/mail.gif"
								style="position: relative; border-top-style: none; border-right-style: none; border-left-style: none; border-bottom-style: none; left: 0px; top: 3px;" />
						</a>&nbsp;Email: &nbsp;&nbsp;
						<img alt="a" src="images/qq.gif"
							style="position: relative; border-top-style: none; border-right-style: none; border-left-style: none; border-bottom-style: none; top: 3px;" />
						&nbsp;QQ&nbsp;&nbsp;
						--><img alt="a" src="images/posttime.gif"
							style="position: relative; border-top-style: none; border-right-style: none; border-left-style: none; border-bottom-style: none; left: 0px; top: 3px;" />
						&nbsp;发布时间：<fmt:formatDate value="${list.CREATETIME}" pattern="yyyy-MM-dd hh:mm:ss"/> &nbsp;&nbsp;
						<!--<a href="">回复</a>&nbsp;&nbsp;
						-->
						
							<a href="NewsreplyServlet?action=delete&id=${list.ID}}&newsid=${list.NEWSID}"> 删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
						
					</div>
				</td>
			</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" align="center"
			style="left: 1px; width: 90%; position: relative; top: 0px; border-right: #e8e8e8 1px solid; border-left: #e8e8e8 1px solid; height: 20px;">
			<tr>
				<td style="width: 125px; border-right: #e8e8e8 1px solid;"
					align="center">
					<div style="width: 100px; position: relative; height: 50px">
					 用户名：	${list.USERNAME}
					</div>
				</td>
				<td
					style="width: 431px; border-bottom: #e8e8e8 1px solid; height: 50px">
					<div
						style="left: 3px; position: relative; top: 0px; text-align: left; height: 32px;">
						${list.CONTENT}
					</div>
					<br /><!--
					——————————————————
					<br />
					管理员回复：稍等
					<br />
					<br />
				--></td>
			</tr>
		</table>
		<!--<table border="0" cellspacing="0" align="center"
			style="border-top: #e8e8e8 1px solid; left: 1px; width: 90%; position: relative; top: 0px; height: 13px">
			<tr>
				<td
					style="background-image: url(images/showbj.gif); width: 114px; border-left: #e8e8e8 1px solid;">
					<img alt="a" src="images/003.gif" style="position: relative" />
				</td>
				<td style="background-image: url(images/showbj.gif);">
				</td>
				<td
					style="background-image: url(images/showbj.gif); width: 133px; border-right: #e8e8e8 1px solid;"
					align="right">
					<img alt="a" src="images/001.gif" style="position: relative" />
				</td>
			</tr>
		</table>
		--></c:forEach>
	</body>
</html>
