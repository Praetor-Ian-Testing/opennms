/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2006, 2008-2009 The OpenNMS Group, Inc.  All rights reserved.
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

public class SnmpInterfaceBuilder {

    private final OnmsSnmpInterface m_snmpIf;

    public SnmpInterfaceBuilder(OnmsSnmpInterface snmpIf) {
        m_snmpIf = snmpIf;
    }

    public SnmpInterfaceBuilder setIfSpeed(long ifSpeed) {
        m_snmpIf.setIfSpeed(new Long(ifSpeed));
        return this;
    }

    public SnmpInterfaceBuilder setIfDescr(String ifDescr) {
        m_snmpIf.setIfDescr(ifDescr);
        return this;
    }
    
    public SnmpInterfaceBuilder setIfName(String ifName) {
        m_snmpIf.setIfName(ifName);
        return this;
    }
    
    public SnmpInterfaceBuilder setIfType(Integer ifType) {
        m_snmpIf.setIfType(ifType);
        return this;
    }

    public OnmsEntity getSnmpInterface() {
        return m_snmpIf;
    }
    
    public SnmpInterfaceBuilder setIfOperStatus(Integer ifOperStatus) {
        m_snmpIf.setIfOperStatus(ifOperStatus);
        return this;
    }

    public SnmpInterfaceBuilder setCollectionEnabled(boolean collect) {
        m_snmpIf.setCollectionEnabled(collect);
        return this;
    }

    public SnmpInterfaceBuilder setPhysAddr(String physAddr) {
        m_snmpIf.setPhysAddr(physAddr);
        return this;
    }
}
