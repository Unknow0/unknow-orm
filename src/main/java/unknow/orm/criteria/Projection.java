package unknow.orm.criteria;

import java.util.*;

import unknow.orm.mapping.*;

public abstract class Projection
	{
	public abstract Collection<String> properties(Entity<?> e);

	public static Projection property(String... p)
		{
		return new Property(p);
		}

	public static Projection keys()
		{
		return new PrimaryKey();
		}

	public static Projection all()
		{
		return new All();
		}

	public static Projection none()
		{
		return new None();
		}

	private static class Property extends Projection
		{
		public Collection<String> properties;

		public Property(String[] p)
			{
			properties=Arrays.asList(p);
			}

		public Collection<String> properties(Entity<?> e)
			{
			return properties;
			}
		}

	private static class PrimaryKey extends Projection
		{
		public Collection<String> properties(Entity<?> entity)
			{
			List<String> keys=new ArrayList<String>();
			for(Entity.Entry e:entity.entries)
				{
				if(e instanceof Entity.ColEntry&&((Entity.ColEntry)e).key)
					keys.add(e.javaName);
				}
			return keys;
			}
		}

	private static class All extends Projection
		{
		public Collection<String> properties(Entity<?> entity)
			{
			List<String> properties=new ArrayList<String>();
			for(Entity.Entry e:entity.entries)
				{
				properties.add(e.javaName);
				}
			return properties;
			}
		}

	private static class None extends Projection
		{
		public Collection<String> properties(Entity<?> entity)
			{
			return Collections.emptyList();
			}
		}
	}
