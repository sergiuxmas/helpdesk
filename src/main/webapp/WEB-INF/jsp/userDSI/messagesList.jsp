<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<style>
    .chat .jqte_toolbar.unselectable { display: none;}
    .jqte_editor, .jqte_source { min-height:28px; padding: 4px; }
    .chat .jqte { border: none}
    .jqte { border-color: #a8a8a8}
</style>
<c:forEach items="${messagesList}" var="mesaj">
    <li class="left clearfix">
        <div class="chat-body clearfix">
            <div class="header message">
                <small class="pull-right text-muted"><span class="glyphicon glyphicon-time"></span>${mesaj.data_expedierii}</small>
                <small class="pull-right text-muted space"><span class="glyphicon glyphicon-user"></span>${mesaj.user}</small>
            </div>
            <textarea class="form-control noedit" >${mesaj.message}</textarea>
        </div>
    </li>
</c:forEach>
<script language="javascript">
    $('.noedit').jqte();
    $( "#chat-container" ).scrollTop( $( "#chat-container" )[0].scrollHeight );
</script>

