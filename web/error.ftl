[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "include/main.ftl"]
</head>
<body>
    [#include 'top.ftl']
<div class="span9">
    <div class="hero-unit">

    页面发生错误: <span class="label label-warning">${(obj.message)!''}</span>

    </div>
</div>
    [#include 'bottom.ftl']
</body>
</html>
[/#escape]