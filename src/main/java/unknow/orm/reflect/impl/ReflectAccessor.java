package unknow.orm.reflect.impl;

import unknow.orm.reflect.*;

public final class ReflectAccessor implements Getter, Setter
	{
	private AccessorHelper access;
	private int methodIndex=-1;
	private int fieldIndex=-1;

	public ReflectAccessor(AccessorHelper access, int fieldIndex, int methodIndex)
		{
		this.access=access;
		this.fieldIndex=fieldIndex;
		this.methodIndex=methodIndex;
		}

	public Object get(Object o) throws ReflectException
		{
		Exception cause=null;
		try
			{
			if(methodIndex>=0)
				return access.invokeMethod(o, methodIndex);
			}
		catch (Exception e)
			{
			cause=e;
			}
		try
			{
			return access.getField(o, fieldIndex);
			}
		catch (Exception e)
			{
			cause=e;
			}
		throw new ReflectException(cause);
		}

	public void set(Object o, Object v) throws ReflectException
		{
		Exception cause=null;
		try
			{
			if(methodIndex>=0)
				{
				access.invokeMethod(o, methodIndex, v);
				return;
				}
			}
		catch (Exception e)
			{
			cause=e;
			}
		try
			{
			access.setField(o, fieldIndex, v);
			return;
			}
		catch (Exception e)
			{
			cause=e;
			}
		throw new ReflectException(cause);
		}
	}
