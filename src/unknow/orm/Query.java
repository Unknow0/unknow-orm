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
package unknow.orm;

import java.io.*;
import java.math.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import unknow.orm.mapping.*;

public class Query implements AutoCloseable
	{
	private Map<String,Integer> paramPos=new HashMap<String,Integer>();
	private Map<String,Class<?>> aliasMapping=new HashMap<String, Class<?>>();
	
	private Database db;
	private Connection co=null;
	private PreparedStatement st=null;
	
	private Result result=null;

	public Query(Database db, CharSequence query, String[] alias, Class<?>[] entities) throws SQLException
		{
		this.db=db;
		if(alias.length!=entities.length)
			throw new SQLException("alias & entity size missmatch");
		aliasMapping=new HashMap<String,Class<?>>();
		for(int i=0; i<alias.length; i++)
			aliasMapping.put(alias[i], entities[i]);
		init(query);
		}

	public Query(Database db, CharSequence query, Map<String,Class<?>> aliasMapping) throws SQLException
		{
		this.db=db;
		this.aliasMapping.putAll(aliasMapping);
		init(query);
		}

	public Query(Database db, String query) throws SQLException
		{
		this(db, query, new HashMap<String,Class<?>>());
		}

	private void init(CharSequence query) throws SQLException
		{
		StringBuilder sb=new StringBuilder();
		StringBuilder tmp=new StringBuilder();
		int i=0;
		int len=query.length();
		int curParamPos=1;
		while (i<len)
			{
			char c=query.charAt(i);
			switch (c)
				{
				case '{':
					c=query.charAt(++i);
					while (i<len&&c!='}')
						{
						tmp.append(c);
						c=query.charAt(++i);
						}
					String alias=tmp.toString();
					if(aliasMapping==null||!aliasMapping.containsKey(alias))
						throw new SQLException("alias '"+alias+"' not in alias mapping");
					Class<?> clazz=aliasMapping.get(alias);
					Table table=db.getMapping(clazz);
					if(table==null)
						throw new SQLException("no mapping found for '"+clazz+"'");
					table.append(sb, alias);
					tmp.setLength(0);
					i++;
					break;
				case ':':
					c=query.charAt(++i);
					while (Character.isLetter(c) && i<len)
						{
						tmp.append(c);
						if(++i<len)
							c=query.charAt(i);
						}
					paramPos.put(tmp.toString(), curParamPos++);
					tmp.setLength(0);
					sb.append('?');
					break;
				case '?':
					curParamPos++;
				default:
					sb.append(c);
					i++;
				}
			}
		co=db.getConnection();
		st=co.prepareStatement(sb.toString());
		}
	
	public QueryResult execute() throws SQLException
		{
		if(result!=null)
			result.close();
		result=new Result(db, st.executeQuery(), aliasMapping);
		return result;
		}

	public String toString()
		{
		return st.toString();
		}

	public void close() throws SQLException
		{
		if(result!=null)
			result.close();
		if(st!=null)
			st.close();
		if(co!=null)
			co.close();
		}

	public void setArray(int parameterIndex, Array x) throws SQLException
		{
		st.setArray(parameterIndex, x);
		}

	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException
		{
		st.setAsciiStream(parameterIndex, x);
		}

	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException
		{
		st.setAsciiStream(parameterIndex, x, length);
		}

	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException
		{
		st.setAsciiStream(parameterIndex, x, length);
		}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException
		{
		st.setBigDecimal(parameterIndex, x);
		}

	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException
		{
		st.setBinaryStream(parameterIndex, x);
		}

	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException
		{
		st.setBinaryStream(parameterIndex, x, length);
		}

	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException
		{
		st.setBinaryStream(parameterIndex, x, length);
		}

	public void setBlob(int parameterIndex, Blob x) throws SQLException
		{
		st.setBlob(parameterIndex, x);
		}

	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException
		{
		st.setBlob(parameterIndex, inputStream);
		}

	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException
		{
		st.setBlob(parameterIndex, inputStream, length);
		}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException
		{
		st.setBoolean(parameterIndex, x);
		}

	public void setByte(int parameterIndex, byte x) throws SQLException
		{
		st.setByte(parameterIndex, x);
		}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException
		{
		st.setBytes(parameterIndex, x);
		}

	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException
		{
		st.setCharacterStream(parameterIndex, reader);
		}

	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException
		{
		st.setCharacterStream(parameterIndex, reader, length);
		}

	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException
		{
		st.setCharacterStream(parameterIndex, reader, length);
		}

	public void setClob(int parameterIndex, Clob x) throws SQLException
		{
		st.setClob(parameterIndex, x);
		}

	public void setClob(int parameterIndex, Reader reader) throws SQLException
		{
		st.setClob(parameterIndex, reader);
		}

	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException
		{
		st.setClob(parameterIndex, reader, length);
		}

	public void setDate(int parameterIndex, Date x) throws SQLException
		{
		st.setDate(parameterIndex, x);
		}

	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException
		{
		st.setDate(parameterIndex, x, cal);
		}

	public void setDouble(int parameterIndex, double x) throws SQLException
		{
		st.setDouble(parameterIndex, x);
		}

	public void setFloat(int parameterIndex, float x) throws SQLException
		{
		st.setFloat(parameterIndex, x);
		}

	public void setInt(int parameterIndex, int x) throws SQLException
		{
		st.setInt(parameterIndex, x);
		}

	public void setLong(int parameterIndex, long x) throws SQLException
		{
		st.setLong(parameterIndex, x);
		}

	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException
		{
		st.setNCharacterStream(parameterIndex, value);
		}

	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException
		{
		st.setNCharacterStream(parameterIndex, value, length);
		}

	public void setNClob(int parameterIndex, NClob value) throws SQLException
		{
		st.setNClob(parameterIndex, value);
		}

	public void setNClob(int parameterIndex, Reader reader) throws SQLException
		{
		st.setNClob(parameterIndex, reader);
		}

	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException
		{
		st.setNClob(parameterIndex, reader, length);
		}

	public void setNString(int parameterIndex, String value) throws SQLException
		{
		st.setNString(parameterIndex, value);
		}

	public void setNull(int parameterIndex, int sqlType) throws SQLException
		{
		st.setNull(parameterIndex, sqlType);
		}

	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException
		{
		st.setNull(parameterIndex, sqlType, typeName);
		}

	public void setObject(int parameterIndex, Object x) throws SQLException
		{
		st.setObject(parameterIndex, x);
		}

	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException
		{
		st.setObject(parameterIndex, x, targetSqlType);
		}

	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException
		{
		st.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
		}

	public void setRef(int parameterIndex, Ref x) throws SQLException
		{
		st.setRef(parameterIndex, x);
		}

	public void setRowId(int parameterIndex, RowId x) throws SQLException
		{
		st.setRowId(parameterIndex, x);
		}

	public void setShort(int parameterIndex, short x) throws SQLException
		{
		st.setShort(parameterIndex, x);
		}

	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException
		{
		st.setSQLXML(parameterIndex, xmlObject);
		}

	public void setString(int parameterIndex, String x) throws SQLException
		{
		st.setString(parameterIndex, x);
		}

	public void setTime(int parameterIndex, Time x) throws SQLException
		{
		st.setTime(parameterIndex, x);
		}

	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException
		{
		st.setTime(parameterIndex, x, cal);
		}

	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException
		{
		st.setTimestamp(parameterIndex, x);
		}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException
		{
		st.setTimestamp(parameterIndex, x, cal);
		}

	public void setURL(int parameterIndex, URL x) throws SQLException
		{
		st.setURL(parameterIndex, x);
		}

	public void setArray(String param, Array x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setArray(parameterIndex, x);
		}

	public void setAsciiStream(String param, InputStream x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setAsciiStream(parameterIndex, x);
		}

	public void setAsciiStream(String param, InputStream x, int length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setAsciiStream(parameterIndex, x, length);
		}

	public void setAsciiStream(String param, InputStream x, long length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setAsciiStream(parameterIndex, x, length);
		}

	public void setBigDecimal(String param, BigDecimal x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBigDecimal(parameterIndex, x);
		}

	public void setBinaryStream(String param, InputStream x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBinaryStream(parameterIndex, x);
		}

	public void setBinaryStream(String param, InputStream x, int length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBinaryStream(parameterIndex, x, length);
		}

	public void setBinaryStream(String param, InputStream x, long length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBinaryStream(parameterIndex, x, length);
		}

	public void setBlob(String param, Blob x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBlob(parameterIndex, x);
		}

	public void setBlob(String param, InputStream inputStream) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBlob(parameterIndex, inputStream);
		}

	public void setBlob(String param, InputStream inputStream, long length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBlob(parameterIndex, inputStream, length);
		}

	public void setBoolean(String param, boolean x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBoolean(parameterIndex, x);
		}

	public void setByte(String param, byte x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setByte(parameterIndex, x);
		}

	public void setBytes(String param, byte[] x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setBytes(parameterIndex, x);
		}

	public void setCharacterStream(String param, Reader reader) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setCharacterStream(parameterIndex, reader);
		}

	public void setCharacterStream(String param, Reader reader, int length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setCharacterStream(parameterIndex, reader, length);
		}

	public void setCharacterStream(String param, Reader reader, long length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setCharacterStream(parameterIndex, reader, length);
		}

	public void setClob(String param, Clob x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setClob(parameterIndex, x);
		}

	public void setClob(String param, Reader reader) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setClob(parameterIndex, reader);
		}

	public void setClob(String param, Reader reader, long length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setClob(parameterIndex, reader, length);
		}

	public void setDate(String param, Date x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setDate(parameterIndex, x);
		}

	public void setDate(String param, Date x, Calendar cal) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setDate(parameterIndex, x, cal);
		}

	public void setDouble(String param, double x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setDouble(parameterIndex, x);
		}

	public void setFloat(String param, float x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setFloat(parameterIndex, x);
		}

	public void setInt(String param, int x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setInt(parameterIndex, x);
		}

	public void setLong(String param, long x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setLong(parameterIndex, x);
		}

	public void setNCharacterStream(String param, Reader value) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setNCharacterStream(parameterIndex, value);
		}

	public void setNCharacterStream(String param, Reader value, long length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setNCharacterStream(parameterIndex, value, length);
		}

	public void setNClob(String param, NClob value) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setNClob(parameterIndex, value);
		}

	public void setNClob(String param, Reader reader) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setNClob(parameterIndex, reader);
		}

	public void setNClob(String param, Reader reader, long length) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setNClob(parameterIndex, reader, length);
		}

	public void setNString(String param, String value) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setNString(parameterIndex, value);
		}

	public void setNull(String param, int sqlType) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setNull(parameterIndex, sqlType);
		}

	public void setNull(String param, int sqlType, String typeName) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setNull(parameterIndex, sqlType, typeName);
		}

	public void setObject(String param, Object x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setObject(parameterIndex, x);
		}

	public void setObject(String param, Object x, int targetSqlType) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setObject(parameterIndex, x, targetSqlType);
		}

	public void setObject(String param, Object x, int targetSqlType, int scaleOrLength) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
		}

	public void setRef(String param, Ref x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setRef(parameterIndex, x);
		}

	public void setRowId(String param, RowId x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setRowId(parameterIndex, x);
		}

	public void setShort(String param, short x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setShort(parameterIndex, x);
		}

	public void setSQLXML(String param, SQLXML xmlObject) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setSQLXML(parameterIndex, xmlObject);
		}

	public void setString(String param, String x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setString(parameterIndex, x);
		}

	public void setTime(String param, Time x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setTime(parameterIndex, x);
		}

	public void setTime(String param, Time x, Calendar cal) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setTime(parameterIndex, x, cal);
		}

	public void setTimestamp(String param, Timestamp x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setTimestamp(parameterIndex, x);
		}

	public void setTimestamp(String param, Timestamp x, Calendar cal) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setTimestamp(parameterIndex, x, cal);
		}

	public void setURL(String param, URL x) throws SQLException
		{
		Integer parameterIndex=paramPos.get(param);
		if(param==null)
			throw new SQLException("param '"+param+"' not in this query");
		st.setURL(parameterIndex, x);
		}
	
	private static class Result extends QueryResult
		{
		public Result(Database db, ResultSet rs, Map<String,Class<?>> mapping)
			{
			super(db, rs, mapping);
			}
		}
	}
