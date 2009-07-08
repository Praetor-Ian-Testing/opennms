/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2006-2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.netmgt.poller.remote;

import java.util.Map;

import org.opennms.netmgt.model.OnmsMonitoredService;

/**
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 */
public class PollConfiguration {
	
	private OnmsMonitoredService m_monitoredService;
	private OnmsPollModel m_pollModel;
	private Map<String,Object> m_monitorConfiguration;
	
	public PollConfiguration(OnmsMonitoredService monitoredService, Map<String,Object> monitorConfiguration, long pollInterval) {
		m_monitoredService = monitoredService;
		m_monitorConfiguration = monitorConfiguration;
		m_pollModel = new OnmsPollModel();
		m_pollModel.setPollInterval(pollInterval);
	}

	public OnmsMonitoredService getMonitoredService() {
		return m_monitoredService;
	}

	public void setMonitoredService(OnmsMonitoredService monitoredService) {
		m_monitoredService = monitoredService;
	}
	
	public Map<String,Object> getMonitorConfiguration() {
		return m_monitorConfiguration;
	}

	public OnmsPollModel getPollModel() {
		return m_pollModel;
	}

	public void setPollModel(OnmsPollModel pollModel) {
		m_pollModel = pollModel;
	}

	public String getId() {
		return m_monitoredService.getNodeId()+":"+m_monitoredService.getIpAddress()+":"+m_monitoredService.getServiceName();
	}

}
