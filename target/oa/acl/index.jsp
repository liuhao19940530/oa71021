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
<script language="javascript" src="${pageContext.request.contextPath}/script/public.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/aclManager.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
<script type="text/javascript">
   function initPage(){//dwr技术，实现了javascript与java的交互
	   
	   //调用AclManagerImpl中对应的实现类中的方法
	   aclManager.findAllAclByMainTypeMainId("${mainType}","${mainId}",function(vs){
		   
		     for(var i=0;i<=vs.length-1;i++){
		    	 
		    	 var array = vs[i];
		    	 
		    	 var moduleId = array[0];
		    	 
		    	 var c = array[1];
		    	 
		    	 var r = array[2];
		    	 
		    	 var u = array[3];
		    	 
		    	 var d = array[4];
		    	 
		    	 var extState  = array[5];
		    	 
		    	 var cb_c = document.getElementById("C_"+moduleId);
		    	 var cb_r = document.getElementById("R_"+moduleId);
		    	 var cb_u = document.getElementById("U_"+moduleId);
		    	 var cb_d = document.getElementById("D_"+moduleId);
		    	 var cb_use = document.getElementById("USE_"+moduleId);
		    	 
		    	 <c:if test="${!empty user}">
		    	   var cb_ext = document.getElementById("EXT_"+moduleId);
		    	   cb_ext.checked = extState == 0?true:false;
		    	   
		    	 </c:if>
		    	   
		    	 cb_c.checked = c>0?true:false;
		    	 cb_r.checked = r>0?true:false;
		    	 cb_u.checked = u>0?true:false;
		    	 cb_d.checked = d>0?true:false;
		    	 cb_use.checked = true;
		    
		     }
	   });
   }
   
   //授权函数
   function addOrUpdateAcl(cb){
	   
	   aclManager.addOrUpdateAcl("${mainType}",${mainId},cb.moduleId,cb.permission,cb.checked);
	   
	   var cb_use = document.getElementById("USE_"+cb.moduleId);
	   
	   cb_use.checked = true;
	   
	   <c:if test="${!empty user}">
	      var cb_ext = document.getElementById("EXT_"+cb.moduleId);
	      cb_ext.checked = true;
	   </c:if>
   }
   
   //联动
   function addOrDelAcl(cb){
	   
	   //设置同步请求处理：排队执行
	   dwr.engine.setAsync(false);
	   
	   if(cb.checked){//添加
		   
		   aclManager.addOrUpdateAcl("${mainType}",${mainId},cb.moduleId,0,cb.checked);
	       aclManager.addOrUpdateAcl("${mainType}",${mainId},cb.moduleId,1,cb.checked);
	       aclManager.addOrUpdateAcl("${mainType}",${mainId},cb.moduleId,2,cb.checked);
	       aclManager.addOrUpdateAcl("${mainType}",${mainId},cb.moduleId,3,cb.checked);
		   
	   }else{//删除
		   
		   aclManager.delAcl("${mainType}",${mainId},cb.moduleId);
	   }
	  
	   //全选全不选
	   var cb_c = document.getElementById("C_"+cb.moduleId);
  	   var cb_r = document.getElementById("R_"+cb.moduleId);
  	   var cb_u = document.getElementById("U_"+cb.moduleId);
  	   var cb_d = document.getElementById("D_"+cb.moduleId);
  	   var cb_use = document.getElementById("USE_"+cb.moduleId);
  	   
	   cb_c.checked = cb_use.checked;
	   cb_r.checked = cb_use.checked;
	   cb_u.checked = cb_use.checked;
	   cb_d.checked = cb_use.checked;
	   
  	   <c:if test="${!empty user}">
         var cb_ext = document.getElementById("EXT_"+cb.moduleId);
         cb_ext.checked = cb_use.checked;
       </c:if>
   }
</script>
<title>
       <c:if test="${!empty user}">请给用户【${user.person.name}】授权</c:if>
       <c:if test="${!empty role}">请给角色【${role.name}】授权</c:if>
