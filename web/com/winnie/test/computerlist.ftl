[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>和易商业智能分析决策平台</title>
	    [#include "/include/main.ftl"]
	</head>
    <script>
		function testCreate() {
			$.post('/tutorial/computerShop/genTestData/5', null,
		 	function (data){
				alert('生成好了 - >' + data);
			 },
			 'json');
		}
	</script>
	<body>
[#include '/top.ftl']
    <div class="span9" >
        <div class="hero-unit">
    	<form action="/tutorial/computerShop" method="post">
        <p>
          <label for="cpu"></label>
          cpu
          <input name="cpu" type="text" id="cpu" value="${(obj.params.cpu)!""}">
          <input type="submit" name="button" id="button" value="查询">
          【<a href="/tutorial/computerShop/new">新建</a>】</p>
          【<a href="javaScript:testCreate();">测试生成5条数据</a>】</p>
        
        [#include '/include/pager.ftl']
        [@showPager pager=obj.pager url='/tutorial/computerShop'/]
    	<table width="50%" border="1" cellspacing="1" cellpadding="1"
               class="table table-striped table-bordered table-condensed">
            <thead>
            <tr>
    	    <th bgcolor="#999999">操作</th>
    	    <th bgcolor="#999999">cpu型号</th>
    	    <th bgcolor="#999999">内存型号</th>
    	    <th bgcolor="#999999">硬盘型号</th>
    	    <th bgcolor="#999999">键盘型号</th>
    	    <th bgcolor="#999999">鼠标型号</th>
    	    <th bgcolor="#999999">组装时间</th>

  	      </tr>
            </thead>
            <tbody>
          [#list obj.computerList?default([]) as c]
    	  <tr>
    	    <td><a href="/tutorial/computerShop/edit/${c.id}"> 编辑</a> <a href="/tutorial/computerShop/del/${c.id}">删之</a></td>
    	    <td>${c.cpu!""}&nbsp; </td>
    	    <td>${c.memory!""}&nbsp;</td>
    	    <td>${c.hardDisk!""}&nbsp;</td>
    	    <td>${c.keyBoard!""}&nbsp;</td>
    	    <td>${c.mouse!""}&nbsp;</td>
    	    <td>${(c.madeDate?string("yyyy-MM-dd"))!""}&nbsp;</td>
  	      </tr>
          [/#list]
            </tbody>
  	  </table> 
      </form>
      </div>
  </div>
[#include '/bottom.ftl']
</body>
</html>
[/#escape]