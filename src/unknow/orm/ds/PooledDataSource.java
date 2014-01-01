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
package unknow.orm.ds;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class PooledDataSource extends SimpleDataSource
	{
	private Queue<CoWrapper> idleConnection=new ConcurrentLinkedQueue<CoWrapper>();
	private Integer maxIdle;
	
	public PooledDataSource(String url, String user, String pass, Integer maxIdle)
		{
		super(url, user, pass);
		this.maxIdle=maxIdle;
		}

	public Connection getConnection() throws SQLException
		{
		CoWrapper co=idleConnection.poll();
		if(co==null)
			co=new CoWrapper(super.getConnection());
		return co;
		}

	private void release(CoWrapper co) throws SQLException
		{
		if(maxIdle!=null && idleConnection.size()>=maxIdle)
			co.realClose();
		else
			idleConnection.offer(co);
		}
	
	private class CoWrapper implements Connection
		{
		private Connection co;

		public CoWrapper(Connection co)
			{
			this.co=co;
			}

		public boolean isWrapperFor(Class<?> iface) throws SQLException
			{
			return co.isWrapperFor(iface);
			}

		public <T> T unwrap(Class<T> iface) throws SQLException
			{
			return unwrap(iface);
			}

		public void abort(Executor executor) throws SQLException
			{
			co.abort(executor);
			}

		public void clearWarnings() throws SQLException
			{
			co.clearWarnings();
			}

		public void close() throws SQLException
			{
			release(this);
			}
		public void realClose() throws SQLException
			{
			co.close();
			}

		public void commit() throws SQLException
			{
			co.commit();
			}

		public Array createArrayOf(String typeName, Object[] elements) throws SQLException
			{
			return co.createArrayOf(typeName, elements);
			}

		public Blob createBlob() throws SQLException
			{
			return co.createBlob();
			}

		public Clob createClob() throws SQLException
			{
			return co.createClob();
			}

		public NClob createNClob() throws SQLException
			{
			return co.createNClob();
			}

		public SQLXML createSQLXML() throws SQLException
			{
			return co.createSQLXML();
			}

		public Statement createStatement() throws SQLException
			{
			return co.createStatement();
			}

		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException
			{
			return co.createStatement(resultSetType, resultSetConcurrency);
			}

		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
			{
			return co.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
			}

		public Struct createStruct(String typeName, Object[] attributes) throws SQLException
			{
			return co.createStruct(typeName, attributes);
			}

		public boolean getAutoCommit() throws SQLException
			{
			return co.getAutoCommit();
			}

		public String getCatalog() throws SQLException
			{
			return co.getCatalog();
			}

		public Properties getClientInfo() throws SQLException
			{
			return co.getClientInfo();
			}

		public String getClientInfo(String name) throws SQLException
			{
			return co.getClientInfo(name);
			}

		public int getHoldability() throws SQLException
			{
			return co.getHoldability();
			}

		public DatabaseMetaData getMetaData() throws SQLException
			{
			return co.getMetaData();
			}

		public int getNetworkTimeout() throws SQLException
			{
			return co.getNetworkTimeout();
			}

		public String getSchema() throws SQLException
			{
			return co.getSchema();
			}

		public int getTransactionIsolation() throws SQLException
			{
			return co.getTransactionIsolation();
			}

		public Map<String,Class<?>> getTypeMap() throws SQLException
			{
			return co.getTypeMap();
			}

		public SQLWarning getWarnings() throws SQLException
			{
			return co.getWarnings();
			}

		public boolean isClosed() throws SQLException
			{
			return co.isClosed();
			}

		public boolean isReadOnly() throws SQLException
			{
			return co.isReadOnly();
			}

		public boolean isValid(int timeout) throws SQLException
			{
			return co.isValid(timeout);
			}

		public String nativeSQL(String sql) throws SQLException
			{
			return co.nativeSQL(sql);
			}

		public CallableStatement prepareCall(String sql) throws SQLException
			{
			return co.prepareCall(sql);
			}

		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
			{
			return co.prepareCall(sql, resultSetType, resultSetConcurrency);
			}

		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
			{
			return co.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
			}

		public PreparedStatement prepareStatement(String sql) throws SQLException
			{
			return co.prepareStatement(sql);
			}

		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException
			{
			return co.prepareStatement(sql, autoGeneratedKeys);
			}

		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException
			{
			return co.prepareStatement(sql, columnIndexes);
			}

		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
			{
			return co.prepareStatement(sql, columnNames);
			}

		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
			{
			return co.prepareStatement(sql, resultSetType, resultSetConcurrency);
			}

		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
			{
			return co.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
			}

		public void releaseSavepoint(Savepoint savepoint) throws SQLException
			{
			co.releaseSavepoint(savepoint);
			}

		public void rollback() throws SQLException
			{
			co.rollback();
			}

		public void rollback(Savepoint savepoint) throws SQLException
			{
			co.rollback(savepoint);
			}

		public void setAutoCommit(boolean autoCommit) throws SQLException
			{
			co.setAutoCommit(autoCommit);
			}

		public void setCatalog(String catalog) throws SQLException
			{
			co.setCatalog(catalog);
			}

		public void setClientInfo(Properties properties) throws SQLClientInfoException
			{
			co.setClientInfo(properties);
			}

		public void setClientInfo(String name, String value) throws SQLClientInfoException
			{
			co.setClientInfo(name, value);
			}

		public void setHoldability(int holdability) throws SQLException
			{
			co.setHoldability(holdability);
			}

		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException
			{
			co.setNetworkTimeout(executor, milliseconds);
			}

		public void setReadOnly(boolean readOnly) throws SQLException
			{
			co.setReadOnly(readOnly);
			}

		public Savepoint setSavepoint() throws SQLException
			{
			return co.setSavepoint();
			}

		public Savepoint setSavepoint(String name) throws SQLException
			{
			return co.setSavepoint(name);
			}

		public void setSchema(String schema) throws SQLException
			{
			co.setSchema(schema);
			}

		public void setTransactionIsolation(int level) throws SQLException
			{
			co.setTransactionIsolation(level);
			}

		public void setTypeMap(Map<String,Class<?>> map) throws SQLException
			{
			co.setTypeMap(map);
			}
		}
	}
