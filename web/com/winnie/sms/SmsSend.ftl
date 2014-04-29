[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>短信业务管理平台</title>
    [#include "/include/jsvalidation.ftl"]
    [#include "/include/main.ftl"]
    [#include "/include/datepicker.ftl"]
    [#include "/include/popdialog.ftl"]
    [#include "/include/swfupload.ftl"]
    <style>
        .popover-inner {
            width: 437px;
        }
    </style>
    <script>
	var smsdian = ${obj.smsdian!0};
    var gfw = "${obj.gfw?js_string}";
	var wordcount = ${obj.wordcount};
        function checkgfw() {
            var gfwlist = gfw.split(",");
            for (i = 0; i < gfwlist.length; i++) {
                if ($("#msg").val().indexOf(gfwlist[i].replace(/(^\s*)|(\s*$)/g, "")) > -1) {
                    alert('发送内容中包含非法内容[' + gfwlist[i] + '],请重新编辑');
                    return false;
                }
            }
            return true;
        }
		
		function checksmsdian() {
			$(document).ajaxStop($.unblockUI);
			$.blockUI({ message: '<h3> 正在提交...</h3>' });
			$.post('/sms/postCheck', {"msg":$('#msg').val(), "mobile":$('#mobile').val()},
                    function (data){
                        if (data.r == "ok" ) {
                        	mainForm.submit();    
                        } else {
							alert('您发送的短信需要扣点[' +data.sendCount + '],你的余额不足['+data.smsdian+'],请联系我们充值。'); 
                        }
                    }
                    ,'json');
			
		}

        function calcMsg() {
            var smsCount = 1;
            if ($('#msg').val().length > wordcount) {
                smsCount = ($('#msg').val().length + wordcount - 5) / (wordcount - 4) | 0;
            }
            $('#msginfo').html('已经输入' + $('#msg').val().length + '字，共分' + smsCount + '条发送。');
			$('#msgshow').html($('#msg').val());
			return smsCount; 
        }

        function calcMobile() {
			var mc = $('#mobile').val().length > 0 ? $('#mobile').val().split('\n').length : 0;
            $('#mobileInfo').html('当前号码数' + (mc) + '个');
			return mc;
        }

        function getContactMobile() {
            popDialog("contactMobile", "从通导录导入",
                    "/sms/contacts/selectKind",
                    function (data) {
                        if (data != null) {
                            $("#mobile").val($("#mobile").val() + ($("#mobile").val() == "" ? "" : "\n") + data.toString().replace(/,/g, "\n"));
                            calcMobile();
                        }
                    }, 550, 670);
        }


        function addOne() {
			var regph = /^([1](3|5|8)[0-9]{9})$|^([0][1-2][0-9][0-9]{8})$|^([0][3-9][0-9]{2}([0-9]{8}|[0-9]{7}))$/;
			if (!regph.exec($('#onemobile').val())) {
				alert('无效号码，请检查。');
                $('#onemobile').focus();
				return;
			}
			
            if ($('#mobile').val() != '')  $('#mobile').val($('#mobile').val() + '\n');
            $('#mobile').val($('#mobile').val() + $('#onemobile').val());
            $('#onemobile').val('');
            $('#onemobile').focus();
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
            mainForm.action="/sms/refreshPhone";
            mainForm.submit();
        }


        var swfu;
        function initFileUpload(){
            var settings = {
                flash_url : "/js/swfupload/swfupload.swf",
                flash9_url : "/js/swfupload/swfupload_fp9.swf",
                upload_url: "/sms/uploadPhone",
                post_params: {"jsessionid" : "[#noescape]${Session["id"]}[/#noescape]"},
                file_post_name : "filedata",
                file_size_limit : "10 MB",
                file_types : "*.txt;*.xls",
                file_types_description : "txt xls Files",
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
                button_image_url: "/images/uploadTel.gif",
                button_width: 97,
                button_height: 25,
                button_placeholder_id: "spanButtonPlaceHolder",

                // The event handler functions are defined in handlers.js
                //swfupload_preload_handler : preLoad,
                //swfupload_load_failed_handler : loadFailed,
                //file_queued_handler : fileQueued,
                //file_queue_error_handler : fileQueueError,
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

        function selectExcelCol(data) {
            mainForm.action="/sms/selectExcelCol/"+data;
            mainForm.submit();
        }

        $(function () {
            $("#filedemo").popover({title:"文件示例", content:"<img src='/images/demo.png' width='437' height='351'/>"});
            calcMobile();
            calcMsg();
            initFileUpload();
            [#if obj.errorExcelFile?exists]
                alert('excel文件格式错误或为空文件');
            [/#if]

            [#if obj.selectCol?exists]

                popDialog("selectCol", "选择号码列",
                        "/sms/showSelectExcelCol",
                        function (data) {
                            if (data != null) {
                                selectExcelCol(data);
                            }
                        }, 400, 600);
            [/#if]

        });
    </script>
</head>
<body>
    [#include '/top.ftl']


<form name="mainForm" id="mainForm" action="/sms/postsend" method="post" class="well">

    <table border="0" align="left" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
            <td height="36">&nbsp;</td>
            <td colspan="3">
            	
              <div align="left"><img src="/images/main_80.gif" width="61" height="14" alt=""></div>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td height="41">&nbsp;</td>
            <td align="left" valign="middle"><input name="onemobile" type="text" id="onemobile" size="20" maxlength="12" onKeyUp="if(event.keyCode==13) {addOne();}"></td>
            <td align="left" valign="middle"><input name="input2" type="image" src="/images/main_85.gif" onClick="addOne();return false;" ></td>
            <td align="left" valign="middle"><input name="input" type="image" src="/images/main_87.gif" onClick="$('#mobile').val('');calcMobile();return false;"></td>
          <td rowspan="6" valign="top">
            <table width="163" height="310" border="0" cellpadding="0" cellspacing="0">
                  <tbody>
                    <tr>
                        <td background="/images/main_89.gif">
                            <table width="100%" height="211" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                <tr>
                                    <td width="17%" height="39">&nbsp;</td>
                                    <td width="66%">&nbsp;</td>
                                    <td width="17%">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td height="159">&nbsp;</td>
                                    <td valign="top">
                                        <div align="left" class="style2" id="msgshow" style="width:120px;height:160px; word-wrap: break-word; word-break: break-all; overflow-y:auto"></div>
                                    </td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td height="13">&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                  </tbody>
              </table>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td colspan="3" align="left"><textarea name="mobile" id="mobile"
                                                   class="input-xlarge required"
                                                   rows="10" onKeyUp="calcMobile()"
                                                   title="请输入接收号码">${obj.dd.mobile!''}</textarea></td>
        </tr>
        <tr>
            <td height="25">&nbsp;</td>
            <td colspan="3" class="style2"><span class="label" id="mobileInfo">当前号码数0个</span></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td colspan="3">
                      <div>
                      <input type="image" id="spanButtonPlaceHolder" src="/images/main_115.gif">
                    <input type="image" name="button" id="button" src="/images/main_115.gif"
                           onClick="getContactMobile();return false;" style="margin: 0px;">
                      </div>
            </td>
        </tr>
        <tr>
            <td height="37">&nbsp;</td>
            <td>
                <div align="left"><img src="/images/main_120.gif" width="61" height="14" alt=""></div>
            </td>
            <td colspan="2" class="style2">${obj.wordcount}字1条</td>
        </tr>
        <tr>
            <td></td>
            <td colspan="3">
                <div align="left">
                    <textarea name="msg" id="msg"
                              class="input-xlarge required"
                              rows="6" onKeyUp="calcMsg()" title="请输入信息内容">${obj.dd.msg!''}</textarea>
                </div>
            </td>
        </tr>
        <tr>
            <td height="31">&nbsp;</td>
            <td colspan="3" align="left" class="style2"><span class="label"
                                                                              id="msginfo">已经输入0字，还可以输入${obj.wordcount}字</span>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td height="31">&nbsp;</td>
            <td colspan="3"><input type="checkbox" name="cbst" id="cbst"
                                   onClick="if ($('#cbst').attr('checked')) $('#send_time').removeAttr('disabled'); else{$('#send_time').attr('disabled','');$('#send_time').val('');} ">
                定时发送
                <input name="send_time" type="text" id="send_time" size="20" class="Wdate"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                       value="${(obj.dd.send_time?string('yyyy-MM-dd hh:mm:ss'))?default('')}" disabled></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td height="36">&nbsp;</td>
            <td colspan="3">
                <div align="left">
                	<input type="image" name="btnsend" id="btnsend" src="/images/main_125.gif"
                           onclick="valForm();return false;">
                </div>
            </td>
            <td>&nbsp;</td>
        </tr>
        </tbody>
    </table>


</form>
<script>
    function valForm() {
        var va = new Validation('mainForm', {onSubmit:false});
        if (checkgfw() && va.validate()) checksmsdian();
    }
</script>

    [#include '/bottom.ftl']
</body>
</html>
[/#escape]