<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    
    .ui-jqgrid tr.jqgrow td {font-size:1.2em}
    
</style>
        <table id="teme" ></table>
        <div id="temePaginare"></div>
        <br />
        <button type="button" id="filterButton" class="btn btn-default navbar-btn" style="margin-top: -1px" onclick="addForm()"><span class="glyphicon glyphicon-edit"></span> Adăugare temă</button>
       


<form class="tema panel panel-default" style="display: none" id="addForm" >
    <div class="panel-footer" style="background-color: #f7f7f7">
    <textarea class="form-control noedit" name="newThemeName" id="newThemeContent" ></textarea>
    <div style="margin-top: 10px"><input type="button" class="btn btn-primary btn-sm" value="Salvare" onclick="saveNewTheme()" ></div>
    </div>
</form>
<script language="javascript">
    $(function(){
        $('input.datepicker').on('click', function() {
           $(this).datepicker({showOn:'focus'}).focus();
        });
     });
     
    $('.noedit').jqte();
    function addForm(){
        $('#newThemeContent').jqteVal("");
        if($("#addForm").css("display")=="none"){
            $("#addForm").css("display","");
        }else{
            $("#addForm").css("display","none");
        }
    }

    String.prototype.replaceHtmlEntites = function() {
        var s = this;
        var translate_re = /&(nbsp|amp|quot|lt|gt);/g;
        var translate = {"nbsp": " ","amp" : "&","quot": "\"","lt"  : "<","gt"  : ">"};
        return ( s.replace(translate_re, function(match, entity) {
          return translate[entity];
        }) );
    };
    
    function saveNewTheme(){
        var newThemeContent=$("#newThemeContent").val();
        newThemeContent=newThemeContent.replaceHtmlEntites().replace(/<\/?[a-z][a-z0-9]*[^<>]*>/ig, "").replace(/ +(?= )/g,'');
        if(newThemeContent.length>1){
                $.ajax({
                    //data: $("#addForm").serialize(),
                    data: {newThemeName:newThemeContent},
                    url: "${contextPath}/exchange/addTheme",
                    type: 'post',
                    async: false,
                    cache: false,
                    success: function(data) {
                        if(data==true){
                            alert("Tema a fost salvata cu succes");
                            $('#newThemeContent').jqteVal("");
                            $("#teme").trigger("reloadGrid", [{page:1,sidx:'date_created',sord:'desc'}]);
                            $("#addForm").css("display","none");
                        }else{
                            alert("Tema nu a fost salvata");
                        }
                    },
                    error: function(){
                      alert("Eroare la procesare");  
                    }
                });
            }else{
                alert("Trebuie de completat denumirea temei");
            }
        
    }
</script>

