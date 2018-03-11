<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>编辑任务</title>
</head>
<body>
<center>
    <h3>编辑Trigger</h3>
    <form action="editJob" method="post">

        <input type="hidden" name="oldjobName" value="${jobEntity.jobName}">
        <input type="hidden" name="oldjobGroupName" value="${jobEntity.jobGroupName}">
        <input type="hidden" name="oldtriggerName" value="${jobEntity.triggerName}">
        <input type="hidden" name="oldtriggerGroup" value="${jobEntity.triggerGroupName}">
        <table id="table_report" class="table table-striped table-bordered table-hover">
            <tr>
                <td>cron</td>
                <td><input type="text" name="cronExpr" value="${jobEntity.cronExpr}"/></td>
            </tr>
            <tr>
                <td>clazz</td>
                <td><input readonly="readonly" type="text" name="clazz" value="${jobEntity.clazz}"/></td>
            </tr>
            <tr>
                <td>jobName</td>
                <td><input type="text" name="jobName" value="${jobEntity.jobName}"/></td>
            </tr>
            <tr>
                <td>jobGroup</td>
                <td><input type="text" name="jobGroupName" value="${jobEntity.jobGroupName}"/></td>
            </tr>
            <tr>
                <td>triggerName</td>
                <td><input type="text" name="triggerName" value="${jobEntity.triggerName}"/></td>
            </tr>
            <tr>
                <td>triggerGroupName</td>
                <td><input type="text" name="triggerGroupName" value="${jobEntity.triggerGroupName}"/></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit" style="border: 0;background-color: #428bca;">提交</button>
                </td>
            </tr>
        </table>
    </form>
</center>
</body>
</html>