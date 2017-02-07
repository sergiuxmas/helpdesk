<div id="tiketViewLayout">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
        .rowGreen{
            background: #bbfcbb; 
        }
        .rowOrange{
            background: #fde1b0; 
        }
        .rowRed{
            background: #ff9999; 
        }
        .hover{
            cursor: pointer;
        }
        .hover:hover{
            text-decoration: underline;
            color: #0044cc;
        }
</style>

<div class="panel panel-primary">
    <div class="panel-body panel-heading">
        <h4 class="panel-title">
            <span data-parent="#accordion" data-toggle="collapse">Solicitarea ID=${idSolicitare}</span>
        </h4>
    </div>
    <div class="panel-footer">
        <table >
            <tbody>
                <tr>
                    <th width="160">Data</th>
                    <th width="10">Tip</th>
                    <th width="120">Statut</th>
                    <th width="100">Prioritatea</th>
                    <th width="160">Solicitantul</th>
                    <th width="140">Directia</th>
                    <th>Responsabil DSI</th>
                </tr>
                <tr 
                    <c:if test="${tiket.user_executor==null}"> class="rowRed" </c:if>
                    <c:if test="${tiket.statut==1}"> class="rowGreen"  </c:if>
                    <c:if test="${tiket.statut==2}"> class="rowOrange"  </c:if>
                >
                    <td>${tiket.data_solicitarii}</td>
                    <td>${tiket.tip}</td>
                    <td>${tiket.statutNume}</td>
                    <td>${tiket.prioritate}</td>
                    <td>${tiket.user}</td>
                    <td>${tiket.diviziune}</td>
                    <td>
                        <select name="executorDSI" id="executorDSI" class="">
                            <option value="">--selectare--</option>
                            <c:forEach items="${userList}" var="user">
                                <c:choose>
                                    <c:when test="${tiket.user_executor == user.id}">
                                        <option value="${user.id}" selected="selected">${user.nume}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${user.id}" >${user.nume}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select> 
                        <img id="responsabil_history" src="<c:url value="/resources/img/icon_list.gif" />" title="istoria repartizarii" style="cursor: pointer" data-toggle="modal" data-target="#myModal">
                        <div id="modifyResponsableDSIBt" class="btn btn-primary">Modificare</div> <c:if test="${tiket.user_decizie_nume!=null}"><span style="color:#666">(decizia: ${tiket.user_decizie_nume}/${tiket.data_stabilirii_deciziei})</span></c:if>
                    </td>
                </tr>
            </tbody>
        </table>
        <table style="margin-bottom: 4px;">
            <tbody>
            <td width="160"><b>Descrierea</b></td>
            <td><b>Domeniul:</b> ${tiket.domeniu}</td>
            </tr>
            </tbody>
        </table>
        <textarea class="form-control" rows="10" placeholder="descrierea" id="tiketDescriere" style="margin-top: -20px">${tiket.descriere}</textarea>
        <c:if test="${fileList!=null && fileList.size()>0}">
                <c:forEach items="${fileList}" var="file">
                    <div style="margin-top: 5px">
                        <a href="${contextPath}/file/get?id=${file.id}" target="_blank"><span class="glyphicon glyphicon-file"></span> ${file.filename}</a>
                    </div>
                </c:forEach>
        </c:if>
    </div>
</div>
<script language="javascript">
    $('#tiketDescriere').jqte(
        {format: false}
    );
</script>


<c:if test="${executareInfo!=null}">
        <div class="panel panel-primary" style="margin-top: 18px">
            <div class="panel-body panel-heading">
                <h4 class="panel-title">
                    <span data-parent="#accordion" data-toggle="collapse">Descrierea executarii solicitarii de catre specialistul DSI</span>
                </h4>
            </div>
            <div class="panel-footer">
                <div style="margin-bottom: 4px">
                    <c:if test="${executareInfo.prioritate!=null}"><b>Prioritatea:</b> ${executareInfo.prioritate} |</c:if>
                    <c:if test="${executareInfo.tip!=null}"><b>Tip:</b> ${executareInfo.tip} |</c:if>
                    <c:if test="${executareInfo.domeniu!=null}"><b>Domeniul:</b> ${executareInfo.domeniu}</c:if>
                </div>
                <textarea id="executareInfoDescriere"><c:out value="${executareInfo.descriere}" escapeXml="true"/></textarea>
                <div style="color: #777; margin-left: 5px">${executareInfo.data_executarii} | ${executareInfo.user}</div>
            </div>
        </div>
    <script language="javascript">
        $('#executareInfoDescriere').jqte(
            {format: false}
        );
    </script>
