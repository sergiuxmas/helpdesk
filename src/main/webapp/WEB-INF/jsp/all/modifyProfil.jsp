<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="userSession" value='${pageContext.request.getSession().getAttribute("user")}'/>

<style>
    .delimiter{
        border-top: 1px solid #999;
        border-bottom: 1px solid #999;
        padding-bottom: 4px;
    }
</style>

<div class="panel panel-primary">
    <div class="panel-body panel-heading">
        <h4 class="panel-title">
            <a href="#collapseOne" data-parent="#accordion" data-toggle="collapse">Redactarea profilului</a>
        </h4>
    </div>
    <c:if test="${!tiket.user_executor.isEmpty()}"><div class="alert alert-warning" style="margin-bottom:0px">${message}</div></c:if>
    <div class="panel-footer">
        <form class="form-horizontal" role="form" method="post">
            <div class="form-group">
              <label class="control-label col-sm-1">Nume</label>
              <div class="col-sm-4">
                  <div class="form-control">${userSession.getNume()}</div>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-1">Atasat</label>
              <div class="col-sm-4">
                  <div class="form-control">${userSession.getAtasat()}</div>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label col-sm-1">Diviziunea</label>
              <div class="col-sm-4">
                      
                  <select name="diviziune" class="form-control">              
                      <option value="">--selectare--</option>
                      ${divizTreeListRendered}
                  </select>
                      
              </div>
            </div>
            <div class="form-group">
              <div class="col-sm-offset-1 col-sm-10">
                <button type="submit" class="btn btn-default">Salvare</button>
              </div>
            </div>
          </form>
    </div>
</div>