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

package org.opennms.netmgt.provision.detector.simple.request;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author Donald Desloge
 *
 */
public class NrpeRequest {
    
    public static final NrpeRequest Null = new NrpeRequest(null) {
        @Override
        public void send(OutputStream out) throws IOException {
        }
    };
    
    private final byte[] m_command;
    
    public NrpeRequest(byte[] command) {
        if (command != null) {
            m_command = command.clone();
        } else {
            m_command = null;
        }
    }

    /**
     * @param socket
     * @throws IOException 
     */
    public void send(OutputStream out) throws IOException {
        out.write( m_command);
    }
    
    public String toString() {
        return String.format("Request: %s", Arrays.toString(m_command));
    }
}
