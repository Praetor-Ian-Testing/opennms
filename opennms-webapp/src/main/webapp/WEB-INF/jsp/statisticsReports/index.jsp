<%--

/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2007-2008 The OpenNMS Group, Inc.  All rights reserved.
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

<%@page language="java" contentType="text/html" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ec" uri="http://www.extremecomponents.org" %>

<jsp:include page="/includes/header.jsp" flush="false" >
  <jsp:param name="title" value="Statistics Reports List" />
  <jsp:param name="headTitle" value="Statistics Reports List" />
  <jsp:param name="location" value="reports" />
  <jsp:param name="breadcrumb" value="<a href='report/index.jsp'>Report</a>"/>
  <jsp:param name="breadcrumb" value="<a href='statisticsReports/index.htm'>Statistics Reports</a>"/>
  <jsp:param name="breadcrumb" value="List"/>
</jsp:include>

<c:choose>
  <c:when test="${empty model}">
    <h3>Report List</h3>
    <div class="boxWrapper">
      <p>
        None found.
      </p>
    </div>
  </c:when>

  <c:otherwise>
    <!-- We need the </script>, otherwise IE7 breaks -->
    <script type="text/javascript" src="js/extremecomponents.js"></script>
      
    <link rel="stylesheet" type="text/css" href="css/onms-extremecomponents.css"/>
        
    <form id="form" action="${relativeRequestPath}" method="post">
      <ec:table items="model" var="row"
        action="${relativeRequestPath}?${pageContext.request.queryString}"
        filterable="false"
        imagePath="images/table/compact/*.gif"
        title="Statistics Report List"
        tableId="reportList"
        form="form"
        rowsDisplayed="25"
        view="org.opennms.web.svclayer.etable.FixedRowCompact"
        showExports="true" showStatusBar="true" 
        autoIncludeParameters="false"
        >
      
        <ec:exportPdf fileName="Statistics Report List.pdf" tooltip="Export PDF"
          headerColor="black" headerBackgroundColor="#b6c2da"
          headerTitle="Statistics Report List" />
        <ec:exportXls fileName="Statistics Report List.xls" tooltip="Export Excel" />
      
        <ec:row highlightRow="false">
        <%--
          <ec:column property="name" interceptor="org.opennms.web.svclayer.outage.GroupColumnInterceptor"/>
          --%>

          <ec:column property="description" title="Report Description">
          	<c:url var="reportUrl" value="statisticsReports/report.htm">
          		<c:param name="id" value="${row.id}" />
          	</c:url>
          	<a href="${reportUrl}">${row.description}</a>
          </ec:column>

          <ec:column property="startDate" title="Reporting Period Start" cell="date" format="MMM d, yyyy  HH:mm:ss"/>
          <ec:column property="endDate" title="Reporting Period End"  cell="date" format="MMM d, yyyy  HH:mm:ss"/>
		  <ec:column property="duration" title="Run Interval">
            ${row.durationString}
          </ec:column>
          
          
        <%--
          <ec:column property="jobStartedDate" title="Job Started"  cell="date" format="MMM d, yyyy  HH:mm:ss"/>
          <ec:column property="jobCompletedDate" title="Job Completed"  cell="date" format="MMM d, yyyy  HH:mm:ss"/>
          <ec:column property="jobDuration" title="Job Run Time">
            ${row.jobDurationString}
          </ec:column>
        --%>

          <ec:column property="purgeDate" title="Keep Until At Least" cell="date" format="MMM d, yyyy  HH:mm:ss"/>
        </ec:row>
      </ec:table>
    </form>
  </c:otherwise>
</c:choose>


<jsp:include page="/includes/footer.jsp" flush="false"/>
