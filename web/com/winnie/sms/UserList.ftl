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

        function newUser() {
            popDialog("newUser", "新建用户",
                    "/manage/newUser",
                    function (data) {
                        if (data =='ok') {
                            mainForm.submit();
                        }
                    }, 500, 500);
        }

        function editUser(id) {
            popDialog("editUser", "编辑用户",
                    "/manage/editUser/" + id,
                    function (data) {
                        if (data =='ok') {
                            mainForm.submit();
                        }
                    }, 500, 500);
        }

        function banUser(id) {
            $(document).ajaxStop($.unblockUI);
            $.blockUI({ message: '<h3> 正在处理...</h3>' });
            $.get('/manage/banUser/'+id,null,
                    function (data){
                        if (data != null ) {
                            $("#a_banUser" + id).html("启用");
                            $("#s_banUser" + id).html("账户停用");
                            $("#s_banUser" + id).removeClass("label-info");
                            $("#s_banUser" + id).addClass("label-warning");
                            $("#a_banUser" + id).attr("href", "javaScript:openUser('"+id+"')");
                        } else {
                            alert('停用用户失败.');
                        }
                    }
                    ,'text');
        }

        function openUser(id) {
            $(document).ajaxStop($.unblockUI);
            $.blockUI({ message: '<h3> 正在处理...</h3>' });
            $.get('/manage/openUser/'+id,null,
                    function (data){
                        if (data != null ) {
                            $("#a_banUser" + id).html("停用");
                            $("#s_banUser" + id).html("正常");
                            $("#s_banUser" + id).addClass("label-info");
                            $("#s_banUser" + id).removeClass("label-warning");
                            $("#a_banUser" + id).attr("href", "javaScript:banUser('"+id+"')");
                        } else {
                            alert('启用用户失败.');
                        }
                    }
                    ,'text');
        }

        function addDian(id) {
            popDialog("addDian", "用户充值",
                    "/manage/addDian/" + id,
                    function (data) {
                        if (data =='ok') {
                            mainForm.submit();
                        }
                    }, 400, 600);
        }
    </script>
</head>
<body>
    [#include '/top.ftl']

            <form name="mainForm" id="mainForm" action="/manage/userlist" method="post" class="well">
                <input type="hidden" name="pager.linesPerPage" id="pager.linesPerPage" value="10">
                <p>
                    <h2>用户列表</h2>
              <p><input type="image" name="button" src="/images/createUser.gif" onclick="newUser(); return false;"></p>
                    <table border="0" cellspacing="1" cellpadding="5">
                      <tr>
                        <td>用户名
                        <input name="username" type="text" id="username" value="${(obj.params.usernamelike)!""}"></td>
                        <td>公司名称
                        <input name="title" type="text" id="title" value="${(obj.params.title)!""}" ></td>
                        <td><input type="image" name="button" src="/images/find.gif"></td>
                      </tr>
                    </table>

                [#include '/include/pager.ftl']
                [@showPager pager=obj.pager url='/manage/userlist'/]
          <table width="50%" 
                       class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th nowrap bgcolor="#999999">操作</th>
                        <th nowrap bgcolor="#999999">用户名</th>
                        <th nowrap bgcolor="#999999">公司名称</th>
                        <th nowrap bgcolor="#999999">手机</th>
                        <th nowrap bgcolor="#999999">邮箱</th>
                        <th nowrap bgcolor="#999999">联系人</th>
                        <th nowrap bgcolor="#999999">余额</th>
                        <th nowrap bgcolor="#999999">充值总数</th>
                      </tr>
                    </thead>
                    <tbody>
                        [#list obj.list?default([]) as c]
                        <tr>
                            <td><a href="javaScript:editUser('${c.id}')">详情</a> <a href="javaScript:addDian('${c.id}')">充值</a> [#if c.login_key
]<a href="javaScript:banUser('${c.id}')" id="a_banUser${c.id}">停用</a><span class="label label-info" id="s_banUser${c.id}">正常</span>[#else]<a href="javaScript:openUser('${c.id}')" id="a_banUser${c.id}">启用</a><span class="label label-warning" id="s_banUser${c.id}">账户停用</span>[/#if]</td>
                            <td>${c.username!""}&nbsp; </td>
                            <td>${(c.title)!''}&nbsp;</td>
                            <td>${(c.mobile)!''}&nbsp;</td>
                            <td>${c.email!''}&nbsp;</td>
                            <td>&nbsp;
                            ${c.lxr!""}</td>
                            <td>${c.smsdian!""}</td>
                            <td>${c.countdian!""}</td>
                        </tr>
                        [/#list]
                    </tbody>
                </table>
            </form>

    [#include '/bottom.ftl']
</body>
</html>
[/#escape]