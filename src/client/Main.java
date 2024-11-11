package client;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entity.Department;
import entity.Employee;

public class Main {

	public static void main(String[] args) {

		Configuration cfg = new Configuration().configure().addAnnotatedClass(Employee.class)
				.addAnnotatedClass(Department.class);
		SessionFactory sessionFactory = cfg.buildSessionFactory();
		Session session = sessionFactory.openSession();

		try {

			// Create Employees
			// Employee emp1 = new Employee("Rohit Singh Thakur", 98.500);
			// Employee emp2 = new Employee("Pavan Kumar", 45.500);
			// Employee emp3 = new Employee("Mangesh Thakre", 68.500);

			// Create Department
			// Department HR = new Department("HR");
			// Department IT = new Department("IT");

			// Set Employee to Department
			// HR.setEmployees(Arrays.asList(emp3));
			// IT.setEmployees(Arrays.asList(emp1, emp2));

			// Save Department
			// session.save(IT);
			// session.save(HR);

			// Set Department To Employee
			// emp1.setDepartment(IT);
			// emp2.setDepartment(IT);
			// emp3.setDepartment(HR);

			// No need to save employees Dep Object will save employee by cascading
			// session.save(emp1);
			// session.save(emp2);
			// session.save(emp3);

			// =========================================================================================

			// HQL OPERATIONS
			// READ

			// List list = session.createQuery("FROM Employee").list();
			// for (Object object : list) {
			// System.out.println(object);
			// }

			// Update
			// Query query = session.createQuery("update Employee set empName = 'Sachin'
			// where empId = 2");
			// query.executeUpdate();

			// Delete
			// Query query = session.createQuery("delete Employee where empId = 2");
			// query.executeUpdate();

			// Aggregation Example

			// get Count of Employees
			// Query query = session.createQuery("SELECT COUNT(e) FROM Employee e");
			// Object result = query.uniqueResult();
			// System.out.println("Total employees : " + result);

			// JOIN :fetch employee names along with their department names
			// List<Object[]> list = session.createQuery("SELECT e.empName, d.depName FROM
			// Employee e JOIN e.department d").list();
			// for (Object[] obj : list) {
			// System.out.println(obj[0]);
			// System.out.println(obj[1]);
			// }

			// Grouping example: count employees in each department
			// List<Object[]> list = session.createQuery("SELECT d.depName, COUNT(e) FROM
			// Employee e JOIN e.department d GROUP BY d.depName").list();
			// for (Object[] objects : list) {
			// System.out.println(objects[0]);
			// System.out.println(objects[1]);
			// }

			// Pagination example: get a paginated list of employees

			// Set the page number and page size
			int pageNumber = 1; // First page
			int pageSize = 5; // Number of records per page

			Query query = session.createQuery("from Employee");
			query.setFirstResult((pageNumber - 1) * pageSize); // Set starting point for the page
			/*
			 * Here pageNumber - 1: Subtracting 1 from the pageNumber adjusts the starting
			 * point because page numbers are generally counted starting from 1 (e.g., Page
			 * 1, Page 2), but indexing starts from 0.Multiplying by pageSize then
			 * calculates the exact offset in the list of records.
			 * 
			 * Page 1 (pageNumber = 1): ((1 - 1) * pageSize) = 0, so it starts at index 0.
			 * Page 2 (pageNumber = 2): ((2 - 1) * pageSize) = pageSize, so it starts at
			 * index equal to pageSize (e.g., index 5 if pageSize is 5). Page 3 (pageNumber
			 * = 3): ((3 - 1) * pageSize) = 2 * pageSize, so it starts at index 10 if
			 * pageSize is 5.
			 * 
			 */
			query.setMaxResults(pageSize);// Set max results per page
			// Execute query and get the list of employees
			List<Employee> employees = query.list();

			// Print the results for the current page
			System.out.println("Page Number: " + pageNumber);
			for (Employee employee : employees) {
				System.out.println("Employee ID: " + employee.getEmpId() + ", Name: " + employee.getEmpName()
						+ ", Salary: " + employee.getSalary());
			}

			session.beginTransaction().commit();
			session.close();
			sessionFactory.close();

		} catch (

		Exception e) {
			e.printStackTrace();
			session.close();
			sessionFactory.close();
		} finally {
			session.close();
			sessionFactory.close();
		}

	}
}
