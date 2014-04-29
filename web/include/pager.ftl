[#macro showPager pager, url]
[#if pager.linesPerPage*pager.page > pager.recorderCount]
    [#assign endcount =  pager.recorderCount ]
[#else]
    [#assign endcount =  pager.linesPerPage*pager.page]
[/#if]
共${pager.recorderCount}条，第${pager.linesPerPage*(pager.page-1)+1}-${endcount}条，${pager.page}/${pager.pageCount}页，分页:
[#if pager.page > 4 ]
    <a href="${url}?pager.page=1&pager.linesPerPage=${pager.linesPerPage}&${pager.query!""}&orderBy=${pager.orderBy!""}">[1]</a>...
[/#if]
[#list pager.page-3..pager.page+3 as i]
    [#if i > 0 ]
        [#if i > pager.pageCount][#break][/#if]
        [#if pager.page = i]
            [${i}]
        [#else]
            <a href="${url}?pager.page=${i}&pager.linesPerPage=${pager.linesPerPage}&${pager.query!""}&orderBy=${pager.orderBy!""}">[${i}]</a>
        [/#if]
    [/#if]
[/#list]
[#if pager.pageCount-pager.page > 3]
    ...<a href="${url}?pager.page=${pager.pageCount}&pager.linesPerPage=${pager.linesPerPage}&${pager.query!""}&orderBy=${pager.orderBy!""}">[${pager.pageCount}]</a>
[/#if]
[/#macro]