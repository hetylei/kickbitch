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
		function addCol() {
			if ($("#select").val()==null || $("#select2").val()==null){
				alert('请选择对应列');
			} else {
                var opvalue = $('#select').val()+','+$('#select2').val();
                var opcaption = $('#select option:selected').html()+','+$('#select2 option:selected').html();
                $("#select3").append("<option value='"+opvalue+"'>"+opcaption+"</option>");
            }
		}
		function delCol() {
			if ($("#select3").val()==''){
				alert('请选择删除列');
			} else {
                $('#select3 option').each(function(){
                    if( $(this).val() == $("#select3").val()){
                        $(this).remove();
                    }
                });
            }
		}
        function selectCol() {
            if ($('#select3 option').length == 0) {
                alert('请选择对应列')
            } else {
                var r = '';
                $('#select3 option').each(function(){
                    if (r != '') r+="|";
                    r += $(this).val();
                });
                closeDialog(r);
            }
        }
    </script>

</head>
<body>
<form name="form1" method="post" action="">
  <p>&nbsp;</p>
  <table border="0" cellspacing="1" cellpadding="5">
    <tr>
      <td><select name="select" size="10" id="select">
        [#list obj as o]
        <option value="${o_index}">
          ${o!""}
          </option>
        [/#list]
      </select></td>
      <td>&nbsp;</td>
      <td>
      <select name="select2" size="10" id="select2">
        <option value="0">姓名</option>				
		<option value="1">公司名称</option>
        <option value="2">手机号</option>
        <option value="3">电话</option>
        <option value="4">备注</option>
      </select></td>
      <td><input type="button" name="button2" id="button2" value="-&gt;" onClick="addCol();"></td>
      <td><select name="select3" size="10" id="select3" style="width:300px"></select>
      <br>
      <input type="button" name="button3" id="button3" value="删除" onClick="delCol();"></td>
    </tr>
  </table>
  <p>
    <input type="image" name="button" id="button" src="/images/save.gif" onClick="selectCol();return false;">
  </p>
</form>
	
</body>
</html>
[/#escape]
