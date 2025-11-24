package core.controller;

import core.controller.utils.Response;
import core.controller.utils.StatusCode;
import core.model.persona.Author;
import core.model.persona.Manager;
import core.model.persona.Narrator;
import core.repository.AuthorRepository;
import core.repository.ManagerRepository;
import core.repository.NarratorRepository;
import core.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

public class PersonController {

    private final AuthorRepository authorRepository;
    private final ManagerRepository managerRepository;
    private final NarratorRepository narratorRepository;
    private final PersonRepository personRepository;

    public PersonController() {
        this.authorRepository = AuthorRepository.getInstance();
        this.managerRepository = ManagerRepository.getInstance();
        this.narratorRepository = NarratorRepository.getInstance();
        this.personRepository = PersonRepository.getInstance();
    }

    public Response createAuthor(long id, String name, String lastName) {

        if (!isValidId(id)) {
            return new Response("ID inválido", StatusCode.BAD_REQUEST);
        }

        if (name == null || name.isEmpty() || lastName == null || lastName.isEmpty()) {
            return new Response("Los campos no pueden estar vacíos", StatusCode.BAD_REQUEST);
        }

        if (personRepository.exists(id)) {
            return new Response("Ya existe una persona con ese ID", StatusCode.BAD_REQUEST);
        }

        Author author = new Author(id, name, lastName);

        authorRepository.add(author);
        personRepository.add(author);

        Author copy = authorRepository.findById(id);

        return new Response(
                "Autor creado correctamente",
                StatusCode.CREATED,
                copy
        );
    }

    public Response createManager(long id, String name, String lastName) {

        if (!isValidId(id)) {
            return new Response("ID inválido", StatusCode.BAD_REQUEST);
        }

        if (name == null || name.isEmpty() || lastName == null || lastName.isEmpty()) {
            return new Response("Los campos no pueden estar vacíos", StatusCode.BAD_REQUEST);
        }

        if (personRepository.exists(id)) {
            return new Response("Ya existe una persona con ese ID", StatusCode.BAD_REQUEST);
        }

        Manager manager = new Manager(id, name, lastName);

        managerRepository.add(manager);
        personRepository.add(manager);

        Manager copy = managerRepository.findById(id);

        return new Response(
                "Gerente creado correctamente",
                StatusCode.CREATED,
                copy
        );
    }

    public Response createNarrator(long id, String name, String lastName) {

        if (!isValidId(id)) {
            return new Response("ID inválido", StatusCode.BAD_REQUEST);
        }

        if (name == null || name.isEmpty() || lastName == null || lastName.isEmpty()) {
            return new Response("Los campos no pueden estar vacíos", StatusCode.BAD_REQUEST);
        }

        if (personRepository.exists(id)) {
            return new Response("Ya existe una persona con ese ID", StatusCode.BAD_REQUEST);
        }

        Narrator narrator = new Narrator(id, name, lastName);

        narratorRepository.add(narrator);
        personRepository.add(narrator);

        Narrator copy = narratorRepository.findById(id);

        return new Response(
                "Narrador creado correctamente",
                StatusCode.CREATED,
                copy
        );
    }

    public Response getAllPeople() {

        List<Object> people = new ArrayList<>();

        // Repositorios ya retornan COPIAS PROFUNDAS
        people.addAll(authorRepository.getAllOrderedById());
        people.addAll(managerRepository.getAllOrderedById());
        people.addAll(narratorRepository.getAllOrderedById());

        return new Response(
                "Personas obtenidas correctamente",
                StatusCode.OK,
                people
        );
    }

    private boolean isValidId(long id) {
        return id >= 0 && String.valueOf(id).length() <= 15;
    }
}
