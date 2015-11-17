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
import unknow.orm.criteria.*;
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
	private boolean caseSensitive;

	/**
	 * @param ds datasource to use.
	 * @param cfg sould contains tables description.
	 */
	public Database(DataSource ds, JsonObject cfg, boolean caseSensitive) throws SQLException, JsonException, ClassNotFoundException, ClassCastException
		{
		this.ds=ds;
		this.caseSensitive=caseSensitive;
		loadTablesDesc();
		loadDaos(cfg);
		}

	private void loadCol(Set<Entry> entries, Table table, JsonObject colcfg) throws JsonException
		{
		Entity.ColEntry e=null;
		for(String colName:colcfg)
			{
			JsonValue opt=colcfg.get(colName);
			Column col=table.getColumn(colName, caseSensitive);
			if(opt instanceof JsonString)
				{
				e=new Entity.ColEntry(col, ((JsonString)opt).value(), col.isAutoIncrement(), false);
				}
			else if(opt instanceof JsonObject)
				{
				JsonObject obj=(JsonObject)opt;
				e=new Entity.ColEntry(col, obj.optString("jname"), obj.optString("setter"), obj.optBoolean("key", false), obj.optBoolean("ai", col.isAutoIncrement()));
				}

			logger.info("> load col %s> %s", e, col);
			if(e!=null)
				entries.add(e);
			}
		}

	public void loadField(Table table, JsonObject fields, Set<Entity.Entry> entries) throws JsonException, ClassNotFoundException
		{
		for(String jname:fields)
			{
			JsonObject fieldcfg=fields.getJsonObject(jname);
			Class<?> clazz=Class.forName(fieldcfg.getString("class"));

			Set<Entity.Entry> set=new HashSet<Entity.Entry>();

			JsonObject colcfg=fieldcfg.optJsonObject("columns");
			if(colcfg!=null)
				loadCol(set, table, colcfg);

			JsonObject fc=fieldcfg.optJsonObject("fields");
			if(fc!=null)
				loadField(table, fc, set);

			entries.add(new Entity.EntityEntry(new Entity<>(clazz, table, set), jname, null));
			}
		}

	private void loadDaos(JsonObject daosCfg) throws SQLException, JsonException
		{
		for(String clazz:daosCfg)
			{
			try
				{
				Class<?> c=Class.forName(clazz);
				JsonObject o=daosCfg.getJsonObject(clazz);
				Set<Entity.Entry> entries=new HashSet<Entity.Entry>();

				String t=o.getString("table");
				JsonObject colcfg=o.optJsonObject("columns");
				JsonObject fields=o.optJsonObject("fields");

				logger.info("load dao for %s", t);
				if(!caseSensitive)
					t=t.toLowerCase();
				Table table=tables.get(t);

				if(colcfg!=null)
					loadCol(entries, table, colcfg);
				if(fields!=null)
					loadField(table, fields, entries);

				mapping.put(c, new Entity<>(c, table, entries));
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

					if(!caseSensitive)
						name=name.toLowerCase();
					tables.put(name, table);
					}
				}
			}
		}

	public <T> T convert(Class<T> expected, Column col, ResultSet rs, String alias) throws SQLException
		{
		String label=alias+"."+col.getName();
		if(typesMapping!=null)
			{
			for(TypeConvertor typeConvert:typesMapping)
				{
				if(typeConvert.canConvert(expected, col))
					return typeConvert.convert(expected, col, rs, label);
				}
			}
		return OrmUtils.convert(expected, col, rs, label);
		}

	@SuppressWarnings("unchecked")
	public <T> Entity<T> getMapping(Class<T> entity) throws SQLException
		{
		Entity<?> e=mapping.get(entity);
		if(e==null)
			throw new SQLException("no mapping founf for class '"+entity.getName()+"'");

		return (Entity<T>)e;
		}

	public void setTypesMapping(TypeConvertor[] types)
		{
		typesMapping=types;
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

	public Query createInsert(String query) throws SQLException
		{
		return new Query(this, query, Statement.RETURN_GENERATED_KEYS);
		}

	public Criteria createCriteria(Class<?> entity) throws SQLException
		{
		return createCriteria(entity, "this");
		}

	public Criteria createCriteria(Class<?> entity, String alias) throws SQLException
		{
		return new Criteria(this, getMapping(entity), alias);
		}

	/**
	 * @return number of row updated.
	 */
	public int update(Object o) throws SQLException
		{
		Entity<?> e=getMapping(o.getClass());
		StringBuilder sql=new StringBuilder();
		int ret;

		sql.append("UPDATE  ");
		sql.append(e.table.getName());
		sql.append(" SET ");

		boolean coma=false;
		for(Entry en:e.entries)
			{
			if(coma)
				sql.append(',');
			coma=OrmUtils.appendUpdate(sql, en, false);
			}
		removeEnding(sql, ',');
		sql.append(" WHERE ");
		coma=false;
		for(Entry en:e.entries)
			{
			if(coma)
				sql.append(',');
			coma=OrmUtils.appendUpdate(sql, en, true);
			}
		removeEnding(sql, ',');
		Query q=new Query(this, sql.toString(), Statement.NO_GENERATED_KEYS);
		int i=1;
		for(Entry en:e.entries)
			{
			i=OrmUtils.appendValue(q, i, en, o, false);
			}
		for(Entry en:e.entries)
			{
			i=OrmUtils.appendValue(q, i, en, o, true);
			}
		ret=q.executeUpdate();
		q.close();

		return ret;
		}

	private static final void removeEnding(StringBuilder sb, char c)
		{
		if(sb.charAt(sb.length()-1)==c)
			sb.setLength(sb.length()-1);
		}

	public void insert(Object o) throws SQLException
		{
		Entity<?> e=getMapping(o.getClass());
		StringBuilder sql=new StringBuilder();

		List<Entity.ColEntry> keys=new ArrayList<Entity.ColEntry>();
		keys.clear();
		sql.append("INSERT INTO  ");
		sql.append(e.table.getName());
		sql.append(" (");
		boolean coma=false;
		for(Entry en:e.entries)
			{
			if(coma)
				sql.append(',');
			coma=OrmUtils.appendColName(keys, sql, en, o);
			}
		removeEnding(sql, ',');
		sql.append(") VALUES (");

		coma=false;
		for(Entry en:e.entries)
			{
			if(coma)
				sql.append(',');
			coma=OrmUtils.appendInsertValues(sql, en, o);
			}
		removeEnding(sql, ',');
		sql.append(')');
		Query q=new Query(this, sql.toString(), Statement.RETURN_GENERATED_KEYS);
		int i=1;
		for(Entry en:e.entries)
			{
			i=OrmUtils.appendValue(q, i, en, o);
			}
		QueryResult krs=q.executeInsert();
		i=0;
		while (krs.next())
			{
			ColEntry colEntry=keys.get(i++);
			OrmUtils.setValue(colEntry, o, krs.getInt(i));
			}
		krs.close();
		q.close();
		}
	}
