<%-- <%@LANGUAGE="JAVASCRIPT" CODEPAGE="936"%> the original --%>
<!-- http://blog.sina.com.cn/s/blog_5a15b7d101015v6m.html -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>three demos to show pop-up dialog box</title>
    <script language="javascript">
        function ale() {//popup an alert dialog box
            alert("you are using demo 1");
        }

        function firm() {//use the vlaue returned from dialog box(ture or false)
            if (confirm("are you sure redirect to somewhere else?")) {//if true
                location.href = "http://thcjp.cnblogs.com";
            }
            else {//if false
                alert("you canceled, then return false");
            }
        }

        function prom() {
            var name = prompt("please input your name", "");//将输入的内容赋给变量 name ，
            //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值
            if (name)//如果返回的有内容
            {
                alert("welcome " + name)
            }
        }
    </script>
</head>

<body>
<p>对话框有三种</p>
<p>1：只是提醒，不能对脚本产生任何改变；</p>
<p>2：一般用于确认，返回 true 或者 false ，所以可以轻松用于 if else判断 </p>
<p>3： 一个带输入的对话框，可以返回用户填入的字符串，常见于某些留言本或者论坛输入内容那里的 插入UBB格式图片 </p>
<p>下面我们分别演示：</p>
<p>演示一：提醒 对话框</p>
<p>
    <input type="submit" name="Submit" value="提交" onclick="ale()"/>
</p>
<p>演示二 ：确认对话框 </p>
<p>
    <input type="submit" name="Submit2" value="提交" onclick="firm()"/>
</p>
<p>演示三 ：要求用户输入，然后给个结果</p>
<p>
    <input type="submit" name="Submit3" value="提交" onclick="prom()"/>
</p>
</body>
</html>
