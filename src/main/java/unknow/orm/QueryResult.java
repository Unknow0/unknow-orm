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

public abstract class QueryResult implements ResultSet
	{
	protected ResultSet rs;
	protected Map<String,Entity<?>> mapping;
	protected Database db;

//	protected QueryResult()
//		{
//		}

	protected QueryResult(Database db, ResultSet rs, Map<String,Entity<?>> mapping)
		{
		this.db=db;
		this.rs=rs;
		this.mapping=mapping;
		}

	public boolean next() throws SQLException
		{
		if(rs==null)
			return false;
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

	public boolean getBoolean(String columnLabel) throws SQLException
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

	public Array getArray(int columnIndex) throws SQLException
		{
		return rs.getArray(columnIndex);
		}

	public InputStream getAsciiStream(int columnIndex) throws SQLException
		{
		return rs.getAsciiStream(columnIndex);
		}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException
		{
		return rs.getBigDecimal(columnIndex);
		}

	public InputStream getBinaryStream(int columnIndex) throws SQLException
		{
		return rs.getBinaryStream(columnIndex);
		}

	public Blob getBlob(int columnIndex) throws SQLException
		{
		return rs.getBlob(columnIndex);
		}

	public boolean getBoolean(int columnIndex) throws SQLException
		{
		return rs.getBoolean(columnIndex);
		}

	public byte getByte(int columnIndex) throws SQLException
		{
		return rs.getByte(columnIndex);
		}

	public byte[] getBytes(int columnIndex) throws SQLException
		{
		return rs.getBytes(columnIndex);
		}

	public Reader getCharacterStream(int columnIndex) throws SQLException
		{
		return rs.getCharacterStream(columnIndex);
		}

	public Clob getClob(int columnIndex) throws SQLException
		{
		return rs.getClob(columnIndex);
		}

	public Date getDate(int columnIndex) throws SQLException
		{
		return rs.getDate(columnIndex);
		}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException
		{
		return rs.getDate(columnIndex, cal);
		}

	public double getDouble(int columnIndex) throws SQLException
		{
		return rs.getDouble(columnIndex);
		}

	public float getFloat(int columnIndex) throws SQLException
		{
		return rs.getFloat(columnIndex);
		}

	public int getInt(int columnIndex) throws SQLException
		{
		return rs.getInt(columnIndex);
		}

	public long getLong(int columnIndex) throws SQLException
		{
		return rs.getLong(columnIndex);
		}

	public Reader getNCharacterStream(int columnIndex) throws SQLException
		{
		return rs.getNCharacterStream(columnIndex);
		}

	public NClob getNClob(int columnIndex) throws SQLException
		{
		return rs.getNClob(columnIndex);
		}

	public String getNString(int columnIndex) throws SQLException
		{
		return rs.getNString(columnIndex);
		}

	public Object getObject(int columnIndex) throws SQLException
		{
		return rs.getObject(columnIndex);
		}

	public Object getObject(int columnIndex, Map<String,Class<?>> map) throws SQLException
		{
		return rs.getObject(columnIndex, map);
		}

	public Ref getRef(int columnIndex) throws SQLException
		{
		return rs.getRef(columnIndex);
		}

	public RowId getRowId(int columnIndex) throws SQLException
		{
		return rs.getRowId(columnIndex);
		}

	public short getShort(int columnIndex) throws SQLException
		{
		return rs.getShort(columnIndex);
		}

	public SQLXML getSQLXML(int columnIndex) throws SQLException
		{
		return rs.getSQLXML(columnIndex);
		}

	public String getString(int columnIndex) throws SQLException
		{
		return rs.getString(columnIndex);
		}

	public Time getTime(int columnIndex) throws SQLException
		{
		return rs.getTime(columnIndex);
		}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException
		{
		return rs.getTime(columnIndex, cal);
		}

	public Timestamp getTimestamp(int columnIndex) throws SQLException
		{
		return rs.getTimestamp(columnIndex);
		}

	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException
		{
		return rs.getTimestamp(columnIndex, cal);
		}

	public URL getURL(int columnIndex) throws SQLException
		{
		return rs.getURL(columnIndex);
		}

	public void close() throws SQLException
		{
		if(rs!=null)
			rs.close();
		}

	@SuppressWarnings("unchecked")
	public <T> T getEntity(String alias) throws SQLException
		{
		Entity<T> e=(Entity<T>)mapping.get(alias);
		return e.build(db, alias, rs);
		}

	public boolean absolute(int row) throws SQLException
		{
		if(rs==null)
			return false;
		return rs.absolute(row);
		}

	public void afterLast() throws SQLException
		{
		if(rs!=null)
			rs.afterLast();
		}

	public void beforeFirst() throws SQLException
		{
		if(rs!=null)
			rs.beforeFirst();
		}

	public void cancelRowUpdates() throws SQLException
		{
		rs.cancelRowUpdates();
		}

	public void clearWarnings() throws SQLException
		{
		rs.clearWarnings();
		}

	public void deleteRow() throws SQLException
		{
		rs.deleteRow();
		}

	public int findColumn(String columnLabel) throws SQLException
		{
		return rs.findColumn(columnLabel);
		}

	public boolean first() throws SQLException
		{
		if(rs==null)
			return false;
		return rs.first();
		}

	@Deprecated
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException
		{
		return rs.getBigDecimal(columnIndex, scale);
		}

	@Deprecated
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException
		{
		return rs.getBigDecimal(columnLabel, scale);
		}

	public int getConcurrency() throws SQLException
		{
		return rs.getConcurrency();
		}

	public String getCursorName() throws SQLException
		{
		return rs.getCursorName();
		}

	public int getFetchDirection() throws SQLException
		{
		return rs.getFetchDirection();
		}

	public int getFetchSize() throws SQLException
		{
		return rs.getFetchSize();
		}

	public int getHoldability() throws SQLException
		{
		return rs.getHoldability();
		}

	public ResultSetMetaData getMetaData() throws SQLException
		{
		return rs.getMetaData();
		}

	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException
		{
		return rs.getObject(columnIndex, type);
		}

	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException
		{
		return rs.getObject(columnLabel, type);
		}

	public int getRow() throws SQLException
		{
		return rs.getRow();
		}

	public Statement getStatement() throws SQLException
		{
		return rs.getStatement();
		}

	public int getType() throws SQLException
		{
		return rs.getType();
		}

	@Deprecated
	public InputStream getUnicodeStream(int columnIndex) throws SQLException
		{
		return rs.getUnicodeStream(columnIndex);
		}

	@Deprecated
	public InputStream getUnicodeStream(String columnLabel) throws SQLException
		{
		return rs.getUnicodeStream(columnLabel);
		}

	public SQLWarning getWarnings() throws SQLException
		{
		return rs.getWarnings();
		}

	public void insertRow() throws SQLException
		{
		rs.insertRow();
		}

	public boolean isAfterLast() throws SQLException
		{
		return rs.isAfterLast();
		}

	public boolean isBeforeFirst() throws SQLException
		{
		return rs.isBeforeFirst();
		}

	public boolean isClosed() throws SQLException
		{
		return rs.isClosed();
		}

	public boolean isFirst() throws SQLException
		{
		return rs.isFirst();
		}

	public boolean isLast() throws SQLException
		{
		return rs.isLast();
		}

	public boolean last() throws SQLException
		{
		return rs.last();
		}

	public void moveToCurrentRow() throws SQLException
		{
		rs.moveToCurrentRow();
		}

	public void moveToInsertRow() throws SQLException
		{
		rs.moveToInsertRow();
		}

	public boolean previous() throws SQLException
		{
		return rs.previous();
		}

	public void refreshRow() throws SQLException
		{
		rs.refreshRow();
		}

	public boolean relative(int rows) throws SQLException
		{
		return rs.relative(rows);
		}

	public boolean rowDeleted() throws SQLException
		{
		return rs.rowDeleted();
		}

	public boolean rowInserted() throws SQLException
		{
		return rs.rowInserted();
		}

	public boolean rowUpdated() throws SQLException
		{
		return rs.rowUpdated();
		}

	public void setFetchDirection(int direction) throws SQLException
		{
		rs.setFetchDirection(direction);
		}

	public void setFetchSize(int rows) throws SQLException
		{
		rs.setFetchSize(rows);
		}

	public void updateArray(int columnIndex, Array x) throws SQLException
		{
		rs.updateArray(columnIndex, x);
		}

	public void updateArray(String columnLabel, Array x) throws SQLException
		{
		rs.updateArray(columnLabel, x);
		}

	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException
		{
		rs.updateAsciiStream(columnIndex, x);
		}

	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException
		{
		rs.updateAsciiStream(columnLabel, x);
		}

	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException
		{
		rs.updateAsciiStream(columnIndex, x, length);
		}

	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException
		{
		rs.updateAsciiStream(columnLabel, x, length);
		}

	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException
		{
		rs.updateAsciiStream(columnIndex, x, length);
		}

	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException
		{
		rs.updateAsciiStream(columnLabel, x, length);
		}

	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException
		{
		rs.updateBigDecimal(columnIndex, x);
		}

	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException
		{
		rs.updateBigDecimal(columnLabel, x);
		}

	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException
		{
		rs.updateBinaryStream(columnIndex, x);
		}

	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException
		{
		rs.updateBinaryStream(columnLabel, x);
		}

	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException
		{
		rs.updateBinaryStream(columnIndex, x, length);
		}

	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException
		{
		rs.updateBinaryStream(columnLabel, x, length);
		}

	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException
		{
		rs.updateBinaryStream(columnIndex, x, length);
		}

	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException
		{
		rs.updateBinaryStream(columnLabel, x, length);
		}

	public void updateBlob(int columnIndex, Blob x) throws SQLException
		{
		rs.updateBlob(columnIndex, x);
		}

	public void updateBlob(String columnLabel, Blob x) throws SQLException
		{
		rs.updateBlob(columnLabel, x);
		}

	public void updateBlob(int columnIndex, InputStream x) throws SQLException
		{
		rs.updateBlob(columnIndex, x);
		}

	public void updateBlob(String columnLabel, InputStream x) throws SQLException
		{
		rs.updateBlob(columnLabel, x);
		}

	public void updateBlob(int columnIndex, InputStream x, long length) throws SQLException
		{
		rs.updateBlob(columnIndex, x, length);
		}

	public void updateBlob(String columnLabel, InputStream x, long length) throws SQLException
		{
		rs.updateBlob(columnLabel, x, length);
		}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException
		{
		rs.updateBoolean(columnIndex, x);
		}

	public void updateBoolean(String columnLabel, boolean x) throws SQLException
		{
		rs.updateBoolean(columnLabel, x);
		}

	public void updateByte(int columnIndex, byte x) throws SQLException
		{
		rs.updateByte(columnIndex, x);
		}

	public void updateByte(String columnLabel, byte x) throws SQLException
		{
		rs.updateByte(columnLabel, x);
		}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException
		{
		rs.updateBytes(columnIndex, x);
		}

	public void updateBytes(String columnLabel, byte[] x) throws SQLException
		{
		rs.updateBytes(columnLabel, x);
		}

	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException
		{
		rs.updateCharacterStream(columnIndex, x);
		}

	public void updateCharacterStream(String columnLabel, Reader x) throws SQLException
		{
		rs.updateCharacterStream(columnLabel, x);
		}

	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException
		{
		rs.updateCharacterStream(columnIndex, x, length);
		}

	public void updateCharacterStream(String columnLabel, Reader x, int length) throws SQLException
		{
		rs.updateCharacterStream(columnLabel, x, length);
		}

	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException
		{
		rs.updateCharacterStream(columnIndex, x, length);
		}

	public void updateCharacterStream(String columnLabel, Reader x, long length) throws SQLException
		{
		rs.updateCharacterStream(columnLabel, x, length);
		}

	public void updateClob(int columnIndex, Clob x) throws SQLException
		{
		rs.updateClob(columnIndex, x);
		}

	public void updateClob(String columnLabel, Clob x) throws SQLException
		{
		rs.updateClob(columnLabel, x);
		}

	public void updateClob(int columnIndex, Reader x) throws SQLException
		{
		rs.updateClob(columnIndex, x);
		}

	public void updateClob(String columnLabel, Reader x) throws SQLException
		{
		rs.updateClob(columnLabel, x);
		}

	public void updateClob(int columnIndex, Reader x, long length) throws SQLException
		{
		rs.updateClob(columnIndex, x);
		}

	public void updateClob(String columnLabel, Reader x, long length) throws SQLException
		{
		rs.updateClob(columnLabel, x, length);
		}

	public void updateDate(int columnIndex, Date x) throws SQLException
		{
		rs.updateDate(columnIndex, x);
		}

	public void updateDate(String columnLabel, Date x) throws SQLException
		{
		rs.updateDate(columnLabel, x);
		}

	public void updateDouble(int columnIndex, double x) throws SQLException
		{
		rs.updateDouble(columnIndex, x);
		}

	public void updateDouble(String columnLabel, double x) throws SQLException
		{
		rs.updateDouble(columnLabel, x);
		}

	public void updateFloat(int columnIndex, float x) throws SQLException
		{
		rs.updateFloat(columnIndex, x);
		}

	public void updateFloat(String columnLabel, float x) throws SQLException
		{
		rs.updateFloat(columnLabel, x);
		}

	public void updateInt(int columnIndex, int x) throws SQLException
		{
		rs.updateInt(columnIndex, x);
		}

	public void updateInt(String columnLabel, int x) throws SQLException
		{
		rs.updateInt(columnLabel, x);
		}

	public void updateLong(int columnIndex, long x) throws SQLException
		{
		rs.updateLong(columnIndex, x);
		}

	public void updateLong(String columnLabel, long x) throws SQLException
		{
		rs.updateLong(columnLabel, x);
		}

	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException
		{
		rs.updateNCharacterStream(columnIndex, x);
		}

	public void updateNCharacterStream(String columnLabel, Reader x) throws SQLException
		{
		rs.updateNCharacterStream(columnLabel, x);
		}

	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException
		{
		rs.updateNCharacterStream(columnIndex, x);
		}

	public void updateNCharacterStream(String columnLabel, Reader x, long length) throws SQLException
		{
		rs.updateNCharacterStream(columnLabel, x, length);
		}

	public void updateNClob(int columnIndex, NClob x) throws SQLException
		{
		rs.updateNClob(columnIndex, x);
		}

	public void updateNClob(String columnLabel, NClob x) throws SQLException
		{
		rs.updateNClob(columnLabel, x);
		}

	public void updateNClob(int columnIndex, Reader x) throws SQLException
		{
		rs.updateNClob(columnIndex, x);
		}

	public void updateNClob(String columnLabel, Reader x) throws SQLException
		{
		rs.updateNClob(columnLabel, x);
		}

	public void updateNClob(int columnIndex, Reader x, long length) throws SQLException
		{
		rs.updateNClob(columnIndex, x, length);
		}

	public void updateNClob(String columnLabel, Reader x, long length) throws SQLException
		{
		rs.updateNClob(columnLabel, x, length);
		}

	public void updateNString(int columnIndex, String x) throws SQLException
		{
		rs.updateNString(columnIndex, x);
		}

	public void updateNString(String columnLabel, String x) throws SQLException
		{
		rs.updateNString(columnLabel, x);
		}

	public void updateNull(int columnIndex) throws SQLException
		{
		rs.updateNull(columnIndex);
		}

	public void updateNull(String columnLabel) throws SQLException
		{
		rs.updateNull(columnLabel);
		}

	public void updateObject(int columnIndex, Object x) throws SQLException
		{
		rs.updateObject(columnIndex, x);
		}

	public void updateObject(String columnLabel, Object x) throws SQLException
		{
		rs.updateObject(columnLabel, x);
		}

	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException
		{
		rs.updateObject(columnIndex, x, scaleOrLength);
		}

	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException
		{
		rs.updateObject(columnLabel, x, scaleOrLength);
		}

	public void updateRef(int columnIndex, Ref x) throws SQLException
		{
		rs.updateRef(columnIndex, x);
		}

	public void updateRef(String columnLabel, Ref x) throws SQLException
		{
		rs.updateRef(columnLabel, x);
		}

	public void updateRow() throws SQLException
		{
		rs.updateRow();
		}

	public void updateRowId(int columnIndex, RowId x) throws SQLException
		{
		rs.updateRowId(columnIndex, x);
		}

	public void updateRowId(String columnLabel, RowId x) throws SQLException
		{
		rs.updateRowId(columnLabel, x);
		}

	public void updateSQLXML(int columnIndex, SQLXML x) throws SQLException
		{
		rs.updateSQLXML(columnIndex, x);
		}

	public void updateSQLXML(String columnLabel, SQLXML x) throws SQLException
		{
		rs.updateSQLXML(columnLabel, x);
		}

	public void updateShort(int columnIndex, short x) throws SQLException
		{
		rs.updateShort(columnIndex, x);
		}

	public void updateShort(String columnLabel, short x) throws SQLException
		{
		rs.updateShort(columnLabel, x);
		}

	public void updateString(int columnIndex, String x) throws SQLException
		{
		rs.updateString(columnIndex, x);
		}

	public void updateString(String columnLabel, String x) throws SQLException
		{
		rs.updateString(columnLabel, x);
		}

	public void updateTime(int columnIndex, Time x) throws SQLException
		{
		rs.updateTime(columnIndex, x);
		}

	public void updateTime(String columnLabel, Time x) throws SQLException
		{
		rs.updateTime(columnLabel, x);
		}

	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException
		{
		rs.updateTimestamp(columnIndex, x);
		}

	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException
		{
		rs.updateTimestamp(columnLabel, x);
		}

	public boolean wasNull() throws SQLException
		{
		return rs.wasNull();
		}

	public boolean isWrapperFor(Class<?> iface) throws SQLException
		{
		return rs.isWrapperFor(iface);
		}

	public <T> T unwrap(Class<T> iface) throws SQLException
		{
		return rs.unwrap(iface);
		}
	}
