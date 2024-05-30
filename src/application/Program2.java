package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("TEST 1: Department findById");
		Department department = departmentDao.findById(2);
		System.out.println(department);
		
		System.out.println("\nTEST 2: Department findAll");
		List<Department> departmentList = departmentDao.findAll();
		departmentList.forEach(System.out::println);
		
		System.out.println("\nTEST 3: Department Insert");
		//Department newDepartment = new Department("Food", null);
		///departmentDao.insert(newDepartment);
		//System.out.println("Seller inserted! New id: " + newDepartment.getId());
		
		System.out.println("\nTEST 4: Department Update");
		department = departmentDao.findById(1);
		department.setName("Science");
		departmentDao.update(department);
		System.out.println("Department update completed!");
		
		System.out.println("\nTEST 5: Department Delete");
		//departmentDao.deleteById(5);
		//System.out.println("Department deleted successfully!");
	}

}
