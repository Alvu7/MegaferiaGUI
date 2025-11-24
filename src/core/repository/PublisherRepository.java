package core.repository;

import core.model.editoriales.Publisher;
import core.model.utils.SerializationUtils;

import java.util.ArrayList;

public class PublisherRepository {

    private static PublisherRepository instance;
    private final ArrayList<Publisher> publishers;

    private PublisherRepository() {
        this.publishers = new ArrayList<>();
    }

    public static synchronized PublisherRepository getInstance() {
        if (instance == null) {
            instance = new PublisherRepository();
        }
        return instance;
    }

    public synchronized boolean add(Publisher publisher) {
        if (publisher == null) {
            return false;
        }

        String nit = publisher.getNit().trim();

        if (exists(nit)) {
            return false;
        }

        int i = 0;
        while (i < publishers.size() && publishers.get(i).getNit().compareTo(nit) < 0) {
            i++;
        }

        publishers.add(i, publisher);
        return true;
    }

    public synchronized Publisher findByNit(String nit) {
        if (nit == null) return null;

        String cleanNit = nit.trim();

        for (Publisher p : publishers) {
            if (p.getNit().equalsIgnoreCase(cleanNit)) {
                return SerializationUtils.deepCopy(p);
            }
        }
        return null;
    }

    public synchronized boolean exists(String nit) {
        if (nit == null) return false;

        String cleanNit = nit.trim();

        for (Publisher p : publishers) {
            if (p.getNit().equalsIgnoreCase(cleanNit)) {
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<Publisher> getAll() {
        ArrayList<Publisher> result = new ArrayList<>();
        for (Publisher p : publishers) {
            result.add(SerializationUtils.deepCopy(p));
        }
        return result;
    }

    public synchronized ArrayList<Publisher> getAllOrderedByNit() {
        ArrayList<Publisher> result = new ArrayList<>();
        for (Publisher p : publishers) {
            result.add(SerializationUtils.deepCopy(p));
        }

        result.sort((a, b) -> a.getNit().compareTo(b.getNit()));
        return result;
    }
}
