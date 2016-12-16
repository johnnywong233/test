<title>test session</title>
</head>
<body>
<%  
    if(session.getAttribute("user") == null) {  
        session.setAttribute("user", "johnny");  
        out.print("null");  
    }  
    else {  
        out.print(session.getAttribute("user"));  
    }  
%>
</body>
</html>