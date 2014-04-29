[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>分组编辑</title>
    [#include "/include/jsvalidation.ftl"]
    [#include "/include/main.ftl"]
    [#include "/include/popdialog.ftl"]

</head>
<body>
    [#if obj.saved??]
    <script>closeDialog('${obj.KindObj.name?default('')}')</script>
    [#else]
    <form name="mainForm" method="post" action="/sms/contacts/saveKind">
        <INPUT TYPE="hidden" NAME="id" VALUE="${obj.KindObj.id?default('')}">
        <INPUT TYPE="hidden" NAME="pid" VALUE="${obj.KindObj.pid?default('')}">


        <p>分组名称
            <INPUT NAME="name" TYPE="text" VALUE="${(obj.KindObj.name?trim)?default('')}" size="20" maxlength="10"
                   class="required">
        </p>

        <p>
            <input type="image" name="button" id="button" src="/images/save.gif">
        </p>

    </form>
    [/#if]

</body>
</html>
[/#escape]
