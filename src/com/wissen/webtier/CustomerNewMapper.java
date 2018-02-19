package com.wissen.webtier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;

import com.wissen.businesstier.CustomerNew;

public class CustomerNewMapper implements RowMapper<CustomerNew>{{

}

@Override
public CustomerNew mapRow(ResultSet resultSet, int rowNum) throws SQLException {
	try{
		CustomerNew customer=new CustomerNew();
		customer.setCustomerId(resultSet.getLong("customer_id"));
		customer.setCustomerName(resultSet.getString("customer_name"));
		customer.setMobile(resultSet.getLong("mobile"));
		customer.setEmail(resultSet.getString("email"));
		customer.setPassword(resultSet.getString("password"));
		
		Long sid=resultSet.getLong("sales_rep");
		
		if( sid!=0){
			customer.setSalesRep(sid);			
		}else{
			customer.setSalesRep(null);			
		}
		
		//convert java.sql.Date to java.util.Dat
		java.util.Date udate=new java.util.Date(resultSet.getDate("birthdate").getTime());
		customer.setBirthdate(udate);
		return customer;
	}
	catch(Exception e){
		e.printStackTrace();
	}
	return null;
}
}
