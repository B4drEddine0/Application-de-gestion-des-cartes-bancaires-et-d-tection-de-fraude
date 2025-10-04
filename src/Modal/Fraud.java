package Modal;

public record Fraud(int id , String description , String level , int idCard) {
    public Fraud(String description , String level , int idCard){
        this(0 , description , level , idCard);
    }
}
