[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>选择号码列</title>
    <link type="text/css" href="/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
    <!--[if lt IE 7]>
    <link type="text/css" href="/js/bootstrap/css/ie6.css" rel="stylesheet" />
    <![endif]-->

    <link type="text/css" href="/css/main.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="/js/jquery.json-2.3.min.js"></script>
    <script type="text/javascript" src="/js/jquery.blockUI.js"></script>
    [#include "/include/popdialog.ftl"]
    <script type="text/javascript">

        function selectCol() {
            if ($("#select").val()=='') {
                alert('请选择列')
            } else {
                closeDialog($("#select").val());
            }
        }
    </script>

</head>
<body>
<form name="form1" method="post" action="">
  <p>
    <select name="select" size="10" id="select">
    [#list obj as o]
    <option value="${o_index}">${o!""}</option>
    [/#list]
    </select>
  </p>
  <p>
    <input type="button" name="button" id="button" value="选择" onClick="selectCol()">
  </p>
</form>
	
</body>
</html>
[/#escape]
