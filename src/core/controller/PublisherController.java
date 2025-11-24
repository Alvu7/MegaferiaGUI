package core.controller;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.editoriales.Publisher;
import core.model.persona.Manager;
import core.model.utils.SerializationUtils;
import core.repository.ManagerRepository;
import core.repository.PublisherRepository;

import java.util.ArrayList;

public class PublisherController {

    private final PublisherRepository publisherRepository;
    private final ManagerRepository managerRepository;

    public PublisherController() {
        this.publisherRepository = PublisherRepository.getInstance();
        this.managerRepository = ManagerRepository.getInstance();
    }

    public Response createPublisher(String nit, String name, String address, long managerId) {

        if (nit == null || nit.isBlank() ||
            name == null || name.isBlank() ||
            address == null || address.isBlank()) {

            return new Response("Todos los campos deben estar completos", StatusCode.BAD_REQUEST);
        }

        if (!nit.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d")) {
            return new Response("Formato de NIT inválido. Debe ser XXX.XXX.XXX-X", StatusCode.BAD_REQUEST);
        }

        if (publisherRepository.exists(nit)) {
            return new Response("Ya existe una editorial con este NIT", StatusCode.BAD_REQUEST);
        }

        Manager manager = managerRepository.findById(managerId);
        if (manager == null) {
            return new Response("El gerente no existe", StatusCode.NOT_FOUND);
        }

        Publisher publisher = new Publisher(nit, name, address, manager);


        publisherRepository.add(publisher);

        Publisher copy = publisherRepository.findByNit(nit);

        return new Response(
            "Editorial registrada correctamente",
            StatusCode.CREATED,
            copy
        );
    }

    public Response getAllPublishers() {

        ArrayList<Publisher> publishers = publisherRepository.getAllOrderedByNit();

        return new Response(
            "Editoriales obtenidas correctamente",
            StatusCode.OK,
            publishers
        );
    }

    public Response getPublisherByNit(String nit) {

        if (nit == null || nit.isBlank()) {
            return new Response("NIT inválido", StatusCode.BAD_REQUEST);
        }

        Publisher publisher = publisherRepository.findByNit(nit);

        if (publisher == null) {
            return new Response("Editorial no encontrada", StatusCode.NOT_FOUND);
        }

        return new Response(
            "Editorial encontrada",
            StatusCode.OK,
            publisher
        );
    }
}
