/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2007-2009 The OpenNMS Group, Inc.  All rights reserved.
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


package org.opennms.netmgt.poller.monitors;

import java.net.InetAddress;
import java.util.Map;

import org.opennms.netmgt.model.PollStatus;
import org.opennms.netmgt.poller.Distributable;
import org.opennms.netmgt.poller.MonitoredService;

/**
 * <P>
 * This class is designed to be used by the service poller framework to test the
 * availability of SSH remote interfaces. The class implements the ServiceMonitor
 * interface that allows it to be used along with other plug-ins by the service
 * poller framework.
 * </P>
 * <P>
 * This plugin is just an exact copy of the {@link SshMonitor} now.
 * </P>
 * 
 * @deprecated use {@link SshMonitor} instead
 * @author <a href="mailto:ranger@opennms.org">Benjamin Reed</a>
 * @author <a href="http://www.opennms.org/">OpenNMS</a>
 * 
 */

@Distributable
final public class JschSshMonitor extends IPv4Monitor {
    private SshMonitor m_monitor;
    
    public JschSshMonitor() {
        m_monitor = new SshMonitor();
    }
    
    public PollStatus poll(InetAddress address, Map<String, Object> parameters) {
        return m_monitor.poll(address, parameters);
    }

    public PollStatus poll(MonitoredService svc, Map<String, Object> parameters) {
        return m_monitor.poll(svc, parameters);
    }

}
