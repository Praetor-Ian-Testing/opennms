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

import org.apache.log4j.Category;
import org.opennms.core.utils.ThreadCategory;

public class AliasedGroup extends AttributeGroup {
	
	AttributeGroup m_group;

	public AliasedGroup(CollectionResource resource, AttributeGroup group) {
		super(resource, group.getGroupType());
		m_group = group;
	}

	public void addAttribute(SnmpAttribute attr) {
		m_group.addAttribute(attr);
	}

	public boolean equals(Object obj) {
		return m_group.equals(obj);
	}

	public Collection<CollectionAttribute> getAttributes() {
		return m_group.getAttributes();
	}

	public AttributeGroupType getGroupType() {
		return m_group.getGroupType();
	}

	public String getName() {
		return m_group.getName();
	}

	public int hashCode() {
		return m_group.hashCode();
	}

	public boolean shouldPersist(ServiceParameters params) {
		return m_group.shouldPersist(params);
	}

	public String toString() {
		return m_group.toString();
	}
	
	Category log(){
		return ThreadCategory.getInstance(getClass());
	}

	public void visit(CollectionSetVisitor visitor) {
		visitor.visitGroup(this);
		
		for(CollectionAttribute attr : getAttributes()) {
		    AliasedAttribute aliased = new AliasedAttribute(getResource(), (SnmpAttribute)attr);
		    log().debug("visiting at aliased  = " + aliased);
		    aliased.visit(visitor);
		}
		
		visitor.completeGroup(this);
	}

}
