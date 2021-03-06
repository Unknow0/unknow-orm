package unknow.orm.criteria;

import java.sql.*;
import java.util.*;

import unknow.orm.mapping.*;

public class Join extends Criteria
	{
	private List<On> on=new ArrayList<On>();

	public Join(Database database, Entity<?> entity, String alias)
		{
		super(database, entity, alias);
		setProjection(Projection.none());
		}

	protected void appendSelect(StringBuilder sb)
		{
		Collection<String> proj=projection.properties(entity);
		if(proj==null||!proj.isEmpty())
			sb.append(',');
		super.appendSelect(sb);
		}

	void append(StringBuilder sb, String parentAlias)
		{
		sb.append(" join ");
		sb.append(entity.table.getName()).append(' ').append(alias);

		if(!on.isEmpty())
			{
			sb.append(" on ");
			boolean first=true;
			for(On o:on)
				{
				if(first)
					first=false;
				else
					sb.append(" and ");
				o.append(sb, parentAlias, alias);
				}
			}
		}

	public void on(On r)
		{
		on.add(r);
		}

	void setValues(PreparedStatement st) throws SQLException
		{
		for(Restriction r:where)
			r.setValue(st);
		}
	}
