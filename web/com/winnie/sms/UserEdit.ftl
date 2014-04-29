[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户编辑</title>
    [#include "/include/jsvalidation.ftl"]
    [#include "/include/main.ftl"]
    [#include "/include/popdialog.ftl"]

</head>
<body>
    [#if obj.saved??]
    <script>closeDialog()</script>
    [#else]
    [#if obj.msg??]
    <div class="span4">
        <div class="alert alert-error">
            <a class="close" data-dismiss="alert">×</a>
            <strong>保存:</strong>
        ${obj.msg!""}
        </div>
    </div>
    [/#if]
    <br>
    <form name="mainForm" id="mainForm" method="post" action="/manage/saveUser">
        <INPUT TYPE="hidden" NAME="id" ID="id" VALUE="${obj.userObj.id!''}">


        <table border="0" cellspacing="1" cellpadding="5">
          <tr>
            <td align="right">用户名</td>
            <td><input name="username" type="text" class="max-length-16 required" id="username" value="${(obj.userObj.username?trim)!''}"
                 maxlength=16></td>
          </tr>
          <tr>
            <td align="right">密码</td>
            <td><input name="password" type="password" class="max-length-16 required" id="password" value="${(obj.userObj.password?trim)!''}"
                 maxlength="16"></td>
          </tr>
          <tr>
            <td align="right">公司名称</td>
            <td><input name="title" type="text" class="max-length-40 required" id="title" value="${(obj.userObj.title?trim)!''}"
                 maxlength="40"></td>
          </tr>
          <tr>
            <td align="right">手机</td>
            <td><input name="mobile" type="text" class="max-length-15" id="mobile" value="${(obj.userObj.mobile?trim)!''}"
                 maxlength="15"></td>
          </tr>
          <tr>
            <td align="right">邮箱</td>
            <td><input name="email" type="text" class="max-length-40 " id="email" value="${(obj.userObj.email?trim)!''}"
                 maxlength="40"></td>
          </tr>
          <tr>
            <td align="right">联系人</td>
            <td><input name="lxr" type="text" class="max-length-50 " id="lxr" size="30" maxlength="40" value="${(obj.userObj.lxr?trim)!''}"></td>
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td><input type="image" name="button" id="button" src="/images/save.gif" onClick="return va.validate()"></td>
          </tr>
        </table>
    </form>
    <script>
        va = new Validation('mainForm');
    </script>
    [/#if]
</body>
</html>
[/#escape]
