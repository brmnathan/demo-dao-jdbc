package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("TEST 1: Seller findById");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\nTEST 2: Seller findByDepartment");
		Department department = new Department("Electronics", 2);
		List<Seller> sellerList01 = sellerDao.findByDepartment(department);
		sellerList01.forEach(System.out::println);
		
		System.out.println("\nTEST 3: Seller findAll");
		List<Seller> sellerList02 = sellerDao.findAll();
		sellerList02.forEach(System.out::println);

	}

}
