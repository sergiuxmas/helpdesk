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
    .ui-jqgrid tr.rowBold td{
        font-weight: bold; 
    }
    
    .ui-jqgrid tr.jqgrow td {font-size:1.1em}
    
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

<script>
    $(function() {
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
    });
</script>

<div class="panel panel-primary">
    <div class="panel-body panel-heading">
        <h4 class="panel-title">
            <span data-parent="#accordion" data-toggle="collapse">Lista solicitarilor</span>
        </h4>
    </div>
    <div class="panel-footer">
        <form id="filter" method="get" action="#">
            <table class="table" style="width: 500px">
                <thead>
                    <tr>
                        <th colspan="3">Data solicitarii</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <div class="input-group" >
                                <span class="input-group-addon" id="basic-addon1">de la:</span>
                                <input type="text" name="data_from" id="data_from" class="form-control datepicker" placeholder="data solicitarii" aria-describedby="basic-addon1">
                            </div>
                        </td>
                        <td>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1">pina la:</span>
                                <input type="text" name="data_to" id="data_to" class="form-control datepicker" placeholder="data solicitarii" aria-describedby="basic-addon1">
                            </div>
                        </td>
                        <td>
                            <button type="button" id="filterButton" class="btn btn-default navbar-btn" style="margin-top: -1px">Afisare</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>

        <table id="solicitari" ></table>
        <div id="solicitariPaginare"></div>
    </div>
</div>

