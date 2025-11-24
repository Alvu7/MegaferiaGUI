package core.model.stands;

import core.model.editoriales.Publisher;
import java.io.Serializable;
import java.util.ArrayList;

public class Stand implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private double price;
    private ArrayList<Publisher> publishers;

    public Stand(long id, double price) {
        this.id = id;
        this.price = price;
        this.publishers = new ArrayList<>();
    }

    public void addPublisher(Publisher publisher) {
        if (!publishers.contains(publisher)) {
            publishers.add(publisher);
        }
    }

    public long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Publisher> getPublishers() {
        return new ArrayList<>(publishers);
    }

    public int getPublisherQuantity() {
        return publishers.size();
    }
}
