/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2008 The OpenNMS Group, Inc.  All rights reserved.
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
package org.opennms.netmgt.provision.service.operations;

import java.util.List;

import org.opennms.netmgt.xml.event.Event;
import org.springframework.core.io.Resource;

public class NoOpProvisionMonitor implements ProvisionMonitor {
	public void beginProcessingOps(int deleteCount, int updateCount, int insertCount) {
	}

	public void finishProcessingOps() {
	}

	public void beginPreprocessingOps() {
	}

	public void finishPreprocessingOps() {
	}

	public void beginPreprocessing(ImportOperation oper) {
	}

	public void finishPreprocessing(ImportOperation oper) {
	}

	public void beginPersisting(ImportOperation oper) {
	}

	public void finishPersisting(ImportOperation oper) {
	}

	public void beginSendingEvents(ImportOperation oper, List<Event> events) {
	}

	public void finishSendingEvents(ImportOperation oper, List<Event> events) {
	}

	public void beginLoadingResource(Resource resource) {
	}

	public void finishLoadingResource(Resource resource) {
	}

	public void beginImporting() {
	}

	public void finishImporting() {
	}

	public void beginAuditNodes() {
	}

	public void finishAuditNodes() {
	}

	public void beginRelateNodes() {
	}

	public void finishRelateNodes() {
	}

}