/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2005-2008 The OpenNMS Group, Inc.  All rights reserved.
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

import java.net.Socket;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.opennms.netmgt.capsd.ConnectionConfig;
import org.opennms.netmgt.utils.RelaxedX509TrustManager;

/**
 * <P>
 * This class is designed to be used by the capabilities daemon to test for the
 * existance of an HTTPS server on remote interfaces. The class implements the
 * Plugin interface that allows it to be used along with other plugins by the
 * daemon.
 * 
 * This plugin generates a HTTP GET request and checks the return code returned
 * by the remote host to determine if it supports the protocol.
 * 
 * The remote host's response will be deemed valid if the return code falls in
 * the 100 to 599 range (inclusive).
 * 
 * This is based on the following information from RFC 1945 (HTTP 1.0) HTTP 1.0
 * GET return codes: 1xx: Informational - Not used, future use 2xx: Success 3xx:
 * Redirection 4xx: Client error 5xx: Server error
 * </P>
 * 
 * This plugin generates a HTTP GET request and checks the return code returned
 * by the remote host to determine if it supports the protocol.
 * 
 * The remote host's response will be deemed valid if the return code falls in
 * the 100 to 599 range (inclusive).
 * 
 * This is based on the following information from RFC 1945 (HTTP 1.0) HTTP 1.0
 * GET return codes: 1xx: Informational - Not used, future use 2xx: Success 3xx:
 * Redirection 4xx: Client error 5xx: Server error
 * </P>
 * 
 * @author <A HREF="mailto:jason@opennms.org">Jason </A>
 * @author <A HREF="http://www.opennms.org">OpenNMS </A>
 * 
 * 
 */
public class HttpsPlugin extends HttpPlugin {

    /**
     * <P>
     * The default ports on which the host is checked to see if it supports
     * HTTP.
     * </P>
     */
    private static final int[] DEFAULT_PORTS = { 443 };

    /**
     * @param protocol
     * @param defaultPort
     * @param defaultTimeout
     * @param defaultRetries
     */
    public HttpsPlugin() {
        super("HTTPS", true, "GET / HTTP/1.0\r\n\r\n", "HTTP/", DEFAULT_PORTS);
    }

    protected Socket wrapSocket(Socket socket, ConnectionConfig config) throws Exception {
        Socket sslSocket;

        // set up the certificate validation. USING THIS SCHEME WILL ACCEPT ALL
        // CERTIFICATES
        SSLSocketFactory sslSF = null;

        TrustManager[] tm = { new RelaxedX509TrustManager() };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, tm, new java.security.SecureRandom());
        sslSF = sslContext.getSocketFactory();
        sslSocket = sslSF.createSocket(socket, config.getInetAddress().getHostAddress(), config.getPort(), true);
        return sslSocket;
    }

}
