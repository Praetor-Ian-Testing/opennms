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

package org.opennms.web.map.db.datasources;




/**
 * The interface DataSource provide a way to get data named like the input.
 * 
 * @author mmigliore
 * @author <a href="mailto:antonio@opennms.it">Antonio Russo</a>
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 */
public interface DataSourceInterface {

	/**
	 * Gets the status of the element with id in input using params in input 
	 * @param velem
	 * @param params
	 * @return the status of velem, -1 if no status is found for velem
	 */
	public String getStatus(Object id);
	/**
	 * Gets the severity of the element with id in input using params in input 
	 * @param velem
	 * @param params
	 * @return the severity of velem, -1 if no severity is found for velem
	 */
	public String getSeverity(Object id);
	/**
	 * Gets the availability of the element with id in input using params in input 
	 * @param velem
	 * @param params
	 * @return the availability of velem, -1 if no availability is found for velem
	 */
	public double getAvailability(Object id);

}
