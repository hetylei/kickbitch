[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "../include/main.ftl"]

    <script>
        $(function () {
            if (window.top != window.self) {
                window.top.location.href = "/login";
            }
        });
    </script>
</head>
<body>
    [#include '../top.ftl']

<br>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="F5F5F5" height="10">
    <tr>
        <td width="11"><img src="images/main_69.gif" width="11" height="10" alt=""></td>
        <td width="658" valign="top">
            <table width="678" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="100%" height="1" bgcolor="CFCFCF"></td>
                </tr>
            </table>
        </td>
        <td width="11"><img src="images/main_72.gif" width="11" height="10" alt=""></td>
    </tr>
</table>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="F5F5F5">
    <tr>
        <td width="1" height="209" bgcolor="CFCFCF"></td>
        <td align="center" valign="top">
            <form name="loginForm" action="/doLogin" method="post">


                <table width="50%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="27%" align="right">&nbsp;</td>
                        <td width="73%" height="30">[#if obj?exists]
                            <div class="span4">
                                <div class="alert alert-error">
                                    <a class="close" data-dismiss="alert">×</a>
                                    <strong>登录错误:</strong>
                                ${obj.message!""}
                                </div>
                            </div>
                        [/#if]</td>
                    </tr>
                    <tr>
                        <td align="right">用户名&nbsp;</td>
                        <td height="35"><input name="uId" type="text" id="uId" size="30" maxlength="18" value=""
                                               placeholder="用户名"></td>
                    </tr>
                    <tr>
                        <td align="right">密码&nbsp;</td>
                        <td height="35"><input name="uPass" type="password" id="uPass" size="30" value=""
                                               placeholder="密码"></td>
                    </tr>
                    <tr>
                        <td align="right"></td>
                        <td height="30"><input type="submit" name="button" id="button" class=".btn" value="登录">
                            &nbsp;</td>
                    </tr>
                </table>


            </form>
        </td>
        <td width="1" bgcolor="CFCFCF"></td>
    </tr>
</table>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="F5F5F5"  height="10">
    <tr>
        <td width="11"><img src="images/main_127.gif" width="11" height="11" alt=""></td>
        <td width="658" valign="bottom">
            <table width="678" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="658" height="1" bgcolor="CFCFCF"></td>
                </tr>
            </table>
        </td>
        <td width="11"><img src="images/main_129.gif" width="11" height="11" alt=""></td>
    </tr>
</table>
    [#include '../bottom.ftl']
</body>
</html>
[/#escape]