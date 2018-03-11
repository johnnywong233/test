<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>任务列表</title>
    <script type="text/javascript">
        //暂停
        function pauseJob(jobName, jobGroupName) {
            if (confirm("确定要暂停任务吗?")) {
                location.href = "pauseJob?jobName=" + jobName + "&jobGroupName="
                    + jobGroupName;
            }
        }

        //启动
        function resumeJob(jobName, jobGroupName) {
            if (confirm("确定要启动任务吗?")) {
                location.href = "resumeJob?jobName=" + jobName + "&jobGroupName="
                    + jobGroupName;
            }
        }

        //删除
        function deleteJob(jobName, jobGroupName, triggerName, triggerGroupName) {
            if (confirm("确定要删除任务吗?")) {
                location.href = "deleteJob?jobName=" + jobName + "&jobGroupName="
                    + jobGroupName + "&triggerName=" + triggerName
                    + "&triggerGroupName=" + triggerGroupName;
            }
        }

        //跳转到编辑页面
        function editJob(jobName, jobGroupName) {
            location.href = "toEditJob?jobName=" + jobName + "&jobGroupName=" + jobGroupName;
        }
    </script>
</head>
<body>
<center>
    <h3>任务列表Test2</h3>
    <table id="table_report" width="100%"
           class="table table-striped table-bordered table-hover" border="1"
           cellpadding="0" cellspacing="0">
        <thead>
        <tr>
            <!-- th class="center">序号</th-->
            <th align="center">任务组名称</th>
            <th align="center">定时任务名称</th>
            <th align="center">触发器组名称</th>
            <th align="center">触发器名称</th>
            <th align="center">时间表达式</th>
            <th align="center">上次运行时间</th>
            <th align="center">下次运行时间</th>
            <th align="center">任务状态</th>
            <!--
            <th class="center">已经运行时间</th>
            <th class="center">持续运行时间</th>
             -->
            <th align="center">开始时间</th>
            <th align="center">结束时间</th>
            <th align="center">任务类名</th>
            <!--
            <th class="center">方法名称</th>
            <th class="center">jobObject</th>
            <th class="center">运行次数</th>-->

            <th align="center" width="15%">操作</th>
        </tr>
        </thead>
        <tbody>
        <!-- 开始循环 -->
        <c:choose>
            <c:when test="${not empty jobInfos && jobInfos.size()>0}">
                <c:forEach items="${jobInfos}" var="var" varStatus="vs">
                    <tr>
                        <td align='center' style="width: auto;">${var.jobGroupName}</td>
                        <td align='center' style="width: auto;">${var.jobName}</td>
                        <td align='center' style="width: auto;">${var.triggerGroupName}</td>
                        <td align='center' style="width: auto;">${var.triggerName}</td>
                        <td align='center' style="width: auto;">${var.cronExpr}</td>
                        <td align='center' style="width: auto;">
                            <fmt:formatDate value="${var.previousFireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td align='center' style="width: auto;">
                            <fmt:formatDate value="${var.nextFireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td align='center' style="width: auto;">
                            <c:if test="${var.jobStatus == 'NONE'}">未知</c:if>
                            <c:if test="${var.jobStatus == 'NORMAL'}">正常运行</c:if>
                            <c:if test="${var.jobStatus == 'PAUSED'}">暂停状态</c:if>
                            <c:if test="${var.jobStatus == 'COMPLETE'}">完成状态</c:if>
                            <c:if test="${var.jobStatus == 'ERROR'}">错误状态</c:if>
                            <c:if test="${var.jobStatus == 'BLOCKED'}">锁定状态</c:if>
                        </td>


                            <%--
                            <td class='center' style="width: auto;">${var.runTimes}</td>
                            <td class='center' style="width: auto;">${var.duration}</td>
                            --%>

                        <td align='center' style="width: auto;"><fmt:formatDate
                                value="${var.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td align='center' style="width: auto;"><fmt:formatDate
                                value="${var.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td align='center' style="width: auto;">${var.jobClass}</td>

                            <%--
                            <td class='center' style="width: auto;">${var.jobMethod}</td>
                            <td class='center' style="width: auto;">${var.jobObject}</td>
                            <td align='center' style="width: auto;">${var.count}</td>--%>

                        <td align='center' style="width: auto;">
                            <a href="#" onclick="editJob('${var.jobName}','${var.jobGroupName}');">编辑</a>&nbsp;
                            <c:if test="${var.jobStatus == 'NORMAL'}">
                                <a href="#" onclick="pauseJob('${var.jobName}','${var.jobGroupName}');">暂停</a>&nbsp;
                            </c:if>
                            <c:if test="${var.jobStatus == 'PAUSED'}">
                                <a href="#" onclick="resumeJob('${var.jobName}','${var.jobGroupName}');">启动</a> &nbsp;
                            </c:if>
                            <a href="#"
                               onclick="deleteJob('${var.jobName}','${var.jobGroupName}','${var.triggerName}','${var.triggerGroupName}');">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr class="main_info">
                    <td colspan="100" align="center">没有相关数据</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</center>
</body>
</html>