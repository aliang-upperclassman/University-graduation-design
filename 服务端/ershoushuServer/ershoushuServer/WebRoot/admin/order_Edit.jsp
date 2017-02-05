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
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />

	</head>
	<body leftmargin="8" topmargin='8' onload="noteAlert()">
		<form id="form" action="<%=path%>/UserOrderServlet?action=editsave"
			method="post">
			<input type="hidden" id="id" name="id" value="${order.id}" />
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-bottom: 8px">
				<tr>
					<td>
						<div style='float: left'>
							<img height="16" src="<%=path%>/images/add.png" width="16" />
							&nbsp;
							<strong>编辑 -订单</strong>
						</div>
					</td>
				</tr>
				<tr>
					<td height="1" background="<%=path%>/images/sp_bg.gif"
						style='padding: 0px'>
					</td>
				</tr>
			</table>
			<table width="98%" align="center" border="0" cellpadding="4"
				cellspacing="1" bgcolor="#CBD8AC" style="margin-bottom: 8px">
				<tr>
					<td colspan="4" background="<%=path%>/images/wbg.gif" class='title'>
						<div style='float: left'>
							<span>必填项 <font color="red">(*)</font> </span>
						</div>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						订单编号:
					</td>
					<td width="38%">
						${order.ordernum}

					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						总价格:
					</td>
					<td width="38%">
						<input type="text" ID="sumprice" name="sumprice" Height="21px"
							value='${order.sumprice}' Width="196px" />

					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						送货地址:
					</td>
					<td width="38%">

						<input type="text" style="width: 196px" id="address"
							name="address" value='${order.address}' />
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						收件人:
					</td>
					<td width="38%">
						<input type="text" style="width: 196px" id="recusername"
							name="recusername" value='${order.recusername}' />
					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						电话:
					</td>
					<td width="38%">
						<input type="text" style="width: 196px" id="tel" name="tel"
							value='${order.tel}' />
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						快递:
					</td>
					<td width="38%">

						<select id="sendtype" name="sendtype">
							<option value="邮政快递"
								<c:if test="${order.sendtype=='邮政快递'}">selected</c:if>>
								邮政快递
							</option>
							<option value="顺丰快递"
								<c:if test="${order.sendtype=='顺丰快递'}">selected</c:if>>
								顺丰快递
							</option>
							<option value="圆通快递"
								<c:if test="${order.sendtype=='圆通快递'}">selected</c:if>>
								圆通快递
							</option>
							<option value="EMS"
								<c:if test="${order.sendtype=='EMS'}">selected</c:if>>
								EMS
							</option>
						</select>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td align="center">
					</td>
					<td colspan="3">
						&nbsp;&nbsp;
						<br />
						<input type="submit" value="保存" />
						&nbsp;
						<a href="<%=path%>/UserOrderServlet?action=list" target="right">返回列表</a>
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

	function openDLG() {
		t = window
				.showModalDialog('common/imageUpload.jsp', '',
						'dialogHeight:100px; dialogWidth:400px;dialogTop:250px;dialogLeft:500px;')

		//for Chrome  
		if (t == undefined) {
			t = window.returnValue;
		}

		if (t != null) {
			document.getElementById("imgpath").value = t;
		}
		document.getElementById("imgpath").value = t;
	}
</script>
