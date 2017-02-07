<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="panel panel-primary">
    <div class="panel-body panel-heading">
        <h4 class="panel-title"><span data-parent="#accordion" data-toggle="collapse">Ghiduri</span></h4>
    </div>
    <div class="panel-footer">
        <ul>
            <li><a href="http://fileserver.cnam.md/ghiduri/UserGuideHelpDesk.pdf" target="_blank">Ghidul privind utilizarea SI Help Desk</a></li>
            <li><a href="http://fileserver.cnam.md/ghiduri/Ghid-email-signature.pdf" target="_blank">Ghidul privind setarea semnăturii poştei electronice corporative</a></li>
        </ul>
    </div>
</div>