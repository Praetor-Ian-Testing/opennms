<%--

/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2002-2006, 2008 The OpenNMS Group, Inc.  All rights reserved.
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
	import="org.opennms.web.MissingParameterException"
%>

<%
	String error = request.getParameter("error");
	String name = request.getParameter("name");
	int errorcode;

	if (error == null) {
		throw new MissingParameterException("error", new String[] { "error", "name" });
	}
	if (name == null) {
		throw new MissingParameterException("name", new String[] { "error", "name" });
	}

	try {
		errorcode = (new Integer(error)).intValue();
	} catch(Throwable t) {
		throw new ServletException("Admin:pollerConfig " + t);
	}
%>

<jsp:include page="/includes/header.jsp" flush="false" >
  <jsp:param name="title" value="User Configuration" />
  <jsp:param name="headTitle" value="Error Page" />
  <jsp:param name="headTitle" value="Configure Poller" />
  <jsp:param name="headTitle" value="Admin" />
  <jsp:param name="breadcrumb" value="<a href='admin/index.jsp'>Admin</a>" />
  <jsp:param name="breadcrumb" value="<a href='admin/pollerConfig/index.jsp'>Configure Pollers</a>" />
  <jsp:param name="breadcrumb" value="Error Page" />
</jsp:include>

<h3>
<% 
	switch(errorcode)
	{
		case 0:	%>
				Missing parameter <%= name %> in the poller configuration file
			
	<%		break;
		case 1: %>
				The <%= name %> poller already exists
	<%		break;
		case 2: %>
				The poller-configuration.xml file is empty
	<%		break;
		case 3: %>
				The capsd-configuration.xml file is empty
	<%
			break;
	}
%>
</h3>

<p>
<a href="admin/pollerConfig/index.jsp">Go back to the Poller Configuration
page</a>.
</p>

<jsp:include page="/includes/footer.jsp" flush="true"/>
