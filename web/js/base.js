// JavaScript Document
/*
 需要引用jquery ui相关js、css
 edt 输入框
 format ‘yy-MM-dd'
 */
function popCalendar(edt, format) {
    if (format == undefined) format = "yy-mm-dd";
    $(edt).datepicker({ monthNames:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'] ,
        monthNamesShort:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'] ,
        dayNames:['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'] ,
        dayNamesMin:['日', '一', '二', '三', '四', '五', '六'] ,
        dayNamesShort:['日', '一', '二', '三', '四', '五', '六'],
        dateFormat: format});
    $(edt).datepicker("show")
}

/*
 给form 添加 onSubmit="formSubmit(this)" 在按钮提交时禁用所有提交按钮
 */
function formSubmit(theform) {
    if (document.all || document.getElementById) {
        for (i = 0; i < theform.length; i++) {
            var tempobj = theform.elements;
            if (tempobj.type != undefined && tempobj.type.toLowerCase() == "submit")
                tempobj.disabled = true
        }
    }
}

/**
 * 上传附件 需要引用popDialog
 * @param spanid  显示控件的id 用于回填上传文件链接<span id="spanid"></span>
 * @param bizType  业务类型
 * @param bizSn    业务sn
 * @param bizSign  业务标识
 */
function uploadFile(spanid, bizType, bizSn, bizSign) {
    popDialog("upload", "上传文件",
                "/file/upload/"+bizType+"/"+bizSn+"/"+bizSign,
                function(data) {
                    if (data != null) {
                        for (i=0;i<data.length;i++) {
                            $('#'+spanid).html($('#'+spanid).html() + getFileLink(data[i].sn));
                        }
                    }
                    },400,400);
}

function delFile(sn) {
    $.get('/file/del/' + sn, null,
            function(data){
                if (data=='ok') {
                    $('#file_'+sn).remove();
                    $('#filedel_'+sn).remove();
                } else {
                    alert('删除失败.');
                }
            },"text");
}

function getFileLink(sn) {
    return ' <a href="javaScript:popDialog(\'photoview\', \'查看附件\', '+
           '\'/file/photoview/'+ sn+'\', null, 650,1050);" id="file_'+sn+'">附件</a>'+
            getFileDelLink(sn);
}

function getFileDelLink(sn) {
   return '<a href="javaScript:if (confirm(\'确定删除?\')) delFile(\''+sn+'\');" id="filedel_'+sn+'">[删]</a>';
}

/**
* 时间对象的格式化;
*/
Date.prototype.format = function(format){
 /*
  * eg:format="YYYY-MM-dd hh:mm:ss";
  */
 var o = {
  "M+" :  this.getMonth()+1,  //month
  "d+" :  this.getDate(),     //day
  "h+" :  this.getHours(),    //hour
      "m+" :  this.getMinutes(),  //minute
      "s+" :  this.getSeconds(), //second
      "q+" :  Math.floor((this.getMonth()+3)/3),  //quarter
      "S"  :  this.getMilliseconds() //millisecond
   }

   if(/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
   }

   for(var k in o) {
    if(new RegExp("("+ k +")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    }
   }
 return format;
}


function addfavorite()
{
    var surl = 'http://'+window.location.host;

    if (document.all)
    {
        window.external.addFavorite(surl,'短信发送平台');
    }
    else if (window.sidebar)
    {
        window.sidebar.addPanel('短信发送平台', surl, '');
    }
}


