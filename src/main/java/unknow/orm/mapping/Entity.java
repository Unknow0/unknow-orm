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

	public final Set<Entry> entries;
	public Table table;

	public Entity(Class<T> cl, Table t, Set<Entry> ent)
		{
		clazz=cl;
		table=t;
		entries=ent;
		}

	public void append(StringBuilder sql, String alias, Collection<String> restrictions)
		{
		boolean first=true;
		for(Entry e:entries)
			{
			if(restrictions!=null&&!restrictions.contains(e.javaName))
				continue;
			if(!first)
				sql.append(',');
			else
				first=false;
			if(e instanceof ColEntry)
				{
				String col=((ColEntry)e).col.getName();
				sql.append(alias).append('.').append(col);
				sql.append(" as ");
				sql.append('"').append(alias).append('.').append(col).append('"');
				}
			else
				((EntityEntry)e).entity.append(sql, alias, restrictions);
			}
		}

	public T build(Database database, String alias, ResultSet rs) throws SQLException
		{
		try
			{
			T entity=clazz.newInstance();
			for(Entry e:entries)
				{
				Field field=Reflection.getField(clazz, e.javaName);
				Class<?> expected=field.getType();

				Object value;
				if(e instanceof ColEntry)
					{
					ColEntry c=(ColEntry)e;
					value=database.convert(expected, c.col, rs, alias);
					}
				else
					value=((EntityEntry)e).entity.build(database, alias, rs);
				try
					{
					try
						{
						Method m=Reflection.getMethod(clazz, e.setter, expected);
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
					throw new SQLException("can't execute '"+e.setter+"("+expected.getName()+")' or set '"+e.javaName+"' in '"+clazz.getName()+"'", ex);
					}
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

	public Entry findCol(String property)
		{
		for(Entry e:entries)
			{
			if(e.javaName.equals(property))
				return e;
			}
		return null;
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

		public String toString()
			{
			return javaName;
			}
		}

	public static class ColEntry extends Entry
		{
		public Column col;
		public boolean ai;
		public boolean key;

		public ColEntry(Column col, String jname, boolean ai, boolean key)
			{
			this(col, jname, null, ai, key);
			}

		public ColEntry(Column col, String javaName, String setter, boolean ai, boolean key)
			{
			super(javaName, setter);
			this.col=col;
			this.ai=ai;
			this.key=key;
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
