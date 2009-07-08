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

package org.opennms.netmgt.collectd;

import org.opennms.netmgt.model.OnmsSnmpInterface;

/**
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 */
public class SnmpIfData {

    private int m_nodeId;
    private boolean m_collectionEnabled;
    private int m_ifIndex;
    private int m_ifType;
    private String m_rrdLabel;
    private String m_ifAlias;

    public SnmpIfData(OnmsSnmpInterface snmpIface) {
        m_nodeId = nullSafeUnbox(snmpIface.getNode().getId(), -1);
        m_collectionEnabled = snmpIface.isCollectionEnabled();
        m_ifIndex = nullSafeUnbox(snmpIface.getIfIndex(), -1);
        m_ifType = nullSafeUnbox(snmpIface.getIfType(), -1);
        m_rrdLabel = snmpIface.computeLabelForRRD();
        m_ifAlias = snmpIface.getIfAlias();
    }
    
    int nullSafeUnbox(Integer num, int dflt) {
        return (num == null ? dflt : num.intValue());
    }

    public int getNodeId() {
        return m_nodeId;
    }

    public boolean isCollectionEnabled() {
        return m_collectionEnabled;
    }

    public int getIfIndex() {
        return m_ifIndex;
    }

    public int getIfType() {
        return m_ifType;
    }

    public String getLabelForRRD() {
        return m_rrdLabel;
    }

    public String getIfAlias() {
        return m_ifAlias;
    }

}
