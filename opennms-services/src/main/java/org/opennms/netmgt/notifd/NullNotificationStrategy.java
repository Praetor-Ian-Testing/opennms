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

package org.opennms.netmgt.notifd;

import java.util.List;

import org.apache.log4j.Category;
import org.opennms.core.utils.Argument;
import org.opennms.core.utils.ThreadCategory;

/**
 * Implements NotificationStragey pattern used to send NULL notifications The
 * idea here is to allow for user assignment of a notice with in the UI with
 * out an email sent. Typically the email will be sent to a shared email box.
 * 
 * @author <A HREF="mailto:Jason@Czerak.com">Jason Czerak</A>
 */
public class NullNotificationStrategy implements NotificationStrategy {
    public NullNotificationStrategy() {
    }

    public int send(List<Argument> arguments) {
        log().debug("In the NullNotification class.");
        return 0;
    }

    private Category log() {
        return ThreadCategory.getInstance(getClass());
    }

}
