package system;

import java.util.List;

public interface EquipmentController {
    // Iteration 1
    Equipment createEquipment(String name, String type, String description, int cost, int value, String date);

    public boolean addDeptToEq(int eid, int did);

    public boolean rmvDeptFromEq(int eid, int did);

    // Iteration 2
    public boolean updateValue(int eid, int value);

    public boolean removeEquipment(int eid);

    public Equipment searchEqByID(int eid);

    public List<Equipment> searchEqByName(String name);

    public List<Equipment> searchEqByDate(String date);
}
