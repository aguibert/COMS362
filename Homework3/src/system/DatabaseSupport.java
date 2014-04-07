package system;

import java.util.List;

public interface DatabaseSupport {
    // Iteration 1
    public boolean putEquipment(Equipment e);

    public Equipment getEquipment(int eid);

    public boolean putDepartment(Department d);

    public Department getDepartment(int did);

    // Iteration 2
    public boolean deleteEquipment(int eid);

    public Equipment searchEqByID(int eid);

    public List<Equipment> searchEqByName(String name);

    public List<Equipment> searchEqByDate(String date);
}
