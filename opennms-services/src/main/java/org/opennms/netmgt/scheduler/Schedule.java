/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2005-2006, 2008-2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.netmgt.scheduler;

import java.util.Random;

import org.opennms.core.utils.ThreadCategory;


/**
 * Represents a Schedule 
 *
 * @author brozow
 */
public class Schedule {

	public static final Random random = new Random();
	
    private final ReadyRunnable m_schedulable;
    private final ScheduleInterval m_interval;
    private final ScheduleTimer m_timer;
    private volatile int m_currentExpirationCode;
    private volatile boolean m_scheduled = false;
	
    
    class ScheduleEntry implements ReadyRunnable {
        private final int m_expirationCode;

        public ScheduleEntry(int expirationCode) {
            m_expirationCode = expirationCode;
        }
        
        /**
         * @return
         */
        private boolean isExpired() {
            return m_expirationCode < m_currentExpirationCode;
        }
        
        public boolean isReady() {
            return isExpired() || m_schedulable.isReady();
        }

        public void run() {
            if (isExpired()) {
                ThreadCategory.getInstance(getClass()).debug("Schedule "+this+" expired.  No need to run.");
                return;
            }
            
            if (!m_interval.scheduledSuspension()) {
                try {
                    Schedule.this.run();
                } catch (PostponeNecessary e) {
				   // Chose a random number of seconds between 5 and 14 to wait before trying again
                    m_timer.schedule(random.nextInt(10)*1000+5000, this);
                    return;
                }
            }
                

            // if it is expired by the current run then don't reschedule
            if (isExpired()) {
                ThreadCategory.getInstance(getClass()).debug("Schedule "+this+" expired.  No need to reschedule.");
                return;
            }
            
            long interval = m_interval.getInterval();
            if (interval >= 0 && m_scheduled)
                m_timer.schedule(interval, this);

        }
        
        public String toString() { return "ScheduleEntry[expCode="+m_expirationCode+"] for "+m_schedulable; }
    }

    /**
     * @param interval
     * @param timer
     * @param m_schedulable
     * 
     */
    public Schedule(ReadyRunnable schedulable, ScheduleInterval interval, ScheduleTimer timer) {
        m_schedulable = schedulable;
        m_interval = interval;
        m_timer = timer;
        m_currentExpirationCode = 0;
    }

    /**
     * 
     */
    public void schedule() {
        m_scheduled = true;
        schedule(0);
    }

    private void schedule(long interval) {
        if (interval >= 0 && m_scheduled)
            m_timer.schedule(interval, new ScheduleEntry(++m_currentExpirationCode));
    }

    /**
     * 
     */
    public void run() {
        m_schedulable.run();
    }

    /**
     * 
     */
    public void adjustSchedule() {
        schedule(m_interval.getInterval());
    }

    /**
     * 
     */
    public void unschedule() {
        m_scheduled = false;
        m_currentExpirationCode++;
    }

}
