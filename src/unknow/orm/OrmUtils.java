package unknow.orm;

import java.lang.reflect.*;
import java.math.*;
import java.sql.*;
import java.sql.Array;
import java.sql.Date;
import java.util.*;

import unknow.common.*;
import unknow.orm.mapping.*;
import unknow.orm.mapping.Entity.ColEntry;
import unknow.orm.mapping.Entity.Entry;
import unknow.orm.mapping.Entity.*;

public class OrmUtils
	{
	public static Object convert(int sqlType, String type, ResultSet rs, String name) throws SQLException
		{
		switch (sqlType)
			{
			case Types.ARRAY:
				return rs.getArray(name);
			case Types.BIGINT:
				return rs.getLong(name);
			case Types.BINARY:
			case Types.LONGVARBINARY:
			case Types.VARBINARY:
			case Types.BIT:
				return rs.getBytes(name);
			case Types.BLOB:
				return rs.getBlob(name);
			case Types.BOOLEAN:
				return rs.getBoolean(name);
			case Types.CHAR:
			case Types.LONGNVARCHAR:
			case Types.LONGVARCHAR:
			case Types.NCHAR:
			case Types.NVARCHAR:
			case Types.VARCHAR:
				return rs.getString(name);
			case Types.CLOB:
			case Types.NCLOB:
				return rs.getClob(name);
			case Types.DATE:
				return rs.getDate(name);
			case Types.DECIMAL:
			case Types.NUMERIC:
			case Types.FLOAT:
				return rs.getBigDecimal(name);
			case Types.DOUBLE:
			case Types.REAL:
				return rs.getDouble(name);
			case Types.INTEGER:
				return rs.getInt(name);
			case Types.NULL:
				return null;
			case Types.REF:
				return rs.getRef(name);
			case Types.ROWID:
				return rs.getRowId(name);
			case Types.SMALLINT:
				return rs.getShort(name);
			case Types.SQLXML:
				return rs.getSQLXML(name);
			case Types.TIME:
				return rs.getTime(name);
			case Types.TIMESTAMP:
				return rs.getTimestamp(name);
			case Types.TINYINT:
				return rs.getByte(name);
			case Types.JAVA_OBJECT:
			case Types.OTHER:
			case Types.STRUCT:
			default:
				throw new SQLException("no conversion found for type '"+type+"'");
			}
		}

	public static Class<?> toJavaType(int sqlType, String type) throws SQLException
		{
		switch (sqlType)
			{
			case Types.ARRAY:
				return Array.class;
			case Types.BIGINT:
				return Long.class;
			case Types.BINARY:
			case Types.LONGVARBINARY:
			case Types.VARBINARY:
			case Types.BIT:
				return Byte[].class;
			case Types.BLOB:
				return Blob.class;
			case Types.BOOLEAN:
				return Boolean.class;
			case Types.CHAR:
			case Types.LONGNVARCHAR:
			case Types.LONGVARCHAR:
			case Types.NCHAR:
			case Types.NVARCHAR:
			case Types.VARCHAR:
				return String.class;
			case Types.CLOB:
			case Types.NCLOB:
				return Clob.class;
			case Types.DATE:
				return Date.class;
			case Types.DECIMAL:
			case Types.NUMERIC:
			case Types.FLOAT:
				return BigDecimal.class;
			case Types.DOUBLE:
			case Types.REAL:
				return Double.class;
			case Types.INTEGER:
				return Integer.class;
			case Types.NULL:
				return null;
			case Types.REF:
				return Ref.class;
			case Types.ROWID:
				return RowId.class;
			case Types.SMALLINT:
				return Short.class;
			case Types.SQLXML:
				return SQLXML.class;
			case Types.TIME:
				return Time.class;
			case Types.TIMESTAMP:
				return Timestamp.class;
			case Types.TINYINT:
				return Byte.class;
			case Types.JAVA_OBJECT:
			case Types.OTHER:
			case Types.STRUCT:
			default:
				throw new SQLException("no conversion found for type '"+type+"'");
			}
		}

	public static int appendValue(Query q, int i, String table, Entry e, Object o) throws SQLException
		{
		if(e instanceof ColEntry)
			{
			q.setObject(i++, getValue((ColEntry)e, o));
			}
		else
			{
			for(Entry en:((EntityEntry)e).entity.entities.get(table))
				i=appendValue(q, i, table, en, o);
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

	public static void appendUpdate(StringBuilder sql, String t, Entry e)
		{
		if(e instanceof Entity.ColEntry)
			{
			String col=((ColEntry)e).col.getName();
			sql.append(col).append("= ?");
			}
		else
			{
			boolean first=true;
			for(Entry en:((EntityEntry)e).entity.entities.get(t))
				{
				if(first)
					first=false;
				else
					sql.append(",");
				appendUpdate(sql, t, en);
				}
			}
		}

	public static void appendColName(List<Entity.ColEntry> keys, StringBuilder sql, String t, Entry e)
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
			for(Entry en:((EntityEntry)e).entity.entities.get(t))
				{
				if(first)
					first=false;
				else
					sql.append(",");
				appendColName(keys, sql, t, en);
				}
			}
		}

	public static void appendInsertValues(StringBuilder sql, String t, Entry e)
		{
		if(e instanceof Entity.ColEntry)
			sql.append("?");
		else
			{
			boolean first=true;
			for(Entry en:((EntityEntry)e).entity.entities.get(t))
				{
				if(first)
					first=false;
				else
					sql.append(",");
				appendInsertValues(sql, t, en);
				}
			}
		}
	}
