/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2008-2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.protocols.wmi.wbem.jinterop;

import org.jinterop.dcom.impls.automation.IJIDispatch;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.common.JIException;
import org.opennms.protocols.wmi.WmiException;
import org.opennms.protocols.wmi.wbem.OnmsWbemMethod;

public class OnmsWbemMethodImpl implements OnmsWbemMethod {
        private IJIDispatch wbemMethodDispatch;

    public OnmsWbemMethodImpl(IJIDispatch wbemMethodDispatch) {
        this.wbemMethodDispatch = wbemMethodDispatch;
    }

    public String getWmiName()throws WmiException {
        try {
            JIVariant variant = wbemMethodDispatch.get("Name");

            return variant.getObjectAsString2();
        } catch (JIException e) {
            throw new WmiException("Unable to retrieve WbemMethod Name attribute: " + e.getMessage(), e);
        }
    }

    public String getWmiOrigin()throws WmiException {
        try {
            JIVariant variant = wbemMethodDispatch.get("Origin");

            return variant.getObjectAsString2();
        } catch (JIException e) {
            throw new WmiException("Unable to retrieve WbemMethod Origin attribute: " + e.getMessage(), e);
        }
    }

    public void getWmiOutParameters() {
        return; // TODO IMPLEEMNT THIS
    }

    public void getWmiInParameters() {
        return; // TODO IMPLEEMNT THIS
    }
}
