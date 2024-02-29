<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Run Job</title>
</head>
<body>
<center>
    <h3>Run Job</h3>
    <table border="1" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td align="center">name</td>
            <td align="center">group</td>
            <td align="center">cron表达式</td>
            <td align="center">状态</td>
            <td align="center">描述</td>
        </tr>
        <c:choose>
            <c:when test="${not empty jobList && jobList.size()>0}">
                <c:forEach var="job" items="${jobList }">
                    <tr>
                        <td align="center">${job.jobName }</td>
                        <td align="center">${job.jobGroup }</td>
                        <td align="center">${job.cronExpression }</td>
                        <td align="center">
                            <c:if test="${job.jobStatus == 'NONE'}">未知</c:if>
                            <c:if test="${job.jobStatus == 'NORMAL'}">正常运行</c:if>
                            <c:if test="${job.jobStatus == 'PAUSED'}">暂停状态</c:if>
                            <c:if test="${job.jobStatus == 'COMPLETE'}">完成状态</c:if>
                            <c:if test="${job.jobStatus == 'ERROR'}">错误状态</c:if>
                            <c:if test="${job.jobStatus == 'BLOCKED'}">锁定状态</c:if></td>
                        <td align="center">${job.description }</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr class="main_info">
                    <td colspan="100" align="center" style="color:red">没有相关数据</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</center>
</body>
</html>