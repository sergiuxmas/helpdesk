<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>

</style>
    <div class="panel panel-default">
        <div class="panel-body panel-heading">
            <h4 class="panel-title">
                <a href="#collapseOne" data-parent="#accordion" data-toggle="collapse">Editarea resurselor</a>
            </h4>
        </div>
        <div class="panel-footer">
            <table id="resources" ></table>
            <div id="resourcesPaginare"></div>
        </div>
    </div>

        <script type="text/javascript">
            $(document).ready(function () {
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
                var categoriiDataSource = $.getValues("${contextPath}/categorii/list");
                
                var template = "<div style='margin-left:15px;'>";
                    template += "<div> Path: </div><div>{path}</div>";
                    template += "<div> Rol: </div><div>{id_role}</div>";
                    template += "<hr style='width:100%;'/>";
                    template += "<div> {sData} {cData}  </div></div>";

                jQuery("#resources").jqGrid({ 
                    url:'${contextPath}/resources/gridlist', 
                    editurl: '${contextPath}/resources/edit',
                    datatype: "json", 
                    mtype : "post",
                    rownumbers: true,
                    //colNames:['HZ','Nr. ord','Denumirea'], 
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
                            label: "Path",
                            name:'path',
                            index:'path', 
                            editable: true,
                            width:400,
                            searchoptions: {
                                sopt: []
                            }
                        }, 
                        {
                            label: "Rol",
                            name:'id_role',
                            index:'id_role',
                            stype: "select",
                            searchoptions: {
                                defaultValue: getHttpValue('id_role'),
                                value: function() {
                                    var s = ":Toate;";
                                    for (var i = 0; i < categoriiDataSource.length; i++) {
                                        s += categoriiDataSource[i]['id'] + ":" + categoriiDataSource[i]['nume'];
                                        if (i < categoriiDataSource.length - 1)
                                            s += ";";
                                    }
                                    return s;
                                }
                            },
                            editable: true,
                            width:200,
                            edittype: "select",
                            editoptions: {
                                dataUrl:"${contextPath}/categorii/list",
                                buildSelect: function(data) {
                                    var response = $.parseJSON(data);
                                    var s = '<select>';
                                    s += '<option value="0">--No category--</option>';
                                    $($.parseJSON(data)).map(function() {
                                        s += '<option value="' + this.id + '">' + this.nume + '</option>';
                                    });
                                    return s + "</select>";

                                }
                            },
                           formatter: function(cellvalue, options, rowObject) {
                                return getCatDataSourceById(cellvalue);
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
                    pager: '#resourcesPaginare', 
                    viewrecords: true, 
                    //loadonce: true,
                    //sortorder: "desc", 
                    //sortname: 'id', 
                    //caption:"Lista ressurselor" 
                    }
                );

                jQuery("#resources").jqGrid('filterToolbar', {
                    searchOperators: true
                });

                $('#resources').navGrid(
                        "#resourcesPaginare", 
                        {
                            edit: true, add: true, del: true, refresh: true, view: false
                        },
                         // options for the Edit Dialog
                        {
                            editCaption: "Editarea resursei",
                            template: template,
                            errorTextFormat: function (data) {
                                return 'Error: ' + data.responseText
                            }
                        },
                        // options for the Add Dialog
                        {
                            addCaption: "Adaugare resursa",
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
             
             function getCatDataSourceById(catId){
                for(var i=0;i<categoriiDataSource.length;i++){
                    if(categoriiDataSource[i]['id']===catId){
                        return categoriiDataSource[i]['nume'];
                    }
                }
                return catId;
            }
            
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
