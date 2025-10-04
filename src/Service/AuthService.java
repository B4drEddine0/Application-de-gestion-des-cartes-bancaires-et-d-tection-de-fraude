package Service;

import Dao.ClientRepository;
import Modal.Client;

import java.util.List;
import java.util.Optional;

public class AuthService {
    private final ClientRepository clientRepo;

    public AuthService(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public void register(String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        if (clientRepo.findClientByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Client client = new Client(name, email, password);
        clientRepo.insertClient(client);
    }

    public Optional<Client> findClientById(int id) {
        return clientRepo.findClientById(id);
    }

    public Optional<Client> findClientByEmail(String email) {
        return clientRepo.findClientByEmail(email);
    }

    public List<Client> getAllClients() {
        return clientRepo.getAllClients();
    }

    public void updateClient(Client client) {
        if (client.id() <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }
        clientRepo.updateClient(client);
    }

    public void deleteClient(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid client ID");
        }
        clientRepo.deleteClient(id);
    }
}
