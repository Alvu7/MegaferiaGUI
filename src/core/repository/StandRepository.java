package core.repository;

import core.model.stands.Stand;
import core.model.utils.SerializationUtils;

import java.util.ArrayList;

public class StandRepository {

    private static StandRepository instance;
    private final ArrayList<Stand> stands;

    private StandRepository() {
        this.stands = new ArrayList<>();
    }

    public static synchronized StandRepository getInstance() {
        if (instance == null) {
            instance = new StandRepository();
        }
        return instance;
    }

    public synchronized boolean add(Stand stand) {
        if (exists(stand.getId())) {
            return false;
        }

        int i = 0;
        while (i < stands.size() && stands.get(i).getId() < stand.getId()) {
            i++;
        }

        stands.add(i, stand);
        return true;
    }

    public synchronized Stand findById(long id) {
        for (Stand s : stands) {
            if (s.getId() == id) {
                return SerializationUtils.deepCopy(s);
            }
        }
        return null;
    }

    public synchronized boolean exists(long id) {
        for (Stand s : stands) {
            if (s.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<Stand> getAll() {
        ArrayList<Stand> result = new ArrayList<>();
        for (Stand s : stands) {
            result.add(SerializationUtils.deepCopy(s));
        }
        return result;
    }

    public synchronized ArrayList<Stand> getAllOrderedById() {
        ArrayList<Stand> result = new ArrayList<>();
        for (Stand s : stands) {
            result.add(SerializationUtils.deepCopy(s));
        }
        result.sort((a, b) -> Long.compare(a.getId(), b.getId()));
        return result;
    }
}
