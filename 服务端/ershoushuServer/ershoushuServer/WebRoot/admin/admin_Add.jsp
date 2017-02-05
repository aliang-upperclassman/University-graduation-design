<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
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
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />
	<script type="text/javascript">
	function advil() {
	var loginname = document.getElementById("loginname").value;	 
		if (loginname == "" || loginname == null) {
			alert("登陆账号不能为空!");
			return false;
		}
		
		var loginpsw = document.getElementById("loginpsw").value;	 
		if (loginpsw == "" || loginpsw == null) {
			alert("密码不能为空!");
			return false;
		}
		 
		 
		 
		this.form1.submit();
	}
	</script>
	</head>
	<body leftmargin="8" topmargin='8' onload="noteAlert()">
		<form id="form" action="<%=path%>/AdminServlet?action=add" method="post">
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-bottom: 8px">
				<tr>
					<td>
						<div style='float: left'>
							 
							<strong>添加管理员</strong>
						</div>
					</td>
				</tr>
				<tr>
					<td height="1" background="<%=path%>/images/sp_bg.gif" style='padding: 0px'>
					</td>
				</tr>
			</table>
			<table width="98%" align="center" border="0" cellpadding="4"
				cellspacing="1" bgcolor="#CBD8AC" style="margin-bottom: 8px">
				 
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						登录账号:
					</td>
					<td width="38%">
						<input type="text" ID="loginname" name="loginname" Height="21px"
							Width="196px" />

					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						登录密码:
					</td>
					<td width="38%">
						<input type="text" ID="loginpsw" name="loginpsw" Height="21px"
							Width="196px" />
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						真实姓名:
					</td>
					<td width="38%">
						<input type="text" ID="username" name="username"  Height="21px" Width="196px" />
						</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td align="center">
					</td>
					<td colspan="3">
						&nbsp;&nbsp;
						<br />
						<input type="submit" value="保存" onclick=" return advil()"/>
						&nbsp;
						<a href="<%=path%>/AdminServlet?action=list" target="right">返回列表</a>
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
			alert("操作成功!");

		} else if (alertNote == "0") {
			alert("添加失败，请联系管理员!");
		}
	}
</script>
