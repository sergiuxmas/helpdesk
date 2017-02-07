<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<form:form action="${contextPath}/exchange/upload" commandName="formModel" modelAttribute="formModel" id="fileForm" method="post" enctype="multipart/form-data" >
    <div class="col-sm-12" id="selectFileBlock" >
        <span class="btn btn-default btn-file">
            <form:input type="hidden" path="id"  />
            <form:input type="file" path="fileupload" id="fileupload"  />
        </span>
        <span class="fileupload-preview"></span><span class="view_file"></span> 
        <button type="button" class="btn btn-primary" onclick="loadFile()">Încărcare</button> 
        <!--<button type="submit" class="btn btn-primary" >Submit</button> -->
        <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none" onclick="$('#fileupload').val('')">×</a>
    </div>
    <form:errors path="fileupload" cssClass="error" />
    <form:hidden path="idTema" id="idTema"></form:hidden>
</form:form>
<script type="text/javascript">
    function loadFile() {
        var request = new FormData();
        request.append('fileupload', $("#fileupload")[0].files[0]);
        request.append('idTema', $('#idTema').val());
        
        $.ajax({
            //data: $("#fileForm").serialize(),
            data:request,
            url: "${contextPath}/exchange/upload",
            //contentType: 'multipart/form-data',
            contentType:false,
            //mimeType    : 'multipart/form-data',
            enctype: 'multipart/form-data',
            type: 'post',
            processData: false,
            async: false,
            cache: false,
            success: function(data) {
                $("#uploadFileForm").html(data);
                //se transmit valorile din forma in grid
                //$("#gs_id_tema").val($('#idTema').val());
                //$("#fisiere")[0].triggerToolbar();
                $('#fisiere').trigger( 'reloadGrid' );
                /*
                 $("#fileUpload").val('');
                 $("#fileUpload.errors").val('');
                 */
                //else alert("Fisierul n-a fost incarcat");
                //var iframe = $(window.top.document).find("#iframe");
                //iframe.height( iframe[0].contentDocument.body.scrollHeight+'px' );
                
            },
            error: function() {
                alert("Eroare la procesare");
            }
        });
    }
    
</script>