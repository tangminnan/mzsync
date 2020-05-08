package com.menzhenchaxun.information.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource  {

	@Override
	protected Object determineCurrentLookupKey() {
		
		DataSourceType.DataBaseType databaseType = DataSourceType.getDataBaseType();
		
		return databaseType;
	}

}
