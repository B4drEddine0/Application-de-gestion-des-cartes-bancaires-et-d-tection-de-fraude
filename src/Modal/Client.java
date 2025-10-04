package Modal;

public record Client(int id, String name, String email, String password) {
    
    public Client(String name, String email, String password) {
        this(0, name, email, password);
    }
}