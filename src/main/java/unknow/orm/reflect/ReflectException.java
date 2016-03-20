package unknow.orm.reflect;

public class ReflectException extends RuntimeException
	{
	private static final long serialVersionUID=1L;

	public ReflectException(String msg, Throwable e)
		{
		super(msg, e);
		}

	public ReflectException(String msg)
		{
		super(msg);
		}

	public ReflectException(Throwable e)
		{
		super(e);
		}
	}
