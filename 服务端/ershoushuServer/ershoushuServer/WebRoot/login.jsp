<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
	<title>“学斧”高校二手书网上交易平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
	<style type="text/css">
body {
	margin-left: 0px;
	background-image: url(<%=path%>/img/bhj.jpg);
}

.style2 {
	color: #990000
}

.input2 {
	font-size: 12px;
	border: 3px double #A8D0EE;
	color: #344898;
}

.submit1 {
	border: 3px double #416C9C;
	height: 22px;
	width: 45px;
	background-color: #F2F2F2;
	font-size: 12px;
	padding-top: 1px;
	background-image: url(bt.gif);
	cursor: hand;
}

.STYLE12 {
	font-family: Georgia, "Times New Roman", Times, serif
}

.STYLE13 {
	color: #316BD6;
}

.STYLE15 {
	color: #fdsere;
	font-size: 9pt;
}
</style>

	<script type="text/javascript">
	function loginSys() {
		var loginName = document.getElementById("loginname").value;

		var password = document.getElementById("loginpassword").value;
		if (loginName == "" || loginName == null) {
			alert("登陆账号不能为空!");
			return false;
		}
		if (password == "" || password == null) {
			alert("登陆密码不能为空!");
			return false;
		}
		this.form1.submit();
	}
	 
</script>
</head>
<body>

	<br>
	<br>
	<br>
	<br>
	<table width="559" height="423" border="0" align="center"
		cellpadding="0" cellspacing="0" background="<%=path%>/img/dfff.jpg">
		<tr>
			<td>
				<div align="center"
					style="FONT-WEIGHT: bold; FONT-SIZE: 30pt; color: #ffffff">
					“学斧”高校二手书网上交易平台
				</div>
			</td>
		</tr>
		<tr>
			<td width="559">
				<form action="LoginServlet" method="post" id="form1">
					<table width="410" height="198" border="0" align="right"
						cellpadding="0" cellspacing="0">
						<tr>
							<td height="10" colspan="2"></td>
						</tr>
						<tr>
							<td width="54" height="22" valign="bottom">
								<span class="STYLE15">用户名：</span>
							</td>
							<td width="356" valign="bottom">
								<input type="text" name="loginname" id="loginname" />
							</td>
						</tr>
						<tr>
							<td height="10" colspan="2" valign="bottom"></td>
						</tr>
						<tr>
							<td height="31" colspan="2" valign="top" class="STYLE15">
								密&nbsp;&nbsp;码：
								<input type="password" name="password" id="loginpassword" />
							</td>
						</tr>
						<tr>
							<td height="10" colspan="2" valign="bottom"></td>
						</tr>
						<tr>
							<td colspan="2" valign="top">
								&nbsp; &nbsp; &nbsp; &nbsp;
								<input name="button" type="button" class="submit1" value="登录"
									onClick="return loginSys()">
								&nbsp;
								<input name="Submit2" type="reset" class="submit1" value="重置">
								<input type="hidden" id="logintype" name="logintype" value="1" />
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</body>

<script type="text/javascript">
	window.onload = function() {	 
		var alertNote = "${alertNote}";
		  if (alertNote == "0") {
			alert("登陆失败，请检查用户名和密码是否正确!");
		}
	} 
</script>