/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2007-2009 The OpenNMS Group, Inc.  All rights reserved.
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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Comparator;

import org.apache.log4j.Category;
import org.opennms.core.utils.InetAddressUtils;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.config.common.Range;

/**
 * Use this class to compare two castor generated Range objects from the SnmpConfig class.
 * @author <a href="mailto:david@opennms.org">David Hustace</a>
 */
public class RangeComparator implements Comparator<Range> {
    Category log = ThreadCategory.getInstance(getClass());

    public int compare(Range rng1, Range rng2) {
        long compared = 0;
        try {
            final long range1Begin = InetAddressUtils.toIpAddrLong(InetAddress.getByName((rng1).getBegin()));
            final long range2Begin = InetAddressUtils.toIpAddrLong(InetAddress.getByName((rng2).getBegin()));
            compared = range1Begin - range2Begin;
        } catch (UnknownHostException e) {
            log.error("compare: Exception sorting ranges.", e);
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
        return (int)compared;
    }
}