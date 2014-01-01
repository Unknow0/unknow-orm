/*******************************************************************************
 * Copyright (c) 2014 Unknow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 * Contributors:
 *     Unknow - initial API and implementation
 ******************************************************************************/
package unknow.orm.mapping;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.*;

import unknow.json.*;

public class Table
	{
	private static final Logger logger=LogManager.getFormatterLogger(Table.class);

	private Database database;

	private String name;
	private String schema;
	private String catalog;
	private String remark;

	private Column[] columns;

	public Table(Database db, DatabaseMetaData metaData, JsonObject tablecfg, String name, String schema, String catalog, String remark) throws SQLException, JsonException, ClassNotFoundException, ClassCastException
		{
		database=db;
		this.name=name;
		this.schema=schema;
		this.catalog=catalog;

		String cl=tablecfg.getString("class");
		database.addMapping(cl, this);
		JsonValue colcfg=tablecfg.get("columns");

		List<Column> list=new ArrayList<Column>();
		try (ResultSet rs=metaData.getColumns(null, null, name, null))
			{
			while (rs.next())
				{
				String colName=rs.getString("COLUMN_NAME");
				Integer sqlType=rs.getInt("DATA_TYPE");
				String type=rs.getString("TYPE_NAME");
				String colRemark=rs.getString("REMARKS");

				logger.trace("%s> %s", name, colName);
				JsonValue cfg=null;
				if(colcfg instanceof JsonObject)
					{
					cfg=((JsonObject)colcfg).opt(colName);
					}
				if(cfg!=null||colcfg instanceof JsonArray&&((JsonArray)colcfg).contains(colName))
					{
					String jcol;
					String setter=null;
					if(cfg instanceof JsonObject)
						{
						jcol=((JsonObject)cfg).getString("name");
						setter=((JsonObject)cfg).optString("setter");
						}
					else
						jcol=((JsonValue.JsonString)cfg).value();
					if(setter==null)
						setter="set"+Character.toUpperCase(jcol.charAt(0))+jcol.substring(1);
					Column col=new Column(jcol, setter, colName, sqlType, type, colRemark);
					list.add(col);
					}
				else
					logger.trace("	not in cfg skipping");
				}
			}
		columns=list.toArray(new Column[0]);
		}

	public Column[] getColumns()
		{
		return columns;
		}

	public void append(StringBuilder sql, String alias)
		{
		for(int i=0; i<columns.length; i++)
			{
			String col=columns[i].getName();
			if(i!=0)
				sql.append(',');
			sql.append(alias).append('.').append(col);
			sql.append(" as ");
			sql.append('"').append(alias).append('.').append(col).append('"');
			}
		}

	public Object build(String alias, Class<?> clazz, ResultSet rs) throws SQLException
		{
		try
			{
			Object entity=clazz.newInstance();
			for(Column c:columns)
				{
				Class<?> type=database.toJavaType(c.getSqlType(), c.getType());
				Object value=database.convert(c.getSqlType(), c.getType(), rs, alias+"."+c.getName());
				try
					{
					try
						{
						Method m=clazz.getMethod(c.getSetter(), type);
						m.invoke(entity, value);
						continue;
						}
					catch (NoSuchMethodException e)
						{
						}
					catch (IllegalArgumentException e)
						{ // TODO logger
						}
					catch (InvocationTargetException e)
						{
						}
					try
						{
						Field field=clazz.getDeclaredField(c.getJavaName());
						field.set(entity, value);
						}
					catch (NoSuchFieldException e)
						{
						throw new SQLException("no accessible '"+c.getSetter()+"("+type.getName()+")' or '"+c.getJavaName()+"' in '"+clazz.getName()+"'");
						}
					catch (SecurityException e)
						{
						throw new SQLException("can't set '"+c.getJavaName()+"' in '"+clazz.getName()+"'", e);
						}
					}
				catch (IllegalAccessException e)
					{
					throw new SQLException("can't execute '"+c.getSetter()+"("+type.getName()+")' or set '"+c.getJavaName()+"' in '"+clazz.getName()+"'", e);
					}
				}
			return entity;
			}
		catch (InstantiationException e)
			{
			throw new SQLException("Can't create '"+clazz.getName()+"'", e);
			}
		catch (IllegalAccessException e)
			{
			throw new SQLException("Can't create '"+clazz.getName()+"'", e);
			}
		}

	public String toString()
		{
		return schema+"."+catalog+"."+name+(remark!=null?" "+remark:"");
		}
	}
