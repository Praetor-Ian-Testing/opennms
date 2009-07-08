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

package org.opennms.netmgt.provision.service.lifecycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * LifeCycle
 *
 * @author brozow
 */
public class LifeCycle {
   
    private static final String[] OF_STRINGS = new String[0];
    
    private final String m_lifeCycleName;
    private final List<String> m_phases;
    
    public LifeCycle(String lifeCycleName) {
        this(lifeCycleName, new ArrayList<String>());
    }

    public LifeCycle(String lifeCycleName, List<String> phaseNames) {
        m_lifeCycleName = lifeCycleName;
        m_phases = phaseNames;
    }

    public String getLifeCycleName() {
        return m_lifeCycleName;
    }

    public LifeCycle addPhase(String phaseName) {
        m_phases.add(phaseName);
        return this;
    }

    public LifeCycle addPhases(String... phases) {
        m_phases.addAll(Arrays.asList(phases));
        return this;
    }

    public String[] getPhaseNames() {
        return m_phases.toArray(OF_STRINGS);
    }

}
