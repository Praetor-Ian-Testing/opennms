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
import org.jinterop.dcom.impls.automation.IJIEnumVariant;
import org.jinterop.dcom.impls.JIObjectFactory;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.common.JIException;
import org.opennms.protocols.wmi.WmiException;
import org.opennms.protocols.wmi.wbem.OnmsWbemObject;
import org.opennms.protocols.wmi.wbem.OnmsWbemObjectSet;

public class OnmsWbemObjectSetImpl implements OnmsWbemObjectSet {
    private IJIDispatch wbemObjectSet;

    public OnmsWbemObjectSetImpl(IJIDispatch wbemObjectSet) {
        this.wbemObjectSet = wbemObjectSet;
    }

    public Integer count() throws WmiException {
        try {
            JIVariant Count = wbemObjectSet.get("Count");
            return Count.getObjectAsInt();
        } catch (JIException e) {
            throw new WmiException("Retrieving Count failed: " + e.getMessage(), e);
        }
    }

    public OnmsWbemObject get(Integer idx) throws WmiException {
        try {
            IJIComObject enumComObject = wbemObjectSet.get("_NewEnum").getObjectAsComObject();
            IJIEnumVariant enumVariant =
                    (IJIEnumVariant) JIObjectFactory.narrowObject(
                            enumComObject.queryInterface(IJIEnumVariant.IID));
            OnmsWbemObject wbemObj = null;
            IJIDispatch wbemObject_dispatch = null;
            for (int i = 0; i < (idx+1); i++) {
                Object[] values = enumVariant.next(1);
                JIArray array = (JIArray)values[0];
                Object[] arrayObj = (Object[])array.getArrayInstance();
                for(int j = 0; j < arrayObj.length; j++)
                {
                    wbemObject_dispatch = (IJIDispatch)JIObjectFactory.narrowObject(((JIVariant)arrayObj[j]).getObjectAsComObject());
                }
            }

            wbemObj = new OnmsWbemObjectImpl(wbemObject_dispatch);
            return wbemObj;
        } catch(JIException e) {
            throw new WmiException("Failed to enumerate WbemObject variant: " + e.getMessage(), e);
        }
    }
}
