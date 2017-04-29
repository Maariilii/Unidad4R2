package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	

public CustomerWS() {
	conectar();
}
private void conectar(){
	try {
		Class.forName("org.postgresql.Driver");
		connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/wstest","postgres","arevalo");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}catch (SQLException e) {
	e.printStackTrace();
	}
}
public int addCustomer(Customer customer){
	int result=0;
	if(connection!=null){
		try {
			ps=connection.prepareStatement("INSERT INTO customer (company_name,contact_name,contact_type,contact_title,address,city,region,postal_code,country,phone,fax) "
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?);");
			ps.setString(1, customer.getCompanyName());
			ps.setString(2, customer.getContactName());
			ps.setString(3, customer.getContactType());
			ps.setString(4, customer.getContactTitle());
			ps.setString(5, customer.getAddress());
			ps.setString(6, customer.getCity());
			ps.setString(7, customer.getRegion());
			ps.setString(8, customer.getPostalCode());
			ps.setString(9, customer.getCountry());
			ps.setString(10, customer.getPhone());
			ps.setString(11, customer.getFax());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}
//para hacer un update
public int updateCustomer(Customer customer){
	int result=0;
	if(connection!=null){
		try {
			ps=connection.prepareStatement("UPDATE customer SET company_name= ?,contact_name= ?,contact_type= ?,contact_title= ?,address= ?,city= ?,region= ?,postal_code= ?,country= ?,phone= ?, fax= ? WHERE id= ?;");
				
			ps.setString(1, customer.getCompanyName());
			ps.setString(2, customer.getContactName());
			ps.setString(3, customer.getContactType());
			ps.setString(4, customer.getContactTitle());
			ps.setString(5, customer.getAddress());
			ps.setString(6, customer.getCity());
			ps.setString(7, customer.getRegion());
			ps.setString(8, customer.getPostalCode());
			ps.setString(9, customer.getCountry());
			ps.setString(10, customer.getPhone());
			ps.setString(11, customer.getFax());
			ps.setInt(12, customer.getId());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}
//para borrar
public int removeCustomer(int id){
	int result=0;
	if(connection!=null){
		try {
			ps=connection.prepareStatement("DELETE FROM customer WHERE id= ?;");
			ps.setInt(1, id);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}
//lista de course
public Customer[] getCustomer(){
	Customer[] result = null;
	List <Customer> customers=new ArrayList<Customer>();
	if(connection!=null){
		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery("SELECT * FROM customer;");
			while(resultSet.next()){
				Customer customer=new Customer(
						resultSet.getInt("id"),
						resultSet.getString("company_name"),
						resultSet.getString("contact_name"), 
						resultSet.getString("contact_type"), 
						resultSet.getString("contact_title"),
						resultSet.getString("address"),
						resultSet.getString("city"),
						resultSet.getString("region"),
						resultSet.getString("postal_code"),
						resultSet.getString("country"),
						resultSet.getString("phone"),
						resultSet.getString("fax"));
				customers.add(customer);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	result = customers.toArray(new Customer[customers.size()]);
	return result;
}
//
public Customer getCustomerbyId(int id){
	Customer customer=null;
	if(connection!=null){
		try {
			ps=connection.prepareStatement("SELECT * FROM customer WHERE id = ?;");
			ps.setInt(1, id);
			resultSet=ps.executeQuery();
			while(resultSet.next()){
				customer=new Customer(
						resultSet.getInt("id"),
						resultSet.getString("company_name"),
						resultSet.getString("contact_name"), 
						resultSet.getString("contact_type"), 
						resultSet.getString("contact_title"), 
						resultSet.getString("address"),
						resultSet.getString("city"),  
						resultSet.getString("region"), 
						resultSet.getString("postal_code"), 
						resultSet.getString("country"), 
						resultSet.getString("phone"), 
						resultSet.getString("fax"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return customer;
}

}
