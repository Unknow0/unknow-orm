package unknow.orm.reflect.impl;

import java.lang.reflect.*;

import unknow.common.*;
import unknow.orm.reflect.*;

@SuppressWarnings("restriction")
public class UnsafeSetter implements Setter
	{
	protected long field;

	public UnsafeSetter(Field f)
		{
		field=Reflection.unsafe().objectFieldOffset(f);
		}

	public void set(Object o, Object v)
		{
		Reflection.unsafe().putObject(o, field, v);
		}

	public static UnsafeSetter get(Class<?> clazz, String field, String method)
		{
		Field f=Reflection.getField(clazz, field);
		Class<?> type=f.getType();
		if(type.isPrimitive())
			{
			if(type.equals(byte.class))
				return new UnsafeSetter.Byte(f);
			if(type.equals(char.class))
				return new UnsafeSetter.Char(f);
			if(type.equals(short.class))
				return new UnsafeSetter.Short(f);
			if(type.equals(int.class))
				return new UnsafeSetter.Int(f);
			if(type.equals(long.class))
				return new UnsafeSetter.Long(f);
			if(type.equals(float.class))
				return new UnsafeSetter.Float(f);
			if(type.equals(double.class))
				return new UnsafeSetter.Double(f);
			}
		return new UnsafeSetter(f);
		}

	private static class Byte extends UnsafeSetter
		{
		public Byte(Field f)
			{
			super(f);
			}

		public void set(Object o, Object v)
			{
			Reflection.unsafe().putByte(o, field, (byte)v);
			}
		}

	private static class Char extends UnsafeSetter
		{
		public Char(Field f)
			{
			super(f);
			}

		public void set(Object o, Object v)
			{
			Reflection.unsafe().putChar(o, field, (char)v);
			}
		}

	private static class Short extends UnsafeSetter
		{
		public Short(Field f)
			{
			super(f);
			}

		public void set(Object o, Object v)
			{
			Reflection.unsafe().putShort(o, field, (short)v);
			}
		}

	private static class Int extends UnsafeSetter
		{
		public Int(Field f)
			{
			super(f);
			}

		public void set(Object o, Object v)
			{
			Reflection.unsafe().putInt(o, field, (int)v);
			}
		}

	private static class Long extends UnsafeSetter
		{
		public Long(Field f)
			{
			super(f);
			}

		public void set(Object o, Object v)
			{
			Reflection.unsafe().putLong(o, field, (long)v);
			}
		}

	private static class Float extends UnsafeSetter
		{
		public Float(Field f)
			{
			super(f);
			}

		public void set(Object o, Object v)
			{
			Reflection.unsafe().putFloat(o, field, (float)v);
			}
		}

	private static class Double extends UnsafeSetter
		{
		public Double(Field f)
			{
			super(f);
			}

		public void set(Object o, Object v)
			{
			Reflection.unsafe().putDouble(o, field, (double)v);
			}
		}
	}