</c:if>
    
    
<!--  codeply chat  -->
    
    <div class="chat-container" >
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-comment"></span> Mesaje
                    <div class="btn-group pull-right">
                        <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                        </button>
                        <ul class="dropdown-menu slidedown">
                            <li><a onclick="refreshMessages()" style="cursor: pointer"><span class="glyphicon glyphicon-refresh"></span>Actualizare</a></li>
                        </ul>
                    </div>
                </div>
                <div class="panel-body" id="chat-container">
                    <ul class="chat">
                        <div id="messageListLayout"></div>
                    </ul>
                </div>
                <div class="panel-footer"><!-- panel-footer -->
                        <form id="messageForm" style="display: ">
                            <c:if test="${tiket.statut!=3}">
                                <div class="">
                                    <textarea name="newmessage" class="form-control" id="newMessageContent" ></textarea>
                                    <input type="hidden" name="idTiket" value="${idSolicitare}" />
                                    <div class="text-left"><div id="expediereMesajBtn" class="btn btn-warning btn-sm" style="margin-top: 4px">Expediere</div></div>
                                </div>
                           </c:if> 
                        </form>
                </div>
            </div>
        </div>
    </div>
</div>
     <script language="javascript">
        $('#newMessageContent').jqte(
            {format: false}
        );
    </script>    
    <!-- end chat   -->
                
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Istoria repartizarii specialistului responsabil</h4>
                </div>
                <div class="modal-body">
                    ...
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    
<script language="javascript">
    $( document ).ready(function() {
        refreshMessages();
        setInterval(function(){ refreshMessages(); }, 30000);//30 sec
    });
    
    $("#modifyResponsableDSIBt").click(function() {
        $.extend({
        getValues: function(url)
        {
            var result = null;
            $.ajax({
                data:{idTiket:${idSolicitare},newExecutorDSI:$("#executorDSI").val(),currentExecutorDSI:'${tiket.user_executor}'},
                url: url,
                type: 'post',
                dataType: 'html',
                //contentType: "application/json; charset=utf-8",
                async: false,
                cache: false,
                success: function(data) {
                    if(data=="sessionout"){
                        location.reload(); 
                    }
                    result = data;
                }
            }
            );
            return result;
        }
    }
    );
    var result = $.getValues("${contextPath}/tiket/admin/modifyResponsableDSI");
    $("#tiketViewLayout").html(result);
    });
    
    $( document ).ready(function() {
            refreshMessages();
    });
    function displayMessageForm(){
            refreshMessages();
            if ($("#messageForm").css("display") == "none"){
                    $("#messageForm").css("display", "inline");
                } else{
                    $("#messageForm").css("display", "none");
            }
        }
        
     function refreshMessages(){
           $.ajax({
            data: {idTiket:${idSolicitare}},
                    url: "${contextPath}/tiket/userDSI/messagesList",
                    type: 'get',
                    dataType: 'html',
                    //contentType: "application/json; charset=utf-8",
                    async: false,
                    cache: false,
                    success: function(data) {
                        if(data=="sessionout"){
                           location.reload(); 
                        }
                        $("#messageListLayout").html(data);
                    },
                    error:function(){
                        alert("Eroare");
                    }    
            });
      }
      $("#expediereMesajBtn").click(function(){
            if($("#newMessageContent").val()!=""){
                $.ajax({
                    data: $("#messageForm").serialize(),
                    url: "${contextPath}/tiket/userDSI/saveMessage",
                    type: 'post',
                    async: false,
                    cache: false,
                    success: function(data) {
                        $("#newMessageContent").val("");
                        $('#newMessageContent').jqteVal("");
                        refreshMessages();
                    },
                    error: function(){
                      alert("Eroare la procesare");  
                    }
                });
            }else{
                alert("Message is empty");
            }
        });
      
      $('#myModal').on('shown.bs.modal', function () {
            $.ajax({
            url: "${contextPath}/tiket/userDSI/responsableHistory",
                    data: {idTiket:${idSolicitare}}
            }).done(function(data) {
            if(data=="sessionout"){
               location.reload(); 
            }
            var html = "";
                    var arr = $.parseJSON(JSON.stringify(data));
                    html += "<table class='table table-striped'>";
                    html += "<tr>";
                    html += "<th>ID</th><th>Executor</th><th>Decizia</th><th>Data luarii deciziei</th><th>Data acceptarii</th>";
                    html += "</tr>";
                    for (var i = 0; i < arr.length; i++){
            html += "<tr>";
                    html += "<td>" + ${tiket.id} + "</td>" + "<td>" + arr[i]["user_executor"] + "</td><td>" + arr[i]["user_decizie"] + "</td><td>" + arr[i]["data_decizie"] + "</td><td>" + arr[i]["data_acceptare"] + "</td>";
                    html += "</tr>";
            }
            html += "<table>";
                    $(".modal-body").html(html);
            })
            .error(function() {
                $(".modal-body").html("Eroare");
             })
        });
    
</script>
</div>