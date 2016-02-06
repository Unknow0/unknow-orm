package unknow.orm.criteria;

public abstract class On
	{
	abstract void append(StringBuilder sb, String aliasLeft, String aliasRight);

	public static On eq(String propLeft, String propRight)
		{
		return new Op(propLeft, "=", propRight);
		}

	public static On ne(String propLeft, String propRight)
		{
		return new Op(propLeft, "<>", propRight);
		}

	public static On lt(String propLeft, String propRight)
		{
		return new Op(propLeft, "<", propRight);
		}

	public static On le(String propLeft, String propRight)
		{
		return new Op(propLeft, "<=", propRight);
		}

	public static On gt(String propLeft, String propRight)
		{
		return new Op(propLeft, ">", propRight);
		}

	public static On ge(String propLeft, String propRight)
		{
		return new Op(propLeft, ">=", propRight);
		}

	public static On or(On left, On right)
		{
		return new Binary(left, "or", right);
		}

	public static On and(On left, On right)
		{
		return new Binary(left, "and", right);
		}

	private static class Op extends On
		{
		private String propLeft;
		private String operation;
		private String propRight;

		private Op(String prop, String op, String right)
			{
			propLeft=prop;
			operation=op;
			propRight=right;
			}

		void append(StringBuilder sb, String aliasLeft, String aliasRight)
			{
			if(aliasLeft!=null)
				sb.append(aliasLeft).append('.');
			sb.append(propLeft);
			sb.append(operation);
			if(aliasRight!=null)
				sb.append(aliasRight).append('.');
			sb.append(propRight);
			}
		}

	private static class Binary extends On
		{
		private On left;
		private On right;
		private String op;

		private Binary(On l, String op, On r)
			{
			left=l;
			right=r;
			}

		void append(StringBuilder sb, String aliasLeft, String aliasRight)
			{
			sb.append('(');
			left.append(sb, aliasLeft, aliasRight);
			sb.append(") ");
			sb.append(op);
			sb.append(" (");
			right.append(sb, aliasLeft, aliasRight);
			sb.append(')');
			}
		}
	}
