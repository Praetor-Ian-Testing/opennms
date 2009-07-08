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

import java.util.HashSet;
import java.util.Set;

import org.opennms.netmgt.xml.event.Event;
import org.springframework.core.style.ToStringCreator;

/**
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 *
 */
public class Cause {
    
    public enum Type {
        POSSIBLE,
        IMPACT,
        ROOT
    }
    
    private Type m_type;
    private Long m_cause;
    private Event m_symptom;
    private Integer m_timerId;
    private Set<Cause> m_impacted = new HashSet<Cause>();

    public Cause(Type type, Long cause, Event symptom, Integer timerId) {
        m_type = type;
        m_cause = cause;
        m_symptom = symptom;
        m_timerId = timerId;
    }
    
    public Cause(Type type, Long cause, Event symptom) {
        this(type, cause, symptom, null);
    }
    
    public Type getType() {
        return m_type;
    }
    
    public void setType(Type type) {
        m_type = type;
    }

    public Long getCause() {
        return m_cause;
    }

    public void setCause(Long causeNodeId) {
        m_cause = causeNodeId;
    }

    public Event getSymptom() {
        return m_symptom;
    }

    public void setSymptom(Event symptomEvent) {
        m_symptom = symptomEvent;
    }
    
    public Set<Cause> getImpacted() {
        return m_impacted;
    }
    
    public void addImpacted(Cause cause) {
        m_impacted.add(cause);
    }
    
    public String toString() {
        return new ToStringCreator(this)
            .append("type", m_type)
            .append("cause", m_cause)
            .append("symptom", m_symptom)
            .append("impacted", m_impacted)
            .toString();
    }

    public Integer getTimerId() {
        return m_timerId;
    }

    public void setTimerId(Integer timerId) {
        m_timerId = timerId;
    }

}
