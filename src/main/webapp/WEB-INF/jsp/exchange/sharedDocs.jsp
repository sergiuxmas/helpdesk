<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style> 
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
    #gbox_teme{ }
</style>

<div class="panel panel-primary">
    <div class="panel-body panel-heading">
        <h4 class="panel-title">
            <span data-parent="#accordion" data-toggle="collapse">Documente partajate</span>
        </h4>
    </div>
    <div class="panel-footer">
        <table id="teme" ></table>
        <div id="temePaginare"></div>
    </div>
</div>

<script type="text/javascript">      
    $(document).ready(function() {
         jQuery("#teme").jqGrid({
            datatype: "local",
            mtype: "post",
            rownumbers: true,
            //rowNum: 20,
            rowList: [5, 10, 20, 30, 50, 100],
            autowidth: true,
            //shrinkToFit: false,
            height: '100%',
            pager: '#temePaginare',
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
                    label: "Teme",
                    name: 'name',
                    index: 'name',
                    editable: true
                },
                {
                    label: "A partajat",
                    name: 'nume',
                    index: 'nume',
                    search: true,
                    width: 150,
                    fixed: true,
                    editable: true
                },
                {
                    label: "Data",
                    name: 'date_created',
                    index: 'date_created',
                    sorttype: 'date',
                    formatter: function(cellvalue, options, rowObject) {
                        var date = new Date(cellvalue);
                        return $.datepicker.formatDate('dd.mm.yy', date) + " " + date.getHours() + ": " + date.getMinutes() + ": " + date.getSeconds();
                    },
                    searchoptions: {
                        dataInit: function(element) {
                            //$(element).val("10.10.2015");
                            $(element).datepicker({
                                id: 'orderDate_datePicker',
                                dateFormat: 'dd.mm.yy',
                                //minDate: new Date(2010, 0, 1),
                                //maxDate: new Date(2020, 0, 1),
                                showOn: 'focus',
                                onSelect: function () {
                                    $("#teme")[0].triggerToolbar();
                                }
                            });
                        }
                    },
                    width: 140,
                    fixed: true
                            //cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="width:70px;"' }
                },
                {
                    label: "Selectare",
                    name: 'sel',
                    search: false,
                    width: 60,
                    fixed: true,
                    align:'center',
                    formatter: function(cellvalue, options, rowObject) {
                        var dataFromTheRow = jQuery('#teme').jqGrid('getRowData',options.rowId);  
                        return "<a href=\"${contextPath}/exchange/sharedDocView?id="+rowObject['id']+"\"><img src=\"<c:url value="/resources/img/detail-icon.png" />\" title=\"detalii\" ></a>";
                    }
                }
            ],
            loadBeforeSend: function(xhr, settings) {
                return true;
            },
            beforeRequest: function() {
            },
            loadComplete: function() {
                //!IMPORTANT daca este id in http se insereaza rindul 
                $(this).jqGrid('setSelection', getHttpValue('id'));

                //colorarea rindurilor cu Responsabil DSI=null
                var rowIds = $(this).jqGrid('getDataIDs');
                for (i = 0; i < rowIds.length; i++) {//iterate over each row
                    rowData = $(this).jqGrid('getRowData', rowIds[i]);
                    if(rowData['active']=='nu')
                        $(this).jqGrid('setRowData', rowIds[i], false, "rowOrange");
                       
                }
            },
            ondblClickRow: function(rowid, iRow, iCol, e) {
                //pentru a se pastra request in istoria browser-ului
                var postData = $(this).jqGrid("getGridParam", "postData");
                var filters = postData.filters;
                filters = (filters) ? filters : "";
                var link = window.location.href.split("?")[0] + "?" +
                        "filters=" + filters + "&page=" + postData.page + "&rows=" + postData.rows + "&sidx=" + postData.sidx + "&sord=" + postData.sord + "&id=" + rowid;
                history.pushState({}, '', link);

                window.location = "${contextPath}/exchange/sharedDocView?id=" + rowid;
            }
        }
        );
        //setarea filterToolbar, este necesara pentru initializarea filtrului din http
        jQuery("#teme").jqGrid('filterToolbar', {
            searchOperators: false,
            stringResult: true,
            searchOnEnter: false,
            //defaultSearch: "cn",
            autosearch: true,
            beforeSearch: function() {

            }
        });
        //necesar pentru a initia filtrul default al tabelului
        function initGrid() {
            var postData = $(this).jqGrid("getGridParam", "postData");

            var sidx = (getHttpValue("sidx")) ? getHttpValue("sidx") : "date_created";
            var sord = (getHttpValue("sord")) ? getHttpValue("sord") : "desc";
            var rows = (getHttpValue("rows")) ? getHttpValue("rows") : 20;
            var page = (getHttpValue("page")) ? getHttpValue("page") : 1;
            var filters = (getHttpValue("filters")) ? getHttpValue("filters") : "";

            $("#teme").setGridParam({datatype: 'json', url: '${contextPath}/exchange/sharedDocsGridlist', page: page, sortname: sidx, sortorder: sord, rows: rows, rowNum: rows, postData: {filters: filters}});
            //$("#solicitari")[0].triggerToolbar();
            $("#teme").trigger("reloadGrid");
          
        }
        initGrid();
        
        //adaoga wrap la coloana
        function addWrapEffect(cellvalue) {
            return '<div class="wrapEffect" title="' + cellvalue + '">' + cellvalue + '</div>';
        }
        
        //var DataGrid = $('#teme');
        //DataGrid.jqGrid('setGridWidth', parseInt($(window).width()) - 20);
    });

//functie concreta, initializaeza filtrul din http
    function getHttpValue(name) {
        if (getQueryStringData(name)) {
            return getQueryStringData(name);
        }
        else {
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
    
    
</script>