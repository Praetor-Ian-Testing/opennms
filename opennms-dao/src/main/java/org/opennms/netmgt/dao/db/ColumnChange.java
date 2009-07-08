/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Copyright (C) 2004-2006, 2008 The OpenNMS Group, Inc.  All rights reserved.
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


package org.opennms.netmgt.dao.db;

public class ColumnChange {
    private Column m_column = null;

    private ColumnChangeReplacement m_columnReplacement = null;

    private boolean m_upgradeTimestamp = false;

    private int m_selectIndex = 0;

    private int m_prepareIndex = 0;

    private int m_columnType = 0;

    public Column getColumn() {
        return m_column;
    }

    public void setColumn(Column column) {
        m_column = column;
    }

    public ColumnChangeReplacement getColumnReplacement() {
        return m_columnReplacement;
    }

    public boolean hasColumnReplacement() {
        return (m_columnReplacement != null);
    }

    public void setColumnReplacement(ColumnChangeReplacement columnReplacement) {
        m_columnReplacement = columnReplacement;
    }

    public boolean isUpgradeTimestamp() {
        return m_upgradeTimestamp;
    }

    public void setUpgradeTimestamp(boolean upgradeTimestamp) {
        m_upgradeTimestamp = upgradeTimestamp;
    }

    public int getSelectIndex() {
        return m_selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        m_selectIndex = selectIndex;
    }

    public int getPrepareIndex() {
        return m_prepareIndex;
    }

    public void setPrepareIndex(int prepareIndex) {
        m_prepareIndex = prepareIndex;
    }

    public int getColumnType() {
        return m_columnType;
    }

    public void setColumnType(int columnType) {
        m_columnType = columnType;
    }
}
