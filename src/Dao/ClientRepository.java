package Dao;

import Modal.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    void insertClient(Client client);

    Optional<Client> findClientByEmail(String email);

    Optional<Client> findClientById(int id);

    List<Client> getAllClients();

    void updateClient(Client client);

    void deleteClient(int id);
}
