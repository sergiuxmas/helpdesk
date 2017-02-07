<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="panel panel-default">
    <div class="panel-body panel-heading">
        <h4 class="panel-title">
            <a href="#collapseOne" data-parent="#accordion" data-toggle="collapse">Utilizatori</a>
        </h4>
    </div>
    <div class="panel-footer">
        <div id="tabs">
            <ul>
                <li><a href="#tabs-1">Utilizatori neafiliati diviziunii</a></li>
                <li><a href="#tabs-2">Utilizatori</a></li>
            </ul>
            <div id="tabs-1">
                <div id='jqxWidget'>
                    <table id="utilizatoriNeafiliati" ></table>
                    <div id="utilizatoriPaginare"></div>
                </div>
            </div>
            <div id="tabs-2" >
                <table id="utilizatori" ></table>
                <div id="utilizatoriPaginare2"></div>
            </div>
        </div>
    </div>
</div>
<script language="javascript">
    // TREE DIVIZIUNI GRID
    $(function() {
        $("#tabs").tabs();
    });

</script>

<script type="text/javascript">
    jQuery.extend
            (
                {
                    getValues: function(url) 
                    {
                        var result = null;
                        $.ajax(
                            {
                                url: url,
                                type: 'get',
                                dataType: 'json',
                                async: false,
                                cache: false,
                                success: function(data) 
                                {
                                    result = data;
                                }
                            }
                        );
                       return result;
                    }
                }
            );
        var categoriiDataSource=$.parseJSON(JSON.stringify($.getValues("${contextPath}/categorii/list")));
        var diviziuniDataSource=$.parseJSON(JSON.stringify($.getValues("${contextPath}/diviziuni/list")));

        function getCatDataSourceById(catId){
            
            for(var i=0;i<categoriiDataSource.length;i++){
                if(categoriiDataSource[i]['id']===catId){
                    return categoriiDataSource[i]['nume'];
                }
            }
            return catId;
        }
        
        function getDivDataSourceById(divId){
            for(var i=0;i<diviziuniDataSource.length;i++){
                if(diviziuniDataSource[i]['idDiviziune']===divId){
                    return diviziuniDataSource[i]['nume'];
                }
            }
            return divId;
        }
        
    //USERI GRID
    $(document).ready(function() {
        var template = "<div style='margin-left:15px;'>";
        template += "<div> Denumirea: </div><div>{nume}</div>";
        template += "<hr style='width:100%;'/>";
        template += "<div> {sData} {cData}  </div></div>";

        jQuery("#utilizatoriNeafiliati").jqGrid({
            url: '${contextPath}/user/listNeatasati',
            editurl: '${contextPath}/user/edit',
            datatype: "json",
            mtype: "post",
            //autowidth: true,
            rownumbers: true,
            //colNames:['HZ','Nr. ord','Denumirea'], 
            rowNum: 20,
            rowList: [10, 20, 30, 50, 100],
            shrinkToFit: false,
            height: '100%',
            pager: '#utilizatoriPaginare',
            viewrecords: true,
            //loadonce: true,
            //sortorder: "desc", 
            //sortname: 'id', 
            //caption:"Lista categoriilor",
            colModel: [
                {
                    label: "ID",
                    name: 'id',
                    index: 'id',
                    key: true,
                    width: 30,
                    hidden: true,
                    search: false
                },
                {
                    label: "IDNP",
                    name: 'idnp',
                    index: 'idnp',
                    width: 100,
                    editable: true
                },
                {
                    label: "username",
                    name: 'username',
                    index: 'username',
                    width: 150
                },
                {
                    label: "Nume",
                    name: 'nume',
                    index: 'nume',
                    width: 250,
                    editable: true
                },
                {
                    label: "diviziune",
                    name: 'id_diviziune',
                    index: 'id_diviziune',
                    width: 200,
                    editable: true,
                    edittype: "select",
                    editoptions: {
                        dataUrl:"${contextPath}/diviziuni/list",
                        buildSelect: function(data) {
                            var response = $.parseJSON(data);
                            var s = '<select>';
                            s += '<option value="0">--No diviziune--</option>';
                            $($.parseJSON(data)).map(function() {
                                s += '<option value="' + this.idDiviziune + '">' + this.nume + '</option>';
                            });
                            return s + "</select>";
                            
                        }
                    },
                    formatter: function(cellvalue, options, rowObject) {
                       return getDivDataSourceById(cellvalue);
                    }
                },
                {
                    label: "categorie",
                    name: 'id_categoria',
                    index: 'id_categoria',
                    width: 200,
                    editable: true,
                    edittype: "select",
                    editoptions: {
                        //formatter:'select',
                        //value: categoriiDataSource,//http://www.trirand.com/jqgridwiki/doku.php?id=wiki:common_rules#select
                        multiple:true,
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
                       /* 
                       var s='';
                        $(categoriiDataSource).map(function() {
                            s +=this.id+"|"+this.nume+"|"+cellvalue+"<br>";
                            if(this.id==cellvalue){ 
                                return;
                            }
                        });
                        return s;
                        */
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
                        delOptions: {}
                    },
                    search: false
                }
            ]
        }
        );

        jQuery("#utilizatoriNeafiliati").jqGrid('filterToolbar', {
            searchOperators: true
        });

        $('#utilizatoriNeafiliati').navGrid(
                "#utilizatoriPaginare",
                {
                    edit: true, add: true, del: true, refresh: true, view: false
                },
        // options for the Edit Dialog
        {
            editCaption: "Editarea utilizatorului",
            template: template,
            errorTextFormat: function(data) {
                return 'Error: ' + data.responseText
            }
        },
        // options for the Delete Dailog
        {
            errorTextFormat: function(data) {
                return 'Error: ' + data.responseText
            }
        });
    });
