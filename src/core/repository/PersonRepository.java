package core.repository;

import core.model.persona.Person;
import core.model.utils.SerializationUtils;

import java.util.ArrayList;

public class PersonRepository {

    private static PersonRepository instance;
    private final ArrayList<Person> persons;

    private PersonRepository() {
        this.persons = new ArrayList<>();
    }

    public static synchronized PersonRepository getInstance() {
        if (instance == null) {
            instance = new PersonRepository();
        }
        return instance;
    }

    public synchronized boolean add(Person person) {
        if (exists(person.getId())) {
            return false;
        }

        int i = 0;
        while (i < persons.size() && persons.get(i).getId() < person.getId()) {
            i++;
        }
        persons.add(i, person);
        return true;
    }

    public synchronized Person findById(long id) {
        for (Person p : persons) {
            if (p.getId() == id) {
                return SerializationUtils.deepCopy(p);
            }
        }
        return null;
    }

    public synchronized boolean exists(long id) {
        for (Person p : persons) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<Person> getAll() {
        ArrayList<Person> result = new ArrayList<>();
        for (Person p : persons) {
            result.add(SerializationUtils.deepCopy(p));
        }
        return result;
    }

    public synchronized ArrayList<Person> getAllOrderedById() {
        ArrayList<Person> result = new ArrayList<>();
        for (Person p : persons) {
            result.add(SerializationUtils.deepCopy(p));
        }
        result.sort((a, b) -> Long.compare(a.getId(), b.getId()));
        return result;
    }

    public synchronized ArrayList<Person> getAllOrderedByName() {
        ArrayList<Person> result = new ArrayList<>();
        for (Person p : persons) {
            result.add(SerializationUtils.deepCopy(p));
        }
        result.sort((a, b) -> a.getFirstName().compareToIgnoreCase(b.getFirstName()));
        return result;
    }
}
