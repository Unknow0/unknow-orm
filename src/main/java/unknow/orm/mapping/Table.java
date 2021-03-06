/*******************************************************************************
 * Copyright (c) 2014 Unknow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 * Contributors:
 * Unknow - initial API and implementation
 ******************************************************************************/
package unknow.orm.mapping;

import java.sql.*;
import java.util.*;

import org.slf4j.*;

import unknow.json.*;

public class Table
	{
	private static final Logger log=LoggerFactory.getLogger(Table.class);

	private String name;
	private String schema;
	private String catalog;
	private String remark;

	private Column[] columns;

	public Table(DatabaseMetaData metaData, String name, String schema, String catalog, String remark) throws SQLException, JsonException, ClassNotFoundException, ClassCastException
		{
		this.name=name;
		this.schema=schema;
		this.catalog=catalog;

		List<Column> list=new ArrayList<Column>();
		try (ResultSet rs=metaData.getColumns(null, null, name, null))
			{
			while (rs.next())
				{
				String colName=rs.getString("COLUMN_NAME");
				Integer sqlType=rs.getInt("DATA_TYPE");
				String type=rs.getString("TYPE_NAME");
				String colRemark=rs.getString("REMARKS");
				int lenth=rs.getInt("COLUMN_SIZE");
				String ai=rs.getString("IS_AUTOINCREMENT");

				log.trace("{}> {}", name, colName);
				Column col=new Column(colName, sqlType, type, lenth, colRemark, ai);
				list.add(col);
				}
			}
		columns=list.toArray(new Column[0]);
		}

	public Column[] getColumns()
		{
		return columns;
		}

	/** @param caseSensitive 
	 * @return The column with name name or null if not found */
	public Column getColumn(String name, boolean caseSensitive)
		{
		for(int i=0; i<columns.length; i++)
			{
			if(caseSensitive&&columns[i].getName().equals(name)||!caseSensitive&&columns[i].getName().equalsIgnoreCase(name))
				return columns[i];
			}
		return null;
		}

	public String getName()
		{
		return name;
		}

	public String toString()
		{
		return (schema==null?"":schema+".")+(catalog==null?"":catalog+".")+name+(remark!=null?" "+remark:"");
		}
	}
