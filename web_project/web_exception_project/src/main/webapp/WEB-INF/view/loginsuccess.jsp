<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <script src="<%=request.getContextPath()%>/static/bui/js/jquery-1.8.1.min.js"></script>
</head>
<body>
login successfully...........
<button id="logout">log out</button>
</body>
<script type="text/javascript">
    $('#logout').click(function () {
        $.ajax({
            type: "post",
            url: "<%=request.getContextPath()%>" + "/logout.json",
            data: {},
            dataType: "json",
            success: function (data) {
                if (data.success === false) {
                    alert(data.errorMsg);
                } else {
                    alert("logout succeed");
                    //login succeed
                    window.location.href = "<%=request.getContextPath()%>" + "/login.jhtml";
                }
            },
            error: function (data) {
                alert("调用失败....");
            }
        });
    });
</script>
</html>