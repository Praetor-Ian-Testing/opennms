/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2004-2009 The OpenNMS Group, Inc.  All rights reserved.
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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Category;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.opennms.core.utils.ThreadCategory;
import org.opennms.netmgt.EventConstants;
import org.opennms.netmgt.config.common.Header;
import org.opennms.netmgt.config.groups.Group;
import org.opennms.netmgt.config.groups.Groupinfo;
import org.opennms.netmgt.config.groups.Groups;
import org.opennms.netmgt.config.groups.Role;
import org.opennms.netmgt.config.groups.Roles;
import org.opennms.netmgt.config.groups.Schedule;
import org.opennms.netmgt.config.users.DutySchedule;
import org.opennms.netmgt.dao.castor.CastorUtils;


/**
 * @author <a href="mailto:david@opennms.org">David Hustace</a>
 * @author <a href="mailto:brozow@opennms.org">Matt Brozowski</a>
 * @author <a href="mailto:ayres@net.orst.edu">Bill Ayres</a>
 * @author <a href="mailto:dj@gregor.com">DJ Gregor</a>
 */
public abstract class GroupManager {

    /**
     * The duty schedules for each group
     */
    protected static HashMap<String, List<DutySchedule>> m_dutySchedules;

    /**
     * A mapping of Group object by name
     */
    private Map<String, Group> m_groups;
    private Map<String, Role> m_roles;
    private Header m_oldHeader;

    /**
     * @param reader
     * @throws MarshalException
     * @throws ValidationException
     */
    @Deprecated
    protected synchronized void parseXml(Reader reader) throws MarshalException, ValidationException {
        Groupinfo groupinfo = CastorUtils.unmarshal(Groupinfo.class, reader);
        initializeGroupsAndRoles(groupinfo);
    }

    protected synchronized void parseXml(InputStream stream) throws MarshalException, ValidationException {
        Groupinfo groupinfo = CastorUtils.unmarshal(Groupinfo.class, stream);
        initializeGroupsAndRoles(groupinfo);
    }

    private void initializeGroupsAndRoles(Groupinfo groupinfo) {
        Groups groups = groupinfo.getGroups();
        m_groups = new LinkedHashMap<String, Group>();
        m_oldHeader = groupinfo.getHeader();
        for (Group curGroup : groups.getGroupCollection()) {
            m_groups.put(curGroup.getName(), curGroup);
        }
        buildDutySchedules(m_groups);
        
        Roles roles = groupinfo.getRoles();
        m_roles = new LinkedHashMap<String, Role>();
        if (roles != null) {
        	for (Role role : roles.getRoleCollection()) {
                m_roles.put(role.getName(), role);
            }
        }
    }

    /**
     * Set the groups data
     */
    public void setGroups(Map<String, Group> grp) {
        m_groups = grp;
    }

    /**
     * Get the groups
     */
    public Map<String, Group> getGroups() throws IOException, MarshalException, ValidationException {
    
        update();
    
        return new LinkedHashMap<String, Group>(m_groups);
    
    }

    /**
     * @throws ValidationException
     * @throws MarshalException
     * @throws IOException
     * 
     */
    protected abstract void update() throws IOException, MarshalException, ValidationException;

    /**
     * Returns a boolean indicating if the group name appears in the xml file
     * 
     * @return true if the group exists in the xml file, false otherwise
     */
    public boolean hasGroup(String groupName) throws IOException, MarshalException, ValidationException {
        update();
    
        return m_groups.containsKey(groupName);
    }

    /**
     */
    public List<String> getGroupNames() throws IOException, MarshalException, ValidationException {
        update();
    
        return new ArrayList<String>(m_groups.keySet());
    }

    /**
     * Get a group using its name
     * 
     * @param name
     *            the name of the group to return
     * @return Group, the group specified by name
     */
    public Group getGroup(String name) throws IOException, MarshalException, ValidationException {
        update();
    
        return m_groups.get(name);
    }

    /**
     */
    public synchronized void saveGroups() throws Exception {
        Header header = m_oldHeader;
    
        header.setCreated(EventConstants.formatToString(new Date()));
    
        Groups groups = new Groups();
        for (Group grp : m_groups.values()) {
            groups.addGroup(grp);
        }
        
        
        Roles roles = new Roles();
        for (Role role : m_roles.values()) {
            roles.addRole(role);
        }
    
        Groupinfo groupinfo = new Groupinfo();
        groupinfo.setGroups(groups);
        if (roles.getRoleCount() > 0)
            groupinfo.setRoles(roles);
        groupinfo.setHeader(header);
    
        m_oldHeader = header;
    
        // marshal to a string first, then write the string to the file. This
        // way the original configuration
        // isn't lost if the XML from the marshal is hosed.
        StringWriter stringWriter = new StringWriter();
        Marshaller.marshal(groupinfo, stringWriter);
        String data = stringWriter.toString();
        saveXml(data);
    }

