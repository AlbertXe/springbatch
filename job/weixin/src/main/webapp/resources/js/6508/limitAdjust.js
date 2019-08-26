function outputdollars(s) {
    
    return "";
}

function outputcents(number1) {
    return "";
}

/**
 * 格式化金额
 * @param number
 * @returns {string}
 */
function outputmoney(number) {
    number = number.replace(/\,/g,"");
    if (number,isNaN(number) || "" == number) return "";
    number = Math.round(number*100)/100;
    if (number <0){
        return '-'+outputdollars(Math.floor(Math.abs(number)-0)+'')+outputcents(Math.abs(number)-0);
    }



    return "";
}

/**
 * 切换卡片 并 查询 额度调整信息
 */
function cardSwitch() {
    var form_tips = $("#form_tips");
    var tip = $("#tip");
    form_tips.css("display","none");
    $("#secondTips").css("display","none");
    tip.html("");

    $("#limitAmount").value(""); //换卡自动清空 申请金额
    var cardNum = $("#cardNum").val();
    if (cardNum == "") {
        form_tips.css("display","block");
        tip.html("您还没有输入哦");
        return false;
    }

    var paraMap = {};
    paraMap.bankNum = $("#bankNum").val();
    paraMap.userId = $("#userId").val();
    paraMap.cardNum = cardNum;

    var regType = $("#regType").val();
    var link = document.getElementById("applybutton");
    //校验卡片信息
    $.ajax({
        type:"POST",
        url:"acct/"+regType+".do",
        data:paraMap,
        dataType:"json",
        success: function (data) {
            if (data.rtncode == "SUCCESS"){
                $("#fixLimit").val(data.fixLimit);
                $("#limitMax").val(data.limitMax);
                $("#outputfixLimix").html("￥"+outputmoney(data.fixLimit)) //格式化金额
                $("#outlimitMax").html("￥"+outputmoney(data.limitMax));
                link.setAttribute("forbidden","no")
            }else{

            }
        },
        error: function () {
            $("#fixLimit").val();
            $("#limitMax").val();
            $("#outputfixLimit").html("￥");
            $("#outlimitMax").html("￥");
            form_tips.css("display","none");
            tip.html("查询失败");
        }
    });


}
