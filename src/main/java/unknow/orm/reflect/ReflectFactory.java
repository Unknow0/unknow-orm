package unknow.orm.reflect;

import java.lang.reflect.*;

import unknow.common.*;
import unknow.orm.reflect.impl.*;

public class ReflectFactory
	{
	public Setter createSetter(Field field, Method setter)
		{
		if(Reflection.hasUnsafe)
			return new UnsafeSetter(field);
		return new ReflectSetter(field, setter);
		}

	public Getter createGetter(Field field, Method setter)
		{
		if(Reflection.hasUnsafe)
			return new UnsafeGetter(field);
		return new ReflectGetter(field, setter);
		}

	public <T> Instantiator<T> createInstantiator(Class<T> clazz)
		{
		if(Reflection.hasUnsafe)
			return new UnsafeInstantiator<T>(clazz);
		return new ReflectInstantiator<T>(clazz);
		}
	}
