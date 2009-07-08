<%--
/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2009 Massimiliano Dess&igrave; (desmax74@yahoo.it)
 * Copyright (C) 2009 The OpenNMS Group, Inc.
 * All rights reserved.
 *
 * This program was developed and is maintained by Rocco RIONERO
 * ("the author") and is subject to dual-copyright according to
 * the terms set in "The OpenNMS Project Contributor Agreement".
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
<%@ include file="/WEB-INF/jsp/taglibs.jsp"%>
<tag:pager href="user.list.page"/>
<div>
    <div align="center"><span><spring:message code="ui.users"/></span></div>
    <c:if test="${msg ne ''}">${msg}</c:if>
    <table class="list">
        <thead>
            <tr><th align="left"><spring:message code="ui.user.username"/></th></tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}" varStatus="status">
            <c:choose>
            <c:when test="${status.count % 2 == 0}">
            <tr class="table-row-dispari"></c:when>
            <c:otherwise>
            <tr class="table-row-pari"></c:otherwise>
            </c:choose>
                <td align="left">${user.username}</td>
                <td align="center"><a href="user.authorities.page?sid=${user.id}"><img border="0" title="<spring:message code="ui.action.view"/>" alt="<spring:message code="ui.action.view"/>" src="img/view.png"/></a></td>
                <td align="center"><a href="user.edit.page?sid=${user.id}"><img border="0" title="<spring:message code="ui.action.edit"/>" alt="<spring:message code="ui.action.edit"/>" src="img/edit.png"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br/>
<tag:pager href="user.list.page"/>
<div align="center">
<input type="image" src="img/add.png" title="<spring:message code="ui.action.user.new"/>" alt="<spring:message code="ui.action.user.new"/>" onclick="location.href = 'user.edit.page'" value="<spring:message code="ui.action.user.new"/>"/>
</div>