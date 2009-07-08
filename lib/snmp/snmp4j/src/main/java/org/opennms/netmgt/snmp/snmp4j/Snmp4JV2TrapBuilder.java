/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2005-2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.netmgt.snmp.snmp4j;

import org.opennms.netmgt.snmp.SnmpAgentConfig;
import org.opennms.netmgt.snmp.SnmpObjId;
import org.opennms.netmgt.snmp.SnmpTrapBuilder;
import org.opennms.netmgt.snmp.SnmpValue;
import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

public class Snmp4JV2TrapBuilder implements SnmpTrapBuilder {
    private Snmp4JStrategy m_strategy;
    private PDU m_pdu;
    
    protected Snmp4JV2TrapBuilder(Snmp4JStrategy strategy, PDU pdu, int type) {
        m_strategy = strategy;
        m_pdu = pdu;
        m_pdu.setType(type);
    }
    
    protected Snmp4JV2TrapBuilder(Snmp4JStrategy strategy) {
        this(strategy, new PDU(), PDU.TRAP);
    }
    
    protected PDU getPDU() {
        return m_pdu;
    }

    public void send(String destAddr, int destPort, String community) throws Exception {
        SnmpAgentConfig snmpAgentConfig = m_strategy.buildAgentConfig(destAddr, destPort, community, m_pdu);
        Snmp4JAgentConfig agentConfig = new Snmp4JAgentConfig(snmpAgentConfig);
        
        m_strategy.send(agentConfig, m_pdu, false);
    }

    public void addVarBind(SnmpObjId name, SnmpValue value) {
        OID oid = new OID(name.getIds());
        Variable val = ((Snmp4JValue) value).getVariable();
        m_pdu.add(new VariableBinding(oid, val));
    }

    public void sendTest(String destAddr, int destPort, String community) throws Exception {
        m_strategy.sendTest(destAddr, destPort, community, m_pdu);
    }

}
