<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" errorPage="errors.jsp"%>
<%@include file="/common/common.jsp" %>
<%
    String path = request.getContextPath();

    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="style/oa.css" rel="stylesheet" type="text/css">
<script language="javascript" src="script/public.js"></script>
<title>角色管理</title>
</head>
<BODY bgColor=#dee7ff leftMargin=0 background="" topMargin=0 marginheight="0" marginwidth="0">
<center>
      <TABLE width="778" border=0 cellPadding=0 cellSpacing=0 borderColor=#ffffff bgColor=#dee7ff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR height=35>
            <TD align=middle width=20 background=images/title_left.gif 
          bgColor=#dee7ff></TD>
            <TD align=middle width=120 background=images/title_left.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7>角色管理<font color="#FFFFFF">&nbsp;</font></FONT> </TD>
            <TD align=middle width=11 background=images/title_middle.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7>&nbsp;</FONT> </TD>
            <TD align=middle background=images/title_right.gif 
          bgColor=#dee7ff><FONT color=#f7f7f7>&nbsp;</FONT> </TD>
          </TR>
        </TBODY>
      </TABLE>
      <TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD width="82%" height=14 align=right vAlign=center noWrap>
            </TD>
            <TD width="18%" align=right vAlign=center noWrap>　</TD>
          </TR>
          <TR>
            <TD height=14 align=right vAlign=center noWrap>
            	<!-- 在这里插入查询表单 -->
            </TD>
            <TD height=14 align="left" vAlign=center noWrap>
            <% 
            /**
            * 在这里定义“添加”，“查询”等按钮
            * <input type="image" name="find" value="find" src="images/cz.gif">
            * &nbsp;&nbsp;&nbsp;&nbsp; 
            * <a href="#" onClick="BeginOut('document.do?method=addInput','470')">
            * <img src="images/addpic.gif" border=0 align=absMiddle style="CURSOR: hand"></a>
            */
            %>
            <c:if test="${my:getPermission(loginUser.id,'role',0)}">
              <span style="cursor:hand;color:#0000ff;"
                  onmouseover="this.style.color='#ff0000'"
                  onmouseout="this.style.color='#0000ff'"
                  onclick="openWin('${pageContext.request.contextPath}/role/addUI.action','addrole',600,200);">
           	     添加角色信息
             	</span>&nbsp;&nbsp;&nbsp;&nbsp;
            </c:if> 	
            <a href="role/findAll.action">返回</a>
            </TD>
          </TR>
          <TR>
            <TD height=28 colspan="2" align=right vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
            <!-- 可以在这里插入分页导航条 -->
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      <table width="778" border="0" cellPadding="0" cellSpacing="1" bgcolor="#6386d6">
          <!-- 列表标题栏 -->
	      <tr bgcolor="#EFF3F7" class="TableBody1">
		      <td width="5%" height="37" align="center"><b>序号</b></td>
		      <td width="18%" height="37" align="center"><B>角色名称</B></td>

              <td width="5%" height="37" align="center"><b>操作</b></td>
          </tr>
          <!-- 列表数据栏 -->
          <c:if test="${!empty roleList}">
          <c:forEach items="${roleList}" var="role">
	      <tr bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
		      <td align="center" vAlign="center">${role.id }</td>
	          <td align="center" vAlign="center">${role.name }</td>
	         
	          <td align="center" vAlign="center">
	             <c:if test="${my:getPermission(loginUser.id,'role',3)}">
	               <span style="cursor:hand;color:#0000ff;"
	                   onmouseover="this.style.color='#ff0000'"
	                   onmouseout="this.style.color='$0000ff'"
	                   onclick="del('${pageContext.request.contextPath}/role/delete.action?id=${role.id}');">
                                                 删除角色 
	               </span>
	             </c:if>
				 <c:if test="${my:getPermission(loginUser.id,'role',2)}">
	             
	               <span  style="cursor:hand;color:#0000ff;"
	             		onmouseover="this.style.color='#ff0000'"
	             		onmouseout="this.style.color='#0000ff'"
	                    onclick="openWin('${pageContext.request.contextPath}/acl/initPage.action?mainType=ROLE&mainId=${role.id}','addacl',600,200,1);">
	                                  角色授权
	                </span>
	             </c:if>
	          </td>
        </tr>
        </c:forEach>
		</c:if>
        <!-- 在列表数据为空的时候，要显示的提示信息 -->
	    <c:if test="${empty roleList}">
	    <tr>
	    	<td colspan="7" align="center" bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
	    	没有找到相应的记录
	    	</td>
	    </tr>
	    </c:if>
      </table>
      <TABLE width="778" border=0 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD height=28 align=right vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
             </TD>
          </TR>
        </TBODY>
      </TABLE>
</center>

</body>

</html>
