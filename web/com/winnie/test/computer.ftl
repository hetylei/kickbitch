[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>和易商业智能分析决策平台</title>
	    [#include "/include/main.ftl"]
	</head>
	<body>
<div class="ui-widget page" >
[#include '/top.ftl']
  <div class="maincontent" >
  <form action="/tutorial/computerShop/save" method="post">
   	<p>
   	  <input type="submit" name="button" id="button" value="保存">
   	</p>
    
   	  <table border="0" cellspacing="1" cellpadding="0">
   	    <tr>
   	      <td bgcolor="#CCCCCC">cpu型号</td>
   	      <td><label for="cpu"></label>
          <input type="text" name="cpu" id="cpu" value="${obj.cpu!""}"></td>
   	      <td bgcolor="#CCCCCC">内存型号</td>
   	      <td><input type="text" name="memory" id="memory" value="${obj.memory!""}"></td>
   	      <td bgcolor="#CCCCCC">硬盘型号</td>
   	      <td><input type="text" name="hardDisk" id="hardDisk" value="${obj.hardDisk!""}"></td>
        </tr>
   	    <tr>
   	      <td bgcolor="#CCCCCC">键盘型号</td>
   	      <td><input type="text" name="keyBoard" id="keyBoard" value="${obj.keyBoard!""}"></td>
   	      <td bgcolor="#CCCCCC">鼠标型号</td>
   	      <td><input type="text" name="mouse" id="mouse" value="${obj.mouse!""}"></td>
   	      <td bgcolor="#CCCCCC">cpu核心数量</td>
   	      <td><input type="text" name="cpuCoreCount" id="cpuCoreCount"  value="${obj.cpuCoreCount!""}"></td>
        </tr>
    	  <tr>
    	    <td bgcolor="#CCCCCC">组装时间</td>
    	    <td><input type="text" name="madeDate" id="madeDate" value="${(obj.madeDate?string("yyyy-MM-dd"))!""}" onClick="popCalendar(this,'yy-mm-dd')"></td>
    	    <td>&nbsp;</td>
    	    <td>&nbsp;</td>
    	    <td>&nbsp;</td>
    	    <td>&nbsp;</td>
   	    </tr>
    </table>
   	  <input type="hidden" name="sn" id="sn" value="${obj.sn!""}">
    </form> 
  </div>
[#include '/bottom.ftl']
</div>
</body>
</html>
[/#escape]