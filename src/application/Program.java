package application;

import java.util.Date;
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
		
		//System.out.println("\nTEST 4: Seller Insert");
		//Seller newSeller = new Seller("Greg Brown", null, "greg@gmail.com", new Date(), 4000.0, department);
		//sellerDao.insert(newSeller);
		//System.out.println("Seller inserted! New id: " + newSeller.getId());
		
		System.out.println("\nTEST 5: Seller Update");
		seller = sellerDao.findById(1);
		seller.setName("Anna White");
		seller.setEmail("anna@gmail.com");
		sellerDao.update(seller);
		System.out.println("Seller update completed!");
		
		System.out.println("\nTEST 6: Seller Delete");
		sellerDao.deleteById(6);
		System.out.println("Seller deleted successfully!");

	}

}
