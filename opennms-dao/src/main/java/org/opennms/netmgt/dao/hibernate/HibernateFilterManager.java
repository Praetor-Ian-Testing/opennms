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


package org.opennms.netmgt.dao.hibernate;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.opennms.netmgt.model.FilterManager;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author brozow
 *
 */
public class HibernateFilterManager implements FilterManager {
    
    private HibernateTemplate m_template;
    
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        m_template = new HibernateTemplate(sessionFactory);
    }

    /* (non-Javadoc)
     * @see org.opennms.netmgt.model.FilterManager#disableAuthorizationFilter()
     */
    public void disableAuthorizationFilter() {
        HibernateCallback cb = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.disableFilter(AUTH_FILTER_NAME);
                return null;
            }
            
        };
        
        m_template.execute(cb);
    }

    /* (non-Javadoc)
     * @see org.opennms.netmgt.model.FilterManager#enableAuthorizationFilter(java.lang.String[])
     */
    public void enableAuthorizationFilter(final String[] authorizationGroups) {
        HibernateCallback cb = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                session.enableFilter(AUTH_FILTER_NAME).setParameterList("userGroups", authorizationGroups);
                return null;
            }
            
        };
        
        m_template.execute(cb);
    }

}
