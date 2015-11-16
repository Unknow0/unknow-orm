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

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import javax.sql.*;

public class SimpleDataSource implements DataSource
	{
	private String url;
	private String user;
	private String pass;
	
	public SimpleDataSource(String url, String user, String pass)
		{
		this.url=url;
		this.user=user;
		this.pass=pass;
		}
	
	public Connection getConnection() throws SQLException
		{
		return DriverManager.getConnection(url, user, pass);
		}

	public PrintWriter getLogWriter() throws SQLException
		{
		throw new SQLException("unimplemented");
		}

	public int getLoginTimeout() throws SQLException
		{
		throw new SQLException("unimplemented");
		}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException
		{
		throw new SQLFeatureNotSupportedException("unimplemented");
		}

	public void setLogWriter(PrintWriter arg0) throws SQLException
		{
		throw new SQLException("unimplemented");
		}

	public void setLoginTimeout(int arg0) throws SQLException
		{
		throw new SQLException("unimplemented");
		}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException
		{
		throw new SQLException("unimplemented");
		}

	public <T> T unwrap(Class<T> arg0) throws SQLException
		{
		throw new SQLException("unimplemented");
		}

	public Connection getConnection(String username, String password) throws SQLException
		{
		throw new SQLException("unimplemented");
		}

	}
