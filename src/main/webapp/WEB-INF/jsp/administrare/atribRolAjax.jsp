<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!-- role-addModule -->
<style type="text/css">  
  a.modules {  
   display: block;  
   border: 1px solid #aaa;  
   text-decoration: none;  
   background-color: #fafafa;  
   color: #123456;  
   margin: 2px;  
   clear:both;  
  }  
  div.modules {    
   text-align: center;  
   margin: 10px;  
  }  
  select.modules {  
   width: 300px;  
   height: 200px;  
  }  
  option.no-select{
  	background-color: #a8a8a8;
  	color:#000;
  }
 </style>  
<script lang="javascript">
function prepareRolData(action='setModule'){
	var data=new Object();
	data.role = $("select[name=role]").val();
	if(action=='addModule'){
		data.module = $("select[name=module]").val();
	}
	if(action=='removeModule'){
		data.module = $("select[name=appliedModules]").val();
	}
	//alert(data.role +' | '+data.module);
	if(action=='addModule'){
		if(data.role==0 || data.module==null){$("#dialog").html("Selectaţi rolul si modulul");iniDialog();}
		else loadAjaxTable('setModule',data);
	}
	else{
		//alert(action);
		loadAjaxTable(action,data);
	}
}
</script>
<div style="margin-left:10px">Rol 
    <select name="role" onchange="prepareRolData()" >
        <option value="">...selectati</option>
        <c:forEach items="${roles}" var="rol">
            <option value="${rol.getId()}" ${selrol eq rol.getId() ? 'selected' : ''}>${rol.getNume()}</option>
        </c:forEach>
    </select>
</div>
<table border="0">
	<tr>
		<td><div class="modules">
				<div>Module</div>
				<select class="modules" multiple="multiple" name="module">  
			  	${deniedModules}
			  	</select>
			  <a class="modules" href="#" id="add" onclick="prepareRolData('addModule')">adăugare &gt;&gt;</a>  
			</div> </td>
		<td>
			<div class="modules">  
				<div>Module aplicate</div>
				<select class="modules" multiple="multiple" name="appliedModules"> 
				${accessibleModules}
				</select>
			
			  <a class="modules" href="#" id="remove" onclick="prepareRolData('removeModule')">&lt;&lt; eliminare</a>  
			</div> 
		</td>
	</tr>
</table> 