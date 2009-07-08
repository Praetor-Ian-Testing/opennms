/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2004-2006, 2008-2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.netmgt.notifd.mock;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.opennms.netmgt.config.NotifdConfigManager;
/**
 * @author David Hustace <david@opennms.org>
 */
public class MockNotifdConfigManager extends NotifdConfigManager {
        
    private String m_nextNotifIdSql;
    private String m_nextUserNotifIdSql;

    /*
     * init the mock config
     */
    
    
    /**
     * @param configString
     * @throws IOException
     * @throws ValidationException
     * @throws MarshalException
     */
    @SuppressWarnings("deprecation")
    public MockNotifdConfigManager(String configString) throws MarshalException, ValidationException, IOException {
        Reader reader = new StringReader(configString);
        parseXml(reader);
        reader.close();
    }

    /* (non-Javadoc)
     * @see org.opennms.netmgt.config.NotifdConfigManager#update()
     */
    protected void update() throws IOException, MarshalException, ValidationException {

    }

    /* (non-Javadoc)
     * @see org.opennms.netmgt.config.NotifdConfigManager#saveXml(java.lang.String)
     */
    protected void saveXml(String xml) throws IOException {
    }

    /* (non-Javadoc)
     * @see org.opennms.netmgt.config.NotifdConfigManager#getNextNotifIdSql()
     */
    public String getNextNotifIdSql() throws IOException, MarshalException,
            ValidationException {
        return m_nextNotifIdSql;
    }
    
    public void setNextNotifIdSql(String sql) {
        m_nextNotifIdSql = sql;
    }

    public String getNextUserNotifIdSql() throws IOException, MarshalException, ValidationException {
        // TODO Auto-generated method stub
        return m_nextUserNotifIdSql;
    }

    public void setNextUserNotifIdSql(String sql) {
        m_nextUserNotifIdSql = sql;
    }
    
}
