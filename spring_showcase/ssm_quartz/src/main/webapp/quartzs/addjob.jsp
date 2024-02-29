<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新增任务</title>
</head>
<body>
<center>

    <form action="${pageContext.request.contextPath }/add" method="post">
        <h3>新增Trigger</h3><br/>
        <br/>
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td>cron</td>
                <td><input type="text" name="cronExpr" value=""/></td>
            </tr>
            <tr>
                <td>clazz</td>
                <td><input type="text" name="clazz" value=""/></td>
            </tr>
            <tr>
                <td>jobName</td>
                <td><input type="text" name="jobName" value=""/></td>
            </tr>
            <tr>
                <td>jobGroupName</td>
                <td><input type="text" name="jobGroupName" value=""/></td>
            </tr>
            <tr>
                <td>triggerName</td>
                <td><input type="text" name="triggerName" value=""/></td>
            </tr>
            <tr>
                <td>triggerGroupName</td>
                <td><input type="text" name="triggerGroupName" value=""/></td>
            </tr>
            <tr>
                <td></td>
                <td align="center" colspan="2">
                    <button type="submit" style="border: 0;background-color: #428bca;">提交</button>
                </td>
            </tr>
        </table>
    </form>

</center>
</body>
</html>