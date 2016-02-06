package unknow.orm.reflect.impl;

import java.lang.reflect.*;

import unknow.orm.reflect.*;

public final class ReflectGetter implements Getter
	{
	private Field field;
	private Method method;

	public ReflectGetter(Field f, Method m)
		{
		field=f;
		method=m;

		field.setAccessible(true);
		if(method!=null)
			method.setAccessible(true);
		}

	public Object get(Object o) throws ReflectException
		{
		Exception cause=null;
		try
			{
			try
				{
				if(method!=null)
					return method.invoke(o);
				}
			catch (Exception e)
				{
				cause=e;
				}
			return field.get(o);
			}
		catch (Exception e)
			{
			throw new ReflectException("Failed to get field '"+field.getName()+"'", cause==null?e:cause);
			}
		}
	}
