<%--

/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2006, 2008 The OpenNMS Group, Inc.  All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc.:
 *
 *      51 Franklin Street
 *      5th Floor
 *      Boston, MA 02110-1301
 *      USA
 *
 * For more information contact:
 *
 *      OpenNMS Licensing <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 *
 *******************************************************************************/


--%>

<%@page language="java"
	contentType="text/html"
	session="true"
	import="org.opennms.netmgt.config.users.*,
	        org.opennms.netmgt.config.*,
		java.util.*"
%>

<%
	UserManager userFactory;
  	Map users = null;
	HashMap usersHash = new HashMap();
	String curUserName = null;
	
	try
    	{
		UserFactory.init();
		userFactory = UserFactory.getInstance();
      		users = userFactory.getUsers();
	}
	catch(Exception e)
	{
		throw new ServletException("User:list " + e.getMessage());
	}

	Iterator i = users.keySet().iterator();
	while (i.hasNext()) {
		User curUser = (User)users.get(i.next());
		usersHash.put(curUser.getUserId(), curUser.getFullName());
	}

%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/includes/header.jsp" flush="false">
	<jsp:param name="title" value="Role Configuration" />
	<jsp:param name="headTitle" value="Roles" />
	<jsp:param name="breadcrumb" value="Role List" />
</jsp:include>

<script language="Javascript" type="text/javascript" >

	function doOperation(op, role) {
		document.roleForm.operation.value=op;
		document.roleForm.role.value=role;
		document.roleForm.submit();
	}
	
	function doView(role) {
		doOperation("view", role);
	}

</script>



<form action="<c:url value='${reqUrl}'/>" method="post" name="roleForm">
	<input type="hidden" name="operation" />
	<input type="hidden" name="role" />
</form>

<h3>Role Configuration</h3>

<table width="100%" border="1" cellspacing="0" cellpadding="2" bordercolor="black">

         <tr bgcolor="#999999">
          <td><b>Name</b></td>
          <td><b>Supervisor</b></td>
          <td><b>Currently On Call</b></td>
          <td><b>Membership Group</b></td>
          <td><b>Description</b></td>
			<c:forEach var="role" items="${roleManager.roles}">
				<c:set var="viewUrl" value="javascript:doView('${role.name}')" />
				
				<tr>
				<td><a href="<c:out value='${viewUrl}'/>"><c:out value="${role.name}"/></a></td>
				<td><c:set var="supervisorUser"><c:out value="${role.defaultUser}"/></c:set><c:set var="fullName"><%= usersHash.get(pageContext.getAttribute("supervisorUser").toString()) %></c:set><span title="<c:out value="${fullName}"/>"><c:out value="${role.defaultUser}"/></span></td>
				<td>
					<c:forEach var="scheduledUser" items="${role.currentUsers}">
						<c:set var="fullName"><%= usersHash.get(pageContext.getAttribute("scheduledUser").toString()) %></c:set>
						<span title="<c:out value="${fullName}"/>"><c:out value="${scheduledUser}"/></span>
					</c:forEach>	
				</td>
				<td><c:out value="${role.membershipGroup}"/></td>
				<td><c:out value="${role.description}"/></td>
				</tr>
			</c:forEach>
		</table>

<jsp:include page="/includes/footer.jsp" flush="false" />
