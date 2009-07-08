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
<div align="center"><span><spring:message code="ui.user.groups.authorities"/></span></div>
<form action="authority.list.page" method="post">
    <p class="delimitato"><span><spring:message code="ui.user.username"/>:</span>${user.username}</p>
    <p class="delimitato"><span><spring:message code="ui.user.groups.authorities"/>:</span></p>
    <p class="delimitato">
        <select size="20" class="list">
        <c:forEach var="group" items="${user.groups}" varStatus="status">
            <option>${group.name}</option>
            <c:forEach var="authority" items="${group.authorities}" varStatus="status">
                <option>-->${authority.name}</option>
            </c:forEach>
        </c:forEach>
        </select>
    </p>
    <p class="delimitato"><input type="Submit" value="<spring:message code="ui.group.list"/>" class="push"/>
    <input type="button" onclick="location.href = 'user.detail.page?sid=${user.id}'" value="<spring:message code="ui.action.edit"/>"/></p>
<c:if test="${mode == 'delete'}"><input type="button" onclick="location.href = 'authority.delete.age?sid=${authority.id}'" value="<spring:message code="authority.delete"/>"/></c:if><br/>
</form>