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

public abstract class QueryResult
	{
	protected ResultSet rs;
	protected Map<String,Class<?>> mapping;
	protected Database db;
	
	protected QueryResult()
		{
		}
	protected QueryResult(Database db, ResultSet rs, Map<String,Class<?>> mapping)
		{
		this.db=db;
		this.rs=rs;
		this.mapping=mapping;
		}

	public boolean next() throws SQLException
		{
		return rs.next();
		}

	public Array getArray(String columnLabel) throws SQLException
		{
		return rs.getArray(columnLabel);
		}

	public InputStream getAsciiStream(String columnLabel) throws SQLException
		{
		return rs.getAsciiStream(columnLabel);
		}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException
		{
		return rs.getBigDecimal(columnLabel);
		}

	public InputStream getBinaryStream(String columnLabel) throws SQLException
		{
		return rs.getBinaryStream(columnLabel);
		}

	public Blob getBlob(String columnLabel) throws SQLException
		{
		return rs.getBlob(columnLabel);
		}

	boolean getBoolean(String columnLabel) throws SQLException
		{
		return rs.getBoolean(columnLabel);
		}

	public byte getByte(String columnLabel) throws SQLException
		{
		return rs.getByte(columnLabel);
		}

	public byte[] getBytes(String columnLabel) throws SQLException
		{
		return rs.getBytes(columnLabel);
		}

	public Reader getCharacterStream(String columnLabel) throws SQLException
		{
		return rs.getCharacterStream(columnLabel);
		}

	public Clob getClob(String columnLabel) throws SQLException
		{
		return rs.getClob(columnLabel);
		}

	public Date getDate(String columnLabel) throws SQLException
		{
		return rs.getDate(columnLabel);
		}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException
		{
		return rs.getDate(columnLabel, cal);
		}

	public double getDouble(String columnLabel) throws SQLException
		{
		return rs.getDouble(columnLabel);
		}

	public float getFloat(String columnLabel) throws SQLException
		{
		return rs.getFloat(columnLabel);
		}

	public int getInt(String columnLabel) throws SQLException
		{
		return rs.getInt(columnLabel);
		}

	public long getLong(String columnLabel) throws SQLException
		{
		return rs.getLong(columnLabel);
		}

	public Reader getNCharacterStream(String columnLabel) throws SQLException
		{
		return rs.getNCharacterStream(columnLabel);
		}

	public NClob getNClob(String columnLabel) throws SQLException
		{
		return rs.getNClob(columnLabel);
		}

	public String getNString(String columnLabel) throws SQLException
		{
		return rs.getNString(columnLabel);
		}

	public Object getObject(String columnLabel) throws SQLException
		{
		return rs.getObject(columnLabel);
		}

	public Object getObject(String columnLabel, Map<String,Class<?>> map) throws SQLException
		{
		return rs.getObject(columnLabel, map);
		}

	public Ref getRef(String columnLabel) throws SQLException
		{
		return rs.getRef(columnLabel);
		}

	public RowId getRowId(String columnLabel) throws SQLException
		{
		return rs.getRowId(columnLabel);
		}

	public short getShort(String columnLabel) throws SQLException
		{
		return rs.getShort(columnLabel);
		}

	public SQLXML getSQLXML(String columnLabel) throws SQLException
		{
		return rs.getSQLXML(columnLabel);
		}

	public String getString(String columnLabel) throws SQLException
		{
		return rs.getString(columnLabel);
		}

	public Time getTime(String columnLabel) throws SQLException
		{
		return rs.getTime(columnLabel);
		}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException
		{
		return rs.getTime(columnLabel, cal);
		}

	public Timestamp getTimestamp(String columnLabel) throws SQLException
		{
		return rs.getTimestamp(columnLabel);
		}

	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException
		{
		return rs.getTimestamp(columnLabel, cal);
		}

	public URL getURL(String columnLabel) throws SQLException
		{
		return rs.getURL(columnLabel);
		}

	public void close() throws SQLException
		{
		rs.close();
		}
	
	public Object getEntity(String alias) throws SQLException
		{
		Class<?> clazz=mapping.get(alias);
		if(clazz==null)
			throw new SQLException("Alias '"+alias+"' not mapped");
		Table table=db.getMapping(clazz);
		return table.build(alias, clazz, rs);
		}
	}
