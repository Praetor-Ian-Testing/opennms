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

package org.opennms.netmgt.ticketd;

import org.springframework.transaction.annotation.Transactional;

/**
 * OpenNMS Trouble Ticket API
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 * @author <a href="mailto:david@opennms.org">David Hustace</a>
 *
 */
@Transactional
public interface TicketerServiceLayer {
	
	/**
	 * Implement to manage creation of tickets through registered plugin.
	 * @param alarmId
	 */
	public void createTicketForAlarm(int alarmId);
	
	/**
	 * Implement to manage updating of tickets through registered plugin.
	 * @param alarmId
	 * @param ticketId
	 */
	public void updateTicketForAlarm(int alarmId, String ticketId);
	
	/**
	 * Implement to manage closing of tickets through registered plugin.
	 * @param alarmId
	 * @param ticketId
	 */
	public void closeTicketForAlarm(int alarmId, String ticketId);
	
	/**
	 * Implement to manage canceling of tickets through registered plugin.
	 * @param alarmId
	 * @param ticketId
	 */
	public void cancelTicketForAlarm(int alarmId, String ticketId);

}
