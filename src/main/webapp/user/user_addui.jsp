<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script language="javascript" src="script/public.js"></script>
<title>请选择分配给用户的角色</title>
</head>
<body bgColor="pink">
<center>
<form action="user/addUrs.action" method="post">
<input type="hidden" name="user.id" value="${userId}"/>
<TABLE class="tableEdit" border="0" cellspacing="1" cellpadding="0" style="width:580px;">
	<TBODY>
		<TR>
			<!-- 这里是添加、编辑界面的标题 -->
			<td align="center" class="tdEditTitle">请选择分配给用户的角色</TD>
		</TR>
		<TR>
			<td>
			<!-- 主输入域开始 -->

<table class="tableEdit" style="width:580px;" cellspacing="0" border="1" cellpadding="0">

	<tr>
		<td class="tdEditLabel" width="20%" align="center" vAlign="center">选择</td>		
		<td class="tdEditLabel" width="80%" align="center" vAlign="center">角色名称</td>			
	</tr>
	<c:forEach items="${roleList}" var="role">
	  <tr>
		<td class="tdEditContent" width="20%" align="center" vAlign="center"><input type="radio" name="role.id" value="${role.id}"></td>
				
		<td class="tdEditContent" width="80%" align="center" vAlign="center">${role.name}</td>
	 </tr>
    </c:forEach>
    
    <tr>
      <td align="left" colspan="2">
      	请输入优先级:<input type="text" name="level"/>
      </td>
    </tr>
</table>

			<!-- 主输入域结束 -->
			</td>
		</TR>
	</TBODY>
</TABLE>

<TABLE>
		<TR align="center">
			<TD colspan="3" bgcolor="#EFF3F7">
			<input type="submit" name="saveButton"
				class="MyButton" value="保存分配的角色信息"> 
			<input type="button" class="MyButton"
				value="关闭窗口" onclick="window.close()">
			</TD>
		</TR>
</TABLE>
</form>
</center>
</body>
</html>