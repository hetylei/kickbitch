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
    <script>
    	function showDetail(id) {
            popDialog("smsdetail", "号码明细",
                    "/sms/sendlog/detail/" + id,
                    null, 500, 500);
		}
    </script>
</head>
<body>
    [#include '/top.ftl']

            <form action="/manage/loglist" method="post" class="well">
                <input type="hidden" name="pager.linesPerPage" id="pager.linesPerPage" value="10">
                <p>
                    <h2>短信发送记录</h2>
                    <table border="0" cellspacing="1" cellpadding="5">
                      <tr>
                        <td align="right">短信内容</td>
                        <td><input name="msg" type="text" id="msg" value="${(obj.params.msg)!""}"></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td align="right"> 用户</td>
                        <td><input name="com_name" type="text" id="com_name" value="${(obj.params.com_name)!""}"></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td align="right">创建时间</td>
                        <td><input name="c_date1" type="text" id="c_date1" value="${(obj.params.c_date1)!""}" class="Wdate"onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                        <input name="c_date2" type="text" id="c_date2" value="${(obj.params.c_date2)!""}" class="Wdate"onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td align="right">发送时间</td>
                        <td><input name="send_time1" type="text" id="send_time1" value="${(obj.params.send_time1)!""}" class="Wdate"onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                        <input name="send_time2" type="text" id="send_time2" value="${(obj.params.send_time2)!""}" class="Wdate"onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>
                        <td><input type="image" name="button" id="button" src="/images/find.gif"></td>
                      </tr>
                    </table>

                [#include '/include/pager.ftl']
                [@showPager pager=obj.pager url='/manage/loglist'/]
                <table 
                       class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th width="75" nowrap bgcolor="#999999">操作</th>
                      <th bgcolor="#999999" style="width:30%">短信内容</th>
                        <th width="75" nowrap bgcolor="#999999">创建时间</th>
                        <th width="60" nowrap bgcolor="#999999">用户</th>
                      <th width="75" nowrap bgcolor="#999999">发送时间</th>
                        <th width="60" nowrap bgcolor="#999999">发送数量</th>
                        <th width="120" bgcolor="#999999">发送状态</th>

                    </tr>
                    </thead>
                    <tbody>
                        [#list obj.list?default([]) as c]
                        <tr>
                            <td><a href="javascript:showDetail('${c.id}')">详细</a></td>
                            <td>${c.msg!""}&nbsp; </td>
                            <td>${(c.c_date?string('yyyy-MM-dd HH:mm:ss'))!''}&nbsp;</td>
                            <td>${c.com_name!''}(${c.user_id!''})&nbsp;</td>
                            <td>${(c.send_time?string('yyyy-MM-dd HH:mm:ss'))!''}&nbsp;</td>
                            <td>${c.tel_count!''}&nbsp;</td>
                            <td>[#if c.sms_flag==-1]余额不足[#elseif c.sms_flag==1]发送失败:${c.mobilek!''}[#else]发送成功[/#if]&nbsp;</td>
                        </tr>
                        [/#list]
                    </tbody>
                </table>
            </form>

    [#include '/bottom.ftl']
</body>
</html>
[/#escape]