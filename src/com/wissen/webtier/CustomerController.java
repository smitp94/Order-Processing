package com.wissen.webtier;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wissen.businesstier.CustomerManager;
import com.wissen.businesstier.CustomerNew;

@Controller
@RequestMapping(value="/mvc")
public class CustomerController {
	
	@Autowired
	private CustomerManager manager;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/verify")
	public ModelAndView verifyCredentials( @RequestParam String email, @RequestParam String password){
		System.out.println(email+","+password);
		
		Boolean flag=manager.validateLoginCredentials(email, password);
		
		if(flag==true){			
			return new ModelAndView("index");
		}else{
			return new ModelAndView("statuslog","status","Invalid Credentials");
		}
	}
	
	@RequestMapping("/allcustomers")
	public ModelAndView getAllCustomerDetails(){
		System.out.println("In Controller");
		List<CustomerNew> customerList=manager.getAllCustomerDetails();
		if(customerList!=null){			
			return new ModelAndView("show_all_customers_pagewise","customerList",customerList);
		}else{
			return new ModelAndView("statuslog","status","Unable to process the request");		
		}		
	}
	
	@RequestMapping("/domainwise")
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
	
	@RequestMapping(value="/showDomainUsers", method=RequestMethod.GET)
	public @ResponseBody ModelAndView getCustomersByDomain( @RequestParam(value="name") String name){
		System.out.println(name);
		List<CustomerNew>  customerList=manager.getCustomersByDomain(name);
					
		if(customerList!=null){				
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
			/*SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
			String bdate=dateFormat.format(customer.getBirthdate());
			System.out.println(bdate);
			try {
				customer.setBirthdate(dateFormat.parse(bdate));
			} catch (ParseException e) {				
				e.printStackTrace();
			}*/
								
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
	
	
	/*@RequestMapping("/processupdate")
	public ModelAndView saveUpdatedCustomer(@ModelAttribute("customerBB") Customer customer){
		System.out.println("Hi");
		System.out.println(customer.getCustomerId()+","+customer.getBirthdate());
		String message = manager.updateCustomer(customer);
		if(message.equals("SUCCESS")){
			return new ModelAndView("statuslog","status","Customer details updated to our database");
		}else{
			return new ModelAndView("statuslog","status","Unable to update the customer record");
		}	
	}*/
	
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
	}

}
