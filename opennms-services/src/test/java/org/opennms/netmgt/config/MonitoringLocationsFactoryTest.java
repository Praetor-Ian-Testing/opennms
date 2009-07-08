/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2006, 2008-2009 The OpenNMS Group, Inc.  All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import junit.framework.TestCase;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.opennms.netmgt.config.monitoringLocations.LocationDef;
import org.opennms.netmgt.config.poller.PollerConfiguration;
import org.opennms.netmgt.dao.castor.CastorUtils;
import org.opennms.netmgt.mock.MockDatabase;
import org.opennms.netmgt.mock.MockNetwork;

/**
 * @author <a href="mailto:david@opennms.org">David Hustace</a>
 */
public class MonitoringLocationsFactoryTest extends TestCase {

    private MonitoringLocationsFactory m_locationFactory;

    private PollerConfigManager m_pollerConfigManager;

    protected void setUp() throws Exception {
        super.setUp();

        MockNetwork network = new MockNetwork();

        MockDatabase db = new MockDatabase();
        db.populate(network);

        DataSourceFactory.setInstance(db);

        InputStream stream = getClass().getResourceAsStream("/org/opennms/netmgt/config/monitoring-locations.testdata.xml");
        m_locationFactory = new MonitoringLocationsFactory(stream);
        stream.close();

        stream = getClass().getResourceAsStream("/org/opennms/netmgt/config/poller-configuration.testdata.xml");
        m_pollerConfigManager = new TestPollerConfigManager(stream, "localhost", false);
        stream.close();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetName() throws MarshalException, ValidationException,
            IOException {
        final String locationName = "RDU";
        LocationDef def = m_locationFactory.getDef(locationName);
        assertNotNull(def);
        assertEquals(locationName, def.getLocationName());
        assertEquals("raleigh", def.getMonitoringArea());

        assertNotNull(m_pollerConfigManager.getPackage(def.getPollingPackageName()));

    }

    static class TestPollerConfigManager extends PollerConfigManager {
        String m_xml;

        @Deprecated
        public TestPollerConfigManager(Reader rdr, String localServer, boolean verifyServer) throws MarshalException, ValidationException, IOException {
            super(rdr, localServer, verifyServer);
            save();
        }

        public TestPollerConfigManager(InputStream stream, String localServer, boolean verifyServer) throws MarshalException, ValidationException {
            super(stream, localServer, verifyServer);
        }

        @SuppressWarnings("deprecation")
        public void update() throws IOException, MarshalException, ValidationException {
            m_config = CastorUtils.unmarshal(PollerConfiguration.class, new StringReader(m_xml));
            setUpInternalData();
        }

        protected void saveXml(String xml) throws IOException {
            m_xml = xml;
        }

        public String getXml() {
            return m_xml;
        }

    }
}
