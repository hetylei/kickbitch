[#escape x as x?html]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>查看图片</title>
    [#include "/include/main.ftl"]
    [#include "/include/popdialog.ftl"]
 	<link type="text/css" href="/css/jquery.iviewer.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery.mousewheel.min.js"></script>
    <script type="text/javascript" src="/js/jquery.iviewer.js"></script>
    <script>
    	$(document).ready(function(){

                  var iv2 = $("#viewer").iviewer(
                  {
                      src: "/file/download/${obj.sn}"
                  });

            });
    </script>
    <style>
            .viewer
            {
                width: 1000px;
                height: 620px;
                border: 1px solid black;
                position: relative;
            }
            
            .wrapper
            {
                overflow: hidden;
            }
        </style>
</head>
<body>
  <div class="wrapper">
        <div id="viewer" class="viewer"></div>
        <br />
    </div>
</body>
</html>
[/#escape]
