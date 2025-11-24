package core.controller;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.editoriales.Publisher;
import core.model.stands.Stand;
import core.repository.PublisherRepository;
import core.repository.StandRepository;

import java.util.ArrayList;
import java.util.List;

public class StandController {

    private final StandRepository standRepository;
    private final PublisherRepository publisherRepository;

    public StandController() {
        this.standRepository = StandRepository.getInstance();
        this.publisherRepository = PublisherRepository.getInstance();
    }

    public Response addStand(long id, double price) {

        if (id < 0 || String.valueOf(id).length() > 15) {
            return new Response("ID de stand inválido", StatusCode.BAD_REQUEST);
        }

        if (price <= 0) {
            return new Response("El precio del stand debe ser mayor que 0", StatusCode.BAD_REQUEST);
        }

        if (standRepository.exists(id)) {
            return new Response("Ya existe un stand con ese ID", StatusCode.BAD_REQUEST);
        }

        Stand stand = new Stand(id, price);
        standRepository.add(stand);

        Stand copy = standRepository.findById(id);

        return new Response("Stand creado correctamente", StatusCode.CREATED, copy);
    }

    public Response buyStand(long standId, List<String> publishersNit) {

        Stand stand = standRepository.findById(standId);

        if (stand == null) {
            return new Response("Stand no encontrado", StatusCode.NOT_FOUND);
        }

        if (publishersNit == null || publishersNit.isEmpty()) {
            return new Response("Debe seleccionar al menos una editorial", StatusCode.BAD_REQUEST);
        }

        ArrayList<Publisher> publishers = new ArrayList<>();

        for (String nit : publishersNit) {

            Publisher publisher = publisherRepository.findByNit(nit);

            if (publisher == null) {
                return new Response("Editorial no encontrada: " + nit, StatusCode.NOT_FOUND);
            }

            if (publishers.contains(publisher)) {
                return new Response("No se permiten editoriales repetidas", StatusCode.BAD_REQUEST);
            }

            publishers.add(publisher);
        }

        for (Publisher publisher : publishers) {
            stand.addPublisher(publisher);
            publisher.addStand(stand);
        }

        Stand copy = standRepository.findById(standId);

        return new Response("Compra de stand realizada con éxito", StatusCode.OK, copy);
    }

    public Response getAllStands() {

        ArrayList<Stand> stands = standRepository.getAllOrderedById();

        return new Response("Lista de stands obtenida correctamente", StatusCode.OK, stands);
    }

    public Response getStandById(long id) {

        Stand stand = standRepository.findById(id);

        if (stand == null) {
            return new Response("Stand no encontrado", StatusCode.NOT_FOUND);
        }

        return new Response("Stand encontrado", StatusCode.OK, stand);
    }
}
