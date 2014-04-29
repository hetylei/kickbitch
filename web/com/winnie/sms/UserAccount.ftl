[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "/include/main.ftl"]
    [#include "/include/datepicker.ftl"]

</head>
<body>
    [#include '/top.ftl']

            <form action="/sms/queue" method="post" class="well">
                <input type="hidden" name="pager.linesPerPage" id="pager.linesPerPage" value="10">
                <p>
                    <h2>查询余额</h2>
                <br><br>
                    <p>您当前的余额为 <span class="label label-success">${obj.smsdian}</span></p>
                [#--
                    充值/消费记录
              <br>

                [#include '/include/pager.ftl']
                [@showPager pager=obj.pager url='/setting/account'/]
                <table width="50%" 
                       class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th bgcolor="#999999">时间</th>
                        <th bgcolor="#999999">数量</th>
                        <th bgcolor="#999999">类型</th>
                      </tr>
                    </thead>
                    <tbody>
                        [#list obj.list?default([]) as c]
                        <tr>
                            <td>${c.logDate?string('yyyy-MM-dd HH:mm:ss')}&nbsp;</td>
                            <td>${c.logCount}&nbsp; </td>
                            <td>${c.op}&nbsp;</td>
                        </tr>
                        [/#list]
                    </tbody>
                </table>
                --]
            </form>

    [#include '/bottom.ftl']
</body>
</html>
[/#escape]