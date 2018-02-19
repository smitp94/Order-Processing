package com.wissen.persistencetier;

import java.util.List;

import com.wissen.businesstier.CustomerNew;

public interface ICustomer {
	public String addNewCustomer(CustomerNew customer);
	public List<CustomerNew> getAllCustomerDetails();
	public CustomerNew getCustomerById(Long ID);
	public CustomerNew getCustomerNewById(Long ID);
	public Boolean findCustomer(Long ID);
	public String deleteCustomer(Long ID);
	public Boolean validateLoginCredentials(String email,String password);
	//public String updateCustomer(Customer customer);
	public String updateCustomerNew(CustomerNew customer);
	public List<CustomerNew> getCustomersByDomain(String domainName);
	
}
