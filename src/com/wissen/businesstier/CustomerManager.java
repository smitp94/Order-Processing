package com.wissen.businesstier;



import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import com.wissen.persistencetier.CustomerService;

@Component
@Configuration
@ComponentScan("com.wissen.persistencetier")
public class CustomerManager {
	
	@Autowired
	private CustomerService customerService;
	
	public String addNewCustomer(CustomerNew customer){
		try{
			//new CustomerValidator().validate(customer);
			//CustomerService service=new CustomerService();
			return customerService.addNewCustomer(customer);
		}
		catch(Exception e){
			Logger logger=Logger.getLogger(this.getClass());
			logger.error(e.getMessage(),e);
		}
		return "FAIL: Unable to add customer to database";
		
	}
	
	public CustomerNew getCustomerById(Long ID){
		//CustomerService service=new CustomerService();
		try{
			if(customerService.findCustomer(ID)){
				return customerService.getCustomerById(ID);
			}else{
				throw new Exception("Manager.INVALID_ID");
			}
		}
		catch(Exception e){
			Logger logger=Logger.getLogger(CustomerManager.class);
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
	}
	
	public CustomerNew getCustomerNewById(Long ID){
		//CustomerService service=new CustomerService();
		try{			
			return customerService.getCustomerNewById(ID);			
		}
		catch(Exception e){
			Logger logger=Logger.getLogger(CustomerManager.class);
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<CustomerNew> getAllCustomerDetails(){
		//CustomerService service=new CustomerService();
		return customerService.getAllCustomerDetails();
	}
	
	public String deleteCustomer(Long ID){
		//CustomerService service=new CustomerService();
		System.out.println("In delete method of manager class");
		return customerService.deleteCustomer(ID);
	}
	
	public Boolean validateLoginCredentials(String email, String password){
		//CustomerService service=new CustomerService();
		System.out.println("In Customer Manager");
		return customerService.validateLoginCredentials(email, password);
	}
	
	/*public String updateCustomer(Customer customer){
		return customerService.updateCustomer(customer);
	}*/
	
	public String updateCustomerNew(CustomerNew customer) {
		return customerService.updateCustomerNew(customer);
	}
	
	public List<CustomerNew> getCustomersByDomain(String domainName) {
		return customerService.getCustomersByDomain(domainName);
	}
}
