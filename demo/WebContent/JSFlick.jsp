<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 2016/11/8
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <script type="text/javascript" language="javascript"
            src="https://www.codefans.net/ajaxjs/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" language="javascript">
        $(function () {
            $("#right").click(function () {
                const roll = $("&lt;div>&lt;/div>", {
                    css: {
                        position: "absolute",
                        border: "solid 1px #999",
                        left: "806px",
                        top: "10px",
                        height: "494px",
                        width: "1px",
                        background: "#fff"
                    }
                }).appendTo($("#book").parent());
                $(roll).animate({
                    left: "10px",
                    width: "398px"
                }, 1000, function () {
                    $("#left").css({"background": "#fff"});
                    $(roll).fadeOut(300, function () {
                        $(roll).remove();
                    })
                });
            });
        });
    </script>
</head>
<body style="padding:5px;margin:0;">
<div id="book" style="width:797px;height:494px;background:#ccc;border:solid 6px #ccc;">
    <div id="left"
         style="width:398px;height:494px;float:left;background:url(https:///) no-repeat top left;cursor:pointer;"></div>
    <div id="right"
         style="width:398px;height:494px;float:left;background:#fff;cursor:pointer;margin-left:1px;text-align:right;"><p
            style="margin-top:470px;font-size:12px;color:#999;">点这翻页</p></div>
</div>
</body>
</html>