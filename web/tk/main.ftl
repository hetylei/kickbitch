[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    [#include "/include/main.ftl"]
</head>
<body>


    <a href="/tk/startall">立即执行所有任务</a>
    <table border="1">
        <tr>
            <td>op</td>
            <td>id</td>
            <td>taskname</td>
            <td>tasktime</td>
            <td>use/delete</td>
            <td>state</td>
            <td>url</td>
        </tr>
    [#list obj as task]
        <tr [#if task.isUse==0 || task.isDelete==1]bgcolor="gray" [/#if]>
            <td><a href="/tk/start/${task.id}">run now</a></td>
            <td>${task.id}</td>
            <td>${task.taskName}</td>
            <td>${task.taskTime}</td>
            <td>${task.isUse}/${task.isDelete}</td>
            <td>[#if task.lastState??]<a href="#" title="${task.lastState}">[#if task.lastState?length>30]<span
                    style="color: red; ">${task.lastState?substring(0,30)}</span>[#else]${task.lastState}[/#if]</a>[/#if]</td>
            <td>[#if task.mainUrl??]<a href="${task.mainUrl}" title="${task.mainUrl}" target="_blank">open</a>[/#if]</td>
        </tr>
    [/#list]
    </table>

</body>
</html>
[/#escape]