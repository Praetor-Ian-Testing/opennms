/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2007-2008 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.netmgt.mock;

import java.io.File;


/**
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 */
public class OpenNMSIntegrationTestCaseTest extends OpenNMSIntegrationTestCase {
    
    
    @Override
    protected String[] getConfigLocations() {
        return new String[] { 
                "classpath:META-INF/opennms/applicationContext-dao.xml",
                "classpath:META-INF/opennms/applicationContext-daemon.xml" 
        };
    }

    public void testHomeDirCreated() {

        String homePath = System.getProperty("opennms.home");
        assertNotNull(homePath);
        
        assertTrue(new File(homePath).exists());
        
    }
    
    public void testEtcDirExists() {
        
        String homePath = System.getProperty("opennms.home");
        assertNotNull(homePath);
        
        File homeDir = new File(homePath);
        File etcDir = new File(homeDir, "etc");
        
        assertTrue(etcDir.exists());
    }



}
