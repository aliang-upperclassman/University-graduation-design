<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		<base target="_self">
		<link rel="stylesheet" type="text/css" href="css/base.css" />

	</head>
	<body leftmargin="8" topmargin='8' onload="noteAlert()">
		<form id="form" action="<%=path%>/UserServlet?action=reg" method="post">
			<table width="98%" align="center" border="0" cellpadding="4"
				cellspacing="1" bgcolor="#CBD8AC" style="margin-bottom: 8px">
				<tr>
					<td colspan="4" background="images/wbg.gif" class='title'>
						<div style='text-align: center'>
							<span>用户注册</span>
						</div>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						用户名:
					</td>
					<td width="38%">
						<input type="text" ID="loginname" name="loginname" Height="21px"
							Width="196px" />
						&nbsp;
						<font color="red">(*)</font>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						登录密码:
					</td>
					<td width="38%">
						<input type="" password"" ID="loginpsw" name="loginpsw"
							Height="21px" Width="196px" />
						&nbsp;
						<font color="red">(*)</font>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						确认密码:
					</td>
					<td width="38%">
						<input type="password" ID="loginpsw1" name="loginpsw1"
							Height="21px" Width="196px" />
						&nbsp;
						<font color="red">(*)</font>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						兴趣爱好:
					</td>
					<td width="38%">
						<textarea rows="4" cols="50" ID="interests" name="interests"></textarea>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						工作:
					</td>
					<td width="38%">
						<input type="text" ID="job" name="job" Height="21px" Width="196px" />
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						关注点:
					</td>
					<td width="38%">
						<input type="text" ID="concern" name="concern" Height="21px"
							Width="196px" />
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						 邮箱:
					</td>
					<td width="38%">
						<input type="text" ID="email" name="email" Height="21px"
							Width="196px" />
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						 电话:
					</td>
					<td width="38%">
						<input type="text" ID="tel" name="tel" Height="21px" Width="196px" />
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">

					<td colspan="2" style="text-algin: center" align="center">
						<input type="submit" value="确  定" />
						&nbsp;
						<input type="button" value="关  闭"
							onclick="javascript: window.close()";/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script>
	//获得标识并显示：             
	function noteAlert() {
		var alertNote = "${alertNote}";
		if (alertNote == "1") {
			alert("注册成功!");
			window.close();

		} else if (alertNote == "0") {
		
			alert("注册失败，请联系管理员!");
		}
	}
</script>
