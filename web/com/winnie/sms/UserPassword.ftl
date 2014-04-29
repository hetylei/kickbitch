[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "/include/jsvalidation.ftl"]
    [#include "/include/main.ftl"]
    <script>

        function checkPass () {
            if ($("#newPassword").val() != $("#newPassword2").val()) {
                alert('新密码两次输入不一致');
                return false;
            }
            return true;
        }
    </script>

</head>
<body>
    [#include '/top.ftl']

            <form action="/savePassword" name="mainForm" id="mainForm" method="post" class="well">
                    <h2>修改密码</h2>
                <br><br>

                <p>[#if obj?? ]<script type="text/javascript">alert('${obj.msg!""}');</script>[/#if]</p>

                <table border="0" cellspacing="1" cellpadding="5">
                    <tr>
                        <td align="right">原密码</td>
                        <td><input name="oldPassword" type="password" class="max-length-16 required" id="oldPassword" value=""
                                   maxlength="16"></td>
                    </tr>
                    <tr>
                        <td align="right">新密码</td>
                        <td><input name="newPassword" type="password" class="max-length-16 required" id="newPassword" value=""
                                   maxlength="16"></td>
                    </tr>
                    <tr>
                        <td align="right">新密码确认</td>
                        <td><input name="newPassword2" type="password" class="max-length-16 required" id="newPassword2" value=""
                                   maxlength="16"></td>
                    </tr>
                    <tr>
                        <td align="right">&nbsp;</td>
                        <td><input type="image" name="button" id="button" src="/images/save.gif" onClick="return va.validate() && checkPass();"></td>
                    </tr>
                </table>
            </form>

<script>
    va = new Validation('mainForm');
</script>
    [#include '/bottom.ftl']
</body>
</html>
[/#escape]