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

package org.opennms.netmgt.model;


/**
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 */
public class StringPropertyAttribute implements OnmsAttribute {
    private String m_name;
    private String m_value;
    private OnmsResource m_resource;

    /**
     * @param name the name
     * @param value the value
     */
    public StringPropertyAttribute(String name, String value) {
        m_name = name;
        m_value = value;
    }

    /**
     * Get the name for this attribute.  This is the property name
     * from the properties file.
     * 
     * @see org.opennms.netmgt.model.OnmsAttribute#getName()
     */
    public String getName() {
        return m_name;
    }
    
    /**
     * Get the value for this attribute.  This is the property value
     * from the properties file.
     */
    public String getValue() {
        return m_value;
    }

    /**
     * @see org.opennms.netmgt.model.OnmsAttribute#getResource()
     */
    public OnmsResource getResource() {
        return m_resource;
    }

    /**
     * @see org.opennms.netmgt.model.OnmsAttribute#setResource(org.opennms.netmgt.model.OnmsResource)
     */
    public void setResource(OnmsResource resource) {
        m_resource = resource;
    }
}
