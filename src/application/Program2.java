package application;

import java.util.Date;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner scanner= new Scanner(System.in);

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		/**
		System.out.println("=== TESTE 1: seller findById ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		System.out.println("\n=== TESTE 2: seller findByDepartment ===");
		Department department=new Department(2,null);
		List<Seller> list=sellerDao.findByDepartment(department);
		for (Seller seller2 : list) {
			System.out.println(seller2);
		}
		
		System.out.println("\n=== TESTE 3: seller findAll ===");
		list=sellerDao.findAll();
		for (Seller seller2 : list) {
			System.out.println(seller2);
		}
		**/
		System.out.println("\n=== TESTE 4: seller insert ===");
		Department department=new Department(7, "marketing");
		departmentDao.insert(department);
	
		System.out.println("inserted! New Id= " + department.getId());
		
		/**
		System.out.println("\n=== TESTE 5: seller update ===");
		seller=sellerDao.findById(1);
		seller.setName("Mateus Florêncio");
		sellerDao.update(seller);
		System.out.println("\n Finished! Update done! ");
		
		
		System.out.println("\n=== TESTE 6: seller delete ===");
		System.out.println("Enter id for delete");
		int id=scanner.nextInt();
		sellerDao.delete(id);
		System.out.println("Delete complete");
		
		scanner.close();
		**/

		
		
		}
}
