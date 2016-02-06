package unknow.orm.criteria;

import java.sql.*;

import unknow.orm.mapping.*;

public abstract class Restriction
	{
	abstract int append(StringBuilder sb, String alias, Entity<?> e, int curParam) throws SQLException;

	abstract void setValue(PreparedStatement st) throws SQLException;

	public static Restriction eq(String property, Object value)
		{
		return new Op(property, "=", value);
		}

	public static Restriction ne(String property, Object value)
		{
		return new Op(property, "<>", value);
		}

	public static Restriction lt(String property, Object value)
		{
		return new Op(property, "<", value);
		}

	public static Restriction le(String property, Object value)
		{
		return new Op(property, "<=", value);
		}

	public static Restriction gt(String property, Object value)
		{
		return new Op(property, ">", value);
		}

	public static Restriction ge(String property, Object value)
		{
		return new Op(property, ">=", value);
		}

	public static Restriction isNull(String property)
		{
		return new Null(property, true);
		}

	public static Restriction isNotNull(String property)
		{
		return new Null(property, false);
		}

	public static Restriction sql(String sql)
		{
		return new Sql(sql);
		}

	public static Restriction or(Restriction left, Restriction right)
		{
		return new Binary(left, "or", right);
		}

	public static Restriction and(Restriction left, Restriction right)
		{
		return new Binary(left, "and", right);
		}

	private static class Op extends Restriction
		{
		private String property;
		private String operation;
		private Object value;
		private int param;

		private Op(String prop, String op, Object v)
			{
			property=prop;
			operation=op;
			value=v;
			}

		int append(StringBuilder sb, String alias, Entity<?> e, int curParam) throws SQLException
			{
			Entity.ColEntry col=e.findCol(property);
			if(alias!=null)
				sb.append(alias).append('.');
			sb.append(col.col.getName());
			sb.append(operation);
			if(value!=null)
				{
				sb.append("?");
				param=curParam++;
				}
			return curParam;
			}

		void setValue(PreparedStatement st) throws SQLException
			{
			// TODO type conversion?
			st.setObject(param, value);
			}
		}

	private static class Null extends Restriction
		{
		private String property;
		private boolean is;

		private Null(String property, boolean is)
			{
			this.property=property;
			this.is=is;
			}

		int append(StringBuilder sb, String alias, Entity<?> e, int curParam) throws SQLException
			{
			Entity.ColEntry col=e.findCol(property);
			if(alias!=null)
				sb.append(alias).append('.');
			sb.append(col.col.getName());
			sb.append("is");
			if(!is)
				sb.append(" not");
			sb.append(" null");
			return curParam;
			}

		void setValue(PreparedStatement st) throws SQLException
			{
			}
		}

	private static class Sql extends Restriction
		{
		private String sql;

		private Sql(String sql)
			{
			this.sql=sql;
			}

		int append(StringBuilder sb, String alias, Entity<?> e, int curParam)
			{
			sb.append(sql);
			return curParam;
			}

		void setValue(PreparedStatement st) throws SQLException
			{
			}
		}

	private static class Binary extends Restriction
		{
		private Restriction left;
		private Restriction right;
		private String op;

		private Binary(Restriction l, String op, Restriction r)
			{
			left=l;
			right=r;
			}

		int append(StringBuilder sb, String alias, Entity<?> e, int curParam) throws SQLException
			{
			sb.append('(');
			curParam=left.append(sb, alias, e, curParam);
			sb.append(") ");
			sb.append(op);
			sb.append(" (");
			curParam=right.append(sb, alias, e, curParam);
			sb.append(')');
			return curParam;
			}

		void setValue(PreparedStatement st) throws SQLException
			{
			right.setValue(st);
			left.setValue(st);
			}
		}
	}
