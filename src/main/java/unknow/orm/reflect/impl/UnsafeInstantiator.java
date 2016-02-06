package unknow.orm.reflect.impl;

import unknow.common.*;
import unknow.orm.reflect.*;

@SuppressWarnings("restriction")
public final class UnsafeInstantiator<T> extends Instantiator<T>
	{
	public UnsafeInstantiator(Class<T> clazz)
		{
		super(clazz);
		}

	@SuppressWarnings("unchecked")
	public T newInstance() throws ReflectException
		{
		try
			{
			return (T)Reflection.unsafe().allocateInstance(clazz);
			}
		catch (Exception e)
			{
			throw new ReflectException(e);
			}
		}
	}
