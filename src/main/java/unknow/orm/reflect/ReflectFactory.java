package unknow.orm.reflect;

import java.lang.reflect.*;

import unknow.common.*;
import unknow.orm.reflect.impl.*;

public class ReflectFactory
	{
	public Setter createSetter(Field field, Method setter)
		{
		if(Reflection.hasUnsafe)
			{
			Class<?> type=field.getType();
			if(type.isPrimitive())
				{
				if(type.equals(byte.class))
					return new UnsafeSetter.Byte(field);
				if(type.equals(char.class))
					return new UnsafeSetter.Char(field);
				if(type.equals(short.class))
					return new UnsafeSetter.Short(field);
				if(type.equals(int.class))
					return new UnsafeSetter.Int(field);
				if(type.equals(long.class))
					return new UnsafeSetter.Long(field);
				if(type.equals(float.class))
					return new UnsafeSetter.Float(field);
				if(type.equals(double.class))
					return new UnsafeSetter.Double(field);
				}
			return new UnsafeSetter(field);
			}
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
