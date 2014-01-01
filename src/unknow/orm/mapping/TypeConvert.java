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
package unknow.orm.mapping;

import java.sql.*;

public interface TypeConvert
	{
	public Object convert(int sqlType, String type, ResultSet rs, String name) throws SQLException;
	
	public Class<?> toJavaType(int sqlType, String type) throws SQLException;
	}
