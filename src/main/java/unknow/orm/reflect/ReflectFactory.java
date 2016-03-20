package unknow.orm.reflect;

import unknow.common.*;
import unknow.orm.reflect.impl.*;

public class ReflectFactory
	{
	public Setter createSetter(Class<?> clazz, String field, String method)
		{
		if(Reflection.hasUnsafe)
			return UnsafeSetter.get(clazz, field, method);

		AccessorHelper acc=AccessorHelper.get(clazz);
		return new ReflectAccessor(acc, acc.fieldIndex(field), acc.methodIndex(method));
		}

	public Getter createGetter(Class<?> clazz, String field, String method)
		{
		if(Reflection.hasUnsafe)
			return new UnsafeGetter(clazz, field);

		AccessorHelper acc=AccessorHelper.get(clazz);
		return new ReflectAccessor(acc, acc.fieldIndex(field), acc.methodIndex(method));
		}

	public <T> Instantiator<T> createInstantiator(Class<T> clazz)
		{
		if(Reflection.hasUnsafe)
			return new UnsafeInstantiator<T>(clazz);
		return new ReflectInstantiator<T>(clazz);
		}
	}