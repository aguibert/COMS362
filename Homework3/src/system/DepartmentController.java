package system;

public interface DepartmentController {
    // Iteration 1
    public Department createDepartment(String name, String address, int phoneNumber);

    public boolean addEqToDept(int eid, int did);

    public boolean rmvEqFromDept(int eid, int did);

    // Iteration 2
    public boolean changeTelephone(int did, int telephone);
}
