package unknow.orm.reflect.impl;

import java.lang.reflect.*;

import unknow.common.*;
import unknow.orm.reflect.*;

@SuppressWarnings("restriction")
public final class UnsafeGetter implements Getter
	{
	private long field;

	public UnsafeGetter(Class<?> clazz, String fieldName)
		{
		Field f=Reflection.getField(clazz, fieldName);
		field=Reflection.unsafe().objectFieldOffset(f);
		}

	public Object get(Object o)
		{
		return Reflection.unsafe().getObject(o, field);
		}
	}
