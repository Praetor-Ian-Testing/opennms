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

package org.opennms.core.resource.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 */
public class DataSourceDbConnectionFactory implements DbConnectionFactory {
    private DataSource m_dataSource;

    public DataSourceDbConnectionFactory(DataSource dataSource) {
        m_dataSource = dataSource;
    }
    
    public void destroy() throws SQLException {
    }

    public Connection getConnection() throws SQLException {
        return m_dataSource.getConnection();
    }

    public void init(String dbUrl, String dbDriver, String username, String password) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("not implemented");
    }

    public void releaseConnection(Connection connection) throws SQLException {
        connection.close();
    }
}