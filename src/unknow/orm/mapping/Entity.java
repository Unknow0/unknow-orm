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

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.*;

import unknow.common.*;

public class Entity<T>
	{
	private static final Logger logger=LogManager.getFormatterLogger(Entity.class);

	private Class<T> clazz;

	public final Map<String,Set<Entry>> entities;

	public Entity(Class<T> cl, Map<String,Set<Entry>> ent)
		{
		clazz=cl;
		entities=ent;
		}

	public void append(StringBuilder sql, String alias) throws SQLException
		{
		String[] a=alias.split(",");
		if(a.length!=entities.size())
			throw new SQLException("aliases count differ from table count");
		boolean first=true;
		int i=0;
		for(String table:entities.keySet())
			{
			for(Entry e:entities.get(table))
				{
				if(!first)
					sql.append(',');
				else
					first=false;
				if(e instanceof ColEntry)
					{
					String col=((ColEntry)e).col.getName();
					sql.append(a[i]).append('.').append(col);
					sql.append(" as ");
					sql.append('"').append(a[i]).append('.').append(col).append('"');
					}
				else
					((EntityEntry)e).entity.append(sql, a[i]);
				}
			i++;
			}
		}

	public T build(Database database, String alias, ResultSet rs) throws SQLException
		{
		String[] a=alias.split(",");
		if(a.length!=entities.size())
			throw new SQLException("aliases count differs from table count");
		try
			{
			T entity=clazz.newInstance();
			int i=0;
			for(String table:entities.keySet())
				{
				for(Entry e:entities.get(table))
					{
					Class<?> type;
					Object value;
					if(e instanceof ColEntry)
						{
						ColEntry c=(ColEntry)e;
						type=database.toJavaType(c.col.getSqlType(), c.col.getType());
						value=database.convert(c.col.getSqlType(), c.col.getType(), rs, a[i]+"."+c.col.getName());
						}
					else
						{
						value=((EntityEntry)e).entity.build(database, a[i], rs);
						type=value.getClass();
						}
					try
						{
						try
							{
							Method m=Reflection.getMethod(clazz, e.setter, type);
							if(m!=null)
								{
								logger.trace(" -> setting %s %s", e.javaName, value);
								m.invoke(entity, value);
								continue;
								}
							}
						catch (IllegalArgumentException ex)
							{
							logger.warn("fail to call '"+e.setter+"' on '"+entity+"'", ex);
							}
						catch (InvocationTargetException ex)
							{
							logger.warn("fail to call '"+e.setter+"' on '"+entity+"'", ex);
							}
						try
							{
							Field field=Reflection.getField(clazz, e.javaName);
							if(field==null)
								throw new SQLException("no accessible '"+e.setter+"("+type.getName()+")' or '"+e.javaName+"' in '"+clazz.getName()+"'");
							field.setAccessible(true);
							logger.trace(" -> setting %s %s", e.javaName, value);
							field.set(entity, value);
							}
						catch (SecurityException ex)
							{
							throw new SQLException("can't set '"+e.javaName+"' in '"+clazz.getName()+"'", ex);
							}
						}
					catch (IllegalAccessException ex)
						{
						throw new SQLException("can't execute '"+e.setter+"("+type.getName()+")' or set '"+e.javaName+"' in '"+clazz.getName()+"'", ex);
						}
					}
				i++;
				}
			logger.trace("build '"+clazz+"' => '"+entity+"'");
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

	public static class Entry
		{
		public String setter;
		public String javaName;
		public String getter;

		public Entry(String javaName, String setter) // TODO setter & javaName format
			{
			this.javaName=javaName;
			this.setter=setter!=null?setter:"set"+Character.toUpperCase(javaName.charAt(0))+javaName.substring(1);
			this.getter="get"+Character.toUpperCase(javaName.charAt(0))+javaName.substring(1);
			}
		}

	public static class ColEntry extends Entry
		{
		public Column col;
		public Boolean aiKey;

		public ColEntry(Column col, String jname, Boolean aiKey)
			{
			this(col, jname, null, aiKey);
			}

		public ColEntry(Column col, String javaName, String setter, Boolean aiKey)
			{
			super(javaName, setter);
			this.col=col;
			}
		}

	public static class EntityEntry extends Entry
		{
		public Entity<?> entity;

		public EntityEntry(Entity<?> entity, String javaName, String setter)
			{
			super(javaName, setter);
			this.entity=entity;
			}
		}
	}
