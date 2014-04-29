<style type="text/css">
    body {
        background-color: #FFEFDE;
    }
</style>
<div align="center">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/main_02.gif">
                <table width="1024" height="96" border="0" align="center" cellpadding="0" cellspacing="0"
                       background="/images/main_02.gif">
                    <tr>
                        <td width="364">
                            <div align="center"><img src="/images/main_10.gif" width="299" height="75"></div>
                        </td>
                        <td width="378">
                            <TABLE border=0 cellSpacing=0 cellPadding=0 width="298">
                                <TBODY>
                                <TR>
                                    <TD colSpan=3>&nbsp;</TD>
                                </TR>
                                <TR>
                                    <TD width=11><IMG src="/images/main_15.gif" width=11 height=51></TD>
                                    <TD background=/images/main_17.gif width=278>
                                        <TABLE border=0 cellSpacing=3 cellPadding=0 width=273>
                                            <TBODY>
                                            <TR>
                                                <TD width=68><IMG alt="" src="/images/main_27.gif" width=54 height=12>
                                                </TD>
                                                <TD width=196>
                                                    <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%"
                                                           background=/images/main_23.gif>
                                                        <TBODY>
                                                        <TR>
                                                            <TD width=10>
                                                                <DIV align=left><IMG alt="" src="/images/main_22.gif"
                                                                                     width=7 height=18></DIV>
                                                            </TD>
                                                            <TD class=style2 vAlign=center width=168>
                                                                <DIV align=left>青岛无限媒体策划有限公司</DIV>
                                                            </TD>
                                                            <TD width=11>
                                                                <DIV align=right><IMG alt="" src="/images/main_25.gif"
                                                                                      width=5 height=18></DIV>
                                                            </TD>
                                                        </TR>
                                                        </TBODY>
                                                    </TABLE>
                                                </TD>
                                            </TR>
                                            <TR>
                                                <TD><IMG alt="" src="/images/main_35.gif" width=54 height=12></TD>
                                                <TD>
                                                    <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%"
                                                           background=/images/main_23.gif>
                                                        <TBODY>
                                                        <TR>
                                                            <TD width=10>
                                                                <DIV align=left><IMG alt="" src="/images/main_32.gif"
                                                                                     width=7 height=18></DIV>
                                                            </TD>
                                                            <TD class=style1 width=168>
                                                                <DIV align=left>0532-88006181</DIV>
                                                            </TD>
                                                            <TD width=11>
                                                                <DIV align=right><IMG alt="" src=/images/main_34.gif width=5 height=18></DIV>
                                                            </TD>
                                                        </TR>
                                                        </TBODY>
                                                    </TABLE>
                                                </TD>
                                            </TR>
                                            </TBODY>
                                        </TABLE>
                                    </TD>
                                    <TD width=9>
                                        <DIV align=right><IMG src="/images/main_19.gif" width=9 height=51></DIV>
                                    </TD>
                                </TR>
                                </TBODY>
                            </TABLE>
                        </td>
                        <td width="282" valign="top">
                            <TABLE border=0 cellSpacing=0 cellPadding=0 width=200 align=center>
                                <TBODY>
                                <TR>
                                    <TD background=/images/main_05.gif width=11>
                                        <DIV align=left><IMG alt="" src="/images/main_04.gif" width=10 height=29></DIV></TD>
                                    <TD background=/images/main_05.gif width=178>
                                        <DIV class="style2 style3" align=center><a href="/" class="white">首页</a>　
                                        [#if Session["menus"]?exists]<a href="/account" class="white">余额</a>[/#if]　
                                            <a href="javaScript:addfavorite()" class="white">收藏</a>　
                                        [#if Session["menus"]?exists]<a href="/logout" class="white">退出</a>[/#if]</DIV></TD>
                                    <TD background=/images/main_05.gif width=11>
                                        <DIV align=right><IMG alt="" src="/images/main_07.gif" width=10 height=29></DIV></TD></TR>
                                <TR>
                                    <TD height=57>&nbsp;</TD>
                                    <TD class=style4 vAlign=bottom>当前日期：<script language=Javascript>
                                        var now=new Date();
                                        document.write(now.getFullYear()+"年"+(now.getMonth()+1)+"月"+now.getDate()+"日");
                                    </script>
                                    </TD>
                                    <TD>&nbsp;</TD></TR></TBODY></TABLE>
                            <DIV align=center></DIV>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

    <table width="100%" height="11" border="0" cellpadding="0" cellspacing="0" background="/images/main_44.gif">
        <tr>
            <td height="11"></td>
        </tr>
    </table>
[#if Session["menus"]?exists]
    <table width="1024" height="11" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td width="37" height="11" bgcolor="FFEFD8">
                <div align="center"></div>
            </td>
            <td width="6"><img src="/images/main_48.gif" width="6" height="33" alt=""></td>
            <td width="154" background="/images/main_49.gif">
                <div align="center"><img src="/images/main_58.gif" width="86" height="24" alt=""></div>
            </td>
            <td width="28">
                <div align="left"><img src="/images/main_51.gif" width="28" height="33" alt=""></div>
            </td>
            <td width="744"  align="absmiddle" background="/images/main_52.gif">
                <marquee behavior="scroll" scrollamount="3" direction="left" width="720">
                        ${Session["pubmsg"!""]}
                </marquee></td>
          <td width="16"><img src="/images/main_55.gif" width="16" height="33" alt=""></td>
            <td width="37" bgcolor="FFEFD8"></td>
        </tr>
    </table>

    <table width="1024" height="11" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFEFDE">
        <tr>
            <td height="11" bgcolor="FFEFD8">
                <div align="center"></div>
                <div align="center"></div>
            </td>
        </tr>
    </table>
<table width="1024" height="281" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFEFDE">
<tr>
    <td width="37" height="281" bgcolor="FFEFD8">
        <div align="center"></div>
    </td>
<td valign="top">
<div align="center">
<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#ABABAB">
<tr>
<td height="577" valign="top" bgcolor="#FFFFFF">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td width="191" height="596" valign="top" background="/images/main_65.gif">
        <table width="96%" border="0" align="left" cellpadding="0" cellspacing="0">
            <tr>
                <td height="86">
                    <div align="center"><img src="/images/main_75.gif" width="124"
                                             height="27" alt=""></div>
                </td>
            </tr>
            [#list Session["menus"].menus?default([]) as menu]
                [#list menu.groups?default([]) as subgroup]
                    <tr>
                        <td height="31" background="/images/main1_83.gif" valign="middle" align="center">
                            <img src="/images/${subgroup.id}.gif" alt="">
                        </td>
                    </tr>
                    [#list subgroup.menus?default([]) as submenu]
                        <tr>
                            <td height="31" background="/images/main1_86.gif" valign="middle" align="center">

                                    <a href="${submenu.link!"#"}"><img
                                            src="/images/${submenu.id}.gif"  alt=""></a>

                            </td>
                        </tr>
                    [/#list]
                [/#list]
            [/#list]
            <tr>
                <td height="31" valign="top">
                    <div align="center"><img src="/images/main1_92.gif" width="183"
                                             height="2"></div>
                </td>
            </tr>

            <td height="31">
                <div align="center"></div>
            </td>
            </tr>
        </table>
    </td>
<td valign="top"><br>
    <table width="700" border="0" align="center" cellpadding="0" cellspacing="0"
           bgcolor="F5F5F5">
        <tr>
            <td width="11"><img src="/images/main_69.gif" width="11" height="10"
                                alt=""></td>
            <td width="658" valign="top">
                <table width="678" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="100%" height="1" bgcolor="CFCFCF"></td>
                    </tr>
                </table>
            </td>
            <td width="11"><img src="/images/main_72.gif" width="11" height="10"
                                alt=""></td>
        </tr>
    </table>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0"
       bgcolor="F5F5F5">
<tr>
    <td width="1" height="209" bgcolor="CFCFCF"></td>
<td valign="top" align="left">

[/#if]
