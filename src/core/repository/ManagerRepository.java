package core.repository;

import core.model.persona.Manager;
import core.model.utils.SerializationUtils;

import java.util.ArrayList;

public class ManagerRepository {

    private static ManagerRepository instance;
    private final ArrayList<Manager> managers;

    private ManagerRepository() {
        this.managers = new ArrayList<>();
    }

    public static synchronized ManagerRepository getInstance() {
        if (instance == null) {
            instance = new ManagerRepository();
        }
        return instance;
    }

    public synchronized boolean add(Manager manager) {
        if (exists(manager.getId())) {
            return false;
        }

        int i = 0;
        while (i < managers.size() && managers.get(i).getId() < manager.getId()) {
            i++;
        }
        managers.add(i, manager);
        return true;
    }

    public synchronized Manager findById(long id) {
        for (Manager m : managers) {
            if (m.getId() == id) {
                return SerializationUtils.deepCopy(m);
            }
        }
        return null;
    }

    public synchronized boolean exists(long id) {
        for (Manager m : managers) {
            if (m.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<Manager> getAllOrderedById() {
        ArrayList<Manager> result = new ArrayList<>();
        for (Manager m : managers) {
            result.add(SerializationUtils.deepCopy(m));
        }
        return result;
    }

    public synchronized ArrayList<Manager> getAll() {
        ArrayList<Manager> result = new ArrayList<>();
        for (Manager m : managers) {
            result.add(SerializationUtils.deepCopy(m));
        }
        return result;
    }
}
