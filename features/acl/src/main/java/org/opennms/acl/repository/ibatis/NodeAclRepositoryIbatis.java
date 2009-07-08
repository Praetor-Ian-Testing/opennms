/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2009 Massimiliano Dess&igrave; (desmax74@yahoo.it)
 * Copyright (C) 2009 The OpenNMS Group, Inc.
 * All rights reserved.
 *
 * This program was developed and is maintained by Rocco RIONERO
 * ("the author") and is subject to dual-copyright according to
 * the terms set in "The OpenNMS Project Contributor Agreement".
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

package org.opennms.acl.repository.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.opennms.acl.model.NodeONMSDTO;
import org.opennms.acl.repository.ItemAclRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author Massimiliano Dess&igrave; (desmax74@yahoo.it)
 * @since jdk 1.5.0
 */
@Repository("nodeAclRepository")
public class NodeAclRepositoryIbatis extends SqlMapClientTemplate implements ItemAclRepository {

    @Autowired
    @Override
    public void setSqlMapClient(@Qualifier("onmsSqlMapClient") SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    public List<NodeONMSDTO> getItems() {
        return queryForList("selectNodes");
    }

    @SuppressWarnings("unchecked")
    public List<NodeONMSDTO> getAuthorityItems(List<Integer> items) {
        return items.size() > 0 ? queryForList("selectNodesAuthority", items) : new ArrayList<NodeONMSDTO>(0);
    }

    @SuppressWarnings("unchecked")
    public List<NodeONMSDTO> getFreeItems(List<Integer> items) {
        return items.size() > 0 ? queryForList("selectFreeNodesAuthority", items) : queryForList("selectNodes");
    }

}