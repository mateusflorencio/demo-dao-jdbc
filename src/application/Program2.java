package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner scanner= new Scanner(System.in);
		List<Department>list=new ArrayList<>();

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Department department=new Department();
		
		
		System.out.println("=== TESTE 1: department findById ===");
		department=departmentDao.findById(3);
		System.out.println(department);
		
		
		System.out.println("\n=== TESTE 2: seller findAll ===");
		list=departmentDao.findAll();
		for (Department department2 : list) {
			System.out.println(department2);
		}
		
		System.out.println("\n=== TESTE 3: seller insert ===");
		department=new Department(8, "sfda");
		departmentDao.insert(department);
		System.out.println("inserted! New Id= " + department.getId());
		
		
		System.out.println("\n=== TESTE 4: department update ===");
		department=departmentDao.findById(1);
		department.setName("Computer");
		departmentDao.update(department);
		System.out.println("\n Finished! Update done! ");
		
	
		System.out.println("\n=== TESTE 5: department delete ===");
		System.out.println("Enter id for delete");
		int id=scanner.nextInt();
		departmentDao.delete(id);
		System.out.println("Delete complete");
		
		scanner.close();
		

		
		
		}
}
