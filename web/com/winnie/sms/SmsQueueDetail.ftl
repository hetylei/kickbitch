[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>号码明细</title>
    [#include "/include/jsvalidation.ftl"]
    [#include "/include/main.ftl"]
    [#include "/include/popdialog.ftl"]

</head>
<body>
    [#noescape]${(obj?replace('|','<br>'))!'任务已经发送，请到日志查看'}[/#noescape]
</body>
</html>
[/#escape]
