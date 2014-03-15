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
import unknow.json.JsonValue.JsonString;
import unknow.orm.*;
import unknow.orm.mapping.Entity.Entry;

public class Database
	{
	/** tables type requested */
	private static final String[] types= {"VIEW", "TABLE"};
	private static final Logger logger=LogManager.getFormatterLogger(Database.class);

	private Map<Class<?>,Entity<?>> mapping=new HashMap<Class<?>,Entity<?>>();

	private TypeConvertor[] typesMapping;

	private DataSource ds;

	private Map<String,Table> tables=new HashMap<String,Table>();

	/**
	 * @param ds datasource to use.
	 * @param cfg sould contains tables description.
	 */
	public Database(DataSource ds, JsonObject cfg) throws SQLException, JsonException, ClassNotFoundException, ClassCastException
		{
		this.ds=ds;
		loadTablesDesc();
		loadDaos(cfg);
		}

	private void loadCol(Set<Entry> entries, Table table, JsonValue colcfg)
		{
		for(Column col:table.getColumns())
			{
			Entity.ColEntry e=null;
			if(colcfg instanceof JsonObject)
				{
				JsonValue opt=((JsonObject)colcfg).opt(col.getName());
				if(opt instanceof JsonString)
					e=new Entity.ColEntry(col, ((JsonString)opt).value(), null);
				else if(opt instanceof JsonObject)
					{
					JsonObject obj=(JsonObject)opt;
					e=new Entity.ColEntry(col, obj.optString("jname"), obj.optString("setter"));
					}
				}
			if(e!=null)
				entries.add(e);
			}
		}

	@SuppressWarnings("unchecked")
	public void loadField(Table table, JsonObject fields, Set<Entity.Entry> entries) throws JsonException, ClassNotFoundException
		{
		for(String jname:fields)
			{
			JsonValue fieldcfg=fields.get(jname);
			if(fieldcfg instanceof JsonString)
				{
				Column col=table.getColumn(((JsonString)fieldcfg).value());
				if(col!=null)
					entries.add(new Entity.ColEntry(col, jname, null));
				}
			else if(fieldcfg instanceof JsonObject)
				{
				JsonObject o=(JsonObject)fieldcfg;
				Class<?> clazz=Class.forName(o.getString("class"));

				Set<Entity.Entry> set=new HashSet<Entity.Entry>();

				JsonValue colcfg=o.opt("columns");
				if(colcfg!=null)
					loadCol(set, table, colcfg);

				JsonObject fc=o.optJsonObject("fields");
				if(fc!=null)
					loadField(table, fc, set);
				
				entries.add(new Entity.EntityEntry(new Entity<>(clazz, new Set[]{set}), jname, null));
				}
			}
		}

	@SuppressWarnings("unchecked")
	private void loadDaos(JsonObject daosCfg) throws SQLException, JsonException
		{
		for(String clazz:daosCfg)
			{
			try
				{
				Class<?> c=Class.forName(clazz);
				JsonArray a=daosCfg.getJsonArray(clazz);
				Set<Entity.Entry>[] cols=new Set[a.length()];
				for(int i=0; i<a.length(); i++)
					{
					JsonValue v=a.get(i);
					Set<Entity.Entry> entries=new HashSet<Entity.Entry>();
					if(v instanceof JsonObject)
						{
						JsonObject o=(JsonObject)v;
						String table=o.getString("table");
						JsonValue colcfg=o.opt("columns");
						JsonObject fields=o.optJsonObject("fields");
						logger.info("load dao for %s\n", table);
						Table t=tables.get(table);

						if(colcfg!=null)
							loadCol(entries, t, colcfg);
						if(fields!=null)
							loadField(t, fields, entries);
						}
					else if(v instanceof JsonString)
						{
						String table=((JsonString)v).value();
						Table t=tables.get(table);
						for(Column col:t.getColumns())
							entries.add(new Entity.ColEntry(col, null, null));
						}
					else
						throw new JsonException("Expected JsonObject or JsonString for '"+clazz+"' daos");

					cols[i]=entries;
					}
				mapping.put(c, new Entity<>(c, cols));
				}
			catch (ClassNotFoundException e)
				{
				throw new SQLException("no class found for '"+clazz+"'");
				}
			}
		}

	private void loadTablesDesc() throws SQLException, JsonException, ClassNotFoundException, ClassCastException
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
					Table table=new Table(metaData, name, schema, catalog, remark);
					tables.put(table.getName(), table);
					}
				}
			}
		}

	public Object convert(int sqlType, String type, ResultSet rs, String name) throws SQLException
		{
		if(typesMapping!=null)
			{
			for(TypeConvertor typeConvert:typesMapping)
				{
				if(typeConvert.canConvert(sqlType, type))
					return typeConvert.convert(sqlType, type, rs, name);
				}
			}
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

	public Class<?> toJavaType(int sqlType, String type) throws SQLException
		{
		if(typesMapping!=null)
			{
			for(TypeConvertor typeConvert:typesMapping)
				{
				if(typeConvert.canConvert(sqlType, type))
					return typeConvert.toJavaType(sqlType, type);
				}
			}
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

	@SuppressWarnings("unchecked")
	public <T> Entity<T> getMapping(Class<T> entity)
		{
		return (Entity<T>)mapping.get(entity);
		}

	public void setTypesMapping(TypeConvertor[] types)
		{
		typesMapping=types;
		}

	public void addMapping(String cl, Entity<?> entity) throws ClassNotFoundException, ClassCastException
		{
		Class<?> clazz=Class.forName(cl);
		mapping.put(clazz, entity);
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
