
//This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.

/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2008-2009 The OpenNMS Group, Inc.  All rights reserved.
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


//This program is free software; you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation; either version 2 of the License, or
//(at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.

//For more information contact:
//OpenNMS Licensing <license@opennms.org>
//http://www.opennms.org/
//http://www.opennms.com/

package org.opennms.netmgt.provision.support.ssh;

import java.net.InetAddress;
import java.util.Map;

import org.apache.regexp.RE;
import org.opennms.core.utils.ParameterMap;
import org.opennms.core.utils.TimeoutTracker;
import org.opennms.netmgt.model.PollStatus;

/**
 * This class is designed to be used by the service poller framework to test the
 * availability of SSH remote interfaces. The class
 * implements the ServiceMonitor interface that allows it to be used along with
 * other plug-ins by the service poller framework.
 * 
 * @author <a href="mailto:ranger@opennms.org">Benjamin Reed</a>
 * @author <a href="http://www.opennms.org/">OpenNMS</a>
 * 
 */

@Distributable
final public class SshMonitor extends IPv4Monitor {

    private static final int DEFAULT_RETRY = 0;
    public static final int DEFAULT_TIMEOUT = 3000;
    public static final int DEFAULT_PORT = 22;

    /**
     * Poll an {@link InetAddress} for SSH availability.
     * 
     * During the poll an attempt is made to connect on the specified port. If
     * the connection request is successful, the banner line generated by the
     * interface is parsed and if the banner text indicates that we are talking
     * to Provided that the interface's response is valid we mark the poll status
     * as available and return.
     * 
     * @param address the address to poll
     * @param parameters  the package parameters (timeout, retry, etc...) to be used for this poll
     * 
     * @return a {@link PollStatus} status object
     */
    @SuppressWarnings("unchecked")
    public PollStatus poll(InetAddress address, Map parameters) {

        TimeoutTracker tracker = new TimeoutTracker(parameters, DEFAULT_RETRY, DEFAULT_TIMEOUT);

        int port = ParameterMap.getKeyedInteger(parameters, "port", DEFAULT_PORT);
        String banner = ParameterMap.getKeyedString(parameters, "banner", null);
        String match = ParameterMap.getKeyedString(parameters, "match", null);
        String clientBanner = ParameterMap.getKeyedString(parameters, "client-banner", Ssh.DEFAULT_CLIENT_BANNER);
        PollStatus ps = PollStatus.unavailable();
        
        Ssh ssh = new Ssh(address, port, tracker.getConnectionTimeout());
        ssh.setClientBanner(clientBanner);

        RE regex = null;
        if (match == null && (banner == null || banner.equals("*"))) {
            regex = null;
        } else if (match != null) {
            regex = new RE(match);
        } else if (banner != null) {
            regex = new RE(banner);
        }

        for (tracker.reset(); tracker.shouldRetry() && !ps.isAvailable(); tracker.nextAttempt()) {
            try {
                ps = ssh.poll(tracker);
            } catch (InsufficientParametersException e) {
                log().error(e);
                break;
            }

            if (!ps.isAvailable()) {
                // not able to connect, retry
                continue;
            }

            // If banner matching string is null or wildcard ("*") then we
            // only need to test connectivity and we've got that!

            if (regex == null) {
                return ps;
            } else {
                String response = ssh.getServerBanner();

                if (regex.match(response)) {
                    if (log().isDebugEnabled()) {
                        log().debug("isServer: matching response=" + response);
                    }
                    return ps;
                } else {
                    // Got a response but it didn't match... no need to attempt
                    // retries
                    if (log().isDebugEnabled()) {
                        log().debug("isServer: NON-matching response=" + response);
                    }
                    return PollStatus.unavailable("server responded, but banner did not match '" + banner + "'");
                }
            }
        }
        return ps;        
    }

    /**
     * Poll the specified address for service availability.
     * 
     * @see #poll(InetAddress, Map)
     * 
     * @param svc the {@link MonitoredService} service object which defines what address to poll, etc.
     * @param parameters the package parameters (timeout, retry, etc.) to be used for this poll
     *
     * @return the availability of the interface
     */
    
    @SuppressWarnings("unchecked")
    public PollStatus poll(MonitoredService svc, Map parameters) {
        NetworkInterface iface = svc.getNetInterface();
        if (iface.getType() != NetworkInterface.TYPE_IPV4) {
            throw new NetworkInterfaceNotSupportedException("Unsupported interface type, only TYPE_IPV4 currently supported");
        }
        InetAddress address = (InetAddress) iface.getAddress();

        return poll(address, parameters);
    }

}
