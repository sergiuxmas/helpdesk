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
</style>

<div class="panel panel-primary">
    <div class="panel-body panel-heading">
        <h4 class="panel-title"><span data-parent="#accordion" data-toggle="collapse">Documente partajate</span></h4>
    </div>
    <div class="panel-footer">
        <table class="tema" width="100%">
            <tr>
                <td style="padding-left: 6px; "><i>Tema:</i> <c:out value="${tema.getName()}" /></td>
            </tr>
        </table>
        <br />
        <div class="form-horizontal" style="background-color: #f7f5f5; ">
            <c:if test="${tema.getId()!=null}">
            <div class="form-group" >
                <div class="col-sm-12" id="attachmentFiles">
                    <table id="fisiere" ></table>
                    <div id="fisierePaginare"></div>
                </div>
            </div>
            </c:if>
        </div>
        <button type="button" class="btn btn-default btn-sm" onclick="window.history.back();">Înapoi</button>
    </div>
</div>

<script language="javascript">
    $('.noedit').jqte();
    //$(".jqte_editor").prop('contenteditable','false');
</script>
<div id="test"></div>

<c:if test="${tema.getId()!=null}">
<script type="text/javascript">
    function updateState(){
        //alert($("#state").prop('checked'));
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
                alert("Eroare la procesare");
            }
        });
        }
    }
    
    $(document).ready(function() {
       jQuery("#fisiere").jqGrid({
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
                    label: "Fișiere",
                    name: 'filename',
                    index: 'filename',
                    editable: true,
                    searchoptions: {
                        defaultValue: getHttpValue('filename')
                    },
                    formatter: function(cellvalue, options, rowObject) {
                        var dataFromTheRow = jQuery('#fisiere').jqGrid('getRowData',options.rowId);  
                        return "<a href=\"${contextPath}/exchange/getFile?id="+rowObject['id']+"\">"+cellvalue+"</a>";
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
                        return "<a href=\"${contextPath}/exchange/getFile?id="+rowObject['id']+"\"><img src=\"<c:url value="/resources/img/detail-icon.png" />\" title=\"detalii\" ></a>";
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
            
            $("#fisiere").setGridParam({datatype: 'json', url: '${contextPath}/exchange/sharedGridlist',page:page,sortname:sidx,sortorder:sord,rows:rows,rowNum:rows,postData: { filters:filters,idTema:${tema.getId()} }});
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
