<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="panel panel-default">
    <div class="panel-body panel-heading">
        <h4 class="panel-title">
            <a href="#collapseOne" data-parent="#accordion" data-toggle="collapse">Meniul</a>
        </h4>
    </div>
    <div class="panel-footer">
        <div id='jqxWidget'>
            <div id="treeGrid"></div>
            <input type="button" style="margin: 10px;" id="jqxAddButton" value="Add new row" /><input type="button" style="margin: 10px;" id="jqxRefButton" value="Reload data" />
        </div>
    </div>
</div>

<script language="javascript">
//http://www.jqwidgets.com/community/topic/adding-1-child-to-the-only-line-in-the-treegrid/
        $(document).ready(function () {
            var treeGrid=$("#treeGrid");
            var sourceUrl='${contextPath}/meniu/gridlist';
            var editUrl='${contextPath}/meniu/edit';
            var meniuUrl='${contextPath}/meniu/list';
            
            var source =
             {
                 dataType: "json",
                 dataFields: [
                      { name: "idMeniu", type: 'string' },
                      { name: "name", type: 'string' },
                      { name: "parentId", type: 'number' },
                      { name: "weight", type: 'number' },
                      { name: "path", type: 'string' },
                      { name: "type", type: 'string' }
                 ],
                 hierarchy:
                     {
                         keyDataField: { name: 'idMeniu' },
                         parentDataField: { name: 'parentId' }
                         //root: "children"
                     },
                 url: sourceUrl,
                 id: "id",
                 type: "POST",
                 updateRow: function (rowID, rowData, commit) {
                    var data = "action=edit&idMeniu="+rowData.idMeniu+"&name="+rowData.name+"&parent="+rowData.parentId+"&weight="+rowData.weight+"&path="+rowData.path+"&type="+rowData.type;
                    $.ajax({
                         dataType: 'json',
                         type: "POST",
                         url: editUrl,
                         data: data,
                         success: function (data, status, xhr) {
                             dropdownListAdapter.dataBind();
                             //dataAdapter.dataBind();
                         }

                   });
                   commit(true);
                 },
                 deleteRow: function(rowID, commit) {
                    if(confirm("Confirmati stergerea rindului?")){
                        var selection = treeGrid.jqxTreeGrid('getSelection');
                        var data = "action=del&idMeniu="+selection[0]['idMeniu'];
                                $.ajax({
                                     dataType: 'json',
                                     type: "POST",
                                     url: editUrl,
                                     data: data,
                                     success: function (data, status, xhr) {
                                         dropdownListAdapter.dataBind();
                                         //dataAdapter.dataBind();
                                     }
                        });
                        commit(true);
                    }
                }
             };
             
            var typeData = [{"idType":"menu", "tttype": "menu"},{"idType":"resource", "tttype": "resource"}];
            
            var sourceType = {
                dataType: "json",
                dataFields: [
                    { name: "idType", type: "string" },
                    { name: "tttype", type: "string" }
                ],
                localData: typeData,
                id: "idType"
            };
            var typeAdapter = new $.jqx.dataAdapter(sourceType, { autoBind: true, async: false });
             
            var dropDownListSource =
            {
                datatype: "json",
                datafields: [
                    { name: 'idMeniu' },
                    { name: 'name' },
                    { name: 'parentId' },
                    { name: 'weight' },
                    { name: 'path' }
                ],
                url: meniuUrl,
                type: "POST"
            };
            
            
            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadComplete: function () {
                }
            });
            
            var dropdownListAdapter = new $.jqx.dataAdapter(dropDownListSource, { autoBind: true, async: false });
            
            this.editrow = -1;
            treeGrid.jqxTreeGrid(
            {
                theme : "lightness",
                width: '100%',
                //width: '600',
                source: dataAdapter,
                //altRows: true,
                autoRowHeight: true,
                pageable: true,
                sortable: true,
                columnsResize: true,
                editable: true,
                pagerButtonsCount: 20,
                //toolbarHeight: 35,
                pageSize: 50,
                pageSizeOptions: ['10', '50', '100'],
                showToolbar: true,
                renderToolbar: function (toolBar) {
                    var toTheme = function(className) {
                        return className + " " + className + "-lightness";
                    };
                    // appends buttons to the status bar.
                    var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
                    var buttonTemplate = "<div style='float: left; padding: 3px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";
                    var addButton = $(buttonTemplate);
                    var infoButton = $(buttonTemplate);
                    var editButton = $(buttonTemplate);
                    var deleteButton = $(buttonTemplate);
                    var cancelButton = $(buttonTemplate);
                    var updateButton = $(buttonTemplate);
                    container.append(addButton);
                    container.append(editButton);
                    container.append(deleteButton);
                    container.append(cancelButton);
                    container.append(updateButton);
                    container.append(infoButton);
                    toolBar.append(container);
                    //infoButton.jqxButton({ cursor: "pointer", enableDefault: false, disabled: false, height: 25, width: 25 });
                    //infoButton.find('div:first').addClass(toTheme('jqx-icon-plus'));
                    //infoButton.jqxTooltip({ position: 'bottom', content: "Info" });
                    addButton.jqxButton({ cursor: "pointer", enableDefault: false, disabled: false, height: 25, width: 25 });
                    addButton.find('div:first').addClass(toTheme('jqx-icon-plus'));
                    addButton.jqxTooltip({ position: 'bottom', content: "Add" });
                    editButton.jqxButton({ cursor: "pointer", disabled: false, enableDefault: false, height: 25, width: 25 });
                    editButton.find('div:first').addClass(toTheme('jqx-icon-edit'));
                    editButton.jqxTooltip({ position: 'bottom', content: "Edit" });
                    deleteButton.jqxButton({ cursor: "pointer", disabled: false, enableDefault: false, height: 25, width: 25 });
                    deleteButton.find('div:first').addClass(toTheme('jqx-icon-delete'));
                    deleteButton.jqxTooltip({ position: 'bottom', content: "Delete" });
                    updateButton.jqxButton({ cursor: "pointer", disabled: false, enableDefault: false, height: 25, width: 25 });
                    updateButton.find('div:first').addClass(toTheme('jqx-icon-save'));
                    updateButton.jqxTooltip({ position: 'bottom', content: "Save Changes" });
                    cancelButton.jqxButton({ cursor: "pointer", disabled: false, enableDefault: false, height: 25, width: 25 });
                    cancelButton.find('div:first').addClass(toTheme('jqx-icon-cancel'));
                    cancelButton.jqxTooltip({ position: 'bottom', content: "Cancel" });
                    var rowKey = null;
                        treeGrid.on('rowSelect', function (event) {
                            var args = event.args;
                            rowKey = args.key;
                            updateButtons('Select');
                        });
                    addButton.click(function (event) {
                        treeGrid.on('rowUnselect', function (event) {
                            updateButtons('Unselect');
                        });
                        treeGrid.on('rowEndEdit', function (event) {
                            updateButtons('End Edit');
                        });
                        treeGrid.on('rowBeginEdit', function (event) {
                            updateButtons('Edit');
                        });

                        if (!addButton.jqxButton('disabled')) {
                            //alert("rowKey: " + rowKey);
                            var selection = treeGrid.jqxTreeGrid('getSelection');
                            //alert(print_r(selection[0].idDiviziune));
                            newRowID='new';
                            treeGrid.jqxTreeGrid('expandRow', rowKey);
                            // add new empty row.
                            treeGrid.jqxTreeGrid('addRow', null, {idMeniu: 'new',parentId:selection[0].idMeniu}, 'first', rowKey);
                            // select the first row and clear the selection.
                            //alert("newRowID: " + newRowID);
                            treeGrid.jqxTreeGrid('clearSelection');
                            treeGrid.jqxTreeGrid('selectRow', newRowID);
                            // edit the new row.
                            treeGrid.jqxTreeGrid('beginRowEdit', newRowID);
                            updateButtons('add');
                        }
                    });
                    
                    cancelButton.click(function (event) {
                        if (!cancelButton.jqxButton('disabled')) {
                            // cancel changes.
                            treeGrid.jqxTreeGrid('endRowEdit', rowKey, true);
                        }
                    });
                    
                    deleteButton.click(function (event) {
                        if (!deleteButton.jqxButton('disabled')) {
                            var selection = treeGrid.jqxTreeGrid('getSelection');
                            if (selection.length > 1) {
                                var keys = new Array();
                                for (var i = 0; i < selection.length; i++) {
                                    keys.push(treeGrid.jqxTreeGrid('getKey', selection[i]));
                                }
                                treeGrid.jqxTreeGrid('deleteRow', keys);
                            }
                            else {
                                treeGrid.jqxTreeGrid('deleteRow', rowKey);
                            }
                            updateButtons('delete');
                        }
                    });
                    
                    var updateButtons = function(action) {
                        switch (action) {
                        case "Select":
                            //addButton.jqxButton({ disabled: false });
                            //deleteButton.jqxButton({ disabled: false });
                            editButton.jqxButton({ disabled: false });
                            cancelButton.jqxButton({ disabled: true });
                            updateButton.jqxButton({ disabled: true });
                            break;
                        case "Unselect":
                            //addButton.jqxButton({ disabled: true });
                            //deleteButton.jqxButton({ disabled: true });
                            editButton.jqxButton({ disabled: true });
                            cancelButton.jqxButton({ disabled: true });
                            updateButton.jqxButton({ disabled: true });
                            break;
                        case "Edit":
                            //addButton.jqxButton({ disabled: true });
                            //deleteButton.jqxButton({ disabled: true });
                            editButton.jqxButton({ disabled: true });
                            cancelButton.jqxButton({ disabled: false });
                            updateButton.jqxButton({ disabled: false });
                            break;
                        case "End Edit":
                            //addButton.jqxButton({ disabled: false });
                            //deleteButton.jqxButton({ disabled: false });
                            editButton.jqxButton({ disabled: false });
                            cancelButton.jqxButton({ disabled: true });
                            updateButton.jqxButton({ disabled: true });
                            break;
                        }
                   };
                },
                ready: function()
                {
                    //treeGrid.jqxTreeGrid('expandRow', 2);
                },
                editable: true,
                editSettings: { saveOnPageChange: true, saveOnBlur: true, saveOnSelectionChange: false, cancelOnEsc: true, saveOnEnter: true, editOnDoubleClick: false, editOnF2: false },
                // called when jqxTreeGrid is going to be rendered.
                rendering: function()
                {
                    // destroys all buttons.
                    if ($(".editButtons").length > 0) {
                        $(".editButtons").jqxButton('destroy');
                    }
                    if ($(".cancelButtons").length > 0) {
                        $(".cancelButtons").jqxButton('destroy');
                    }
                },
                // called when jqxTreeGrid is rendered.
                rendered: function () {
                    if ($(".editButtons").length > 0) {
                        $(".cancelButtons").jqxButton();
                        $(".editButtons").jqxButton();
                        
                        var editClick = function (event) {
                            var target = $(event.target);
                            // get button's value.
                            var value = target.val();

                            // get clicked row.
                            var rowKey = event.target.getAttribute('data-row');
                            if (value == "Edit") {
                                // begin edit.
                                treeGrid.jqxTreeGrid('beginRowEdit', rowKey);
                                target.parent().find('.cancelButtons').show();
                                target.val("Save");
                            }
                            else {
                                // end edit and save changes.
                                target.parent().find('.cancelButtons').hide();
                                target.val("Edit");
                                treeGrid.jqxTreeGrid('endRowEdit', rowKey);
                                
                            }
                        }
                        $(".editButtons").on('click', function (event) {
                            editClick(event);
                        });
                 
                        $(".cancelButtons").click(function (event) {
                            // end edit and cancel changes.
                            var rowKey = event.target.getAttribute('data-row');
                            treeGrid.jqxTreeGrid('endRowEdit', rowKey, true);
                        });
                    }
                },
                columns: [
                   {
                      text: 'Ord', sortable: false, filterable: false, editable: false,hidden: true,
                      groupable: false, draggable: false, resizable: true,
                      datafield: '', columntype: 'number', width: 80,
                      cellsrenderer: function (row, column, value) {
                          rowIndex=row+1;
                          return rowIndex;
                      }
                  },
                  { text: 'ID', dataField: 'idMeniu', editable: false,  width: 50, hidden: true },
                  { text: 'Name', dataField: 'name', editable: true, sortable: true, resizable: true },
                  { text: 'Parent', dataField: 'parentId',columnType:'template',editable: true, sortable: true, resizable: true,
                       initeditor: function (row, cellvalue, editor) {
                           editor.jqxDropDownList('selectItem', cellvalue);
                       },
                        createeditor: function (row, cellvalue, editor) {
                            editor.jqxDropDownList({ autoDropDownHeight: true, source: dropdownListAdapter, displayMember: 'name', valueMember: 'idMeniu'});
                        },
                        
                        geteditorvalue: function (row, cellvalue, editor) {
                            return editor.val();
                        },
                       cellsRenderer: function (row, column, value) {
                          //var data=dropdownListAdapter.records;
                          return getDataFromAdapter(value);
                          //return print_r(data[0]);
                        }
                      
                  },
                  { text: 'Path', dataField: 'path', editable: true, sortable: true, resizable: true },
                  { text: 'Type', dataField: 'type',columnType:'template', editable: true, sortable: true, resizable: true,
                      initeditor: function (row, cellvalue, editor) {
                          editor.jqxDropDownList('selectItem', cellvalue);
                       },
                       createeditor: function (row, cellvalue, editor) {
                           editor.jqxDropDownList({ autoDropDownHeight: true, source: typeAdapter, displayMember: 'tttype', valueMember: 'idType'});
                        },
                       geteditorvalue: function (row, cellvalue, editor) {
                            return editor.val();
                        }
                  },
                  { text: 'Order', dataField: 'weight', editable: true, sortable: true, resizable: true },
                  {
                      text: 'Edit', cellsAlign: 'center', width: 120, align: "center", columnType: 'none', editable: false, sortable: false, dataField: null, 
                      cellsRenderer: function (row, column, value) {
                          // render custom column.
                          return "<button data-row='" + row + "' class='editButtons'>Edit</button><button style='display: none; margin-left: 5px;' data-row='" + row + "' class='cancelButtons'>Cancel</button>";
                      }
                  }
                ]
            });
            
            treeGrid.on('rowselect', function (event) {
                 //alert('ok');
            });
                            
            treeGrid.on('rowClick', function (event) {
                //alert('ok');
                
            });
            
            function getDataFromAdapter(parentId){
                var data=dropdownListAdapter.records;
                for(var i=0;i<data.length;i++){
                    if(data[i]['idMeniu']==parentId)
                    return data[i]['name'];
                }
                return '';
            }
            
            
            $('#jqxAddButton').click(function () {
                treeGrid.jqxTreeGrid('addRow', null, {
                    idMeniu: 'new',
                    name: null,
                    weight:null,
                    path:null,
                    parent:1
                }, 'first')
                
            });
            
            $('#jqxRefButton').click(function () {
                treeGrid.jqxTreeGrid('updateBoundData');
                dropdownListAdapter.dataBind();
            });
        });
        
        
        /*
        function print_r(arr,level) {
            var dumped_text = "";
            if(!level) level = 0;

            //The padding given at the beginning of the line.
            var level_padding = "";
            for(var j=0;j<level+1;j++) level_padding += "    ";

            if(typeof(arr) == 'object') { //Array/Hashes/Objects 
                for(var item in arr) {
                    var value = arr[item];

                    if(typeof(value) == 'object') { //If it is an array,
                        dumped_text += level_padding + "'" + item + "' ...\n";
                        dumped_text += print_r(value,level+1);
                    } else {
                        dumped_text += level_padding + "'" + item + "' => \"" + value + "\"\n";
                    }
                }
            } else { //Stings/Chars/Numbers etc.
                dumped_text = "===>"+arr+"<===("+typeof(arr)+")";
            }
            return dumped_text;
            }
        */
</script>


