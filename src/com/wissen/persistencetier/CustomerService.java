package com.wissen.persistencetier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.wissen.businesstier.CustomerNew;
import com.wissen.webtier.CustomerNewMapper;


public class CustomerService implements ICustomer{
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}


	public String addNewCustomer(CustomerNew customer) {
		String sql="insert into customer(customer_name,mobile,email,password,sales_rep,birthdate) "
				+ "values(?,?,?,?,?,?)";
				
		java.sql.Date sdate=new java.sql.Date(customer.getBirthdate().getTime());
		try{
			if(customer.getSalesRep()==0){
				customer.setSalesRep(null);
			}
			int n= jdbcTemplate.update(sql, new Object[]{customer.getCustomerName(),customer.getMobile(),customer.getEmail(),customer.getPassword(),customer.getSalesRep(),sdate});
			if(n>0){
				return "SUCCESS";
			}else{
				throw new Exception("Unable to insert customer record");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "FAIL";
		/*PreparedStatement preparedStatement=null;
		DataSource dataSource=null;
		Connection connection=null;			
		Context context=null;
		Context envContext=null;
		
		try{
			// Obtain our environment naming context
			context = new InitialContext();
			envContext = (Context) context.lookup("java:comp/env");
			// Look up our data source
			dataSource = (DataSource) envContext.lookup("jdbc/orderprocessDB");
			connection=dataSource.getConnection();
			
			preparedStatement=connection.prepareStatement(sql);
			populatePreparedStatement(customer,preparedStatement);			
			int rowCount=preparedStatement.executeUpdate();
			if(rowCount>0){
				return "SUCCESS: Customer added to database";
			}else{
				return "FAIL: Unable to add customer to database";
			}		
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
		catch(Exception e){			
				Logger logger=Logger.getLogger(this.getClass());
				logger.error(e.getMessage(),e);
				e.printStackTrace();
			}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return null;*/	
		
	}


	/*private void populatePreparedStatement(Customer customer, PreparedStatement preparedStatement) throws SQLException{
		preparedStatement.setString(1,customer.getCustomerName() );
		//convert java.util.Calander to java.sql.Date
		
		//1. convert java.util.Calander to java.util.Date
		java.util.Date mdate= customer.getBirthdate().getTime();
		//2. convert java.util.Date to java.sql.Date
		java.sql.Date sdate=new java.sql.Date(mdate.getTime());
		preparedStatement.setDate(2, sdate);
		
		if(customer.getMobile()!=null){
			preparedStatement.setLong(3, customer.getMobile());
		}else{
			preparedStatement.setNull(3, Types.NULL);
		}		
		
		preparedStatement.setString(4, customer.getEmail());		
		preparedStatement.setString(5, customer.getPassword());
		
		if(customer.getSalesRep()!=null){
			preparedStatement.setLong(6, customer.getSalesRep());
		}else{
			preparedStatement.setNull(6,Types.NULL);
		}
	}*/

	public List<CustomerNew> getAllCustomerDetails() {
		String sql="select * from customer";
		
		try{
			List<CustomerNew> customerList=jdbcTemplate.query(sql, new CustomerNewMapper());
			if(customerList.size()>0){
				return customerList;
			}else{
				throw new Exception("No customers in the database");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
		/*DataSource dataSource=null;
		Connection connection=null;			
		Context context=null;
		Context envContext=null;
		ResultSet resultSet=null;
		Statement statement=null;
		
		try{			
			context = new InitialContext();
			envContext = (Context) context.lookup("java:comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/orderprocessDB");
			connection=dataSource.getConnection();
			
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			if(resultSet !=null){				
				List<Customer> customerList=new ArrayList<>();
				populateCustomerList(customerList,resultSet);
				return customerList;
			}else{
				throw new Exception("ResultSet is null");
			}
			
			
		}
		catch(SQLException e){
			Logger logger=Logger.getLogger(this.getClass());
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		catch(Exception e){
			Logger logger=Logger.getLogger(this.getClass());
			logger.error(e.getMessage(),e);
			e.printStackTrace();		
			}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return null;*/
	}

	/*private void populateCustomerList(List<Customer> customerList, ResultSet resultSet) throws Exception {
		while(resultSet.next()){
			Customer customer=new Customer();
			populateCustomer(customer,resultSet);
			customerList.add(customer);
		}
		
	}*/
	
	

	public CustomerNew getCustomerById(Long ID) {
		String sql="select * from customer where customer_id=?";
		try{
			CustomerNew customer=jdbcTemplate.queryForObject(sql, new Object[]{ID}, new CustomerNewMapper());
			if(customer!=null){
				return customer;
			}else{
				throw new Exception("Invalid Customer ID");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
		/*DataSource dataSource=null;
		Connection connection=null;			
		Context context=null;
		Context envContext=null;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
		
		try{
			context = new InitialContext();
			envContext = (Context) context.lookup("java:comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/orderprocessDB");
			connection=dataSource.getConnection();
			
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setLong(1, ID);
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()){
				Customer customer=new Customer();
				populateCustomer(customer,resultSet);
				return customer;
			}else{
				throw new Exception("Service.INVALID_ID");
			}
		
		}
		catch(SQLException e){			
			e.printStackTrace();
		}
		catch(Exception e){
			Logger logger=Logger.getLogger(this.getClass());
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return null;*/
	}

	/*private void populateCustomer(Customer customer, ResultSet resultSet) throws Exception{
		customer.setCustomerId(resultSet.getLong("customer_id"));
		customer.setCustomerName(resultSet.getString("customer_name"));
		customer.setMobile(resultSet.getLong("mobile"));
		customer.setEmail(resultSet.getString("email"));
		customer.setPassword(resultSet.getString("password"));
		Long sid=resultSet.getLong("sales_rep");
		//System.out.println(sid);
		if( sid!=0){
			customer.setSalesRep(sid);			
		}else{
			customer.setSalesRep(null);			
		}
		
		//convert java.sql.Date to java.util.Calendar
		//1. convert java.sql.Date to java.util.Dat
		java.util.Date udate=new java.util.Date(resultSet.getDate("birthdate").getTime());
		//2. convert java.util.Date to java.util.Calendar
		java.util.Calendar cdate=Calendar.getInstance();
		cdate.setTime(udate);
		customer.setBirthdate(cdate);	
		
	}*/

	public Boolean findCustomer(Long ID) {
		String sql="select * from customer where customer_id=?";
		try{
			CustomerNew customer=jdbcTemplate.queryForObject(sql, new Object[]{ID}, new CustomerNewMapper());
			if(customer!=null){
				return true;
			}else{
				throw new Exception("Invalid Customer ID");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
		/*DataSource dataSource=null;
		Connection connection=null;			
		Context context=null;
		Context envContext=null;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
		
		try{
			context = new InitialContext();
			envContext = (Context) context.lookup("java:comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/orderprocessDB");
			connection=dataSource.getConnection();
			
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setLong(1, ID);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}else{
				return false;
			}
		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return false;*/
	}

	public String deleteCustomer(Long ID) {
		//String sql="{call delete_customer(?)}";
		
		try{
			System.out.println("In delete method of service class");
			SimpleJdbcCall jdbcCall=new SimpleJdbcCall(dataSource).withProcedureName("delete_customer");
			SqlParameterSource in = new MapSqlParameterSource().addValue("p_id", ID);
			Map<String, Object> out = jdbcCall.execute(in);
			return "SUCCESS";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "FAIL";
		/*DataSource dataSource=null;
		Connection connection=null;			
		Context context=null;
		Context envContext=null;
		ResultSet resultSet=null;
		CallableStatement callableStatement=null;
		
		try{
			context = new InitialContext();
			envContext = (Context) context.lookup("java:comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/orderprocessDB");
			connection=dataSource.getConnection();
			
			callableStatement=connection.prepareCall(sql);
			callableStatement.setLong(1, ID);
			int status=callableStatement.executeUpdate();
			if(status>0){
				return "SUCCESS";
			}else{
				throw new Exception("Service.INVALID_ID");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(Exception e){
			Logger logger=Logger.getLogger(this.getClass());
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		return "FAILED";*/
	}


	@Override
	public Boolean validateLoginCredentials(String email, String password) {
		String sql="select * from customer where email=? AND password=?";
		
		try{
			CustomerNew customer=jdbcTemplate.queryForObject(sql, new Object[]{email,password}, new CustomerNewMapper());
			if(customer !=null){
				return true;
			}else{
				throw new Exception("Invalid Credentials");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;		
	}


	/*@Override
	public String updateCustomer(Customer customer) {
		String sql="update customer set customer_name=:customerName,mobile=:mobile,email=:email,"
				+ "password=:password,sales_rep=:salesRep,birthdate=:birthdate where customer_id=:customerId";
		BeanPropertySqlParameterSource source=new BeanPropertySqlParameterSource(customer);
		try{
			int n= jdbcTemplate.update(sql, source);
			if(n>0){
				return "SUCCESS";
			}else{
				throw new Exception("Invalid Customer ID");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "FAIL";
	}*/


	@Override
	public CustomerNew getCustomerNewById(Long ID) {
		String sql="select * from customer where customer_id=?";
		try{
			CustomerNew customer=jdbcTemplate.queryForObject(sql, new Object[]{ID}, new CustomerNewMapper());
			if(customer!=null){
				return customer;
			}else{
				throw new Exception("Invalid Customer ID");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String updateCustomerNew(CustomerNew customer) {
		String sql="update customer set customer_name=?,mobile=?,email=?,"
				+ "password=?,sales_rep=?,birthdate=? where customer_id=?";
		
		java.sql.Date sdate=new java.sql.Date(customer.getBirthdate().getTime());
		try{
			if(customer.getSalesRep()==0){
				customer.setSalesRep(null);
			}
			int n= jdbcTemplate.update(sql, new Object[]{customer.getCustomerName(),customer.getMobile(),customer.getEmail(),customer.getPassword(),customer.getSalesRep(),sdate,customer.getCustomerId()});
			if(n>0){
				return "SUCCESS";
			}else{
				throw new Exception("Invalid Customer ID");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "FAIL";
	}


	@Override
	public List<CustomerNew> getCustomersByDomain(String domainName) {
		String sql="select * from customer";
				
		try{
			List<CustomerNew> customerList=jdbcTemplate.query(sql, new CustomerNewMapper());
			if(customerList.size()>0){
				List<CustomerNew> newCustomerList=new ArrayList<>();
				populateCustomers(customerList,newCustomerList,domainName);
				return newCustomerList;
			}else{
				throw new Exception("No customers with the domain in the database");
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return null;				
	}


	private void populateCustomers(List<CustomerNew> customerList, List<CustomerNew> newCustomerList,String domainName) {
		Iterator<CustomerNew> iterator=customerList.iterator();
		while(iterator.hasNext()){
			/*String string1=iterator.next().getEmail();
			String string2=string1.substring(string1.indexOf("@")+1);
			String string3=string2.substring(0, string2.indexOf("."));*/
			CustomerNew customerNew=iterator.next();
			if(customerNew.getEmail().contains(domainName)){
				newCustomerList.add(customerNew);
			}
		}
		
	}

}
