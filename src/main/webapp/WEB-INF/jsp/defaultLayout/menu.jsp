<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="md.cnam.helpdesk.model.MenuGuest"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="userSession" value='${pageContext.request.getSession().getAttribute("user")}'/>
<c:set var="menuSession" value='${pageContext.request.getSession().getAttribute("menu")}'/>

<jsp:useBean id="menuGuest" class="md.cnam.helpdesk.model.MenuGuest" />

<%
   ApplicationContext ctx = RequestContextUtils.getWebApplicationContext(request);
   MenuGuest bean = (MenuGuest) ctx.getBean("menuGuest");
%>
<c:if test="${userSession==null}"><%=bean.getHtmlMenu()%></c:if>
${menuSession.getHtmlMenu()}
<c:if test="${userSession!=null}">        
    <div class="container-menu">
        <div class="col-sm-12 col-md-12">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseProfil">Profil</a>
                        </h4>
                    </div>
                    <div id="collapseProfil" class="panel-collapse collapse in">
                        <table class="table">
                            <tr>
                                <td>
                                    Nume: ${userSession.nume}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Atasat: ${userSession.atasat}
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <c:if test="${userSession.getDiviziuneNume()==null}"><div style="color: #942a25">Diviziunea nu este definita</div></c:if>
                                    <c:if test="${userSession.getDiviziuneNume()!=null}">Diviziunea: ${userSession.getDiviziuneNume()}</c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="${contextPath}/user/modifyProfil">Redactarea profilului</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="${contextPath}/user/changePassword">Modificarea parolei</a>
                                </td>
                            </tr>
                            <tr>
                                <td align="left">
                                    <a href="${contextPath}/user/logout"><b>Iesire</b></a>
                                </td>
                            </tr>
                        </table>
                    </div>    
                </div>
            </div></div></div> 
            <br />&nbsp;<br />                   
        </c:if>
<!--
<div class="container-menu">
    <div class="col-sm-12 col-md-12">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Administrare</a>
                    </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in">

                    <table class="table">
                        <tr>
                            <td>
                                <a href="${contextPath}/meniu/index">Meniu</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="${contextPath}/meniu/atribRol">Atribuirea meniului la rol</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="${contextPath}/categorii">Categorii</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="${contextPath}/diviziuni">Diviziuni</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="${contextPath}/domenii">Domenii</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="${contextPath}/utilizatori">Utilizatori</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="${contextPath}/regAbsente">Registrul absentelor spec. DSIeT</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="${contextPath}/tiket/admin/gridlist">Lista solicitarilor</a>
                            </td>
                        </tr>
                    </table>

                </div>
            </div>
                            
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">User</a>
                    </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse in">

                    <table class="table">
                        <tr>
                            <td>
                                <a href="${contextPath}/tiket/new">Crearea solicitarii</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="${contextPath}/tiket/user/gridlist">Lista solicitarilor</a>
                            </td>
                        </tr>
                    </table>

                </div>
            </div>
                            
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTree">User DSI</a>
                    </h4>
                </div>
                <div id="collapseTree" class="panel-collapse collapse in">

                    <table class="table">
                        <tr>
                            <td>
                                <a href="${contextPath}/tiket/userDSI/gridlist">Lista solicitarilor</a>
                            </td>
                        </tr>
                    </table>

                </div>
            </div>                

            
        </div>
    </div>

</div>   
-->