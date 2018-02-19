package com.wissen.businesstier;

import org.apache.log4j.Logger;

import com.wissen.utility.AppConfig;

public class CustomerValidator {
	/**
	 * 
	 * @param customerName
	 * @return true if customer name is of only alphabets 
	 * and/or space(s) else returns false
	 */
	public Boolean isValidCustomerName(String customerName){
		String regex="^[a-zA-Z ]*$";
		if(customerName.matches(regex)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param email
	 * @return true if email is a valid emailId else return false
	 */
	public Boolean isValidEmail(String email){
		String regex="^[a-z]+[a-z0-9._]+@{1}[a-z]{3,}.{1}[a-z]{2,}$";
		if(email.matches(regex)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param mobile
	 * @return false if all the digits are same(typically service provider's number)  else r				returns true
	 */
	public Boolean isValidMobile(Long mobile){
		String smobile=mobile.toString();
		Boolean flag=false;
		char firstDigit=smobile.charAt(0);
		for(int i=1;i<smobile.length();i++){
			if(firstDigit!=smobile.charAt(i)){
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * @param customer
	 * @throws Exception
	 */
	public void validate(CustomerNew customer) throws Exception{
		String error_status=null;
		try{
			if(!this.isValidCustomerName(customer.getCustomerName())){
				error_status="Validator.INVALID_USERNAME";
			}else if(!this.isValidEmail(customer.getEmail())){
				error_status="Validator.INVALID_EMAIL";
			}else if(!this.isValidMobile(customer.getMobile())){
				error_status="Validator.INVALID_MOBILE";
			}
			
			if(error_status != null){
					throw new Exception(error_status);		
			}
		}
		catch(Exception e){
			Logger logger=Logger.getLogger(this.getClass());
			String message=AppConfig.PROPERTIES.getProperty(e.getMessage());
			logger.error(message,e);
			throw e;
		}
	}
}
