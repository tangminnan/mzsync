package com.menzhenchaxun.information.dataSource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.menzhenchaxun.information.dataSource.DataSourceType.DataBaseType;

@Aspect
@Component
public class DataSourceAop {
		
	 	@Before("execution(* com.xinshineng.information.service.menzhen.service.impl..*.*(..))")
	    public void setDataSource1test01() {
	        System.err.println("test01业务");
	        DataSourceType.setDataBaseType(DataBaseType.TEST01);
	    }

	    @Before("execution(* com.xinshineng.information.service.shujuchaxun.service.impl..*.*(..))")
	    public void setDataSource2test02() {
	        System.err.println("test02业务");
	        DataSourceType.setDataBaseType(DataBaseType.TEST02);
	    }
	    
	    
}
