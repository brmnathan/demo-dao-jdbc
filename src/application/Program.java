package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department dp = new Department("Books", 1);
		Seller seller = new Seller("John", 1, "john@gmail.com", new Date(), 2000.0, dp);
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println(dp);
		System.out.println(seller);

	}

}