    /**
     * Builds a mapping between groups and duty schedules. These are used when
     * determining to send a notice to a given group. This helps speed up the decision process.
     * @param groups the map of groups parsed from the XML configuration file
     */
    private static void buildDutySchedules(Map<String, Group> groups) {
        m_dutySchedules = new HashMap<String, List<DutySchedule>>();
        Iterator<String> i = groups.keySet().iterator();
        while(i.hasNext()) {
            String key = i.next();
            Group curGroup = groups.get(key);
            if (curGroup.getDutyScheduleCount() > 0) {
                List<DutySchedule> dutyList = new ArrayList<DutySchedule>();
                for (String duty : curGroup.getDutyScheduleCollection()) {
                	dutyList.add(new DutySchedule(duty));
                }
                m_dutySchedules.put(key, dutyList);
            }
        }
    }

    /**
     * Determines if a group is on duty at a given time. If a group has no duty schedules
     * listed in the configuration file, that group is assumed to always be on duty.
     * @param group the group whose duty schedule we want
     * @param time the time to check for a duty schedule
     * @return boolean, true if the group is on duty, false otherwise.
     */
    public boolean isGroupOnDuty(String group, Calendar time) throws IOException, MarshalException, ValidationException {
        update();
        //if the group has no duty schedules then it is on duty
        if (!m_dutySchedules.containsKey(group)) {
            return true;
        }
        List<DutySchedule> dutySchedules = m_dutySchedules.get(group);
        for (DutySchedule curSchedule : dutySchedules) {
        	if (curSchedule.isInSchedule(time)) {
        		return true;
        	}
        }
        return false;
    }
  
    /**
     * Determines when a group is next on duty. If a group has no duty schedules
     * listed in the configuration file, that group is assumed to always be on duty.
     * @param group the group whose duty schedule we want
     * @param time the time to check for a duty schedule
     * @return long, the time in milliseconds until the group is next on duty
     */
    public long groupNextOnDuty(String group, Calendar time) throws IOException, MarshalException, ValidationException {
        Category log = ThreadCategory.getInstance(this.getClass());
        long next = -1;
        update();
        //if the group has no duty schedules then it is on duty
        if (!m_dutySchedules.containsKey(group)) {
            return 0;
        }
        List<DutySchedule> dutySchedules = m_dutySchedules.get(group);
        for (int i = 0; i < dutySchedules.size(); i++) {
            DutySchedule curSchedule = dutySchedules.get(i);
            long tempnext =  curSchedule.nextInSchedule(time);
            if( tempnext < next || next == -1 ) {
                if (log.isDebugEnabled()) {
                    log.debug("isGroupOnDuty: On duty in " + tempnext + " millisec from schedule " + i);
                }
                next = tempnext;
            }
        }
        return next;
    } 

    /**
     * @param data
     * @throws IOException
     */
    protected abstract void saveXml(String data) throws IOException;

    /**
     * Adds a new user and overwrites the "groups.xml"
     */
    public synchronized void saveGroup(String name, Group details) throws Exception {
        if (name == null || details == null) {
            throw new Exception("GroupFactory:saveGroup  null");
        } else {
            m_groups.put(name, details);
        }
    
        saveGroups();
    }
    
    public void saveRole(Role role) throws Exception {
        m_roles.put(role.getName(), role);
        saveGroups();
    }


    /**
     * Removes the user from the list of groups. Then overwrites to the
     * "groups.xml"
     */
    public synchronized void deleteUser(String name) throws Exception {
        // Check if the user exists
        if (name != null && !name.equals("")) {
            // Remove the user in the group.
        	for (Group group : m_groups.values()) {
        		group.removeUser(name);
        	}

        	for (Role role : m_roles.values()) {
                Iterator<Schedule> s = role.getScheduleCollection().iterator();
                while(s.hasNext()) {
                    Schedule sched = s.next();
                    if (name.equals(sched.getName())) {
                        s.remove();
                    }
                }
            }
        } else {
            throw new Exception("GroupFactory:delete Invalid user name:" + name);
        }
        
        
        // Saves into "groups.xml" file
        saveGroups();
    }

    /**
     * Removes the group from the list of groups. Then overwrites to the
     * "groups.xml"
     */
    public synchronized void deleteGroup(String name) throws Exception {
        // Check if the group exists
        if (name != null && !name.equals("")) {
            if (m_groups.containsKey(name)) {
                // Remove the group.
                m_groups.remove(name);
            } else
                throw new Exception("GroupFactory:delete Group doesnt exist:" + name);
        } else {
            throw new Exception("GroupFactory:delete Invalid user group:" + name);
        }
        // Saves into "groups.xml" file
        saveGroups();
    }
    
    public void deleteRole(String name) throws Exception {
        if (name != null && !name.equals("")) {
            if (m_roles.containsKey(name)) {
                m_roles.remove(name);
            }
            else 
                throw new Exception("GroupFacotry:deleteRole Role doesn't exist: "+name);
        }
        else
            throw new Exception("GroupFactory:deleteRole Invalid role name: "+name);
        
        saveGroups();
    }


