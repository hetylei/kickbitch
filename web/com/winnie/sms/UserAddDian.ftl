[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户充值</title>
    [#include "/include/jsvalidation.ftl"]
    [#include "/include/main.ftl"]
    [#include "/include/popdialog.ftl"]

</head>
<body>
    [#if obj.saved??]
    <script>closeDialog('ok')</script>
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
    <form name="mainForm" id="mainForm" method="post" action="/manage/saveDian" class="form-horizontal">
<fieldset>
        <INPUT TYPE="hidden" NAME="id" ID="id" VALUE="${obj.userObj.id!''}">

		<div class="control-group">
            <label class="control-label">用户名</label>
            <div class="controls">
              <span class="label label-success">${obj.userObj.username}</span>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">公司名称</label>
            <div class="controls">
              <span class="label label-success">${obj.userObj.title}</span>
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">余额</label>
            <div class="controls">
              <span class="label label-important">${obj.userObj.smsdian}</span> 
            </div>
          </div>
          <div class="control-group">
            <label class="control-label">充值</label>
            <div class="controls">
              <INPUT NAME="dian" TYPE="text" class="max-length-40 required validate-number" ID="dian" VALUE=""
                 maxlength="40"> 
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="memo">备注</label>
            <div class="controls">
              <INPUT NAME="memo" TYPE="text" class="max-length-100" ID="memo" VALUE="" size="40"
                 maxlength="100"><br>
            </div>
          </div>
          <div class="control-group">
            <div class="controls">
              <input type="image" name="button" id="button" src="/images/save.gif" onclick="return va.validate()">
            </div>
          </div>
          
 </fieldset> 
</form>
    <script>
        va = new Validation('mainForm');
    </script>
    [/#if]
</body>
</html>
[/#escape]
