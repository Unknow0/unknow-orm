package unknow.orm.reflect.impl;

import unknow.orm.reflect.*;

public final class ReflectInstantiator<T> extends Instantiator<T>
	{
	public ReflectInstantiator(Class<T> clazz)
		{
		super(clazz);
		}

	public T newInstance() throws ReflectException
		{
		try
			{
			return clazz.newInstance();
			}
		catch (Exception e)
			{
			throw new ReflectException(e);
			}
		}
	}
