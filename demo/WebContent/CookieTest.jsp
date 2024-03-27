<title>test cookie</title>
</head>
<body>
<%
    Cookie cookie1 = new Cookie("name", "johnny");
    response.addCookie(cookie1);  //set cookie expire when browser close

    Cookie cookie2 = new Cookie("age", "26");
    cookie2.setMaxAge(60 * 60 * 24); // set cookie expire after 1 day
    response.addCookie(cookie2);
%>
<h1>C'est La Vie </h1>
</body>
</html>