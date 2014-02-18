package unknow.orm.mapping;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

public class Entity<T>
	{
	private Class<T> clazz;

	private Set<Entry>[] columns;

	private Database database;

	public Entity(Database db, Class<T> cl, Set<Entry>[] col)
		{
		database=db;
		clazz=cl;
		columns=col;
		}

	public void append(StringBuilder sql, String alias) throws SQLException
		{
		String[] a=alias.split(",");
		if(a.length!=columns.length)
			throw new SQLException("aliases count differ from table count");
		boolean first=true;
		for(int i=0; i<a.length; i++)
			{
			for(Entry e:columns[i])
				{
				String col=e.col.getName();
				if(!first)
					sql.append(',');
				else
					first=false;
				sql.append(a[i]).append('.').append(col);
				sql.append(" as ");
				sql.append('"').append(a[i]).append('.').append(col).append('"');
				}
			}
		}

	public T build(String alias, ResultSet rs) throws SQLException
		{
		String[] a=alias.split(",");
		if(a.length!=columns.length)
			throw new SQLException("aliases count differ from table count");
		try
			{
			T entity=clazz.newInstance();
			for(int i=0; i<a.length; i++)
				{
				for(Entry c:columns[i])
					{
					Class<?> type=database.toJavaType(c.col.getSqlType(), c.col.getType());
					Object value=database.convert(c.col.getSqlType(), c.col.getType(), rs, a[i]+"."+c.col.getName());
					try
						{
						try
							{
							Method m=clazz.getMethod(c.setter, type);
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
							Field field=clazz.getDeclaredField(c.javaName);
							field.set(entity, value);
							}
						catch (NoSuchFieldException e)
							{
							throw new SQLException("no accessible '"+c.setter+"("+type.getName()+")' or '"+c.javaName+"' in '"+clazz.getName()+"'");
							}
						catch (SecurityException e)
							{
							throw new SQLException("can't set '"+c.javaName+"' in '"+clazz.getName()+"'", e);
							}
						}
					catch (IllegalAccessException e)
						{
						throw new SQLException("can't execute '"+c.setter+"("+type.getName()+")' or set '"+c.javaName+"' in '"+clazz.getName()+"'", e);
						}
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

	static class Entry
		{
		public String setter;
		public String javaName;
		public Column col;
		public Entry(Column col, String javaName, String setter)	// TODO setter & javaName format
			{
			this.col=col;
			this.javaName=javaName!=null?javaName:col.getName();
			this.setter=setter!=null?setter:"set"+Character.toUpperCase(javaName.charAt(0))+javaName.substring(1);
			}
		}
	}
