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
import unknow.common.data.*;
import unknow.orm.reflect.*;

public class Entity<T>
	{
	private static final Logger logger=LogManager.getFormatterLogger(Entity.class);

	private Instantiator<T> instantiator;

	public final Set<Entry> entries;
	public Table table;

	public Entity(ReflectFactory reflect, Class<T> cl, Table t, Set<Entry> ent)
		{
		instantiator=reflect.createInstantiator(cl);
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
				if(alias!=null)
					sql.append(alias).append('.');
				sql.append(col);
				sql.append(" as ");
				sql.append('"');
				if(alias!=null)
					sql.append(alias).append('.');
				sql.append(col).append('"');
				}
			else
				((EntityEntry)e).entity.append(sql, alias, restrictions);
			}
		}

	public T build(Database database, String alias, ResultSet rs) throws SQLException
		{
		ResultSetMetaData metaData=rs.getMetaData();
		int columnCount=metaData.getColumnCount();
		List<String> colomnLabels=new SortedList<String>(columnCount);
		for(int i=0; i<columnCount; i++)
			colomnLabels.add(metaData.getColumnLabel(i+1));
		return build(database, alias, rs, colomnLabels);
		}

	public T build(Database database, String alias, ResultSet rs, List<String> columnLabels) throws SQLException
		{
		try
			{
			T entity=instantiator.newInstance();
			for(Entry e:entries)
				{
				Object value;
				if(e instanceof ColEntry)
					{
					ColEntry c=(ColEntry)e;
					String label=c.col.getName();
					if(alias!=null)
						label=alias+'.'+label;
					if(columnLabels.contains(label))
						value=database.convert(c.type, c.col, rs, label);
					else
						value=database.defaultValue(c.type, c.col);
					}
				else
					value=((EntityEntry)e).entity.build(database, alias, rs, columnLabels);

				try
					{
					e.setter.set(entity, value);
					}
				catch (ReflectException ex)
					{
					throw new SQLException(ex);
					}
				}
			logger.trace("build '"+instantiator.className()+"' => '"+entity+"'");
			return entity;
			}
		catch (ReflectException e)
			{
			throw new SQLException("Can't create '"+instantiator.className()+"'", e);
			}
		}

	public ColEntry findCol(String property) throws SQLException
		{
		for(Entry e:entries)
			{
			if(e.javaName.equals(property))
				{
				if(e instanceof ColEntry)
					return (ColEntry)e;
				throw new SQLException("composite not supported"); // TODO
				}
			}
		throw new SQLException("Property '"+property+"' not found on '"+instantiator.className()+"'");
		}

	public static class Entry
		{
		public final String javaName;

		public final Getter getter;
		public final Setter setter;

		public final Class<?> type;

		public Entry(ReflectFactory reflect, Class<?> clazz, String javaName, String setter) throws SQLException
			{
			this.javaName=javaName;

			// TODO setter & javaName format
			setter=setter!=null?setter:"set"+Character.toUpperCase(javaName.charAt(0))+javaName.substring(1);
			String getter="get"+Character.toUpperCase(javaName.charAt(0))+javaName.substring(1);

			Field field=Reflection.getField(clazz, javaName);
			if(field==null)
				throw new SQLException("field '"+javaName+"' not found on '"+clazz+"'");
			Method sm=Reflection.getMethod(clazz, setter, field.getType());
			Method gm=Reflection.getMethod(clazz, getter, field.getType());

			this.type=field.getType();
			this.setter=reflect.createSetter(field, sm);
			this.getter=reflect.createGetter(field, gm);
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

		public ColEntry(ReflectFactory reflect, Class<?> clazz, Column col, String jname, boolean ai, boolean key) throws SQLException
			{
			this(reflect, clazz, col, jname, null, ai, key);
			}

		public ColEntry(ReflectFactory reflect, Class<?> clazz, Column col, String javaName, String setter, boolean ai, boolean key) throws SQLException
			{
			super(reflect, clazz, javaName, setter);
			this.col=col;
			this.ai=ai;
			this.key=key;
			}
		}

	public static class EntityEntry extends Entry
		{
		public Entity<?> entity;

		public EntityEntry(ReflectFactory reflect, Class<?> clazz, Entity<?> entity, String javaName, String setter) throws SQLException
			{
			super(reflect, clazz, javaName, setter);
			this.entity=entity;
			}
		}
	}
