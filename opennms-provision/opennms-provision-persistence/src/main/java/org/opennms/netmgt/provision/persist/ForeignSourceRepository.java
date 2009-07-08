/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2008-2009 The OpenNMS Group, Inc.  All rights reserved.
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


package org.opennms.netmgt.provision.persist;

import java.net.URL;
import java.util.Set;

import org.opennms.netmgt.provision.persist.foreignsource.ForeignSource;
import org.opennms.netmgt.provision.persist.requisition.Requisition;
import org.springframework.core.io.Resource;

/**
 * @author <a href="mailto:ranger@opennms.org">Benjamin Reed</a>
 * @author <a href="mailto:brozow@opennms.org">Matt Brozowski</a>
 *
 */

public interface ForeignSourceRepository {

    public Set<String> getActiveForeignSourceNames();
    
    public int getForeignSourceCount() throws ForeignSourceRepositoryException;
    public Set<ForeignSource> getForeignSources() throws ForeignSourceRepositoryException;
    public ForeignSource getForeignSource(String foreignSourceName) throws ForeignSourceRepositoryException;
    public void save(ForeignSource foreignSource) throws ForeignSourceRepositoryException;
    public void delete(ForeignSource foreignSource) throws ForeignSourceRepositoryException;

    public ForeignSource getDefaultForeignSource() throws ForeignSourceRepositoryException;
    public void putDefaultForeignSource(ForeignSource foreignSource) throws ForeignSourceRepositoryException;
    public void resetDefaultForeignSource() throws ForeignSourceRepositoryException;

    public Requisition importResourceRequisition(Resource resource) throws ForeignSourceRepositoryException;
    public Set<Requisition> getRequisitions() throws ForeignSourceRepositoryException;
    public Requisition getRequisition(String foreignSourceName) throws ForeignSourceRepositoryException;
    public Requisition getRequisition(ForeignSource foreignSource) throws ForeignSourceRepositoryException;
    public URL getRequisitionURL(String foreignSource);
    public void save(Requisition requisition) throws ForeignSourceRepositoryException;
    public void delete(Requisition requisition) throws ForeignSourceRepositoryException;
    
    public OnmsNodeRequisition getNodeRequisition(String foreignSource, String foreignId) throws ForeignSourceRepositoryException;
    
}
