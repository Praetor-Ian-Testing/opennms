/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2005-2006, 2008 The OpenNMS Group, Inc.  All rights reserved.
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


package org.opennms.netmgt.capsd.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.opennms.netmgt.capsd.AbstractTcpPlugin;
import org.opennms.netmgt.capsd.ConnectionConfig;

/**
 * <P>
 * This class is designed to be used by the capabilities daemon to test for the
 * existance of an Citrix server on remote interfaces. The class implements the
 * Plugin interface that allows it to be used along with other plugins by the
 * daemon.
 * </P>
 * 
 * @author <A HREF="mailto:jason@opennms.org">Jason </A>
 * @author <A HREF="http://www.opennsm.org">OpenNMS </A>
 * 
 * 
 */
public final class CitrixPlugin extends AbstractTcpPlugin {

    /**
     * <P>
     * The default port on which the host is checked to see if it supports
     * Citrix.
     * </P>
     */
    private static final int DEFAULT_PORT = 1494;

    /**
     * Default number of retries for Citrix requests.
     */
    private final static int DEFAULT_RETRY = 0;

    /**
     * Default timeout (in milliseconds) for Citrix requests.
     */
    private final static int DEFAULT_TIMEOUT = 5000; // in milliseconds

    /**
     * <P>
     * The capability name of the plugin.
     * </P>
     */
    private static final String PROTOCOL_NAME = "Citrix";

    /**
     */
    public CitrixPlugin() {
        super(PROTOCOL_NAME, DEFAULT_PORT, DEFAULT_TIMEOUT, DEFAULT_RETRY);
    }

    /**
     * @param socket
     * @param isAServer
     * @return
     * @throws IOException
     */
    protected boolean checkProtocol(Socket socket, ConnectionConfig config) throws IOException {
        boolean isAServer = false;
        // Allocate a line reader
        //
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuffer buffer = new StringBuffer();

        // FIXME: This doesn't seem right. This loops until an exception occurs.
        // Should at least check for EOF
        while (!isAServer) {
            buffer.append((char) reader.read());
            if (buffer.toString().indexOf("ICA") > -1) {
                isAServer = true;
            }
        }
        return isAServer;
    }
}
