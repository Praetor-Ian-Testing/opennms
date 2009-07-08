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

package org.opennms.acl.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.opennms.acl.model.AuthorityDTO;
import org.opennms.acl.model.AuthorityView;

@Ignore("test database is not thread-safe, port to opennms temporary database code")
public class AuthoritiesNodeHelperTest {

    @Test
    public void constructor() {
        AuthorityDTO authority = createAuthority();

        List<String> items = new ArrayList<String>();
        items.add("1");

        authority.setItems(items);

        List<AuthorityDTO> authorities = new ArrayList<AuthorityDTO>();
        authorities.add(authority);

        AuthoritiesNodeHelper helper = new AuthoritiesNodeHelper(authorities);
        assertNotNull(helper);
    }

    @Test
    public void getAuthoritiesItems() {

        AuthorityDTO authority = createAuthority();

        List<String> items = new ArrayList<String>();
        items.add("1");

        authority.setItems(items);

        List<AuthorityDTO> authorities = new ArrayList<AuthorityDTO>();
        authorities.add(authority);

        AuthoritiesNodeHelper helper = new AuthoritiesNodeHelper(authorities);
        assertNotNull(helper);
        Set<AuthorityView> auths = new HashSet<AuthorityView>();
        auths.add(authority);

        assertTrue(helper.getAuthoritiesItems(auths).size() == 1);
        assertTrue(helper.getAuthorities().size() == 1);

    }

    @Test
    public void getAuthoritiesItemsNoDuplicated() {

        AuthorityDTO authority = createAuthority();

        List<String> items = new ArrayList<String>();
        items.add("1");
        items.add("2");
        items.add("3");

        authority.setItems(items);

        List<AuthorityDTO> authorities = new ArrayList<AuthorityDTO>();
        authorities.add(authority);
        authorities.add(authority);

        AuthoritiesNodeHelper helper = new AuthoritiesNodeHelper(authorities);
        assertNotNull(helper);
        Set<AuthorityView> auths = new HashSet<AuthorityView>();
        auths.add(authority);

        assertTrue(helper.getAuthorities().size() == 1);

    }

    @Test
    public void deleteItems() {

        AuthorityDTO authority = createAuthority();

        List<String> items = new ArrayList<String>();
        items.add("1");
        items.add("2");
        items.add("3");

        authority.setItems(items);

        List<AuthorityDTO> authorities = new ArrayList<AuthorityDTO>();
        authorities.add(authority);
        authorities.add(authority);

        AuthoritiesNodeHelper helper = new AuthoritiesNodeHelper(authorities);
        assertNotNull(helper);
        Set<AuthorityView> auths = new HashSet<AuthorityView>();
        auths.add(authority);

        assertTrue(helper.getAuthorities().size() == 1);

        Set<AuthorityView> authoritiesView = new HashSet<AuthorityView>();
        authoritiesView.add(authority);

        assertTrue(helper.deleteItem(1));
        assertTrue(helper.getAuthoritiesItems(authoritiesView).size() == 2);

        assertTrue(helper.deleteItem(2));
        assertTrue(helper.getAuthoritiesItems(authoritiesView).size() == 1);

        assertTrue(helper.deleteItem(3));
        assertTrue(helper.getAuthoritiesItems(authoritiesView).size() == 0);

    }

    private AuthorityDTO createAuthority() {
        AuthorityDTO authority = new AuthorityDTO();
        authority.setDescription("this is a description");
        authority.setId(12);
        authority.setName("ROLE_USER");
        return authority;
    }

}
