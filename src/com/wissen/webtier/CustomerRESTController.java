package com.wissen.webtier;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wissen.businesstier.CustomerManager;
import com.wissen.businesstier.CustomerNew;

@RestController
@RequestMapping(value="/rest")
public class CustomerRESTController {
	
	@Autowired
	private CustomerManager manager;
	
	@RequestMapping(value="/all",method=RequestMethod.GET, produces="application/json" )
	public List<CustomerNew> getAllCustomerDetails(){
		System.out.println("In getAllCustomerDetails()");
		List<CustomerNew> customerList=manager.getAllCustomerDetails();
		if(customerList!=null){	
			System.out.println(customerList.get(0).getBirthdate());
			return customerList;
		}else{
			return null;		
		}		
	}
	
	@RequestMapping(value="/read/{id}",method=RequestMethod.GET, produces="application/json" )
	public CustomerNew getCustomerById(@PathVariable("id") Long id){
		CustomerNew customer=manager.getCustomerById(id);
		return customer;
				
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces="application/json") 
	public void deleteCustomer(@PathVariable("id") Long id) {
		System.out.println("In deleteCustomer() of RESTController");
		String message=manager.deleteCustomer(id); 
		System.out.println(message);				
		} 
	
		
	
	@RequestMapping(value = "/new", method = RequestMethod.POST, headers = "Accept=application/json") 
	public void addCustomer(@RequestBody CustomerNew customer) { 
		 System.out.println("Inside addCustomer() of REST Controller");
		 String message=manager.addNewCustomer(customer);	
		 System.out.println(message);
		} 
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes="application/json") 
	public void updateCustomer(@RequestBody CustomerNew customer) { 
		 String message=manager.updateCustomerNew(customer);	
		 System.out.println(message);
		} 
	
	/*@RequestMapping(method=RequestMethod.GET)
	public String login(){
		return "login";
	}*/
	
	@RequestMapping(value="/verify/{email}/{password}" ,method = RequestMethod.GET, produces="application/json")
	public void verifyCredentials( @PathVariable("email") String email, @PathVariable("password") String password){
		System.out.println(email+","+password);
		
		Boolean flag=manager.validateLoginCredentials(email, password);
		
		if(flag==true){		
			System.out.println("success");			
		}else{
			System.out.println("fail");			
		}
	}
	
		
	
	/*@RequestMapping("/customersbydomain.htm")
	public ModelAndView getCustomersByDomain(){	
		List<String> domainList=new ArrayList<>();
		domainList.add("gmail");domainList.add("yahoo");
		domainList.add("wissen");domainList.add("hotmail");
		System.out.println("domainList populated");
		ModelAndView model =new ModelAndView();
		model.addObject("myForm", new MyForm());
		model.addObject("domainList", domainList);
		model.setViewName("show_customers_domainwise");
		//return new ModelAndView("show_customers_domainwise","domainList",domainList);	
		return model;
	}
	
	@RequestMapping(value="/showDomainUsers.htm", method=RequestMethod.POST)
	@ResponseBody
	public void getCustomersByDomain( @RequestParam(value="name") String name){
		System.out.println(name);
		List<CustomerNew>  customerListByDomain=manager.getCustomersByDomain(name);
		
		//return "customers_domain";		
		if(customerList!=null){	
			System.out.println("2");
			return new ModelAndView("show_all_customers_pagewise","customerList",customerList);
		}else{
			return new ModelAndView("statuslog","status","Unable to process the request");		
		}			
	}
	
	
	
	@RequestMapping("/deleteCustomer")
	public ModelAndView deleteCustomer( @RequestParam(value="id", required=true) Long id){
		System.out.println("In deleteCustomer() of controller class");
		String message=manager.deleteCustomer(id);
		
		if(message.equals("SUCCESS")){
			return new ModelAndView("statuslog","status","Customer successfully deleted from our database");
		}else{
			return new ModelAndView("statuslog","status","Unable to delete the customer from our database");
		}	
	}
	
	@RequestMapping("/updateCustomer")
	public ModelAndView updateCustomer( @RequestParam(value="id", required=true) Long id){
		//Customer customer=manager.getCustomerById(id);
		CustomerNew customer=manager.getCustomerNewById(id);
		
		if(customer!=null){
			if(customer.getSalesRep()==null){
				customer.setSalesRep(0L);
			}
			SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
			String bdate=dateFormat.format(customer.getBirthdate());
			System.out.println(bdate);
			try {
				customer.setBirthdate(dateFormat.parse(bdate));
			} catch (ParseException e) {				
				e.printStackTrace();
			}
								
			return new ModelAndView("update_customer","customerBB",customer);
		}else{
			return new ModelAndView("statuslog","status","Invalid Customer ID");
		}	
	}
	
	@RequestMapping("/processupdate")
	public ModelAndView saveUpdatedCustomer(@ModelAttribute("customerBB") CustomerNew customer){
		//System.out.println("Hi");
		//System.out.println(customer.getCustomerId()+","+customer.getBirthdate()+","+customer.getSalesRep());
		//String message = manager.updateCustomer(customer);
		String message = manager.updateCustomerNew(customer);
		if(message.equals("SUCCESS")){
			return new ModelAndView("statuslog","status","Customer details updated to our database");
		}else{
			return new ModelAndView("statuslog","status","Unable to update the customer record");
		}	
	}
	
	
	@RequestMapping("/processupdate")
	public ModelAndView saveUpdatedCustomer(@ModelAttribute("customerBB") Customer customer){
		System.out.println("Hi");
		System.out.println(customer.getCustomerId()+","+customer.getBirthdate());
		String message = manager.updateCustomer(customer);
		if(message.equals("SUCCESS")){
			return new ModelAndView("statuslog","status","Customer details updated to our database");
		}else{
			return new ModelAndView("statuslog","status","Unable to update the customer record");
		}	
	}
	
	@RequestMapping("/newCustomer")
	public ModelAndView processNewCustomer(){
		CustomerNew customer=new CustomerNew(); //form backing object 
		return new ModelAndView("register_customer","customerNewBB",customer);
	}
	
	@RequestMapping("/register")
	public ModelAndView addNewCustomer(@ModelAttribute("customerNewBB") CustomerNew customer){
		String message = manager.addNewCustomer(customer);
		if(message.equals("SUCCESS")){
			return new ModelAndView("statuslog","status","New customer successfully added to our database");
		}else{
			return new ModelAndView("statuslog","status","Unable to add the customer details to our database");
		}	
	}*/

}
