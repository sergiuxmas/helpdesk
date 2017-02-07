<style>

.ui-jqgrid,
.ui-jqgrid .ui-jqgrid-view,
.ui-jqgrid .ui-jqgrid-pager,
.ui-jqgrid .ui-pg-input {
    font-size: 1em;
}

.ui-jqgrid tr.jqgrow td{
     white-space: normal;
     padding-left: 4px;
}
</style>
    <div class="panel panel-default">
        <div class="panel-body panel-heading">
            <h4 class="panel-title">
                <a href="#collapseOne" data-parent="#accordion" data-toggle="collapse">Editarea categoriilor</a>
            </h4>
        </div>
        <div class="panel-footer">
            <table id="categorii" ></table>
            <div id="categoriiPaginare"></div>
        </div>
    </div>

        <script type="text/javascript">
            $(document).ready(function () {
                
                var template = "<div style='margin-left:15px;'>";
                    template += "<div> Denumirea: </div><div>{nume}</div>";
                    template += "<hr style='width:100%;'/>";
                    template += "<div> {sData} {cData}  </div></div>";

                jQuery("#categorii").jqGrid({ 
                    url:'${contextPath}/helpdesk/categorii/gridlist', 
                    editurl: '${contextPath}/helpdesk/categorii/edit',
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
                            label: "Denumirea",
                            name:'nume',
                            index:'nume', 
                            editable: true,
                            width:500,
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
                    pager: '#categoriiPaginare', 
                    viewrecords: true, 
                    //loadonce: true,
                    //sortorder: "desc", 
                    //sortname: 'id', 
                    //caption:"Lista categoriilor" 
                    }
                );

                jQuery("#categorii").jqGrid('filterToolbar', {
                    searchOperators: true
                });

                $('#categorii').navGrid(
                        "#categoriiPaginare", 
                        {
                            edit: true, add: true, del: true, refresh: true, view: false
                        },
                         // options for the Edit Dialog
                        {
                            editCaption: "Editarea categoriei",
                            template: template,
                            errorTextFormat: function (data) {
                                return 'Error: ' + data.responseText
                            }
                        },
                        // options for the Add Dialog
                        {
                            addCaption: "Adaugarea categoriei",
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
        </script>
