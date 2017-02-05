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
		<form id="form" action="<%=path %>/GoodsServlet?action=add" method="post">
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-bottom: 8px">
				<tr>
					<td>
						<div style='float: left'>
							<img height="16" src="<%=path%>/images/add.png" width="16" />
							&nbsp;
							<strong>添加 -书籍</strong>
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
				<tr>
					<td colspan="4" background="<%=path%>/images/wbg.gif" class='title'>
						<div style='float: left'>
							<span>必填项 <font color="red">(*)</font> </span>
						</div>
					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						书籍名称:
					</td>
					<td width="38%">
						<input type="text" ID="goodsname" name="goodsname" Height="21px"
							Width="196px" />

					</td>
				</tr>

				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						书籍类型:
					</td>
					<td width="38%">
						<select id="goodstypeid" name="goodstypeid">
							<option value="">
								--请选择--
							</option>
							<c:forEach items="${goodsTypeList}" var="goodsType">
								<option value="${goodsType.ID}">
									${goodsType.TYPENAME}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>

<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						封面:
					</td>
					<td width="38%">
							<input type="text" ID="imgpath" name="imgpath"  Height="21px" readonly="readonly"
							style="width: 200px" />
						<input type="button" value="选择图片" onclick=
	openDLG();
/>

					</td>
				</tr>
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						作者:
					</td>
					<td width="38%">
						<input type="text" ID="author" name="author" Height="21px"
							Width="196px" />

					</td>
				</tr>
                 
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						描述:
					</td>
					<td width="38%">
						<textarea rows="5" cols="100" ID="description" name="description"></textarea>
					</td>
				</tr>
				
				
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						库存:
					</td>
					<td width="38%">
						<input type="text" style="width: 196px" id="num" name="num"/>
					</td>
				</tr>
				
				<tr bgcolor="#FFFFFF">
					<td width="12%" align="center">
						单价:
					</td>
					<td width="38%">
						<input type="text" style="width: 196px" id="price" name="price"/>
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
						<a href="<%=path %>/GoodsServlet?action=list" target="right">返回列表</a>
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
		  if(t==undefined)  
		  {  
		    t = window.returnValue;  
		  }
		  if(t!=null)
		  {  		 
		     document.getElementById("imgpath").value = t;	
		  }	 
	}
</script>
