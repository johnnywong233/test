<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.johnny.factory.DAOFactory" %>
<%@ page import="com.johnny.vo.Myemp" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<html>
<head><title>This is DAO demo list</title></head>
<% request.setCharacterEncoding("UTF-8"); %>
<body>
<%
    String keyWord = request.getParameter("keyword");
    if (keyWord == null) {
        keyWord = "";    //if empty, then search all
    }
    List<Myemp> all = null;
    try {
        all = DAOFactory.getIEmpDAOInstance().findAll(keyWord);
    } catch (Exception e) {
        e.printStackTrace();
    }
    Iterator<Myemp> iter = all != null ? all.iterator() : null;
%>
<div style="text-align: center;">
    <form action="list.jsp" method="post">
        Please input key word for search：<input type="text" name="keyword">
        <input type="submit" value="search">
    </form>
    <table border="1" width="70%">
        <tr>
            <td>ID</td>
            <td>Username</td>
            <td>Password</td>
        </tr>
        <%
            assert iter != null;
            while (iter.hasNext()) {
                Myemp emp = iter.next();
        %>
        <tr>
            <td><%=emp.getId()%>
            </td>
            <td><%=emp.getUsername()%>
            </td>
            <td><%=emp.getPassword()%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>