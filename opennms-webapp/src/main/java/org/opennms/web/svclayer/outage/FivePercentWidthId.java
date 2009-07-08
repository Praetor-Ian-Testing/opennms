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


package org.opennms.web.svclayer.outage;

import org.extremecomponents.table.bean.Column;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.interceptor.ColumnInterceptor;

/**
 * 
 * @author <a href="mailto:joed@opennms.org">Johan Edstrom</a>
 */
public class FivePercentWidthId implements ColumnInterceptor {

	public void addColumnAttributes(TableModel arg0, Column arg1) {
		String value = arg1.getPropertyValueAsString();
            
            //String style = "width:5%";
            arg1.setStyle(value);
            arg1.setFilterStyle(value);
            arg1.setWidth("100");
            arg1.addAttribute("width",Integer.toString(100)); 
            
        
	}

	public void modifyColumnAttributes(TableModel arg0, Column arg1) {
		// TODO Auto-generated method stub
		
	}
	
}

