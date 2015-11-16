package unknow.orm;

public class TestEntity
	{
	Integer id;
	String name;
	Integer value;

	public TestEntity()
		{
		}

	public TestEntity(int id, String name, int value)
		{
		this.id=id;
		this.name=name;
		this.value=value;
		}

	public boolean equals(Object o)
		{
		if(!(o instanceof TestEntity))
			return false;
		TestEntity e=(TestEntity)o;
		if(id==null&&e.id!=null||id!=null&&e.id==null||id!=e.id)
			return false;
		if(name==null&&e.name!=null||name!=null&&e.name==null||!name.equals(e.name))
			return false;
		if(value==null&&e.value!=null||value!=null&&e.value==null||value!=e.value)
			return false;
		return true;
		}

	public String toString()
		{
		return id+": "+name+" "+value;
		}
	}
