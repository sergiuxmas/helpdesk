<div id="tiketViewLayout">
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>

    <style>
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
        
        .no_edit .jqte_toolbar { display: none;}
        .no_edit .jqte { border-color: #a8a8a8}
    </style>

    <div class="panel panel-primary no_edit">
        <div class="panel-body panel-heading">
            <h4 class="panel-title">
                <span data-parent="#accordion" data-toggle="collapse"><b>Solicitarea ID=${idSolicitare}</b></span>
            </h4>
        </div>
        <div class="panel-footer">
            <table>
                <tbody>
                    <tr>
                        <th width="160">Data</th>
                        <th width="30">Tip</th>
                        <th width="120">Statut</th>
                        <th width="100">Prioritatea</th>
                        <th width="160">Solicitantul</th>
                        <th width="140">Directia</th>
                        <th>Responsabil DSI</th>
                    </tr>
                    <tr <c:if test="${tiket.user_executor==null}">class="rowRed" </c:if>>
                        <td>${tiket.data_solicitarii}</td>
                        <td>${tiket.tip}</td>
                        <td>${tiket.statutNume}</td>
                        <td>${tiket.prioritate}</td>
                        <td>${tiket.user}</td>
                        <td>${tiket.diviziune}</td>
                        <td>
                            ${tiket.user_executor_name}
                            <img id="responsabil_history" src="<c:url value="/resources/img/icon_list.gif" />" title="istoria repartizarii" style="cursor: pointer" data-toggle="modal" data-target="#myModal">
                        </td>
                    </tr>
                </tbody>
            </table>
            <table class="table" style="margin: 6px">
                <tbody>
                <td width="160">Descrierea</td>
                <td>Domeniul: ${tiket.domeniu}</td>
                </tr>
                </tbody>
            </table>
            <textarea class="form-control" rows="7" placeholder="descrierea" id="tiketDescriere" style="margin-top: -20px">${tiket.descriere}</textarea>
            <c:if test="${fileList!=null && fileList.size()>0}">
                <c:forEach items="${fileList}" var="file">
                    <div style="margin-top: 5px">
                        <a href="${contextPath}/file/get?id=${file.id}" target="_blank"><span class="glyphicon glyphicon-file"></span> ${file.filename}</a>
                    </div>
                </c:forEach>
            </c:if>
            
            <c:if test="${tiket.statut!=3}">
            <hr style="margin:10px" />
            <c:if test="${tiket.acceptat!=1}">
                <div id="acceptareTiketBt" class="btn btn-primary">Acceptarea solicitarii</div>
            </c:if>
                
            <span class="glyphicon glyphicon-wrench hover" onclick="displayOrHideTiketAttributes()" > Modificarea responsabilului DSI</span>
            <div id="adminTiketParams" style="display: none;">
                <div style="margin-left: 25px">
                    Responsabil DSI:
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
                    <div id="modifyResponsableDSIBt" class="btn btn-primary">Modificare</div>
                </div>
            </div>
            </c:if>
        </div>
    </div>
    <script language="javascript">
    $('#tiketDescriere').jqte(
        {format: false}
    );
    </script>
    
      
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
    
        
    <c:if test="${tiket.acceptat==1}">    
        <form:form id="executareForm" commandName="executareFormModel" >
        <div class="panel panel-primary" style="margin-top: 18px">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <span data-parent="#accordion" data-toggle="collapse">Executarea solicitarii</span>
                </h4>
            </div>
            <div class="panel-footer">
                <c:if test="${tiket.statut!=3}">
                <div style="margin-bottom: 4px">
                    Prioritatea: 
                    <select name="prioritate" >
                        <option value="">definita de solicitant</option>
                        <c:forEach items="${prioritateList}" var="prioritate">
                            <c:choose>
                                <c:when test="${prioritate.getId() == executareFormModel.getPrioritate()}">
                                    <option value="${prioritate.getId()}" selected="selected">${prioritate.getNume()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${prioritate.getId()}" >${prioritate.getNume()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    Tip: 
                    <select name="tip" >
                        <option value="">definita de solicitant</option>
                        <c:forEach items="${tipList}" var="tip">
                            <c:choose>
                                <c:when test="${tip.getId() == executareFormModel.getTip()}">
                                    <option value="${tip.getId()}" selected="selected">${tip.getNume()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${tip.getId()}" >${tip.getNume()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    Domeniul: 
                    <select name="domeniu" >
                        <option value="">definita de solicitant</option>
                        <c:forEach items="${domeniuList}" var="domeniu">
                            <c:choose>
                                <c:when test="${domeniu.getId() == executareFormModel.getDomeniu()}">
                                    <option value="${domeniu.getId()}" selected="selected">${domeniu.getNume()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${domeniu.getId()}" >${domeniu.getNume()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <div id="salvareExecutareBtn" class="btn btn-default btn-xs" style="margin-top: -5px;"><span class="glyphicon glyphicon-floppy-save"></span> Salvare</div>
                </div>
                </c:if>
                <form:input type="hidden" name="idTiket" path="idTiket" value="${idSolicitare}" />
                <form:textarea id="descriereExp" name="descriere" path="descriere" placeholder="descriere" class="form-control" />
                <form:errors path="descriere" cssClass="error" />
                <c:if test="${tiket.statut!=3}">
                <div id="closeStatusBtn" class="btn btn-info" style="margin-top: 4px;"><span class="glyphicon glyphicon-off"></span> Inchiderea solicitarii</div>
                </c:if>
            </div>
        </div>
        </form:form>
        <script language="javascript">
        $('#descriereExp').jqte(
            {format: false}
        );
        </script>
    </c:if>

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
        
        function displayOrHideTiketAttributes(){
           if ($("#adminTiketParams").css("display") == "none"){
                $("#adminTiketParams").css("display", "inline");
           } else{
                $("#adminTiketParams").css("display", "none");
           }
        }
                
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

        $("#modifyResponsableDSIBt").click(function() {
            $.ajax({
                    data: {idTiket:${idSolicitare}, newExecutorDSI: $("#executorDSI").val()<c:if test="${tiket.user_executor!=null}">, currentExecutorDSI:${tiket.user_executor}</c:if>},
                    url: "${contextPath}/tiket/userDSI/modifyResponsableDSI",
                    type: 'post',
                    dataType: 'html',
                    //contentType: "application/json; charset=utf-8",
                    async: false,
                    cache: false,
                    success: function(data) {
                        if(data=="sessionout"){
                           location.reload(); 
                        }
                        $("#tiketViewLayout").html(data);
                    }
            });
        });
        
        $("#acceptareTiketBt").click(function() {
            $.ajax({
            data: {idTiket:${idSolicitare}<c:if test="${tiket.id_executor_history!=null}">, id_executor_history:${tiket.id_executor_history} </c:if>},
                    url: "${contextPath}/tiket/userDSI/acceptTiket",
                    type: 'post',
                    dataType: 'html',
                    //contentType: "application/json; charset=utf-8",
                    async: false,
                    cache: false,
                    success: function(data) {
                        if(data=="sessionout"){
                           location.reload(); 
                        }
                        $("#tiketViewLayout").html(data);
                    }
            });
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
        
        $("#expediereMesajBtn").click(function(){
            if($("#newMessageContent").val()!=""){
                $.ajax({
                    data: $("#messageForm").serialize(),
                    url: "${contextPath}/tiket/userDSI/saveMessage",
                    type: 'post',
                    async: false,
                    cache: false,
                    success: function(data) {
                        //$("#newMessageContent").val("");
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
        
        $("#salvareExecutareBtn").click(function(){
            $.ajax({
                data: $("#executareForm").serialize(),
                url: "${contextPath}/tiket/userDSI/executareTiket",
                type: 'post',
                async: false,
                cache: false,
                success: function(data) {
                    if(data=="sessionout"){
                        location.reload(); 
                    }
                    $("#tiketViewLayout").html(data);
                    //$("#descriereExp").val("");
                    //$('#newMessageContent').jqteVal("");
                },
                error: function(){
                  alert("Eroare la procesare");  
                }
            });
        });
        
        $("#closeStatusBtn").click(function(){
            if(confirm("Confirmati inchiderea solicitarii?")){
                $("#salvareExecutareBtn").click();
                $.ajax({
                    data: {idTiket:${idSolicitare}},
                    url: "${contextPath}/tiket/userDSI/inchidereTiket",
                    type: 'post',
                    async: false,
                    cache: false,
                    success: function(data) {
                        if(data=="sessionout"){
                           location.reload(); 
                        }
                        $("#tiketViewLayout").html(data);
                    },
                    error: function(){
                      alert("Eroare la procesare");  
                    }
                });
            }
        });
        
         
    </script>
</div>