<script type="text/javascript">
    themeIsEdited=false;
    
    function initdatepicker(){
        //$("#ui-datepicker-div").css("display","block");
        /*
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
        */
     }
        
    $(document).ready(function() {
        initdatepicker();

        jQuery("#teme").jqGrid({
            editurl: '${contextPath}/exchange/themeEdit',
            //postData: $("form#filter").serialize(),
            datatype: "local",
            mtype: "post",
            rownumbers: true,
            //rowNum: 20,
            rowList: [5, 10, 15, 20, 30, 50, 100],
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
                    label: "Tema",
                    name: 'name',
                    index: 'name',
                    width: 300,
                    editable: true
                },
                {
                    label: "Publicat",
                    name: 'active',
                    index: 'active',
                    search: true,
                    width: 55,
                    fixed: true,
                    align: 'center',
                    editable: true,
                    edittype:'checkbox',
                    formatter: "checkbox", formatoptions: { disabled: false},
                    editoptions: { value: "1:0"},
                    searchoptions: {
                        value: function() {
                            var s = ":toate;1:publicate;0:nepublicate";
                            return s;
                        }
                    },
                    
                    stype: "select", 
                    //searchoptions:{value: "1:0"},
                    //formatter: function(cellvalue, options, rowObject) {
                    //    if(cellvalue==1)
                    //        return 'da';
                    //    else return 'nu';
                    //}
                },
                {
                    label: "Data crearii",
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
                    width: 150,
                    fixed: true
                            //cellattr: function (rowId, tv, rawObject, cm, rdata) { return 'style="width:70px;"' }
                },
                {
                    label: "Selectare",
                    name: 'sel',
                    search: false,
                    width: 60,
                    fixed: true,
                    formatter: function(cellvalue, options, rowObject) {
                        var dataFromTheRow = jQuery('#teme').jqGrid('getRowData',options.rowId);  
                        return "<a href=\"${contextPath}/exchange/new?id="+rowObject['id']+"\" style=\" margin-left:14px;\"><img src=\"<c:url value="/resources/img/detail-icon.png" />\" title=\"detalii\" ></a>";
                    }
                },
                {
                    label: "Actiuni",
                    name: 'action',
                    search: false,
                    width: 60,
                    fixed: true,
                    formatter: "actions",
                    formatoptions: {
                        keys: true,
                        editOptions: {},
                        addOptions: {},
                        delOptions: {},
                        onEdit: function (id) {
                            themeIsEdited=true;
                        },
                        delOptions: {
                            onclickSubmit: function(rp_ge, rowid) {
                                themeIsEdited=true;
                                return true;
                            }
                        }
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
                /*
                //pentru a se pastra request in istoria browser-ului
                var postData = $(this).jqGrid("getGridParam", "postData");
                var filters = postData.filters;
                filters = (filters) ? filters : "";
                var link = window.location.href.split("?")[0] + "?" +
                        "filters=" + filters + "&page=" + postData.page + "&rows=" + postData.rows + "&sidx=" + postData.sidx + "&sord=" + postData.sord + "&id=" + rowid;
                history.pushState({}, '', link);
                */
                window.location = "${contextPath}/exchange/new?id=" + rowid;
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
            var rows = (getHttpValue("rows")) ? getHttpValue("rows") : 10;
            var page = (getHttpValue("page")) ? getHttpValue("page") : 1;
            var filters = (getHttpValue("filters")) ? getHttpValue("filters") : "";

            $("#teme").setGridParam({datatype: 'json', url: '${contextPath}/exchange/themeGridlist', page: page, sortname: sidx, sortorder: sord, rows: rows, rowNum: rows, postData: {filters: filters}});
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
        
        var myEditOptions = {
            keys: true,
            oneditfunc: function (rowid) {
                //alert("row with rowid=" + rowid + " is editing.");
                themeIsEdited=true;
            },
            aftersavefunc: function (rowid, response, options) {
                //alert("Rîndul " + rowid + " a fost modificat.");
                themeIsEdited=true;
            }
        };
        var myAddOptions = {
            keys: true,
            oneditfunc: function (rowid) {
                //alert("row with rowid=" + rowid + " is editing.");
            },
            aftersavefunc: function (rowid, response, options) {
                //alert("Rîndul " + rowid + " a fost modificat.");
                themeIsEdited=true;
                //$("#teme").trigger("reloadGrid");
                $("#teme").trigger("reloadGrid", [{page:1,sidx:'date_created',sord:'desc'}]); 
                //! problema - sa se insereze primul rind si page sa fie=1
                //var ids = $("#teme").jqGrid('getDataIDs');
                //var firstRowId = ids[0];
                //$("#teme").jqGrid('setSelection', '1');
                //$("#teme").setSelection(ids[0]);
                //alert(firstRowId);
            }
        };

    $('#teme').jqGrid('inlineNav','#temePaginare', {
        edit: true,
        add: true,
        del: true,
        cancel: true,
        addtext: "Adăugare",
        edittext: "Editare",
        savetext: "Salvare",
        canceltext: "Anulare",  
        refresh: true,
        addParams: { 
            position: "afterSelected",
            addRowParams: myAddOptions
        },
        //addedrow: "last",
        editParams: myEditOptions
    });

    
    /* //add button example
    $('#teme').navButtonAdd('#temePaginare',
        {
            caption: "Details",
            buttonicon: "ui-icon-document",
            onClickButton: function (id) {
                  alert("/ResourceManager/Details/" + id);
            },
            position: "first"
         }
        )
    */
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