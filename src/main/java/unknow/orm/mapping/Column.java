/*******************************************************************************
 * Copyright (c) 2014 Unknow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.html
 * 
 * Contributors:
 * Unknow - initial API and implementation
 ******************************************************************************/
package unknow.orm.mapping;

public class Column
	{
	private String name;
	private int sqlType;
	private String type;
	private String remark;
	private int length;
	private boolean ai;

	public Column(String colName, int sqlType, String type, int length, String remark, String ai)
		{
		this.name=colName;
		this.sqlType=sqlType;
		this.type=type;
		this.remark=remark;
		this.length=length;
		this.ai="YES".equals(ai);
		}

	public String getName()
		{
		return name;
		}

	public int getSqlType()
		{
		return sqlType;
		}

	public String getType()
		{
		return type;
		}

	public int getLength()
		{
		return length;
		}

	public boolean isAutoIncrement()
		{
		return ai;
		}

	public String toString()
		{
		return type+" ("+sqlType+") "+name+(remark!=null?" "+remark:"");
		}
	}
