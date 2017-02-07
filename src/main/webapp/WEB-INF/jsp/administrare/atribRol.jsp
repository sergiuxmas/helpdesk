<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="panel panel-default">
    <div class="panel-body panel-heading">
        <h4 class="panel-title">
            <a href="#collapseOne" data-parent="#accordion" data-toggle="collapse">Atribuirea meniului la rol</a>
        </h4>
    </div>
    <div class="panel-footer">
        <div class="result-content" id="resultcontent" >
            <div id="loading-image"></div>
            <div id="resultcontentAjax" ></div> 		
        </div>
    </div>
</div>
<div id="dialog" title="Rezultatul interpelarii"></div>

<script lang="javascript">
    function loadAjaxTable(action, data, errorMessage) {
        if (action == null)
            action = 'setModule';
        if (data == null)
            data = '';
        if (errorMessage == null)
            errorMessage = '';
        //alert (action);

        var sendData = {'action': action, 'data': data};
        request = $.ajax({
            url: "${contextPath}/meniu/atribRolAjax",
            cache: true,
            type: "POST",
            data: sendData,
            dataType: "html",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            statusCode: {
                400: function() {
                    $("#dialog").append("Server a înţeles cererea, dar conţinutul cererii a fost invalid");
                    iniDialog();
                },
                401: function() {
                    $("#dialog").append("Acces neautorizat");
                    iniDialog();
                },
                403: function() {
                    $("#dialog").append("Sursa nu poate fi accesată");
                    iniDialog();
                },
                404: function() {
                    $("#dialog").append("Pagina nu există");
                    iniDialog();
                },
                500: function() {
                    $("#dialog").append("Eroare generată de server");
                    $("#dialog").append("<br>" + errorMessage + "</br>");
                    iniDialog();
                },
                503: function() {
                    $("#dialog").append("Serviciul, la moment, nu este disponibil");
                    iniDialog();
                },
            },
            beforeSend: function() {
                //$("#resultcontentAjax").html("");
                $("#loading-image").show();
            },
            error: function(request, textStatus, errorThrown) {
                if (request.getResponseHeader('ErrorMessage') != null)
                    errorMessage = '<b>' + request.getResponseHeader('ErrorMessage') + '</b>';
            }
        });

        request.done(function(msg) {
            $("#loading-image").hide();
            $("#dialog").html("");
            $("#resultcontentAjax").html(msg);
        });

        request.fail(function(jqXHR, textStatus) {
            $("#loading-image").hide();
            //alert( "Request failed: " + textStatus );
        });
    }
    ;

//$(document).ready(function() {
    function iniDialog() {
        $("#dialog").dialog({
            autoOpen: true,
            resizable: true,
            //height:140,
            modal: true,
            buttons: {
                "Închide": function() {
                    $(this).dialog("close");
                    $("#dialog").html("");
                }
            }
        });
        //$("#dialog").dialog();
    }
    ;

    loadAjaxTable();
//});
</script>