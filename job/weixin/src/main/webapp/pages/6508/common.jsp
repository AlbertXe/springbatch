<%@ page import="java.lang.annotation.Repeatable" %><%--
  Created by IntelliJ IDEA.
  User: HASEE
  Date: 2019/3/28
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>
<c:set var="ctx" value="${pageContext.request.['contextPath']}"/>
<c:url var="www_url" value="${ctx}"/> //???


<%
    response.setHeader("Pragma", "No-Cache"); //兼容1.0
    response.setHeader("Cache-Control", "No-Cache");// http 1.1
    response.setDateHeader("Expires", 0);
%>

<script src="resources/js/6508/jquery.js" type="text/javascript"></script>
<script src="resources/js/6508/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/6508/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
    var  path = ${ctx};
    var userId = ${userId};
    var bankNum = '${bankNum}';


</script>
