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
				and/or
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
		"types": // optional
			[
			<full qualified class>	// add a TypeConvertor, they will be checked in this order
			...
			],
		"case_sensitive": TRUE/FALSE // optional if table/column should be check with the case or not
		}
	}

Column cfg
======
for simple column mapping:

	{
	<col name>: <java name>,
	...
	}

also you can specify setter if you don't use `set<Jname>`

	{
	<col name>:
		{
		"jname": <java name>,
		"setter": <setter function>,	// optional
		"key": TRUE/FALSE,	// optional set this column as a key
		"ai": TRUE/FALSE		// optional set this colmn as autoincrement
		}
	}

Field cfg
=====
if your field is another Dao/Pojo:

	{
	<field name>:
		{
		"class": <full calified class>
		"columns": <columns cfg>
		and/or
		"fields": <fields cfg>
		}
	}

