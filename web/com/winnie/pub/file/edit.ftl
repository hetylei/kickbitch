[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>上传文件编辑</title>
    [#include "/include/jsvalidation.ftl"]
    [#include "/include/main.ftl"]
    [#include "/include/popdialog.ftl"]
    <script>
        $(function() {
            $("#UploadFileEdit").tabs();
        });

    </script>
</head>
<body>
    [#if obj.saved?default('') != '']
        <script>
			closeDialog([#noescape]${obj.result!''}[/#noescape]);
		</script>
    [#else]

                        <div id="UploadFile_base">
                            <form action="/file/save" method="post" enctype="multipart/form-data" name="mainForm">

                                <input TYPE="hidden" size="20" id="bizType" name="bizType" class="max-length-40 "
                                       value="${obj.bizType!''}">
                                <input TYPE="hidden" size="20" id="bizSn" name="bizSn" class="max-length-40 "
                                       value="${obj.bizSn!''}">
                                <input TYPE="hidden" size="20" id="bizSign" name="bizSign" class="max-length-40 "
                                       value="${obj.bizSign!''}">


                                <p>
                                    <input name="upfile" type="file"></p>
                                 <p>   <input name="upfile" type="file"></p>
                                 <p>   <input name="upfile" type="file"></p>
                                <p>    <input name="upfile" type="file"></p>
                                <p>    <input name="upfile" type="file"></p>
                                </p>

                                <p>
                                    <input type="submit" name="button" id="button" value="上传">
                                </p>


                            </form>
                        </div>

    [/#if]
</body>
</html>
[/#escape]
