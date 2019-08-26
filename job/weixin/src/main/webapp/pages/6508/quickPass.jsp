<%--
  Created by IntelliJ IDEA.
  User: HASEE
  Date: 2019/4/2
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../resources/js/6508/jquery.js"></script>
    
    <script>
        function showSecond() {
            setTimeout(alert("hello"),2000);
        }

        var timeout = 45000;
        function showCountDown(id) {
            if (timeout<=0){
                $('#'+ id).html("重新发送");
                timeout = 45000;
            }else{
                timeout =timeout - 1000;
                $("#"+id).html(timeout/1000+"秒");
                setTimeout("showCountDown('"+id+"')",1000);
            }
        }
        
    </script>
    <style>
        div .switchOn, div .switchOff{
            background: antiquewhite;
            border-radius: 20px;
            float: right;
        }
        div label{
            border-radius: 50%;
            margin: 1px;
            width: 24px;
            height: 24px;
            box-shadow: none;
        }
    </style>
</head>
<body>
    <a href="javascript:showSecond();">hello</a>

    <div>
        <font style="position: relative;color: red;">
            <button class="switchOff">按钮</button>
        关闭<label></label>
        </font>
    </div>

    <div>
        <button id="smsSend" onclick="showCountDown('smsSend');">倒计时</button>
    </div>
</body>
</html>
