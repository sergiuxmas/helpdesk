<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    .tema .jqte_toolbar.unselectable { display: none;}
    .jqte_editor, .jqte_source { min-height:32px; padding: 4px; background-color: #f5f5f5;}
    .tema .jqte { border: none; }

    .mytheme.panel.panel-primary{ border: none}
    .mytheme .panel-body.panel-heading{ display: none}
    
    .ui-jqgrid tr.jqgrow td {font-size:1.2em}
    /* adaoga posibilitatea wrap pentru toate coloane*/
    .ui-jqgrid tr.jqgrow td {
        word-wrap: break-word; /* IE 5.5+ and CSS3 */
        white-space: pre-wrap; /* CSS3 */
        white-space: -moz-pre-wrap; /* Mozilla, since 1999 */
        white-space: -pre-wrap; /* Opera 4-6 */
        white-space: -o-pre-wrap; /* Opera 7 */
        overflow: hidden;
        height: auto;
        vertical-align: middle;
        padding-top: 3px;
        padding-bottom: 3px
    }
    
    .ui-dialog {
        top:7% !important;
      }
</style>
<div class="panel panel-primary">
    <div class="panel-body panel-heading">
        <h4 class="panel-title"><span data-parent="#accordion" data-toggle="collapse">Încărcarea documentelor</span></h4>
    </div>
    <div class="panel-footer">
        <table class="tema" width="100%">
            <tr>
                <td width="55" ><i>Publicat</i></td>
                <td style="padding-left: 6px;"><i>Tema</i></td>
                <td width="65">&nbsp;</td>
            </tr>
            <tr>
                <td align="center">
                    <input type="checkbox" id="state" 
                        <c:if test="${tema.getActive() == true}"> checked="checked" </c:if> 
                        <c:if test="${tema.getId()==null}"> disabled="disabled" </c:if>
                        onclick="updateState()"
                    />
                </td>
                <td style="padding-left: 8px; padding-right: 10px; <c:if test="${tema.getId()==null}"> background-color:#eaeaea; </c:if>">
                    <c:out value="${tema.getName()}" />
                    <!--<textarea class="form-control noedit" ></textarea>-->
                </td>
                <td><input type="button" class="form-control btn btn-primary btn-sm" value="..." onclick="showThemesListModal()" ></td>
            </tr>
        </table>
        <div id="dialog" style="display: none"></div>            
        <br />
        <div class="form-horizontal" style="background-color: #f7f5f5; ">
            <c:if test="${tema.getId()!=null}">
            <div class="form-group" >
                <div class="col-sm-12" id="attachmentFiles">
                    <table id="fisiere" ></table>
                    <div id="fisierePaginare"></div>
                </div>
            </div>
            <div class="form-group" style="margin-bottom: 4px; padding-left: 9px;">
                <div class="col-sm-12">
                    Atașare fișier
                </div>
            </div>
            <div class="form-group" style="padding-left: 6px; padding-bottom: 6px;" id="uploadFileForm" onload="refrashUploadFileForm()">
                <!-- uploadFileForm.jsp -->
            </div>
            </c:if>
        </div>

    </div>
</div>
<!--
<iframe style="display:none" src="${contextPath}/exchange/upload" width="100%" id="iframe" onload="resizeIframe(this)" scrolling="no" marginwidth="0" marginheight="0" frameborder="1" vspace="0" hspace="0"></iframe>
-->
<script language="javascript">
    $('.noedit').jqte();
    //$(".jqte_editor").prop('contenteditable','false');
</script>
<div id="test" ></div>

<script type="text/javascript">
    $('div[onload]').trigger('onload');
    $('#iframe').trigger('onload');
    
    function refrashUploadFileForm(){
        $("#uploadFileForm").load("${contextPath}/exchange/upload?idTema=${tema.getId()}");
    }
    
    function resizeIframe(iframe) {
        //var h=iframe.contentWindow.document.body.scrollHeight;
        //iframe.height = iframe.contentWindow.document.body.scrollHeight + "px";
        //$('#iframe').css('height',h);
        //alert(h);
    }
    
    //$("#test").append('<iframe src="${contextPath}/exchange/themeajax" width="100%" height="800px" border="0" frameborder="0" id="newIframe" ></iframe>');

    function showThemesListModal() {
        //$("#test").append('<iframe src="${contextPath}/exchange/themeajax" width="100%" height="800px" border="0" frameborder="0" id="newIframe" ></iframe>');

        /*
         $.fancybox([
         '${contextPath}/exchange/themeajax'//url to your page
         ], {
         'padding'           : 10,
         'type'              : 'iframe',//set type to iframe
         'overlayOpacity'    : 0.7,
         'overlayColor'      : 'black',
         'speedIn'           : 900,
         'speedOut'          : 400,
         'minWidth'          : 300,
         'width'             : '70%',
         //'autoDimensions' : true,
         //'autoScale' : true,
         'height'            : '70%',
         afterShow: function() {
         //var frameID = $('#fancybox-frame')
         //frameID.getParam("test");                         
         }
         });
         */
        //$('#gbox_teme').css('width','800px');
        //var doc = $('iframe' )[0].contentWindow.document;
        //alert(doc);   
        //alert(parent.jQuery.fancybox);
        //parent.jQuery.fancybox.close();

        //var DataGrid = $('#teme');
        //DataGrid.jqGrid('setGridWidth', parseInt($(window).width()) - 20);

        $('#dialog').load('${contextPath}/exchange/themeajax', function() {
            $(this).dialog({
                modal: true,
                height: 'auto',
                width: '90%',
                //position: 'center',
                //position: [200,200],
                //position: ['middle',20],
                resizable: true,
                title: 'Selectați tema',
                open: function(event, ui) {
                    //hide titlebar.
                    //$(this).parent().children('.ui-dialog-titlebar').hide();
                    $(".datepicker").datepicker({
                        showButtonPanel: true,
                        changeMonth: true,
                        changeYear: true,
                        //showOn: "button",
                        //buttonImage: "images/calendar.gif",
                        //buttonImageOnly: true,
                        //buttonText: "Select date"
                    });
                    $(".datepicker").datepicker("option", "dateFormat", "dd.mm.yy");
                },
                close: function() {
                    $('#dialog').html("");
                    if(themeIsEdited==true){
                        location.reload();
                    }
                }
            });
        });
        

    }

</script>

<!--
<iframe scrolling="auto" src="/helpdesk/exchange/themeajax" height="800" width="100%"></iframe>

-->
<c:if test="${tema.getId()!=null}">
<script type="text/javascript">
    var themeIsEdited=false;
    
    function updateState(){
        var state=${tema.getActive()};
        if(confirm("Doriti sa schimbati statutul temei?")){
            var data={id:${tema.getId()}, active: $("#state").prop('checked')};
            $.ajax({
            data: data,
            url: "${contextPath}/exchange/updateStateTheme",
            //contentType:false,
            //enctype: 'multipart/form-data',
            type: 'post',
            dataType: 'html',
            //processData: false,
            async: false,
            cache: false,
            success: function(data) {
                location.reload();
            },
            error: function() {
                $("#state").prop('checked',state);
                alert("Eroare la procesare");
            }
        });
        }else{
            //if($("#state").prop('checked')==false)
            $("#state").prop('checked',state);
            //else $("#state").prop('checked',false);
        }
    }
    
    $(document).ready(function() {
       jQuery("#fisiere").jqGrid({
           editurl: '${contextPath}/exchange/fileEdit',
            datatype: "local",
            mtype: "post",
            rownumbers: true,
            //rowNum: 20,
            rowList: [5, 10, 20, 30, 50, 100],
            autowidth: true,
            //shrinkToFit: false,
            height: '100%',
            pager: '#fisierePaginare',
            viewrecords: true,
            //sortname: "data_solicitarii",
            //sortorder: "desc",
            //ignoreCase: true,
            colModel: [
                {
                    label: "ID",
                    name: 'id',
                    index: 'id',
                    key: true,
                    editrules: {required: true},
                    hidden: true
                },
                {
                    label: "Tema",
                    name: 'id_tema',
                    index: 'id_tema',
                    hidden: true,
                    searchoptions: {
                        defaultValue: getHttpValue('id')
                    }
                },
                {
                    label: "Denumirea fișierului",
                    name: 'filename',
                    index: 'filename',
                    editable: true,
                    searchoptions: {
                        defaultValue: getHttpValue('filename')
                    }
                },
                {
                    label: "Ext.",
                    name: 'extention',
                    index: 'extention',
                    align: 'center',
                    width: 60,
                    editable: false,
                    search: false,
                    fixed: true,
                    formatter: function(cellvalue, options, rowObject) {
                        var dataFromTheRow = jQuery('#fisiere').jqGrid('getRowData',options.rowId);  
                        return "."+cellvalue;
                    }
                },
                {
                    label: "Data încărcării",
                    name: 'date_upload',
                    index: 'date_upload',
                    formatter: function(cellvalue, options, rowObject) {
                        var date = new Date(cellvalue);
                        return $.datepicker.formatDate('dd.mm.yy', date) + " " + date.getHours() + ": " + date.getMinutes() + ": " + date.getSeconds();
                    },
                    sorttype: 'date',
                    searchoptions: {
                        defaultValue: getHttpValue('date_upload'),
                        dataInit: function(element) {
                            //$(element).val("10.10.2015");
                            $(element).datepicker({
                                id: 'orderDate_datePicker',
                                dateFormat: 'dd.mm.yy',
                                //minDate: new Date(2010, 0, 1),
                                //maxDate: new Date(2020, 0, 1),
                                showOn: 'focus',
                                onSelect: function () {
                                    $("#fisiere")[0].triggerToolbar();
                                }
                            });
                        }
                    },
                    width: 140,
                    fixed: true
                    //cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="width:70px;"' }
                },
                {
                    label: "Descărcare",
                    name: 'username',
                    index: 'username',
                    width: 80,
                    align: 'center',
                    fixed: true,
                    formatter: function(cellvalue, options, rowObject) {
                        var dataFromTheRow = jQuery('#fisiere').jqGrid('getRowData',options.rowId);  
                        return "<a href=\"${contextPath}/exchange/getFile?id="+rowObject['id']+"\"><img src=\"<c:url value="/resources/img/detail-icon.png" />\" title=\"descarcare\" ></a>";
                    }
                },
                {
                    label: "Acțiuni",
                    name: 'action',
                    search: false,
                    width: 60,
                    fixed: true,
                    formatter: "actions",
                    formatoptions: {
                        keys: true,
                        editOptions: {},
                        addOptions: {},
                        delOptions: {}
                    }
                }
            ],
            loadBeforeSend: function(xhr, settings) {
                return true;
            },
            beforeRequest: function() {
            },
            loadComplete: function() {
            },
            ondblClickRow: function(rowid, iRow, iCol, e) {
            }
        }
        );
        //setarea filterToolbar, este necesara pentru initializarea filtrului din http
        jQuery("#fisiere").jqGrid('filterToolbar', {
            searchOperators: false,
            stringResult: true,
            searchOnEnter: false,
            autosearch: true,
            beforeSearch: function() {
            }
        });

        //necesar pentru a initia filtrul default al tabelului
        function initGrid(){
            //var postData = $(this).jqGrid("getGridParam", "postData");
            var sidx=(getHttpValue("sidx"))?getHttpValue("sidx"):"date_upload";
            var sord=(getHttpValue("sord"))?getHttpValue("sord"):"desc";
            var rows=(getHttpValue("rows"))?getHttpValue("rows"):20;
            var page=(getHttpValue("page"))?getHttpValue("page"):1;
            var filters=(getHttpValue("filters"))?getHttpValue("filters"):"";
            
            $("#fisiere").setGridParam({datatype: 'json', url: '${contextPath}/exchange/gridlist',page:page,sortname:sidx,sortorder:sord,rows:rows,rowNum:rows,postData: { filters:filters,idTema:${tema.getId()} }});
            //$("#solicitari")[0].triggerToolbar();
            $("#fisiere").trigger("reloadGrid");
        }
        initGrid(); 
        
    });
    
     //functie generala, returneaza valoarea din http dupa nume
    function getQueryStringData(name) {
        var result = null;
        var regexS = "[\\?&#]" + name + "=([^&#]*)";
        var regex = new RegExp(regexS);
        var results = regex.exec('?' + window.location.href.split('?')[1]);
        if (results != null) {
            result = decodeURIComponent(results[1].replace(/\+/g, " "));
        }
        return result;
    }

    //functie concreta, initializaeza filtrul din http
    function getHttpValue(name) {
        if(getQueryStringData(name)){
            return getQueryStringData(name);
        }
        else{
            var filter = getQueryStringData("filters");
            if (filter) {
                var rules = $.parseJSON(filter).rules;
                for (var i = 0; i < rules.length; i++) {
                    if (name == rules[i].field)
                        return rules[i].data;
                }
            }
        }
        return "";
    }
    
    //adaoga wrap la coloana
    function addWrapEffect(cellvalue) {
        return '<div class="wrapEffect" title="' + cellvalue + '">' + cellvalue + '</div>';
    }
</script>
</c:if>
