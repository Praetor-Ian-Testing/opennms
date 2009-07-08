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
%>

<jsp:include page="/includes/header.jsp" flush="false" >
  <jsp:param name="title" value="Import/Export Assets" />
  <jsp:param name="headTitle" value="Import/Export" />
  <jsp:param name="headTitle" value="Assets" />
  <jsp:param name="breadcrumb" value="<a href='admin/index.jsp'>Admin</a>" />
  <jsp:param name="breadcrumb" value="Import/Export Assets" />
</jsp:include>

<div id="width: 40%; float: left;">
  <h3>Import and Export Assets</h3>

  <p>
    <a href="admin/asset/import.jsp">Import Assets</a>
  </p>

  <p>
    <a href="admin/asset/assets.csv">Export Assets</a>
  </p>
</div>

<div id="width: 60%; float: left;">
  <h3>Importing Asset Information</h3>

  <p>
    The asset import page imports a comma-separated value file (.csv),
    (probably exported from spreadsheet) into the assets database.
  </p>

  <h3>Exporting Asset Information</h3>

  <p>
    All the nodes with asset information will be exported to a 
    comma-separated value file (.csv), which is suitable for use in a 
    spreadsheet application. 
  </p>
</div>

<jsp:include page="/includes/footer.jsp" flush="false" />
