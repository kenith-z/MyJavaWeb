<%@ page contentType="text/html; charset=UTF-8"%>
<c:set var="sectionSize"  value="${SECTION_SIZE}"/>
<c:set var="sectionFirst" value="${page-(page%sectionSize==0?sectionSize:page%sectionSize)+1}"/>
<c:set var="sectionLast"  value="${page-(page%sectionSize==0?sectionSize:page%sectionSize)+sectionSize}"/>
<c:if test="${pageCount>1}">
<table border=0 cellspacing=0 cellpadding=0 width=100% align=center>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td align="center">
        <c:if test="${page!=1}"><a href="javascript:goPage(theform, '1')">首页</a></c:if>
        <c:if test="${page==1}">首页</c:if>
        <c:if test="${sectionSize<pageCount && sectionFirst>1}">
          <a href="javascript:goPage(theform, '${sectionFirst-1}')">&lt;&lt;</a>
        </c:if>
        <c:if test="${pageCount>1}">
          <c:forEach var="i" begin="${sectionFirst}" end="${sectionLast<pageCount?sectionLast:pageCount}">
            <c:if test="${page==i}">${i}</c:if>
            <c:if test="${page!=i}"><a href="javascript:goPage(theform, '${i}')">${i}</a></c:if>
          </c:forEach>
        </c:if>
        <c:if test="${sectionSize<pageCount && pageCount>sectionLast}">
          <a href="javascript:goPage(theform, '${sectionLast+1}')">&gt;&gt;</a>
        </c:if>
        <c:if test="${page!=pageCount}"><a href="javascript:goPage(theform, '${pageCount}')">尾页</a></c:if>
        <c:if test="${page==pageCount}">尾页</c:if>
        &nbsp;&nbsp;total ${pageCount} pages, ${rowsCount} records
    </td>
  </tr>
</table>
</c:if>