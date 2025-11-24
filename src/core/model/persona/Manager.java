package core.model.persona;

import core.model.editoriales.Publisher;

import java.io.Serializable;

public class Manager extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private Publisher publisher;

    public Manager(long id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
