package cn.techtutorial.dao;

import java.sql.*;
import java.util.*;

import cn.techtutorial.model.Cart;
import cn.techtutorial.model.Product;
public class ProductDao {

	
	
	private Connection con;

	private String query;
    private PreparedStatement pst;
    private ResultSet rs;
    

	public ProductDao(Connection con) {
		super();
		this.con = con;
	}
	
	
	public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {

            query = "select * from products";
            pst = this.con.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
            	Product row = new Product();
                row.setId(rs.getInt("id"));
                row.setName(rs.getString("name"));
                row.setCategory(rs.getString("category"));
                row.setPrice(rs.getDouble("price"));
                row.setImage(rs.getString("image"));

                products.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return products;
    }
	
	
	  public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
	        List<Cart> product = new ArrayList<>();
	        try {
	            if (cartList.size() > 0) {
	                for (Cart item : cartList) {
	                    query = "select * from products where id=?";
	                    pst = this.con.prepareStatement(query);
	                    pst.setInt(1, item.getId());
	                    rs = pst.executeQuery();
	                    while (rs.next()) {
	                        Cart row = new Cart();
	                        row.setId(rs.getInt("id"));
	                        row.setName(rs.getString("name"));
	                        row.setCategory(rs.getString("category"));
	                        row.setPrice(rs.getDouble("price")*item.getQuantity());
	                        row.setQuantity(item.getQuantity());
	                        product.add(row);
	                    }

	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }
	        return product;
	    }
	  
	  public double getTotalCartPrice(ArrayList<Cart> cartList) {
	        double sum = 0;
	        try {
	            if (cartList.size() > 0) {
	                for (Cart item : cartList) {
	                    query = "select price from products where id=?";
	                    pst = this.con.prepareStatement(query);
	                    pst.setInt(1, item.getId());
	                    rs = pst.executeQuery();
	                    while (rs.next()) {
	                        sum+=rs.getDouble("price")*item.getQuantity();
	                    }

	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }
	        return sum;
	    }

	  public Product getSingleProduct(int id) {
			 Product row = null;
		        try {
		            query = "select * from products where id=? ";

		            pst = this.con.prepareStatement(query);
		            pst.setInt(1, id);
		            ResultSet rs = pst.executeQuery();

		            while (rs.next()) {
		            	row = new Product();
		                row.setId(rs.getInt("id"));
		                row.setName(rs.getString("name"));
		                row.setCategory(rs.getString("category"));
		                row.setPrice(rs.getDouble("price"));
		                row.setImage(rs.getString("image"));
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            System.out.println(e.getMessage());
		        }

		        return row;
		    }
	
}
