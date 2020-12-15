package jpa;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EmployeeDaoTest {

    private EmployeeDao employeeDao;

    @Before
    public void init() {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("pu");
        employeeDao = new EmployeeDao(factory);
    }

    @Test
    public void testCreateAndList() {
        employeeDao.saveEmployee(new Employee("John Doe"));
        employeeDao.saveEmployee(new Employee("Jane Doe"));

        List<Employee> employees = employeeDao.listEmployees();
        List<String> names = employees.stream().map(Employee::getName).collect(Collectors.toList());
        assertEquals(Arrays.asList("Jane Doe", "John Doe"), names);
    }

    @Test
    public void testUpdateName() {
        Employee employee = new Employee("John Doe");
        employeeDao.saveEmployee(employee);

        employeeDao.updateEmployeeName(employee.getId(), "Jack Doe");

        Employee modifiedEmployee = employeeDao.findEmployeeById(employee.getId());

        assertEquals("Jack Doe", modifiedEmployee.getName());
    }

    @Test
    public void testDelete() {
        Employee employee = new Employee("John Doe");
        employeeDao.saveEmployee(employee);
        employeeDao.saveEmployee(new Employee("Jane Doe"));

        employeeDao.deleteEmployee(employee.getId());

        List<Employee> employees = employeeDao.listEmployees();
        List<String> names = employees.stream().map(Employee::getName).collect(Collectors.toList());
        assertEquals(Arrays.asList("Jane Doe"), names);
    }

    @Test
    public void testIllegalId() {
        Employee employee = employeeDao.findEmployeeById(12L);
        assertEquals(null, employee);
    }

    @Test
    public void testEmployeeWithAttributes() {
        for (int i = 0; i < 10; i++) {
            employeeDao.saveEmployee(new Employee("John Doe", Employee.EmployeeType.HALF_TIME,
                    LocalDate.of(2000, 1, 1)));
        }
        Employee employee = employeeDao.listEmployees().get(0);
        assertEquals(LocalDate.of(2000, 1, 1), employee.getDateOfBirth());
    }

    @Test
    public void testSaveEmployeeChangeState() {
        Employee employee = new Employee("John Doe");
        employeeDao.saveEmployee(employee);

        employee.setName("Jack Doe");
        Employee modifiedEmployee = employeeDao.findEmployeeById(employee.getId());

        assertEquals("John Doe", modifiedEmployee.getName());
        assertFalse(employee == modifiedEmployee);
    }

    @Test
    public void testMerge() {
        Employee employee = new Employee("John Doe");
        employeeDao.saveEmployee(employee);

        employee.setName("Jack Doe");
        employeeDao.updateEmployee(employee);

        Employee modifiedEmployee = employeeDao.findEmployeeById(employee.getId());
        assertEquals("Jack Doe", modifiedEmployee.getName());
    }

    @Test
    public void testFlush() {
        for (int i = 0; i < 10; i++) {
            employeeDao.saveEmployee(new Employee("John Doe" + i));
        }

        employeeDao.updateEmployeeNames();
    }

}