    /**
     * Renames the group from the list of groups. Then overwrites to the
     * "groups.xml"
     */
    public synchronized void renameGroup(String oldName, String newName) throws Exception {
    	if (oldName != null && !oldName.equals("")) {
    		if (m_groups.containsKey(oldName)) {
    			Group grp = m_groups.get(oldName);
    			grp.setName(newName);
    			m_groups.put(newName, grp);
    		} else {
    			throw new Exception("GroupFactory.renameGroup: Group doesn't exist: " + oldName);
    		}
    		// Save into groups.xml
    		saveGroups();
    	}
    }

    /**
     * When this method is called group name is changed, so also is the
     * group name belonging to the view. Also overwrites the "groups.xml" file
     */
    public synchronized void renameUser(String oldName, String newName) throws Exception {
        // Get the old data
        if (oldName == null || newName == null || oldName == "" || newName == "") {
            throw new Exception("Group Factory: Rename user.. no value ");
        } else {
            Map<String, Group> map = new LinkedHashMap<String, Group>();
            
        	for (Group group : m_groups.values()) {        	   
        	   for(ListIterator<String> userList = group.getUserCollection().listIterator(); userList.hasNext();){
        	       String name = userList.next();
        	       
        	       if(name.equals(oldName)){
        	          userList.set(newName); 
        	       }
        	   }
        	   map.put(group.getName(), group);
        	}
        	
        	m_groups.clear();
        	m_groups.putAll(map);

            for (Role role : m_roles.values()) {
            	for (Schedule sched : role.getScheduleCollection()) {
                    if (oldName.equals(sched.getName())) {
                        sched.setName(newName);
                    }
                }
            }
            
            saveGroups();
        }
    }

    public String[] getRoleNames() {
        return (String[]) m_roles.keySet().toArray(new String[m_roles.keySet().size()]);
    }
    
    public Collection<Role> getRoles() {
        return m_roles.values();
    }
    
    public Role getRole(String roleName) {
        return (Role)m_roles.get(roleName);
    }

    public boolean userHasRole(String userId, String roleid) throws MarshalException, ValidationException, IOException {
        update();

        for (Schedule sched : getRole(roleid).getScheduleCollection()) {
            if (userId.equals(sched.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public List<Schedule> getSchedulesForRoleAt(String roleId, Date time) throws MarshalException, ValidationException, IOException {
        update();

        List<Schedule> schedules = new ArrayList<Schedule>();
        for (Schedule sched : getRole(roleId).getScheduleCollection()) {
            if (BasicScheduleUtils.isTimeInSchedule(time, sched)) {
                schedules.add(sched);
            }
        }
        return schedules;
    }
    
    public List<Schedule> getUserSchedulesForRole(String userId, String roleId) throws MarshalException, ValidationException, IOException {
        update();

        List<Schedule> scheds = new ArrayList<Schedule>();
        for (Schedule sched : getRole(roleId).getScheduleCollection()) {
            if (userId.equals(sched.getName())) {
                scheds.add(sched);
            }
        }
        return scheds;
        
    }

    public boolean isUserScheduledForRole(String userId, String roleId, Date time) throws MarshalException, ValidationException, IOException {
        update();

        for (Schedule sched : getUserSchedulesForRole(userId, roleId)) {
            if (BasicScheduleUtils.isTimeInSchedule(time, sched)) {
                return true;
            }
        }
        
        // if no user is scheduled then the supervisor is schedule by default 
        Role role = getRole(roleId);
        if (userId.equals(role.getSupervisor())) {
        	for (Schedule sched : role.getScheduleCollection()) {
                if (BasicScheduleUtils.isTimeInSchedule(time, sched)) {
                    // we found another scheduled user
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public OwnedIntervalSequence getRoleScheduleEntries(String roleid, Date start, Date end) throws MarshalException, ValidationException, IOException {
        update();

        OwnedIntervalSequence schedEntries = new OwnedIntervalSequence();
                 Role role = getRole(roleid);
                 for (int i = 0; i < role.getScheduleCount(); i++) {
                    Schedule sched = (Schedule) role.getSchedule(i);
                     Owner owner = new Owner(roleid, sched.getName(), i);
                     schedEntries.addAll(BasicScheduleUtils.getIntervalsCovering(start, end, sched, owner));
                 }
                 
                 OwnedIntervalSequence defaultEntries = new OwnedIntervalSequence(new OwnedInterval(start, end));
                 defaultEntries.removeAll(schedEntries);
                 Owner supervisor = new Owner(roleid, role.getSupervisor());
                 for (Iterator it = defaultEntries.iterator(); it.hasNext();) {
                     OwnedInterval interval = (OwnedInterval) it.next();
                     interval.addOwner(supervisor);
                 }
                 schedEntries.addAll(defaultEntries);
                 return schedEntries;
        
    }

    public List<Group> findGroupsForUser(String user) {
        List<Group> groups = new ArrayList<Group>();
        
        for (Group group : m_groups.values()) {
            if (group.getUserCollection().contains(user)) {
                groups.add(group);
            }
        }

        return groups;
    }

}
