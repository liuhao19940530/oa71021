<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/common/common.jsp"%>
<HTML>
<!--
 ---------------------------------------------------------------------------
 this script is copyright (c) 2001 by Michael Wallner!
 http://www.wallner-software.com
 mailto:dhtml@wallner-software.com

 you may use this script on web pages of your own
 you must not remove this copyright note!

 This script featured on Dynamic Drive (http://www.dynamicdrive.com)
 Visit http://www.dynamicdrive.com for full source to this script and more
 ---------------------------------------------------------------------------
-->
<HEAD>
<%
   String path = request.getContextPath();
		 
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=utf-8">
<TITLE>Outlook Like Bar</TITLE>

<!--
  you need this style or you will get an error in ns4 on first page load!
-->
<STYLE>
  div    {
         position:absolute;
         }
</STYLE>

<script language="JavaScript" src="crossbrowser.js" type="text/javascript">
</script>
<script language="JavaScript" src="outlook.js" type="text/javascript">
</script>

<SCRIPT>

// ---------------------------------------------------------------------------
// Example of howto: use OutlookBar
// ---------------------------------------------------------------------------


  //create OutlookBar
  var o = new createOutlookBar('Bar',0,0,screenSize.width,screenSize.height,'#606060','white')//'#000099') // OutlookBar
  
  /*
  var p

  //create first panel
  p = new createPanel('al','组织机构');
  p.addButton('netm.gif','机构管理','parent.main.location="org/findAll.action?pid=0"');
  p.addButton('news.gif','人员管理','parent.main.location="person/findAll.action"');

  o.addPanel(p);
  
  p = new createPanel('p','权限管理');
  p.addButton('mail.gif','模块管理','parent.main.location="module/findAll.action?pid=0"');
  p.addButton('peditor.gif','角色管理','parent.main.location="role/findAll.action"');
  p.addButton('netm.gif','用户管理','parent.main.location="user/findAll.action"');
 
  o.addPanel(p);

  p = new createPanel('l','系统管理');
  o.addPanel(p);
   */
   
  <c:if test="${!empty moduleList}">
     <c:forEach items="${moduleList}" var="module">
       <c:if test="${empty module.parent}">
          var p_${module.id} = new createPanel("a_${module.id}","${module.name}");
          o.addPanel(p_${module.id});
       </c:if>
     </c:forEach>
  </c:if>
  
  <c:if test="${!empty moduleList}">
     <c:forEach items="${moduleList}" var="childModule">
        <c:if test="${!empty childModule.parent}">
           //为了防止出现顶级模块为NUll的情况，这里需要用try{}catch(err){}忽略
           try{
        	 
        	   p_${childModule.parent.id}.addButton('mail.gif','${childModule.name}','parent.main.location="${childModule.url}"');
        	   
           }catch(err){
        	   
        	   
           }
        </c:if>
     </c:forEach>
  </c:if>
   
  o.draw();         //draw the OutlookBar

//-----------------------------------------------------------------------------
//functions to manage window resize
//-----------------------------------------------------------------------------
//resize OP5 (test screenSize every 100ms)
function resize_op5() {
  if (bt.op5) {
    o.showPanel(o.aktPanel);
    var s = new createPageSize();
    if ((screenSize.width!=s.width) || (screenSize.height!=s.height)) {
      screenSize=new createPageSize();
      //need setTimeout or resize on window-maximize will not work correct!
      //ben�tige das setTimeout oder das Maximieren funktioniert nicht richtig
      setTimeout("o.resize(0,0,screenSize.width,screenSize.height)",100);
    }
    setTimeout("resize_op5()",100);
  }
}

//resize IE & NS (onResize event!)
function myOnResize() {
  if (bt.ie4 || bt.ie5 || bt.ns5) {
    var s=new createPageSize();
    o.resize(0,0,s.width,s.height);
  }
  else
    if (bt.ns4) location.reload();
}

</SCRIPT>

</head>
<!-- need an onResize event to redraw outlookbar after pagesize changes! -->
<!-- OP5 does not support onResize event! use setTimeout every 100ms -->
<body onLoad="resize_op5();" onResize="myOnResize();">
   
</body>
</html>


