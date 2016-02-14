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

	public static class Byte extends UnsafeSetter
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

	public static class Char extends UnsafeSetter
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

	public static class Short extends UnsafeSetter
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

	public static class Int extends UnsafeSetter
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

	public static class Long extends UnsafeSetter
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

	public static class Float extends UnsafeSetter
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

	public static class Double extends UnsafeSetter
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
