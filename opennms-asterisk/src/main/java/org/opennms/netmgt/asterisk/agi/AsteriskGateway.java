/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.netmgt.asterisk.agi;

import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.fastagi.ClassNameMappingStrategy;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.opennms.netmgt.daemon.AbstractServiceDaemon;
import org.opennms.netmgt.daemon.SpringServiceDaemon;

/**
 * @author <A HREF="mailto:jeffg@opennms.org">Jeff Gehlbach</A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS</A>
 *
 */
public class AsteriskGateway extends AbstractServiceDaemon implements SpringServiceDaemon {

    AgiServerThread m_agiServerThread;
    private int m_port = 4573;
    private int m_maxPoolSize = 10;
    
    protected AsteriskGateway() {
        super("OpenNMS.AsteriskGateway");
    }

    @Override
    protected void onInit() {
        int port = Integer.getInteger("org.opennms.netmgt.asterisk.agi.listenPort", m_port);
        int maxPoolSize = Integer.getInteger("org.opennms.netmgt.asterisk.agi.maxPoolSize", m_maxPoolSize);
        
        DefaultAgiServer agiServer = new DefaultAgiServer(new ClassNameMappingStrategy(false));
        
        agiServer.setPort(port);
        agiServer.setMaximumPoolSize(maxPoolSize);
        
        m_agiServerThread = new AgiServerThread(agiServer);
        
        // This is the default, but be explicit
        m_agiServerThread.setDaemon(true);
    }
    
    @Override
    protected void onStart() {
        m_agiServerThread.startup();
    }
    
    @Override
    protected void onStop() {
        m_agiServerThread.shutdown();
    }

}
