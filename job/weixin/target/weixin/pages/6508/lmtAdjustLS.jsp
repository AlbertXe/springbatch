<%--
  Created by IntelliJ IDEA.
  User: HASEE
  Date: 2019/3/27
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="common.jsp"%>
    <script type="text/javascript" src="resources/js/6508/limitAdjust.js"></script>
</head>

<script>
    var loadData = function () {

    };
    $(function () {
        checkCardNum();//初始化卡号
        loadData();
    })

    function checkCardNum() {
        var cardNum = $("#cardNum").val();
        if (cardNum != "") {

        }else{
            cardSwitch();
        }

    }

</script>

<body>

<form id="formSub" name="formSub" action="webLmt.do" method="post">
    <input type="hidden" name="action" value="lmtAdjLS"/>
    <input type="hidden" name="bankNum" id="bankNum" value="<c:out value='${bankNum}'>">

</form>
</body>
</html>
