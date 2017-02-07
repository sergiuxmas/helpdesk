<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
#newfile{
	display: none;
}
span.file{
	color: #3a87ad;
}
span.file:hover{
	text-decoration: underline;
	cursor: pointer;
}
</style>

<div class="panel panel-default">
    <div class="panel-body panel-heading">
        <h4 class="panel-title">
            <span data-parent="#accordion" data-toggle="collapse">Solicitare</span>
        </h4>
    </div>
    <div class="panel-footer">
        <form:form role="form" action="${contextPath}/tiket/new" commandName="formModel" method="POST" enctype="multipart/form-data">
            <table class="table table-hover">
                <tbody>
                    
                    <tr>
                        <td>Tip:</td>
                        <td>
                        <form:select path="tip" name="tip" >                              
                         <option value="">--selectare--</option>
                         <c:forEach items="${listaTipurilor}" var="tip">
                             <c:choose>
                                 <c:when test="${formModel.tip == tip.id}">
                                     <option value="${tip.id}" selected="selected">${tip.nume}</option>
                                 </c:when>
                                 <c:otherwise>
                                     <option value="${tip.id}" >${tip.nume}</option>
                                 </c:otherwise>
                             </c:choose>
                         </c:forEach>
                     </form:select>
                     <form:errors path="tip" cssClass="error" />
                     </td>
                     </tr>
                     
                    <tr>
                        <td>Prioritate:</td>
                        <td>
                   <form:select path="prioritate" name="prioritate" >                              
                    <option value="">--selectare--</option>
                    <c:forEach items="${listaPrioritatilor}" var="prioritate">
                        <c:choose>
                            <c:when test="${formModel.prioritate == prioritate.id}">
                                <option value="${prioritate.id}" selected="selected">${prioritate.nume}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${prioritate.id}" >${prioritate.nume}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </form:select>
                <form:errors path="prioritate" cssClass="error" />
                </td>
                </tr>
                <tr>
                    <td width="200">Domeniul:</td>
                    <td>
                        <select name="domeniu">
                            <option value="">--selectare--</option>
                            <c:forEach items="${listaDomeniilor}" var="domeniu">
                                <c:choose>
                                    <c:when test="${formModel.domeniu == domeniu.id}">
                                        <option value="${domeniu.id}" selected="selected">${domeniu.nume}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${domeniu.id}" >${domeniu.nume}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <form:errors path="domeniu" cssClass="error" />
                    </td>
                </tr>
                <tr>
                    <td>Descrierea problemei:</td>
                    <td>
                        <form:textarea path="descrierea" id="descrierea" class="form-control" rows="5" placeholder="descrierea" />
                        <form:errors path="descrierea" cssClass="error" />
                        <!-- atasare -->
                        <div style="margin-top: 5px;">
                            <span class="glyphicon glyphicon-paperclip"></span><span class="file" onclick="showSelectFiles()"> Adaugare fisier</span>
                        </div>
                        <div class="col-sm-12" id="selectFileBlock" style="display: none; ">
                                <span class="btn btn-default btn-file">
                                    <form:input type="file" path="fileUpload" id="fileUpload" accept="image/*,application/pdf,application/x-rar-compressed,application/x-rar,application/zip,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document" />
                                    <!--<input type="file" name="fileUpload" id="filesPost" accept="application/pdf,image/jpg,image/jpeg,application/x-rar-compressed,application/x-rar,application/zip" />-->
                                </span>
                                <span class="fileupload-preview"></span><span class="view_file"></span>
                                <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none" onclick="clearFile()">×</a> <div id="documenteAtasate"></div>
                        </div>
                        <form:errors path="fileUpload" cssClass="error" />            
                        <!-- end atasare -->
                    </td>
                </tr>
                </tbody>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Inregistrare" class="btn btn-success" />
                    </td>
                </tr>

            </table>
        </form:form>
    </div>
</div>

<script language="javascript">
    $('#descrierea').jqte(
        {format: false}
    );
    
    function showSelectFiles(){
        $("#selectFileBlock").css("display","inline");
    }
    
    function clearFile(){
        $("#fileUpload").val('');
        $("#selectFileBlock").css("display", "none");
        $("#fileUpload.errors").val('');
    }
</script>
