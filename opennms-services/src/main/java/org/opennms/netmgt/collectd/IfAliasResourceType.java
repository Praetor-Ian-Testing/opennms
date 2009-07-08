/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2006-2009 The OpenNMS Group, Inc.  All rights reserved.
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
import java.util.HashMap;
import java.util.Map;

import org.opennms.netmgt.snmp.SnmpInstId;

public class IfAliasResourceType extends ResourceType {

    private IfResourceType m_ifResourceType;
    private Map<Integer, AliasedResource> m_aliasedIfs = new HashMap<Integer, AliasedResource>();
    private ServiceParameters m_params;

    public IfAliasResourceType(CollectionAgent agent, OnmsSnmpCollection snmpCollection, ServiceParameters params, IfResourceType ifResourceType) {
        super(agent, snmpCollection);
        m_ifResourceType = ifResourceType;
        m_params = params;
    }

    public SnmpCollectionResource findResource(SnmpInstId inst) {
        // This is here for completeness but it should not get called here.
        // findAliasedResource should be called instead
        log().debug("findResource: Should not get called from IfAliasResourceType");
        return null;
    }
    public SnmpCollectionResource findAliasedResource(SnmpInstId inst, String ifAlias) {
        Integer key = new Integer(inst.toInt());
        AliasedResource resource = (AliasedResource) m_aliasedIfs.get(key);
        if (resource == null) {
            IfInfo ifInfo = (IfInfo)m_ifResourceType.findResource(inst);
            
            if(ifInfo == null) {
            	log().info("Not creating an aliased resource for ifInfo = null");
            } else {
                log().info("Creating an aliased resource for "+ifInfo);
            
                resource = new AliasedResource(this, m_params.getDomain(), ifInfo, m_params.getIfAliasComment(), ifAlias);
            
                m_aliasedIfs.put(key, resource);
            }
        }
        return resource;
    }
    
    @Override
    public SnmpInstId[] getCollectionInstances() {
        return m_ifResourceType.getCollectionInstances();
    }

    public Collection<SnmpAttributeType> loadAttributeTypes() {
        return getCollection().getAliasAttributeTypes(getAgent());
   }

    public Collection<AliasedResource> getResources() {
        return m_aliasedIfs.values();
    }
    

}
