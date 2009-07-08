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


package org.opennms.netmgt.correlation.drools;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 *
 */
public class Affliction {
    Long m_nodeid;
    String m_ipAddr;
    String m_svcName;
    
    public static enum Type {
        UNDECIDED,
        ISOLATED,
        WIDE_SPREAD
    }
    
    List<Integer> m_reporters = new ArrayList<Integer>();
    private Type m_type  = Type.UNDECIDED;
    
    public Affliction(Long nodeId, String ipAddr, String svcName, Integer reporter) {
        m_nodeid = nodeId;
        m_ipAddr = ipAddr;
        m_svcName = svcName;
        m_reporters.add(reporter);
    }

    public String getIpAddr() {
        return m_ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        m_ipAddr = ipAddr;
    }

    public Long getNodeid() {
        return m_nodeid;
    }

    public void setNodeid(Long nodeid) {
        m_nodeid = nodeid;
    }

    public List<Integer> getReporters() {
        return m_reporters;
    }

    public void setReporters(List<Integer> reporters) {
        m_reporters = reporters;
    }

    public String getSvcName() {
        return m_svcName;
    }

    public void setSvcName(String svcName) {
        m_svcName = svcName;
    }
    
    public int getReporterCount() {
        return m_reporters.size();
    }
    
    public void addReporter(Integer reporter) {
        m_reporters.add( reporter );
    }
    
    public void removeReporter(Integer reporter) {
        m_reporters.remove(reporter);
    }
    
    public Type getType() {
        return m_type;
    }
    
    public void setType(Type type) {
        m_type = type;
    }
    
}
