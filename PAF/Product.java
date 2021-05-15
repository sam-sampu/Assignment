package model;

import java.sql.*; 

public class Product {
	
	// DB connection
	private Connection connect()
	{
			Connection con = null;
			
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");

				//server , database, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_product_db?serverTimezone=Australia/Sydney", "root", "");
			}
			catch (Exception e)
			{e.printStackTrace();}

			return con;
	}
	
	public String getProducts()
	 {
			String output = "";
			
			try
			{
				Connection con = connect();
	 
				if (con == null)
				{return "Error while connecting to database for reading...."; }
				
				// html table to be displayed
				output = "<table class='table table-bordered table-hover' ><tr><th>Project Code</th><th>Product Name</th>" +
						"<th>Description</th>" +
						"<th>Product Type</th>" +
						"<th>Amount</th>"+
						"<th>Update</th><th>Remove</th></tr>";

				String query = "select * from product";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
	 
				// iterate through the rows in the result set
				while (rs.next())
				{
					String prodID = Integer.toString(rs.getInt("ID"));
					String prodName = rs.getString("Name");
					String prodDes = rs.getString("Description");
					String prodType = rs.getString("Type");
					String prodAmount = Double.toString(rs.getDouble("Amount"));
	 
					// Add into the html table
					output += "<tr><td><input id='hidprodNOUpdate' name='hidprodNOUpdate' type='hidden' value='" + prodID
							+ "'>" + prodID + "</td>";
					output += "<td>" + prodName + "</td>";
					output += "<td class='desc-view'>" + prodDes + "</td>";
					output += "<td>" + prodType + "</td>";
					output += "<td>" + prodAmount + "</td>";
					
					// buttons
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-warning'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-productno='"
							+ prodID + "'>" + "</td></tr>";
					
				}
	 
				con.close();
				
				output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while reading the items.";
				System.err.println(e.getMessage());
			}
	 
			return output;
	 }
	
	public String getProductByID(String ID)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
 
			if (con == null)
			{return "Error while connecting to database for reading...."; }

			String query = "select * from product where ID =" + ID;
			
			// execute the statement
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
            while(rs.next())
            {
				String id = Integer.toString(rs.getInt("ID"));
				String name = rs.getString("Name");
				String des = rs.getString("Description");
				String type = rs.getString("Type");
				String amount = Double.toString(rs.getDouble("Amount"));
				
				output += "ID  : " + id + "\n";
				output	+= "Name  : " + name + "\n";
				output += "Description  : " + des + "\n";
				output += "Type  : " + type + "\n";
				output += "Amount : " + amount + "\n";
            }
           
		}
		catch (Exception e)
		{
			output = "Error while read an item.";
			System.err.println(e.getMessage());
		}
 
		return output;
	}
	
	public String addProduct(String name, String des ,String type,  String amount)
	{
			String output = "";
		
			try 
			{
					Connection con = connect();
					
					if (con == null) {
						return "Error while connecting to the database for inserting...";
					}
			
			
			// create a prepared statement
		    String query = " insert into product(`Name`,`Description`,`Type`,`Amount`)"
									+ " values (?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, des);
			preparedStmt.setString(3, type);
			preparedStmt.setDouble(4, Double.parseDouble(amount));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newProducts = getProducts();
			output = "{\"status\":\"success\", \"data\": \"" + newProducts + "\"}";
			
			}
			
			catch (Exception e) 
			{
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the product.\"}";
				System.err.println(e.getMessage());
			}
			return output;
		
	}
	
	public String updateProduct(String ID, String name,String des , String type, String amount)
	{
			String output = "";
		
			try 
			{
				Connection con = connect();
				if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			
				// create a prepared statement
				String query = "UPDATE product SET Name=?,Description=?,Type=?,Amount=? WHERE ID=?";
			
				PreparedStatement preparedStmt = con.prepareStatement(query);
			
				// binding values
				preparedStmt.setString(1, name);
				preparedStmt.setString(2, des);
				preparedStmt.setString(3, type);
				preparedStmt.setDouble(4, Double.parseDouble(amount));
				preparedStmt.setInt(5, Integer.parseInt(ID));
			
				// execute the statement
				preparedStmt.execute();
				con.close();
			
				String newProducts = getProducts();
				
				output = "{\"status\":\"success\", \"data\": \"" + newProducts + "\"}";
		} 
			catch (Exception e) 
			{
				output = "{\"status\":\"error\", \"data\": \"Error while updating the product.\"}";
				System.err.println(e.getMessage());
			}
		
			return output;
	}
	
	public String deleteProduct(String prodID)

	{
			String output = "";
		
			try 
			{
				Connection con = connect();
				if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
				// create a prepared statement
				String query = "DELETE FROM product WHERE ID=?";
			
				PreparedStatement preparedStmt = con.prepareStatement(query);
			
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(prodID));
			
				// execute the statement
				preparedStmt.execute();
				con.close();
			
				String newProducts = getProducts();
				
				System.out.print(preparedStmt);
				output = "{\"status\":\"success\", \"data\": \"" + newProducts + "\"}";
		} 
			catch (Exception e) 
			{
				output = "{\"status\":\"error\", \"data\": \"Error while deleting the product.\"}";
				System.err.println(e.getMessage());
			}
		
			return output;
	}

}
