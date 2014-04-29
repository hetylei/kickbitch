[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "/include/main.ftl"]
    [#include "/include/zTree.ftl"]
    [#include "/include/popdialog.ftl"]
    [#include "/include/flexigrid.ftl"]
    [#include "/include/swfupload.ftl"]
    <style>
        .popover-inner {
            width: 437px;
        }
    </style>
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
            callback : {
                click: zTreeOnClick
            }
        };



        function zTreeOnClick(event, treeId, treeNode) {
            dirSn = treeNode.id;
			if (opMoveKind) {
				$(document).ajaxStop($.unblockUI);
				$.blockUI({ message: '<h3> 正在提交...</h3>' });
				var str = '';
				$("[name='flexigridCheck']:checked").each(function(){
	              str+=$(this).val()+"|";
	          	})
                $.post('/sms/contacts/moveKind' , {ids: str, newKind: dirSn},
                        function(data) {
                            if (data == 'ok') {
								$("#aExport").attr("href", "/sms/contactsExport/" + dirSn);
								$("#kindid").val(dirSn);
								$(".Contactlist").flexOptions({url : '/sms/Contacts/' + dirSn , page: 1});
								$(".Contactlist").flexReload();
								$("#btnGroup").val($("<div>").html("分组："+ treeNode.name+ "&nabla;").text());
								$("#treediv").hide();
								opMoveKind = false;
                            } else {
                                alert(data);
                            }
                        }
                        , "json");	
			} else {
				$("#aExport").attr("href", "/sms/contactsExport/" + dirSn);
				$("#kindid").val(dirSn);
				$(".Contactlist").flexOptions({url : '/sms/Contacts/' + dirSn , page: 1});
				$(".Contactlist").flexReload();
				$("#btnGroup").val($("<div>").html("分组："+ treeNode.name+ "&nabla;").text());
				$("#treediv").hide();
			}
        }

        function query() {
            if ($("#name").val() != '') {
                $(".Contactlist").flexOptions({url : '/sms/contacts/' + dirSn, query:$("#name").val() , page: 1});
                $(".Contactlist").flexReload();
            }

        }

        var zTree;
        $(document).ready(function() {
            zTree = $("#tree").zTree(setting);
            $("#filedemo").popover({title:"文件示例", content:"<img src='/images/demo2.jpg' width='384' height='251'/>"});
            initFileUpload();
            [#if obj.errorExcelFile?exists]
                alert('excel文件格式错误或为空文件');
            [/#if]
            [#if obj.selectCol?exists]
				dirSn = "${obj.dirSn?default('-1')}";
                popDialog("selectCol", "选择通讯录对应列",
                        "/sms/contacts/showSelectCol",
                        function (data) {
                            if (data != null) {
								$.blockUI({ message: '<h3> 正在导入...</h3>' });
                                mainForm.action="/sms/contactsImport/" + dirSn +"/"+data;
                                mainForm.submit();
                            }
                        }, 400, 800);
            [/#if]
        });


        function refreshDir () {
            var treeNode = zTree.getSelectedNode();
            if (!treeNode) {
                return;
            }
            zTree.reAsyncChildNodes(treeNode, "refresh")
        }


        function editDir1(sn) {
            if (dirSn == ''|| dirSn=='-999' || dirSn=='-1') {
                alert('请选择分组("全部"和"未分组"不能编辑)');
                return
            }
			$("#treediv").hide();
            popDialog("editDir", "编辑分组",
                    "/sms/Contacts/editKind/" + sn,
                    function(){
                        if (popDialogResult != null) {
                            zTree.getSelectedNode().name = popDialogResult;
                            zTree.updateNode(zTree.getSelectedNode());
                        }
                    }, 200, 200);
        }

        function newSubDir1(sn) {
            if (dirSn == '' || dirSn=='-999') {
                alert('请选择分组("未分组"下不能建子分组)');
                return
            }
			$("#treediv").hide();
            popDialog("newSubDir", "新建分组",
                    "/sms/Contacts/newKind/" + sn,
                    function (data) {
                        if (data != null) {
                            zTree.getSelectedNode().isParent = true;
                            refreshDir();
                        }
                    }, 200, 200);
        }

        function delDir1(sn) {
            if (dirSn == '' || dirSn=='-999' || dirSn=='-1') {
                alert('请选择分组("全部"和"未分组"不能删除)');
            } else {
                if (confirm('确定要删除本分组？')) {
                    $.post('/sms/Contacts/delKind/' + sn, null,
                            function(data){
                                if (data == 'ok') {
                                    dirSn = zTree.getSelectedNode().parentNode.id;
                                    zTree.selectNode(zTree.getSelectedNode().parentNode);
                                    refreshDir();
                                    rc();
                                } else {
                                    alert(data);
                                }
                            },
                            "json");
                }
            }
        }

        var rc = function refreshContact() {
            $(".Contactlist").flexOptions({url : '/sms/Contacts/' + dirSn , page: 1});
            $(".Contactlist").flexReload();
        };

        function editContact(sn) {
            popDialog("editContact", "编辑联系人",
                    "/sms/Contacts/editContact/" + sn,
                    rc, 500, 500);
        }

        function delContact(sn) {
            if (confirm('确定要删除本联系人？')) {
                $.post('/sms/contacts/delContact/' + sn, null,
                        function(data) {
                            if (data == 'ok') {
                                rc();
                            } else {
                                alert(data);
                            }
                        }
                        , "json");
            }
        }
		
		function delSelectContact() {
            if (($("[name='flexigridCheck']:checked").length > 0) && confirm('确定要删除?')) {
				$(document).ajaxStop($.unblockUI);
				$.blockUI({ message: '<h3> 正在提交...</h3>' });
				var str = '';
				$("[name='flexigridCheck']:checked").each(function(){
	              str+=$(this).val()+"|";
	          	})
                $.post('/sms/contacts/delSelectContact' , {ids: str},
                        function(data) {
                            if (data == 'ok') {
                                rc();
                            } else {
                                alert(data);
                            }
                        }
                        , "json");
            } else {
				alert('请选择要删除的联系人');
			}
        }

        function newContact1(sn) {
            if (dirSn == '') {
                alert('请选择分组 ');
                return
            }
            popDialog("newContact", "新建联系人",
                    "/sms/Contacts/newContact/" + sn,
                    rc, 500, 500);
        }

        function upfileCheck(){
            if ($('#fileField').val() == '') {
                alert('请选择要上传的文件(xls).');
                return false;
            }
            var treeNode = zTree.getSelectedNode();
            if (!treeNode) {
                alert('请选择分组');
                return;
            }
            if (confirm('将通讯录导入[' + treeNode.name +']分组？')) {
                mainForm.action='/sms/contactsImport';
                mainForm.submit();
            }
        }

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

        function fileDialogComplete(numFilesSelected, numFilesQueued) {
            try {

                /* I want auto start the upload and I can do that here */
                this.startUpload();
            } catch (ex)  {
                this.debug(ex);
            }
        }

        function uploadStart(){
            $.blockUI({ message: '<h3> 正在上传...</h3>' });
        }

        function uploadSuccess() {
            mainForm.action="/sms/contacts/selectCol/" + dirSn;
            mainForm.submit();
        }


        var swfu;
        function initFileUpload(){
            var settings = {
                flash_url : "/js/swfupload/swfupload.swf",
                flash9_url : "/js/swfupload/swfupload_fp9.swf",
                upload_url: "/sms/contacts/uploadContacts",
                post_params: {"jsessionid" : "[#noescape]${Session["id"]}[/#noescape]"},
                file_post_name : "filedata",
                file_size_limit : "10 MB",
                file_types : "*.xls",
                file_types_description : "xls Files",
                file_upload_limit : 100,
                file_queue_limit : 0,
                /*
                custom_settings : {
                    progressTarget : "fsUploadProgress",
                    cancelButtonId : "btnCancel"
                },
                */
                debug: false,

                // Button settings
                button_image_url: "/images/contactImport.gif",
                button_width: 97,
                button_height: 25,
                button_placeholder_id: "spanButtonPlaceHolder",

                // The event handler functions are defined in handlers.js
                //swfupload_preload_handler : preLoad,
                //swfupload_load_failed_handler : loadFailed,
                //file_queued_handler : fileQueued,
                //file_queue_error_handler : fileQueueError,
                mouse_click_handler : mouseClick,
                file_dialog_complete_handler : fileDialogComplete,
                upload_start_handler : uploadStart,
                //upload_progress_handler : uploadProgress,
                //upload_error_handler : uploadError,
                upload_success_handler : uploadSuccess
                //upload_complete_handler : uploadComplete,
                //queue_complete_handler : queueComplete	// Queue plugin event
            };

            swfu = new SWFUpload(settings);
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
		
		function startMoveKind() {
			if ($("[name='flexigridCheck']:checked").length > 0) {
				$('#treediv').show();
				opMoveKind = true;
            } else {
				alert('请选择要移动分类的联系人');
			}
		}
		
		function cancelTreeOP() {
			$('#treediv').hide();
			opMoveKind = false;
		}
		
		function delSelectContact() {
            if (($("[name='flexigridCheck']:checked").length > 0) && confirm('确定要删除?')) {
				$(document).ajaxStop($.unblockUI);
				$.blockUI({ message: '<h3> 正在提交...</h3>' });
				var str = '';
				$("[name='flexigridCheck']:checked").each(function(){
	              str+=$(this).val()+"|";
	          	})
                $.post('/sms/contacts/delSelectContact' , {ids: str},
                        function(data) {
                            if (data == 'ok') {
                                rc();
                            } else {
                                alert(data);
                            }
                        }
                        , "json");
            } else {
				alert('请选择要删除的联系人');
			}
        }

    </script>
</head>
<body>
    [#include '/top.ftl']

        <form id="mainForm" name="mainForm" method="post" action="" class="well">
            <h2>通讯录   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="aExport" href="/sms/contactsExport/-1"><img  src="/images/contactExport.gif"></a><span id="spanButtonPlaceHolder"></span></h2>
            [#if obj.importCount?exists]
                [#if obj.importCount > 0]
                    <span class="label label-success">成功导入${obj.importCount}个联系人</span>
                [#else]
                    <span class="label label-warning">无记录导入：文件格式错误或文件为空</span>
                [/#if]
            [/#if]
            
<div style="width:600px;height:600px;float:left; position:relative;">
        		
		  <table>
            <tr>
              <td>
                <input type="hidden" name="kindid" id="kindid" value=""/>
              </td>
              <td>
                <input type="button" name="btn" id="btnGroup" value="分组：全部 &nabla;" onClick="$('#treediv').show();"/>|
                <input type="button" name="btn" id="btn" value="新建联系人" onClick="newContact1(dirSn);"/>
              </td>
              <td>
                <INPUT NAME="name" TYPE="text" ID="name" maxlength="20" size="6">
              </td>
              <td>
          <input type="button" name="btn4" id="btn4" value="查找" onClick="query();"/>|
       		  </td>
              <td>
                <input type="button" name="btn2" id="btn2" value="批量删除" onClick="delSelectContact();"/>
              </td>
              <td>
                <input type="button" name="btn3" id="btn3" value="修改分类" onClick="startMoveKind();"/>
              </td>
   		    </tr>
                <tr>
                  <td colspan="6"><div id="treediv" style="width:330px;height:350px; position:absolute;display:none; z-index:99999999; background-color:#FFFFFF; border-style:outset">
                    <ul id="tree" class="tree" style="width:330px; height:280px; overflow:auto;"></ul>
                    
                    <input type="button" name="button" id="button" value="新建子分组" onClick="newSubDir1(dirSn);"/>
                    <input type="button" name="button2" id="button2" value="修改分组" onclick="editDir1(dirSn);"/>
                    <input type="button" name="button3" id="button3" value="删除分组" onclick="delDir1(dirSn);"/>|
                    <input type="button" name="button3" id="button3" value="关闭" onclick="cancelTreeOP();"/>
                </div>[<a href="#" onClick="selectAll();">全选</a>][<a href="#" onClick="selectOther();">反选</a>][<a href="#" onClick="cancelSelect();">取消选择</a>]</td>
                </tr>
          </table>
          
<table class="Contactlist" style="display: none"></table>
                <script>

                    $(".Contactlist").flexigrid({
                        url : '/sms/Contacts/-1',
                        dataType : 'json',
                        colModel : [
							{display : 'ck', name : 'id', width : 25, sortable : true, align : 'left', checkbox:true},
							{display : 'id', name : 'id', width : 25, sortable : true, align : 'left', hide:true},
                            {display : '操作', name : 'op', width : 30, sortable : true, align : 'left'},
                      
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
                        height : 350,
                        onRowDblclick : function(rowData) {
                            editContact($(rowData).data("id").toString());
                        }

                    });

                </script>

        </div>
        </form>


    [#include '/bottom.ftl']
</body>
</html>
[/#escape]