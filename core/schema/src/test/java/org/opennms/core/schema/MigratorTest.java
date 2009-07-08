/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2009 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.core.schema;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opennms.netmgt.dao.db.JUnitTemporaryDatabase;
import org.opennms.netmgt.dao.db.TemporaryDatabase;
import org.opennms.netmgt.dao.db.TemporaryDatabaseExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;


/**
 * Unit test for ModelImport application.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
    TemporaryDatabaseExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class
})
@ContextConfiguration(locations={
        "classpath:/migratorTest.xml"
})
@JUnitTemporaryDatabase(populate=false)
public class MigratorTest {

    @Autowired
    DataSource m_dataSource;

    @Autowired
    ResourceLoader m_resourceLoader;

    private Migration m_migration;
    private String m_changeLog = "changelog.xml";

    @Before
    public void setUp() throws Exception {
        m_migration = new Migration();
        m_migration.setAdminUser(System.getProperty(TemporaryDatabase.ADMIN_USER_PROPERTY, TemporaryDatabase.DEFAULT_ADMIN_USER));
        m_migration.setAdminPassword(System.getProperty(TemporaryDatabase.ADMIN_PASSWORD_PROPERTY, TemporaryDatabase.DEFAULT_ADMIN_PASSWORD));
        m_migration.setDatabaseUser(System.getProperty(TemporaryDatabase.ADMIN_USER_PROPERTY, TemporaryDatabase.DEFAULT_ADMIN_USER));
        m_migration.setDatabasePassword(System.getProperty(TemporaryDatabase.ADMIN_PASSWORD_PROPERTY, TemporaryDatabase.DEFAULT_ADMIN_PASSWORD));
        m_migration.setChangeLog(m_changeLog);
    }

    @Test
    public void testUpdate() throws Exception {
        Migrator m = new Migrator();
        m.setDataSource(m_dataSource);
        m.setAdminDataSource(m_dataSource);
        m.setValidateDatabaseVersion(false);
        m.setCreateUser(false);
        m.setCreateDatabase(false);

        m.prepareDatabase(m_migration);
        m.migrate(m_migration);

        Connection conn = null;
        try {
            conn = m_dataSource.getConnection();

            Set<String> tables = new HashSet<String>();
            ResultSet rs = conn.getMetaData().getTables(null, null, "%", null);
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME").toLowerCase());
            }
            assertTrue("must contain table 'alarms'", tables.contains("alarms"));

            Set<String> procs = new HashSet<String>();
            rs = conn.getMetaData().getProcedures(null, null, "%");
            while (rs.next()) {
                procs.add(rs.getString("PROCEDURE_NAME").toLowerCase());
            }
            System.err.println("procs = " + procs);
            assertTrue("must have stored procedure 'setSnmpInterfaceKeysOnUpdate'", procs.contains("setsnmpinterfacekeysonupdate"));
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    @Test
    @Ignore("takes a long time, just did this to make sure 'upgrades' would not bomb")
    public void testUpdateTwice() throws Exception {
        Migrator m = new Migrator();
        m.setDataSource(m_dataSource);
        m.migrate(m_migration);
        m.migrate(m_migration);
    }
}
