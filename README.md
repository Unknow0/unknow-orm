unknow-orm
==========

Simple ORM framework

Mappings.load(String, Cfg) will initialize the framework

Cfg format
=========
	{
	<database name>:
		{
		"tables":
			{
			<table name>:
				{
				"class": <full qualified class>,
				"columns":
					// if database column name are the same as java.
					[
					<col name>,
					<col name>
					...
					]
					// or if they aren't
					{
					<col name>: <java name>,
					...
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
					{
					<sql type name>: <full qualified class>	// assign a TypesConvert to this sql type
					...
					}
				}
			}
		}
	}
