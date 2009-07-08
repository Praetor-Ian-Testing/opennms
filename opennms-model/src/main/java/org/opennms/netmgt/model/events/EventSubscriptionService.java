/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2008 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.netmgt.model.events;

import java.util.Collection;

public interface EventSubscriptionService {
    
    /**
     * Registers an event listener that is interested in all events
     */
    public void addEventListener(EventListener listener);

    /**
     * Registers an event listener interested in the UEIs in the passed list
     */
    public void addEventListener(EventListener listener, Collection<String> ueis);

    /**
     * Registers an event listener interested in the passed UEI
     */
    public void addEventListener(EventListener listener, String uei);

    /**
     * Removes a registered event listener
     */
    public void removeEventListener(EventListener listener);

    /**
     * Removes a registered event listener - the UEI list indicates the list of
     * events the listener is no more interested in
     */
    public void removeEventListener(EventListener listener, Collection<String> ueis);

    /**
     * Removes a registered event listener - the UEI indicates an event the
     * listener is no more interested in
     */
    public void removeEventListener(EventListener listener, String uei);


}