</script>


<script type="text/javascript">
    var template = "<div style='margin-left:15px;'>";
        template += "<div> Denumirea: </div><div>{nume}</div>";
        template += "<hr style='width:100%;'/>";
        template += "<div> {sData} {cData}  </div></div>";
        
    $(document).ready(function() {
        jQuery("#utilizatori").jqGrid({
            url: '${contextPath}/user/gridlist',
            editurl: '${contextPath}/user/edit',
            datatype: "json",
            mtype: "post",
            //autowidth: true,
            rownumbers: true,
            //colNames:['HZ','Nr. ord','Denumirea'], 
            rowNum: 20,
            rowList: [10, 20, 30, 50, 100],
            shrinkToFit: false,
            height: '100%',
            pager: '#utilizatoriPaginare2',
            viewrecords: true,
            //loadonce: true,
            //sortorder: "desc", 
            //sortname: 'id', 
            //caption:"Lista categoriilor",
            colModel: [
                {
                    label: "ID",
                    name: 'id',
                    index: 'id',
                    key: true,
                    width: 30,
                    hidden: true,
                    search: false
                },
                {
                    label: "IDNP",
                    name: 'idnp',
                    index: 'idnp',
                    width: 100,
                    editable: true
                },
                {
                    label: "username",
                    name: 'username',
                    index: 'username',
                    width: 150
                },
                {
                    label: "Nume",
                    name: 'nume',
                    index: 'nume',
                    width: 250,
                    editable: true
                },
                {
                    label: "diviziune",
                    name: 'id_diviziune',
                    index: 'id_diviziune',
                    width: 200,
                    editable: true,
                    edittype: "select",
                    editoptions: {
                        dataUrl:"${contextPath}/diviziuni/list",
                        buildSelect: function(data) {
                            var response = $.parseJSON(data);
                            var s = '<select>';
                            s += '<option value="0">--No diviziune--</option>';
                            $($.parseJSON(data)).map(function() {
                                s += '<option value="' + this.idDiviziune + '">' + this.nume + '</option>';
                            });
                            return s + "</select>";
                            
                        }
                    },
                    formatter: function(cellvalue, options, rowObject) {
                       return getDivDataSourceById(cellvalue);
                    }
                },
                {
                    label: "categorie",
                    name: 'id_categoria',
                    index: 'id_categoria',
                    width: 200,
                    editable: true,
                    edittype: "select",
                    editoptions: {
                        //formatter:'select',
                        //value: categoriiDataSource,//http://www.trirand.com/jqgridwiki/doku.php?id=wiki:common_rules#select
                        multiple:true,
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
                       /* 
                       var s='';
                        $(categoriiDataSource).map(function() {
                            s +=this.id+"|"+this.nume+"|"+cellvalue+"<br>";
                            if(this.id==cellvalue){ 
                                return;
                            }
                        });
                        return s;
                        */
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
                        delOptions: {}
                    },
                    search: false
                }
            ],
            grouping:true, 
            groupingView : { groupField : ['id_diviziune'] }
        }
        );

        jQuery("#utilizatori").jqGrid('filterToolbar', {
            searchOperators: true
        });

        $('#utilizatori').navGrid(
                "#utilizatoriPaginare2",
                {
                    edit: true, add: true, del: true, refresh: true, view: false
                },
        // options for the Edit Dialog
        {
            editCaption: "Editarea utilizatorului",
            template: template,
            errorTextFormat: function(data) {
                return 'Error: ' + data.responseText
            }
        },
        // options for the Delete Dailog
        {
            errorTextFormat: function(data) {
                return 'Error: ' + data.responseText
            }
        });
    });
</script>