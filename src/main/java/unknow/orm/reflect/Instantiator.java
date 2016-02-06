package unknow.orm.reflect;

public abstract class Instantiator<T>
	{
	protected Class<T> clazz;

	protected Instantiator(Class<T> clazz)
		{
		this.clazz=clazz;
		}

	public abstract T newInstance() throws ReflectException;

	public String className()
		{
		return clazz.getName();
		}
	}
