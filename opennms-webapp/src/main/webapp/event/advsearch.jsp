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
  <jsp:param name="title" value="Advanced Event Search" />
  <jsp:param name="headTitle" value="Advanced Search" />
  <jsp:param name="headTitle" value="Events" />
  <jsp:param name="breadcrumb" value="<a href='event/index.jsp'>Events</a>" />
  <jsp:param name="breadcrumb" value="Advanced Event Search" />
</jsp:include>

  <div id="contentleft">
      <h3>Advanced Event Search</h3>

      <jsp:include page="/includes/event-advquerypanel.jsp" flush="false" />
  </div> <!-- id="contentleft" -->

  <div id="contentright">
      <h3>Searching Instructions</h3>

      <p>The <strong>Advanced Event Search</strong> page can be used to search the event list on
      multiple fields. Fill in values for each field that you wish to use to narrow down
      the search.</p>

      <p>To select events by time, first check the box for the time range
      that you wish to limit and then fill out the time in the boxes provided.</p>

      <p>If you wish to select events within a specific time span, check <em>both</em>
      boxes and enter the beginning and end of the range in the boxes provided.</p>
  </div> <!-- id="contentright" -->

<jsp:include page="/includes/footer.jsp" flush="false" />