</title>
</head>
<BODY onload="initPage()" bgColor=#dee7ff leftMargin=0 background="" topMargin=0 marginheight="0" marginwidth="0">
<center>
     
      <TABLE width="778" border=1 align=center cellPadding=0 cellSpacing=0 borderColor=#ffffff style="FONT-SIZE: 10pt">
        <TBODY>
          <TR>
            <TD height=28 colspan="2" align=center vAlign=center noWrap background=images/list_middle.jpg>&nbsp;&nbsp;
           		<c:if test="${!empty user}">请给用户【${user.person.name}】授权</c:if>
           		<c:if test="${!empty role}">请给角色【${role.name}】授权</c:if>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
   
      <table width="778" border="0" cellPadding="0" cellSpacing="1" bgcolor="#6386d6">
          <!-- 列表标题栏 -->
	      <tr bgcolor="#EFF3F7" class="TableBody1">
		      <td width="10%" height="37" align="center"><b>顶级模块</b></td>
		      <td width="18%" height="37" align="center"><B>二级模块</B></td>
		      <td width="36%" height="37" align="center"><b>授权</b></td>
		      
		      <c:if test="${!empty user}">
		         <td width="18%" height="37" align="center"><b>不继承</b></td>
		      </c:if>  
		        
              <td width="18%" height="37" align="center"><b>启用</b></td>
             
          </tr>
          <!-- 列表数据栏 -->
          <c:if test="${!empty pm.dataList}">
          <c:forEach items="${pm.dataList}" var="module">
	        <tr bgcolor="#EFF3F7" class="TableBody1" onmouseover="this.bgColor = '#DEE7FF';" onmouseout="this.bgColor='#EFF3F7';">
		      <td align="center" vAlign="center">${module.name}</td>
	          <td align="center" vAlign="center">&nbsp;&nbsp;</td>
	          <td align="center" vAlign="center">
	             <input type="checkbox" name="C_${module.id}" moduleId="${module.id}" permission="0" onclick="addOrUpdateAcl(this)"/>C
	             <input type="checkbox" name="R_${module.id}" moduleId="${module.id}" permission="1" onclick="addOrUpdateAcl(this)"/>R
	             <input type="checkbox" name="U_${module.id}" moduleId="${module.id}" permission="2" onclick="addOrupdateAcl(this)"/>U
	             <input type="checkbox" name="D_${module.id}" moduleId="${module.id}" permission="3" onclick="addOrUpdateAcl(this)"/>D
	          </td>
	          <c:if test="${!empty user}">
	             <td align="center" vAlign="center">
   					<input type="checkbox" name="EXT_${module.id}"/>
				 </td>
	          </c:if>
	          <td align="center" vAlign="center">
	             <input type="checkbox" name="USE_${module.id}" moduleId="${module.id}" onclick="addOrDelAcl(this)"/>
	          </td>
	        </tr>
	         <!-- 迭代二级模块 -->
	         <c:forEach items="${module.childList}" var="childModule">
	           <tr>
	            <td align="center" vAlign="center"></td>
	            <td align="center" vAlign="center">
	              ${childModule.name }
	            </td>
	            <td align="center" vAlign="center">
	              <input type="checkbox" name="C_${childModule.id}" moduleId="${childModule.id}" permission="0" onclick="addOrUpdateAcl(this)"/>C
	              <input type="checkbox" name="R_${childModule.id}"} moduleId="${childModule.id}" permission="1" onclick="addOrUpdateAcl(this)"/>R
	              <input type="checkbox" name="U_${childModule.id}" moduleId="${childModule.id}" permission="2" onclick="addOrUpdateAcl(this)"/>U
	              <input type="checkbox" name="D_${childModule.id}" moduleId="${childModule.id}" permission="3" onclick="addOrUpdateAcl(this)"/>D
	            </td>
	            <c:if test="${!empty user}">
	              <td align="center" vAlign="center">
   					<input type="checkbox" name="EXT_${childModule.id}"/>
				  </td>
	            </c:if>
	            <td align="center" vAlign="center">
	              <input type="checkbox" name="USE_${childModule.id}" moduleId="${childModule.id}" onclick="addOrDelAcl(this)"/>
	            </td>
	           </tr>  
	         </c:forEach>
        </c:forEach>
		</c:if>
        <!-- 在列表数据为空的时候，要显示的提示信息 -->
	    <c:if test="${empty pm.dataList}">
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
          <tr> 
             <td align="center" vAlign="center"><input type="button" value="关闭窗口" onclick="window.close()"/></td>
          </tr>
        </TBODY>
      </TABLE>
</center>

</body>

</html>
