<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <tiles:insertAttribute name="meta" />
    </head>
    <body>
        <div id="page-wrap">
            <div id="page">
                <div id="body">
                    <tiles:insertAttribute name="header" />
                    <div class="authorization"><a href="${contextPath}/index">Pagina de start</a></div>
                    <div id="content">
                        <div id="content-block">
                            <!-- content init-->
                            <tiles:insertAttribute name="body" />
                            <!-- content end -->                        	
                        </div>
                    </div>
                </div>
                <div id="footer">
                    <!-- footer init -->
                    <tiles:insertAttribute name="footer" />
                    <!-- footer end -->
                </div>
            </div>
        </div>
    </body>
</html>
