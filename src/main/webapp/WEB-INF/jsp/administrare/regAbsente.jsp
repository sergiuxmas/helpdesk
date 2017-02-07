<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!-- https://eonasdan.github.io/bootstrap-datetimepicker/ 
<div class="row">
        <div class='col-sm-6'>
            <div class="form-group">
                <div class='input-group date' id='datetimepicker1'>
                    <input type='text' class="form-control" />
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-time"></span>
                    </span>
                </div>
            </div>
        </div>
        <script type="text/javascript">
                $('#datetimepicker1').datetimepicker({
                    locale: 'ro'
                });
        </script>
    </div>
-->
<div class="panel panel-primary">
        <div class="panel-body panel-heading">
            <h4 class="panel-title">
                <a href="#collapseOne" data-parent="#accordion" data-toggle="collapse">Registrul absentelor specialistilor DSIeT<a>
            </h4>
        </div>
        <div class="panel-footer">
            
            <table id="regAbsente" ></table>
            <div id="regAbsentePaginare"></div>
        </div>
 </div>

<script type="text/javascript">
    jQuery.extend({
        getValues: function(url)
        {
            var result = null;
            $.ajax({
                url: url,
                type: 'post',
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
    });
    var usersdsiDataSource = $.getValues("${contextPath}/userdsi/list");
    
            $(document).ready(function () {
                
                var template = "<div style='margin-left:15px;'>";
                    template += "<div> User DSIeT: </div><div>{user}</div>";
                    template += "<div> de la: </div><div>{from}</div>";
                    template += "<div> pina la: </div><div>{to}</div>";
                    template += "<hr style='width:100%;'/>";
                    template += "<div> {sData} {cData}  </div></div>";

                jQuery("#regAbsente").jqGrid({ 
                    url:'${contextPath}/regAbsente/gridlist', 
                    editurl: '${contextPath}/regAbsente/edit',
                    datatype: "json", 
                    mtype : "post",
                    rownumbers: true,
                    colModel:[ 
                        {
                            label: "ID",
                            name:'id',
                            index:'id',
                            key: true,
                            editable: false,
                            editrules : { required: true},
                            width:55,
                            hidden: true
                        }, 
                        {
                            label: "User DSIeT",
                            name:'user',
                            index:'user', 
                            stype: "select",
                            editable: true,
                            edittype:'select',
                            editoptions:{
                                //value:{1:'One',2:'Two'}
                                value: function() {
                                    var s = "";
                                    for (var i = 0; i < usersdsiDataSource.length; i++) {
                                        s += usersdsiDataSource[i]['id'] + ":" + usersdsiDataSource[i]['nume'];
                                        if (i < usersdsiDataSource.length - 1)
                                            s += ";";
                                    }
                                    return s;
                                }
                            },
                            width:300,
                            searchoptions: {
                                defaultValue: getHttpValue('user'),
                                value: function() {
                                    var s = "";
                                    for (var i = 0; i < usersdsiDataSource.length; i++) {
                                        s += usersdsiDataSource[i]['id'] + ":" + usersdsiDataSource[i]['nume'];
                                        if (i < usersdsiDataSource.length - 1)
                                            s += ";";
                                    }
                                    return s;
                                }
                            }
                        }, 
                        {
                            label: "din",
                            name:'from',
                            index:'from', 
                            editable: true,
                            width:200,
                            editoptions: {
                                dataInit : function (elem) {
                                    $(elem).datetimepicker({format:'d.m.Y H:i',lang:'ro'});
                                }
                            },
                            searchoptions: {
                                sopt: []
                            }
                        }, 
                        {
                            label: "pina la",
                            name:'to',
                            index:'to', 
                            editable: true,
                            editoptions: {
                                dataInit : function (elem) {
                                    $(elem).datetimepicker({format:'d.m.Y H:i',lang:'ro'});
                                }
                            },
                            width:200,
                            searchoptions: {
                                sopt: []
                            }
                        },
                        {
			    label: "Actiuni",
                            name: "actions",
                            width: 70,
                            formatter: "actions",
                            formatoptions: {
                                keys: true,
                                editOptions: {},
                                addOptions: {},
                                delOptions: {}
                            },
                            search:false
                        }
                    ], 
                    rowNum:20, 
                    rowList:[10,20,30,50,100], 
                    //autowidth: true,
                    shrinkToFit: false,
                    height: '100%',
                    pager: '#regAbsentePaginare', 
                    viewrecords: true, 
                    //loadonce: true,
                    //sortorder: "desc", 
                    //sortname: 'id', 
                    //caption:"Lista categoriilor" 
                    }
                );

                jQuery("#regAbsente").jqGrid('filterToolbar', {
                    searchOperators: true
                });

                $('#regAbsente').navGrid(
                        "#regAbsentePaginare", 
                        {
                            edit: true, add: true, del: true, refresh: true, view: false
                        },
                         // options for the Edit Dialog
                        {
                            editCaption: "Editarea registrului",
                            template: template,
                            errorTextFormat: function (data) {
                                return 'Error: ' + data.responseText
                            }
                        },
                        // options for the Add Dialog
                        {
                            addCaption: "Adaugare inregistrare",
                            template: template,
                            errorTextFormat: function (data) {
                                return 'Error: ' + data.responseText
                            }
                        },
                        // options for the Delete Dailog
                        {
                            errorTextFormat: function (data) {
                                return 'Error: ' + data.responseText
                        }
                  });
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
        </script>
        
        <script type="text/javascript">
             $(function () {
                $('#gs_from').datetimepicker({
                    format:'d.m.Y H:i',
                    inline:false,
                    lang:'ro'
                });
                $('#gs_to').datetimepicker({
                    format:'d.m.Y H:i',
                    lang:'ro'
                });
                $('#from').datetimepicker({
                    format:'d.m.Y H:i',
                    lang:'ro'
                });
            });
            
            $( document ).ready(function() {
                $( "#from" ).click(function() {
                    alert( "Handler for .click() called." );
                  });
            });
            
        </script>