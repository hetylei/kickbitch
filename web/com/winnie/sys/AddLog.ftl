[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "/include/main.ftl"]
    [#include "/include/datepicker.ftl"]
	[#include "/include/popdialog.ftl"]

</head>
<body>
    [#include '/top.ftl']

            <form action="/manage/addLog" method="post" class="well">
                <input type="hidden" name="pager.linesPerPage" id="pager.linesPerPage" value="10">
                <p>
                    <h2>充值记录</h2>
                    <table border="0" cellspacing="1" cellpadding="5">
                      <tr>
                        <td align="right">用户名</td>
                        <td><input name="username" type="text" id="username" value="${(obj.params.username)!""}"></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td align="right">充值时间</td>
                        <td><input name="opdate1" type="text" id="opdate1" value="${(obj.params.opdate1)!""}" class="Wdate"onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                        <input name="opdate2" type="text" id="opdate2" value="${(obj.params.opdate2)!""}" class="Wdate"onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>
                        <td><input type="image" name="button" id="button" src="/images/find.gif"></td>
                      </tr>
                    </table>

                [#include '/include/pager.ftl']
                [@showPager pager=obj.pager url='/manage/addLog'/]
                <table width="100%" 
                       class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th bgcolor="#999999" style="width:30%">用户名</th>
                        <th bgcolor="#999999">充值时间</th>
                        <th bgcolor="#999999">充值数量</th>
                        <th bgcolor="#999999">备注</th>

                    </tr>
                    </thead>
                    <tbody>
                        [#list obj.list?default([]) as c]
                        <tr>
                            <td>${c.username!""}&nbsp; </td>
                            <td>${(c.opdate?string('yyyy-MM-dd HH:mm:ss'))!''}&nbsp;</td>
                            <td>${c.charge!'0'}&nbsp;</td>
                            <td>${c.memo!''}&nbsp;</td>
                        </tr>
                        [/#list]
                    </tbody>
                </table>
            </form>
    [#include '/bottom.ftl']
</body>
</html>
[/#escape]