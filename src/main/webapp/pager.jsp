<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/common.jsp" %>
<%@ taglib prefix="pg"  uri="http://jsptags.com/tags/navigation/pager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Pager-taglib分页处理演示</title>
</head>
<body>
<pg:pager items="1000" maxPageItems="20" maxIndexPages="15" export="currentPageNumber=pageNumber">
	<pg:first>
		<a href="${pageUrl}">首页</a>
	</pg:first>
	<pg:prev>
		<a href="${pageUrl}">前页</a>
	</pg:prev>
	<pg:pages>
		<c:choose>
			<c:when test="${currentPageNumber eq pageNumber}">
				<font color="red">${pageNumber }</font>
			</c:when>
			<c:otherwise>
				<a href="${pageUrl}">${pageNumber }</a>
			</c:otherwise>
		</c:choose>
	</pg:pages>
	<pg:next>
		<a href="${pageUrl}">下页</a>
	</pg:next>
	<pg:last>
		<a href="${pageUrl}">尾页</a>
	</pg:last>
</pg:pager>
</body>
</html>