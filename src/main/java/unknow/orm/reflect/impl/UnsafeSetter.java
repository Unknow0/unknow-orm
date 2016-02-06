package unknow.orm.reflect.impl;

import java.lang.reflect.*;

import unknow.common.*;
import unknow.orm.reflect.*;

@SuppressWarnings("restriction")
public final class UnsafeSetter implements Setter
	{
	private long field;

	public UnsafeSetter(Field f)
		{
		field=Reflection.unsafe().objectFieldOffset(f);
		}

	public void set(Object o, Object v)
		{
		Reflection.unsafe().putObject(o, field, v);
		}
	}
