package unknow.orm.reflect.impl;

import java.lang.reflect.*;

import unknow.orm.reflect.*;

public final class ReflectSetter implements Setter
	{
	private Field field;
	private Method method;

	public ReflectSetter(Field f, Method m)
		{
		field=f;
		method=m;

		field.setAccessible(true);
		if(method!=null)
			method.setAccessible(true);
		}

	public void set(Object o, Object v) throws ReflectException
		{
		Exception cause=null;
		try
			{
			if(method!=null)
				{
				try
					{
					method.invoke(o, v);
					return;
					}
				catch (Exception e)
					{
					cause=e;
					}
				}
			field.set(o, v);
			}
		catch (Exception e)
			{
			throw new ReflectException("Failed to get field '"+field.getName()+"'", cause==null?e:cause);
			}
		}
	}
