package core.model.persona;

import java.io.Serializable;

public abstract class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    protected final long id;
    protected String firstName;
    protected String lastName;

    public Person(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
