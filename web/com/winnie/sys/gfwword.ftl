[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "/include/main.ftl"]

</head>
<body>
    [#include '/top.ftl']
    <form name="mainForm" id="mainForm" method="post" action="/manage/gfwwordsave" class="well">
        <h2>屏蔽词设置</h2>
        [#if obj.saved??]
            <span class="label label-success">保存成功</span>
        [/#if]
        <div class="alert alert-info" style="width: 400px">屏蔽词用半角逗号分隔,发送时会根据匹配提示</div>
        <div >
        <textarea id="gfwword" name="gfwword"  cols="400" rows="15" style="width: 600px"></textarea>
        <script>$("#gfwword").val("${(obj.smObj.gfwword?js_string)!""}")</script>
        <br>
        <input type="image" name="button" id="button" src="/images/save.gif">
        </div>
    </form>

    [#include '/bottom.ftl']
</body>
</html>
[/#escape]