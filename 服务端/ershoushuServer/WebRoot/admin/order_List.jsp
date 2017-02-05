<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin-bottom: 8px">
			<tr>
				<td>
					<div style='float: left'>
						<img height="16" src="images/add.png" width="16" />
						&nbsp;
						<strong>订单列表</strong>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" background="images/sp_bg.gif" style='padding: 0px'>
				</td>
			</tr>
		</table>
		<table class="table">
			<thead>
				<tr>
					<td style="width: 5%;" class="gvtitle">
						序号
					</td>
					<td style="width: 10%;" class="gvtitle">
						订单编号
					</td>
					<td style="width: 10%;" class="gvtitle">
						用户账号
					</td>
					<td style="width: 10%;" class="gvtitle">
						总价
					</td>
					<td style="width: 10%;" class="gvtitle">
						送货地址
					</td>
					<td style="width: 10%;" class="gvtitle">
						收件人
					</td>

					<td style="width: 10%;" class="gvtitle">
						电话
					</td>
					<td style="width: 10%;" class="gvtitle">
						快递
					</td>
					<td style="width: 10%;" class="gvtitle">
						订单状态
					</td>
					<td class="gvtitle" style="width: 10%;">
						操作
					</td>
				</tr>
				<c:forEach var="list" items="${orderList}" varStatus="status">
					<tr>
						<td>
							${status.count}
						</td>
						<td>
							${list.ORDERNUM}
						</td>
						<td>
							${(list.LOGINNAME)}

						</td>
						<td>
							${(list.SUMPRICE)}

						</td>
						<td>
							${(list.ADDRESS)}

						</td>
						<td>
							${(list.RECUSERNAME)}

						</td>

						<td>
							${(list.TEL)}

						</td>
						<td>
							${(list.SENDTYPE)}

						</td>
						<td>

							<c:if test='${list.STATE=="0"}'>
						            已付款&nbsp;&nbsp;&nbsp;<input type="button" value="发货" onClick="SendGoods('${list.ID}')" />
							</c:if>
							<c:if test='${list.STATE=="1"}'>
						                已发货				
						    </c:if>
							<c:if test='${list.STATE=="2"}'>
						                 已完成			
						    </c:if>
						</td>


						<td>
							<a href="javascript:void(0)" onclick="showDetail('${list.ID}')">订单明细</a>&nbsp;|&nbsp;
							<a href="<%=path%>/UserOrderServlet?action=edit&id=${list.ID}">修改</a>&nbsp;|&nbsp;
							<a href="<%=path%>/UserOrderServlet?action=delete&id=${list.ID}">删除</a>
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

	function showDetail(id) {
		var openUrl = "<%=path%>/UserOrderServlet?action=goodslist&id="+id;//弹出窗口的url
		var iWidth = 800; //弹出窗口的宽度;
		var iHeight = 600; //弹出窗口的高度;
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
		window.open(openUrl, "", "height=" + iHeight + ", width=" + iWidth
				+ ", top=" + iTop + ", left=" + iLeft);
	}
	
	function SendGoods(id)
	{
	 
	    document.location.href="<%=path%>/UserOrderServlet?action=send&state=1&id="+id;
	 
	}
</script>