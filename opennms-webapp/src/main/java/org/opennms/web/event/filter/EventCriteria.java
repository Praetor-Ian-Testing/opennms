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

package org.opennms.web.event.filter;

import java.util.Arrays;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.opennms.web.event.AcknowledgeType;
import org.opennms.web.event.SortStyle;
import org.opennms.web.filter.Filter;

public class EventCriteria {
    
    public static interface EventCriteriaVisitor<E extends Exception>{
        public void visitAckType(AcknowledgeType ackType) throws E;
        public void visitFilter(Filter filter) throws E;
        public void visitSortStyle(SortStyle sortStyle) throws E;
        public void visitLimit(int limit, int offset) throws E;
    }
    
    public static class BaseEventCriteriaVisitor<E extends Exception> implements EventCriteriaVisitor<E>{
        public void visitAckType(AcknowledgeType ackType) throws E { }
        public void visitFilter(Filter filter) throws E { }
        public void visitLimit(int limit, int offset) throws E { }
        public void visitSortStyle(SortStyle sortStyle) throws E { }
        
    }
    
    Filter[] m_filters = null;
    SortStyle m_sortStyle = SortStyle.TIME;
    AcknowledgeType m_ackType = AcknowledgeType.UNACKNOWLEDGED;
    int m_offset = -1;
    int m_limit = -1;
    
    public EventCriteria(Filter... filters){
        this(filters, null, null, -1, -1);
    }
    
    public EventCriteria(AcknowledgeType ackType, Filter[] filters) {
        this(filters, null, ackType, -1, -1);
    }

    public EventCriteria(Filter[] filters, SortStyle sortStyle, AcknowledgeType ackType, int limit, int offset){
        m_filters = filters;
        m_sortStyle = sortStyle;
        m_ackType = ackType;
        m_limit = limit;
        m_offset = offset;
    }
    
    public <E extends Exception> void visit(EventCriteriaVisitor<E> visitor) throws E{
        if(m_ackType != null){
            visitor.visitAckType(m_ackType);
        }
        for(Filter filter : m_filters){
            visitor.visitFilter(filter);
        }
        if(m_sortStyle != null){
            visitor.visitSortStyle(m_sortStyle);
        }
        if(m_limit > 0 && m_offset > -1){
            visitor.visitLimit(m_limit, m_offset);
        }
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
        .append("filters", Arrays.asList(m_filters))
            .append("sortStyle", m_sortStyle)
            .append("ackType", m_ackType)
            .append("limit", m_limit)
            .append("offset", m_offset)
            .toString();
    }
}
