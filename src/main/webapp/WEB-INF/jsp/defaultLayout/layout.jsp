<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="userSession" value='${pageContext.request.getSession().getAttribute("user")}'/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <tiles:insertAttribute name="meta" />
    </head>
    <body>
        <div id="page-wrap">
            <div id="page">
                <div id="body">
                    <tiles:insertAttribute name="header" />
                    <c:if test="${userSession==null}"><div class="authorization"><a href="${contextPath}/auth/index">Autorizare</a></div></c:if>
                    <c:if test="${userSession!=null}"><div class="authorization"><a href="${contextPath}/user/logout">Iesire</a></div></c:if>
                    <div id="content">
                        <div id="content-block">
                            <!-- content init-->
                            <table width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="meniu">
                                        <!-- meniu init -->
                                        <tiles:insertAttribute name="menu" />                    			
                                        <!-- meniu end -->
                                    </td>
                                    <td class="continut">
                                        <!-- continut init -->
                                        <tiles:insertAttribute name="body" />
                                        <!-- continut end -->
                                    </td>
                                </tr>
                            </table>
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
