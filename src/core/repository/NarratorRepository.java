package core.repository;

import core.model.persona.Narrator;
import core.model.utils.SerializationUtils;

import java.util.ArrayList;

public class NarratorRepository {

    private static NarratorRepository instance;
    private final ArrayList<Narrator> narrators;

    private NarratorRepository() {
        this.narrators = new ArrayList<>();
    }

    public static synchronized NarratorRepository getInstance() {
        if (instance == null) {
            instance = new NarratorRepository();
        }
        return instance;
    }

    public synchronized boolean add(Narrator narrator) {
        if (exists(narrator.getId())) {
            return false;
        }

        int i = 0;
        while (i < narrators.size() && narrators.get(i).getId() < narrator.getId()) {
            i++;
        }

        narrators.add(i, narrator);
        return true;
    }

    public synchronized Narrator findById(long id) {
        for (Narrator n : narrators) {
            if (n.getId() == id) {
                return SerializationUtils.deepCopy(n);
            }
        }
        return null;
    }

    public synchronized boolean exists(long id) {
        for (Narrator n : narrators) {
            if (n.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<Narrator> getAllOrderedById() {
        ArrayList<Narrator> result = new ArrayList<>();
        for (Narrator n : narrators) {
            result.add(SerializationUtils.deepCopy(n));
        }
        result.sort((a, b) -> Long.compare(a.getId(), b.getId()));
        return result;
    }

    public synchronized ArrayList<Narrator> getAll() {
        ArrayList<Narrator> result = new ArrayList<>();
        for (Narrator n : narrators) {
            result.add(SerializationUtils.deepCopy(n));
        }
        return result;
    }
}