<script type="text/javascript">
    jQuery.extend({
        getValues: function(url)
        {
            var result = null;
            $.ajax({
                url: url,
                type: 'get',
                dataType: 'json',
                //contentType: "application/json; charset=utf-8",
                async: false,
                cache: false,
                success: function(data) {
                    result = data;
                }
            }
            );
            return result;
        }
    }
    );
    var statutDataSource = $.getValues("${contextPath}/statut/list");
    var domeniiDataSource = $.getValues("${contextPath}/domenii/list");
    var diviziuniDataSource = $.getValues("${contextPath}/diviziuni/list");
    var prioritateDataSource = $.getValues("${contextPath}/prioritate/list");

    $(document).ready(function() {
        //se transmit valorile din forma in grid
        $("#filterButton").click(function() {
            //var formData = $("form#filter").serialize();
            $("#gs_data_solicitarii_from").val($("#data_from").val());
            $("#gs_data_solicitarii_to").val($("#data_to").val());
            $("#solicitari")[0].triggerToolbar();
        });
        //se initiaza valorile din forma daca sint in http
        $("#data_from").val(getHttpValue('data_solicitarii_from'));
        $("#data_to").val(getHttpValue('data_solicitarii_to'));

        //tabelul
        var template = "<div style='margin-left:15px;'>";
        template += "<div> Denumirea: </div><div>{nume}</div>";
        template += "<hr style='width:100%;'/>";
        template += "<div> {sData} {cData}  </div></div>";
        jQuery("#solicitari").jqGrid({
            //editurl: '${contextPath}/tiket/edit',
            //postData: $("form#filter").serialize(),
            datatype: "local",
            mtype: "post",
            rownumbers: true,
            //rowNum: 20,
            rowList: [5, 10, 20, 30, 50, 100],
            autowidth: true,
            //shrinkToFit: false,
            height: '100%',
            pager: '#solicitariPaginare',
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
                    label: "Data solicitarii",
                    name: 'data_solicitarii',
                    index: 'data_solicitarii',
                    //formatter: "date",
                    //formatoptions: {
                    //    srcformat: 'datetime',
                    //    newformat: 'd/m/Y h:i:s',
                    //    defaultValue:null // does nothing!
                    //},
                    formatter: function(cellvalue, options, rowObject) {
                        var date = new Date(cellvalue);
                        return $.datepicker.formatDate('dd.mm.yy', date) + " " + date.getHours() + ": " + date.getMinutes() + ": " + date.getSeconds();
                    },
                    sorttype: 'date',
                    searchoptions: {
                        defaultValue: getHttpValue('data_solicitarii'),
                        dataInit: function(element) {
                            //$(element).val("10.10.2015");
                            $(element).datepicker({
                                id: 'orderDate_datePicker',
                                dateFormat: 'dd.mm.yy',
                                //minDate: new Date(2010, 0, 1),
                                //maxDate: new Date(2020, 0, 1),
                                showOn: 'focus'
                            });
                        }
                    },
                    width: 125,
                    fixed: true
                },
                {
                    label: "Statut",
                    name: 'statut',
                    index: 'statut',
                    stype: "select",
                    searchoptions: {
                        defaultValue: getHttpValue('statut'),
                        value: function() {
                            var s = ":Toate;";
                            for (var i = 0; i < statutDataSource.length; i++) {
                                s += statutDataSource[i]['id'] + ":" + statutDataSource[i]['nume'];
                                if (i < statutDataSource.length - 1)
                                    s += ";";
                            }
                            return s;
                        }
                    },
                    width: 75,
                    fixed: true
                },
                {
                    label: "Prioritatea",
                    name: 'prioritate',
                    index: 'prioritate',
                    stype: "select",
                    searchoptions: {
                        defaultValue: getHttpValue('prioritate'),
                        value: function() {
                            var s = ":Toate;";
                            for (var i = 0; i < prioritateDataSource.length; i++) {
                                s += prioritateDataSource[i]['id'] + ":" + prioritateDataSource[i]['nume'];
                                if (i < prioritateDataSource.length - 1)
                                    s += ";";
                            }
                            return s;
                        }
                    },
                    width: 60,
                    fixed: true
                },
                {
                    label: "Solicitant",
                    name: 'user',
                    index: 'user',
                    width: 100,
                    fixed: true,
                    searchoptions: {
                        defaultValue: getHttpValue('user')
                    }
                },
                {
                    label: "Diviziune",
                    name: 'diviziune',
                    index: 'diviziune',
                    stype: "select",
                    searchoptions: {
                        defaultValue: getHttpValue('diviziune'),
                        value: function() {
                            var s = ":Toate;";
                            for (var i = 0; i < diviziuniDataSource.length; i++) {
                                s += diviziuniDataSource[i]['idDiviziune'] + ":" + diviziuniDataSource[i]['nume'];
                                if (i < diviziuniDataSource.length - 1)
                                    s += ";";
                            }
                            return s;
                        }
                    },
                    width: 70,
                    fixed: true
                },
                {
                    label: "Responsabil DSI",
                    name: 'user_executor_name',
                    index: 'user_executor_name',
                    width: 110,
                    fixed: true,
                    searchoptions: {
                        defaultValue: getHttpValue('user_executor_name')
                    }
                },
                {
                    label: "Domeniu",
                    name: 'domeniu',
                    index: 'domeniu',
                    stype: "select",
                    searchoptions: {
                        defaultValue: getHttpValue('domeniu'),
                        value: function() {
                            var s = ":Toate;";
                            for (var i = 0; i < domeniiDataSource.length; i++) {
                                s += domeniiDataSource[i]['id'] + ":" + domeniiDataSource[i]['nume'];
                                if (i < domeniiDataSource.length - 1)
                                    s += ";";
                            }
                            return s;
                        }
                    },
                    width: 120,
                    fixed: true
                },
                {
                    label: "Descrierea",
                    name: 'descriere',
                    index: 'descriere',
                    searchoptions: {
                        defaultValue: getHttpValue('descriere')
                    },
                    formatter: function(cellvalue, options, rowObject) {
                        ///<([^>]+?)([^>]*?)>(.*?)<\/\1>/ig
                        return cellvalue.replace(/<\/?[a-z][a-z0-9]*[^<>]*>/ig, " ").replace(/ +(?= )/g,'').replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");//se sterg tagurile si spatiile multiple
                    }
                },
                {
                    label: "data_solicitarii_from",
                    name: 'data_solicitarii_from',
                    hidden: true,
                    searchoptions: {
                        defaultValue: getHttpValue('data_solicitarii_from')
                    }
                },
                {
                    label: "data_solicitarii_to",
                    name: 'data_solicitarii_to',
                    hidden: true,
                    searchoptions: {
                        defaultValue: getHttpValue('data_solicitarii_to')
                    }
                },
                {
                    label: "opened",
                    name: 'opened',
                    index: 'opened',
                    hidden: true
                },
                {
                    label: "Detalii",
                    width: 50,
                    fixed: true,
                    search:false,
                    formatter: function(cellvalue, options, rowObject) {
                        var dataFromTheRow = jQuery('#solicitari').jqGrid('getRowData',options.rowId);  
                        return "<a href=\"${contextPath}/tiket/admin/view?id="+rowObject['id']+"\" style=\" margin-left:14px;\"><img src=\"<c:url value="/resources/img/detail-icon.png" />\" title=\"detalii\" ></a>";
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
                    if (rowData['opened']=="false" || rowData['opened']=="")
                        $(this).jqGrid('setRowData', rowIds[i], false, "rowBold");
                    if(rowData['statut']=="noua")
                        $(this).jqGrid('setRowData', rowIds[i], false, "rowGreen");
                    if(rowData['statut']=="in executie")
                        $(this).jqGrid('setRowData', rowIds[i], false, "rowOrange");
                    if(rowData['user_executor_name']=="")
                        $(this).jqGrid('setRowData', rowIds[i], false, "rowRed");   
                }
            },
            /*         
             ondblClickRow: function(rowid, iRow, iCol, e) {
             //pentru a se pastra request in istoria browser-ului
             var postData = $(this).jqGrid("getGridParam", "postData");
             if (postData.filters) {
             var link = window.location.href.split("?")[0] + "?" + 
             $.param({filters: postData.filters})+"&page="+postData.page+"&rows="+postData.rows+"&sidx="+postData.sidx+"&sord="+postData.sord+"&id="+rowid;
             history.pushState({}, '', link);
             }    
             window.location = "${contextPath}/tiket/view?id=" + rowid;
             }
             */
            ondblClickRow: function(rowid, iRow, iCol, e) {
                //pentru a se pastra request in istoria browser-ului
                var postData = $(this).jqGrid("getGridParam", "postData");
                var filters = postData.filters;
                filters = (filters) ? filters : "";
                var link = window.location.href.split("?")[0] + "?" +
                        "filters=" + filters + "&page=" + postData.page + "&rows=" + postData.rows + "&sidx=" + postData.sidx + "&sord=" + postData.sord + "&id=" + rowid;
                history.pushState({}, '', link);

                window.location = "${contextPath}/tiket/admin/view?id=" + rowid;
            }
        }
        );
        //setarea filterToolbar, este necesara pentru initializarea filtrului din http
        jQuery("#solicitari").jqGrid('filterToolbar', {
            searchOperators: false,
            stringResult: true,
            searchOnEnter: true,
            //defaultSearch: "cn",
            autosearch: true,
            beforeSearch: function() {

            }
        });

        //necesar pentru a initia filtrul default al tabelului
        function initGrid() {
            //var postData = $(this).jqGrid("getGridParam", "postData");

            var sidx = (getHttpValue("sidx")) ? getHttpValue("sidx") : "data_solicitarii";
            var sord = (getHttpValue("sord")) ? getHttpValue("sord") : "desc";
            var rows = (getHttpValue("rows")) ? getHttpValue("rows") : 20;
            var page = (getHttpValue("page")) ? getHttpValue("page") : 1;
            var filters = (getHttpValue("filters")) ? getHttpValue("filters") : "";

            $("#solicitari").setGridParam({datatype: 'json', url: '${contextPath}/tiket/admin/gridlist', page: page, sortname: sidx, sortorder: sord, rows: rows, rowNum: rows, postData: {filters: filters}});
            //$("#solicitari")[0].triggerToolbar();
            $("#solicitari").trigger("reloadGrid");
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

    /*
     //pentru vizualizarea obiectelor javascript
     function print_r(arr, level) {
     var dumped_text = "";
     if (!level)
     level = 0;
     
     //The padding given at the beginning of the line.
     var level_padding = "";
     for (var j = 0; j < level + 1; j++)
     level_padding += "    ";
     
     if (typeof (arr) == 'object') { //Array/Hashes/Objects 
     for (var item in arr) {
     var value = arr[item];
     
     if (typeof (value) == 'object') { //If it is an array,
     dumped_text += level_padding + "'" + item + "' ...\n";
     dumped_text += print_r(value, level + 1);
     } else {
     dumped_text += level_padding + "'" + item + "' => \"" + value + "\"\n";
     }
     }
     } else { //Stings/Chars/Numbers etc.
     dumped_text = "===>" + arr + "<===(" + typeof (arr) + ")";
     }
     return dumped_text;
     }
     */
</script>