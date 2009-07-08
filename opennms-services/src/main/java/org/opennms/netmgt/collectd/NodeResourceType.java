/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2006-2008 The OpenNMS Group, Inc.  All rights reserved.
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

import java.util.Collection;
import java.util.Collections;

import org.opennms.netmgt.snmp.SnmpInstId;

public class NodeResourceType extends ResourceType {
    
    private NodeInfo m_nodeInfo;

    public NodeResourceType(CollectionAgent agent, OnmsSnmpCollection snmpCollection) {
        super(agent, snmpCollection);
        m_nodeInfo = new NodeInfo(this, agent);
    }

    public NodeInfo getNodeInfo() {
        return m_nodeInfo;
    }

    public SnmpCollectionResource findResource(SnmpInstId inst) {
        return m_nodeInfo;
    }

    public SnmpCollectionResource findAliasedResource(SnmpInstId inst, String ifAlias) {
    // This is here for completeness but it should not get called from here.
    // findResource should be called instead
        log().debug("findAliasedResource: Should not get called from NodeResourceType");
        return null;
    }

    public Collection<NodeInfo> getResources() {
        return Collections.singleton(m_nodeInfo);
    }

    @Override
    protected Collection<SnmpAttributeType> loadAttributeTypes() {
        return getCollection().getNodeAttributeTypes(getAgent());
    }

}
