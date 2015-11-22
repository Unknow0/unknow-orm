package unknow.orm.criteria;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.*;

import unknow.orm.*;
import unknow.orm.mapping.*;

public class Criteria
	{
	private static final Logger log=LogManager.getFormatterLogger(Criteria.class);

	private Database db;
	protected Entity<?> entity;
	protected String alias;
	protected List<Restriction> where=new ArrayList<Restriction>();
	protected Projection projection=Projection.all();
	protected List<Join> join=new ArrayList<Join>();

	public Criteria(Database database, Entity<?> e, String a)
		{
		db=database;
		entity=e;
		alias=a;
		}

	protected void appendSelect(StringBuilder sb)
		{
		entity.append(sb, alias, projection.properties(entity));
		for(Join j:join)
			{
			j.appendSelect(sb);
			}
		}

	protected boolean hasWhere()
		{
		if(!where.isEmpty())
			return true;
		for(Join j:join)
			{
			if(j.hasWhere())
				return true;
			}
		return false;
		}

	protected int appendWhere(StringBuilder sb, int param) throws SQLException
		{
		boolean first=true;
		for(Restriction r:where)
			{
			if(first)
				first=false;
			else
				sb.append(" and ");
			param=r.append(sb, alias, entity, param);
			}
		for(Join j:join)
			{
			if(j.hasWhere())
				{
				if(first)
					first=false;
				else
					sb.append(" and ");
				param=j.appendWhere(sb, param);
				}
			}
		return param;
		}

	public String getSql() throws SQLException
		{
		StringBuilder sb=new StringBuilder("select ");
		appendSelect(sb);

		sb.append(" from ").append(entity.table.getName()).append(" ").append(alias);
		for(Join j:join)
			{
			j.append(sb, alias);
			}

		if(hasWhere())
			{
			sb.append(" where ");
			appendWhere(sb, 1);
			}

		return sb.toString();
		}

	protected void populateAlias(Map<String,Entity<?>> map) throws SQLException
		{
		if(map.containsKey(alias))
			throw new SQLException("alias '"+alias+"' already defined");
		map.put(alias, entity);
		for(Join j:join)
			j.populateAlias(map);
		}

	public void setAlias(String a)
		{
		alias=a;
		}

	public void setProjection(Projection p)
		{
		projection=p;
		}

	public void add(Restriction r)
		{
		where.add(r);
		}

	public Join addJoin(Class<?> e, String alias) throws SQLException
		{
		Join j=new Join(db, db.getMapping(e), alias);
		join.add(j);
		return j;
		}

	public QueryResult execute() throws SQLException
		{
		Map<String,Entity<?>> map=new HashMap<String,Entity<?>>();
		populateAlias(map);

		Connection co=db.getConnection();
		String sql=getSql();
		log.trace(sql);
		PreparedStatement st=co.prepareStatement(sql);

		for(Restriction r:where)
			r.setValue(st);
		for(Join j:join)
			j.setValues(st);
		return new Result(db, co, st, map);
		}

	private static class Result extends QueryResult
		{
		private PreparedStatement st;
		private Connection co;

		protected Result(Database db, Connection co, PreparedStatement st, Map<String,Entity<?>> mapping) throws SQLException
			{
			super(db, st.executeQuery(), mapping);
			this.co=co;
			this.st=st;
			}

		public void close() throws SQLException
			{
			super.close();
			st.close();
			co.close();
			}
		}
	}
