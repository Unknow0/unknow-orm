package unknow.orm.reflect;

import org.objenesis.instantiator.*;

public abstract class Instantiator<T> implements ObjectInstantiator<T>
	{
	protected Class<T> clazz;

	protected Instantiator(Class<T> clazz)
		{
		this.clazz=clazz;
		}

	public abstract T newInstance();

	public String className()
		{
		return clazz.getName();
		}
	}
