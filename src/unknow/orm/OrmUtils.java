package unknow.orm;

import java.lang.reflect.*;
import java.math.*;
import java.sql.*;
import java.sql.Array;
import java.sql.Date;
import java.util.*;

import unknow.common.*;
import unknow.orm.mapping.*;
import unknow.orm.mapping.Entity.*;

public class OrmUtils
	{
	@SuppressWarnings("unchecked")
	private static <T> T checkInt(Class<T> expected, Column col, ResultSet rs, String label) throws SQLException
		{
		if(byte.class.equals(expected)||Byte.class.equals(expected))
			return (T)(Byte)rs.getByte(label);
		if(short.class.equals(expected)||Short.class.equals(expected))
			return (T)(Short)rs.getShort(label);
		if(int.class.equals(expected)||Integer.class.equals(expected))
			return (T)(Integer)rs.getInt(label);
		if(long.class.equals(expected)||Long.class.equals(expected))
			return (T)(Long)rs.getLong(label);
		return null;
		}

	@SuppressWarnings("unchecked")
	private static <T> T checkFloat(Class<T> expected, Column col, ResultSet rs, String label) throws SQLException
		{
		if(float.class.equals(expected)||Float.class.equals(expected))
			return (T)(Float)rs.getFloat(label);
		if(float.class.equals(expected)||Float.class.equals(expected))
			return (T)(Float)rs.getFloat(label);
		if(float.class.equals(expected)||Float.class.equals(expected))
			{
			BigDecimal v=rs.getBigDecimal(label);
			return (T)(v==null?BigDecimal.ZERO:v);
			}
		return null;
		}

	private static final List<Integer> BOOLEAN_TYPES=Arrays.asList(Types.BOOLEAN, Types.BIT, Types.TINYINT, Types.SMALLINT, Types.INTEGER, Types.BIGINT);

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> T convert(Class<T> expected, Column col, ResultSet rs, String label) throws SQLException
		{
		if(expected.equals(String.class))
			return (T)rs.getString(label);
		if(expected.equals(boolean.class)||expected.equals(Boolean.class))
			{
			if(BOOLEAN_TYPES.contains(col.getSqlType()))
				{
				boolean b=rs.getBoolean(label);
				return (T)(rs.wasNull()?null:b);
				}
			}
		T r=checkInt(expected, col, rs, label);
		if(r!=null)
			return rs.wasNull()?null:r;

		r=checkFloat(expected, col, rs, label);
		if(r!=null)
			return rs.wasNull()?null:r;

		if(Reflection.isAssignable(Enum.class, expected))
			{
			return (T)Enum.valueOf((Class<Enum>)expected, rs.getString(label));
			}

		switch (col.getSqlType())
			{
			case Types.NULL:
				return null;
			case Types.ARRAY:
				if(Array.class.equals(expected))
					return (T)rs.getArray(label);
				throw new SQLException("can't convert sql Array to '"+expected.getName()+"'");

			case Types.BIT:
			case Types.BINARY:
			case Types.LONGVARBINARY:
			case Types.VARBINARY:
				if(byte[].class.equals(expected))
					return (T)rs.getBytes(label);
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");

			case Types.BLOB:
				if(expected.equals(Blob.class))
					return (T)rs.getBlob(label);
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");

			case Types.CLOB:
				if(expected.equals(Clob.class))
					return (T)rs.getClob(label);
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");

			case Types.NCLOB:
				if(expected.equals(NClob.class))
					return (T)rs.getNClob(label);
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");

			case Types.DATE:
				if(expected.equals(Date.class))
					return (T)rs.getDate(label);
				else if(expected.equals(Calendar.class))
					{
					Date d=rs.getDate(label);
					if(d==null)
						return null;
					Calendar c=Calendar.getInstance();
					c.setTimeInMillis(d.getTime());
					return (T)c;
					}
				else if(expected.equals(java.util.Date.class))
					{
					Date d=rs.getDate(label);
					if(d==null)
						return null;
					return (T)new java.util.Date(d.getTime());
					}
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");
			case Types.TIME:
				if(expected.equals(Time.class))
					return (T)rs.getTime(label);
				else if(expected.equals(Calendar.class))
					{
					Time d=rs.getTime(label);
					if(d==null)
						return null;
					Calendar c=Calendar.getInstance();
					c.setTimeInMillis(d.getTime());
					return (T)c;
					}
				else if(expected.equals(java.util.Date.class))
					{
					Time d=rs.getTime(label);
					if(d==null)
						return null;
					return (T)new java.util.Date(d.getTime());
					}
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");

			case Types.TIMESTAMP:
				if(expected.equals(Timestamp.class))
					return (T)rs.getTimestamp(label);
				else if(expected.equals(Calendar.class))
					{
					Timestamp d=rs.getTimestamp(label);
					if(d==null)
						return null;
					Calendar c=Calendar.getInstance();
					c.setTimeInMillis(d.getTime());
					return (T)c;
					}
				else if(expected.equals(java.util.Date.class))
					{
					Timestamp d=rs.getTimestamp(label);
					if(d==null)
						return null;
					return (T)new java.util.Date(d.getTime());
					}
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");

			case Types.REF:
				if(expected.equals(Ref.class))
					return (T)rs.getRef(label);
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");
			case Types.ROWID:
				if(expected.equals(RowId.class))
					return (T)rs.getRowId(label);
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");
			case Types.SQLXML:
				if(expected.equals(SQLXML.class))
					return (T)rs.getSQLXML(label);
				throw new SQLException("can't convert '"+col.getType()+"' to '"+expected.getName()+"'");
			case Types.JAVA_OBJECT:
			case Types.OTHER:
			case Types.STRUCT:
			default:
				throw new SQLException("no conversion found for type '"+col.getType()+"'");
			}
		}

	public static int appendValue(Query q, int i, Entry e, Object o) throws SQLException
		{
		if(e instanceof ColEntry)
			{
			q.setObject(i++, getValue((ColEntry)e, o));
			}
		else
			{
			for(Entry en:((EntityEntry)e).entity.entries)
				i=appendValue(q, i, en, o);
			}
		return i;
		}

	public static Object getValue(ColEntry e, Object o) throws SQLException
		{
		Class<?> cl=o.getClass();
		try
			{
			Method m=Reflection.getMethod(cl, e.getter);
			if(m==null)
				{
				Field f=Reflection.getField(cl, e.javaName);
				if(f!=null)
					return f.get(o);
				else
					throw new SQLException("can't found '"+e.javaName+"' or '"+e.getter+"()' in '"+cl.getName()+"'");
				}
			else
				return m.invoke(o);
			}
		catch (Exception e1)
			{
			throw new SQLException(e1);
			}
		}

	public static void setValue(ColEntry e, Object o, Object v) throws SQLException
		{
		Class<?> cl=o.getClass();
		try
			{
			Method m=Reflection.getMethod(cl, e.setter, v.getClass());
			if(m==null)
				{
				Field f=Reflection.getField(cl, e.javaName, v.getClass());
				if(f!=null)
					f.set(o, v);
				else
					throw new SQLException("can't found '"+e.javaName+"' or '"+e.getter+"()' in '"+cl.getName()+"'");
				}
			else
				m.invoke(o, v);
			}
		catch (Exception e1)
			{
			throw new SQLException(e1);
			}
		}

	public static void appendUpdate(StringBuilder sql, Entry e)
		{
		if(e instanceof Entity.ColEntry)
			{
			String col=((ColEntry)e).col.getName();
			sql.append(col).append("= ?");
			}
		else
			{
			boolean first=true;
			for(Entry en:((EntityEntry)e).entity.entries)
				{
				if(first)
					first=false;
				else
					sql.append(",");
				appendUpdate(sql, en);
				}
			}
		}

	public static void appendColName(List<Entity.ColEntry> keys, StringBuilder sql, Entry e)
		{
		if(e instanceof Entity.ColEntry)
			{
			sql.append(((ColEntry)e).col.getName());
			if(((ColEntry)e).aiKey!=null&&((ColEntry)e).aiKey)
				keys.add((ColEntry)e);
			}
		else
			{
			boolean first=false;
			for(Entry en:((EntityEntry)e).entity.entries)
				{
				if(first)
					first=false;
				else
					sql.append(",");
				appendColName(keys, sql, en);
				}
			}
		}

	public static void appendInsertValues(StringBuilder sql, Entry e)
		{
		if(e instanceof Entity.ColEntry)
			sql.append("?");
		else
			{
			boolean first=true;
			for(Entry en:((EntityEntry)e).entity.entries)
				{
				if(first)
					first=false;
				else
					sql.append(",");
				appendInsertValues(sql, en);
				}
			}
		}
	}
