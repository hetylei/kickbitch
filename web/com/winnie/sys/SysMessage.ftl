[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "/include/main.ftl"]
    <script type="text/javascript" language="JavaScript" src="/js/ckeditor/ckeditor.js"></script>
    <script>
        $(document).ready(function () {
            CKEDITOR.replace('msg');
            var oEditor1 = CKEDITOR.instances.msg;

            oEditor1.setData("[#noescape ]${((obj.smObj.msg)!'')?js_string}[/#noescape]");
        });
    </script>
</head>
<body>
    [#include '/top.ftl']
    <form name="mainForm" id="mainForm" method="post" action="/manage/msgsave" class="well">
        <h2>平台公告</h2>
        [#if obj.saved??]
            <span class="label label-success">保存成功</span>
        [/#if]
        <textarea id="msg" name="msg"  cols="200" rows="20"></textarea>
        <input type="image" name="button" id="button" src="/images/save.gif">

    </form>
    [#include '/bottom.ftl']
</body>
</html>
[/#escape]