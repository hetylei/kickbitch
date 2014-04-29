[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>从通讯录导入号码</title>
    
    <link type="text/css" href="/css/cupertino/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
    <!--[if lt IE 7]>
    <link type="text/css" href="/js/bootstrap/css/ie6.css" rel="stylesheet" />
    <![endif]-->

    <link type="text/css" href="/css/main.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="/js/jquery.json-2.3.min.js"></script>
    <script type="text/javascript" src="/js/jquery.blockUI.js"></script>
    [#include "/include/zTree.ftl"]
    [#include "/include/popdialog.ftl"]
    [#include "/include/flexigrid.ftl"]
        <script>
        var dirSn = '-1';
		var opMoveKind = false;
        var setting = {
            async : true,
            asyncUrl : "/sms/contacts/ckindlist",
            asyncParam : ["id"],
            treeNodeKey : "id",
            treeNodeParentKey : "pid",
            showLine : true,
            expandSpeed:"",
            checkable:true,
            checkStyle:"checkbox",
            checkType:{ "Y": "", "N": "" },
            callback : {
                click: zTreeOnClick
            }
        };



        function zTreeOnClick(event, treeId, treeNode) {
            dirSn = treeNode.id;

			$("#aExport").attr("href", "/sms/contactsExport/" + dirSn);
			$("#kindid").val(dirSn);
			$(".Contactlist").flexOptions({url : '/sms/Contacts/' + dirSn , page: 1});
			$(".Contactlist").flexReload();
			$("#btnGroup").val($("<div>").html("分组："+ treeNode.name+ "&nabla;").text());
			$("#treediv").hide();
        }

        function query() {
            if ($("#name").val() != '') {
                $(".Contactlist").flexOptions({url : '/sms/contacts/' + dirSn , query:$("#name").val() , page: 1});
                $(".Contactlist").flexReload();
            }

        }

        var zTree;
        $(document).ready(function() {
            zTree = $("#tree").zTree(setting);
        
        });


        function refreshDir () {
            var treeNode = zTree.getSelectedNode();
            if (!treeNode) {
                return;
            }
            zTree.reAsyncChildNodes(treeNode, "refresh")
        }


        var rc = function refreshContact() {
            $(".Contactlist").flexOptions({url : '/sms/Contacts/' + dirSn , page: 1});
            $(".Contactlist").flexReload();
        };

     

        function mouseClick() {
            try {

                alert('请选择分组');
                var treeNode = zTree.getSelectedNode();
                if (!treeNode) {
                    alert('请选择分组');
                    return false;
                }
            } catch (ex)  {
                this.debug(ex);
            }
        }

      
		
		function selectAll() {
			$("[name='flexigridCheck']").attr("checked",'true');
		}
		
		function selectOther() {
			 $("[name='flexigridCheck']").each(function(){
				if($(this).attr("checked"))
				{
					$(this).removeAttr("checked");
				}else {
					$(this).attr("checked",'true');
				}
			});
		}
		
		function cancelSelect() {
			 $("[name='flexigridCheck']").removeAttr("checked");//取消全选
		}
		

		
		function cancelTreeOP() {
			$('#treediv').hide();
		}
		
		function getContactMobile() {
            var treeNodes = zTree.getCheckedNodes();
            var kinds = "";
            for (i=0;i<treeNodes.length;i++) {
                if (kinds != "") kinds += ",";
                kinds += treeNodes[i].id;
            }
            $.post('/sms/contacts/getKindsMobile', {"kinds":kinds},
                    function (data){
                        if (data != null ) {
                            closeDialog(data);
                        } else {
                            alert('获取分类号码失败.');
                        }
                    }
                    ,'json');
        }
	
		function getContactMobile2() {
			var str = '';
			$("[name='flexigridCheck']:checked").each(function(){
			  if (str != '') str += ",";str+=$(this).val();
			});
			closeDialog(str);
		}
    </script>


</head>
<body>
<form id="mainForm" name="mainForm" method="post" action="" class="well">           
<div style="width:600px;height:600px;float:left; position:relative;">
        		
		  <table>
            <tr>
              <td>
                <input type="hidden" name="kindid" id="kindid" value=""/>
              </td>
              <td>
                <input type="button" name="btn" id="btnGroup" value="分组：全部 &nabla;" onClick="$('#treediv').show();"/>|
                <input type="button" name="btn" id="btn" value="从选择分类导入" onClick="getContactMobile();"/>
              </td>
              <td>

              </td>
              <td>
         
       		  </td>
              <td>
                
              </td>
              <td>
              </td>
   		    </tr>
                <tr>
                  <td colspan="6"><div id="treediv" style="width:330px;height:350px; position:absolute;display:none; z-index:99999999; background-color:#FFFFFF; border-style:outset">
                    <ul id="tree" class="tree" style="width:330px; height:280px; overflow:auto;"></ul>
                    
                    <input type="button" name="button3" id="button3" value="关闭" onclick="cancelTreeOP();"/>
                </div>[<a href="#" onClick="selectAll();">全选</a>][<a href="#" onClick="selectOther();">反选</a>][<a href="#" onClick="cancelSelect();">取消选择</a>]                <INPUT NAME="name" TYPE="text" ID="name" maxlength="20" size="6"> <input type="button" name="btn4" id="btn4" value="查找" onClick="query();"/>|<input type="button" name="btn2" id="btn2" value="从选择联系人导入" onClick="getContactMobile2();"/></td>
                </tr>
          </table>
          
<table class="Contactlist" style="display: none"></table>
                <script>

                    $(".Contactlist").flexigrid({
                        url : '/sms/Contacts/-1',
                        dataType : 'json',
                        colModel : [
							{display : 'ck', name : 'mobile', width : 25, sortable : true, align : 'left', checkbox:true},
							{display : 'id', name : 'id', width : 25, sortable : true, align : 'left', hide:true},
                            {display : '操作', name : 'op', width : 30, sortable : true, align : 'left', hide:true},
                      
                            {display : '姓名', name : 'name', width : 45, sortable : true, align : 'left'},
                            {display : '公司名称', name : 'company', width : 200, sortable : true, align : 'left'},
                            {display : '手机号', name : 'mobile', width : 85, sortable : true, align : 'left'},
                            {display : '电话', name : 'tel', width : 85, sortable : true, align : 'left'},
                            {display : '备注', name : 'memo', width : 70, sortable : true, align : 'left'}
                        ],
                        sortname : "id",
                        sortorder : "desc",
                        usepager : true,
                        title : '联系人',
                        useRp : true,
                        rp: 50, //results per page
						rpOptions: [50, 100, 150, 200], //allowed per-page values
                        showTableToggleBtn : false,
                        singleSelect : true,
                        width : 640,
                        height : 350

                    });

                </script>

        </div>
        </form>
</body>
</html>
[/#escape]
