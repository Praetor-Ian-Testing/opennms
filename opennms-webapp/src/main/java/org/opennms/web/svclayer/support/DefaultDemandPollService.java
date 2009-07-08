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

package org.opennms.web.svclayer.support;

import java.util.Date;

import org.opennms.netmgt.dao.DemandPollDao;
import org.opennms.netmgt.dao.MonitoredServiceDao;
import org.opennms.netmgt.model.DemandPoll;
import org.opennms.netmgt.model.OnmsMonitoredService;
import org.opennms.web.services.PollerService;
import org.opennms.web.svclayer.DemandPollService;

/**
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 * @author <a href="mailto:david@opennms.org">David Hustace</a>
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 */
public class DefaultDemandPollService implements DemandPollService {
	
	private PollerService m_pollerService;
	private DemandPollDao m_demandPollDao;
	private MonitoredServiceDao m_monitoredServiceDao;
	
	public void setDemandPollDao(DemandPollDao demandPollDao) {
		m_demandPollDao = demandPollDao;
	}
	
	public void setPollerAPI(PollerService pollerAPI) {
		m_pollerService = pollerAPI;
	}
	
	public void setMonitoredServiceDao(MonitoredServiceDao monitoredServiceDao) {
		m_monitoredServiceDao = monitoredServiceDao;
	}
	
	public DemandPoll pollMonitoredService(int nodeId, String ipAddr, int ifIndex, int serviceId) {
		DemandPoll demandPoll = new DemandPoll();
		demandPoll.setRequestTime(new Date());
		
		m_demandPollDao.save(demandPoll);
		
		OnmsMonitoredService monSvc = m_monitoredServiceDao.get(nodeId, ipAddr, ifIndex, serviceId);
		
		if (monSvc == null) {
			throw new RuntimeException("Service doesn't exist: "+monSvc);
		}
		m_pollerService.poll(monSvc, demandPoll.getId());
		return demandPoll;
	}

	public DemandPoll getUpdatedResults(int pollId) {
		return m_demandPollDao.get(pollId);
	}

}
