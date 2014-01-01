/*******************************************************************************
 * Copyright (c) 2014 Unknow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 * Contributors:
 *     Unknow - initial API and implementation
 ******************************************************************************/
package unknow.orm;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

import unknow.common.*;
import unknow.json.*;
import unknow.orm.ds.*;
import unknow.orm.mapping.*;

public class Mappings
	{
	private static Map<String,Database> mapping=new HashMap<String,Database>();
	
	public static void load(String key, Cfg cfg) throws JsonException, SQLException, ClassNotFoundException, ClassCastException, InstantiationException, IllegalAccessException, NamingException
		{
		JsonObject o=cfg.getJsonObject(key);
		for(String db: o)
			{
			loadMapping(db, o.getJsonObject(db));
			}
		}
	
	public static void loadMapping(String dbName, JsonObject dbCfg) throws JsonException, SQLException, ClassNotFoundException, ClassCastException, InstantiationException, IllegalAccessException, NamingException
		{
		JsonObject tables=dbCfg.getJsonObject("tables");
		JsonObject o=dbCfg.getJsonObject("connection");
		String type=o.getString("type");
		DataSource ds= null;
		if(type.equals("jdbc"))
			{
			String url=o.getString("url");
			String user=o.getString("user");
			String pass=o.getString("pass");
			Integer idle=o.optInt("max_idle");
			if(idle==null)
				ds=new SimpleDataSource(url, user, pass);
			else
				ds=new PooledDataSource(url, user, pass, idle);
			}
		else if(type.equals("jndi"))
			{
			ds=(DataSource)new InitialContext().lookup(o.getString("url"));
			}
		else
			throw new JsonException("unknown connection type '"+type+"'.");
		
		Database database=new Database(ds, tables);
		mapping.put(dbName, database);
		
		o=dbCfg.optJsonObject("types");
		if(o!=null)
			{
			for(String t: o)
				{
				Class<?> clazz=Class.forName(o.getString(t));
				TypeConvert newInstance=(TypeConvert)clazz.newInstance();
				database.addTypesMapping(t, newInstance);
				}
			}
		}
	
	public static Database getDatabase(String database)
		{
		return mapping.get(database);
		}
	}
