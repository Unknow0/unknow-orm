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

import java.math.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import javax.sql.*;

import org.apache.logging.log4j.*;

import unknow.json.*;
import unknow.orm.*;

public class Database
	{
	/** tables type requested */
	private static final String[] types= {"VIEW", "TABLE"};
	private static final Logger logger=LogManager.getFormatterLogger(Database.class);

	private Map<Class<?>,Table> mapping=new HashMap<Class<?>,Table>();

	private Map<String,TypeConvert> typesMapping=new HashMap<String,TypeConvert>();

	private DataSource ds;

	private List<Table> tables=new ArrayList<Table>();

	/**
	 * @param ds datasource to use.
	 * @param cfg sould contains tables description.
	 */
	public Database(DataSource ds, JsonObject cfg) throws SQLException, JsonException, ClassNotFoundException, ClassCastException
		{
		this.ds=ds;
		loadTablesDesc(cfg);
		}

	private void loadTablesDesc(JsonObject dbcfg) throws SQLException, JsonException, ClassNotFoundException, ClassCastException
		{
		try (Connection co=ds.getConnection())
			{
			DatabaseMetaData metaData=co.getMetaData();
			try (ResultSet rs=metaData.getTables(null, null, null, types))
				{
				while (rs.next())
					{
					String catalog=rs.getString("TABLE_CAT");
					String schema=rs.getString("TABLE_SCHEM");
					String name=rs.getString("TABLE_NAME");
//					String type=rs.getString("TABLE_TYPE");
					String remark=rs.getString("REMARKS");

					logger.trace("Found table '%s'", name);
					JsonObject tablecfg=dbcfg.optJsonObject(name);
					if(tablecfg!=null)
						{
						Table table=new Table(this, metaData, tablecfg, name, schema, catalog, remark);
						tables.add(table);
						}
					else
						logger.trace("	not in cfg skipping");
					}
				}
			}
		}

	public Object convert(int sqlType, String type, ResultSet rs, String name) throws SQLException
		{
		TypeConvert typeConvert=typesMapping.get(type);
		if(typeConvert!=null)
			return typeConvert.convert(sqlType, type, rs, name);
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
				throw new SQLException("no converssion found for type '"+type+"'");
			}
		}

	public Class<?> toJavaType(int sqlType, String type) throws SQLException
		{
		TypeConvert typeConvert=typesMapping.get(type);
		if(typeConvert!=null)
			return typeConvert.toJavaType(sqlType, type);
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
				throw new SQLException("no converssion found for type '"+type+"'");
			}
		}

	public Table getMapping(Class<?> entity)
		{
		return mapping.get(entity);
		}

	public void addTypesMapping(String type, TypeConvert convert)
		{
		typesMapping.put(type, convert);
		}

	public void addMapping(String cl, Table table) throws ClassNotFoundException, ClassCastException
		{
		Class<?> clazz=Class.forName(cl);
		mapping.put(clazz, table);
		}

	public Connection getConnection() throws SQLException
		{
		return ds.getConnection();
		}

	public Query createQuery(String query) throws SQLException
		{
		return new Query(this, query);
		}

	public Query createQuery(String query, String[] alias, Class<?>[] entities) throws SQLException
		{
		return new Query(this, query, alias, entities);
		}

	public Query createQuery(String query, Map<String,Class<?>> aliasMapping) throws SQLException
		{
		return new Query(this, query, aliasMapping);
		}
	}
