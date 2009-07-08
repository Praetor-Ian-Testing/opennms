/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2006-2008 The OpenNMS Group, Inc.  All rights reserved.
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

/**
 * 
 */
package org.opennms.netmgt.importer.operations;

import java.util.List;

import org.opennms.netmgt.capsd.EventUtils;
import org.opennms.netmgt.model.AbstractEntityVisitor;
import org.opennms.netmgt.model.OnmsIpInterface;
import org.opennms.netmgt.model.OnmsMonitoredService;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.xml.event.Event;

public final class DeleteEventVisitor extends AbstractEntityVisitor {
	private final List<Event> m_events;

	DeleteEventVisitor(List<Event> events) {
		m_events = events;
	}

	public void visitMonitoredServiceComplete(OnmsMonitoredService monSvc) {
		m_events.add(EventUtils.createServiceDeletedEvent("ModelImporter", monSvc.getNodeId().longValue(), monSvc.getIpAddress(), monSvc.getServiceType().getName(), -1L));
	}

	public void visitIpInterfaceComplete(OnmsIpInterface iface) {
		m_events.add(EventUtils.createInterfaceDeletedEvent("ModelImporter", iface.getNode().getId().longValue(), iface.getIpAddress(), -1L));
	}

	public void visitNodeComplete(OnmsNode node) {
		m_events.add(EventUtils.createNodeDeletedEvent("ModelImporter", node.getId().intValue(), node.getLabel(), node.getLabel(), -1L));
	}
}