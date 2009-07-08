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
<c:if test="${mode == 'delete'}">
<c:choose>
<c:when test="${group.emptyUsers}"><font color="red"><spring:message code="group.delete.no"/></font></c:when>
<c:otherwise><font color="red"><spring:message code="group.delete.confirm"/></font></c:otherwise>
</c:choose>
</c:if>
<form action="group.list.page" method="post">
<p class="delimitato"><span><spring:message code="ui.group.name"/>:</span>${group.name}</p>
<input type="Submit" value="<spring:message code="group.list"/>" class="push"/>
<input type="button" onclick="location.href = 'group.edit.page?gid=${group.id}'" value="<spring:message code="ui.action.edit"/>"/>
<input type="button" onclick="location.href = 'group.items.page?gid=${group.id}'" value="<spring:message code="ui.action.edit.groups"/>"/>
<c:if test="${mode == 'delete'}">
<c:if test="${!group.emptyUsers}"><input type="button" onclick="location.href = 'group.delete.page?gid=${group.id}'" value="<spring:message code="group.delete"/>"/></c:if>
</c:if><br/>
</form>