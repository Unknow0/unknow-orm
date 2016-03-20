package unknow.orm.reflect;

import java.util.*;

import com.esotericsoftware.reflectasm.*;

public class AccessorHelper
	{
	private static final Map<Class<?>,AccessorHelper> accessCache=new HashMap<Class<?>,AccessorHelper>();

	private FieldAccess field;
	private MethodAccess method;

	private AccessorHelper(Class<?> c)
		{
		field=FieldAccess.get(c);
		method=MethodAccess.get(c);
		}

	public Object invokeMethod(Object o, int methodIndex, Object... arg)
		{
		return method.invoke(o, methodIndex, arg);
		}

	public void setField(Object o, int fieldIndex, Object v)
		{
		field.set(o, fieldIndex, v);
		}

	@SuppressWarnings("unchecked")
	public <T> T getField(Object o, int fieldIndex)
		{
		return (T)field.get(o, fieldIndex);
		}

	public int methodIndex(String name, Class<?>... params)
		{
		return method.getIndex(name, params);
		}

	public int fieldIndex(String name)
		{
		return field.getIndex(name);
		}

	public static AccessorHelper get(Class<?> clazz)
		{
		synchronized (accessCache)
			{
			AccessorHelper accessorHelper=accessCache.get(clazz);
			if(accessorHelper==null)
				{
				accessorHelper=new AccessorHelper(clazz);
				accessCache.put(clazz, accessorHelper);
				}
			return accessorHelper;
			}
		}
	}