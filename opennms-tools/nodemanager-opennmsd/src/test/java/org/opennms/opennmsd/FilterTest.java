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

package org.opennms.opennmsd;

import junit.framework.TestCase;

public class FilterTest extends TestCase {
    
    Filter m_filter;
    NNMEvent m_event;
    
    public void setUp() {
        m_filter = new Filter();
        m_event = MockNNMEvent.createEvent("Category", "Severity", "name", "1.1.1.1");
        
    }
    
    public void testMatchCategory() {
        m_filter.setCategoryMatcher("^.*Category$");
        assertFilterMatches();
    }
    
    public void testMatchCategoryPartial() {
        m_filter.setCategoryMatcher("gor");
        assertFilterMatches();
    }
    
    public void testMismatchCategory() {
        m_filter.setCategoryMatcher("nomatch");
        assertFilterDoesntMatch();
    }

    public void testMatchSeverity() {
        m_filter.setSeverityMatcher("ever");
        assertFilterMatches();
    }

    public void testMismatchSeverity() {
        m_filter.setSeverityMatcher("nomatch");
        assertFilterDoesntMatch();
    }

    public void testMatchName() {
        m_filter.setEventNameMatcher("am");
        assertFilterMatches();
    }

    public void testMismatchName() {
        m_filter.setEventNameMatcher("nomatch");
        assertFilterDoesntMatch();
    }

    public void testMatchAddress() {
        m_filter.setAddressMatchSpec("1.1-3.*.1,3");
        assertFilterMatches();
    }

    public void testMismatchAddress() {
        m_filter.setAddressMatchSpec("192.168.*.*");
        assertFilterDoesntMatch();
    }
    
    public void testCategoryAndAddress() {
        m_filter.setCategoryMatcher("ategory");
        m_filter.setAddressMatchSpec("1.*.*.*");
        
        assertFilterMatches();
    }

    public void testCategoryButNotAddress() {
        m_filter.setCategoryMatcher("ategory");
        m_filter.setAddressMatchSpec("192.*.*.*");
        
        assertFilterDoesntMatch();
    }

    private void assertFilterMatches() {
        assertTrue("Expected filter to match: "+m_filter, m_filter.matches(m_event));
    }

    private void assertFilterDoesntMatch() {
        assertFalse("Expected filter NOT to match: "+m_filter, m_filter.matches(m_event));
    }

}
