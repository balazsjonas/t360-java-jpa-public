package bjonas.jpaee;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("employees")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class EmployeeResource {
    @Inject
    private EmployeeDao employeeDao;

    @POST
    public void saveEmployee(Employee employee) {
        employeeDao.saveEmployee(employee);
        System.err.println("HURRÁ: "+ employee.getName()+" added");
    }

    @GET
    public List<Employee> listEmployees() {
        return employeeDao.listEmployees();
    }
}
