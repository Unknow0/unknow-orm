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
package unknow.orm;

import java.io.*;
import java.math.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import unknow.orm.mapping.*;

public abstract class QueryResult implements AutoCloseable
	{
	protected ResultSet rs;
	protected Map<String,Entity<?>> mapping;
	protected Database db;

	protected QueryResult(Database db, ResultSet rs, Map<String,Entity<?>> mapping)
		{
		this.db=db;
		this.rs=rs;
		this.mapping=mapping;
		}

	/**
	 * @see java.sql.ResultSet#next()
	 */
	public boolean next() throws SQLException
		{
		if(rs==null)
			return false;
		return rs.next();
		}

	/**
	 * @see java.sql.ResultSet#getArray(String)
	 */
	public Array getArray(String columnLabel) throws SQLException
		{
		return rs.getArray(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getAsciiStream(String)
	 */
	public InputStream getAsciiStream(String columnLabel) throws SQLException
		{
		return rs.getAsciiStream(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getBigDecimal(String)
	 */
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException
		{
		return rs.getBigDecimal(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getBinaryStream(String)
	 */
	public InputStream getBinaryStream(String columnLabel) throws SQLException
		{
		return rs.getBinaryStream(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getBlob(String)
	 */
	public Blob getBlob(String columnLabel) throws SQLException
		{
		return rs.getBlob(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getBoolean(String)
	 */
	public Boolean getBoolean(String columnLabel) throws SQLException
		{
		boolean b=rs.getBoolean(columnLabel);
		return rs.wasNull()?null:b;
		}

	/**
	 * @see java.sql.ResultSet#getByte(String)
	 */
	public Byte getByte(String columnLabel) throws SQLException
		{
		byte b=rs.getByte(columnLabel);
		return rs.wasNull()?null:b;
		}

	/**
	 * @see java.sql.ResultSet#getBytes(String)
	 */
	public byte[] getBytes(String columnLabel) throws SQLException
		{
		return rs.getBytes(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getCharacterStream(String)
	 */
	public Reader getCharacterStream(String columnLabel) throws SQLException
		{
		return rs.getCharacterStream(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getClob(String)
	 */
	public Clob getClob(String columnLabel) throws SQLException
		{
		return rs.getClob(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getDate(String)
	 */
	public Date getDate(String columnLabel) throws SQLException
		{
		return rs.getDate(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getDate(String,Calendar)
	 */
	public Date getDate(String columnLabel, Calendar cal) throws SQLException
		{
		return rs.getDate(columnLabel, cal);
		}

	/**
	 * @see java.sql.ResultSet#getDouble(String)
	 */
	public Double getDouble(String columnLabel) throws SQLException
		{
		double v=rs.getDouble(columnLabel);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getFloat(String)
	 */
	public Float getFloat(String columnLabel) throws SQLException
		{
		float v=rs.getFloat(columnLabel);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getInt(String)
	 */
	public Integer getInt(String columnLabel) throws SQLException
		{
		int v=rs.getInt(columnLabel);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getLong(String)
	 */
	public Long getLong(String columnLabel) throws SQLException
		{
		long v=rs.getLong(columnLabel);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getNCharacterStream(String)
	 */
	public Reader getNCharacterStream(String columnLabel) throws SQLException
		{
		return rs.getNCharacterStream(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getNClob(String)
	 */
	public NClob getNClob(String columnLabel) throws SQLException
		{
		return rs.getNClob(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getNString(String)
	 */
	public String getNString(String columnLabel) throws SQLException
		{
		return rs.getNString(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getObject(String)
	 */
	public Object getObject(String columnLabel) throws SQLException
		{
		return rs.getObject(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getObject(String,Map<String,Class<?>>)
	 */
	public Object getObject(String columnLabel, Map<String,Class<?>> map) throws SQLException
		{
		return rs.getObject(columnLabel, map);
		}

	/**
	 * @see java.sql.ResultSet#getRef(String)
	 */
	public Ref getRef(String columnLabel) throws SQLException
		{
		return rs.getRef(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getRowId(String)
	 */
	public RowId getRowId(String columnLabel) throws SQLException
		{
		return rs.getRowId(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getShort(String)
	 */
	public Short getShort(String columnLabel) throws SQLException
		{
		short v=rs.getShort(columnLabel);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getSQLXML(String)
	 */
	public SQLXML getSQLXML(String columnLabel) throws SQLException
		{
		return rs.getSQLXML(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getString(String)
	 */
	public String getString(String columnLabel) throws SQLException
		{
		return rs.getString(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getTime(String)
	 */
	public Time getTime(String columnLabel) throws SQLException
		{
		return rs.getTime(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getTime(String,Calendar)
	 */
	public Time getTime(String columnLabel, Calendar cal) throws SQLException
		{
		return rs.getTime(columnLabel, cal);
		}

	/**
	 * @see java.sql.ResultSet#getTimestamp(String)
	 */
	public Timestamp getTimestamp(String columnLabel) throws SQLException
		{
		return rs.getTimestamp(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getTimestamp(String,Calendar)
	 */
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException
		{
		return rs.getTimestamp(columnLabel, cal);
		}

	/**
	 * @see java.sql.ResultSet#getURL(String)
	 */
	public URL getURL(String columnLabel) throws SQLException
		{
		return rs.getURL(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getArray(int)
	 */
	public Array getArray(int columnIndex) throws SQLException
		{
		return rs.getArray(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getAsciiStream(int)
	 */
	public InputStream getAsciiStream(int columnIndex) throws SQLException
		{
		return rs.getAsciiStream(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getBigDecimal(int)
	 */
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException
		{
		return rs.getBigDecimal(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getBinaryStream(int)
	 */
	public InputStream getBinaryStream(int columnIndex) throws SQLException
		{
		return rs.getBinaryStream(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getBlob(int)
	 */
	public Blob getBlob(int columnIndex) throws SQLException
		{
		return rs.getBlob(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getBoolean(int)
	 */
	public Boolean getBoolean(int columnIndex) throws SQLException
		{
		boolean v=rs.getBoolean(columnIndex);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getByte(int)
	 */
	public Byte getByte(int columnIndex) throws SQLException
		{
		byte v=rs.getByte(columnIndex);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getBytes(int)
	 */
	public byte[] getBytes(int columnIndex) throws SQLException
		{
		return rs.getBytes(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getCharacterStream(int)
	 */
	public Reader getCharacterStream(int columnIndex) throws SQLException
		{
		return rs.getCharacterStream(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getClob(int)
	 */
	public Clob getClob(int columnIndex) throws SQLException
		{
		return rs.getClob(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getDate(int)
	 */
	public Date getDate(int columnIndex) throws SQLException
		{
		return rs.getDate(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getDate(int,Calendar)
	 */
	public Date getDate(int columnIndex, Calendar cal) throws SQLException
		{
		return rs.getDate(columnIndex, cal);
		}

	/**
	 * @see java.sql.ResultSet#getDouble(int)
	 */
	public Double getDouble(int columnIndex) throws SQLException
		{
		double v=rs.getDouble(columnIndex);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getFloat(int)
	 */
	public Float getFloat(int columnIndex) throws SQLException
		{
		float v=rs.getFloat(columnIndex);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getInt(int)
	 */
	public Integer getInt(int columnIndex) throws SQLException
		{
		int v=rs.getInt(columnIndex);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getLong(int)
	 */
	public Long getLong(int columnIndex) throws SQLException
		{
		long v=rs.getLong(columnIndex);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getNCharacterStream(int)
	 */
	public Reader getNCharacterStream(int columnIndex) throws SQLException
		{
		return rs.getNCharacterStream(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getNClob(int)
	 */
	public NClob getNClob(int columnIndex) throws SQLException
		{
		return rs.getNClob(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getNString(int)
	 */
	public String getNString(int columnIndex) throws SQLException
		{
		return rs.getNString(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getObject(int)
	 */
	public Object getObject(int columnIndex) throws SQLException
		{
		return rs.getObject(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getObject(int,Map<String,Class<?>>)
	 */
	public Object getObject(int columnIndex, Map<String,Class<?>> map) throws SQLException
		{
		return rs.getObject(columnIndex, map);
		}

	/**
	 * @see java.sql.ResultSet#getRef(int)
	 */
	public Ref getRef(int columnIndex) throws SQLException
		{
		return rs.getRef(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getRowId(int)
	 */
	public RowId getRowId(int columnIndex) throws SQLException
		{
		return rs.getRowId(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getShort(int)
	 */
	public Short getShort(int columnIndex) throws SQLException
		{
		short v=rs.getShort(columnIndex);
		return rs.wasNull()?null:v;
		}

	/**
	 * @see java.sql.ResultSet#getSQLXML(int)
	 */
	public SQLXML getSQLXML(int columnIndex) throws SQLException
		{
		return rs.getSQLXML(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getString(int)
	 */
	public String getString(int columnIndex) throws SQLException
		{
		return rs.getString(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getTime
	 */
	public Time getTime(int columnIndex) throws SQLException
		{
		return rs.getTime(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getTime(int,Calendar)
	 */
	public Time getTime(int columnIndex, Calendar cal) throws SQLException
		{
		return rs.getTime(columnIndex, cal);
		}

	/**
	 * @see java.sql.ResultSet#getTimestamp(int)
	 */
	public Timestamp getTimestamp(int columnIndex) throws SQLException
		{
		return rs.getTimestamp(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#getTimestamp(int,Calendar)
	 */
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException
		{
		return rs.getTimestamp(columnIndex, cal);
		}

	/**
	 * @see java.sql.ResultSet#getURL(int)
	 */
	public URL getURL(int columnIndex) throws SQLException
		{
		return rs.getURL(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#close()
	 */
	public void close() throws SQLException
		{
		if(rs!=null)
			rs.close();
		}

	/**
	 * Build and return a entity.
	 * @param alias alias of the entity.
	 * @return The newly created entity
	 * @throws SQLException if an error occurs
	 */
	@SuppressWarnings("unchecked")
	public <T> T getEntity(String alias) throws SQLException
		{
		Entity<T> e=(Entity<T>)mapping.get(alias);
		if(e==null)
			throw new SQLException("Alias '"+alias+"' not found on this result");
		return e.build(db, alias, rs);
		}

	/**
	 * @see java.sql.ResultSet#absolute(int)
	 */
	public boolean absolute(int row) throws SQLException
		{
		if(rs==null)
			return false;
		return rs.absolute(row);
		}

	/**
	 * @see java.sql.ResultSet#afterLast()
	 */
	public void afterLast() throws SQLException
		{
		if(rs!=null)
			rs.afterLast();
		}

	/**
	 * @see java.sql.ResultSet#beforeFirst()
	 */
	public void beforeFirst() throws SQLException
		{
		if(rs!=null)
			rs.beforeFirst();
		}

	/**
	 * @see java.sql.ResultSet#cancelRowUpdates()
	 */
	public void cancelRowUpdates() throws SQLException
		{
		rs.cancelRowUpdates();
		}

	/**
	 * @see java.sql.ResultSet#clearWarnings()
	 */
	public void clearWarnings() throws SQLException
		{
		if(rs!=null)
			rs.clearWarnings();
		}

	/**
	 * @see java.sql.ResultSet#deleteRow()
	 */
	public void deleteRow() throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		rs.deleteRow();
		}

	/**
	 * @see java.sql.ResultSet#findColumn(String)
	 */
	public int findColumn(String columnLabel) throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		return rs.findColumn(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#first()
	 */
	public boolean first() throws SQLException
		{
		if(rs==null)
			return false;
		return rs.first();
		}

	@Deprecated
	/**
	 * @see java.sql.ResultSet#getBigDecimal(int,int)
	 */
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException
		{
		return rs.getBigDecimal(columnIndex, scale);
		}

	@Deprecated
	/**
	 * @see java.sql.ResultSet#getBigDecimal(String,int)
	 */
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException
		{
		return rs.getBigDecimal(columnLabel, scale);
		}

	/**
	 * @see java.sql.ResultSet#getConcurrency()
	 */
	public int getConcurrency() throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		return rs.getConcurrency();
		}

	/**
	 * @see java.sql.ResultSet#getCursorName()
	 */
	public String getCursorName() throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		return rs.getCursorName();
		}

	/**
	 * @see java.sql.ResultSet#getFetchDirection()
	 */
	public int getFetchDirection() throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		return rs.getFetchDirection();
		}

	/**
	 * @see java.sql.ResultSet#getFetchSize()
	 */
	public int getFetchSize() throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		return rs.getFetchSize();
		}

	/**
	 * @see java.sql.ResultSet#getHoldability()
	 */
	public int getHoldability() throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		return rs.getHoldability();
		}

	/**
	 * @see java.sql.ResultSet#getMetaData()
	 */
	public ResultSetMetaData getMetaData() throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		return rs.getMetaData();
		}

	/**
	 * @see java.sql.ResultSet#getObject(int,Class<T>)
	 */
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException
		{
		return rs.getObject(columnIndex, type);
		}

	/**
	 * @see java.sql.ResultSet#getObject(String,Class<T>)
	 */
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException
		{
		return rs.getObject(columnLabel, type);
		}

	/**
	 * @see java.sql.ResultSet#getRow()
	 */
	public int getRow() throws SQLException
		{
		if(rs==null)
			return 0;
		return rs.getRow();
		}

	/**
	 * @see java.sql.ResultSet#getStatement()
	 */
	public Statement getStatement() throws SQLException
		{
		if(rs==null)
			return null;
		return rs.getStatement();
		}

	/**
	 * @see java.sql.ResultSet#getType()
	 */
	public int getType() throws SQLException
		{
		if(rs==null)
			throw new SQLException("ResultSet doesn't exist");
		return rs.getType();
		}

	@Deprecated
	/**
	 * @see java.sql.ResultSet#getUnicodeStream(int)
	 */
	public InputStream getUnicodeStream(int columnIndex) throws SQLException
		{
		return rs.getUnicodeStream(columnIndex);
		}

	@Deprecated
	/**
	 * @see java.sql.ResultSet#getUnicodeStream(String)
	 */
	public InputStream getUnicodeStream(String columnLabel) throws SQLException
		{
		return rs.getUnicodeStream(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#getWarnings()
	 */
	public SQLWarning getWarnings() throws SQLException
		{
		return rs.getWarnings();
		}

	/**
	 * @see java.sql.ResultSet#insertRow()
	 */
	public void insertRow() throws SQLException
		{
		rs.insertRow();
		}

	/**
	 * @see java.sql.ResultSet#isAfterLast()
	 */
	public boolean isAfterLast() throws SQLException
		{
		if(rs==null)
			return true;
		return rs.isAfterLast();
		}

	/**
	 * @see java.sql.ResultSet#isBeforeFirst()
	 */
	public boolean isBeforeFirst() throws SQLException
		{
		if(rs==null)
			return true;
		return rs.isBeforeFirst();
		}

	/**
	 * @see java.sql.ResultSet#isClosed()
	 */
	public boolean isClosed() throws SQLException
		{
		if(rs==null)
			return true;
		return rs.isClosed();
		}

	/**
	 * @see java.sql.ResultSet#isFirst()
	 */
	public boolean isFirst() throws SQLException
		{
		if(rs==null)
			return false;
		return rs.isFirst();
		}

	/**
	 * @see java.sql.ResultSet#isLast()
	 */
	public boolean isLast() throws SQLException
		{
		if(rs==null)
			return false;
		return rs.isLast();
		}

	/**
	 * @see java.sql.ResultSet#last()
	 */
	public boolean last() throws SQLException
		{
		if(rs==null)
			return false;
		return rs.last();
		}

	/**
	 * @see java.sql.ResultSet#moveToCurrentRow()
	 */
	public void moveToCurrentRow() throws SQLException
		{
		if(rs!=null)
			rs.moveToCurrentRow();
		}

	/**
	 * @see java.sql.ResultSet#moveToInsertRow()
	 */
	public void moveToInsertRow() throws SQLException
		{
		if(rs==null)
			throw new SQLException("Non modifiable");
		rs.moveToInsertRow();
		}

	/**
	 * @see java.sql.ResultSet#previous()
	 */
	public boolean previous() throws SQLException
		{
		if(rs==null)
			return false;
		return rs.previous();
		}

	/**
	 * @see java.sql.ResultSet#refreshRow()
	 */
	public void refreshRow() throws SQLException
		{
		rs.refreshRow();
		}

	/**
	 * @see java.sql.ResultSet#relative(int)
	 */
	public boolean relative(int rows) throws SQLException
		{
		if(rs==null)
			return false;
		return rs.relative(rows);
		}

	/**
	 * @see java.sql.ResultSet#rowDeleted()
	 */
	public boolean rowDeleted() throws SQLException
		{
		return rs.rowDeleted();
		}

	/**
	 * @see java.sql.ResultSet#rowInserted()
	 */
	public boolean rowInserted() throws SQLException
		{
		return rs.rowInserted();
		}

	/**
	 * @see java.sql.ResultSet#rowUpdated()
	 */
	public boolean rowUpdated() throws SQLException
		{
		return rs.rowUpdated();
		}

	/**
	 * @see java.sql.ResultSet#setFetchDirection(int)
	 */
	public void setFetchDirection(int direction) throws SQLException
		{
		rs.setFetchDirection(direction);
		}

	/**
	 * @see java.sql.ResultSet#setFetchSize(int)
	 */
	public void setFetchSize(int rows) throws SQLException
		{
		rs.setFetchSize(rows);
		}

	/**
	 * @see java.sql.ResultSet#updateArray(int,Array)
	 */
	public void updateArray(int columnIndex, Array x) throws SQLException
		{
		rs.updateArray(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateArray(String,Array)
	 */
	public void updateArray(String columnLabel, Array x) throws SQLException
		{
		rs.updateArray(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateAsciiStream(int,InputStream)
	 */
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException
		{
		rs.updateAsciiStream(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateAsciiStream(String,InputStream)
	 */
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException
		{
		rs.updateAsciiStream(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateAsciiStream(int,InputStream,int)
	 */
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException
		{
		rs.updateAsciiStream(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateAsciiStream(String,InputStream,int)
	 */
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException
		{
		rs.updateAsciiStream(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateAsciiStream(int,InputStream,long)
	 */
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException
		{
		rs.updateAsciiStream(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateAsciiStream(String,InputStream,long)
	 */
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException
		{
		rs.updateAsciiStream(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateBigDecimal(int,BigDecimal)
	 */
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException
		{
		rs.updateBigDecimal(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBigDecimal(String,BigDecimal)
	 */
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException
		{
		rs.updateBigDecimal(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBinaryStream(int,InputStream)
	 */
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException
		{
		rs.updateBinaryStream(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBinaryStream(String,InputStream)
	 */
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException
		{
		rs.updateBinaryStream(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBinaryStream(int,InputStream,int)
	 */
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException
		{
		rs.updateBinaryStream(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateBinaryStream(String,InputStream,int)
	 */
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException
		{
		rs.updateBinaryStream(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateBinaryStream(int,InputStream,long)
	 */
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException
		{
		rs.updateBinaryStream(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateBinaryStream(String,InputStream,long)
	 */
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException
		{
		rs.updateBinaryStream(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateBlob(int,Blob)
	 */
	public void updateBlob(int columnIndex, Blob x) throws SQLException
		{
		rs.updateBlob(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBlob(String,Blob)
	 */
	public void updateBlob(String columnLabel, Blob x) throws SQLException
		{
		rs.updateBlob(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBlob(int,InputStream)
	 */
	public void updateBlob(int columnIndex, InputStream x) throws SQLException
		{
		rs.updateBlob(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBlob(String,InputStream)
	 */
	public void updateBlob(String columnLabel, InputStream x) throws SQLException
		{
		rs.updateBlob(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBlob(int,InputStream,long)
	 */
	public void updateBlob(int columnIndex, InputStream x, long length) throws SQLException
		{
		rs.updateBlob(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateBlob(String,InputStream,long)
	 */
	public void updateBlob(String columnLabel, InputStream x, long length) throws SQLException
		{
		rs.updateBlob(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateBoolean(int,boolean)
	 */
	public void updateBoolean(int columnIndex, boolean x) throws SQLException
		{
		rs.updateBoolean(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBoolean(String,boolean)
	 */
	public void updateBoolean(String columnLabel, boolean x) throws SQLException
		{
		rs.updateBoolean(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateByte(int,byte)
	 */
	public void updateByte(int columnIndex, byte x) throws SQLException
		{
		rs.updateByte(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateByte(String,byte)
	 */
	public void updateByte(String columnLabel, byte x) throws SQLException
		{
		rs.updateByte(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBytes(int,byte[])
	 */
	public void updateBytes(int columnIndex, byte[] x) throws SQLException
		{
		rs.updateBytes(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateBytes(String,Byte[])
	 */
	public void updateBytes(String columnLabel, byte[] x) throws SQLException
		{
		rs.updateBytes(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateCharacterStream(int,Reader)
	 */
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException
		{
		rs.updateCharacterStream(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateCharacterStream(String,Reader)
	 */
	public void updateCharacterStream(String columnLabel, Reader x) throws SQLException
		{
		rs.updateCharacterStream(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateCharacterStream(int,Reader,int)
	 */
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException
		{
		rs.updateCharacterStream(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateCharacterStream(String,Reader,int)
	 */
	public void updateCharacterStream(String columnLabel, Reader x, int length) throws SQLException
		{
		rs.updateCharacterStream(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateCharacterStream(int,Reader,long)
	 */
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException
		{
		rs.updateCharacterStream(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateCharacterStream(String,Reader,long)
	 */
	public void updateCharacterStream(String columnLabel, Reader x, long length) throws SQLException
		{
		rs.updateCharacterStream(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateClob(int,Clob)
	 */
	public void updateClob(int columnIndex, Clob x) throws SQLException
		{
		rs.updateClob(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateClob(String,Clob)
	 */
	public void updateClob(String columnLabel, Clob x) throws SQLException
		{
		rs.updateClob(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateClob(int,Reader)
	 */
	public void updateClob(int columnIndex, Reader x) throws SQLException
		{
		rs.updateClob(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateClob(String,Reader)
	 */
	public void updateClob(String columnLabel, Reader x) throws SQLException
		{
		rs.updateClob(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateClob(int,Reader,long)
	 */
	public void updateClob(int columnIndex, Reader x, long length) throws SQLException
		{
		rs.updateClob(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateClob(String,Reader,long)
	 */
	public void updateClob(String columnLabel, Reader x, long length) throws SQLException
		{
		rs.updateClob(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateDate(int,Date)
	 */
	public void updateDate(int columnIndex, Date x) throws SQLException
		{
		rs.updateDate(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateDate(String,Date)
	 */
	public void updateDate(String columnLabel, Date x) throws SQLException
		{
		rs.updateDate(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateDouble(int,double)
	 */
	public void updateDouble(int columnIndex, double x) throws SQLException
		{
		rs.updateDouble(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateDouble(String,double)
	 */
	public void updateDouble(String columnLabel, double x) throws SQLException
		{
		rs.updateDouble(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateFloat(int,float)
	 */
	public void updateFloat(int columnIndex, float x) throws SQLException
		{
		rs.updateFloat(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateFloat(String,float)
	 */
	public void updateFloat(String columnLabel, float x) throws SQLException
		{
		rs.updateFloat(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateInt(int,int)
	 */
	public void updateInt(int columnIndex, int x) throws SQLException
		{
		rs.updateInt(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateInt(String,int)
	 */
	public void updateInt(String columnLabel, int x) throws SQLException
		{
		rs.updateInt(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateLong(int,long)
	 */
	public void updateLong(int columnIndex, long x) throws SQLException
		{
		rs.updateLong(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateLong(String,long)
	 */
	public void updateLong(String columnLabel, long x) throws SQLException
		{
		rs.updateLong(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNCharacterStream(int,Reader)
	 */
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException
		{
		rs.updateNCharacterStream(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNCharacterStream(String,Reader)
	 */
	public void updateNCharacterStream(String columnLabel, Reader x) throws SQLException
		{
		rs.updateNCharacterStream(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNCharacterStream(int,Reader,long)
	 */
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException
		{
		rs.updateNCharacterStream(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateNCharacterStream(String,Reader,long)
	 */
	public void updateNCharacterStream(String columnLabel, Reader x, long length) throws SQLException
		{
		rs.updateNCharacterStream(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateNClob(int,NClob)
	 */
	public void updateNClob(int columnIndex, NClob x) throws SQLException
		{
		rs.updateNClob(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNClob(String,NClob)
	 */
	public void updateNClob(String columnLabel, NClob x) throws SQLException
		{
		rs.updateNClob(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNClob(int,Reader)
	 */
	public void updateNClob(int columnIndex, Reader x) throws SQLException
		{
		rs.updateNClob(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNClob(String,Reader)
	 */
	public void updateNClob(String columnLabel, Reader x) throws SQLException
		{
		rs.updateNClob(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNClob(int,Reader,long)
	 */
	public void updateNClob(int columnIndex, Reader x, long length) throws SQLException
		{
		rs.updateNClob(columnIndex, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateNClob(String,Reader,long)
	 */
	public void updateNClob(String columnLabel, Reader x, long length) throws SQLException
		{
		rs.updateNClob(columnLabel, x, length);
		}

	/**
	 * @see java.sql.ResultSet#updateNString(int,String)
	 */
	public void updateNString(int columnIndex, String x) throws SQLException
		{
		rs.updateNString(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNString(String,String)
	 */
	public void updateNString(String columnLabel, String x) throws SQLException
		{
		rs.updateNString(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateNull(int)
	 */
	public void updateNull(int columnIndex) throws SQLException
		{
		rs.updateNull(columnIndex);
		}

	/**
	 * @see java.sql.ResultSet#updateNull(String)
	 */
	public void updateNull(String columnLabel) throws SQLException
		{
		rs.updateNull(columnLabel);
		}

	/**
	 * @see java.sql.ResultSet#updateObject(int,Object)
	 */
	public void updateObject(int columnIndex, Object x) throws SQLException
		{
		rs.updateObject(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateObject(String,Object)
	 */
	public void updateObject(String columnLabel, Object x) throws SQLException
		{
		rs.updateObject(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateObject(int,Object,int)
	 */
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException
		{
		rs.updateObject(columnIndex, x, scaleOrLength);
		}

	/**
	 * @see java.sql.ResultSet#updateObject(String,Object,int)
	 */
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException
		{
		rs.updateObject(columnLabel, x, scaleOrLength);
		}

	/**
	 * @see java.sql.ResultSet#updateRef(int,Ref)
	 */
	public void updateRef(int columnIndex, Ref x) throws SQLException
		{
		rs.updateRef(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateRef(String,Ref)
	 */
	public void updateRef(String columnLabel, Ref x) throws SQLException
		{
		rs.updateRef(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateRow()
	 */
	public void updateRow() throws SQLException
		{
		rs.updateRow();
		}

	/**
	 * @see java.sql.ResultSet#updateRowId(int,RowId)
	 */
	public void updateRowId(int columnIndex, RowId x) throws SQLException
		{
		rs.updateRowId(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateRowId(String,RowId)
	 */
	public void updateRowId(String columnLabel, RowId x) throws SQLException
		{
		rs.updateRowId(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateSQLXML(int,SQLXML)
	 */
	public void updateSQLXML(int columnIndex, SQLXML x) throws SQLException
		{
		rs.updateSQLXML(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateSQLXML(String,SQLXML)
	 */
	public void updateSQLXML(String columnLabel, SQLXML x) throws SQLException
		{
		rs.updateSQLXML(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateShort(int,short)
	 */
	public void updateShort(int columnIndex, short x) throws SQLException
		{
		rs.updateShort(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateShort(String,short)
	 */
	public void updateShort(String columnLabel, short x) throws SQLException
		{
		rs.updateShort(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateString(int,String)
	 */
	public void updateString(int columnIndex, String x) throws SQLException
		{
		rs.updateString(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateString(String,String)
	 */
	public void updateString(String columnLabel, String x) throws SQLException
		{
		rs.updateString(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateTime(int,Time)
	 */
	public void updateTime(int columnIndex, Time x) throws SQLException
		{
		rs.updateTime(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateTime(String,Time)
	 */
	public void updateTime(String columnLabel, Time x) throws SQLException
		{
		rs.updateTime(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#updateTimestamp(int,Timestamp)
	 */
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException
		{
		rs.updateTimestamp(columnIndex, x);
		}

	/**
	 * @see java.sql.ResultSet#updateTimestamp(String,Timestamp)
	 */
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException
		{
		rs.updateTimestamp(columnLabel, x);
		}

	/**
	 * @see java.sql.ResultSet#wasNull()
	 */
	public boolean wasNull() throws SQLException
		{
		return rs.wasNull();
		}

	/**
	 * @see java.sql.ResultSet#isWrapperFor(Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException
		{
		return rs.isWrapperFor(iface);
		}

	/**
	 * @see java.sql.ResultSet#unwrap(Class)
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException
		{
		return rs.unwrap(iface);
		}
	}
