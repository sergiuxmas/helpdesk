<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="panel panel-primary">
        <div class="panel-body panel-heading">
            <h4 class="panel-title">
                <a href="#collapseOne" data-parent="#accordion" data-toggle="collapse">Modificarea parolei</a>
            </h4>
        </div>
        <div class="panel-footer">
            <form:form action="${contextPath}/helpdesk/user/changePassword/post" method="POST" commandName="userPassword" class="form-horizontal" role="form">
				<table width="100%" cellpadding="0" cellspacing="4" class="form-cautare" border="0">
					<tr>
						<td align="left" width="160">Parola curenta</td>
                                                <td align="left">
                                                    <div class="tableRow">
                                                        <form:input path="parolaCurenta" id="parolaCurenta" type="password" class="form-control" placeholder="parola curenta" />
                                                        <form:errors path="parolaCurenta" cssClass="error" />
                                                    </div>
                                                </td>
				    </tr>
				    <tr>
				    	<td align="left">Parola noua</td>
				    	<td align="left">
                                            <div class="tableRow">
                                                <form:input path="parolaNoua" id="parolaNoua" type="password" class="form-control" placeholder="parola noua" />
                                                <form:errors path="parolaNoua" cssClass="error" />
                                            </div>
				    		
				    	</td>
				    </tr>
				    <tr>
				    	<td align="left">Confirmarea parolei noi</td>
				    	<td align="left">
                                            <div class="tableRow">
                                                <form:input path="confirmareaParolei" id="confirmareaParolei" type="password" class="form-control" placeholder="confirmarea parolei" />
                                                <form:errors path="confirmareaParolei" cssClass="error" />
                                                <div class="error">${message}</div>
                                            </div>
				    		
				    	</td>
				    </tr>
				    <tr>
				    	<td align="left"></td>
				    	<td nowrap="nowrap" align="left"><input type="submit" value="Modificare" class="btn btn-success" /></td>
				    </tr>
				</table>
			</form:form>
        </div>
    </div>
