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
import java.util.*;

import javax.sql.*;

import org.apache.logging.log4j.*;

import unknow.json.*;
import unknow.json.JsonValue.JsonString;
import unknow.orm.*;
import unknow.orm.mapping.Entity.ColEntry;
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
					e=new Entity.ColEntry(col, obj.optString("jname"), obj.optString("setter"), obj.optBoolean("key"));
					}
				}
			if(e!=null)
				entries.add(e);
			}
		}

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

				Map<String,Set<Entry>> map=new HashMap<String,Set<Entry>>();
				map.put(table.getName(), set);
				entries.add(new Entity.EntityEntry(new Entity<>(clazz, map), jname, null));
				}
			}
		}

	private void loadDaos(JsonObject daosCfg) throws SQLException, JsonException
		{
		for(String clazz:daosCfg)
			{
			try
				{
				Class<?> c=Class.forName(clazz);
				JsonArray a=daosCfg.getJsonArray(clazz);
				Map<String,Set<Entity.Entry>> cols=new HashMap<String,Set<Entity.Entry>>();
				for(int i=0; i<a.length(); i++)
					{
					JsonValue v=a.get(i);
					Set<Entity.Entry> entries=new HashSet<Entity.Entry>();
					String table;
					if(v instanceof JsonObject)
						{
						JsonObject o=(JsonObject)v;
						table=o.getString("table");
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
						table=((JsonString)v).value();
						Table t=tables.get(table);
						for(Column col:t.getColumns())
							entries.add(new Entity.ColEntry(col, null, null));
						}
					else
						throw new JsonException("Expected JsonObject or JsonString for '"+clazz+"' daos");

					cols.put(table, entries);
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
		return OrmUtils.convert(sqlType, type, rs, name);
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
		return OrmUtils.toJavaType(sqlType, type);
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

	public Query createInsert(String query) throws SQLException
		{
		return new Query(this, query, Statement.RETURN_GENERATED_KEYS);
		}

	/**
	 * @return number of row updated per table.
	 */
	public int[] update(Database db, Object o) throws SQLException
		{
		Entity<?> e=db.getMapping(o.getClass());
		StringBuilder sql=new StringBuilder();
		int[] ret=new int[e.entities.size()];
		int r=0;
		for(String table:e.entities.keySet())
			{
			sql.append("UPDATE  ");
			sql.append(table);
			sql.append(" SET ");

			boolean first=true;
			for(Entry en:e.entities.get(table))
				{
				if(!first)
					sql.append(',');
				else
					first=false;
				OrmUtils.appendUpdate(sql, table, en);
				}
			Query q=new Query(this, sql.toString(), Statement.NO_GENERATED_KEYS);
			for(String t:e.entities.keySet())
				{
				int i=0;
				for(Entry en:e.entities.get(t))
					{
					i=OrmUtils.appendValue(q, i, t, en, o);
					}
				}
			ret[r++]=q.executeUpdate();
			q.close();
			}
		return ret;
		}

	public void insert(Object o) throws SQLException
		{
		Entity<?> e=getMapping(o.getClass());
		StringBuilder sql=new StringBuilder();

		List<Entity.ColEntry> keys=new ArrayList<Entity.ColEntry>();
		for(String table:e.entities.keySet())
			{
			keys.clear();
			sql.append("INSERT INTO  ");
			sql.append(table);
			sql.append('(');
			boolean first=true;
			for(Entry en:e.entities.get(table))
				{
				if(!first)
					sql.append(',');
				else
					first=false;
				OrmUtils.appendColName(keys, sql, table, en);
				}
			sql.append(") VALUES (");

			first=true;
			for(Entry en:e.entities.get(table))
				{
				if(!first)
					sql.append(',');
				else
					first=false;
				OrmUtils.appendInsertValues(sql, table, en);
				}
			Query q=new Query(this, sql.toString(), Statement.RETURN_GENERATED_KEYS);
			for(String t:e.entities.keySet())
				{
				int i=0;
				for(Entry en:e.entities.get(t))
					{
					i=OrmUtils.appendValue(q, i, t, en, o);
					}
				}
			QueryResult krs=q.executeInsert();
			int i=0;
			while (krs.next())
				{
				ColEntry colEntry=keys.get(i++);
				OrmUtils.setValue(colEntry, o, krs.getInt(i));
				}
			krs.close();
			q.close();
			}
		}
	}
