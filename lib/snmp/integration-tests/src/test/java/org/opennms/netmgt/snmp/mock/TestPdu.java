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

package org.opennms.netmgt.snmp.mock;


import org.opennms.netmgt.snmp.SnmpObjId;

public abstract class TestPdu {

    private TestVarBindList m_varBindList;

    public TestPdu() {
        m_varBindList = new TestVarBindList();
    }

    public static ResponsePdu getResponse() {
        return new ResponsePdu();
    }

    public static GetPdu getGet() {
        return new GetPdu();
    }

    
    public static NextPdu getNext() {
        return new NextPdu();
    }

    public static BulkPdu getBulk() {
        return new BulkPdu();
    }
    
    public TestVarBindList getVarBinds() {
        return m_varBindList;
    }

    void setVarBinds(TestVarBindList varBinds) {
        m_varBindList = new TestVarBindList(varBinds);
    }

    public void addVarBind(SnmpObjId objId) {
        m_varBindList.addVarBind(objId);
    }

    public void addVarBind(String oid, int inst) {
        addVarBind(SnmpObjId.get(oid, ""+inst));
    }

    public int size() {
        return m_varBindList.size();
    }

    public TestVarBind getVarBindAt(int i) {
        return m_varBindList.getVarBindAt(i);
    }

    public void addVarBind(String oid) {
        addVarBind(SnmpObjId.get(oid));
    }

    public void addVarBind(TestVarBind newVarBind) {
        m_varBindList.add(newVarBind);
    }



}