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
package unknow.orm.mapping;

import java.sql.*;

public interface TypeConvertor
	{
	/**
	 * Check if this TypeConvertor can do the conversion for this column to the expected type.
	 * 
	 * @param expected the type to convert to
	 * @param sqlColumn the column data
	 * @return true if a call to convert will works false overwise
	 */
	public boolean canConvert(Class<?> expected, Column sqlColumn);

	/**
	 * Ask to convert this colmn from rs with the label to the expected type.
	 * 
	 * @param expected the type to oncvert to.
	 * @param sqlColumn the colupmn data.
	 * @param rs the resultset holding sql data
	 * @param label the labin in this resultSet
	 * @return the generated class.
	 */
	public <T> T convert(Class<T> expected, Column sqlColumn, ResultSet rs, String label) throws SQLException;

	/**
	 * Check if this typoe convertor as a defaultValue for this column and type.
	 * 
	 * @param expected the expected value type
	 * @param sqlColumn the column data.
	 * @return true if defaultValue hold a value.
	 */
	public boolean hasDefault(Class<?> expected, Column sqlColumn);

	/**
	 * Get the default value for this colmumn.
	 * 
	 * @param expected the expected value type
	 * @param sqlColumn the column data.
	 * @return the default value of this column
	 */
	public <T> T defaultValue(Class<?> expected, Column sqlColumn);
	}
