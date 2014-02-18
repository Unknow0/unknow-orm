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
				[
					{
					"table": <table name>,
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
						// or if setter aren't set<Jname>
						<col name>:
							{
							"jname": <java name>,
							"setter": <setter function>
							}
						}
						...
					}
					// if this dao use more than one table
				]
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
			<full qualified class>	// add a TypeConvertor, they will be cecked in this order
			...
			]
		}
	}
