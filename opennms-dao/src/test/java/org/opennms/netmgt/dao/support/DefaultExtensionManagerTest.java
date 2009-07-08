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


package org.opennms.netmgt.dao.support;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.opennms.netmgt.dao.ExtensionManager;


/**
 * @author brozow
 *
 */
public class DefaultExtensionManagerTest {
    
    @Test
    public void testRegisterExtension() {
       
        DefaultExtensionManager mgr = new DefaultExtensionManager();
        
        mgr.registerExtension(mgr, ExtensionManager.class);
        
        Collection<ExtensionManager> extensions = mgr.findExtensions(ExtensionManager.class); 
        
        assertEquals(1, extensions.size());
        assertSame(mgr, extensions.iterator().next());
        
    }

    @Test
    public void testRegisterExtensionUsingActualClass() {
       
        DefaultExtensionManager mgr = new DefaultExtensionManager();
        
        mgr.registerExtension(mgr, DefaultExtensionManager.class);
        
        Collection<DefaultExtensionManager> extensions = mgr.findExtensions(DefaultExtensionManager.class); 
        
        assertEquals(1, extensions.size());
        assertSame(mgr, extensions.iterator().next());
        
    }

    @Test
    public void testRegisterExtensionUsingMultipleClasses() {
       
        DefaultExtensionManager mgr = new DefaultExtensionManager();
        
        mgr.registerExtension(mgr, ExtensionManager.class, DefaultExtensionManager.class);
        
        Collection<ExtensionManager> extensions = mgr.findExtensions(ExtensionManager.class); 
        Collection<DefaultExtensionManager> extensions2 = mgr.findExtensions(DefaultExtensionManager.class); 
        
        assertEquals(1, extensions.size());
        assertSame(mgr, extensions.iterator().next());

        assertEquals(1, extensions2.size());
        assertSame(mgr, extensions2.iterator().next());

    }

}
