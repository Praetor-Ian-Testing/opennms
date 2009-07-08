/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2004-2006, 2008 The OpenNMS Group, Inc.  All rights reserved.
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


package org.opennms.netmgt.config;


/**
 * @author brozow
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface PollOutagesConfig {
    
    /**
     * Return if the node represented by the nodeid is part of specified outage.
     * 
     * @param lnodeid
     *            the nodeid to be checked
     * @param outName
     *            the outage name
     * 
     * @return the node is part of the specified outage
     */
    public abstract boolean isNodeIdInOutage(long lnodeid, String outName);

    /**
     * Return if interfaces is part of specified outage.
     * 
     * @param linterface
     *            the interface to be looked up
     * @param outName
     *            the outage name
     * 
     * @return the interface is part of the specified outage
     */
    public abstract boolean isInterfaceInOutage(String linterface, String outName);

    /**
     * Return if current time is part of specified outage.
     * 
     * @param outName
     *            the outage name
     * 
     * @return true if current time is in outage
     */
    public abstract boolean isCurTimeInOutage(String outName);
    
    /**
     * Return if time is part of specified outage.
     * 
     * @param time
     *            the time in millis to look up
     * @param outName
     *            the outage name
     * 
     * @return true if time is in outage
     */
    public abstract boolean isTimeInOutage(long time, String outName);

    public abstract void update() throws Exception;
    

}
