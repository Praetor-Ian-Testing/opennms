/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.web.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public abstract class InFilter<T> extends MultiArgFilter<T> {
    
    public InFilter(String filterType, SQLType<T> type, String fieldName, String propertyName, T[] values){
        super(filterType, type, fieldName, propertyName, values);
    }
    
    @Override
    public Criterion getCriterion() {
        return Restrictions.in(getPropertyName(), getValuesAsList());
    }
    
    @Override
    public String getSQLTemplate() {
        StringBuilder buf = new StringBuilder(" ");
        buf.append(getSQLFieldName());
        buf.append(" IN (");
        T[] values = getValues();
        
        for(int i = 0; i < values.length; i++) {
            if (i != 0) {
                buf.append(", ");
            }
            buf.append("%s");
        }
        buf.append(") ");
        return buf.toString();
    }

}
