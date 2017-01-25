<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>sucks</title>
</head>
<body>
 you suck, Johnny!  中文测试
 
 
<%="Hello,johnny,your life sucks!" %> <hr>

<%! int day = 3; %> 
<h3>IF...ELSE 实例</h3>
<% if (day == 1 | day == 7) { %>
      <p>今天是周末</p>
<% } else { %>
      <p>今天不是周末</p>
<% } %>
<hr>

<h3>SWITCH...CASE 实例</h3>
<% 
switch(day) {
	case 0:
	   out.println("星期天");
	   break;
	case 1:
	   out.println("星期一");
	   break;
	case 2:
	   out.println("星期二");
	   break;
	case 3:
	   out.println("星期三");
	   break;
	case 4:
	   out.println("星期四");
	   break;
	case 5:
	   out.println("星期五");
	   break;
	default:
	   out.println("星期六");
}
%>
<hr>

<%! int fontSize; %>
<h3>For 循环实例1 </h3>
<%for ( fontSize = 1; fontSize <= 3; fontSize++){ %>
   <font color="green" size="<%= fontSize %>">
    菜鸟教程
   </font><br />
<%}%>
<hr>

<%! int fontSize1; %> <!-- 此处会初始化为0 -->
<h3>While 循环实例2 </h3>
<%while ( fontSize1 <= 3){ %>
   <font color="green" size="<%= fontSize1 %>">
    菜鸟教程
   </font><br />
<%fontSize1++;%>
<%}%>	<hr>

<%@ page import="java.io.*,java.util.*" %>
<head>
<title>自动刷新实例</title>
</head>

<h4>自动刷新实例</h4>
<%
   // 设置每隔5秒刷新一次
   response.setIntHeader("Refresh", 5);
   // 获取当前时间
   Calendar calendar = new GregorianCalendar();
   String am_pm;
   int hour = calendar.get(Calendar.HOUR);
   int minute = calendar.get(Calendar.MINUTE);
   int second = calendar.get(Calendar.SECOND);
   if(calendar.get(Calendar.AM_PM) == 0)
      am_pm = "AM";
   else
      am_pm = "PM";
   String CT = hour+":"+ minute +":"+ second +" "+ am_pm;
   out.println("当前时间为: " + CT + "\n");
%><hr>


<head><title><h3>访问量统计 </h3></title></head>
<%
    Integer hitsCount = 
      (Integer)application.getAttribute("hitCounter");
    if( hitsCount ==null || hitsCount == 0 ){
       /* 第一次访问 */
       out.println("欢迎访问!");
       hitsCount = 1;
    }else{
       /* 返回访问值 */
       out.println("欢迎再次访问!");
       hitsCount += 1;
    }
    application.setAttribute("hitCounter", hitsCount);
%>
<p>页面访问量为: <%= hitsCount%></p>

<hr>
<a href = "result.jsp"> result.jsp</a>

<hr>
<body>
<form action="Servlet1" method="get">  <!--url-pattern-->
  <input type=text name=text1>
  <input type=submit>
</form>

<hr>
<%
	session.setAttribute("a", "10");
	session.setAttribute("b", "11");
	session.setAttribute("c", "a>b");
%>
${a > b ? b : c}


</body>
</html>