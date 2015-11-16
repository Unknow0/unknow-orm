unknow-orm
==========

Simple ORM framework

Mappings.load(String, Cfg) will initialize the framework

Cfg format
=========
	{
	<database name>:
		{
		"daos":
			{
			<full qualified class>:
				{
				"table": <table name>,
				"columns": <columns cfg>
				"fields": <fields cfg>
				}
			},
		"connection":
			{
			"type": "jdbc",
			"url": <url>,
			"user": <user>,
			"pass": <pass>,
			"max_idle": <max idle>	// if omited database connection won't be pooled
			}
			or
			{
			"type": "jndi",
			"url": <jndi url>
			},
		"types":
			[
			<full qualified class>	// add a TypeConvertor, they will be checked in this order
			...
			],
		"case_sensitive": TRUE/FALSE // if table/column should be check with the case or not
		}
	}

Column cfg
======
if database column name are the same as java.
	[
	<col name>,
	<col name>
	...
	]
	
if they aren't
	{
	<col name>: <java name>,
	...
	}

also you can specify setter if you don't use `set<Jname>`
	{
	<col name>:
		{
		"jname": <java name>,
		"setter": <setter function>
		}
	}

Field cfg
=====
	{
	<field name>: <column name>
	...
	}

if your field is another Dao/Pojo:
	{
	<field name>:
		{
		"class": <full calified class>
		"columns": <columns cfg>
		"fields": <fields cfg>
		}
	}

