/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2007-2008 The OpenNMS Group, Inc.  All rights reserved.
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

package org.opennms.web.svclayer.support;

import java.util.List;

import org.opennms.netmgt.dao.ResourceDao;
import org.opennms.netmgt.dao.StatisticsReportDao;
import org.opennms.netmgt.model.StatisticsReport;
import org.opennms.netmgt.model.StatisticsReportData;
import org.opennms.web.command.StatisticsReportCommand;
import org.opennms.web.svclayer.StatisticsReportService;
import org.opennms.web.svclayer.support.StatisticsReportModel.Datum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;

/**
 * Web service layer implementation for statistics reports.
 * 
 * @author <a href="mailto:dj@opennms.org">DJ Gregor</a>
 */
public class DefaultStatisticsReportService implements StatisticsReportService, InitializingBean {
    private StatisticsReportDao m_statisticsReportDao;
    private ResourceDao m_resourceDao;

    public List<StatisticsReport> getStatisticsReports() {
        return m_statisticsReportDao.findAll();
    }

    public StatisticsReportModel getReport(StatisticsReportCommand command, BindException errors) {
        StatisticsReportModel model = new StatisticsReportModel();
        model.setErrors(errors);
        
        if (errors.hasErrors()) {
            return model;
        }
        
        Assert.notNull(command.getId(), "id property on command object cannot be null");
        
        StatisticsReport report = m_statisticsReportDao.load(command.getId());
        model.setReport(report);
        
        m_statisticsReportDao.initialize(report);
        m_statisticsReportDao.initialize(report.getData());
        
        for (StatisticsReportData datum : report.getData()) {
            Datum d = new Datum();
            d.setValue(datum.getValue());
            try {
                d.setResource(m_resourceDao.loadResourceById(datum.getResourceId()));
            } catch (ObjectRetrievalFailureException e) {
                d.setResourceThrowable(e);
            }
            model.addData(d);
        }
        
        return model;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.state(m_statisticsReportDao != null, "property statisticsReportDao must be set to a non-null value");
        Assert.state(m_resourceDao != null, "property resourceDao must be set to a non-null value");
    }

    public StatisticsReportDao getStatisticsReportDao() {
        return m_statisticsReportDao;
    }

    public void setStatisticsReportDao(StatisticsReportDao statisticsReportDao) {
        m_statisticsReportDao = statisticsReportDao;
    }

    public ResourceDao getResourceDao() {
        return m_resourceDao;
    }

    public void setResourceDao(ResourceDao resourceDao) {
        m_resourceDao = resourceDao;
    }
}
