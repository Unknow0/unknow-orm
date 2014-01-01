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
package unknow.orm.mapping;

public class Column
	{
	private String javaName;
	private String setter;
	private String name;
	private int sqlType;
	private String type;
	private String remark;

	public Column(String javaName, String setter, String colName, int sqlType, String type, String remark)
		{
		this.javaName=javaName;
		this.setter=setter;
		this.name=colName;
		this.sqlType=sqlType;
		this.type=type;
		this.remark=remark;
		}
	
	public String getName()
		{
		return name;
		}

	public String getJavaName()
		{
		return javaName;
		}
	
	public String getSetter()
		{
		return setter;
		}

	public int getSqlType()
		{
		return sqlType;
		}
	public String getType()
		{
		return type;
		}
	
	public String toString()
		{
		return type+" ("+sqlType+") "+name+(remark!=null?" "+remark:"");
		}
	}
