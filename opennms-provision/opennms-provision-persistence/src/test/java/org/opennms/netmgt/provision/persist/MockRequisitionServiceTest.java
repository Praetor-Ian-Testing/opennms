/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2009-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.provision.persist;

import static org.junit.Assert.assertEquals;
import static org.opennms.netmgt.provision.persist.requisition.RequisitionMapper.toPersistenceModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.junit.Before;
import org.junit.Test;
import org.opennms.netmgt.model.requisition.RequisitionEntity;
import org.opennms.netmgt.provision.persist.requisition.Requisition;
import org.springframework.core.io.ClassPathResource;

public class MockRequisitionServiceTest extends ForeignSourceRepositoryTestCase {
    private String m_defaultForeignSourceName;

    private RequisitionService m_foreignSourceRepository;

    @Before
    public void setUp() {
        m_foreignSourceRepository = new MockRequisitionService();
        m_defaultForeignSourceName = "imported:";
    }

    private RequisitionEntity createRequisition() throws Exception {
        RequisitionEntity requisition = toPersistenceModel(JAXB.unmarshal(new ClassPathResource("/requisition-test.xml").getURL(), Requisition.class));
        m_foreignSourceRepository.saveOrUpdateRequisition(requisition);
        return requisition;
    }

    @Test
    public void testRequisition() throws Exception {
        createRequisition();
        RequisitionEntity r = m_foreignSourceRepository.getRequisition(m_defaultForeignSourceName);
        assertEquals("number of nodes visited", 2, r.getNodes().size());
        assertEquals("node name matches", "apknd", r.getNodes().get(0).getNodeLabel());
    }

    /**
     * This test ensures that the Spring Bean accessor classes work properly
     * since our REST implementation uses bean access to update the values.
     */
    @Test
    // TODO MVR remove this
    public void testBeanWrapperAccess() throws Exception {
        createRequisition();
        RequisitionEntity r = m_foreignSourceRepository.getRequisition(m_defaultForeignSourceName);
        List<String> categories = new ArrayList<>(r.getNodes().get(0).getCategories());
        assertEquals("AC", categories.get(0));
        assertEquals("UK", categories.get(1));
        assertEquals("low", categories.get(2));
        assertEquals(0, r.getNodes().get(1).getCategories().size());

        // TODO MVR ..
//        wrapper.setPropertyValue("node[1].categories[0]", new RequisitionCategory("Hello world"));
//        wrapper.setPropertyValue("node[1].categories[1]", new RequisitionCategory("Hello again"));
//
//        assertEquals(2, ((RequisitionCategory[])wrapper.getPropertyValue("node[1].category")).length);
    }

}
