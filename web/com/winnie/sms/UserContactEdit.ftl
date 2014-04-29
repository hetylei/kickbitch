[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>联系人编辑</title>
    [#include "/include/jsvalidation.ftl"]
    [#include "/include/main.ftl"]
    [#include "/include/popdialog.ftl"]

</head>
<body>
    [#if obj.saved??]
    <script>closeDialog()</script>
    [#else]
    <form name="mainForm" id="mainForm" method="post" action="/sms/contacts/saveContact">
        <INPUT TYPE="hidden" NAME="kindid" ID="kindid" VALUE="${obj.ContactObj.kindid!''}">
        <INPUT TYPE="hidden" NAME="id" ID="id" VALUE="${obj.ContactObj.id!''}">


        <table border="0" cellspacing="1" cellpadding="5">
          <tr>
            <td align="right">姓名</td>
            <td><input name="name" type="text" class="max-length-20 required" id="name" value="${obj.ContactObj.name!''}"
                 maxlength="20"></td>
          </tr>
          <tr>
            <td align="right">公司名称</td>
            <td><input name="company" type="text" class="max-length-40 " id="company" size="30" maxlength="40"></td>
          </tr>
          <tr>
            <td align="right">手机号</td>
            <td><input name="mobile" type="text" class="max-length-20 required" id="mobile" size="30" maxlength="20"></td>
          </tr>
          <tr>
            <td align="right">电话</td>
            <td><input name="tel" type="text" class="max-length-20 " id="tel" size="30" maxlength="20"></td>
          </tr>
          <tr>
            <td align="right">备注</td>
            <td><textarea id="memo" name="memo" cols="27" rows="10" class="max-length-200 "></textarea></td>
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td><input type="image" name="button" id="button" src="/images/save.gif" onClick="return va.validate()"></td>
          </tr>
        </table>
    <script>
            $("#name").val("${(obj.ContactObj.name?trim)!''}");
            $("#company").val("${(obj.ContactObj.company?trim)!''}");
            $("#mobile").val("${(obj.ContactObj.mobile?trim)!''}");
            $("#tel").val("${(obj.ContactObj.tel?trim)!''}");
            $("#memo").val("${(obj.ContactObj.memo?trim)!''}");
        </script>
    </form>
    <script>
        va = new Validation('mainForm');
    </script>
    [/#if]
</body>
</html>
[/#escape]
